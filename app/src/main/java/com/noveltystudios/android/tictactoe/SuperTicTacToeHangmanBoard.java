package com.noveltystudios.android.tictactoe;


import java.util.ArrayList;

/**
 * Created by tstavets on 7/11/17.
 */

public class SuperTicTacToeHangmanBoard {


    private Board[] mBoards; private HangmanModel[] mHangmanModels;
    private boolean mGameWon, mGameFull, mXWon, mXTurn, mBoardPickingState, mSTALEMATE;
    private int lastMoveBoard, lastMoveTile, lastMoveIndex;
    private int mNumTurns;

    public SuperTicTacToeHangmanBoard() {
        mXTurn = true;
        mGameWon = false;
        mGameFull = false;
        mXWon = false;
        mSTALEMATE = false;
        mBoardPickingState = false;
        mNumTurns = 0;
        lastMoveBoard = -1;
        lastMoveTile = -1;
        lastMoveIndex = -1;

        mBoards = new Board[9];
        mBoards[0] = new Board();
        mBoards[1] = new Board();
        mBoards[2] = null;
        mBoards[3] = new Board();
        mBoards[4] = new Board();
        mBoards[5] = new Board();
        mBoards[6] = null;
        mBoards[7] = new Board();
        mBoards[8] = new Board();

        mHangmanModels = new HangmanModel[2];
        mHangmanModels[0] = new HangmanModel();
        mHangmanModels[1] = new HangmanModel();
    }

    public void setXWord(String word) {
        mHangmanModels[1].chooseTheWord(word);
    }
    public void setOWord(String word) {
        mHangmanModels[0].chooseTheWord(word);
    }

    public boolean isBoardPickingState() {
        return mBoardPickingState;
    }

    public boolean move(int boardNum, int tileNum) {
        if (!mGameFull && !mGameWon) {
            if (mBoards[boardNum].move(tileNum)) {
                lastMoveBoard = boardNum;
                lastMoveTile = tileNum;
                mXTurn = !mXTurn;
                setXTurnForBoards();
                isGameOver();
                deactivateAll();
                activateBoards();
                return true;
            }
        }
        return false;
    }
    public boolean xGuess(String guess) {
       boolean ans = false;
        if (mSTALEMATE) mHangmanModels[0].setActive(true);
        if (mHangmanModels[0].guess(guess)) {
            if (!mSTALEMATE) {
                ans = true;
                activateAll();
                mBoardPickingState = true;
            }
            else {
                ans = true;
                activateAll();
            }
        }
        else {
            ans = false;
            activateAll();
            mXTurn = !mXTurn;
            setXTurnForBoards();
        }
        deactivate(2);
        return ans;
    }
    public boolean oGuess(String guess) {
        boolean ans = false;
        if (mSTALEMATE) mHangmanModels[1].setActive(true);
        if (mHangmanModels[1].guess(guess)) {
            if (!mSTALEMATE) {
                ans = true;
                activateAll();
                mBoardPickingState = true;
            }
            else {
                ans = true;
                activateAll();
            }
        }
        else {
            ans = false;
            activateAll();
            mXTurn = !mXTurn;
            setXTurnForBoards();
        }
        deactivate(6);
        return ans;
    }

    public boolean selectBoard(int index) {
        if (index == 2) {
            if (!mHangmanModels[0].isGameOver()) {
                deactivateAll();
                mHangmanModels[0].setActive(true);
                mXTurn = !mXTurn;
                setXTurnForBoards();
                return true;
            }
        }
        else if (index == 6) {
            if (!mHangmanModels[1].isGameOver()) {
                deactivateAll();
                mHangmanModels[1].setActive(true);
                mXTurn = !mXTurn;
                setXTurnForBoards();
                return true;
            }
        }
        else if (!mBoards[index].isGameWon() && !mBoards[index].isGameFull()) {
            deactivateAll();
            mBoards[index].setActive(true);
            mXTurn = !mXTurn;
            setXTurnForBoards();
            return true;
        }
        return false;
    }

    public void isGameOver() {
        isGameWonX();
        isGameWonO();
        isGameFull();
    }
    public void isGameWonX() {
            if (mBoards[0].isGameWonByX()           && mBoards[1].isGameWonByX() && mHangmanModels[0].isGameWon()){ mGameWon = true; mXWon = true; }
            else if (mBoards[3].isGameWonByX()      && mBoards[4].isGameWonByX() && mBoards[5].isGameWonByX()){ mGameWon = true; mXWon = true; }
            else if (mHangmanModels[1].isGameLost() && mBoards[7].isGameWonByX() && mBoards[8].isGameWonByX()){ mGameWon = true; mXWon = true; }
            else if (mBoards[0].isGameWonByX()      && mBoards[3].isGameWonByX() && mHangmanModels[1].isGameLost()){ mGameWon = true; mXWon = true; }
            else if (mBoards[1].isGameWonByX()      && mBoards[4].isGameWonByX() && mBoards[7].isGameWonByX()){ mGameWon = true; mXWon = true; }
            else if (mHangmanModels[0].isGameWon()  && mBoards[5].isGameWonByX() && mBoards[8].isGameWonByX()){ mGameWon = true; mXWon = true; }
            else if (mBoards[0].isGameWonByX()      && mBoards[4].isGameWonByX() && mBoards[8].isGameWonByX()){ mGameWon = true; mXWon = true; }
            else if (mHangmanModels[0].isGameWon()  && mBoards[4].isGameWonByX() && mHangmanModels[1].isGameLost()){ mGameWon = true; mXWon = true; }
        }
    public void isGameWonO() {
            if (mBoards[0].isGameWonByO()           && mBoards[1].isGameWonByO() && mHangmanModels[0].isGameLost()){ mGameWon = true; mXWon = false; }
            else if (mBoards[3].isGameWonByO()      && mBoards[4].isGameWonByO() && mBoards[5].isGameWonByO()){ mGameWon = true; mXWon = false; }
            else if (mHangmanModels[1].isGameWon()  && mBoards[7].isGameWonByO() && mBoards[8].isGameWonByO()){ mGameWon = true; mXWon = false; }
            else if (mBoards[0].isGameWonByO()      && mBoards[3].isGameWonByO() && mHangmanModels[1].isGameWon()){ mGameWon = true; mXWon = false; }
            else if (mBoards[1].isGameWonByO()      && mBoards[4].isGameWonByO() && mBoards[7].isGameWonByO()){ mGameWon = true; mXWon = false; }
            else if (mHangmanModels[0].isGameLost() && mBoards[5].isGameWonByO() && mBoards[8].isGameWonByO()){ mGameWon = true; mXWon = false; }
            else if (mBoards[0].isGameWonByO()      && mBoards[4].isGameWonByO() && mBoards[8].isGameWonByO()){ mGameWon = true; mXWon = false; }
            else if (mHangmanModels[0].isGameLost() && mBoards[4].isGameWonByO() && mHangmanModels[1].isGameWon()){ mGameWon = true; mXWon = false; }
    }
    public boolean isGameFull() {
        int x = 0;
        if (!mBoards[0].isGameWon() && !mBoards[0].isGameFull()) x++;
        if (!mBoards[1].isGameWon() && !mBoards[1].isGameFull()) x++;
        if (!mHangmanModels[0].isGameOver()) x++;
        if (!mBoards[3].isGameWon() && !mBoards[3].isGameFull()) x++;
        if (!mBoards[4].isGameWon() && !mBoards[4].isGameFull()) x++;
        if (!mBoards[5].isGameWon() && !mBoards[5].isGameFull()) x++;
        if (!mHangmanModels[1].isGameOver()) x++;
        if (!mBoards[7].isGameWon() && !mBoards[7].isGameFull()) x++;
        if (!mBoards[8].isGameWon() && !mBoards[8].isGameFull()) x++;

        return x == 0;
    }

    public boolean isBoardWonX(int boardNum) {
        if (boardNum == 2) {
            return mHangmanModels[0].isGameWon();
        }
        if (boardNum == 6) {
            return mHangmanModels[1].isGameLost();
        }
        return mBoards[boardNum].isGameWonByX();
    }
    public boolean isBoardWonO(int boardNum) {
        if (boardNum == 2) {
            return mHangmanModels[0].isGameLost();
        }
        if (boardNum == 6) {
            return mHangmanModels[1].isGameWon();
        }
        return mBoards[boardNum].isGameWonByO();
    }

    public void deactivateAll() {
        mHangmanModels[0].setActive(false);
        mHangmanModels[1].setActive(false);

        mBoards[0].setActive(false);
        mBoards[1].setActive(false);
        mBoards[3].setActive(false);
        mBoards[4].setActive(false);
        mBoards[5].setActive(false);
        mBoards[7].setActive(false);
        mBoards[8].setActive(false);
    }
    public void activateAll() {
        mHangmanModels[0].setActive(true);
        mHangmanModels[1].setActive(true);

        mBoards[0].setActive(true);
        mBoards[1].setActive(true);
        mBoards[3].setActive(true);
        mBoards[4].setActive(true);
        mBoards[5].setActive(true);
        mBoards[7].setActive(true);
        mBoards[8].setActive(true);
    }
    public void deactivate(int index) {
        if (index == 2) {
            mHangmanModels[0].setActive(false);
        }
        else if (index == 6) {
            mHangmanModels[1].setActive(false);
        }
        else {
            mBoards[index].setActive(false);
        }
    }
    public void activateBoards() {
        if (mBoardPickingState) activateAll();
        else if (lastMoveTile == 6 || lastMoveTile == 2) {
            if (mXTurn) {
                if (!mHangmanModels[0].isGameOver()) {
                    mHangmanModels[0].setActive(true);
                }
                else {
                    activateAll();
                    deactivate(6);
                }
            }
            else {
                if (!mHangmanModels[1].isGameOver()) {
                    mHangmanModels[1].setActive(true);
                }
                else {
                    activateAll();
                    deactivate(2);
                }
            }
        }
        else {
            if (mBoards[lastMoveTile].isGameFull() || mBoards[lastMoveTile].isGameWon()) {
                activateAll();

                if(mXTurn) deactivate(6);
                else deactivate(2);
            }
            else mBoards[lastMoveTile].setActive(true);
        }
    }

    public int[] getToActivate() {
        if (stalemate()) {
            ArrayList<Integer> temp = new ArrayList<>(0);

            if (!mHangmanModels[0].isGameOver() && mHangmanModels[0].isGameStarted()) {
                temp.add(2);
                mXTurn = true;
                setXTurnForBoards();
            }
            else if (!mHangmanModels[1].isGameOver() && mHangmanModels[1].isGameStarted()) {
                temp.add(6);
                mXTurn = false;
                setXTurnForBoards();
            }

            int[] ans = new int[temp.size()];

            for (int i = 0; i < temp.size(); i++) ans[i] = temp.get(i);

            return ans;


        }



        ArrayList<Integer> temp = new ArrayList<>(0);


        if (mBoards[0].isActive() && !mBoards[0].isGameFull()) temp.add(0);
        if (mBoards[1].isActive() && !mBoards[1].isGameFull()) temp.add(1);
        if (mHangmanModels[0].isActive()) temp.add(2);
        if (mBoards[3].isActive() && !mBoards[3].isGameFull()) temp.add(3);
        if (mBoards[4].isActive() && !mBoards[4].isGameFull()) temp.add(4);
        if (mBoards[5].isActive() && !mBoards[5].isGameFull()) temp.add(5);
        if (mHangmanModels[1].isActive()) temp.add(6);
        if (mBoards[7].isActive() && !mBoards[7].isGameFull()) temp.add(7);
        if (mBoards[8].isActive() && !mBoards[8].isGameFull()) temp.add(8);


        int[] ans = new int[temp.size()];

        for (int i = 0; i < temp.size(); i++) ans[i] = temp.get(i);

        return ans;

    }


    private boolean stalemate() {
        if (mBoards[0].isGameWon() || mBoards[0].isGameFull()) {
            if (mBoards[1].isGameWon() || mBoards[1].isGameFull()) {
                if (mBoards[3].isGameWon() || mBoards[3].isGameFull()) {
                    if (mBoards[4].isGameWon() || mBoards[4].isGameFull()) {
                        if (mBoards[5].isGameWon() || mBoards[5].isGameFull()) {
                            if (mBoards[7].isGameWon() || mBoards[7].isGameFull()) {
                                if (mBoards[8].isGameWon() || mBoards[8].isGameFull()) {


                                    if (!mHangmanModels[0].isGameOver() && mHangmanModels[1].isGameOver()) {
                                        mSTALEMATE = true;
                                        return true;
                                    }
                                    else if (mHangmanModels[0].isGameOver() && !mHangmanModels[1].isGameOver()) {
                                        mSTALEMATE = true;
                                        return true;
                                    }


                                }}}}}}}
        return false;
    }
    public  boolean STALEMATE() { return mSTALEMATE; }

    private void setXTurnForBoards() {
        mBoards[0].setXTurn(mXTurn);
        mBoards[1].setXTurn(mXTurn);
        mBoards[3].setXTurn(mXTurn);
        mBoards[4].setXTurn(mXTurn);
        mBoards[5].setXTurn(mXTurn);
        mBoards[7].setXTurn(mXTurn);
        mBoards[8].setXTurn(mXTurn);

    }
    public void setXTurn(Boolean xTurn) {
        mXTurn = xTurn;
        setXTurnForBoards();
    }
    public boolean isXTurn() { return mXTurn; }

    public void setBoardPickingState(boolean boardPickingState) {
        mBoardPickingState = boardPickingState;
    }

    public boolean isGameWon() {
        return mGameWon;
    }

    public boolean isXWon() {
        return mXWon;
    }



    public HangmanModel[] getHangmanModels() {
        return mHangmanModels;
    }
}




