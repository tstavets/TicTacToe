package com.noveltystudios.android.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by tstavets on 8/20/17.
 */

public class BlankFragment extends Fragment {

    private mainMenuListener mListener;
    private LinearLayout mLinearLayout;
    private Button mButton;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);

        mLinearLayout = (LinearLayout) v.findViewById(R.id.blank_layout_fragment);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutPressed();
            }
        });

        mButton = (Button) v.findViewById(R.id.blank_fragment_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justMoveOn();
            }
        });


        return v;
    }



        @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (mainMenuListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            mListener.cancelPressed();
            return;
        }
        else {
            mListener.okayPressed();
        }
    }

    public void justMoveOn() {
        mListener.justMoveOn();
    }
    public void layoutPressed() { mListener.layoutPressed(); }

    public interface mainMenuListener {
        public void okayPressed();
        public void cancelPressed();
        public void justMoveOn();
        public void layoutPressed();
    }



}
