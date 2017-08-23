package com.noveltystudios.android.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by tstavets on 8/7/17.
 */

public class Directions_Fragment extends Fragment {

    private TextView mTextView;
    private String mDirections;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_directions, container, false);

        if (mDirections == null) mDirections = "test";
        mTextView = (TextView) v.findViewById(R.id.directions_fragment_text);
        mTextView.setText(mDirections);

        return v;
    }

    public void setDirectionsText(String text) {
        mDirections = text;
    }

    public void updateText() {
        mTextView.setText(mDirections);
    }



}
