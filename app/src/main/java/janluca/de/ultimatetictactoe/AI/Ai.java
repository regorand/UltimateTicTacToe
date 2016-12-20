package janluca.de.ultimatetictactoe.AI;

import java.util.ArrayList;

import janluca.de.ultimatetictactoe.game.FieldState;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    //TODO difficulty (as enum ?)

    public Ai(){

    }


    public Move decideOnMove(final GameState gameState){
        ArrayList<Move> allowedMoves = getAllowedMoves(gameState);



        return allowedMoves.get((int) Math.random() % allowedMoves.size());
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
            //logic for getting allowed moves when all free spaces are allowed
        }
        return allowedMoves;
    }




}
