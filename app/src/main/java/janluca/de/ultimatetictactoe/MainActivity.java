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
import janluca.de.ultimatetictactoe.fragments.MenuFragment;
import janluca.de.ultimatetictactoe.game.Game;

public class MainActivity extends AppCompatActivity {


    //TODO eventually create menu (fragments)

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
                    .add(R.id.root_layout, MenuFragment.newInstance(), "menu")
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

    public void changeActiveFragment(Fragment fragment, String id){

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, id)
                .addToBackStack(null)
                .commit();
    }
}
