package janluca.de.ultimatetictactoe.game;


public class InnerFieldState {

    private PlayerType[] field;

    private boolean isOver;

    public InnerFieldState(PlayerType[] field, boolean hasWinner){
        this.field = field;
        this.isOver = hasWinner;
    }

    public PlayerType getStateById(int id){
        return field[id];
    }

    public PlayerType[] getStates(){
        return field;
    }

    public boolean isOver(){
        return isOver;
    }
}
