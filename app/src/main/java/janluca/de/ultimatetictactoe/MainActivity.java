package janluca.de.ultimatetictactoe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import janluca.de.ultimatetictactoe.fragments.TwoPlayerFragment;
import janluca.de.ultimatetictactoe.fragments.HelpFragment;
import janluca.de.ultimatetictactoe.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity {

    TwoPlayerFragment activeGame;

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
        //maybe make TwoPlayerFragment abstract class/interface and let SinglePlayerFragment and DoublePlayerFragment implement it
        // -> scalable for online
        if(activeGame != null){
            activeGame.handleClick(view);
        }
    }

    public void startSinglePlayerGame(){

    }

    public void startGame(){
        TwoPlayerFragment fragment = TwoPlayerFragment.newInstance();

        activeGame = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, TwoPlayerFragment.FRAGMENT_MANAGER_ID)
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
