package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.AI.AIGameController;
import janluca.de.ultimatetictactoe.AI.Difficulty;
import janluca.de.ultimatetictactoe.AI.ShowdownGameController;
import janluca.de.ultimatetictactoe.R;

public class BotShowdownFragment extends Fragment implements PlayableFragment {

    final static public String FRAGMENT_MANAGER_ID = "botShowdown";

    private Difficulty difficultyO, difficultyX;

    private ShowdownGameController showdownGameController;

    public static BotShowdownFragment newInstance(Difficulty difficultyBotX, Difficulty difficultyBotO) {
        BotShowdownFragment fragment = new BotShowdownFragment();
        fragment.difficultyX = difficultyBotX;
        fragment.difficultyO = difficultyBotO;
        return fragment;
    }

    public BotShowdownFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bot_showdown_layout, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        showdownGameController = new ShowdownGameController(getView(), difficultyX, difficultyO);
        getView().findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
        System.out.println("started");
    }

    @Override
    public void handleClick(View view) {
        showdownGameController.handleClick(view);
    }

    public void resetGame(){
        showdownGameController.resetGame(getView());
    }
}
