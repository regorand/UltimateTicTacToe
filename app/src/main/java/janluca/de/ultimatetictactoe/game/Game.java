package janluca.de.ultimatetictactoe.game;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

import janluca.de.ultimatetictactoe.MainActivity;
import janluca.de.ultimatetictactoe.R;


public class Game {

    //0 = all, 1 = topleft, 3 top right etc.
    private int activeOuterField;

    private FieldState winner;

    private boolean gameOver;

    MainActivity activity;

    private int turnCounter;

    private InnerField[] field;

    public Game(MainActivity activity){
        this.activity = activity;
        field = new InnerField[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = new InnerField();
        }
        winner = FieldState.EMPTY;
        gameOver = false;
        activeOuterField = 0;
        turnCounter = 0;

    }

    public boolean handleClickOnView(Button view){
        //felt dirty wrting this - sorry
        int outerTag = Integer.valueOf((String) ((FrameLayout) view.getParent().getParent().getParent()).getTag()),
            innerTag = Integer.valueOf((String) view.getTag());

        if(fieldIsValidMove(outerTag, innerTag)){
            doMove(outerTag, innerTag, view);
        }else {
            return false;
        }

        //TODO refactor so that view id gets saved before game start, so the visual state can be changed for every view after every move #clarity
        //view.getId()


        return false;
    }

    public void doMove(int outerFieldId, int innerFieldId, Button view){
        final FieldState activePlayer = turnCounter % 2 == 0 ? FieldState.PLAYER_X : FieldState.PLAYER_O;
        field[outerFieldId].doMove(innerFieldId, activePlayer);
        //TODO once drawables are in place FieldState doesnt need a String anymore
        view.setText(activePlayer.toString());
        if(activePlayer == FieldState.PLAYER_X){
            //TODO create drawable for X and O and then set them
            //view.setBackgroundResource(R.drawable.tic_tac_toe_x);
        } else {
            view.setBackgroundResource(R.drawable.tic_tac_toe_o);
        }
        turnCounter++;
        checkGameOver();
    }

    public boolean fieldIsValidMove(int outerFieldId, int innerFieldId){
        final boolean outerFieldValid = outerFieldIsValid(outerFieldId);

        final boolean innerFieldValid = field[outerFieldId].isValidMove(innerFieldId);

        return outerFieldValid && innerFieldValid;
    }

    public boolean outerFieldIsValid(int outerFieldId){
        final boolean outerFieldValidMove = activeOuterField == 0 || activeOuterField == outerFieldId;

        final boolean innerFieldHasWinner = field[outerFieldId].hasWinner();

        return outerFieldValidMove && !innerFieldHasWinner;
    }

    private void checkGameOver(){
        //TODO winner logic goes here
    }
}
