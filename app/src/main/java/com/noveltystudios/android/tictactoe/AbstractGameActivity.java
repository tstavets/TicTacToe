package com.noveltystudios.android.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tstavets on 7/25/17.
 */

public abstract class AbstractGameActivity extends AppCompatActivity implements AbstractStrings, AbstractGameFragment.buttonListener  {

    protected ArrayList<AbstractGameFragment> mFragments;
    protected ViewPager mViewPager;
    protected TextView mDirections;
    protected Button mResetButton;
    protected PlayerStyle p1, p2;
    protected int pageNum_directions;
    protected boolean musicPlaying;

    private static final String DIALOG_TAG = "idontknowwhatthisdoes";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle();
        MainMenuActivity.setMusicHelper(false);
        musicPlaying = MainMenuActivity.musicPlaying();

        mFragments = new ArrayList<>(0);

        mDirections = (TextView) findViewById(R.id.v2_directions_text);

        mResetButton = (Button) findViewById(R.id.v2_reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.playSoundEffect();
                reset();
            }
        });

        p1 = new PlayerStyle(); p2 = new PlayerStyle();
        setPlayerStyles();


    }



    protected void setPlayerStyles() {
        setPlayerOneStyle();
        setPlayerTwoStyle();
    }
    protected void setPlayerOneStyle() {
        SharedPreferences sp = getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);

        p1.setPrimaryColor(sp.getInt(PRIMARY_COLOR, 0));
        p1.setDarken(sp.getInt(PRIMARY_COLOR_DARKEN, 0));
        p1.setPlayerName(sp.getString(PLAYER_NAME, ""));

    }
    protected void setPlayerTwoStyle() {
        SharedPreferences sp = getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);

        p2.setPrimaryColor(sp.getInt(PRIMARY_COLOR, 0));
        p2.setDarken(sp.getInt(PRIMARY_COLOR_DARKEN, 0));
        p2.setPlayerName(sp.getString(PLAYER_NAME, ""));

    }
    protected void update() {
        updateBoard();
        updateDirectionsText();
    }


    abstract void updateBoard();
    abstract void updateDirectionsText();
    abstract void reset();
    abstract void setTitle();



    @Override
    public void buttonPressed(int index){
       if (index == -2) MainMenuActivity.playSoundEffect();
    }

    @Override
    public abstract void turnImagePressed();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_directions) {
            MainMenuActivity.setMusicHelper(true);
            Intent intent = new Intent(this, DirectionsActivity.class);
            intent.putExtra(PAGE_NUMBER, pageNum_directions);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.menu_edit_p1) {
            FragmentManager manager = getSupportFragmentManager();
            PlayerEditorDialog dialog = PlayerEditorDialog.newInstance(1, p1.getPlayerName() + ", edit your info");
            dialog.setTargetFragment(mFragments.get(mViewPager.getCurrentItem()), 0);
            dialog.show(manager, DIALOG_TAG);
        }
        else if (item.getItemId() == R.id.menu_edit_p2) {
            FragmentManager manager = getSupportFragmentManager();
            PlayerEditorDialog dialog = PlayerEditorDialog.newInstance(2,  p2.getPlayerName() + ", edit your info");
            dialog.setTargetFragment(mFragments.get(mViewPager.getCurrentItem()), 0);
            dialog.show(manager, DIALOG_TAG);
        }
        else if (item.getItemId() == R.id.menu_music) {
            MainMenuActivity.startStopMusic();
        }
        else if (item.getItemId() == R.id.menu_sfx) {
            MainMenuActivity.setSfxHelper(!MainMenuActivity.isSfxHelper());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResultCalledMINE(int resultCode) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        else {
            setPlayerStyles();
            updateDirectionsText();
            updateBoard();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!MainMenuActivity.isMusicHelper()) {
            musicPlaying = MainMenuActivity.musicPlaying();
            MainMenuActivity.stopMusic();
        }

        MainMenuActivity.updateSettingsFragments();


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
