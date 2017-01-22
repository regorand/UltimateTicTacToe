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

        return moveEvaluator.evaluateMoveScores(allowedMoves);
    }

}
