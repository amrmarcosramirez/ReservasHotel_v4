package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {
    SALIR("Salir") {
        @Override
        public void ejecutar() {vista.terminar();}
    },
    INSERTAR_HUESPED("Insertar hu�sped") {
        @Override
        public void ejecutar() {vista.insertarHuesped();}
    },
    BUSCAR_HUESPED("Buscar hu�sped") {
        @Override
        public void ejecutar() {vista.buscarHuesped();}
    },
    BORRAR_HUESPED("Borrar hu�sped") {
        @Override
        public void ejecutar() {vista.borrarHuesped();}
    },
    MOSTRAR_HUESPEDES("Mostrar hu�spedes") {
        @Override
        public void ejecutar() {vista.mostrarHuespedes();}
    },
    INSERTAR_HABITACION("Insertar habitaci�n") {
        @Override
        public void ejecutar() {vista.insertarHabitacion();}
    },
    BUSCAR_HABITACION("Buscar habitaci�n") {
        @Override
        public void ejecutar() {vista.buscarHabitacion();}
    },
    BORRAR_HABITACION("Borrar habitaci�n") {
        @Override
        public void ejecutar() {vista.borrarHabitacion();}
    },
    MOSTRAR_HABITACIONES("Mostrar habitaciones") {
        @Override
        public void ejecutar() {vista.mostrarHabitaciones();}
    },
    INSERTAR_RESERVA("Insertar reserva") {
        @Override
        public void ejecutar() {vista.insertarReserva();}
    },
    ANULAR_RESERVA("Anular reserva") {
        @Override
        public void ejecutar() {vista.anularReserva();}
    },
    MOSTRAR_RESERVAS("Mostrar reservas") {
        @Override
        public void ejecutar() {vista.mostrarReservas();}
    },
    LISTAR_RESERVAS_HUESPED("Listar reservas hu�sped") {
        @Override
        public void ejecutar() {vista.mostrarReservasHuesped();}
    },
    LISTAR_RESERVAS_TIPO_HABITACION("Listar reservas tipo habitaci�n") {
        @Override
        public void ejecutar() {vista.mostrarReservasTipoHabitacion();}
    },
    CONSULTAR_DISPONIBILIDAD("Consultar disponibilidad") {
        @Override
        public void ejecutar() {vista.comprobarDisponibilidad();}
    },
    REALIZAR_CHECKIN("Realizar checkin") {
        @Override
        public void ejecutar() {vista.realizarCheckin();}
    },
    REALIZAR_CHECKOUT("Realizar checkout") {
        @Override
        public void ejecutar() {vista.realizarCheckOut();}
    };

    // Se crean los atributos con su visibilidad adecuada
    private String mensajeAMostrar;
    private static Vista vista;

    //Constructor
    Opcion(String mensajeAMostrar) {
        this.mensajeAMostrar = mensajeAMostrar;
    }

    public abstract void ejecutar();

    protected static void setVista(Vista vista) {
        Opcion.vista = vista;
    }

    //M�todo toString
    @Override
    public String toString() {
        return String.format("%d.- %s", ordinal(), this.mensajeAMostrar);
    }
}
