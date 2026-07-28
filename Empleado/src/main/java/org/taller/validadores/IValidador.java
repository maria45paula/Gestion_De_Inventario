package main.java.org.taller.validadores;

public interface IValidador {
    /**
     * Valida un String, verificando que no se encuentre vacío
     *
     * @param input String que va a ser verificado
     *
     * @return booleano que define si se acepta o no el String
     */
    boolean validarString(String input);

    /**
     * Valida si un String contiene a un número entero, y si ese número es positivo
     *
     * @param input String que va a ser verificado
     *
     * @return true-> En caso que el String contenga un número entero positiva
     *         false -> En caso que el String no contenga un número entero positivo
     */
    boolean validarInt(String input);
}
