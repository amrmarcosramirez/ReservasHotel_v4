package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class MongoDB {

    // Se crean los atributos con su visibilidad adecuada
    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final String SERVIDOR = "cluster0.nkyqs3e.mongodb.net";
    private static final int PUERTO = 27017;
    private static final String BD = "reservashotel";
    private static final String USUARIO = "reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";

    private static final String HUESPED = "huesped";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";
    private static final String CORREO = "correo";
    private static final String FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String HUESPED_DNI = HUESPED + "." + DNI;
    private static final String HABITACION = "habitacion";
    private static final String IDENTIFICADOR = "identificador";
    private static final String PLANTA = "planta";
    private static final String PUERTA = "puerta";
    private static final String PRECIO = "precio";
    private static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    private static final String TIPO = "tipo";
    private static final String HABITACION_TIPO = HABITACION + "." + TIPO;
    private static final String TIPO_SIMPLE = "SIMPLE";
    private static final String TIPO_DOBLE = "DOBLE";
    private static final String TIPO_TRIPLE = "TRIPLE";
    private static final String TIPO_SUITE = "SUITE";
    private static final String CAMAS_INDIVIDUALES = "camas_individuales";
    private static final String CAMAS_DOBLES = "camas_dobles";
    private static final String BANOS = "banos";
    private static final String JACUZZI = "jacuzzi";
    private static final String RESERVA = "reserva";
    private static final String REGIMEN = "regimen";
    private static final String FECHA_INICIO_RESERVA = "fecha_inicio_reserva";
    private static final String FECHA_FIN_RESERVA = "fecha_fin_reserva";
    private static final String CHECKIN = "chekin";
    private static final String CHECKOUT = "checkout";
    private static final String PRECIO_RESERVA = "precio_reserva";
    private static final String NUMERO_PERSONAS = "numero_personas";
    private static MongoClient conexion;

    //Constructores
    private MongoDB() {
        establecerConexion();
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

    private static void establecerConexion(){

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
        }
        catch (MongoException e)
        {
            e.printStackTrace();
        }
    }

    public static void cerrarConexion() {
        // Cerramos la conexion en caso de no ser nula
        if(!(conexion == null)) {
            conexion.close();
        }
    }

    public static Document getDocumento(Huesped huesped) {
        // Accedemos a la coleccion de datos.
        MongoCollection<Document> coleccion = getBD().getCollection(HUESPED);
        coleccion.drop();

        Document docHuesped = (Document) coleccion.find().filter(eq(huesped.getDni()));
        return docHuesped;
    }

    public static Huesped getHuesped(Document documentoHuesped) {
        String nombre = documentoHuesped.getString(NOMBRE);
        String dni = documentoHuesped.getString(DNI);
        String correo = documentoHuesped.getString(CORREO);
        String telefono = documentoHuesped.getString(TELEFONO);
        LocalDate fechaNacimiento = (LocalDate) documentoHuesped.get(FECHA_NACIMIENTO);

        Huesped huesped = new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
        return huesped;
    }

    public static Document getDocumento(Habitacion habitacion) {
        // Accedemos a la coleccion de datos.
        MongoCollection<Document> coleccion = getBD().getCollection(HABITACION);
        coleccion.drop();
        Document dochabitacion = (Document) coleccion.find().filter(eq(habitacion.getIdentificador()));
        return dochabitacion;
    }

    public static TipoHabitacion getHabitacion(Document documentoHabitacion) {
        TipoHabitacion tipoHabitacion = TipoHabitacion.valueOf(documentoHabitacion.getString(HABITACION_TIPO));
        return tipoHabitacion;
    }

    public static Document getDocumento(Reserva reserva) {
        // Accedemos a la coleccion de datos.
        MongoCollection<Document> coleccion = getBD().getCollection(RESERVA);
        coleccion.drop();

        Document docReserva = (Document) coleccion.find().filter(and(eq(reserva.getHabitacion()),
                eq(reserva.getFechaInicioReserva())));
        return docReserva;
    }

    public static Reserva getReserva(Document documentoReserva) {
        Document documentoHuesped = getDocumento((Huesped) documentoReserva.get(HUESPED_DNI));
        Huesped huesped = getHuesped(documentoHuesped);
        //Habitacion habitacion = getHabitacion((Document) documentoReserva.get(HABITACION_IDENTIFICADOR));
        Document documentoHabitacion = getDocumento((Habitacion) documentoReserva.get(HABITACION_IDENTIFICADOR));
        TipoHabitacion tipoHabitacionhabitacion = getHabitacion(documentoHabitacion);
       /* String nombre = documentoReserva.getString(NOMBRE);
        String dni = documentoReserva.getString(DNI);
        String correo = documentoReserva.getString(CORREO);*/
        Regimen regimen = Regimen.valueOf(documentoReserva.getString(REGIMEN));
        LocalDate fechaInicioReserva = (LocalDate) documentoReserva.get(FECHA_INICIO_RESERVA);
        LocalDate fechaFinReserva = (LocalDate) documentoReserva.get(FECHA_FIN_RESERVA);
        int numeroPersonas =  documentoReserva.getInteger(NUMERO_PERSONAS);
        LocalDateTime checkIn = (LocalDateTime) documentoReserva.get(CHECKIN);
        LocalDateTime checkOut = (LocalDateTime) documentoReserva.get(CHECKOUT);
        //double precioReserva = documentoReserva.getDouble(PRECIO_RESERVA);
        Habitacion habitacion;
        int planta = documentoHabitacion.getInteger(PLANTA);
        int puerta = documentoHabitacion.getInteger(PUERTA);
        double precio = documentoHabitacion.getDouble(PRECIO);

        if(tipoHabitacionhabitacion.toString().equals(TIPO_SIMPLE)){
            habitacion = new Simple(planta, puerta, precio);
        } else if(tipoHabitacionhabitacion.toString().equals(TIPO_DOBLE)){
            int numCamasIndividuales = documentoHabitacion.getInteger(CAMAS_INDIVIDUALES);
            int numCamasDobles = documentoHabitacion.getInteger(CAMAS_DOBLES);
            habitacion = new Doble(planta, puerta, precio, numCamasIndividuales,
                    numCamasDobles);
        } else if(tipoHabitacionhabitacion.toString().equals(TIPO_TRIPLE)){
            int numBanos = documentoHabitacion.getInteger(BANOS);
            int numCamasIndividuales = documentoHabitacion.getInteger(CAMAS_INDIVIDUALES);
            int numCamasDobles = documentoHabitacion.getInteger(CAMAS_DOBLES);
            habitacion = new Triple(planta, puerta, precio, numBanos,
                    numCamasIndividuales, numCamasDobles);
        } else {
            int numBanos = documentoHabitacion.getInteger(BANOS);
            boolean tieneJacuzzi = documentoHabitacion.getBoolean(JACUZZI);
            habitacion = new Suite(planta, puerta, precio, numBanos, tieneJacuzzi);
        }

        Reserva reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva,
                fechaFinReserva, numeroPersonas);
        reserva.setCheckIn(checkIn);
        reserva.setCheckOut(checkOut);

        reserva = new Reserva(reserva);

        return reserva;
    }
}
