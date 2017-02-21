package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import janluca.de.ultimatetictactoe.AI.Difficulty;
import janluca.de.ultimatetictactoe.MainActivity;
import janluca.de.ultimatetictactoe.R;

public class ShowdownDifficultyFragment extends Fragment {

    final static public String FRAGMENT_MANAGER_ID = "showdownDifficulty";

    private Button[] buttons_X = new Button[4], buttons_O = new Button[4];

    private int activeButtonX, activeButtonO;

    private Difficulty selectedDiffX, selectedDiffO;

    public static ShowdownDifficultyFragment newInstance(){
        ShowdownDifficultyFragment fragment = new ShowdownDifficultyFragment();
        fragment.setDiffX(Difficulty.VERY_EASY);
        fragment.setDiffO(Difficulty.VERY_EASY);
        return new ShowdownDifficultyFragment();
    }

    public ShowdownDifficultyFragment(){

    }

    public void setDiffX(Difficulty difficulty){
        this.selectedDiffX = difficulty;
    }

    public void setDiffO(Difficulty difficulty){
        this.selectedDiffO = difficulty;
    }

    private Difficulty findDifficultyById(int id){
        switch (id){
            case 0:
                return Difficulty.VERY_EASY;
            case 1:
                return Difficulty.EASY;
            case 2:
                return Difficulty.MEDIUM;
            case 3:
                return Difficulty.HARD;
        }
        return Difficulty.VERY_EASY;
    }

    private void changeActiveButtonX(int newActiveButtonId){
        buttons_X[activeButtonX].setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
        buttons_X[newActiveButtonId].setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected));
        activeButtonX = newActiveButtonId;
        setDiffX(findDifficultyById(newActiveButtonId));
    }

    private void changeActiveButtonO(int newActiveButtonId){
        buttons_O[activeButtonO].setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
        buttons_O[newActiveButtonId].setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected));
        activeButtonO = newActiveButtonId;
        setDiffO(findDifficultyById(newActiveButtonId));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.showdown_difficulty_fragment_layout, container, false);
        changeActiveButtonX(0);
        changeActiveButtonO(0);
        return view;
    }

    public void startShowdown(){
        ((MainActivity) getActivity()).startBotShowdown(selectedDiffX, selectedDiffO);
    }

    @Override
    public void onStart() {
        super.onStart();
        buttons_X[0] = (Button) getActivity().findViewById(R.id.very_easy_bot_button_X);
        buttons_X[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(0);
            }
        });
        buttons_X[1] = (Button) getActivity().findViewById(R.id.easy_bot_button_X);
        buttons_X[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(1);
            }
        });
        buttons_X[2] = (Button) getActivity().findViewById(R.id.medium_bot_button_X);
        buttons_X[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(2);
            }
        });
        buttons_X[3] = (Button) getActivity().findViewById(R.id.hard_bot_button_X);
        buttons_X[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(3);
            }
        });

        buttons_O[0] = (Button) getActivity().findViewById(R.id.very_easy_bot_button_O);
        buttons_O[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(0);
            }
        });
        buttons_O[1] = (Button) getActivity().findViewById(R.id.easy_bot_button_O);
        buttons_O[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(1);
            }
        });
        buttons_O[2] = (Button) getActivity().findViewById(R.id.medium_bot_button_O);
        buttons_O[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(2);
            }
        });
        buttons_O[3] = (Button) getActivity().findViewById(R.id.hard_bot_button_O);
        buttons_O[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(3);
            }
        });

        getActivity().findViewById(R.id.start_showdown_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startShowdown();
            }
        });
    }
}
