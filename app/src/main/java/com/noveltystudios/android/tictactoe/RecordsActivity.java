package com.noveltystudios.android.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.noveltystudios.android.tictactoe.database.GameBaseHelper;

public class RecordsActivity extends AppCompatActivity implements AbstractStrings {

    private Button mButton;
    private TextView mTextView;
    private EditText mEditText;
    private GameBaseHelper mGameBaseHelper;
    private Switch s1, s2, s3, s4;
    private int x;
    private boolean musicPlaying;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_records);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle();
        MainMenuActivity.setMusicHelper(false);
        musicPlaying = MainMenuActivity.musicPlaying();

        mGameBaseHelper = new GameBaseHelper(this);




        s1 = (Switch) findViewById(R.id.switch1_Record);
        s2 = (Switch) findViewById(R.id.switch2_Record);
        s3 = (Switch) findViewById(R.id.switch3_Record);
        s4 = (Switch) findViewById(R.id.switch4_Record);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked();
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked();
            }
        });
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked();
            }
        });
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked();
            }
        });

        mButton = (Button) findViewById(R.id.getRecordButton_RecordActivity);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuActivity.playSoundEffect();
                if (x==0) {
                    mTextView.setText(mGameBaseHelper.getRecord(mEditText.getText().toString().trim()));
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    if (mEditText.getText().toString().trim().equals("Jasmine+")) {
                        Toast t = Toast.makeText(getApplicationContext(), "   I love you Jasmine!~   ", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.getView().setBackgroundColor(getColor(R.color.cobalt));
                        t.show();
                    }
                }
                if (x==1) {
                    mGameBaseHelper.resetPlayerData(mEditText.getText().toString().trim());
                    mTextView.setText(R.string.the_player_s_record_will_appear_here);
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    Toast.makeText(getApplicationContext(), mEditText.getText().toString().trim() + " data deleted", Toast.LENGTH_SHORT).show();
                }
                if (x==2) {
                    mGameBaseHelper.resetDataBase();
                    mTextView.setText(R.string.the_player_s_record_will_appear_here);
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                    Toast.makeText(getApplicationContext(), "all data deleted", Toast.LENGTH_SHORT).show();
                }

                AnimatedVectorDrawable d = (AnimatedVectorDrawable) getDrawable(R.drawable.searching_avd);
                d.setTint(getColor(R.color.gray1));
                mButton.setCompoundDrawablesWithIntrinsicBounds(d, null, d, null);
                d.start();
            }
        });


        mEditText = (EditText) findViewById(R.id.playerName_records);
        mTextView = (TextView) findViewById(R.id.playerRecord);

        SharedPreferences sp = getSharedPreferences(RECORDS_SHAREDPREFERENCE, MODE_PRIVATE);
        mEditText.setText(sp.getString(PLAYER_NAME, getString(R.string.type_player_name_here)));

    }

    private void checked() {
        MainMenuActivity.playSoundEffect();
        saveSharedPreferece();
        int num = 0;
        if (s1.isChecked()) num++;
        if (s2.isChecked()) num++;
        if (s3.isChecked()) num++;
        if (s4.isChecked()) num++;

        if (num == 4) {
            x = 2;
            mButton.setText("DELETE ALL GAME DATA");
            mButton.setBackgroundColor(getColor(R.color.fireBrick2));
        }
        else if (num == 2 || num == 3) {
            x = 1;
            mButton.setText("DELETE ALL PLAYER DATA");
            mButton.setBackgroundColor(getColor(R.color.cadmiumYellow));
        }
        else {
            x = 0;
            mButton.setText(R.string.getRecord);
            mButton.setBackgroundColor(getColor(R.color.coldGrey));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        saveSharedPreferece();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveSharedPreferece();
    }

    

    private void saveSharedPreferece(){
        SharedPreferences sp = getSharedPreferences(RECORDS_SHAREDPREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(PLAYER_NAME, mEditText.getText().toString().trim());

        editor.apply();
    }

    private void setTitle() {
        getSupportActionBar().setTitle("Records");
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
            MainMenuActivity.setMusicHelper(true);
            Intent intent = new Intent(this, DirectionsActivity.class);
            intent.putExtra(PAGE_NUMBER, 5);
            startActivity(intent);
            return true;
        }
        else if (item.getItemId() == R.id.menu_get_record) {

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
