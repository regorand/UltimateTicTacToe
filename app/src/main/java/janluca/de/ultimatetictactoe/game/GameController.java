package janluca.de.ultimatetictactoe.game;

import android.view.View;

public interface GameController {
    void handleClick(View rootView);
    void moveFound(Move move);
}
