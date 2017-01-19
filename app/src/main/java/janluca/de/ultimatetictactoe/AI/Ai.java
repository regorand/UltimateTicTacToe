package janluca.de.ultimatetictactoe.AI;

import java.util.Random;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.PlayerType;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai {

    private final PlayerType AI_PLAYER_TYPE = PlayerType.PLAYER_O;

    private Difficulty difficulty;



    public Ai(Difficulty difficulty, Game game){
        this.difficulty = difficulty;

    }

    // maybe move this into different class
    public Move decideOnMove(final Game game){
        Move[] allowedMoves = game.getAllowedMoves();
        MoveEvaluator moveEvaluator = new MoveEvaluator(difficulty, AI_PLAYER_TYPE, game);

        int[] scores = moveEvaluator.evaluateMoveScores(allowedMoves);

        return allowedMoves[getMoveIndexByDifficulty(scores)];
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
