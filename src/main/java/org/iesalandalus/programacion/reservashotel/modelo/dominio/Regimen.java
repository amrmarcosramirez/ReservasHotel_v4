package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("Sólo alojamiento", 0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y desayuno", 15),
    MEDIA_PENSION("Media Pensión", 30),
    PENSION_COMPLETA("Pensión Completa", 50);

    // Se crean los atributos con su visibilidad adecuada
    private String cadenaAMostrar;
    private double incrementoPrecio;

    //Constructor
    Regimen(String cadenaAMostrar, double incrementoPrecio) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }

    //Método de acceso
    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    //Método toString
    @Override
    public String toString() {
        return String.format("Régimen[descripción=%s, incremento precio=%s]",
                this.cadenaAMostrar, getIncrementoPrecio());
    }
}
