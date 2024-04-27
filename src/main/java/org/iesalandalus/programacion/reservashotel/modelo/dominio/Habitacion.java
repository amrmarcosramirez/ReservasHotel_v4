package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {

    // Se crean los atributos con su visibilidad adecuada
    public static final double MIN_PRECIO_HABITACION = 40.0;
    public static final double MAX_PRECIO_HABITACION = 150.0;
    public static final int MIN_NUMERO_PUERTA = 0;
    public static final int MAX_NUMERO_PUERTA = 14;
    public static final int MIN_NUMERO_PLANTA = 1;
    public static final int MAX_NUMERO_PLANTA = 3;
    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;

    //Constructores
    public Habitacion(int planta, int puerta, double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador(String.valueOf(planta)+puerta);
        setIdentificador();
    }

    public Habitacion(Habitacion habitacion){
        Objects.requireNonNull(habitacion,
                "ERROR: No es posible copiar una habitación nula.");
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setIdentificador(String.valueOf(habitacion.getPlanta())+habitacion.getPuerta());
        setIdentificador();
    }

    //Métodos de acceso y modificación
    public abstract int getNumeroMaximoPersonas();

    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador() {
        this.identificador = String.valueOf(getPlanta())+getPuerta();
    }

    protected void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que "
                    + MIN_NUMERO_PLANTA + " ni mayor que "
                    + MAX_NUMERO_PLANTA + ".");
        } else {
            this.planta = planta;
        }
    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) {
        if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que "
                    + MIN_NUMERO_PUERTA + " ni mayor que "
                    + MAX_NUMERO_PUERTA + ".");
        } else {
            this.puerta = puerta;
        }
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) {
        if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que "
                    + MIN_PRECIO_HABITACION + " ni mayor que "
                    + MAX_PRECIO_HABITACION + ".");
        } else {
            this.precio = precio;
        }
    }

    //Métodos equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion habitacion2)) return false;
        return identificador.equals(habitacion2.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return String.format("identificador = %s (%d-%d), precio habitación = %s€",
                this.identificador, this.planta, this.puerta, this.precio);
    }
}
