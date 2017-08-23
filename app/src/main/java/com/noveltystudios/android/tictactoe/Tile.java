package com.noveltystudios.android.tictactoe;

/**
 * Created by tstavets on 5/30/17.
 */

public class Tile {
    private boolean mIsX, mIsOpen;

    public Tile(){
        mIsOpen = true;
        mIsX = true;
    }

    public boolean isX() {
        return mIsX;
    }

    public void setX(boolean x) {
        mIsX = x;
    }

    public boolean isOpen() {
        return mIsOpen;
    }

    public void setOpen(boolean open) {
        mIsOpen = open;
    }

    public Tile copy() {
        Tile ans = new Tile();
        ans.setOpen(mIsOpen);
        ans.setX(mIsX);
        return ans;
    }
}
