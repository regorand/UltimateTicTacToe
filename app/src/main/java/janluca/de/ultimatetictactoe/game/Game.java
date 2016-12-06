package janluca.de.ultimatetictactoe.game;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import janluca.de.ultimatetictactoe.MainActivity;
import janluca.de.ultimatetictactoe.R;


public class Game {

    private View[] outerFieldViews;

    final View rootView;

    private final int[] OUTER_FIELD_VIEW_IDS = {R.id.outerFieldZero, R.id.outerFieldOne, R.id.outerFieldTwo,
                                                R.id.outerFieldThree, R.id.outerFieldFour, R.id.outerFieldFive,
                                                R.id.outerFieldSix, R.id.outerFieldSeven, R.id.outerFieldEight};

    //0 = all, 1 = topleft, 3 top right etc.
    private int activeOuterField;

    private FieldState winner;

    private boolean gameOver;

    private int turnCounter;

    private InnerField[] field;


    //TODO create AI
    //http://www.gamedev.net/page/resources/_/technical/artificial-intelligence/a-practical-guide-to-building-a-complete-game-a-r784
    public Game(View rootView){
        this.rootView = rootView;

        field = new InnerField[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = new InnerField();
        }

        winner = FieldState.EMPTY;
        gameOver = false;
        activeOuterField = -1;
        turnCounter = 0;

        saveOuterFieldViews();
        updateStatusField();
        clearField();
        changeEnabledFieldsVisualisation();
    }

    private void clearField(){
        for (View outerFieldView : outerFieldViews) {
            View innerFieldParent = outerFieldView.findViewById(R.id.innerFieldParent);
            for(int i = 0; i < 9; i++){
                innerFieldParent.findViewWithTag(String.valueOf(i)).setBackground(null);
            }
        }
    }

    private void changeEnabledFieldsVisualisation(){
        //maybe get colors with less transparency, when player clicks on not available field

        int enabled, disabled;

        if(gameOver){
            enabled = rootView.getResources().getColor(R.color.field_white);
            disabled = rootView.getResources().getColor(R.color.field_white);
        }else {
            enabled = rootView.getResources().getColor(R.color.field_enabled);
            disabled = rootView.getResources().getColor(R.color.field_disabled);
        }
        for (int i = 0; i < outerFieldViews.length; i++) {
            if(outerFieldIsValid(i)){
                outerFieldViews[i].setBackgroundColor(enabled);
            }else {
                outerFieldViews[i].setBackgroundColor(disabled);
            }
        }
    }

    private void updateStatusField(){
        TextView statusView = (TextView) rootView.findViewById(R.id.statusText);
        if(gameOver){
            statusView.setText(winner.toString() + " won the game!");
        } else {
            statusView.setText("It's " + getActivePlayer().toString() + "'s turn");
        }
    }

    public boolean handleClickOnView(Button view){
        //felt dirty wrting this - sorry
        int outerTag = Integer.valueOf((String) ((FrameLayout) view.getParent().getParent().getParent()).getTag()),
            innerTag = Integer.valueOf((String) view.getTag());

        System.out.println(outerTag + ": " + innerTag);

        if(fieldIsValidMove(outerTag, innerTag)){
            doMove(outerTag, innerTag, view);
            return true;
        }else {
            return false;
        }
    }

    public boolean isStillActive() {
        return !gameOver;
    }

    private void doMove(int outerFieldId, int innerFieldId, Button view){

        final FieldState activePlayer = getActivePlayer();
        field[outerFieldId].doMove(innerFieldId, activePlayer);
        if(activePlayer == FieldState.PLAYER_X){
            view.setBackgroundResource(R.drawable.tic_tac_toe_x);
        } else {
            view.setBackgroundResource(R.drawable.tic_tac_toe_o);
        }
        turnCounter++;
        activeOuterField = field[innerFieldId].hasWinner() ? -1 : innerFieldId;
        checkGameOver();

        updateStatusField();
        changeEnabledFieldsVisualisation();

    }

    private FieldState getActivePlayer(){
        return turnCounter % 2 == 0 ? FieldState.PLAYER_X : FieldState.PLAYER_O;
    }

    private boolean fieldIsValidMove(int outerFieldId, int innerFieldId){
        final boolean outerFieldValid = outerFieldIsValid(outerFieldId);

        final boolean innerFieldValid = field[outerFieldId].isValidMove(innerFieldId);

        return outerFieldValid && innerFieldValid;
    }

    private boolean outerFieldIsValid(int outerFieldId){
        final boolean outerFieldValidMove = activeOuterField == -1 || activeOuterField == outerFieldId;

        final boolean innerFieldHasWinner = field[outerFieldId].hasWinner();

        return outerFieldValidMove && !innerFieldHasWinner;
    }

    private void setWinner(FieldState winner){
        this.winner = winner;
        gameOver = true;
        System.out.println("entire game won for " + winner.toString());
    }

    private void checkGameOver(){
        //checks vertical winning
        for(int i = 0; i < 3; i++){
            if(field[i].getWinner() == field[i+3].getWinner() && field[i].getWinner() == field[i+6].getWinner() && field[i].getWinner() != FieldState.EMPTY){
                setWinner(field[i].getWinner());
                return;
            }
        }

        //checks horizontal winning
        for(int i = 0; i <= 6; i+=3){
            if(field[i].getWinner() == field[i+1].getWinner() && field[i].getWinner() == field[i+2].getWinner() && field[i].getWinner() != FieldState.EMPTY){
                setWinner(field[i].getWinner());
                return;
            }
        }

        //diagonal winning check top-left to bottom-right
        if(field[0].getWinner() == field[4].getWinner() && field[4].getWinner() == field[8].getWinner() && field[4].getWinner() != FieldState.EMPTY){
            setWinner(field[4].getWinner());
            return;
        }

        //diagonal winning check top-right to bottom-Left
        if(field[2].getWinner() == field[4].getWinner() && field[4].getWinner() == field[6].getWinner() && field[4].getWinner() != FieldState.EMPTY){
            setWinner(field[4].getWinner());
        }
    }

    private void saveOuterFieldViews(){
        outerFieldViews = new View[OUTER_FIELD_VIEW_IDS.length];
        for(int i = 0; i < OUTER_FIELD_VIEW_IDS.length; i++){
            outerFieldViews[i] = rootView.findViewById(OUTER_FIELD_VIEW_IDS[i]);
        }
    }
}
