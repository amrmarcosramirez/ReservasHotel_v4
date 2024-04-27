package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;

import java.util.List;

public class FuenteDatosMongoDB implements IFuenteDatos {

    public IHuespedes crearHuespedes(){
        /*Huespedes huespedes = null;
        List<Huesped> coleccionHuespedes = huespedes.get();
        return (IHuespedes) coleccionHuespedes;*/
        return new Huespedes();
    };

    public IHabitaciones crearHabitaciones(){
        /*Habitaciones habitaciones = null;
        List<Habitacion> coleccionHabitaciones = habitaciones.get();
        return (IHabitaciones) coleccionHabitaciones;*/
        return new Habitaciones();
    };

    public IReservas crearReservas(){
        /*Reservas reservas = null;
        List<Reserva> coleccionReservas = reservas.get();
        return (IReservas) coleccionReservas;*/
        return new Reservas();
    };

}
