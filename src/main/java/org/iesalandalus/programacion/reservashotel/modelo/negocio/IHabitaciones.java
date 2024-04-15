package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Reservas;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IHabitaciones {
    List<Habitacion> get();
    List<Habitacion> get(TipoHabitacion tipoHabitacion);
    int getTamano();
    void insertar(Habitacion habitacion) throws OperationNotSupportedException;
    Habitacion buscar(Habitacion habitacion);
    void borrar(Habitacion habitacion) throws OperationNotSupportedException;
    void comenzar();
    void terminar();
}
