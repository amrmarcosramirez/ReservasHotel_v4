package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Huespedes;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;
import static org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB.*;
import static org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB.getHuesped;

public class Reservas implements IReservas {

    // Se crean los atributos con su visibilidad adecuada
    //private List<Reserva> coleccionReservas;
    private MongoCollection<Document> coleccionReservas;
    private static final String COLECCION = "reservas";

    public  Reservas() {

        comenzar();
    }

    public void comenzar() {
        this.coleccionReservas = getBD().getCollection(COLECCION);
        //this.coleccionReservas.drop();
    }

    public void terminar() {cerrarConexion();}

    @Override
    public List<Reserva> get() {
        List<Reserva> copiaReservas = new ArrayList<>();

        Document ordenacion = new Document(FECHA_INICIO_RESERVA,1); // 1 A-Z, 0 Z-A
        FindIterable<Document> listado = coleccionReservas.find().sort(ordenacion);
        for(Document documentoReserva : listado)
        {
            copiaReservas.add(getReserva(documentoReserva));
        }
        return copiaReservas;
    }

    @Override
    public int getTamano() {
        int tamanio = (int) coleccionReservas.countDocuments();
        return tamanio;
    }

    @Override
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
       Objects.requireNonNull(reserva, "ERROR: No se puede insertar una reserva nula.");

       Document documentoReserva = getDocumento(reserva);
       List<Reserva> listaReserva = get();
       if (!listaReserva.contains(reserva)) {
           coleccionReservas.insertOne(documentoReserva);
       } else {
           throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
       }
    }

    @Override
    public Reserva buscar(Reserva reserva) {
        Objects.requireNonNull(reserva, "ERROR: No se puede buscar una reserva nula.");

        List<Reserva> listaReservas = get();
        int indice = listaReservas.indexOf(reserva);
        if (indice == -1) {
            return null;
        } else {
            return new Reserva(listaReservas.get(indice));
        }
    }

    @Override
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        Objects.requireNonNull(reserva, "ERROR: No se puede borrar una reserva nula.");

        List<Reserva> listaReservas = get();
        int indice = listaReservas.indexOf(reserva);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        } else {
            Document documentoReserva = getDocumento(reserva);
            coleccionReservas.deleteOne(documentoReserva);
        }
    }

    @Override
    public List<Reserva> getReservas(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se pueden buscar reservas de un hu�sped nulo.");
        List<Reserva> reservasHuesped = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();
            if (reserva != null && reserva.getHuesped().equals(huesped)) {
                reservasHuesped.add(new Reserva(reserva));
            }
        }
        return reservasHuesped;
    }

    @Override
    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        Objects.requireNonNull(tipoHabitacion, "ERROR: No se pueden buscar reservas de un tipo de habitaci�n nula.");
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();

            if (reserva.getHabitacion() instanceof Simple && tipoHabitacion == TipoHabitacion.SIMPLE) {
                reservasHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Doble && tipoHabitacion == TipoHabitacion.DOBLE) {
                reservasHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Triple && tipoHabitacion == TipoHabitacion.TRIPLE) {
                reservasHabitacion.add(new Reserva(reserva));
            } else if (reserva.getHabitacion() instanceof Suite && tipoHabitacion == TipoHabitacion.SUITE) {
                reservasHabitacion.add(new Reserva(reserva));
            }
        }
        return reservasHabitacion;
    }

    @Override
    public List<Reserva> getReservas(Habitacion habitacion) {
        Objects.requireNonNull(habitacion, "ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();
            if (reserva != null && reserva.getHabitacion().equals(habitacion)) {
                reservasHabitacion.add(new Reserva(reserva));
            }
        }
        return reservasHabitacion;
    }

    @Override
    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        Objects.requireNonNull(habitacion, "ERROR: No se pueden buscar reservas de una habitaci�n nula.");
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();
            if (reserva != null && reserva.getHabitacion().equals(habitacion) &&
            reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasHabitacion.add(new Reserva(reserva));
                //System.out.println("NADA");
            }
        }
        return reservasHabitacion;
    }

    @Override
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reserva.setCheckIn(fecha);
        Bson campo = set(CHECKIN, reserva.getCheckIn().format(FORMATO_DIA_HORA));
        Bson c2 = and(eq(HUESPED_DNI, reserva.getHuesped().getDni()),
                eq(HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()));
        /*coleccionReservas.updateOne(c2, Updates.set(RESERVA+"."+CHECKIN,
                reserva.getCheckIn().format(FORMATO_DIA_HORA)));*/

        coleccionReservas.updateOne(c2, campo);
    }

    @Override
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        //System.out.println(reserva.getCheckOut().format(FORMATO_DIA_HORA));
        //System.out.println(fecha.format(FORMATO_DIA_HORA));
        //System.out.println(reserva.getCheckIn());
        reserva.setCheckOut(fecha);
        //System.out.println(reserva.getCheckOut().format(FORMATO_DIA_HORA));
        Bson campo = set(CHECKOUT, reserva.getCheckOut().format(FORMATO_DIA_HORA));
        Bson c2 = and(eq(HUESPED_DNI, reserva.getHuesped().getDni()),
                eq(HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador()));
        coleccionReservas.updateOne(c2, campo);
    }

}
