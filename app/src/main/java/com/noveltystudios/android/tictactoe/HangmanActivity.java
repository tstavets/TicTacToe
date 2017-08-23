package com.noveltystudios.android.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by tstavets on 7/8/17.
 */

public class HangmanActivity extends AbstractGameActivity implements AbstractStrings {

    private boolean game1Over, game2Over;
    private int mPageNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum_directions = 1;

        game1Over = false;
        game2Over = false;

        mPageNumber = 0;

        mFragments = new ArrayList<>(0);

        mFragments.add(new HangmanGameFragment());
        mFragments.add(new HangmanGameFragment());

        ((HangmanGameFragment) mFragments.get(0)).setPlayerNum(1);
        ((HangmanGameFragment) mFragments.get(1)).setPlayerNum(2);



        mViewPager = (ViewPager) findViewById(R.id.activity_game_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return 2;
            }



        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPageNumber = position;
                updateDirectionsText();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });

        mDirections.setText(p1.getPlayerName() + " type out word");

        mViewPager.setOffscreenPageLimit(2);

    }

    @Override
    void updateBoard() {
        mFragments.get(0).updateBoard();
        mFragments.get(1).updateBoard();
    }

    @Override
    void updateDirectionsText() {
        if (mPageNumber == 0){
            if (((HangmanGameFragment) mFragments.get(0)).isGameWonO()) {
                mDirections.setText(p2.getPlayerName() + " won!");
            }
            else if (((HangmanGameFragment) mFragments.get(0)).isGameWonX()) {
                mDirections.setText(p1.getPlayerName() + " won!");
            }
            else if (((HangmanGameFragment) mFragments.get(0)).started()) {
                mDirections.setText(p2.getPlayerName() + " guess");
            }
            else {
                mDirections.setText(p1.getPlayerName() + " type out word");
            }
        }
        else if (mPageNumber == 1){
            if (((HangmanGameFragment) mFragments.get(1)).isGameWonO()) {
                mDirections.setText(p2.getPlayerName() + " won!");
            }
            else if (((HangmanGameFragment) mFragments.get(1)).isGameWonX()) {
                mDirections.setText(p1.getPlayerName() + " won!");
            }
            else if (((HangmanGameFragment) mFragments.get(1)).started()) {
                mDirections.setText(p1.getPlayerName() + " guess");
            }
            else {
                mDirections.setText(p2.getPlayerName() + " type out word");
            }
        }
    }

    @Override
    void reset() {
        mFragments.get(0).reset();
        mFragments.get(1).reset();
        game1Over = false;
        game2Over = false;
        updateDirectionsText();
    }

    @Override
    void setTitle() {
        getSupportActionBar().setTitle(R.string.HangmanTitle);
    }

    @Override
    public void buttonPressed(int index) {
        super.buttonPressed(index);
        updateDirectionsText();
    }

    @Override
    public void turnImagePressed() {

    }
}
