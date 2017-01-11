package janluca.de.ultimatetictactoe.AI;

import java.util.ArrayList;
import java.util.Random;

import janluca.de.ultimatetictactoe.game.PlayerType;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.InnerFieldState;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    private Difficulty difficulty;

    private MoveEvaluator moveEvaluator;

    public Ai(Difficulty difficulty, GameState gameState){
        this.difficulty = difficulty;
        moveEvaluator = new MoveEvaluator(difficulty);
        moveEvaluator.setGameState(gameState);
    }

    public Move decideOnMove(final GameState gameState){
        Move[] allowedMoves = getAllowedMoves(gameState);
        int[] scores = moveEvaluator.evaluateMoveScores(allowedMoves);

        return allowedMoves[getMoveIndexByDifficulty(scores)];
    }


    // maybe move this into GameState class
    private Move[] getAllowedMoves(GameState gameState){
        ArrayList<Move> allowedMoves = new ArrayList<>();
        int activeOuterField = gameState.getActiveOuterField();
        if(activeOuterField != -1){
            PlayerType[] innerPlayerTypes = gameState.getInnerFieldStateById(activeOuterField).getStates();
            for(int i = 0; i < innerPlayerTypes.length; i++){
                if(innerPlayerTypes[i] == PlayerType.EMPTY){
                    allowedMoves.add(new Move(activeOuterField, i));
                }
            }
        } else {
            InnerFieldState[] innerField = gameState.getInnerFieldStates();
            for (int i = 0; i < innerField.length ; i++) {
                if(!innerField[i].isOver()){
                    PlayerType[] innerPlayerTypes = gameState.getInnerFieldStateById(i).getStates();
                    for(int j = 0; j < innerPlayerTypes.length; j++){
                        if(innerPlayerTypes[j] == PlayerType.EMPTY){
                            allowedMoves.add(new Move(i, j));
                        }
                    }
                }
            }{

            }
        }
        return (Move[]) allowedMoves.toArray();
    }

    private int getMoveIndexByDifficulty(int[] scores){
        //either get random move of top x for different difficulties or use different levels of recursion and prediction in getMovesScores()
        if(difficulty == Difficulty.VERY_EASY){
            Random r = new Random();
            return r.nextInt(scores.length);
        } else if(difficulty == Difficulty.EASY){

        } else if(difficulty == Difficulty.MEDIUM){

        } else if(difficulty == Difficulty.HARD){

        }
        return 0;
    }







}
