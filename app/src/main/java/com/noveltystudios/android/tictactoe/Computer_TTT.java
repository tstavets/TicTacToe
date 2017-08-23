package com.noveltystudios.android.tictactoe;

import java.util.ArrayList;

/**
 * Created by tstavets on 6/5/17.
 */

public class Computer_TTT {
    private static final String TAG = "TAGTIMMY";

    private boolean mIsX;


    public Computer_TTT(Board board) {
        mIsX = true;

    }


    public Board randomMove(Board b) {
        while (true) {
            int index = (int) (Math.random()*9);
            if( b.isTileOpen(index)) {
                b.move(index);
                return b;
            }

        }
    }
    public Board cyclicMove(Board b) {
        for (int index = 0; index < 9; index++) {
            if( !(b.isTileO(index) || b.isTileX(index))){
                b.move(index);
                return b;
            }
        }
        return b;
    }
    public Board ifCanWinDoWinMove(Board b) {
        for (int index = 0; index < 9; index++) {
            if( !(b.isTileO(index) || b.isTileX(index))){
                Board bb = b.copy();
                bb.move(index);
                if (bb.isGameWon()) {
                    b.move(index);
                    return b;
                }
            }
        }
        return randomMove(b);
    }
    private Board centerOrCornerMove(Board b) {
        if(b.isTileOpen(4)) {
            b.move(4);
            return b;
        }
        else{
            ArrayList<Integer> moves = new ArrayList<Integer>(0);
            if (b.isTileOpen(0)) moves.add(0);
            if (b.isTileOpen(2)) moves.add(2);
            if (b.isTileOpen(6)) moves.add(6);
            if (b.isTileOpen(8)) moves.add(8);
            if (moves.size() > 0) b.move( moves.get( (int) (Math.random()*moves.size())));
            else randomMove(b);
            return b;
        }
    }
    public Board dontLetThemWinMove(Board b) {
        ArrayList<Board> gameStates = getGameStates(b);

        if(b.getNumTurns() <= 1) {
            return centerOrCornerMove(b);
        }


        for (int i = 0; i < gameStates.size(); i++) {
            if (gameStates.get(i).getScore(b.isXTurn()) == 1) return gameStates.get(i);
        }

        for (int i = 0; i < gameStates.size(); i++) {

            ArrayList<Board> temp = getGameStates(gameStates.get(i));

            for (int j = 0; j < temp.size(); j++) {
                if (temp.get(j).getScore(b.isXTurn()) == -1) {
                    j = temp.size() + 1;
                    gameStates.remove(i);
                    i--;
                }
            }
        }
        if (gameStates.size() == 0) return centerOrCornerMove(b);
        return gameStates.get((int)(Math.random()*gameStates.size()));
    }


    public Board neverLoseMove(Board b) {
        mIsX = b.isXTurn();
        if (b.getNumTurns() == 1 || b.getNumTurns() == 0) return centerOrCornerMove(b);
        return minMax(b, true, 9).getBoard();


    }

    private BoardAndScore minMax(Board b, boolean myTurn, int depth) {
        if (b.isGameFull() || b.isGameWon() || depth == 0) return new BoardAndScore(b.copy(), b.getScore(mIsX));

        ArrayList<Board> children = getGameStates(b);
        BoardAndScore bestValue = new BoardAndScore(children.get(0), minMax(children.get(0), !myTurn, depth - 1).getScore());

        if (myTurn) {
            for (int i = 0; i < children.size(); i++) {
                BoardAndScore newValue = new BoardAndScore(children.get(i), minMax(children.get(i).copy(), !myTurn, depth-1).getScore());
                if(bestValue.getScore() < newValue.getScore()) {
                    bestValue = newValue.copy();
                }
            }
        }
        else {
            for (int i = 0; i < children.size(); i++) {
                BoardAndScore newValue = new BoardAndScore(children.get(i), minMax(children.get(i).copy(), !myTurn, depth-1).getScore());
                if(bestValue.getScore() > newValue.getScore()) {
                    bestValue = newValue.copy();
                }
            }
        }

        return bestValue;
    }


    private class BoardAndScore {
        private Board mBoard;
        private int mScore;

        public BoardAndScore(Board board, int score) {
            mBoard = board;
            mScore = score;
        }

        public Board getBoard() {
            return mBoard;
        }

        public void setBoard(Board board) {
            mBoard = board;
        }

        public int getScore() {
            return mScore;
        }

        public void setScore(int score) {
            mScore = score;
        }

        public BoardAndScore copy() {
            return new BoardAndScore(mBoard.copy(), mScore);
        }
    }

    private ArrayList<Board> getGameStates(Board b) {
        ArrayList<Board> ans = new ArrayList<Board>(0);
        for (int i = 0; i < 9; i++) {
            if( b.isTileOpen(i) ){
                Board bb = b.copy();
                if (bb.move(i)) {
                    ans.add(bb);
                }
            }
        }
        return ans;
    }

    private Board max(Board board1, Board board2, boolean b) {
        if( board1.getScore(b) > board2.getScore(b)) return board1;
        if( board1.getScore(b) < board2.getScore(b)) return board2;
        return board1;
    }
    private Board min(Board board1, Board board2, boolean b) {
        if( board1.getScore(b) < board2.getScore(b)) return board1;
        if( board1.getScore(b) > board2.getScore(b)) return board2;
        return board1;
    }




}
