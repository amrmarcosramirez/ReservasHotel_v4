package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum Regimen {
    SOLO_ALOJAMIENTO("S�lo alojamiento", 0),
    ALOJAMIENTO_DESAYUNO("Alojamiento y desayuno", 15),
    MEDIA_PENSION("Media Pensi�n", 30),
    PENSION_COMPLETA("Pensi�n Completa", 50);

    // Se crean los atributos con su visibilidad adecuada
    private String cadenaAMostrar;
    private double incrementoPrecio;

    //Constructor
    Regimen(String cadenaAMostrar, double incrementoPrecio) {
        this.cadenaAMostrar = cadenaAMostrar;
        this.incrementoPrecio = incrementoPrecio;
    }

    //M�todo de acceso
    public double getIncrementoPrecio() {
        return incrementoPrecio;
    }

    //M�todo toString
    @Override
    public String toString() {
        return String.format("R�gimen[descripci�n=%s, incremento precio=%s]",
                this.cadenaAMostrar, getIncrementoPrecio());
    }
}
