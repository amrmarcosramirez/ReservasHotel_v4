package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB.*;


public class Habitaciones implements IHabitaciones {

    // Se crean los atributos con su visibilidad adecuada
    //private List<Habitacion> coleccionHabitaciones;
    private MongoCollection<Document> coleccionHabitaciones;
    private static final String COLECCION = "habitaciones";

    //Constructores
    public Habitaciones() {
        comenzar();
    }

    public void comenzar() {
        //MongoCollection<Document> coleccion = getBD().getCollection(COLECCION);
        this.coleccionHabitaciones = getBD().getCollection(COLECCION);
        this.coleccionHabitaciones.drop();
    }

    public void terminar() {
        cerrarConexion();
    }

    //M�todos de acceso y modificaci�n
    @Override
    public List<Habitacion> get() {
        List<Habitacion> copiaHabitaciones = new ArrayList<>();

        Document ordenacion = new Document(IDENTIFICADOR,1); // 1 A-Z, 0 Z-A
        //FindIterable<Document> listado = coleccionHabitaciones.find().sort(ordenacion);
        //MongoCursor<Document> cursor = coleccionHabitaciones.find().sort(ordenacion).iterator();

        //for (Iterator<Habitacion> it = coleccionHabitaciones.iterator(); it.hasNext(); ) {
        for (MongoCursor<Document> cursor = coleccionHabitaciones.find().sort(ordenacion).iterator();
             cursor.hasNext();) {
            Document documentoHabitacion = cursor.next();
            copiaHabitaciones.add(getHabitacion(documentoHabitacion));

            /*Habitacion habitacion = getHabitacion(documentoHabitacion);
            if(habitacion instanceof Simple){
                copiaHabitaciones.add(new Simple((Simple) habitacion));
            }else if(habitacion instanceof Doble){
                copiaHabitaciones.add(new Doble((Doble) habitacion));
            }else if(habitacion instanceof Triple){
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            }else {
                copiaHabitaciones.add(new Suite((Suite) habitacion));
            }*/
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
        int tamanio = (int) coleccionHabitaciones.countDocuments();
        return tamanio;
    }

    @Override
    public void insertar(Habitacion habitacion) throws OperationNotSupportedException {
        Objects.requireNonNull(habitacion, "ERROR: No se puede insertar una habitaci�n nula.");
        Document documentoHabitacion = getDocumento(habitacion);
        List<Habitacion> listaHabitacion = get();
        if (!listaHabitacion.contains(habitacion)) {
            coleccionHabitaciones.insertOne(documentoHabitacion);
            /*if(habitacion instanceof Simple){
                coleccionHabitaciones.add(new Simple((Simple) habitacion));
            }else if(habitacion instanceof Doble){
                coleccionHabitaciones.add(new Doble((Doble) habitacion));
            }else if(habitacion instanceof Triple){
                coleccionHabitaciones.add(new Triple((Triple) habitacion));
            }else {
                coleccionHabitaciones.add(new Suite((Suite) habitacion));
            }*/
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }
    }

    @Override
    public Habitacion buscar(Habitacion habitacion) {
        Objects.requireNonNull(habitacion, "ERROR: No se puede buscar una habitacion nula.");

        List<Habitacion> listaHabitacion = get();
        int indice = listaHabitacion.indexOf(habitacion);
        if (indice == -1) {
            return null;
        } else {
            if(listaHabitacion.get(indice) instanceof Simple){
                return new Simple((Simple) listaHabitacion.get(indice));
            }else if(listaHabitacion.get(indice) instanceof Doble){
                return new Doble((Doble) listaHabitacion.get(indice));
            }else if(listaHabitacion.get(indice) instanceof Triple){
                return new Triple((Triple) listaHabitacion.get(indice));
            }else {
                return new Suite((Suite) listaHabitacion.get(indice));
            }
        }
    }

    @Override
    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        Objects.requireNonNull(habitacion, "ERROR: No se puede borrar una habitaci�n nula.");

        List<Habitacion> listaHabitacion = get();
        int indice = listaHabitacion.indexOf(habitacion);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n con ese identificador.");
        } else {
            Document documentoHabitacion = getDocumento(habitacion);
            coleccionHabitaciones.deleteOne(documentoHabitacion);
        }
    }

}
