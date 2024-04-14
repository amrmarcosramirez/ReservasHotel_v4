package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.*;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Huespedes implements IHuespedes {

    // Se crean los atributos con su visibilidad adecuada
    private List<Huesped> coleccionHuespedes;

    //Constructores
    public Huespedes() {
        this.coleccionHuespedes = new ArrayList<>();
    }

    public void comenzar() {}

    public void terminar() {}

    //Métodos de acceso y modificación
    @Override
    public List<Huesped> get() {
        List<Huesped> copiaHuespedes = new ArrayList<>();
        for (Huesped coleccionHuespede : coleccionHuespedes) {
            copiaHuespedes.add(new Huesped(coleccionHuespede));
        }
        return copiaHuespedes;
    }

    @Override
    public int getTamano() {
        return coleccionHuespedes.size();
    }

    @Override
    public void insertar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede insertar un huésped nulo.");
        if (!coleccionHuespedes.contains(huesped)) {
            coleccionHuespedes.add(new Huesped(huesped));
        } else {
            throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
        }
    }

    @Override
    public Huesped buscar(Huesped huesped) {
        Objects.requireNonNull(huesped, "ERROR: No se puede buscar un huésped nulo.");
        int indice = coleccionHuespedes.indexOf(huesped);
        if (indice == -1) {
            return null;
        } else {
            return new Huesped(coleccionHuespedes.get(indice));
        }
    }

    @Override
    public void borrar(Huesped huesped) throws OperationNotSupportedException {
        Objects.requireNonNull(huesped, "ERROR: No se puede borrar un huésped nulo.");
        int indice = coleccionHuespedes.indexOf(huesped);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        } else {
            coleccionHuespedes.remove(indice);
        }
    }

}
