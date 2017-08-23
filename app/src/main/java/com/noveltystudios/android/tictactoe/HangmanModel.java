package com.noveltystudios.android.tictactoe;

import java.util.ArrayList;

/**
 * Created by tstavets on 6/30/17.
 */

public class HangmanModel {


    private String mTheWord;
    private ArrayList<Guess> mGuesses;
    private String mWordProgress;
    private boolean mGameStarted;
    private boolean mActive;

    public HangmanModel() {
        mGameStarted = false;
        mActive = true;
    }

    public void chooseTheWord(String theWord) {
        mTheWord = theWord.toUpperCase();
        mWordProgress = "";

        for (int i = 0; i < mTheWord.length(); i++) {
            if (mTheWord.substring(i, i+1).equals(" ")) mWordProgress =  mWordProgress + " ";
            else mWordProgress = mWordProgress + "_";
        }
        mGuesses = new ArrayList<Guess>(0);

        mGameStarted = true;
    }
    public int getNumGuesses() { return mGuesses.size(); }
    public String getLastGuess() { return  mGuesses.get(mGuesses.size()-1).getGuess(); }
    public String getGuess(int index) { return mGuesses.get(index).getGuess(); }
    public boolean isGameWon() {
        if (isGameStarted()) return mWordProgress.indexOf("_") == -1;
        return false;
    }
    public boolean isGameLost() {
        return mGameStarted && !isGameWon() && isGameOver();
    }
    public boolean isGameOver() {
        if (!mGameStarted) return false;
        return isGameWon() || mGuesses.size() == 7;
    }
    public String getTheWord() { return mTheWord; }
    public String getWordProgress() {
        String ans = "";
        for (int i = 0; i < mWordProgress.length(); i++) {
            ans += mWordProgress.substring(i, i + 1) + " ";
        }
        return ans;
    }

    public boolean guess(String g) {
        if (mActive) {
            Guess guess = new Guess(g);
            if (mTheWord.indexOf(guess.getGuess()) > -1) {
                for (int i = 0; i < mTheWord.length(); i++) {
                    if (mTheWord.substring(i, i + 1).equals(guess.getGuess()))
                        mWordProgress = mWordProgress.substring(0, i) + guess.getGuess() + mWordProgress.substring(i + 1);
                }
                return true;
            }
            mGuesses.add(guess);
            return false;
        }
        return false;
    }
    public boolean isGameStarted() { return mGameStarted; }


    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    protected class Guess {
        private String mGuess;

        public Guess(String guess) {
            mGuess = guess.toUpperCase();
            mGuess = mGuess.trim();
        }

        public String getGuess() { return mGuess; }

        @Override
        public boolean equals(Object g) {
            if (g.getClass() != this.getClass()) return false;
            return ( (Guess) g).getGuess().equals(mGuess);
        }


    }
}
