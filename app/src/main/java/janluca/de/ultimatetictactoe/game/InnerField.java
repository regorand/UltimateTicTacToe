package janluca.de.ultimatetictactoe.game;


public class InnerField {

    FieldState winner;

    private boolean hasWinner;

    private FieldState[] field;

    public InnerField(){
        field = new FieldState[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = FieldState.EMPTY;
        }
        winner = FieldState.EMPTY;
        hasWinner = false;
    }

    public boolean isValidMove(int fieldId){
        return field[fieldId] == FieldState.EMPTY;
    }

    public void doMove(int fieldId, FieldState newState){
        field[fieldId] = newState;
        checkGameOver();
    }

    private void checkGameOver(){

        //TODO winner logic here
        //write winner in this.winner
    }

    public boolean hasWinner(){
        return hasWinner;
    }

    public FieldState getWinner(){
        return winner;
    }

}
