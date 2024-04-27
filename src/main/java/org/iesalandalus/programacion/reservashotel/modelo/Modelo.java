package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.*;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.List;

public class Modelo implements IModelo {

    // Se crean los atributos con su visibilidad adecuada
    private IHabitaciones habitaciones;
    private IReservas reservas;
    private IHuespedes huespedes;
    private IFuenteDatos fuenteDatos;

    //Se crea el constructor
    public Modelo(FactoriaFuenteDatos factoriaFuenteDatos) {
        setFuenteDatos(factoriaFuenteDatos.crear());
        comenzar();
    }

    //Se crean los métodos
    public void comenzar() {
        this.habitaciones = fuenteDatos.crearHabitaciones();
        this.reservas = fuenteDatos.crearReservas();
        this.huespedes = fuenteDatos.crearHuespedes();
    }

    public void terminar() {
        //System.out.println("Hasta luego.!!!");
        habitaciones.terminar();
        reservas.terminar();
        huespedes.terminar();
    }

    private void setFuenteDatos(IFuenteDatos fuenteDatos) {
        this.fuenteDatos = fuenteDatos;
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped) {
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        huespedes.borrar(huesped);
    }

    public List<Huesped> getHuespedes() {
        return huespedes.get();
    }

    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion) {
        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        habitaciones.borrar(habitacion);
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones.get();
    }

    public List<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion) {
        return habitaciones.get(tipoHabitacion);
    }

    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        reservas.insertar(reserva);
    }

    public Reserva buscar(Reserva reserva) {
        return reservas.buscar(reserva);
    }

    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        reservas.borrar(reserva);
    }

    public List<Reserva> getReservas() {
        return reservas.get();
    }

    public List<Reserva> getReservas(Huesped huesped) {
        return reservas.getReservas(huesped);
    }

    public List<Reserva> getReservas(Habitacion habitacion) {
        return reservas.getReservas(habitacion);
    }

    public List<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        return reservas.getReservas(tipoHabitacion);
    }

    public List<Reserva> getReservasFuturas(Habitacion habitacion) {
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckin(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckin(reserva, fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckOut(reserva, fecha);
    }

}
