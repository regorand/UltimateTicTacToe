package janluca.de.ultimatetictactoe.game;


public class InnerFieldState {

    private FieldState[] field;


    public InnerFieldState(FieldState[] field){
        this.field = field;
    }

    public FieldState getStateById(int id){
        return field[id];
    }

    public FieldState[] getStates(){
        return field;
    }

}
