package com.noveltystudios.android.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.noveltystudios.android.tictactoe.database.GameBaseHelper;

public class SettingsFragment extends Fragment implements AbstractStrings{


    private EditText mName;
    private ImageButton[] mPrimaryColor;
    private ImageView mHangmanImage, mTicTacToeImage;
    private Button mRecordButton;
    private int[] mColors; private String[] mColorsNames;
    private int mColorCountPrimary, playerNum;
    private int mHangmanCount, mTicTacToeCount;
    private GameBaseHelper mGameBaseHelper;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mGameBaseHelper = new GameBaseHelper(getContext());

        setColorArrays(v);

        mColorCountPrimary = 1;

        mHangmanCount = 6;
        mTicTacToeCount = 3;




        setImageViews(v);
        setPlayerNames(v);
        setPrimaryColor(v);
        setButton(v);

        mHangmanImage.getDrawable().setTint(mColors[mColorCountPrimary]);
        mTicTacToeImage.getDrawable().setTint(mColors[mColorCountPrimary]);

        update();

        animateHang(v);
        animateTic(v);

        ( (HorizontalScrollView) v.findViewById(R.id.horizontal_scrollview_settings) ).fling(1000);

        return v;
    }

    private void animateHang(View view) {

        AnimatedVectorDrawable drawable = getHangAVD(view);

        if (drawable != null) {
            drawable.setTint(mColors[mColorCountPrimary]);
            mHangmanImage.setImageDrawable(drawable);
            drawable.start();
        }

    }
    private void animateTic(View view) {

        AnimatedVectorDrawable drawable = getTicAVD(view);

        if (drawable != null) {
            drawable.setTint(mColors[mColorCountPrimary]);
            mTicTacToeImage.setImageDrawable(drawable);
            drawable.start();
        }

    }
    private int nextColor(int colorCount) {
        colorCount++;
        if (colorCount == 12) colorCount = 0;
        if (colorCount == 8) colorCount++;
        return colorCount;
    }
    private void setImageColor() {
        mHangmanImage.getDrawable().setTint(mColors[mColorCountPrimary]);
        mTicTacToeImage.getDrawable().setTint(mColors[mColorCountPrimary]);
 //       for (int i = 0; i < 9; i++) mPrimaryColor[i].getDrawable().setTint(mColors[i]);
    }


    private void setImageViews(View view) {
        mHangmanImage = (ImageView) view.findViewById(R.id.hangman_image_settings_fragment);
        mHangmanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHangmanCount += 1;
                if (mHangmanCount == 7){
                    mHangmanCount = 0;
                }
                animateHang(v);
            }
        });

        mTicTacToeImage = (ImageView) view.findViewById(R.id.tictactoe_image);
        mTicTacToeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTicTacToeCount += 1;
                if (mTicTacToeCount == 4){
                    mTicTacToeCount = 0;
                }
                animateTic(v);
            }
        });
    }
    private void setButton(View view) {
        mRecordButton = (Button) view.findViewById(R.id.getRecordButton);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String record = mGameBaseHelper.getQuickRecord(mName.getText().toString().trim());
                Toast t = Toast.makeText(v.getContext(), record, Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        });
    }
    private void setPrimaryColor(View view) {
        mPrimaryColor = new ImageButton[11];
        mPrimaryColor[0] = (ImageButton) view.findViewById(R.id.color_selector_0);
        mPrimaryColor[1] = (ImageButton) view.findViewById(R.id.color_selector_1);
        mPrimaryColor[2] = (ImageButton) view.findViewById(R.id.color_selector_2);
        mPrimaryColor[3] = (ImageButton) view.findViewById(R.id.color_selector_3);
        mPrimaryColor[4] = (ImageButton) view.findViewById(R.id.color_selector_4);
        mPrimaryColor[5] = (ImageButton) view.findViewById(R.id.color_selector_5);
        mPrimaryColor[6] = (ImageButton) view.findViewById(R.id.color_selector_6);
        mPrimaryColor[7] = (ImageButton) view.findViewById(R.id.color_selector_7);
        mPrimaryColor[8] = (ImageButton) view.findViewById(R.id.color_selector_8);
        mPrimaryColor[9] = (ImageButton) view.findViewById(R.id.color_selector_9);
        mPrimaryColor[10] = (ImageButton) view.findViewById(R.id.color_selector_10);

        for (int i = 0; i < 11; i++) {
            mPrimaryColor[i].getDrawable().setTint(mColors[i]);
            final int j = i;

            mPrimaryColor[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainMenuActivity.playSoundEffect();
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.color_chooser_avd);
                    d.setTint(mColors[j]);
                    mPrimaryColor[j].setImageDrawable(d);
                    d.start();

                    mColorCountPrimary = j;
                    setImageColor();
                }
            });
        }


    }
    private void setPlayerNames(View view) {
        mName = (EditText) view.findViewById(R.id.player_one_name);

        if (playerNum == 1) {

            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mName.setText(sp.getString(PLAYER_NAME, "Player One"));

        }

        else {
            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mName.setText(sp.getString(PLAYER_NAME, "Player Two"));
        }
    }
    private AnimatedVectorDrawable getHangAVD(View view) {
        if (mHangmanCount == 0) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_0_to_1);
        if (mHangmanCount == 1) return(AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_1_to_2);
        if (mHangmanCount == 2) return(AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_2_to_3);
        if (mHangmanCount == 3) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_3_to_4);
        if (mHangmanCount == 4) return(AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_4_to_5);
        if (mHangmanCount == 5) return(AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_5_to_6);
        if (mHangmanCount == 6) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_6_to_0);
        return null;
    }
    private AnimatedVectorDrawable getTicAVD(View view) {
        if (mTicTacToeCount == 0) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_o);
        if (mTicTacToeCount == 1) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_tictactoe_o_to_x);
        if (mTicTacToeCount == 2) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_tictactoe_x_to_o);
        if (mTicTacToeCount == 3) return (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_tictactoe_o_to_empty);
        return null;
    }
    private void setColorArrays(View view){
        mColors = new int[12]; mColorsNames = new String[12];
        mColors[0] = view.getContext().getColor(R.color.cadmiumYellow); mColorsNames[0] = "Cadmium Yellow";
        mColors[1] = view.getContext().getColor(R.color.burntSienna); mColorsNames[1] = "Burnt Sienna";
        mColors[2] = view.getContext().getColor(R.color.darkOrchid3); mColorsNames[2] = "Dark Orchid 3";
        mColors[3] = view.getContext().getColor(R.color.fireBrick2); mColorsNames[3] = "Fire Brick 2";
        mColors[4] = view.getContext().getColor(R.color.cobalt); mColorsNames[4] = "Cobalt";
        mColors[5] = view.getContext().getColor(R.color.forestGreen); mColorsNames[5] = "Forest Green";
        mColors[6] = view.getContext().getColor(R.color.gold2); mColorsNames[6] = "Gold 2";
        mColors[7] = view.getContext().getColor(R.color.coldGrey); mColorsNames[7] = "Cold Grey";
        mColors[8] = view.getContext().getColor(R.color.lightSkyBlue); mColorsNames[8] = "Light Sky Blue";
        mColors[9] = view.getContext().getColor(R.color.seaGreen); mColorsNames[9] = "Sea Green";
        mColors[10] = view.getContext().getColor(R.color.mintCream); mColorsNames[10] = "Mint Cream";
        mColors[11] = view.getContext().getColor(R.color.colorAccent); mColorsNames[10] = "Gray 1";
    }
    private void setTextColor() {
        mName.setTextColor(mColors[getTextColor()]);
    }

    public int getTextColor() {
         return 10;
    }



    public EditText getName() {
        return mName;
    }


    public void savePlayerInfo() {
        SharedPreferences sp;
        if (playerNum == 1) {
            sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
        }
        else {
            sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLAYER_NAME, mName.getText().toString().trim());
        editor.putInt(PRIMARY_COLOR, mColors[mColorCountPrimary]);
        editor.putInt(PRIMARY_COLOR_DARKEN, getDarken());
        editor.putInt(PRIMARY_COLOR_COUNT, mColorCountPrimary);
        editor.putInt(SECONDARY_COLOR, mColors[getTextColor()]);

        editor.apply();
        }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public void update() {
            if (playerNum == 1) {
                SharedPreferences sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
                mName.setText(sp.getString(PLAYER_NAME, "Player One"));
                mColorCountPrimary = sp.getInt(PRIMARY_COLOR_COUNT, 1);

            } else if (playerNum == 2) {
                SharedPreferences sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
                mName.setText(sp.getString(PLAYER_NAME, "Player Two"));
                mColorCountPrimary = sp.getInt(PRIMARY_COLOR_COUNT, 1);

            }

            setImageColor();
            setTextColor();
    }

    private int getDarken() {
             if (mColorCountPrimary == 0 ) return 45;
        else if (mColorCountPrimary == 1 ) return 65;
        else if (mColorCountPrimary == 2 ) return 35;
        else if (mColorCountPrimary == 3 ) return 65;
        else if (mColorCountPrimary == 4 ) return 65;
        else if (mColorCountPrimary == 5 ) return 65;
        else if (mColorCountPrimary == 6 ) return 125;
        else if (mColorCountPrimary == 7 ) return 30;
        else if (mColorCountPrimary == 8 ) return -65;
        else if (mColorCountPrimary == 9 ) return 65;
        else if (mColorCountPrimary == 10) return -65;
        else if (mColorCountPrimary == 11) return 65;
        else return 0;

        }





}
