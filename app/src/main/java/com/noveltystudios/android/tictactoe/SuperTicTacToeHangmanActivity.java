package com.noveltystudios.android.tictactoe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.noveltystudios.android.tictactoe.database.GameBaseHelper;

import java.util.ArrayList;

public class SuperTicTacToeHangmanActivity extends AbstractGameActivity implements SuperTicTacToeHangmanFragment.PlayerMoveListener, HangmanSuperFragment.HangmanListener, AbstractStrings {



    private SuperTicTacToeHangmanBoard mBoard;
    private boolean mIsXTurn;
    private ArrayList<Integer> p1Guesses, p2Guesses;
    private int countdown;
    private GameBaseHelper dbHelper;
    private boolean mGameSaved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum_directions = 4;



        /**********/
        dbHelper = new GameBaseHelper(this);
        /**********/
        mGameSaved = false;





        mDirections.setText(R.string.stthm_starting_directions);

        countdown = 2;

        mBoard = new SuperTicTacToeHangmanBoard();
        mBoard.deactivateAll();
        mIsXTurn = true;
        p1Guesses = new ArrayList<>(0);
        p2Guesses = new ArrayList<>(0);


        mFragments = new ArrayList<>(0);

        mFragments.add( new HangmanSuperFragment());
        mFragments.add( new SuperTicTacToeHangmanFragment());
        mFragments.add( new HangmanSuperFragment());

        ((HangmanSuperFragment) mFragments.get(0)).setPlayerNum(1);
        ((HangmanSuperFragment) mFragments.get(2)).setPlayerNum(2);


        mViewPager = (ViewPager) findViewById(R.id.activity_game_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }

        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mDirections.setText(p1.getPlayerName() + "'s word");
                }
                else if (position == 1) {
                    updateTurnText();
                }
                else if (position == 2) {
                    mDirections.setText(p2.getPlayerName() + "'s word");
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });


        mViewPager.setCurrentItem(1);


        mViewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void hangmanTapped(int playerNum) {
        buttonPressed(-1);
        if (mBoard.isBoardPickingState()) {
            if (playerNum == 0) {
                if (mBoard.selectBoard(6)) {
                    mBoard.setBoardPickingState(false);
                    nextTurn();
                }
            }
            else {
                if (mBoard.selectBoard(2)) {
                    mBoard.setBoardPickingState(false);
                    nextTurn();
                }
            }
            updateBoards();
        }
        mViewPager.setCurrentItem(playerNum, true);
    }

    @Override
    public void activate(int num) {
        ( (HangmanGameFragment) mFragments.get(num)).setActive(true);
    }

    @Override
    public void deactivate(int num) {
        ( (HangmanGameFragment) mFragments.get(num)).setActive(false);
    }

    @Override
    public void tileTapped(int boardNum, int tileNum, int index) {
         if (!mBoard.isBoardPickingState()) {

             if (mBoard.move(boardNum,tileNum)) {

                 if (mIsXTurn) {
                     ( (SuperTicTacToeHangmanFragment)  mFragments.get(1)).animateTileToX(index);
                     buttonPressed(-2);
                 }
                 else {
                     ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).animateTileToO(index);
                     buttonPressed(-2);
                 }

                 nextTurn();

             }


         }
         else {

             if(mBoard.selectBoard(boardNum)) {
                 mBoard.setBoardPickingState(false);
                 nextTurn();
             }

         }


        updateBoards();
    }

    @Override
    public void firstPlayerChanged() {
        mIsXTurn = !mIsXTurn;
        mBoard.setXTurn(mIsXTurn);
    }

    @Override
    public void wordMade(String word, int INDEX) {
        if (INDEX == 1) mBoard.setXWord(word);
        else mBoard.setOWord(word);

        countdown --;
        if (countdown == 0) {
            ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).setStarted(true);

            mBoard.activateAll();
            if (mIsXTurn) mBoard.deactivate(6);
            else mBoard.deactivate(2);
            updateBoards();
        }

        if (countdown == 1) {
            if (INDEX == 1) mViewPager.setCurrentItem(2, true);
            else mViewPager.setCurrentItem(0, true);
        }
        else {
            mViewPager.setCurrentItem(1, true);
        }
    }

    @Override
    public void letterGuessed(String guess, int index) {
        buttonPressed(-2);
        if (mIsXTurn && !mBoard.isBoardPickingState()) {
            p1Guesses.add(index);
            if(!mBoard.xGuess(guess)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).animateP1Hangman();
                ((HangmanSuperFragment) mFragments.get(2)).animate();

                nextTurn();
            }
            else if (mBoard.isBoardWonX(2)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).animateP1HangmanWin();
                ((HangmanSuperFragment) mFragments.get(2)).animateWin();
                ((HangmanSuperFragment) mFragments.get(2)).getWordProgress().setText(mBoard.getHangmanModels()[0].getWordProgress());
            }
            else {
                ((HangmanSuperFragment) mFragments.get(2)).getWordProgress().setText(mBoard.getHangmanModels()[0].getWordProgress());
            }
        }

        else  if (!mBoard.isBoardPickingState()){
            p2Guesses.add(index);
            if(!mBoard.oGuess(guess)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).animateP2Hangman();
                ((HangmanSuperFragment) mFragments.get(0)).animate();


                nextTurn();

            }
            else if (mBoard.isBoardWonO(6)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).animateP2HangmanWin();
                ((HangmanSuperFragment) mFragments.get(0)).animateWin();
                ((HangmanSuperFragment) mFragments.get(0)).getWordProgress().setText(mBoard.getHangmanModels()[1].getWordProgress());
            }
            else {
                ((HangmanSuperFragment) mFragments.get(0)).getWordProgress().setText(mBoard.getHangmanModels()[1].getWordProgress());
            }
        }

        if (!mBoard.isBoardPickingState()) mViewPager.setCurrentItem(1, true);
        updateBoards();

    }



    public void updateBoards() {
        int[] toActivate = mBoard.getToActivate();



        ((HangmanSuperFragment) mFragments.get(0)).setActive(false);
        ((HangmanSuperFragment) mFragments.get(2)).setActive(false);
        ((SuperTicTacToeHangmanFragment) mFragments.get(1)).deactivateAll();


        if (mBoard.STALEMATE()) {

            mIsXTurn = mBoard.isXTurn();
            ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).setXTurn(!mIsXTurn);
            ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).animateTurnImage();
        }

        int playNum = 1;
        if (!mIsXTurn) playNum = 2;

        mBoard.isGameOver();
        if (!mBoard.isGameWon() && !mBoard.isGameFull()) {

            for (int i = 0; i < toActivate.length; i++) {
                if (toActivate[i] == 2) {
                    if (!mBoard.isBoardPickingState()) {
                        ((HangmanSuperFragment) mFragments.get(2)).setActive(true);
                    }
                    ((SuperTicTacToeHangmanFragment) mFragments.get(1)).activate(2, playNum);
                } else if (toActivate[i] == 6) {
                    if (!mBoard.isBoardPickingState()) {
                        ((HangmanSuperFragment) mFragments.get(0)).setActive(true);
                    }
                    ((SuperTicTacToeHangmanFragment) mFragments.get(1)).activate(6, playNum);
                } else {
                    ((SuperTicTacToeHangmanFragment) mFragments.get(1)).activate(toActivate[i], playNum);
                }
            }
        }
        else {
            ((HangmanSuperFragment) mFragments.get(0)).getWord().setVisibility(View.VISIBLE);
            ((HangmanSuperFragment) mFragments.get(2)).getWord().setVisibility(View.VISIBLE);


            if (mBoard.isXWon()) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).victorAnim(true);
            }
            else if (mBoard.isGameWon()) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).victorAnim(false);
            }

            String victor = null;


            if (mBoard.isXWon()) {

                victor = p1.getPlayerName();
            }
            else if (mBoard.isGameWon()){
                victor = p2.getPlayerName();
            }


            else if (mBoard.isGameFull()) {

                victor = DRAW;

            }

            if (victor != null && !mGameSaved) {
                dbHelper.addGame(getString(R.string.SUPERTICTACTOEHANGMAN), p1.getPlayerName(), p2.getPlayerName(), victor);
                mGameSaved = true;
            }

        }



        for (int i = 0; i < 9; i++) {
            if (mBoard.isBoardWonX(i)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).setBoardToVictorColor(i, 1);
            }
            else if (mBoard.isBoardWonO(i)) {
                ((SuperTicTacToeHangmanFragment) mFragments.get(1)).setBoardToVictorColor(i, 2);
            }
        }

        updateTurnText();
    }

    private void updateTurnText() {
        if (mBoard.isGameWon()) {
            if (mBoard.isXWon()) mDirections.setText(p1.getPlayerName() + " Won!");
            else mDirections.setText(p2.getPlayerName() + " Won!");
        }
        else if (mBoard.isBoardPickingState()) {
            if (mIsXTurn) {
                mDirections.setText(p1.getPlayerName() + " selecting board");
            }
            else {
                mDirections.setText(p2.getPlayerName() + " selecting board");
            }
        }
        else if (mBoard.isGameFull()) {
            mDirections.setText("Draw");
        }
        else if (countdown == 0) {
            if (mIsXTurn) {
                mDirections.setText(p1.getPlayerName() + "'s turn");
            } else {
                mDirections.setText(p2.getPlayerName() + "'s turn");
            }
        }
        else {
            mDirections.setText(R.string.stthm_starting_directions);
        }

        
    }
    private void nextTurn() {
        ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).setXTurn(mIsXTurn);
        ( (SuperTicTacToeHangmanFragment) mFragments.get(1)).animateTurnImage();
        mIsXTurn = !mIsXTurn;
    }

    @Override
    public void reset() {
        mBoard = new SuperTicTacToeHangmanBoard();

        if (!mIsXTurn) {
            nextTurn();
        }

        mBoard.deactivateAll();

        ((HangmanSuperFragment) mFragments.get(0)).reset();
        ((SuperTicTacToeHangmanFragment) mFragments.get(1)).reset();
        ((HangmanSuperFragment) mFragments.get(2)).reset();

        mGameSaved = false;

        countdown = 2;

        updateBoards();

        updateDirectionsText();

    }


    @Override
    void updateBoard() {
        for (int i = 0; i < mFragments.size(); i++) {
            mFragments.get(i).updateBoard();
        }
    }

    @Override
    void updateDirectionsText() {
        int position = mViewPager.getCurrentItem();
        if (position == 0) {
            mDirections.setText(p1.getPlayerName() + "'s word");
        }
        else if (position == 1) {
            updateTurnText();
        }
        else if (position == 2) {
            mDirections.setText(p2.getPlayerName() + "'s word");
        }

    }

    @Override
    void setTitle() {
        getSupportActionBar().setTitle(R.string.SuperTictactoeHangmanTitle);
    }


    @Override
    public void turnImagePressed() {

    }
}
