package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.AI.AIController;
import janluca.de.ultimatetictactoe.R;

public class SinglePlayerFragment extends Fragment implements playableFragment {

    private AIController aiController;

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
        aiController = new AIController(getView());
    }

    @Override
    public void handleClick(View rootView) {

    }
}
