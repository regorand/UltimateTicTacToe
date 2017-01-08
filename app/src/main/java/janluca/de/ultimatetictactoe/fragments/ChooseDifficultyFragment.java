package janluca.de.ultimatetictactoe.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.AI.Difficulty;
import janluca.de.ultimatetictactoe.MainActivity;
import janluca.de.ultimatetictactoe.R;

public class ChooseDifficultyFragment extends Fragment{

    public static final String FRAGMENT_MANAGER_ID = "chooseDifficulty";

    public static ChooseDifficultyFragment newInstance(){
        return new ChooseDifficultyFragment();
    }

    public ChooseDifficultyFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_difficulty_fragment_layout, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.very_easy_bot_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startSinglePlayerGame(Difficulty.VERY_EASY);
            }
        });
        getActivity().findViewById(R.id.easy_bot_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startSinglePlayerGame(Difficulty.EASY);
            }
        });
        getActivity().findViewById(R.id.medium_bot_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startSinglePlayerGame(Difficulty.MEDIUM);
            }
        });
        getActivity().findViewById(R.id.hard_bot_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startSinglePlayerGame(Difficulty.HARD);
            }
        });
    }
}
