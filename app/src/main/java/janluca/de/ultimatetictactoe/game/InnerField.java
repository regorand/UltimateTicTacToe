package janluca.de.ultimatetictactoe.game;


import java.util.Arrays;

public class InnerField {

    private Game game;

    int fieldId;

    private int amountMoves;

    PlayerType winner;

    private boolean hasWinner;

    private PlayerType[] field;

    public InnerField(Game game, int fieldId){
        this.game = game;
        this.fieldId = fieldId;
        field = new PlayerType[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = PlayerType.EMPTY;
        }
        winner = PlayerType.EMPTY;
        hasWinner = false;
        amountMoves = 0;
    }

    public InnerField createCopyForPrediction(Game newGame){
        InnerField copy = new InnerField(newGame, fieldId);
        copy.setRequiredCopyValues(
                fieldId, amountMoves, winner, hasWinner, field
        );
        return copy;
    }

    private void setRequiredCopyValues(int fieldId, int amountMoves, PlayerType winner, boolean hasWinner, PlayerType[] field){
        this.fieldId = fieldId;
        this.amountMoves = amountMoves;
        this.winner = winner;
        this.hasWinner = hasWinner;
        this.field = Arrays.copyOf(field, field.length);
    }

    public PlayerType[] getFieldStatus(){
        return field;
    }

    public PlayerType getFieldStatusById(int id){
        return field[id];
    }

    public boolean isValidMove(int fieldId){
        return field[fieldId] == PlayerType.EMPTY;
    }

    public void doMove(int fieldId, PlayerType newState){
        field[fieldId] = newState;
        amountMoves++;
        checkGameOver();
    }

    private void checkGameOver(){
        //checks vertical winning
        for(int i = 0; i < 3; i++){
            if(field[i] == field[i+3] && field[i] == field[i+6] && field[i] != PlayerType.EMPTY){
                setWinner(field[i]);
                return;
            }
        }

        //checks horizontal winning
        for(int i = 0; i <= 6; i+=3){
            if(field[i] == field[i+1] && field[i] == field[i+2] && field[i] != PlayerType.EMPTY){
                setWinner(field[i]);
                return;
            }
        }

        //diagonal winning check top-left to bottom-right
        if(field[0] == field[4] && field[4] == field[8] && field[4] != PlayerType.EMPTY){
            setWinner(field[4]);
            return;
        }

        //diagonal winning check top-right to bottom-Left
        if(field[2] == field[4] && field[4] == field[6] && field[4] != PlayerType.EMPTY){
            setWinner(field[4]);
        }
    }

    private void setWinner(PlayerType winner){
        this.winner = winner;
        hasWinner = true;
        game.updateInnerFieldWon(fieldId, winner);
    }

    public boolean isOver(){
        return hasWinner || amountMoves >= 9;
    }

    public PlayerType getWinner(){
        return winner;
    }

}
