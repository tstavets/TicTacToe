package com.noveltystudios.android.tictactoe;

/**
 * Created by tstavets on 6/5/17.
 */

public class SuperBoard {
    Board[] mBoards;
    private boolean mGameWon, mGameFull, mXWon, mXTurn;
    private int lastMoveBoard, lastMoveTile, lastMoveIndex;
    private int mNumTurns;


    public SuperBoard() {
        mXTurn = true;
        mGameWon = false;
        mGameFull = false;
        mXWon = false;
        mNumTurns = 0;
        lastMoveBoard = -1;
        lastMoveTile = -1;
        lastMoveIndex = -1;

        mBoards = new Board[9];
        for (int i =0; i < 9; i++) {
            mBoards[i] = new Board();
            mBoards[i].setXTurn(mXTurn);
        }
    }

    public boolean move(int index) {
        int boardNumber = getBoardNumber(index);
        int tileNumber = getTileNumber(index);
        return  move(boardNumber, tileNumber);
    }

    public boolean move(int boardNumber, int tileNumber) {
        if (!mGameFull && !mGameWon) {
            if (mBoards[boardNumber].move(tileNumber)) {
                mNumTurns++;
                deactivateAll();
                checkGameOver();
                activateBoard(tileNumber);
                mXTurn = !mXTurn;
                setXTurnForBoards();
                lastMoveBoard = boardNumber;
                lastMoveTile = tileNumber;
                return true;
            }
        }
        return false;
    }

    public void deactivateAll() {
        for (int i = 0; i < 9; i++) {
            mBoards[i].setActive(false);
        }
    }
    public void activateAll() {
        for (int i = 0; i < 9; i++) {
            mBoards[i].setActive(true);
        }
    }

    public void checkGameOver() {
        checkGameWon();
        checkGameFull();
    }

    private void checkGameWon(){
        if (isTileWon(0) && isTileWon(1) && isTileWon(2)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(3) && isTileWon(4) && isTileWon(5)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(6) && isTileWon(7) && isTileWon(8)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(0) && isTileWon(3) && isTileWon(6)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(1) && isTileWon(4) && isTileWon(7)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(2) && isTileWon(5) && isTileWon(8)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(0) && isTileWon(4) && isTileWon(8)){ mGameWon = true; mXWon = mXTurn; }
        else if (isTileWon(2) && isTileWon(4) && isTileWon(6)){ mGameWon = true; mXWon = mXTurn; }
    }
    private boolean isTileWon(int index) {
        return (mBoards[index].isGameWon() && (mBoards[index].isGameWonByX() == mXTurn));
    }

    private void checkGameFull() {
        mGameFull = true;
        for (int i = 0; i < 9; i++) {
            if( !(mBoards[i].isGameWon() || mBoards[i].isGameFull()) ) mGameFull = false;
        }
    }

    public void activateBoard(int index) {
        if (mBoards[index].isGameFull() || mBoards[index].isGameWon()) activateAll();
        else mBoards[index].setActive(true);
    }

    private void setXTurnForBoards() {
        for (int i = 0; i < 9; i++) {
            mBoards[i].setXTurn(mXTurn);
        }
    }

    public boolean isGameWon() {
        return mGameWon;
    }

    public boolean isGameFull() {
        return mGameFull;
    }

    public boolean isGameOver() {
        return  mGameWon || mGameFull;
    }

    public boolean isXWon() {
        return mXWon;
    }

    public boolean isXTurn() {
        return mXTurn;
    }

    public int getNumTurns() {
        return mNumTurns;
    }

    public void setXTurn(boolean b) {
        mXTurn = b;
        setXTurnForBoards();
    }

    public boolean isTileActive(int boardNumber) {
        return isBoardActive(boardNumber);
    }
    public boolean isBoardActive(int boardNumber) {
        if(mBoards[boardNumber].isOpen()) return true;
        return false;
    }
    public boolean isTileWonX(int boardNumber, int tileNumber) {
        return  mBoards[boardNumber].isTileX(tileNumber);
    }
    public boolean isTileWonO(int boardNumber, int tileNumber) {
        return mBoards[boardNumber].isTileO(tileNumber);
    }
    public boolean isBoardWonX(int index) {
        return (mBoards[index].isGameWonByX() && mBoards[index].isGameWon());
    }
    public boolean isBoardWonO(int index) {
        return (!mBoards[index].isGameWonByX() && mBoards[index].isGameWon());
    }
    public boolean isBoardWon(int index) {
        return ( isBoardWonX(index) || isBoardWonO(index) );
    }
    public boolean isBoardFull(int index) { return mBoards[index].isGameFull(); }

    public int getBoardNumber(int index) {
        if (index == 0  || index == 1  || index == 2  || index == 9  || index == 10 || index == 11 || index == 18 || index == 19 || index == 20) return 0;
        if (index == 3  || index == 4  || index == 5  || index == 12 || index == 13 || index == 14 || index == 21 || index == 22 || index == 23) return 1;
        if (index == 6  || index == 7  || index == 8  || index == 15 || index == 16 || index == 17 || index == 24 || index == 25 || index == 26) return 2;
        if (index == 27 || index == 28 || index == 29 || index == 36 || index == 37 || index == 38 || index == 45 || index == 46 || index == 47) return 3;
        if (index == 30 || index == 31 || index == 32 || index == 39 || index == 40 || index == 41 || index == 48 || index == 49 || index == 50) return 4;
        if (index == 33 || index == 34 || index == 35 || index == 42 || index == 43 || index == 44 || index == 51 || index == 52 || index == 53) return 5;
        if (index == 54 || index == 55 || index == 56 || index == 63 || index == 64 || index == 65 || index == 72 || index == 73 || index == 74) return 6;
        if (index == 57 || index == 58 || index == 59 || index == 66 || index == 67 || index == 68 || index == 75 || index == 76 || index == 77) return 7;
        if (index == 60 || index == 61 || index == 62 || index == 69 || index == 70 || index == 71 || index == 78 || index == 79 || index == 80) return 8;
        return -1;
    }
    public int getTileNumber(int index) {
        if (index == 0  || index == 3  || index == 6  || index == 27 || index == 30 || index == 33 || index == 54 || index == 57 || index == 60) return 0;
        if (index == 1  || index == 4  || index == 7  || index == 28 || index == 31 || index == 34 || index == 55 || index == 58 || index == 61) return 1;
        if (index == 2  || index == 5  || index == 8  || index == 29 || index == 32 || index == 35 || index == 56 || index == 59 || index == 62) return 2;
        if (index == 9  || index == 12 || index == 15 || index == 36 || index == 39 || index == 42 || index == 63 || index == 66 || index == 69) return 3;
        if (index == 10 || index == 13 || index == 16 || index == 37 || index == 40 || index == 43 || index == 64 || index == 67 || index == 70) return 4;
        if (index == 11 || index == 14 || index == 17 || index == 38 || index == 41 || index == 44 || index == 65 || index == 68 || index == 71) return 5;
        if (index == 18 || index == 21 || index == 24 || index == 45 || index == 48 || index == 51 || index == 72 || index == 75 || index == 78) return 6;
        if (index == 19 || index == 22 || index == 25 || index == 46 || index == 49 || index == 52 || index == 73 || index == 76 || index == 79) return 7;
        if (index == 20 || index == 23 || index == 26 || index == 47 || index == 50 || index == 53 || index == 74 || index == 77 || index == 80) return 8;
        return -1;
    }
    public int getLastMoveIndex() {
        int ans = 0;
        int b = 0;
        int t = 0;


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                b = j + 3*(i/3);
                for (int k = 0; k < 3; k++) {
                    t = k + 3*(i%3);
                    if (lastMoveBoard == b && lastMoveTile == t) return ans;
                    ans++;
                }
            }
        }
        return -1;

    }

    public int getLastMoveBoard() {
        return lastMoveBoard;
    }
    public int getLastMoveTile() {
        return lastMoveTile;
    }


    public SuperBoard copy() {
        SuperBoard ans = new SuperBoard();

        Board[] boardCopies = new Board[9];
        for (int i = 0; i < 9; i++) {
            boardCopies[i] = mBoards[i].copy();
        }
        ans.setBoards(boardCopies);
        ans.setGameFull(mGameFull);
        ans.setGameWon(mGameWon);
        ans.setLastMoveBoard(lastMoveBoard);
        ans.setLastMoveTile(lastMoveTile);
        ans.setNumTurns(mNumTurns);
        ans.setXTurn(mXTurn);
        ans.setXTurnForBoards();
        ans.setXWon(mXWon);

        return ans;
    }

    public Board[] getBoards() {
        return mBoards;
    }

    public void setBoards(Board[] boards) {
        mBoards = boards;
    }

    public void setGameWon(boolean gameWon) {
        mGameWon = gameWon;
    }

    public void setGameFull(boolean gameFull) {
        mGameFull = gameFull;
    }

    public void setXWon(boolean XWon) {
        mXWon = XWon;
    }

    public void setLastMoveBoard(int lastMoveBoard) {
        this.lastMoveBoard = lastMoveBoard;
    }

    public void setLastMoveTile(int lastMoveTile) {
        this.lastMoveTile = lastMoveTile;
    }

    public void setNumTurns(int numTurns) {
        mNumTurns = numTurns;
    }

    public int getScore(boolean b) {
        if (isGameWon() && mXWon == b) return 1;
        if (isGameWon() && mXWon != b) return -1;
        return 0;
    }

}
