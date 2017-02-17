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
        System.out.println("onActivityCreated was called");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate was called");
    }

    public void registerGame(){
        if(game == null){
            game = new Game(getView());
        }
    }

    public TwoPlayerFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("onCreateView was called");
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
