package janluca.de.ultimatetictactoe.game;

import java.lang.reflect.Field;

/**
 * Created by root on 29.11.16.
 */

public enum FieldState {
    EMPTY(""),
    PLAYER_X("X"),
    PLAYER_O("O");

    private final String value;

    private FieldState(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
