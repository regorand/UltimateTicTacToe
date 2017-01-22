package janluca.de.ultimatetictactoe.AI;

import android.os.AsyncTask;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.PlayerType;
import janluca.de.ultimatetictactoe.game.Move;

public class Ai extends AsyncTask<Game, Void, Move> {

    private final PlayerType AI_PLAYER_TYPE = PlayerType.PLAYER_O;

    private Difficulty difficulty;

    private final GameController gameController;

    public Ai(Difficulty difficulty, GameController gameController){
        this.difficulty = difficulty;
        this.gameController = gameController;
    }

    // maybe move this into different class
    public Move decideOnMove(final Game game){
        Move[] allowedMoves = game.getAllowedMoves();
        MoveEvaluator moveEvaluator = new MoveEvaluator(difficulty, AI_PLAYER_TYPE, game);

        return moveEvaluator.evaluateMoveScores(allowedMoves);
    }

    @Override
    protected Move doInBackground(Game... games) {
        return decideOnMove(games[0]);
    }

    @Override
    protected void onPostExecute(Move move) {
        gameController.moveFound(move);
    }
}
