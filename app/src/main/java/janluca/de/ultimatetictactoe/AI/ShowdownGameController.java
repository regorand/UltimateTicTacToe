package janluca.de.ultimatetictactoe.AI;

import android.view.View;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.Move;

public class ShowdownGameController implements GameController {

    private Game game;

    private Ai ai_X, ai_O;

    public ShowdownGameController(View rootView, Difficulty difficultyX, Difficulty difficultyO) {
        game = new Game(rootView);
        ai_X = new Ai(difficultyX, this);
        ai_O = new Ai(difficultyO, this);
    }

    public void handleClick(View view) {
        startShowdown();
    }

    @Override
    public void moveFound(Move move) {

    }

    private void startShowdown() {

    }

    public void resetGame(View view) {
        game = new Game(view);
    }

}
