package janluca.de.ultimatetictactoe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import janluca.de.ultimatetictactoe.game.Game;

public class MainActivity extends AppCompatActivity {

    Game game;

    //TODO eventually create menu (fragments)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = new Game(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("test");
                resetGame();
            }
        });
    }


    public void handleClick(View view){
        if(game.isStillActive()){
            game.handleClickOnView((Button) view);
        }
    }

    public void resetGame(){
        game = new Game(this);
    }
}
