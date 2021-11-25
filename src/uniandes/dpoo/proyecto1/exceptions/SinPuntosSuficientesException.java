package uniandes.dpoo.proyecto1.exceptions;

import uniandes.dpoo.proyecto1.modelo.Cliente;

public class SinPuntosSuficientesException extends Exception {
    public SinPuntosSuficientesException(Cliente cliente) {
        super("¡El cliente tiene únicamente "+cliente.getPuntos()+"!");
    }
}
