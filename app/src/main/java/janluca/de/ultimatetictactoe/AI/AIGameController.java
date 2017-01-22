package janluca.de.ultimatetictactoe.AI;


import android.view.View;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.Move;

public class AIGameController implements GameController {

    private Game game;

    //only used during botShowdown
    private Ai ai;

    public AIGameController(View rootView, Difficulty difficulty) {
        game = new Game(rootView);
        ai = new Ai(difficulty, this);
    }

    public void handleClick(View view) {
        if (game.isStillActive()) {
            if (game.handleClickOnView((Button) view)) {
                doAIMove();
            }
        }
    }

    @Override
    public void moveFound(Move move) {

    }

    private void doAIMove() {
        if (game.isStillActive()) {
            Move move = ai.decideOnMove(game);
            game.doMove(move);
        }
    }

    public void resetGame(View view) {
        game = new Game(view);
    }
}
