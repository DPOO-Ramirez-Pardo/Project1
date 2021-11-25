package uniandes.dpoo.proyecto1.exceptions;

public class FechasCantidadesInconsistentesException extends Exception {
    public FechasCantidadesInconsistentesException(int fechasSize, int cantidadesSize) {
        super(String.format("La cantidad de fechas introducida fue %d y la cantidad de cantidades introducidas fue %d",
                fechasSize, cantidadesSize));
    }
}
