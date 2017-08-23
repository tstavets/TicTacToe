package com.noveltystudios.android.tictactoe;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DirectionsActivity extends AppCompatActivity implements AbstractStrings {

    private TextView mTextView;
    private ViewPager mViewPager;
    private Directions_Fragment[] mFragments;
    private boolean musicPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_directions);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle();
        MainMenuActivity.setMusicHelper(false);
        musicPlaying = MainMenuActivity.musicPlaying();


        mTextView = (TextView) findViewById(R.id.v2_directions_text);
        mTextView.setText("App Directions");

        mFragments = new Directions_Fragment[6];
        for (int i = 0; i < 6; i++) mFragments[i] = new Directions_Fragment();

        mFragments[0].setDirectionsText(getString(R.string.DIRECTIONS_App));
        mFragments[1].setDirectionsText(getString(R.string.DIRECTIONS_Hangman));
        mFragments[2].setDirectionsText(getString(R.string.DIRECTIONS_TicTacToe));
        mFragments[3].setDirectionsText(getString(R.string.DIRECTIONS_SuperTicTacToe));
        mFragments[4].setDirectionsText(getString(R.string.DIRECTIONS_SuperTicTacToeHangman));
        mFragments[5].setDirectionsText(getString(R.string.DIRECTIONS_Records));




        mViewPager = (ViewPager) findViewById(R.id.directions_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return mFragments.length;
            }
        });



        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) mTextView.setText("App Directions");
                if (position == 1) mTextView.setText("Hangman Directions");
                if (position == 2) mTextView.setText("Tic Tac Toe Directions");
                if (position == 3) mTextView.setText("Super Tic Tac Toe Directions");
                if (position == 4) mTextView.setText("Super Tic Tac Toe Hangman Directions");
                if (position == 5) mTextView.setText("Searching Records");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        int p = 0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                p = 0;
            } else {
                p = extras.getInt(PAGE_NUMBER);
            }
        } else {
            p = 0;
        }

        mViewPager.setCurrentItem(p);

    }



    private void setTitle() {
        getSupportActionBar().setTitle(R.string.DIRECTIONS);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!MainMenuActivity.isMusicHelper()) {
            musicPlaying = MainMenuActivity.musicPlaying();
            MainMenuActivity.stopMusic();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainMenuActivity.setMusicHelper(false);
        if (musicPlaying) MainMenuActivity.startMusic();
    }
    @Override
    public void onBackPressed() {

        MainMenuActivity.setMusicHelper(true);
        super.onBackPressed();

    }
}
