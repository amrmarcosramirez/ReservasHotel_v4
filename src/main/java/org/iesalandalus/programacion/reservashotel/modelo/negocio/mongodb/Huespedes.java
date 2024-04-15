package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;
import static org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB.*;

public class Huespedes implements IHuespedes {

    // Se crean los atributos con su visibilidad adecuada
    //private List<Huesped> coleccionHuespedes;
    private MongoCollection<Document> coleccionHuespedes;
    private static final String COLECCION = "huespedes";

    //Constructores
    public Huespedes() {
        //this.coleccionHuespedes = new ArrayList<>();
        comenzar();
    }

    public void comenzar() {
        //MongoCollection<Document> coleccion = getBD().getCollection(COLECCION);
        this.coleccionHuespedes = getBD().getCollection(COLECCION);
        this.coleccionHuespedes.drop();
    }

    public void terminar() {
        cerrarConexion();
    }

    //M�todos de acceso y modificaci�n
    @Override
    public List<Huesped> get() {
        List<Huesped> copiaHuespedes = new ArrayList<>();

        Document ordenacion = new Document(DNI,1); // 1 A-Z, 0 Z-A
        FindIterable<Document> listado = coleccionHuespedes.find().sort(ordenacion);
        for(Document documentoHuesped : listado)
        {
            copiaHuespedes.add(new Huesped(getHuesped(documentoHuesped)));
        }
        return copiaHuespedes;
    }

    @Override
    public int getTamano() {
        int tamanio = (int) coleccionHuespedes.countDocuments();
        return tamanio;
    }

    @Override
    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede insertar un hu�sped nulo.");
        Document documentoHuesped = getDocumento(huesped);
        List<Huesped> listaHuespedes = get();
        if (!listaHuespedes.contains(huesped)) {
            coleccionHuespedes.insertOne(documentoHuesped);
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un hu�sped con ese dni.");
        }
    }

    @Override
    public Huesped buscar(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se puede buscar un hu�sped nulo.");

        List<Huesped> listaHuespedes = get();
        int indice = listaHuespedes.indexOf(huesped);
        if (indice == -1) {
            return null;
        } else {
            return new Huesped(listaHuespedes.get(indice));
        }
    }

    @Override
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede borrar un hu�sped nulo.");

        List<Huesped> listaHuespedes = get();
        int indice = listaHuespedes.indexOf(huesped);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ning�n hu�sped como el indicado.");
        } else {
            Document documentoHuesped = getDocumento(huesped);
            coleccionHuespedes.deleteOne(documentoHuesped);
        }
    }

}
