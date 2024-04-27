package org.iesalandalus.programacion.reservashotel;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.IModelo;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;

public class MainApp {


    public static void main(String[] args) {
        System.out.println("Programa para la Gestión de Hoteles IES Al-Ándalus");

        try {
            IModelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo, vista);
            controlador.comenzar();

        } catch (IllegalArgumentException|NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private static FactoriaFuenteDatos procesarArgumentosFuenteDatos(String[] args) {
        FactoriaFuenteDatos fuenteDatos = FactoriaFuenteDatos.MONGODB;
        for (String argumento : args) {
            if (argumento.equalsIgnoreCase("-fdmemoria")) {
                fuenteDatos = FactoriaFuenteDatos.MEMORIA;
            } else if (argumento.equalsIgnoreCase("-fdmongodb")) {
                fuenteDatos = FactoriaFuenteDatos.MONGODB;
            }
        }
        return fuenteDatos;
    }
}
