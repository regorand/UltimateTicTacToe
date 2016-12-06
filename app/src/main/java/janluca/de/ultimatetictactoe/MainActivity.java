package janluca.de.ultimatetictactoe;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import janluca.de.ultimatetictactoe.fragments.GameFragment;
import janluca.de.ultimatetictactoe.fragments.HelpFragment;
import janluca.de.ultimatetictactoe.fragments.MenuFragment;
import janluca.de.ultimatetictactoe.game.Game;

public class MainActivity extends AppCompatActivity {

    GameFragment activeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activeGame = null;

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_layout, MenuFragment.newInstance(), MenuFragment.FRAGMENT_MANAGER_ID)
                    .commit();
        }
    }

    public void handleClick(View view){
        if(activeGame != null){
            activeGame.handleClick(view);
        }
    }

    public void startGame(){
        GameFragment fragment = GameFragment.newInstance();

        activeGame = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, GameFragment.FRAGMENT_MANAGER_ID)
                .addToBackStack(null)
                .commit();
    }

    public void showHelp(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, HelpFragment.newInstance(), HelpFragment.FRAGMENT_MANAGER_ID)
                .addToBackStack(null)
                .commit();
    }
}
