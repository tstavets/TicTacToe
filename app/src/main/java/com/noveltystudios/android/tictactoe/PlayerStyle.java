package com.noveltystudios.android.tictactoe;

/**
 * Created by tstavets on 7/8/17.
 */

public class PlayerStyle {

    private int mPrimaryColor,mDarken;
    private String mPlayerName;

    public PlayerStyle(int primaryColor, int darken, String playerName) {
        mPrimaryColor = primaryColor;
        mPlayerName = playerName;
        mDarken = darken;
    }
    public PlayerStyle() {
        mPrimaryColor = 0;
        mDarken = 0;
        mPlayerName = "";
    }

    public int getPrimaryColor() {
        return mPrimaryColor;
    }

    public void setPrimaryColor(int primaryColor) {
        mPrimaryColor = primaryColor;
    }

    public int getDarken() {
        return mDarken;
    }

    public void setDarken(int darken) {
        mDarken = darken;
    }

    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String playerName) {
        mPlayerName = playerName;
    }
}
