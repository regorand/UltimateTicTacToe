package janluca.de.ultimatetictactoe.AI;


import android.view.View;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

public class AIGameController implements GameController {

    private Game game;

    private Difficulty difficulty;
    //only used during botShowdown
    private Ai ai;

    public AIGameController(View rootView, Difficulty difficulty) {
        game = new Game(rootView);
        ai = new Ai(difficulty, this);
        this.difficulty = difficulty;
    }

    public void handleClick(View view) {
        if (game.isStillActive() && game.getActivePlayer() == PlayerType.PLAYER_X) {
            if (game.handleClickOnView((Button) view)) {
                doAIMove();
            }
        }
    }

    @Override
    public void moveFound(Move move) {
        game.doMove(move);
        ai.cancel(true);
        ai = new Ai(difficulty, this);
    }

    private void doAIMove() {
        if (game.isStillActive()) {
            ai.execute(game.createCopyForPrediction());
        }
    }

    public void resetGame(View view) {
        game = new Game(view);
    }
}
