package janluca.de.ultimatetictactoe.game;


public enum FieldState {
    EMPTY(""),
    PLAYER_X("Player X"),
    PLAYER_O("Player O");

    private String value;

    FieldState(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
