package uniandes.dpoo.proyecto1.exceptions;

public class ClienteNoExisteException extends Exception {
    public ClienteNoExisteException(int cedula){
        super("No existe un cliente con cédula "+cedula);
    }
}
