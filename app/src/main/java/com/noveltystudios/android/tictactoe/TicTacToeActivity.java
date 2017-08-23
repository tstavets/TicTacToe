package com.noveltystudios.android.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

public class TicTacToeActivity extends AbstractGameActivity implements  AbstractGameFragment.buttonListener {

    private int pageNumber;
    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        pageNum_directions = 2;

        started = false;
        mFragments.add(new TicTacToeFragment());
        mFragments.get(0).setINDEX_stthm(0);
        mFragments.add(new TicTacToeFragment());
        mFragments.get(1).setINDEX_stthm(1);
        mFragments.add(new TicTacToeFragment());
        mFragments.get(2).setINDEX_stthm(2);
        mFragments.add(new TicTacToeFragment());
        mFragments.get(3).setINDEX_stthm(3);
        mFragments.add(new TicTacToeFragment());
        mFragments.get(4).setINDEX_stthm(4);
        pageNumber = 3;

        mViewPager = (ViewPager) findViewById(R.id.activity_game_pager);


        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageNumber = position;
           /*     if (position == 4 || position == 0) {
                    for (int i = 0; i < mFragments.size(); i++) mFragments.get(i).resetNoAnim();
                    mViewPager.setCurrentItem(2, false);
                    updateDirectionsText();
                } //*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            /*    if (state == mViewPager.SCROLL_STATE_IDLE && pageNumber != 2) {
                    for (int i = 0; i < mFragments.size(); i++)
                    mFragments.get(i).resetNoAnim();
                    mViewPager.setCurrentItem(2, false);
                    updateDirectionsText();
                } //*/
            updateDirectionsText();
            }
        });

        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(2, false);

        mDirections.setText(p1.getPlayerName() + "'s move");
    }

    @Override
    public void updateBoard() {
        for(int i = 0; i < mFragments.size(); i++) {
            mFragments.get(i).updateBoard();
        }
    }

    @Override
    public void updateDirectionsText() {
       if (!(mFragments.get(pageNumber).isGameWon() || mFragments.get(pageNumber).isGameFull())) {
            if (mFragments.get(pageNumber).isXTurn()) mDirections.setText(p1.getPlayerName() + "'s move");
            else mDirections.setText(p2.getPlayerName() + "'s move");
        }
        else {
            if (mFragments.get(pageNumber).isGameWonX()) mDirections.setText(p1.getPlayerName() + " Won!");
            else if (mFragments.get(pageNumber).isGameWonO() && ((TicTacToeFragment) (mFragments.get(pageNumber))).isTwoPlayer()) mDirections.setText(p2.getPlayerName() + " Won!");
            else if (mFragments.get(pageNumber).isGameWonO() && !((TicTacToeFragment) (mFragments.get(pageNumber))).isTwoPlayer()) mDirections.setText("Computer Won!");
            else mDirections.setText(R.string.Game_Draw_Text);
        }
    }


    @Override
    public void buttonPressed(int index) {
        super.buttonPressed(index);
        updateDirectionsText();
    }

    @Override
    public void turnImagePressed() {
        updateDirectionsText();
    }

    @Override
    public void reset() {
        mFragments.get(pageNumber).reset();
        updateDirectionsText();
    }

    @Override
    public void setTitle() {
        getSupportActionBar().setTitle(R.string.TictactoeTitle);
    }







}
