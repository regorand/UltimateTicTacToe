package janluca.de.ultimatetictactoe;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import janluca.de.ultimatetictactoe.AI.Difficulty;
import janluca.de.ultimatetictactoe.fragments.BotShowdownFragment;
import janluca.de.ultimatetictactoe.fragments.ChooseDifficultyFragment;
import janluca.de.ultimatetictactoe.fragments.PlayableFragment;
import janluca.de.ultimatetictactoe.fragments.SinglePlayerFragment;
import janluca.de.ultimatetictactoe.fragments.TwoPlayerFragment;
import janluca.de.ultimatetictactoe.fragments.HelpFragment;
import janluca.de.ultimatetictactoe.fragments.MenuFragment;

public class MainActivity extends AppCompatActivity {

    PlayableFragment activeGame;

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

    public void startBotShowdown(){
        //second Difficulty goes first and is X
        BotShowdownFragment fragment = BotShowdownFragment.newInstance(Difficulty.HARD, Difficulty.EASY);

        activeGame = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, BotShowdownFragment.FRAGMENT_MANAGER_ID)
                .addToBackStack(null)
                .commit();
    }

    public void startSinglePlayerGame(Difficulty difficulty){
        SinglePlayerFragment fragment = SinglePlayerFragment.newInstance(difficulty);

        activeGame = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, SinglePlayerFragment.FRAGMENT_MANAGER_ID)
                .addToBackStack(null)
                .commit();
    }

    public void startTwoPlayerGame(){
        TwoPlayerFragment fragment = TwoPlayerFragment.newInstance();

        activeGame = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, fragment, TwoPlayerFragment.FRAGMENT_MANAGER_ID)
                .addToBackStack(null)
                .commit();
    }

    public void chooseDifficulty(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root_layout, ChooseDifficultyFragment.newInstance(), ChooseDifficultyFragment.FRAGMENT_MANAGER_ID)
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
