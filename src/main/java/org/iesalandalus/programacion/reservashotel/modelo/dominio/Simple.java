package org.iesalandalus.programacion.reservashotel.modelo.dominio;


public class Simple extends Habitacion{

    // Atributos
    private static final int NUM_MAXIMO_PERSONAS=1;

    //Constructores
    public Simple(int planta, int puerta, double precio){
        super(planta, puerta, precio);
    }

    public Simple(Simple habitacionSimple){
        super(habitacionSimple);
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +
                String.format("habitación simple, capacidad = %d persona",
                this.getNumeroMaximoPersonas());
    }
}
