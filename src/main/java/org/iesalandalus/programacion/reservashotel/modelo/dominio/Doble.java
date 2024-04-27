package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Doble extends Habitacion{

    // Atributos
    private static final int NUM_MAXIMO_PERSONAS=2;
    static final int MIN_NUM_CAMAS_INDIVIDUALES=0;
    static final int MAX_NUM_CAMAS_INDIVIDUALES=2;
    static final int MIN_NUM_CAMAS_DOBLES=0;
    static final int MAX_NUM_CAMAS_DOBLES=1;
    private int numCamasIndividuales;
    private int numCamasDobles;

    //Constructores
    public Doble(int planta, int puerta, double precio, int numCamasIndividuales,
                 int numCamasDobles){
        super(planta, puerta, precio);
        setNumCamasIndividuales(numCamasIndividuales);
        setNumCamasDobles(numCamasDobles);
        validaNumCamas();
    }

    public Doble(Doble habitacionDoble){
        super(habitacionDoble);
        setNumCamasIndividuales(habitacionDoble.getNumCamasIndividuales());
        setNumCamasDobles(habitacionDoble.getNumCamasDobles());
        validaNumCamas();
    }

    public int getNumCamasIndividuales() {
        return numCamasIndividuales;
    }

    public void setNumCamasIndividuales(int numCamasIndividuales) {
        this.numCamasIndividuales = numCamasIndividuales;
    }

    public int getNumCamasDobles() {
        return numCamasDobles;
    }

    public void setNumCamasDobles(int numCamasDobles) {
        this.numCamasDobles = numCamasDobles;
    }

    private void validaNumCamas(){
        if (getNumCamasIndividuales() < MIN_NUM_CAMAS_INDIVIDUALES ||
            getNumCamasIndividuales() > MAX_NUM_CAMAS_INDIVIDUALES) {
            throw new IllegalArgumentException("ERROR: El número de camas individuales " +
                    "de una habitación doble no puede ser " +
                    "inferior a " + MIN_NUM_CAMAS_INDIVIDUALES + " ni mayor que " +
                    MAX_NUM_CAMAS_INDIVIDUALES);
        } else if (getNumCamasDobles() < MIN_NUM_CAMAS_DOBLES ||
                getNumCamasDobles() > MAX_NUM_CAMAS_DOBLES) {
            throw new IllegalArgumentException("ERROR: El número de camas dobles " +
                    "de una habitación doble no puede ser " +
                    "inferior a " + MIN_NUM_CAMAS_DOBLES + " ni mayor que " +
                    MAX_NUM_CAMAS_DOBLES);
        } else if( !((getNumCamasIndividuales() == MIN_NUM_CAMAS_INDIVIDUALES &&
                getNumCamasDobles() == MAX_NUM_CAMAS_DOBLES) ||
                (getNumCamasIndividuales() == MAX_NUM_CAMAS_INDIVIDUALES &&
                        getNumCamasDobles() == MIN_NUM_CAMAS_DOBLES))){
            throw new IllegalArgumentException("ERROR: La distribución de camas " +
                    "en una habitación doble tiene que ser " + MAX_NUM_CAMAS_INDIVIDUALES +
                    " camas individuales y " + MIN_NUM_CAMAS_DOBLES + " doble o " +
                    MIN_NUM_CAMAS_INDIVIDUALES + " camas individuales y " +
                    MAX_NUM_CAMAS_DOBLES + " doble");
        }
    }

    @Override
    public int getNumeroMaximoPersonas() {
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString() + ", " +
                String.format("habitación doble, capacidad = %d personas," +
                        " camas individuales = %d, camas dobles = %d",
                        this.getNumeroMaximoPersonas(), this.getNumCamasIndividuales(),
                        this.getNumCamasDobles());
    }
}
