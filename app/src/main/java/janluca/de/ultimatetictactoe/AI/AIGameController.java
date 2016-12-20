package janluca.de.ultimatetictactoe.AI;


import android.view.View;
import android.widget.Button;

import janluca.de.ultimatetictactoe.game.Game;
import janluca.de.ultimatetictactoe.game.GameState;

public class AIGameController {

    private Game game;

    public AIGameController(View rootView){
        game = new Game(rootView, false);
    }

    public void handleClick(View view){
        game.handleClickOnView((Button) view);


        doAIMove();
    }

    private void doAIMove(){
        final GameState gameState = game.getCurrentState();




    }

    //TODO create AI
    //http://www.gamedev.net/page/resources/_/technical/artificial-intelligence/a-practical-guide-to-building-a-complete-game-a-r784

    public void resetGame(View view){
        game = new Game(view, false);
    }
}
