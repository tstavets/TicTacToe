package com.noveltystudios.android.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tstavets on 6/10/17.
 */

public class Computer_SuperTTT {
    private static final String TAG = "TAGTIMMY";
    private ArrayList<SuperCoordinate> mBoardEncoded;
    private boolean mIsX;

    public Computer_SuperTTT() {
        mBoardEncoded = new ArrayList<SuperCoordinate>(0);

        mIsX = false;

        resetSuperCoordinates();


    }

    public void updateBoard(SuperBoard b, boolean isX) {
        int boardIndex = b.getLastMoveBoard();
        int tileIndex = b.getLastMoveTile();
        if (boardIndex != -1 && tileIndex != -1) {
            if (isX) mBoardEncoded.get(boardIndex).getCoordinate(tileIndex).setX(true);
            else mBoardEncoded.get(boardIndex).getCoordinate(tileIndex).setO(true);

            if (b.isBoardWonO(boardIndex)) mBoardEncoded.get(boardIndex).setO(true);
            if (b.isBoardWonX(boardIndex)) mBoardEncoded.get(boardIndex).setX(true);
        }
    }

    public SuperBoard moveRandom(SuperBoard b) {
        updateBoard(b, b.isXTurn());
        ArrayList<SuperMove> potentialMoves = getPotentialMoves(b);

        SuperMove supMove = potentialMoves.get( (int) (Math.random() * potentialMoves.size()) );

        b.move(supMove.getBoardNumber(), supMove.getTileNumber());
        return b;

    }

    public SuperBoard moveRanking(SuperBoard b) {

        updateBoard(b, b.isXTurn());
        ArrayList<SuperMove> potentialMoves = getPotentialMoves(b);
        //Log.d(TAG, "There are " + potentialMoves.size() + " potential Moves");

        for (int i = 0; i < potentialMoves.size(); i++) {
            scoreMove(potentialMoves.get(i), b.copy());
            //Log.d(TAG, "Move " + i + "/" + (potentialMoves.size()-1) +", is this: " + potentialMoves.get(i) + ", and has a score of " + potentialMoves.get(i).mScore);
        }

       // Log.d(TAG, "Sorting moves");
        Collections.sort(potentialMoves, new SuperMoveComparator());

        SuperMove ans = getOneOfBest(potentialMoves);
        b.move(ans.getBoardNumber(), ans.getTileNumber()); //Log.d(TAG, "Did a move");
      //  else Log.d(TAG, "That's not a real move . . . in Iceland");
        return b;

    }

    public void reset() {
        resetSuperCoordinates();
    }


    /**
     * Check each scoring thing to see if it actually works......
     * @param move
     * @param b
     */
    private void scoreMove(SuperMove move, SuperBoard b) {

        if (thisMoveWinsThisBoard(move, b.copy())) {
            move.changeScore(10 * boardPlacementScore(move.getBoardNumber()));
            if (thisMoveWinsGame(move, b.copy())) {
                move.changeScore(1000000);
            }
        }

        if (thisMoveSendsOpponentToWinnableBoard(move, b.copy())) {
            move.changeScore(-10 * boardPlacementScore(move.getTileNumber()));
            if (thisMoveLosesGame(move, b.copy())) {
                move.changeScore(-10000);
            }
        }

        if (thisMoveActivatesWholeBoard(move, b.copy())) {
            move.changeScore(-20);
        }

        if (thisMoveBlocksThemFromThisBoard(move, b.copy())) {
            move.changeScore(5);
        }

        if (thisMoveGetsUsCloserToThisBoard(move, b.copy())) {
            move.changeScore(3);
        }

        if (thisMoveFillsBoard(move, b.copy())) {
            if (moveBlocksUs(move, b.copy())) move.changeScore(-30);
            else if (moveBlocksThem(move, b.copy())) move.changeScore(30);
            else move.changeScore(-5);
        }


    }










































    private boolean thisMoveWinsThisBoard(SuperMove move, SuperBoard b) {
        b.move(move.getBoardNumber(), move.getTileNumber());
        if (b.isBoardWon(move.getBoardNumber())) return true;
        return false;
    }
    private boolean thisMoveWinsGame(SuperMove move, SuperBoard b) {
        b.move(move.getBoardNumber(), move.getTileNumber());
        if (b.isGameWon()) return true;
        return false;
    }
    private boolean thisMoveSendsOpponentToWinnableBoard(SuperMove move, SuperBoard b) {
        b.move(move.getBoardNumber(), move.getTileNumber());
        ArrayList<SuperMove> moves = getPotentialMoves(b);

        for (int i = 0; i < moves.size(); i++) {
            if (thisMoveWinsThisBoard(moves.get(i), b.copy())) return true;
        }
        return false;

    }
    private boolean thisMoveLosesGame(SuperMove move, SuperBoard b) {
        b.move(move.getBoardNumber(), move.getTileNumber());
        ArrayList<SuperMove> moves = getPotentialMoves(b);

        for (int i = 0; i < moves.size(); i++) {
            if (thisMoveWinsGame(moves.get(i), b.copy())) return true;
        }
        return false;
    }
    private boolean thisMoveActivatesWholeBoard(SuperMove move, SuperBoard b) {
        return  (b.isBoardWon(move.getTileNumber()) || b.isBoardFull(move.getTileNumber()));
    }
    private boolean thisMoveBlocksThemFromThisBoard(SuperMove move, SuperBoard sb) {
        Board b = sb.getBoards()[move.getBoardNumber()].copy();

        ArrayList<Integer> potentialMoves = getPotentialMoves(b.copy());
        b.setXTurn(!sb.isXTurn());

        for (int i = 0; i < potentialMoves.size(); i++) {
            Board temp = b.copy();
            temp.move(potentialMoves.get(i));

            if (temp.isGameWon()) {
                if (potentialMoves.get(i) == move.getTileNumber()) return true;
            }
        }
        return false;
    }
    private boolean thisMoveGetsUsCloserToThisBoard(SuperMove move, SuperBoard sb) {
        boolean xTurn = sb.isXTurn();
        sb.move(move.getBoardNumber(), move.getTileNumber());

        ArrayList<Integer> potentialMoves = getPotentialMoves(move.getBoardNumber());

        Board b = sb.getBoards()[move.getBoardNumber()];
        b.setXTurn(xTurn);

        for (int i = 0; i < potentialMoves.size(); i++) {
            Board temp = b.copy();
            temp.move(potentialMoves.get(i));
            if (temp.isGameWon()) return true;
        }

        return false;
    }
    private boolean thisMoveFillsBoard(SuperMove move, SuperBoard b) {
        b.move(move.getBoardNumber(), move.getTileNumber());
        return  b.isBoardFull(move.getBoardNumber());
    }
    private boolean moveBlocksUs(SuperMove move, SuperBoard b) {
        b.getBoards()[move.getBoardNumber()].setXWon(b.isXTurn());

        b.checkGameOver();

        return b.isGameWon();
    }
    private boolean moveBlocksThem(SuperMove move, SuperBoard b) {
        b.getBoards()[move.getBoardNumber()].setXWon(!b.isXTurn());

        b.checkGameOver();

        return b.isGameWon();
    }

    private int boardPlacementScore(int index) {
        if (index == 0 || index == 2 || index == 6 || index == 8) return 2;
        else if (index == 4) return 3;
        else return 1;
    }
    private SuperMove getOneOfBest(ArrayList<SuperMove> potentialMoves) {
        int bestScore = potentialMoves.get(0).getScore();
        int highestIndexWithBestScore = 0;

        for (int i = 0; i < potentialMoves.size(); i++) {
            if (potentialMoves.get(i).getScore() == bestScore) highestIndexWithBestScore = i;
        }
        int ans = (int) (Math.random() * highestIndexWithBestScore);
        //Log.d(TAG, "Choosing one of " + (highestIndexWithBestScore + 1) + " possible moves . . . Chose move #" + ans + ", " + potentialMoves.get(ans));
        return potentialMoves.get(ans);

    }
    private ArrayList<SuperMove> getPotentialMoves(SuperBoard b) {
        ArrayList<SuperMove> potentialMoves = new ArrayList<SuperMove>(0);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (b.isBoardActive(i)) {
                    if (!mBoardEncoded.get(i).getCoordinate(j).isX() && !mBoardEncoded.get(i).getCoordinate(j).isO()) {
                        potentialMoves.add( new SuperMove(i,j) );
                    }
                }
            }
        }
        return potentialMoves;
    }
    private ArrayList<Integer> getPotentialMoves(int index) {
        ArrayList<Integer> potentialMoves = new ArrayList<Integer>(0);

        for (int i = 0; i < 9; i++) {
            if (!mBoardEncoded.get(index).getCoordinate(i).isX() && !mBoardEncoded.get(index).getCoordinate(i).isO()) {
                potentialMoves.add(i);
            }
        }
        return potentialMoves;
    }
    private ArrayList<Integer> getPotentialMoves(Board b) {
        ArrayList<Integer> potentialMoves = new ArrayList<Integer>(0);

        for (int i = 0; i < 9; i++) {
            Board temp = b.copy();
            if (temp.move(i)) potentialMoves.add(i);
        }
        return potentialMoves;

    }

    private void resetSuperCoordinates() {
        mBoardEncoded.clear();
        for (int i = 0; i < 9; i++) {
            mBoardEncoded.add(new SuperCoordinate(i));
        }
    }


    private class SuperMove {
        private int mBoardNumber, mTileNumber, mScore;

        public SuperMove(int boardNumber, int tileNumber) {
            mBoardNumber = boardNumber;
            mTileNumber = tileNumber;
            mScore = 0;
        }

        public int getBoardNumber() {
            return mBoardNumber;
        }

        public void setBoardNumber(int boardNumber) {
            mBoardNumber = boardNumber;
        }

        public int getTileNumber() {
            return mTileNumber;
        }

        public void setTileNumber(int tileNumber) {
            mTileNumber = tileNumber;
        }

        public void changeScore(int delta) {
            mScore += delta;
        }

        public int getScore() { return mScore; }

        @Override
        public String toString() {
            String ans ="";
            ans = "{" + mBoardNumber + ", " + mTileNumber + "}";
            return ans;
        }
    }

    private class SuperMoveComparator implements Comparator<SuperMove> {
        @Override
        public int compare(SuperMove s1, SuperMove s2) {
            if ( s1.getScore()  > s2.getScore()) {
                return -1;
            }
            else if (s1.getScore() == s2.getScore()){
                return 0;
            }
            return 1;
        }
    }

}
