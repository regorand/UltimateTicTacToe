package janluca.de.ultimatetictactoe.AI;

import java.util.ArrayList;
import java.util.Random;

import janluca.de.ultimatetictactoe.game.FieldState;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.InnerFieldState;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    //TODO difficulty (as enum ?)

    public Ai(){

    }


    public Move decideOnMove(final GameState gameState){
        ArrayList<Move> allowedMoves = getAllowedMoves(gameState);
        Random r = new Random();
        return allowedMoves.get(r.nextInt(allowedMoves.size()));
    }

    private ArrayList<Move> getAllowedMoves(GameState gameState){
        ArrayList<Move> allowedMoves = new ArrayList<>();
        int activeOuterField = gameState.getActiveOuterField();
        if(activeOuterField != -1){
            FieldState[] innerFieldStates = gameState.getInnerFieldStateById(activeOuterField).getStates();
            for(int i = 0; i < innerFieldStates.length; i++){
                if(innerFieldStates[i] == FieldState.EMPTY){
                    allowedMoves.add(new Move(activeOuterField, i));
                }
            }
        } else {
            InnerFieldState[] innerField = gameState.getInnerFieldStates();
            for (int i = 0; i < innerField.length ; i++) {
                if(!innerField[i].isOver()){
                    FieldState[] innerFieldStates = gameState.getInnerFieldStateById(i).getStates();
                    for(int j = 0; j < innerFieldStates.length; j++){
                        if(innerFieldStates[j] == FieldState.EMPTY){
                            allowedMoves.add(new Move(i, j));
                        }
                    }
                }
            }{

            }
        }
        return allowedMoves;
    }




}
