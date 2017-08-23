package com.noveltystudios.android.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.BooleanResult;

/**
 * Created by tstavets on 7/10/17.
 */

public class MainMenuActivity extends AppCompatActivity implements AbstractStrings, BlankFragment.mainMenuListener{
    private AdView mAdView;
    private static Fragment[] mFragments;
    private Fragment mBlankFragment;
    private ViewPager mViewPager;
    private Button mLeftButton, mRightButton;
    private static MediaPlayer musicPlayer, sfxPlayer;
    private static boolean musicHelper, sfxHelper;
    private int startedHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle();
        mBlankFragment = null;
        startedHelper = -1;


        setUpMusicPlayer();
        setSfxPlayer();

        startAds();

        initializeFragmentArray();

        if (startedHelper == -1) {
            createLeftButton();
            createRightButton();
        }

        setViewPager();
    }

    private void createRightButton() {
        mRightButton = (Button) findViewById(R.id.main_menu_right_tab_button);
        mRightButton.setText("Edit Player 2");
        mRightButton.setVisibility(View.VISIBLE);
        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundEffect();
                AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_arrow_right);
                mRightButton.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);
                d.start();

                if (mViewPager.getCurrentItem() < 2) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
            }
        });
    }

    private void createLeftButton() {
        mLeftButton = (Button) findViewById(R.id.main_menu_left_tab_button);
        mLeftButton.setText("Edit Player 1");
        mLeftButton.setVisibility(View.VISIBLE);
        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSoundEffect();
                AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_arrow_left);
                mLeftButton.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
                d.start();

                if (mViewPager.getCurrentItem() > 0) {
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                }
            }
        });
    }

    private void setUpMusicPlayer() {
        musicHelper = false;
        musicPlayer = MediaPlayer.create(this, R.raw.background_music_v1);
        musicPlayer.setLooping(true);


        SharedPreferences sp = getSharedPreferences(MUSIC_SHAREDPREFERENCE, MODE_PRIVATE);
        if (sp.getBoolean(MUSIC_SHAREDPREFERENCE, true)) musicPlayer.start();

    }

    private void setSfxPlayer(){
        sfxHelper = true;
        sfxPlayer = MediaPlayer.create(this, R.raw.tic_tac_toe_button_sfx);
        sfxPlayer.setLooping(false);
    }

    private void startAds() {
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713"); /****/
        mAdView = (AdView) findViewById(R.id.adView); /****/
        AdRequest adRequest = new AdRequest.Builder().build(); /****/
        mAdView.loadAd(adRequest); /****/}

    private void initializeFragmentArray() {
        mFragments = new Fragment[3];
        mFragments[0] = new SettingsFragment();
        ((SettingsFragment) mFragments[0]).setPlayerNum(1);
        mFragments[1] = new GameChooserFragment();
        mFragments[2] = new SettingsFragment();
        ((SettingsFragment) mFragments[2]).setPlayerNum(2);
    }

    private void setViewPager() {
        SharedPreferences sp = getSharedPreferences(MAIN_MENU_SHAREDPREFERENCE, MODE_PRIVATE);
        if ( !(sp.getBoolean(HASSTARTED, false)) ) {
            sp.edit().putBoolean(HASSTARTED, true).commit();
            createPreViewPager();
            mAdView.setVisibility(View.INVISIBLE);
        }
        else {
            createMainViewPager();
        }
    }

    private void createMainViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.main_menu_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }

            @Override
            public int getCount() {
                return 3;
            }
        });


        mViewPager.setCurrentItem(1);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 1) {
                    ( (SettingsFragment) mFragments[0] ).savePlayerInfo();
                    ( (SettingsFragment) mFragments[2] ).savePlayerInfo();

                }


                if (position == 0) {
                    mLeftButton.setText("");
                    mRightButton.setText("Choose Game");
                }
                else if (position == 1) {
                    mRightButton.setText("Edit Player 2");
                    mLeftButton.setText("Edit Player 1");
                }
                if (position == 2) {
                    mRightButton.setText("");
                    mLeftButton.setText("Choose Game");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mViewPager.setOffscreenPageLimit(4);

        ((SettingsFragment) mFragments[2]).setPlayerNum(2);
        ((SettingsFragment) mFragments[0]).setPlayerNum(1);

 //       ((SettingsFragment) mFragments[2]).update();
 //       ((SettingsFragment) mFragments[0]).update();
    }

    private void createPreViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.main_menu_view_pager);

        mBlankFragment = new BlankFragment();

        startedHelper = 0;

        mLeftButton.setVisibility(View.INVISIBLE);
        mRightButton.setVisibility(View.INVISIBLE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mBlankFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

        startDialog(1);
    }

    private void setTitle() {
        getSupportActionBar().setTitle("Main Menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_directions) {
            musicHelper = true;
            Intent intent = new Intent(this, DirectionsActivity.class);
            intent.putExtra(PAGE_NUMBER, 0);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.menu_get_record) {
            musicHelper = true;
            Intent intent = new Intent(this, RecordsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.menu_music) {
            startStopMusic();
        }
        else if (item.getItemId() == R.id.menu_sfx) {
            setSfxHelper(!MainMenuActivity.isSfxHelper());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()){
            destroyMediaPlayer();
            destroySFXPlayer();
        }
        else if (!musicHelper) {
            destroyMediaPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicHelper = false;
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(this, R.raw.background_music_v1);
            musicPlayer.setLooping(true);
            SharedPreferences sp = getSharedPreferences(MUSIC_SHAREDPREFERENCE, MODE_PRIVATE);
            if (sp.getBoolean(MUSIC_SHAREDPREFERENCE, true)) musicPlayer.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public static void startStopMusic() {
        if (musicPlayer != null) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
            } else {
                musicPlayer.start();
            }
        }
    }
    public static void stopMusic() {
        if (musicPlayer != null) {
            if (musicPlayer.isPlaying()) {
                musicPlayer.pause();
            }
        }
    }
    public static void startMusic() {
        if (musicPlayer != null) {
            if (!musicPlayer.isPlaying()) {
                musicPlayer.start();
            }
        }
    }
    public static boolean musicPlaying() {
        return musicPlayer.isPlaying();
    }
    public void destroyMediaPlayer() {
        SharedPreferences sp = getSharedPreferences(MUSIC_SHAREDPREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (musicPlayer.isPlaying()) {
            editor.putBoolean(MUSIC_SHAREDPREFERENCE, true);
        }
        else {
            editor.putBoolean(MUSIC_SHAREDPREFERENCE, false);
        }
        editor.apply();

        musicPlayer.release();
        musicPlayer = null;
    }
    public static boolean isMusicHelper() {
        return musicHelper;
    }
    public static void setMusicHelper(boolean bool) {
        musicHelper = bool;
    }
    public static MediaPlayer getMusicPlayer(){
        return musicPlayer;
    }

    public static void playSoundEffect() {
        if (sfxHelper) {
            sfxPlayer.seekTo(0);
            sfxPlayer.start();
        }
    }
    public static boolean isSfxHelper() { return sfxHelper; }
    public static void setSfxHelper(boolean b) { sfxHelper = b; }
    public static MediaPlayer getSfxPlayer() { return sfxPlayer; }
    public static void destroySFXPlayer() {
        sfxPlayer.release();
        sfxPlayer = null;
    }

    @Override
    public void okayPressed() {
        startedHelper ++;

        startedHelperUpdated();
    }

    @Override
    public void cancelPressed() {
        startedHelper++;

        startedHelperUpdated();
    }

    @Override
    public void justMoveOn() {
        createLeftButton();
        createRightButton();
        createMainViewPager();
    }

    @Override
    public void layoutPressed() {
        startDialog(startedHelper + 1);
    }


    private void startedHelperUpdated() {
        if (startedHelper == 1) {
            startDialog(2);
        }
        if (startedHelper == 2) {
            createLeftButton();
            createRightButton();
            createMainViewPager();
            mAdView.setVisibility(View.VISIBLE);
        }
    }

    private void startDialog(int playerNum) {
        String title = "";
        if (playerNum == 1 || playerNum == 2) {
            if (playerNum == 1) title = "Player 1, choose your name and color";
            if (playerNum == 2) title = "Player 2, choose your name and color";

            FragmentManager manager = getSupportFragmentManager();
            PlayerEditorDialog dialog = PlayerEditorDialog.newInstance(playerNum, title);
            dialog.setTargetFragment(mBlankFragment, 0);
            dialog.show(manager, "DIALOG_TAG");

        }
    }

    public static void updateSettingsFragments() {
        ((SettingsFragment) mFragments[2]).update();
        ((SettingsFragment) mFragments[0]).update();
    }
}
