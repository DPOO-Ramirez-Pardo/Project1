package uniandes.dpoo.proyecto1.exceptions;

public class SinReciboActualException extends Exception {
    public SinReciboActualException(){
        super("¡No hay un recibo actual en proceso!");
    }
}
