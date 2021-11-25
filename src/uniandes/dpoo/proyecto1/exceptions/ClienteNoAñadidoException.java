package uniandes.dpoo.proyecto1.exceptions;

public class ClienteNoAñadidoException extends Exception {
    public ClienteNoAñadidoException(){
        super("Ningún cliente ha sido añadido como titular al recibo.");
    }
}
