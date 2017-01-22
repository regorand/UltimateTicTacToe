package janluca.de.ultimatetictactoe.AI;

import android.view.View;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

public class ShowdownGameController implements GameController {

    private Game game;

    private Ai ai_X, ai_O;

    private boolean showdownStarted;

    private Difficulty difficultyX, difficultyO;

    public ShowdownGameController(View rootView, Difficulty difficultyX, Difficulty difficultyO) {
        game = new Game(rootView);
        this.difficultyX = difficultyX;
        this.difficultyO = difficultyO;
        ai_X = new Ai(difficultyX, this);
        ai_O = new Ai(difficultyO, this);
        showdownStarted = false;
    }

    public void handleClick(View view) {
        if(!showdownStarted){
            startShowdown();
            showdownStarted = true;
        }
    }

    @Override
    public void moveFound(Move move) {
        game.doMove(move);
        if(game.isStillActive()){
            if(game.getActivePlayer() == PlayerType.PLAYER_X){
                ai_X.execute(game.createCopyForPrediction());
                ai_O.cancel(true);
                ai_O = new Ai(difficultyO, this);
            } else {
                ai_O.execute(game.createCopyForPrediction());
                ai_X.cancel(true);
                ai_X = new Ai(difficultyX, this);
            }
        }
    }

    private void startShowdown() {
        ai_X.execute(game);
    }

    public void resetGame(View view) {
        ai_X.cancel(true);
        ai_O.cancel(true);
        ai_X = new Ai(difficultyX, this);
        ai_O = new Ai(difficultyO, this);
        game = new Game(view);
        showdownStarted = false;
    }
}
