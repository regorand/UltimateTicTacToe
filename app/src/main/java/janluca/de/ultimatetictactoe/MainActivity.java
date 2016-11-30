package janluca.de.ultimatetictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import janluca.de.ultimatetictactoe.game.Game;

public class MainActivity extends AppCompatActivity {

    Game game;

    //TODO eventually create menu (fragments)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);
    }


    public void handleClick(View view){
        game.handleClickOnView((Button) view);
    }
}
