package com.noveltystudios.android.tictactoe;

import java.util.ArrayList;

/**
 * Created by tstavets on 6/10/17.
 */

public class SuperCoordinate extends Coordinate {

    private ArrayList<Coordinate> mSubCoordinates;
    private boolean isFull;


    public SuperCoordinate(double X, double Y) {
        super(X,Y);
        isFull = false;
        mSubCoordinates = new ArrayList<Coordinate>(0);
        resetSubCoordinates();
    }
    public SuperCoordinate(int index) {
        super(index);
        isFull = false;
        mSubCoordinates = new ArrayList<Coordinate>(0);
        resetSubCoordinates();
    }

    public Coordinate getCoordinate(int index) {
        return mSubCoordinates.get(index);
    }

    @Override
    public void setX(boolean x) {
        super.setX(x);
        for (int i = 0; i < 9; i++) {
            mSubCoordinates.get(i).setX(x);
        }
    }

    @Override
    public void setO(boolean o) {
        super.setO(o);
        for (int i = 0; i < 9; i++) {
            mSubCoordinates.get(i).setO(o);
        }
    }



    public void resetSubCoordinates() {
        mSubCoordinates.clear();
        for (int i = 0; i < 9; i++) mSubCoordinates.add(new Coordinate(i));
    }


}
