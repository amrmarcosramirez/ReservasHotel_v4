package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion.*;


public class Consola {

    //Constructor
    private Consola(){}

    //Métodos
    public static void mostrarMenu(){
        String mensaje = "Menú de opciones:";
        System.out.printf("%n%s%n", mensaje);
        String cadena = "%0" + mensaje.length() + "d";
        System.out.println(String.format(cadena, 0).replace("0", "-"));
        for (Opcion opcion: Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        int ordinalOpcion;
        do {
            String mensaje = "Elige una opción:";
            System.out.printf("%n%s%n", mensaje);
            String cadena = "%0" + mensaje.length() + "d";
            System.out.println(String.format(cadena, 0).replace("0", "-"));

            ordinalOpcion = Entrada.entero();
        } while (!(ordinalOpcion >= 0 && ordinalOpcion <= (Opcion.values().length - 1)));
        return Opcion.values()[ordinalOpcion];
    }

    public static Huesped leerHuesped() {
        Huesped huesped;
        String nombre;
        String telefono;
        String correo;
        String dni;

        do {
            System.out.print("Introduce el nombre del huésped: ");
            nombre = Entrada.cadena();
        } while (nombre.trim().isEmpty());

        do {
            System.out.print("Introduce el DNI del huésped: ");
            dni = Entrada.cadena();
        } while (dni.trim().isEmpty());

        do {
            System.out.print("Introduce el correo del huésped: ");
            correo = Entrada.cadena();
        } while (correo.trim().isEmpty());

        do {
            System.out.print("Introduce el teléfono del huésped: ");
            telefono = Entrada.cadena();
        } while (telefono.trim().isEmpty());

        String mensaje = "Introduce la fecha de nacimiento del huésped (%s): ";
        LocalDate fechaNacimiento = leerFecha(mensaje);

        huesped = new Huesped(nombre, dni, correo, telefono, fechaNacimiento);

        return new Huesped(huesped);
    }

    public static Huesped leerClientePorDni() {
        Huesped huesped;
        String nombre = "Huésped Ficticio";
        String telefono = "666666666";
        String correo = "HuspedFicticio@gmail.com";
        String dni;
        LocalDate fechaNacimiento = LocalDate.now();

        do {
            System.out.print("Introduce el DNI del huésped: ");
            dni = Entrada.cadena();
        } while (dni.isEmpty());

        huesped = new Huesped(nombre, dni, correo, telefono, fechaNacimiento);

        return new Huesped(huesped);
    }

    public static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;
        boolean fechaValida = false;
        do {
            try {
                System.out.printf(mensaje, Huesped.FORMATO_FECHA);
                fecha = LocalDate.parse(Entrada.cadena(), DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA));
                fechaValida = true;
            } catch (DateTimeParseException e) {
                fechaValida = false;
            }
        } while(!fechaValida);
        return fecha;
    }

    public static LocalDateTime leerFechaHora(String mensaje) {
        LocalDateTime fecha = null;
        boolean fechaValida = false;

        do {
            try {
                System.out.printf(mensaje, Reserva.FORMATO_FECHA_HORA_RESERVA);
                LocalDate fecha1 = leerFecha("\nIntroduce la fecha: ");
                System.out.print("Introduce la hora: ");
                LocalTime time1 = LocalTime.parse(Entrada.cadena());
                /*fecha = LocalDateTime.from(LocalDate.parse(Entrada.cadena(),
                        DateTimeFormatter.ofPattern(Reserva.FORMATO_FECHA_HORA_RESERVA)));*/
                fecha = LocalDateTime.of(fecha1, time1);
                fechaValida = true;
            } catch (DateTimeParseException e) {
                fechaValida = false;
            }
        } while(!fechaValida);
        return fecha;
    }

    public static Habitacion leerHabitacion() {
        //Habitacion habitacion;
        int planta;
        int puerta;
        double precio;
        int camasIndividuales;
        int camasDobles;
        int banios;
        char indJacuzzi;
        boolean tieneJacuzzi;

        do {
            System.out.print("Introduce la planta de la habitación: ");
            planta = Entrada.entero();
        } while (planta < Habitacion.MIN_NUMERO_PLANTA ||
                planta > Habitacion.MAX_NUMERO_PLANTA);

        do {
            System.out.print("Introduce la puerta de la habitación: ");
            puerta = Entrada.entero();
        } while (puerta < Habitacion.MIN_NUMERO_PUERTA ||
                puerta > Habitacion.MAX_NUMERO_PUERTA);

        do {
            System.out.print("Introduce el precio de la habitación: ");
            precio = Entrada.realDoble();
        } while (precio < Habitacion.MIN_PRECIO_HABITACION ||
                precio > Habitacion.MAX_PRECIO_HABITACION);

        TipoHabitacion tipoHabitacion = leerTipoHabitacion();

        if (tipoHabitacion.equals(SIMPLE)) {
            Simple habitacion = new Simple(planta, puerta, precio);

            return new Simple(habitacion);
        } else if (tipoHabitacion.equals(DOBLE)) {

            System.out.println("Introduce el número de camas (1 - Doble) o " +
                    "(2 - Individuales):");
            System.out.println("Camas individuales (0 o 2):");
            camasIndividuales = Entrada.entero();
            System.out.println("Camas dobles (0 o 1):");
            camasDobles = Entrada.entero();

            Doble habitacion = new Doble(planta, puerta, precio,
                    camasIndividuales, camasDobles);

            return new Doble(habitacion);
        } else if (tipoHabitacion.equals(TRIPLE)) {

            System.out.println("Introduce el número de camas " +
                    "(2 - Individuales y 1 - Doble) o " +
                    "(3 - Individuales):");
            System.out.println("Camas individuales (2 o 3):");
            camasIndividuales = Entrada.entero();
            System.out.println("Camas dobles (0 o 1):");
            camasDobles = Entrada.entero();

            System.out.println("Introduce el número de baños (2 o 3):");
            banios = Entrada.entero();

            Triple habitacion = new Triple(planta, puerta, precio, banios,
                    camasIndividuales, camasDobles);

            return new Triple(habitacion);
        } else {

            System.out.println("Introduce el número de baños (1 o 3):");
            banios = Entrada.entero();

            do {
                System.out.println("Desea baño con jacuzzi (S/N):");
                indJacuzzi = Entrada.caracter();
            } while (indJacuzzi != 'S' && indJacuzzi != 'N');

            tieneJacuzzi = indJacuzzi == 'S';
            Suite habitacion = new Suite(planta, puerta, precio, banios, tieneJacuzzi);

            return new Suite(habitacion);
        }
    }

    public static Habitacion leerHabitacionPorIdentificador() {
        Simple habitacion;
        int planta;
        int puerta;
        double precio = 70.0;
        //TipoHabitacion tipoHabitacion = SIMPLE;

        do {
            System.out.print("Introduce la planta de la habitación: ");
            planta = Entrada.entero();
        } while (planta < Habitacion.MIN_NUMERO_PLANTA ||
                planta > Habitacion.MAX_NUMERO_PLANTA);

        do {
            System.out.print("Introduce la puerta de la habitación: ");
            puerta = Entrada.entero();
        }  while (puerta < Habitacion.MIN_NUMERO_PUERTA ||
                puerta > Habitacion.MAX_NUMERO_PUERTA);

        habitacion = new Simple(planta, puerta, precio);

        return new Simple(habitacion);
    }

    public static TipoHabitacion leerTipoHabitacion(){
        int ordinalHabitacion;
        for (TipoHabitacion habitacion: TipoHabitacion.values()) {
            System.out.println(habitacion.ordinal() + ".- " + habitacion);
        }
        do {
            System.out.println("\nElige una opción: ");
            ordinalHabitacion = Entrada.entero();
        } while (!(ordinalHabitacion >= 0 && ordinalHabitacion <= (TipoHabitacion.values().length - 1)));
        return TipoHabitacion.values()[ordinalHabitacion];
    }

    public static Regimen leerRegimen(){
        int ordinalRegimen;
        for (Regimen regimen: Regimen.values()) {
            System.out.println(regimen.ordinal() + ".- " + regimen);
        }
        do {
            System.out.println("\nElige un régimen: ");
            ordinalRegimen = Entrada.entero();
        } while (!(ordinalRegimen >= 0 && ordinalRegimen <= (Regimen.values().length - 1)));
        return Regimen.values()[ordinalRegimen];
    }

    /*
    public static Reserva leerReserva() {
        Reserva reserva;
        Huesped huesped;
        Habitacion habitacion;
        Regimen regimen;
        int numeroPersonas;

        huesped = leerHuesped();
        habitacion = leerHabitacion();
        regimen = leerRegimen();

        String mensaje = "Introduce la fecha de inicio de la reserva (%s): ";
        LocalDate fechaInicioReserva = leerFecha(mensaje);

        String mensaje2 = "Introduce la fecha de fin de la reserva (%s): ";
        LocalDate fechaFinReserva = leerFecha(mensaje2);

        System.out.print("Introduce el número de personas: ");
        numeroPersonas = Entrada.entero();

        reserva = new Reserva(huesped, habitacion, regimen, fechaInicioReserva,
                fechaFinReserva, numeroPersonas);

        return new Reserva(reserva);
    }
    */
}
