package janluca.de.ultimatetictactoe.game;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import janluca.de.ultimatetictactoe.R;


public class Game {

    private FrameLayout[] outerFieldViews;

    private final boolean isTwoPlayerGame;

    final View rootView;

    private final int[] OUTER_FIELD_VIEW_IDS = {R.id.outerFieldZero, R.id.outerFieldOne, R.id.outerFieldTwo,
                                                R.id.outerFieldThree, R.id.outerFieldFour, R.id.outerFieldFive,
                                                R.id.outerFieldSix, R.id.outerFieldSeven, R.id.outerFieldEight};

    //-1 = all, 0 = topleft, 2 top right etc.
    private int activeOuterField;

    private PlayerType winner;

    private boolean gameOver;

    private int turnCounter;

    private InnerField[] field;

    private int[] winningFieldIds;

    private boolean gameUsedForPrediction;

    //Constructors and methods for copying
    //-----



    //TODO mabye Highlight last move made
    public Game(View rootView, boolean isTwoPlayerGame){
        this(rootView, isTwoPlayerGame, false);
    }

    private Game(View rootView, boolean isTwoPlayerGame, boolean gameUsedForPrediction){
        this.isTwoPlayerGame = isTwoPlayerGame;
        this.rootView = rootView;

        field = new InnerField[9];
        for (int i = 0; i < field.length; i++) {
            field[i] = new InnerField(this, i);
        }

        winner = PlayerType.EMPTY;
        gameOver = false;
        activeOuterField = -1;
        turnCounter = 0;

        this.gameUsedForPrediction = gameUsedForPrediction;

        if(!gameUsedForPrediction){
            saveOuterFieldViews();
            updateStatusField();
            clearField();
            changeEnabledFieldsVisualisation();
        }
    }

    public Game createCopyForPrediction(){
        Game newGame = new Game(this.rootView, this.isTwoPlayerGame, true);
        newGame.setRequiredCopyValues(
                activeOuterField, winner, gameOver, turnCounter, field
        );
        return newGame;
    }


    private void setRequiredCopyValues(int activeOuterField,
                                       PlayerType winner,
                                       boolean gameOver,
                                       int turnCounter,
                                       InnerField[] field){
        this.activeOuterField = activeOuterField;
        this.winner = winner;
        this.gameOver = gameOver;
        this.turnCounter = turnCounter;
        for(int i = 0; i < field.length; i++){
            this.field[i] = field[i].createCopyForPrediction(this);
        }
    }



    //methods for updating the GUI
    //-----

    private void clearField(){

        for (FrameLayout outerFieldView : outerFieldViews) {
            outerFieldView.removeAllViews();

            LayoutInflater factory = LayoutInflater.from(rootView.getContext());



            outerFieldView.addView(factory.inflate(R.layout.tic_tac_toe_small, null));
            View innerFieldParent = outerFieldView.findViewById(R.id.innerFieldParent);
            for(int i = 0; i < 9; i++){
                innerFieldParent.findViewWithTag(String.valueOf(i)).setBackground(null);
            }
        }
    }

    private void changeEnabledFieldsVisualisation(){
        //maybe get colors with less transparency, when player clicks on not available field

        int enabled, disabled;
        int blue = rootView.getResources().getColor(R.color.field_blue);

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
        if(winningFieldIds != null){
            outerFieldViews[winningFieldIds[0]].setBackgroundColor(blue);
            outerFieldViews[winningFieldIds[1]].setBackgroundColor(blue);
            outerFieldViews[winningFieldIds[2]].setBackgroundColor(blue);
        }
    }

    private void updateButtonViewAfterMove(int outerFieldId, int innerFieldId, PlayerType activePlayer){
        View tempView = outerFieldViews[outerFieldId].
                findViewById(R.id.innerFieldParent);
        //TODO do not leave in production code
        Button view = null;
        if (tempView == null) {
            int a = 0;
            a++;
            int b = a*2;
        } else {
            view = (Button)
                    tempView.findViewWithTag(String.valueOf(innerFieldId));
        }

        if(activePlayer == PlayerType.PLAYER_X){
            view.setBackgroundResource(R.drawable.tic_tac_toe_x);
        } else {
            view.setBackgroundResource(R.drawable.tic_tac_toe_o);
        }
    }

    private void updateStatusField(){
        TextView statusView = (TextView) rootView.findViewById(R.id.statusText);
        if(gameOver && winner == PlayerType.EMPTY) {
            statusView.setText(R.string.draw_display_string);
        } else if(gameOver) {
                statusView.setText(winner.toString() + " won the game!");
        } else {
            statusView.setText("It's " + getActivePlayer().toString() + "'s turn");
        }
    }

    private void saveOuterFieldViews(){
        outerFieldViews = new FrameLayout[OUTER_FIELD_VIEW_IDS.length];
        for(int i = 0; i < OUTER_FIELD_VIEW_IDS.length; i++){
            outerFieldViews[i] = (FrameLayout) rootView.findViewById(OUTER_FIELD_VIEW_IDS[i]);
        }
    }



    public void updateInnerFieldWon(int id, PlayerType winner){
        if(!gameUsedForPrediction){
            FrameLayout layout = (FrameLayout) rootView.findViewById(OUTER_FIELD_VIEW_IDS[id]);
            layout.removeAllViews();
            ImageView view = new ImageView(rootView.getContext());
            if(winner == PlayerType.PLAYER_X){
                view.setImageResource(R.drawable.tic_tac_toe_x);
            } else if(winner == PlayerType.PLAYER_O){
                view.setImageResource(R.drawable.tic_tac_toe_o);
            }
            layout.addView(view);
        }
    }




    //methods handling user (or AI) interaction
    //-----

    public boolean handleClickOnView(Button view){
        //felt dirty writing this - sorry
        int outerTag = Integer.valueOf((String) ((View) view.getParent().getParent().getParent()).getTag()),
            innerTag = Integer.valueOf((String) view.getTag());



        System.out.println(outerTag + ": " + innerTag);

        if(fieldIsValidMove(outerTag, innerTag)){
            doMove(outerTag, innerTag);
            return true;
        }else {
            return false;
        }
    }

    public boolean isStillActive() {
        return !gameOver;
    }

    public void doMove(Move move){
        doMove(move.getOuterFieldId(), move.getInnerFieldId());
    }

    private void doMove(int outerFieldId, int innerFieldId){


        final PlayerType activePlayer = getActivePlayer();
        field[outerFieldId].doMove(innerFieldId, activePlayer);
        turnCounter++;
        activeOuterField = field[innerFieldId].isOver() ? -1 : innerFieldId;
        checkGameOver();

        if(!gameUsedForPrediction){
            if(!field[outerFieldId].isOver()){
                updateButtonViewAfterMove(outerFieldId, innerFieldId, activePlayer);
            }
            updateStatusField();
            changeEnabledFieldsVisualisation();
            System.out.println("on move " + turnCounter);
        }

    }

    //getter methods
    //-----


    public PlayerType getOuterFieldById(int id) {

        return field[id].getWinner();
    }

    public InnerField getInnerFieldStateById(int id){
        return field[id];
    }

    private PlayerType getActivePlayer(){
        return turnCounter % 2 == 0 ? PlayerType.PLAYER_X : PlayerType.PLAYER_O;
    }

    public Move[] getAllowedMoves(){
        ArrayList<Move> allowedMoves = new ArrayList<>();
        if(activeOuterField != -1){
            PlayerType[] innerPlayerTypes = field[activeOuterField].getFieldStatus();
            for(int i = 0; i < innerPlayerTypes.length; i++){
                if(innerPlayerTypes[i] == PlayerType.EMPTY){
                    allowedMoves.add(new Move(activeOuterField, i));
                }
            }
        } else {
            for (int i = 0; i < field.length ; i++) {
                if(!field[i].isOver()){
                    PlayerType[] innerPlayerTypes = field[i].getFieldStatus();
                    for(int j = 0; j < innerPlayerTypes.length; j++){
                        if(innerPlayerTypes[j] == PlayerType.EMPTY){
                            allowedMoves.add(new Move(i, j));
                        }
                    }
                }
            }
        }
        Move[] returnMoves = new Move[allowedMoves.size()];
        for(int i = 0; i < allowedMoves.size(); i++){
            returnMoves[i] = allowedMoves.get(i);
        }
        return returnMoves;
    }



    //game engine methods
    //-----

    private boolean fieldIsValidMove(int outerFieldId, int innerFieldId){
        final boolean outerFieldValid = outerFieldIsValid(outerFieldId);

        final boolean innerFieldValid = field[outerFieldId].isValidMove(innerFieldId);

        return outerFieldValid && innerFieldValid;
    }

    private boolean outerFieldIsValid(int outerFieldId){
        final boolean outerFieldValidMove = activeOuterField == -1 || activeOuterField == outerFieldId;

        final boolean innerFieldIsOver = field[outerFieldId].isOver();

        return outerFieldValidMove && !innerFieldIsOver;
    }

    private void setWinner(PlayerType winner, int[] winningFieldIds){
        this.winningFieldIds = winningFieldIds;
        if(!gameUsedForPrediction){
            System.out.println("Game won");
        }
        this.winner = winner;
        gameOver = true;
    }

    private void setDraw(){
        gameOver = true;
    }

    private void checkGameOver(){


        int[] winningFieldIds = new int[3];
        //checks vertical winning
        for(int i = 0; i < 3; i++){
            if(field[i].getWinner() == field[i+3].getWinner() && field[i].getWinner() == field[i+6].getWinner() && field[i].getWinner() != PlayerType.EMPTY){
                winningFieldIds[0] = i;
                winningFieldIds[1] = i + 3;
                winningFieldIds[2] = i + 6;
                setWinner(field[i].getWinner(), winningFieldIds);
                return;
            }
        }

        //checks horizontal winning
        for(int i = 0; i <= 6; i+=3){
            if(field[i].getWinner() == field[i+1].getWinner() && field[i].getWinner() == field[i+2].getWinner() && field[i].getWinner() != PlayerType.EMPTY){
                winningFieldIds[0] = i;
                winningFieldIds[1] = i + 1;
                winningFieldIds[2] = i + 2;
                setWinner(field[i].getWinner(), winningFieldIds);
                return;
            }
        }

        //diagonal winning check top-left to bottom-right
        if(field[0].getWinner() == field[4].getWinner() && field[4].getWinner() == field[8].getWinner() && field[4].getWinner() != PlayerType.EMPTY){
            winningFieldIds[0] = 0;
            winningFieldIds[1] = 4;
            winningFieldIds[2] = 8;
            setWinner(field[4].getWinner(), winningFieldIds);
            return;
        }

        //diagonal winning check top-right to bottom-Left
        if(field[2].getWinner() == field[4].getWinner() && field[4].getWinner() == field[6].getWinner() && field[4].getWinner() != PlayerType.EMPTY){
            winningFieldIds[0] = 2;
            winningFieldIds[1] = 4;
            winningFieldIds[2] = 6;
            setWinner(field[4].getWinner(), winningFieldIds);
        }

        boolean isDraw = true;
        for(InnerField f: field){
            isDraw = isDraw && f.isOver();
        }
        if(isDraw){
            setDraw();
        }
    }


}
