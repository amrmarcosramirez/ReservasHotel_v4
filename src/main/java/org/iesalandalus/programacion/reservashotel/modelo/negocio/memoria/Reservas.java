package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Reservas implements IReservas {

    // Se crean los atributos con su visibilidad adecuada
    private List<Reserva> coleccionReservas;

    public  Reservas() {
        this.coleccionReservas = new ArrayList<>();
    }

    public void comenzar() {}

    public void terminar() {}

    @Override
    public List<Reserva> get() {
        List<Reserva> copiaReservas = new ArrayList<>();
        for (Reserva coleccionReserva : coleccionReservas) {
            copiaReservas.add(new Reserva(coleccionReserva));
        }
        return copiaReservas;
    }

    @Override
    public int getTamano() {
        return coleccionReservas.size();
    }

    @Override
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
       Objects.requireNonNull(reserva, "ERROR: No se puede insertar una reserva nula.");
        if (!coleccionReservas.contains(reserva)) {
            coleccionReservas.add(new Reserva(reserva));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        }

    }

    @Override
    public Reserva buscar(Reserva reserva) {
        Objects.requireNonNull(reserva, "ERROR: No se puede buscar una reserva nula.");
        int indice = coleccionReservas.indexOf(reserva);
        if (indice == -1) {
            return null;
        } else {
            return new Reserva(coleccionReservas.get(indice));
        }
    }

    @Override
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        Objects.requireNonNull(reserva, "ERROR: No se puede borrar una reserva nula.");
        int indice = coleccionReservas.indexOf(reserva);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        } else {
            coleccionReservas.remove(indice);
        }
    }

    @Override
    public List<Reserva> getReservas(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se pueden buscar reservas de un huésped nulo.");
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
        Objects.requireNonNull(tipoHabitacion, "ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();
            if (reserva.getHabitacion().getClass().isInstance(tipoHabitacion)) {
                reservasHabitacion.add(new Reserva(reserva));
            }
        }
        return reservasHabitacion;
    }

    public List<Reserva> getReservas(Habitacion habitacion) {
        Objects.requireNonNull(habitacion, "ERROR: No se pueden buscar reservas de una habitación nula.");
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
        Objects.requireNonNull(habitacion, "ERROR: No se pueden buscar reservas de una habitación nula.");
        List<Reserva> reservasHabitacion = new ArrayList<>();
        for (Iterator<Reserva> it = get().iterator(); it.hasNext();) {
            Reserva reserva = it.next();
            if (reserva != null && reserva.getHabitacion().equals(habitacion) &&
                    reserva.getFechaInicioReserva().isAfter(LocalDate.now())) {
                reservasHabitacion.add(new Reserva(reserva));
            }
        }
        return reservasHabitacion;
    }

    @Override
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reserva.setCheckIn(fecha);
    }

    @Override
    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        reserva.setCheckOut(fecha);
    }

}
