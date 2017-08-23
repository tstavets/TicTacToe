package com.noveltystudios.android.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noveltystudios.android.tictactoe.database.GameBaseHelper;

/**
 * Created by tstavets on 7/27/17.
 */

public abstract class AbstractGameFragment extends Fragment implements AbstractStrings {

    public buttonListener mListener;
    protected PlayerStyle p1,p2;
    private int INDEX;
    protected boolean mIsXTurn;
    protected GameBaseHelper dbHelper;
    protected boolean mGameSaved;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        INDEX = 0;

        dbHelper = new GameBaseHelper(getContext());

        mGameSaved = false;
        mIsXTurn = true;
    }

    abstract public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);



    protected void setPlayerStyles() {
        p1 = new PlayerStyle(); p2 = new PlayerStyle();
        setPlayerOneStyle();
        setPlayerTwoStyle();
    }
    protected void setPlayerOneStyle() {
        SharedPreferences sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);

        p1.setPrimaryColor(sp.getInt(PRIMARY_COLOR, 0));
        p1.setDarken(sp.getInt(PRIMARY_COLOR_DARKEN, 0));
        p1.setPlayerName(sp.getString(PLAYER_NAME, ""));

    }
    protected void setPlayerTwoStyle() {
        SharedPreferences sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);

        p2.setPrimaryColor(sp.getInt(PRIMARY_COLOR, 0));
        p2.setDarken(sp.getInt(PRIMARY_COLOR_DARKEN, 0));
        p2.setPlayerName(sp.getString(PLAYER_NAME, ""));

    }


    abstract void updateBoard();
    abstract public void reset();
    abstract public void resetNoAnim();
    abstract public void gameOver();
    abstract public void saveGame();
    abstract public void setButtons();

    abstract public boolean isGameWon();
    abstract public boolean isGameFull();
    abstract public boolean isGameWonX();
    abstract public boolean isGameWonO();
    abstract public boolean isXTurn();



    public int getINDEX() {
        return INDEX;
    }

    public void setINDEX_stthm(int INDEX) {
        this.INDEX = INDEX;
    }

    public interface buttonListener {
        public void buttonPressed(int index);
        public void turnImagePressed();
        public void onActivityResultCalledMINE(int requestCode);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (buttonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mListener.onActivityResultCalledMINE(resultCode);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        else {
            updateBoard();
        }
    }



}
