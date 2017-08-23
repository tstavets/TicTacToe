package com.noveltystudios.android.tictactoe;

import java.util.ArrayList;

/**
 * Created by tstavets on 5/30/17.
 */

public class Board {
    private Tile[] mTiles;
    private ArrayList<Integer> mXmoves;
    private boolean mGameWon, mGameFull, mXWon, mXTurn, mActive;
    private int mNumTurns, lastTurn;

    /**
     * Makes a new Board with nine tiles.
     * Chooses X goes first
     * All other booleans except for 'mActive' are false
     */
    public Board() {
        mXTurn = true;
        mGameWon = false;
        mGameFull = false;
        mXWon = false;
        mActive = true;
        mNumTurns = 0;
        lastTurn = -1;
        mXmoves = new ArrayList<Integer>(0);

        mTiles = new Tile[9];
        for (int i = 0; i < 9; i++) {
            mTiles[i] = new Tile();
        }
    }

    /**
     * At the index in the array of tiles, makes that tile either X or O depending on whose turn it is.
     * Returns true if that is a valid move, i.e. if the tile is open, the game is not over, and the game is active
     * returns false otherwise
     * @param index
     * @return
     */
    public boolean move(int index) {
        if(!isTileOpen(index) || mGameFull || mGameWon || !mActive) return false;

        lastTurn = index;
        setTileX(index, mXTurn);
        mNumTurns++;
        checkGameWon();
        checkGameFull();

        if(mXTurn) mXmoves.add(index);

        mXTurn = !mXTurn;
        return true;
    }


    /**
     * Sets mGamewon to gameWon
     * @param gameWon
     */
    public void setGameWon(boolean gameWon) {
        mGameWon = gameWon;
    }

    /**
     * Sets mGameFull to gameFull
     * @param gameFull
     */
    public void setGameFull(boolean gameFull) {
        mGameFull = gameFull;
    }

    /**
     * Sets mXWon to XWon, used mostly to copy
     * @param XWon
     */
    public void setXWon(boolean XWon) {
        mXWon = XWon;
    }

    /**
     * Sets mNumTurns to numTurns, used mostly to copy
     * @param numTurns
     */
    public void setNumTurns(int numTurns) {
        mNumTurns = numTurns;
    }

    /**
     * returns mXturn
     * @return
     */
    public boolean isXTurn(){
        return mXTurn;
    }

    public void setLastTurn(int x) {lastTurn = x;}

    /**
     * Returns mGameWon, does not recheck the board
     * @return
     */
    public boolean isGameWon() {
        return mGameWon;
    }

    /**
     * Returns mXwon AND mGameWon
     * @return
     */
    public boolean isGameWonByX() {
        return mXWon && mGameWon;
    }
    public boolean isGameWonByO() {
        return !mXWon && mGameWon;
    }

    /**
     * Returns mGameFull, does not recheck
     * @return
     */
    public boolean isGameFull() { return mGameFull; }

    /**
     * Returns mActive
     * @return
     */
    public boolean isActive() { return mActive; }

    /**
     * Returns true if the game is active and not full or won. Used in Super Tic Tac toe
     * @return
     */
    public boolean isOpen() { return (mActive && !mGameFull && !mGameWon); }

    /**
     * Returns true if the tile at the given index is x (isX == true, isOpen == false)
     * @param index
     * @return
     */
    public boolean isTileX(int index) {return mTiles[index].isX() && !mTiles[index].isOpen();}

    /**
     * Returns true if the tile at the given index is O (isX == false, isOpen == false)
     * @param index
     * @return
     */
    public boolean isTileO(int index) {return !mTiles[index].isX() && !mTiles[index].isOpen();}

    /**
     * returns mNumTurns
     * @return
     */
    public int getNumTurns() { return mNumTurns; }

    /**
     * Sets mXTurn to xTurn
     * @param xTurn
     */
    public void setXTurn(boolean xTurn) {
        mXTurn = xTurn;
    }

    /**
     * Returns true if the tile at the given index is open
     * @param index
     * @return
     */
    public boolean isTileOpen(int index){
        return mTiles[index].isOpen();
    }

    /**
     * Returns true only if the tile is taken by the same player whose turn it is,
     * so that tile is not open, and tile.isX == mXTurn
     * @param index
     * @return
     */
    private boolean isTileWon(int index){
        return !mTiles[index].isOpen() && ( mTiles[index].isX() == mXTurn );
    }

    /**
     * Sets a given tile to be xTurn (true for x, false for o)
     * Sets tiile.open to false and tile.isX to xTurn
     * @param index
     * @param xTurn
     */
    private void setTileX(int index, boolean xTurn){
        mTiles[index].setOpen(false);
        mTiles[index].setX(xTurn);
    }

    /**
     * Checks to see if the game is won, checks all horizonals, verticals, and diagonals.
     * Only checks it for mXTurn.
     * If the game is won, mGameWon = true, and mXWon = mXTurn
     */
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

    /**
     * returns true if numTurns ≥ 9. Cause then there have been nine turns
     */
    private void checkGameFull(){
        if (mNumTurns >= 9) mGameFull = true;
    }

    /**
     * Sets mActive to b
     * @param b
     */
    protected void setActive(boolean b) { mActive = b; }

    /**
     * Creats a board from a given boardAray. sets mXTurn to xTurn
     * Does not check to see if game is won or full, so those booleans are set to false.
     * @param boardArray
     * @param xTurn
     */
    public Board(int[] boardArray, boolean xTurn) {
        mGameWon = false;
        mGameFull = false;
        mXWon = false;
        mActive = true;
        mNumTurns = 0;
        mTiles = new Tile[9];
        for (int i = 0; i < 9; i++) {
            mTiles[i] = new Tile();
        }

        mXTurn = xTurn;
        for (int i = 0; i < 9; i++) {
            if (boardArray[i] == -1) {
                mTiles[i].setX(false);
                mTiles[i].setOpen(false);
            }
            else if (boardArray[i] == 0) {
                mTiles[i].setOpen(true);
            }
            else if (boardArray[i] == 1) {
                mTiles[i].setX(true);
                mTiles[i].setOpen(false);
            }
        }
    }

    /**
     * Returns a Board that has all of the same traits as mBoard
     * @return
     */
    public Board copy() {
        Board ans = new Board();
        ans.setActive(mActive);
        ans.setXTurn(mXTurn);
        ans.setGameFull(mGameFull);
        ans.setGameWon(mGameWon);
        ans.setNumTurns(mNumTurns);
        ans.setLastTurn(lastTurn);
        ans.setXWon(mXWon);
        for (int i = 0; i < mTiles.length; i++) {
            if (!mTiles[i].isOpen()) {
                ans.setTileX(i, mTiles[i].isX());
            }
        }
        return ans;
    }

    /**
     * Returns 1 if game is won by b
     * returns -1 if game is won by !b
     * returns 0 otherwise
     * @param b
     * @return
     */
    public int getScore(boolean b) {
        if (isGameWon() && isGameWonByX() == b) return 1;
        if (isGameWon() && isGameWonByX() != b) return -1;
        return 0;
    }

    /**
     * Reruns the constructor
     */
    public void reset(){
        double x = Math.random();
        if(x >= 0.5) mXTurn = true;
        else mXTurn = false;
        mGameWon = false;
        mGameFull = false;
        mXWon = false;
        mActive = true;
        mNumTurns = 0;
        mXmoves.clear();

        mTiles = new Tile[9];
        for (int i = 0; i < 9; i++) {
            mTiles[i] = new Tile();
        }
    }


    public ArrayList<Integer> getXs() {
        ArrayList<Integer> ans = new ArrayList<Integer>(0);
        for (int i = 0; i < 9; i++) {
            if(!mTiles[i].isOpen() && mTiles[i].isX()) ans.add(i);
        }
        return ans;
    }
    public ArrayList<Integer> getXsEncoded() {
        ArrayList<Integer> ans = getXs();

        for (int i = 0; i < ans.size(); i++) {
            int index = ans.get(i);

            if (index == 0 || index == 2 || index == 6 || index == 8) ans.set(i, -1);
            else if (index == 1 || index == 3 || index == 5 || index == 7) ans.set(i, 1);
            else ans.set(i, 0);
        }
        return ans;
    }
    public ArrayList<Integer> getOs() {
        ArrayList<Integer> ans = new ArrayList<Integer>(0);
        for (int i = 0; i < 9; i++) {
            if(!mTiles[i].isOpen() && !mTiles[i].isX()) ans.add(i);
        }
        return ans;
    }
    public int getLastTurn() { return lastTurn; }



    public boolean equalsBoard(Board b) {
        boolean test = (b.isTileO(0) == isTileO(0));
        for(int i = 0; i < 9; i++) {
            if ( (b.isTileO(i) == isTileO(i)) != test) return false;
            if ( (b.isTileX(i) == isTileX(i)) != test) return false;
        }
        return true;
    }

    public boolean equalsBoardOnRotation(Board board) {
        Board b = board.copy();
        for (int i = 0; i < 4; i++) {
            if (equalsBoard(b)) return true;
            b.rotateBoard();
        }
        return false;
    }

    public boolean eqaualsBoardOnReflection(Board board) {
        Board b = board.copy();

        if (equalsBoard(b)) return true;

        b.reflectBoardX();

        if (equalsBoard(b)) return true;

        b.reflectBoardX();
        b.reflectBoardY();

        if (equalsBoard(b)) return true;

        return false;

    }

    public boolean equalsBoardOnReflectionOrRotation(Board b) {
        if (equalsBoardOnRotation(b) || eqaualsBoardOnReflection(b)) return true;
        return false;
    }

    public void rotateBoard() {
        Tile[] copies = new Tile[9];
        for (int i = 0; i < 9; i++) {
            copies[i] = mTiles[i].copy();
        }

        mTiles[0] = copies[6];
        mTiles[1] = copies[3];
        mTiles[2] = copies[0];

        mTiles[3] = copies[7];
        mTiles[4] = copies[4];
        mTiles[5] = copies[1];

        mTiles[6] = copies[8];
        mTiles[7] = copies[5];
        mTiles[8] = copies[2];
    }
    public void reflectBoardY() {
        switchTiles(0,2);
        switchTiles(3,5);
        switchTiles(6,8);
    }
    public void reflectBoardX() {
        switchTiles(0,6);
        switchTiles(1,7);
        switchTiles(8,2);
    }
    public void switchTiles(int indexA, int indexB) {
        Tile temp = mTiles[indexA].copy();
        mTiles[indexA] = mTiles[indexB];
        mTiles[indexB] = temp;
    }

    public ArrayList<Integer> getXmoves() {
        return mXmoves;
    }
}
