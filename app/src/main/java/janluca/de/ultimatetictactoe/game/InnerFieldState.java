package janluca.de.ultimatetictactoe.game;


public class InnerFieldState {

    private FieldState[] field;

    private boolean isOver;

    public InnerFieldState(FieldState[] field, boolean hasWinner){
        this.field = field;
        this.isOver = hasWinner;
    }

    public FieldState getStateById(int id){
        return field[id];
    }

    public FieldState[] getStates(){
        return field;
    }

    public boolean isOver(){
        return isOver;
    }
}
