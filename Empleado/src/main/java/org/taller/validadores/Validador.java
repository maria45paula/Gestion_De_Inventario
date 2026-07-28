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
            int num = Integer.parseInt(input);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
