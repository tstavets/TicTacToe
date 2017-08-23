package com.noveltystudios.android.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * Created by tstavets on 8/16/17.
 */

public class PlayerEditorDialog extends DialogFragment implements AbstractStrings {


    private ImageButton[] mPrimaryColor;
    private EditText mName;
    private int mColorCountPrimary;
    private int[] mColors;
    private int mPlayerNum;
    private String mTitle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_player_editor, null);

        setColorCount();
        setColorArrays(v);
        setName(v);
        setPrimaryColor(v);

        return  new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(mTitle)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp;
                                if (mPlayerNum == 1) {
                                    sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
                                }
                                else {
                                    sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
                                }

                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(PLAYER_NAME, mName.getText().toString().trim());
                                editor.putInt(PRIMARY_COLOR, mColors[mColorCountPrimary]);
                                editor.putInt(PRIMARY_COLOR_DARKEN, getDarken());
                                editor.putInt(SECONDARY_COLOR, mColors[getTextColor()]);
                                editor.putInt(PRIMARY_COLOR_COUNT, mColorCountPrimary);

                                editor.apply();


                                if (getTargetFragment() == null) {
                                    return;
                                }

                                Intent intent = new Intent();
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            }
                        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (getTargetFragment() == null) {
                            return;
                        }
                        Intent intent = new Intent();
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                    }
                })
                .create();
    }

    public static PlayerEditorDialog newInstance(int playerNum, String title) {
        PlayerEditorDialog fragment = new PlayerEditorDialog();
        fragment.setPlayerNum(playerNum);
        fragment.setTitle(title);
        return fragment;
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
                }
            });
        }
    }

    private void setColorArrays(View view){
        mColors = new int[12];
        mColors[0] = view.getContext().getColor(R.color.cadmiumYellow);
        mColors[1] = view.getContext().getColor(R.color.burntSienna);
        mColors[2] = view.getContext().getColor(R.color.darkOrchid3);
        mColors[3] = view.getContext().getColor(R.color.fireBrick2);
        mColors[4] = view.getContext().getColor(R.color.cobalt);
        mColors[5] = view.getContext().getColor(R.color.forestGreen);
        mColors[6] = view.getContext().getColor(R.color.gold2);
        mColors[7] = view.getContext().getColor(R.color.coldGrey);
        mColors[8] = view.getContext().getColor(R.color.lightSkyBlue);
        mColors[9] = view.getContext().getColor(R.color.seaGreen);
        mColors[10] = view.getContext().getColor(R.color.mintCream);
        mColors[11] = view.getContext().getColor(R.color.colorAccent);
    }

    private void setColorCount() {
        if (mPlayerNum == 1) {

            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mColorCountPrimary = sp.getInt(PRIMARY_COLOR_COUNT, 0);

        } else {
            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mColorCountPrimary = sp.getInt(PRIMARY_COLOR_COUNT, 0);
        }
    }

    private void setName(View view) {
        mName = (EditText) view.findViewById(R.id.player_one_name);

        if (mPlayerNum == 1) {

            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_ONE_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mName.setText(sp.getString(PLAYER_NAME, "Player One"));

        }

        else {
            SharedPreferences sp = getContext().getSharedPreferences(PLAYER_TWO_SHAREDPREFERENCE, Context.MODE_PRIVATE);
            mName.setText(sp.getString(PLAYER_NAME, "Player Two"));
        }
    }

    public void setPlayerNum(int playerNum){
        mPlayerNum = playerNum;
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
    public int getTextColor() {
        return 10;
    }


    public void setTitle(String title) {
        mTitle = title;
    }

}
