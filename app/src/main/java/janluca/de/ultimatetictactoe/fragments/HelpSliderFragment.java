package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.R;

public class HelpSliderFragment extends Fragment {

    public static final String FRAGMENT_MANAGER_ID = "help";

    public static HelpSliderFragment newInstance(){
        return new HelpSliderFragment();
    }

    public HelpSliderFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tutorial_slider_layout, container, false);
    }
}
