package janluca.de.ultimatetictactoe.AI;


import android.view.View;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameState;
import janluca.de.ultimatetictactoe.game.Move;

public class AIGameController {

    private Game game;

    private Ai ai;

    public AIGameController(View rootView, Difficulty difficulty){
        game = new Game(rootView, false);
        ai = new Ai(difficulty, game.getCurrentState());
    }

    public void handleClick(View view){
        if(game.isStillActive()){
            if(game.handleClickOnView((Button) view)){
                doAIMove();
            }
        }


    }

    private void doAIMove(){
        final GameState gameState = game.getCurrentState();

        Move move = ai.decideOnMove(gameState);

        game.doMove(move);
    }

    //TODO create AI
    //http://www.gamedev.net/page/resources/_/technical/artificial-intelligence/a-practical-guide-to-building-a-complete-game-a-r784

    public void resetGame(View view){
        game = new Game(view, false);
    }
}
