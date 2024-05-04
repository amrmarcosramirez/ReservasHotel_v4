package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.time.Period.*;

public class Reserva {

    // Se crean los atributos con su visibilidad adecuada
    public static final int MAX_NUMERO_MESES_RESERVA = 6;
    public static final int MAX_HORAS_POSTERIOR_CHECKOUT = 12;
    public static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA = "dd/MM/yyyy hh:mm";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    //Constructores
    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen,
                   LocalDate fechaInicioReserva, LocalDate fechaFinReserva,
                   int numeroPersonas){
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        //this.checkIn = null;
        //this.checkOut = null;
        setPrecio();
    }

    public Reserva(Reserva reserva){
        Objects.requireNonNull(reserva,
                "ERROR: No es posible copiar una reserva nula.");
        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setNumeroPersonas(reserva.getNumeroPersonas());
        this.checkIn = reserva.getCheckIn();
        this.checkOut = reserva.getCheckOut();
        //setCheckIn(reserva.getCheckIn());
        //setCheckOut(reserva.getCheckOut());
        //this.checkIn = null;
        //this.checkOut = null;
        setPrecio();
    }

    //Métodos de acceso y modificación
    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        Objects.requireNonNull(huesped,
                "ERROR: El huésped de una reserva no puede ser nulo.");
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        /*
        if(habitacion instanceof Simple){
            return new Simple((Simple) habitacion);
        }else if(habitacion instanceof Doble){
            return new Doble((Doble) habitacion);
        }else if(habitacion instanceof Triple){
            return new Triple((Triple) habitacion);
        }else {
            return new Suite((Suite) habitacion);
        }
        */
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        Objects.requireNonNull(habitacion,
                "ERROR: La habitación de una reserva no puede ser nula.");
        if(habitacion instanceof Simple){
            this.habitacion = new Simple((Simple) habitacion);
        }else if(habitacion instanceof Doble){
            this.habitacion = new Doble((Doble) habitacion);
        }else if(habitacion instanceof Triple){
            this.habitacion = new Triple((Triple) habitacion);
        }else {
            this.habitacion = new Suite((Suite) habitacion);
        }
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        Objects.requireNonNull(regimen,
                "ERROR: El régimen de una reserva no puede ser nulo.");
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        Objects.requireNonNull(fechaInicioReserva,
                "ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if (fechaInicioReserva.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
        } else if (fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        } else{
            this.fechaInicioReserva = fechaInicioReserva;
        }
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        Objects.requireNonNull(fechaFinReserva,
                "ERROR: La fecha de fin de una reserva no puede ser nula.");
        if (!fechaFinReserva.isAfter(getFechaInicioReserva())){
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        } else {
            this.fechaFinReserva = fechaFinReserva;
        }
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        //Objects.requireNonNull(checkIn,"ERROR: El checkin de una reserva no puede ser nulo.");
        if (checkIn.toLocalDate().isBefore(getFechaInicioReserva())){
            throw new IllegalArgumentException(
                    "ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        } else{
            this.checkIn = checkIn;
        }
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        //Objects.requireNonNull(checkOut,"ERROR: El checkout de una reserva no puede ser nulo.");
        if (checkOut.isBefore(getCheckIn())){
            throw new IllegalArgumentException(
                    "ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        } else if (checkOut.isAfter(getFechaFinReserva().atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))){
            throw new IllegalArgumentException(
                    "ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        } else{
            this.checkOut = checkOut;
        }
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas <= 0){
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        } else if (numeroPersonas > habitacion.getNumeroMaximoPersonas()) {
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establacidas para el tipo de habitación reservada.");
        } else{
            this.numeroPersonas = numeroPersonas;
        }
    }

    public double getPrecio() {
        return precio;
    }

    private void setPrecio() {
        Period period = between(getFechaInicioReserva(), getFechaFinReserva());
        this.precio = (habitacion.getPrecio() + regimen.getIncrementoPrecio()) * period.getDays();
    }

    //Métodos equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva reserva2)) return false;
        return habitacion.equals(reserva2.habitacion) &&
                fechaInicioReserva.isEqual(reserva2.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {
        return String.format("Huesped: %s %s Habitación:%s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s " +
                        "Checkout: %s Precio: %.2f Personas: %d", this.huesped.getNombre(), this.huesped.getDni(),
                this.getHabitacion(),
                this.fechaInicioReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                this.fechaFinReserva.format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                (!(this.checkIn==null))? getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA)):
                        "No registrado",
                (!(this.checkOut==null))? getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA)):
                        "No registrado",
                this.precio, 1);
    }
}
