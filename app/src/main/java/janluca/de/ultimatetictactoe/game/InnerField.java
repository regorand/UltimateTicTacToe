package janluca.de.ultimatetictactoe.game;


public class InnerField {

    private Game game;

    int fieldId;

    private int amountMoves;

    FieldState winner;

    private boolean hasWinner;

    private FieldState[] field;

    public InnerField(Game game, int fieldId){
        this.game = game;
        this.fieldId = fieldId;
        field = new FieldState[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = FieldState.EMPTY;
        }
        winner = FieldState.EMPTY;
        hasWinner = false;
        amountMoves = 0;
    }

    public boolean isValidMove(int fieldId){
        return field[fieldId] == FieldState.EMPTY;
    }

    public void doMove(int fieldId, FieldState newState){
        field[fieldId] = newState;
        amountMoves++;
        checkGameOver();
    }

    private void checkGameOver(){
        //checks vertical winning
        for(int i = 0; i < 3; i++){
            if(field[i] == field[i+3] && field[i] == field[i+6] && field[i] != FieldState.EMPTY){
                setWinner(field[i]);
                return;
            }
        }

        //checks horizontal winning
        for(int i = 0; i <= 6; i+=3){
            if(field[i] == field[i+1] && field[i] == field[i+2] && field[i] != FieldState.EMPTY){
                setWinner(field[i]);
                return;
            }
        }

        //diagonal winning check top-left to bottom-right
        if(field[0] == field[4] && field[4] == field[8] && field[4] != FieldState.EMPTY){
            setWinner(field[4]);
            return;
        }

        //diagonal winning check top-right to bottom-Left
        if(field[2] == field[4] && field[4] == field[6] && field[4] != FieldState.EMPTY){
            setWinner(field[4]);
        }
    }

    private void setWinner(FieldState winner){
        this.winner = winner;
        hasWinner = true;
        game.updateInnerFieldWon(fieldId, winner);
        System.out.println("field won for " + winner.toString());
    }

    public boolean isOver(){
        return hasWinner || amountMoves >= 9;
    }

    public FieldState getWinner(){
        return winner;
    }

}
