package janluca.de.ultimatetictactoe.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import janluca.de.ultimatetictactoe.R;

public class HelpFragment extends Fragment {

    public static final String FRAGMENT_MANAGER_ID = "help";

    public static HelpFragment newInstance(){
        return new HelpFragment();
    }

    public HelpFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help_fragment_layout, container, false);
    }
}
