package main.java.org.taller.validadores;

public class Validador implements IValidador {


    @Override
    public boolean validarString(String input) {
        return !input.isEmpty();
    }

    @Override
    public boolean validarInt(String input) {
        if (input.isEmpty()) return false;
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
