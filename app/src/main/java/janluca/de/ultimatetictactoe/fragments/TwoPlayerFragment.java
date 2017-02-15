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
import janluca.de.ultimatetictactoe.game.PlayerType;

public class TwoPlayerFragment extends Fragment implements PlayableFragment {

    public static final String FRAGMENT_MANAGER_ID = "twoPlayer";

    Bundle savedGame;

    Game game;

    public static TwoPlayerFragment newInstance(){
        return new TwoPlayerFragment();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList("fieldStates", game.getFieldStateForSavingInstanceState());
        outState.putInt("activeOuterField", game.getActiveOuterFieldId());
        outState.putInt("turnCounter", game.getTurnCounter());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("I was here!");
    }

    public void registerGame(){
        game = new Game(getView());
        if(savedGame != null){
            game.loadFieldStateFromInstanceState(savedGame.getIntegerArrayList("fieldStates"));
            game.setActiveOuterField(savedGame.getInt("activeOuterField"));
            game.setTurnCounter(savedGame.getInt("turnCounter"));
        }
    }

    public TwoPlayerFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.two_player_fragment_layout, container, false);
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

    @Override
    public void handleClick(View view){
        if(game.isStillActive()){
            game.handleClickOnView((Button) view);
        }
    }

    public void resetGame(){
        game = new Game(getView());
    }

}
