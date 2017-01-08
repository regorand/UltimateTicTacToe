package janluca.de.ultimatetictactoe.AI;

import java.util.ArrayList;
import java.util.Random;

import janluca.de.ultimatetictactoe.game.FieldState;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.InnerFieldState;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    private Difficulty difficulty;

    public Ai(Difficulty difficulty){
        this.difficulty = difficulty;
    }

    public Move decideOnMove(final GameState gameState){
        Move[] allowedMoves = getAllowedMoves(gameState);
        int[] scores = evaluateMoves(allowedMoves);

        return allowedMoves[getMoveIndexByDifficulty(scores)];
    }

    private int getMoveIndexByDifficulty(int[] scores){#
        //either get random move of top x for different difficulties or use different levels of recursion and prediction in evaluateMoves()
        if(difficulty == Difficulty.VERY_EASY){
            Random r = new Random();
            return r.nextInt(scores.length);
        } else if(difficulty == Difficulty.EASY){

        } else if(difficulty == Difficulty.MEDIUM){

        } else if(difficulty == Difficulty.HARD){

        }
        return 0;
    }

    private int[] evaluateMoves(Move[] allowedMoves){
        int [] scores = new int[allowedMoves.length];
        for(Move move: allowedMoves){
            int score = 0;
        }
        return scores;
    }

    private Move[] getAllowedMoves(GameState gameState){
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
        return (Move[]) allowedMoves.toArray();
    }




}
