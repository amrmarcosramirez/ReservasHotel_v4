package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {
    SUITE("Suite"),
    SIMPLE("Simple"),
    DOBLE("Doble"),
    TRIPLE("Triple");

    // Se crean los atributos con su visibilidad adecuada
    private String cadenaAMostrar;

    //Constructor
    TipoHabitacion(String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    //Método toString
    @Override
    public String toString() {
        return String.format("Tipo Habitacion: %s",
                this.cadenaAMostrar);
    }
}
