package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Huespedes;
import org.iesalandalus.programacion.utilidades.Entrada;


public class MongoDB {

    // Se crean los atributos con su visibilidad adecuada
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final String SERVIDOR = "cluster0.nkyqs3e.mongodb.net";
    //private static final String SERVIDOR = "localhost";
    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";
    //static final String USUARIO = "admin";
    //static final String CONTRASENA = "1234";

    public static final String HUESPED = "huesped";
    public static final String NOMBRE = "nombre";
    public static final String DNI = "dni";
    public static final String TELEFONO = "telefono";
    public static final String CORREO = "correo";
    public static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String HUESPED_DNI = HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR = "identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO = "precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    public static final String TIPO_SIMPLE = "SIMPLE";
    public static final String TIPO_DOBLE = "DOBLE";
    public static final String TIPO_TRIPLE = "TRIPLE";
    public static final String TIPO_SUITE = "SUITE";
    public static final String CAMAS_INDIVIDUALES = "camas_individuales";
    public static final String CAMAS_DOBLES = "camas_dobles";
    public static final String BANOS = "banos";
    public static final String JACUZZI = "jacuzzi";
    public static final String RESERVA = "reserva";
    public static final String REGIMEN = "regimen";
    public static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    public static final String CHECKIN = "chekin";
    public static final String CHECKOUT = "checkout";
    public static final String PRECIO_RESERVA = "precio_reserva";
    public static final String NUMERO_PERSONAS = "numero_personas";
    private static MongoClient conexion = null;

    //Constructores
    private MongoDB() {

        //establecerConexion();
    }

    //Métodos de acceso y modificación
    public static MongoDatabase getBD() {
        // Establecemos la conexion si es nula
        if(conexion == null) {
            establecerConexion();
        }
        // Accedemos a la base de datos
        MongoDatabase database = conexion.getDatabase(BD);
        return database;
    }

    private static MongoClient establecerConexion(){

        //tring connectionString = "mongodb://"+SERVIDOR+":"+PUERTO;
        String connectionString = "mongodb+srv://"+USUARIO+":"+CONTRASENA+"@"+SERVIDOR+
                "/?retryWrites=true&w=majority&appName=Cluster0";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        try
        {
            conexion = MongoClients.create(settings);
            System.out.println("Conexión a MongoDB realizada correctamente.");
        }
        catch (MongoException e)
        {
            e.printStackTrace();
        }


        /*Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);
        if (conexion == null) {
            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, BD, CONTRASENA.toCharArray());
            conexion = MongoClients.create(
                    MongoClientSettings.builder()
                            .applyToClusterSettings(builder ->
                                    builder.hosts(Arrays.asList(new ServerAddress(SERVIDOR, PUERTO))))
                            .credential(credenciales)
                            .build());
            System.out.println("Conexión a MongoDB realizada correctamente.");
        }*/
        return conexion;
    }

    public static void cerrarConexion() {
        // Cerramos la conexion en caso de no ser nula
        if(!(conexion == null)) {
            conexion.close();
        }
    }

    public static Document getDocumento(Huesped huesped) {
        Document docHuesped = new Document();
        docHuesped.append(NOMBRE, huesped.getNombre());
        docHuesped.append(DNI, huesped.getDni());
        docHuesped.append(CORREO, huesped.getCorreo());
        docHuesped.append(TELEFONO, huesped.getTelefono());
        docHuesped.append(FECHA_NACIMIENTO, huesped.getFechaNacimiento().format(FORMATO_DIA));

        return docHuesped;
    }

    public static Huesped getHuesped(Document documentoHuesped) {

        String nombre = documentoHuesped.getString(NOMBRE);
        String dni = documentoHuesped.getString(DNI);
        String correo = documentoHuesped.getString(CORREO);
        String telefono = documentoHuesped.getString(TELEFONO);
        LocalDate fechaNacimiento = LocalDate.parse(documentoHuesped.getString(FECHA_NACIMIENTO),
                FORMATO_DIA);
        Huesped huesped = new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
        return huesped;
    }

    public static Document getDocumento(Habitacion habitacion) {
        Document dochabitacion = new Document();
        dochabitacion.append(PLANTA, habitacion.getPlanta());
        dochabitacion.append(PUERTA, habitacion.getPuerta());
        dochabitacion.append(PRECIO, habitacion.getPrecio());
        dochabitacion.append(IDENTIFICADOR, habitacion.getIdentificador());

        if(habitacion instanceof Simple){
            //dochabitacion.append(HABITACION_TIPO, TIPO_SIMPLE);
            dochabitacion.append(TIPO, TIPO_SIMPLE);
        } else if(habitacion instanceof Doble){
            Doble habitacion1 = new Doble((Doble) habitacion);
            dochabitacion.append(CAMAS_INDIVIDUALES, habitacion1.getNumCamasIndividuales());
            dochabitacion.append(CAMAS_DOBLES, habitacion1.getNumCamasDobles());
            //dochabitacion.append(HABITACION_TIPO, TIPO_DOBLE);
            dochabitacion.append(TIPO, TIPO_DOBLE);
        } else if(habitacion instanceof Triple){
            Triple habitacion1 = new Triple((Triple) habitacion);
            dochabitacion.append(BANOS, habitacion1.getNumBanos());
            dochabitacion.append(CAMAS_INDIVIDUALES, habitacion1.getNumCamasIndividuales());
            dochabitacion.append(CAMAS_DOBLES, habitacion1.getNumCamasDobles());
            //dochabitacion.append(HABITACION_TIPO, TIPO_TRIPLE);
            dochabitacion.append(TIPO, TIPO_TRIPLE);
        } else {
            Suite habitacion1 = new Suite((Suite) habitacion);
            dochabitacion.append(BANOS, habitacion1.getNumBanos());
            dochabitacion.append(JACUZZI, habitacion1.isTieneJacuzzi());
            //dochabitacion.append(HABITACION_TIPO, TIPO_SUITE);
            dochabitacion.append(TIPO, TIPO_SUITE);
        }
        return dochabitacion;
    }

    public static Habitacion getHabitacion(Document documentoHabitacion) {
        Habitacion habitacion;
        int planta = documentoHabitacion.getInteger(PLANTA);
        int puerta = documentoHabitacion.getInteger(PUERTA);
        double precio = documentoHabitacion.getDouble(PRECIO);

        if(documentoHabitacion.getString(TIPO).equals(TIPO_SIMPLE)){
            habitacion = new Simple(planta, puerta, precio);
        } else if(documentoHabitacion.getString(TIPO).equals(TIPO_DOBLE)){
            int numCamasIndividuales = documentoHabitacion.getInteger(CAMAS_INDIVIDUALES);
            int numCamasDobles = documentoHabitacion.getInteger(CAMAS_DOBLES);
            habitacion = new Doble(planta, puerta, precio, numCamasIndividuales, numCamasDobles);
        } else if(documentoHabitacion.getString(TIPO).equals(TIPO_TRIPLE)){
            int numBanos = documentoHabitacion.getInteger(BANOS);
            int numCamasIndividuales = documentoHabitacion.getInteger(CAMAS_INDIVIDUALES);
            int numCamasDobles = documentoHabitacion.getInteger(CAMAS_DOBLES);
            habitacion = new Triple(planta, puerta, precio, numBanos, numCamasIndividuales,
                                    numCamasDobles);
        } else {
            int numBanos = documentoHabitacion.getInteger(BANOS);
            boolean tieneJacuzzi = documentoHabitacion.getBoolean(JACUZZI);
            habitacion = new Suite(planta, puerta, precio, numBanos, tieneJacuzzi);
        }

        return habitacion;
    }

    public static Document getDocumento(Reserva reserva) {
        Document docReserva = new Document();
        Document documentoHuesped = getDocumento(reserva.getHuesped());
        Document documentoHabitacion = getDocumento(reserva.getHabitacion());

        docReserva.append(HUESPED, documentoHuesped);
        docReserva.append(HABITACION, documentoHabitacion);

        //Document documentoreserva = new Document();

       /* Document regimen = new Document();
        regimen.append("nombre", Regimen.values()[reserva.getRegimen().ordinal()]);
        regimen.append("incremento", reserva.getRegimen().getIncrementoPrecio());*/
        /*documentoreserva.append(REGIMEN, reserva.getRegimen().ordinal() + ".- " +
                reserva.getRegimen());
        documentoreserva.append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA));
        documentoreserva.append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_DIA));
        documentoreserva.append(NUMERO_PERSONAS, reserva.getNumeroPersonas());

        if (reserva.getCheckIn() == null) {
            documentoreserva.append(CHECKIN, null);
        } else {
            documentoreserva.append(CHECKIN, reserva.getCheckIn().format(FORMATO_DIA_HORA));
        }

        if (reserva.getCheckOut() == null) {
            documentoreserva.append(CHECKOUT, null);
        } else {
            documentoreserva.append(CHECKOUT, reserva.getCheckOut().format(FORMATO_DIA_HORA));
        }
        //documentoreserva.append(CHECKIN, null);
        //documentoreserva.append(CHECKOUT, null);

        documentoreserva.append(PRECIO_RESERVA, reserva.getPrecio());
        docReserva.append(RESERVA, documentoreserva);*/

        docReserva.append(REGIMEN, reserva.getRegimen().ordinal() + ".- " +
                reserva.getRegimen());
        docReserva.append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva().format(FORMATO_DIA));
        docReserva.append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva().format(FORMATO_DIA));
        docReserva.append(NUMERO_PERSONAS, reserva.getNumeroPersonas());

        if (reserva.getCheckIn() == null) {
            docReserva.append(CHECKIN, null);
        } else {
            docReserva.append(CHECKIN, reserva.getCheckIn().format(FORMATO_DIA_HORA));
        }

        if (reserva.getCheckOut() == null) {
            docReserva.append(CHECKOUT, null);
        } else {
            docReserva.append(CHECKOUT, reserva.getCheckOut().format(FORMATO_DIA_HORA));
        }
        //documentoreserva.append(CHECKIN, null);
        //documentoreserva.append(CHECKOUT, null);

        docReserva.append(PRECIO_RESERVA, reserva.getPrecio());

        return docReserva;
    }

    public static Reserva getReserva(Document documentoReserva) {
        //Document documentoHuesped = getDocumento((Huesped) documentoReserva.get(HUESPED));
        Document documentoHuesped = (Document) documentoReserva.get(HUESPED);
        Huesped huesped = getHuesped(documentoHuesped);

        Document documentoHabitacion = (Document) documentoReserva.get(HABITACION);
        Habitacion habitacion = getHabitacion(documentoHabitacion);

        //System.out.println("NADA");

        /*
        String regimen = documentoReserva.getString(REGIMEN);
        Regimen.values().toString().equals(regimen);
        System.out.println(Regimen.values().toString().equals(regimen));
        */

        //Document documentoReserva2 = (Document) documentoReserva.get(RESERVA);
        //System.out.println(documentoReserva2.toString());
        int k = Integer.parseInt(documentoReserva.getString(REGIMEN).substring(0,1));
        Regimen regimen = Regimen.values()[k];

        LocalDate fechaInicioReserva = LocalDate.parse(documentoReserva.getString(FECHA_INICIO_RESERVA),
                FORMATO_DIA);
        LocalDate fechaFinReserva = LocalDate.parse(documentoReserva.getString(FECHA_FIN_RESERVA),
                FORMATO_DIA);
        //System.out.println(fechaInicioReserva);
        //System.out.println(fechaFinReserva);

        int numeroPersonas = documentoReserva.getInteger(NUMERO_PERSONAS);

        Reserva reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva,
                fechaFinReserva, numeroPersonas);

        //System.out.println(reserva.getCheckIn());

        if(documentoReserva.getString(CHECKIN) != null){
            LocalDateTime checkIn = LocalDateTime.parse(documentoReserva.getString(CHECKIN),FORMATO_DIA_HORA);
            reserva.setCheckIn(checkIn);

            //System.out.println(reserva.getCheckIn());
        }
        if(documentoReserva.getString(CHECKOUT) != null){
            LocalDateTime checkOut = LocalDateTime.parse(documentoReserva.getString(CHECKOUT),FORMATO_DIA_HORA);
            reserva.setCheckOut(checkOut);
            //System.out.println(reserva.getCheckOut());
        }


        //System.out.println(checkOut);

        //LocalDateTime checkIn = (LocalDateTime) documentoReserva.get(CHECKIN);
        //LocalDateTime checkOut = (LocalDateTime) documentoReserva.get(CHECKOUT);



        //reserva.setCheckIn(checkIn);
        //reserva.setCheckOut(checkOut);
        //reserva.getPrecio();
        //reserva = new Reserva(reserva);
        //reserva.setCheckIn(checkIn);
        //reserva.setCheckOut(checkOut);
        //System.out.println(reserva.toString());
        return reserva;
    }
}
