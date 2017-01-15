package janluca.de.ultimatetictactoe.AI;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;

import janluca.de.ultimatetictactoe.game.PlayerType;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.InnerFieldState;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    private final PlayerType AI_PLAYER_TYPE = PlayerType.PLAYER_O;

    private Difficulty difficulty;



    public Ai(Difficulty difficulty, GameState gameState){
        this.difficulty = difficulty;

    }

    public Move decideOnMove(final GameState gameState){
        Move[] allowedMoves = gameState.getAllowedMoves();
        MoveEvaluator moveEvaluator = new MoveEvaluator(difficulty, AI_PLAYER_TYPE, gameState);

        int[] scores = moveEvaluator.evaluateMoveScores(allowedMoves);

        return allowedMoves[getMoveIndexByDifficulty(scores)];
    }


    // maybe move this into GameState class


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
