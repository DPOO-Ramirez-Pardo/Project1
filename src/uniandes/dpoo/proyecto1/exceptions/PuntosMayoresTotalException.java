package uniandes.dpoo.proyecto1.exceptions;

import uniandes.dpoo.proyecto1.modelo.Recibo;

public class PuntosMayoresTotalException extends Exception {
    public PuntosMayoresTotalException(Recibo recibo){
        super("¡Los puntos necesarios para pagar este recibo son "+recibo.maximoPuntosRedimidos()+"!");
    }
}
