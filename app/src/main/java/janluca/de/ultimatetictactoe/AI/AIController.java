package janluca.de.ultimatetictactoe.AI;


import android.view.View;

import janluca.de.ultimatetictactoe.game.Game;

public class AIController {

    private Game game;

    public AIController(View rootView){
        game = new Game(rootView);
    }


}
