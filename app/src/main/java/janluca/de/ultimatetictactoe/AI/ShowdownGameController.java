package janluca.de.ultimatetictactoe.AI;

import android.view.View;
import android.widget.Button;

import java.util.Random;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameController;
import janluca.de.ultimatetictactoe.game.Move;
import janluca.de.ultimatetictactoe.game.PlayerType;

public class ShowdownGameController implements GameController {

    private Game game;

    private View rootView;

    private Ai ai_X, ai_O;

    private boolean showdownStarted;

    private static final int MAX_LOOP_DEPTH = 100;

    private int currentLoopDepth = 0;

    private Difficulty difficultyX, difficultyO;

    private int winCounterX = 0, winCounterO = 0, draw = 0;

    public ShowdownGameController(View rootView, Difficulty difficultyX, Difficulty difficultyO) {
        this.rootView = rootView;
        game = new Game(rootView);
        this.difficultyX = difficultyX;
        this.difficultyO = difficultyO;
        ai_X = new Ai(difficultyX, this);
        ai_O = new Ai(difficultyO, this);
        showdownStarted = false;
    }

    public void handleClick(View view) {
        if(!showdownStarted){
            showdownStarted = true;
            game.handleClickOnView((Button) view);
            startShowdown();
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
        if(!game.isStillActive()){
            restartShowdown();
        }
    }

    private void restartShowdown(){
        if(currentLoopDepth < MAX_LOOP_DEPTH){
            System.out.println(currentLoopDepth);
            currentLoopDepth++;
            Random r = new Random();

            if(game.getWinner() == PlayerType.PLAYER_X){
                winCounterX++;
            } else if (game.getWinner() == PlayerType.PLAYER_O){
                winCounterO++;
            } else {
                draw++;
            }
            resetGame(rootView);
            showdownStarted = true;
            int innerField = r.nextInt(9),
                    outerField = r.nextInt(9);

            game.doMove(new Move(outerField, innerField));
            startShowdown();
        }else {
            System.out.println("Wins for X: " + winCounterX + "\nWins for O: " + winCounterO + "\nDraws: " + draw);
        }

    }

    private void startShowdown() {
        ai_O.execute(game);
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
