package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class Habitaciones implements IHabitaciones {

    // Se crean los atributos con su visibilidad adecuada
    private List<Habitacion> coleccionHabitaciones;

    //Constructores
    public Habitaciones() {
        this.coleccionHabitaciones = new ArrayList<>();
    }

    public void comenzar() {}

    public void terminar() {}

    //Métodos de acceso y modificación
    @Override
    public List<Habitacion> get() {
        List<Habitacion> copiaHabitaciones = new ArrayList<>();
        for (Iterator<Habitacion> it = coleccionHabitaciones.iterator(); it.hasNext(); ) {
            Habitacion habitacion = it.next();
            if(habitacion instanceof Simple){
                copiaHabitaciones.add(new Simple((Simple) habitacion));
            }else if(habitacion instanceof Doble){
                copiaHabitaciones.add(new Doble((Doble) habitacion));
            }else if(habitacion instanceof Triple){
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            }else {
                copiaHabitaciones.add(new Suite((Suite) habitacion));
            }
        }

        return copiaHabitaciones;
    }

    @Override
    public List<Habitacion> get(TipoHabitacion tipoHabitacion) {
        List<Habitacion> copiaHabitaciones = new ArrayList<>();
        for (Iterator<Habitacion> it = get().iterator();it.hasNext();){
            Habitacion habitacion = it.next();
            if(habitacion.getClass().isInstance(tipoHabitacion)) {
                if(habitacion instanceof Simple){
                    copiaHabitaciones.add(new Simple((Simple) habitacion));
                }else if(habitacion instanceof Doble){
                    copiaHabitaciones.add(new Doble((Doble) habitacion));
                }else if(habitacion instanceof Triple){
                    copiaHabitaciones.add(new Triple((Triple) habitacion));
                }else {
                    copiaHabitaciones.add(new Suite((Suite) habitacion));
                }
            }
        }
        return copiaHabitaciones;
    }

    @Override
    public int getTamano() {
        return coleccionHabitaciones.size();
    }

    @Override
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        Objects.requireNonNull(habitacion, "ERROR: No se puede insertar una habitación nula.");
        if (!coleccionHabitaciones.contains(habitacion)) {
            if(habitacion instanceof Simple){
                coleccionHabitaciones.add(new Simple((Simple) habitacion));
            }else if(habitacion instanceof Doble){
                coleccionHabitaciones.add(new Doble((Doble) habitacion));
            }else if(habitacion instanceof Triple){
                coleccionHabitaciones.add(new Triple((Triple) habitacion));
            }else {
                coleccionHabitaciones.add(new Suite((Suite) habitacion));
            }
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
    }

    @Override
    public Habitacion buscar(Habitacion habitacion) {
        int indice = coleccionHabitaciones.indexOf(habitacion);
        if (indice == -1) {
            return null;
        } else {
            if(coleccionHabitaciones.get(indice) instanceof Simple){
                return new Simple((Simple) coleccionHabitaciones.get(indice));
            }else if(coleccionHabitaciones.get(indice) instanceof Doble){
                return new Doble((Doble) coleccionHabitaciones.get(indice));
            }else if(coleccionHabitaciones.get(indice) instanceof Triple){
                return new Triple((Triple) coleccionHabitaciones.get(indice));
            }else {
                return new Suite((Suite) coleccionHabitaciones.get(indice));
            }
        }
    }

    @Override
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        Objects.requireNonNull(habitacion, "ERROR: No se puede borrar una habitación nula.");
        int indice = coleccionHabitaciones.indexOf(habitacion);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún aula con ese nombre.");
        } else {
            coleccionHabitaciones.remove(indice);
        }
    }

}
