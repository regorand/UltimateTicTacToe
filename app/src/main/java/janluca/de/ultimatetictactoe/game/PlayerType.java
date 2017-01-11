package janluca.de.ultimatetictactoe.game;


public enum PlayerType {
    EMPTY(""),
    PLAYER_X("Player X"),
    PLAYER_O("Player O");

    private String value;

    PlayerType(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
