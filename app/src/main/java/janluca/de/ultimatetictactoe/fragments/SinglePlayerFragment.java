package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.AI.AIGameController;
import janluca.de.ultimatetictactoe.R;

public class SinglePlayerFragment extends Fragment implements PlayableFragment {

    final static public String FRAGMENT_MANAGER_ID = "singlePlayer";

    private AIGameController aiGameController;

    public static SinglePlayerFragment newInstance(){
        return new SinglePlayerFragment();
    }

    public SinglePlayerFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.single_player_fragment_layout, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        aiGameController = new AIGameController(getView());
    }

    @Override
    public void handleClick(View rootView) {

    }
}
