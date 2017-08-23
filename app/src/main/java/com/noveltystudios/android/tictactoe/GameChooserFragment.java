package com.noveltystudios.android.tictactoe;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class GameChooserFragment extends Fragment {
   private Button mHangmanButton, mTicTacToeButton, mSuperTicTacToeButton, mSuperTicTacToeHangmanButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_chooser, container, false);


        mHangmanButton = (Button) v.findViewById(R.id.HangmanButton);
        mHangmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.playSoundEffect();
                MainMenuActivity.setMusicHelper(true);
                Intent intent = new Intent(v.getContext(), HangmanActivity.class);
                startActivity(intent);
            }
        });

        mTicTacToeButton = (Button) v.findViewById(R.id.TicTacToeButton);
        mTicTacToeButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
               MainMenuActivity.playSoundEffect();
               MainMenuActivity.setMusicHelper(true);
               Intent intent = new Intent(v.getContext(), TicTacToeActivity.class);
               startActivity(intent);
           }
        });

        mSuperTicTacToeButton = (Button) v.findViewById(R.id.SuperTicTacToeButton);
        mSuperTicTacToeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.playSoundEffect();
                MainMenuActivity.setMusicHelper(true);
                Intent intent = new Intent(v.getContext(), SuperTicTacToeActivity.class);
                startActivity(intent);
            }
        });

        mSuperTicTacToeHangmanButton = (Button) v.findViewById(R.id.SuperTicTacToeHangmanButton);
        mSuperTicTacToeHangmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.playSoundEffect();
                MainMenuActivity.setMusicHelper(true);
                Intent intent = new Intent(v.getContext(), SuperTicTacToeHangmanActivity.class);
                startActivity(intent);
            }
        });


        return v;
    }


}
