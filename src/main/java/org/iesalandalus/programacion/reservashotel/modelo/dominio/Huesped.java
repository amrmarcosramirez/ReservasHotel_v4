package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Huesped {

    // Se crean los atributos con su visibilidad adecuada
    private static final String ER_TELEFONO = "[9867]\\d{8}";
    private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    //Constructores
    public Huesped(String nombre, String dni, String correo, String telefono,
                   LocalDate fechaNacimiento){
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    public Huesped(Huesped huesped){
        Objects.requireNonNull(huesped,
                "ERROR: No es posible copiar un huésped nulo.");
        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());
    }

    // Método formteaNombre que se utiliza para dar formato al nombre del huesped
    private String formateaNombre(String nombre){
        nombre = nombre.toLowerCase();
        String[] nombre2 = nombre.replaceAll("[^a-z0-9áéíóú\\s]", "").split("\\s");
        StringBuilder resultado = new StringBuilder();
        for (String s : nombre2) {
            if (!s.isEmpty()) {
                resultado.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
                resultado.append(" "); //espacio entre cada palabra
            }
        }
        resultado = new StringBuilder(resultado.toString().trim());
        return resultado.toString();
    }

    // Método que se utiliza para comprobar el DNI del huesped
    private Boolean comprobarLetraDni(String dni){
        Pattern patron;
        Matcher comparador;
        char[] LETRAS_DNI = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D',
                             'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L',
                             'C', 'K', 'E'};
        patron = Pattern.compile(ER_DNI);
        comparador = patron.matcher(dni);
        if (!comparador.matches()){
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        }else{
            int numero = Integer.parseInt(comparador.group(1));
            char letra = LETRAS_DNI[numero % 23];
            return (comparador.group(2).charAt(0) == letra);
        }
    }

    //Métodos de acceso y modificación
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){
        Objects.requireNonNull(nombre,
                "ERROR: El nombre de un huésped no puede ser nulo.");
        nombre = nombre.trim();
        if(nombre.isEmpty()) {
            throw new IllegalArgumentException(
                    "ERROR: El nombre de un huésped no puede estar vacío.");
        }else{
            this.nombre = formateaNombre(nombre);
        }
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni, "ERROR: El dni de un huésped no puede ser nulo.");
        dni = dni.trim();
        if(!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
        }else{
            this.dni = dni;
        }
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        Objects.requireNonNull(correo, "ERROR: El correo de un huésped no puede ser nulo.");
        correo = correo.trim();
       if(!correo.matches(ER_CORREO)){
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        }else {
            this.correo = correo;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono,
                "ERROR: El teléfono de un huésped no puede ser nulo.");
        telefono = telefono.trim();
        if(!telefono.matches(ER_TELEFONO)){
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        }else{
            this.telefono = telefono;
        }
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) {
        Objects.requireNonNull(fechaNacimiento,
                "ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        this.fechaNacimiento = fechaNacimiento;
    }

    private String getIniciales(){
        String[] nombre2 = getNombre().split("\\s");
        StringBuilder iniciales = new StringBuilder();
        for (String s : nombre2) {
                iniciales.append(s.charAt(0));
        }
        iniciales = new StringBuilder(iniciales.toString().trim());
        return iniciales.toString();
    }

    //Métodos equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Huesped huesped2)) return false;
        return dni.equals(huesped2.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",
                this.nombre, getIniciales(), this.dni, this.correo, this.telefono,
                this.fechaNacimiento.format(DateTimeFormatter.ofPattern(FORMATO_FECHA)));
    }

}
