package janluca.de.ultimatetictactoe.AI;


import android.view.View;

import janluca.de.ultimatetictactoe.game.Game;

public class AIGameController {

    private Game game;

    //tracks whether the ai needs to decide on a move
    private boolean isPlayersTurn;

    public AIGameController(View rootView){
        game = new Game(rootView);
        isPlayersTurn = true;
    }

    //TODO create AI
    //http://www.gamedev.net/page/resources/_/technical/artificial-intelligence/a-practical-guide-to-building-a-complete-game-a-r784



}
