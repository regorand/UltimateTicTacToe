package janluca.de.ultimatetictactoe.AI;


import android.view.View;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.Move;

public class AIGameController {

    private Game game;

    boolean isShowdown;

    //only used during botShowdown
    private Ai ai, firstAi;

    public AIGameController(View rootView, Difficulty difficulty){
        game = new Game(rootView, false);
        ai = new Ai(difficulty, game);
        isShowdown = false;
        firstAi = null;
    }

    public AIGameController(View rootView, Difficulty difficultyO, Difficulty difficultyX){
        game = new Game(rootView, false);
        ai = new Ai(difficultyO, game);
        firstAi = new Ai(difficultyX, game);
        isShowdown = true;
    }

    public void handleClick(View view){
        if(isShowdown){
            doShowdown();
        }else {
            if(game.isStillActive()){
                if(game.handleClickOnView((Button) view)){
                    doAIMove();
                }
            }
        }
    }

    private void doShowdown(){
        while(game.isStillActive()){
            game.doMove(firstAi.decideOnMove(game));
            doAIMove();
        }
    }

    private void doAIMove(){
        if(game.isStillActive()){
            Move move = ai.decideOnMove(game);
            game.doMove(move);
        }
    }

    public void resetGame(View view){
        game = new Game(view, false);
    }
}
