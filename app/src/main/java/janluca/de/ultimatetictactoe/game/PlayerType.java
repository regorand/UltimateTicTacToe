package janluca.de.ultimatetictactoe.game;


public enum PlayerType {
    EMPTY(""),
    PLAYER_X("Player X"),
    PLAYER_O("Player O");

    private String value;

    PlayerType(String value){
        this.value = value;
    }

    public  PlayerType getEnemy(){
        if(this == PLAYER_O){
            return PLAYER_X;
        } else if(this == PLAYER_X){
            return PLAYER_O;
        }
        return EMPTY;
    }

    @Override
    public String toString() {
        return value;
    }
}
