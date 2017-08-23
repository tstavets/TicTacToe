package com.noveltystudios.android.tictactoe;

/**
 * Created by tstavets on 6/8/17.
 */

public class Coordinate {
    protected double mX, mY;
    protected boolean mIsX, mIsO;

    public Coordinate(double x, double y) {
        mX = x;
        mY = y;
        mIsX = false;
        mIsO = false;
    }
    public Coordinate(int index){
        mX = convertFromIndexToCoordinate(index).getX();
        mY = convertFromIndexToCoordinate(index).getY();

        mIsX = false;
        mIsO = false;
    }


    public double getX() {
        return mX;
    }

    public void setXCoor(double x) {
        mX = x;
    }

    public double getY() {
        return mY;
    }

    public void setYCoor(double y) {
        mY = y;
    }


    public Coordinate getNextInLine(Coordinate a) {

        double dx = Math.abs(mX - a.getX());
        double newX = a.getX() + dx;
        wrap(newX);

        double dy = Math.abs(mY - a.getY());
        double newY = a.getY() + dy;
        wrap(newY);

        return new Coordinate(newX, newY);
    }

    public Coordinate getMidPoint(Coordinate a) {

        double newX = (a.getX() + mX) / 2;
        double newY = (a.getY() + mY) / 2;

        return new Coordinate(newX, newY);


    }

    public Coordinate convertFromIndexToCoordinate(int index) {
        double newX = 0;
        double newY = 0;

        if (index == 0 || index == 3 || index == 6) newX = -1;
        else if (index == 2 || index == 5 || index == 8) newX = 1;

        if (index == 0 || index == 1 || index == 2) newY = 1;
        else if (index == 6 || index == 7 || index == 8) newY = -1;

        return new Coordinate(newX, newY);
    }

    public int convertFromCoordinateToIndex() {
        if (mY == 1) {
            for(int i = -1; i < 2; i++) if(mX == i) return (i + 1);
        }
        else if (mY == 0) {
            for(int i = -1; i < 2; i++) if(mX == i) return (i + 1 + 3);
        }
        else if (mY == -1) {
            for(int i = -1; i < 2; i++) if(mX == i) return (i + 1 + 6);
        }
        return -1;
    }

    public boolean sameX(Coordinate a) {
        return a.getX() == mX;
    }

    public boolean sameY(Coordinate a) {
        return a.getY() == mY;
    }

    public boolean sameXorY(Coordinate a) { return sameX(a) || sameY(a); }

    public boolean onDiagonal(Coordinate a) {
        double dx = Math.abs(a.getX() - mX);
        double dy = Math.abs(a.getY() - mY);

        return  (dx == dy);
    }

    public boolean isCorner(){
        return Math.abs(mX) == Math.abs(mY);
    }

    public boolean isOuterMiddle() {
        return Math.abs(mX) + Math.abs(mY) == 1;
    }

    public boolean isCenter() {
        return  ( mX == mY && mX == 0.0 ) ;
    }

    public boolean sameRelativeLocation(Coordinate a) {
        if(isCenter() != a.isCenter()) return false;
        if(isCorner() != a.isCorner()) return false;
        if(isOuterMiddle() != a.isOuterMiddle()) return false;
        return true;
    }

    public void wrap(double coor) {
        while (coor > 1) {
            coor -= 3;
        }
        while (coor < -1) {
            coor += 3;
        }
    }

    public boolean isX() {
        return mIsX;
    }

    public void setX(boolean x) {
        mIsX = x;
    }

    public boolean isO() {
        return mIsO;
    }

    public void setO(boolean o) {
        mIsO = o;
    }

    @Override
    public boolean equals(Object a){

        if(((Coordinate) a).getX() == mX && ((Coordinate) a).getY() == mY) return true;
        return false;

    }

    @Override
    public String toString() {
        return "(" + mX + ", " + mY + ")";
    }


}
