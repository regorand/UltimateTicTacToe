package janluca.de.ultimatetictactoe.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import janluca.de.ultimatetictactoe.R;
import janluca.de.ultimatetictactoe.game.Game;

public class GameFragment extends Fragment{

    public static final String FRAGMENT_MANAGER_ID = "game";

    Game game;

    public static GameFragment newInstance(){
        return new GameFragment();

    }

    public void registerGame(){
        game = new Game(getView());
    }

    public GameFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerGame();
        getView().findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        game = new Game(getView());
    }

}
