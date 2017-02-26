package janluca.de.ultimatetictactoe.fragments;

import android.os.Build;
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

    private View activeViewX, activeViewO;

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

    private void changeActiveButtonX(View view){
        //view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(activeViewX != null) {
                activeViewX.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected, null));
            }
            view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected, null));
        } else {
            if(activeViewX != null) {
                activeViewX.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
            }
            view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected));
        }
        activeViewX = view;
    }

    private void changeActiveButtonO(View view){
        //view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(activeViewO != null) {
                activeViewO.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected, null));
            }
            view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected, null));
        } else {
            if(activeViewO != null){
                activeViewO.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_not_selected));
            }
            view.setBackgroundColor(getView().getResources().getColor(R.color.showdown_button_selected));
        }
        activeViewO = view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.showdown_difficulty_fragment_layout, container, false);
    }

    public void startShowdown(){
        ((MainActivity) getActivity()).startBotShowdown(selectedDiffX, selectedDiffO);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button veryEasyX = (Button) getActivity().findViewById(R.id.very_easy_bot_button_X);
        changeActiveButtonX(veryEasyX);
        veryEasyX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(view);
                setDiffX(Difficulty.VERY_EASY);
            }
        });
        getActivity().findViewById(R.id.easy_bot_button_X).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(view);
                setDiffX(Difficulty.EASY);
            }
        });
        getActivity().findViewById(R.id.medium_bot_button_X).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(view);
                setDiffX(Difficulty.MEDIUM);
            }
        });
        getActivity().findViewById(R.id.hard_bot_button_X).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonX(view);
                setDiffX(Difficulty.HARD);
            }
        });

        Button veryEasyO = (Button) getActivity().findViewById(R.id.very_easy_bot_button_O);
        changeActiveButtonO(veryEasyO);
        veryEasyO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(view);
                setDiffO(Difficulty.VERY_EASY);
            }
        });
        getActivity().findViewById(R.id.easy_bot_button_O).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(view);
                setDiffO(Difficulty.EASY);
            }
        });
        getActivity().findViewById(R.id.medium_bot_button_O).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(view);
                setDiffO(Difficulty.MEDIUM);
            }
        });
        getActivity().findViewById(R.id.hard_bot_button_O).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActiveButtonO(view);
                setDiffO(Difficulty.HARD);
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
