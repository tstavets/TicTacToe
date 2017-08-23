package com.noveltystudios.android.tictactoe;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tstavets on 7/8/17.
 */

public class HangmanGameFragment extends AbstractGameFragment implements AbstractStrings {

    protected int hangManCount;
    protected AnimatedVectorDrawable zero, one, two, three, four, five, hang, saved;
    protected ImageView mImageView;
    protected Button mDeleteButton, mSendButton;
    protected Button[] alphabetButtons;
    protected TextView mWord, mWordProgress;
    protected HangmanModel mHangmanModel;
    protected PlayerStyle mPlayerStyle;
    protected boolean mActive;
    protected int playerNum;
    protected View mView;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hangman_game, container, false);

        mActive = true;

        hangManCount = 0;

        setPlayerStyles();
        if (playerNum == 1) mPlayerStyle = p1;
        else mPlayerStyle = p2;


        setAVDs(v);

        mImageView = (ImageView) v.findViewById(R.id.mainMenuHangmanImage);
        mImageView.setImageDrawable(v.getContext().getDrawable(R.drawable.avd_6_to_0));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpEasterEgg(v);
            }
        });

        setColor();

        mHangmanModel = new HangmanModel();

        mView = v;
        alphabetButtons = new Button[26];
        setButtons();

        mSendButton.setVisibility(View.INVISIBLE);


        mWord = (TextView) v.findViewById(R.id.theWord);
        mWord.setText("");

        mWordProgress = (TextView) v.findViewById(R.id.wordProgress);
        mWordProgress.setText("");

        mGameSaved = false;





        return v;
    }


    @Override
    void updateBoard() {
        setPlayerStyles();
        if (playerNum == 1) mPlayerStyle = p1;
        else mPlayerStyle = p2;

        if(!mHangmanModel.isGameStarted()) {
            reset();
        }
        setColor();
    }

    @Override
    public void resetNoAnim() {
        reset();
    }

    @Override
    public void gameOver() {
        if(mHangmanModel.isGameOver()) {
            saveGame();
        }
    }

    @Override
    public void saveGame() {
        if (!mGameSaved) {
            String victor = DRAW;
            if (isGameWonO()) victor = p2.getPlayerName();
            else if (isGameWonX()) victor = p1.getPlayerName();

            dbHelper.addGame(getString(R.string.HANGMAN), p1.getPlayerName(), p2.getPlayerName(), victor);
            mGameSaved = true;
        }
    }

    @Override
    public void setButtons() {
        setDeleteButton(mView);
        setAlphabetButtons(mView);
        setSendButton(mView);
    }

    @Override
    public boolean isGameWon() {
        return mHangmanModel.isGameWon();
    }

    @Override
    public boolean isGameFull() {
        return mHangmanModel.isGameLost();
    }

    @Override
    public boolean isGameWonX() {
        if (playerNum == 1) return isGameFull();
        else return isGameWon();
    }

    @Override
    public boolean isGameWonO() {
        if (playerNum == 2) return isGameFull();
        else return isGameWon();
    }

    @Override
    public boolean isXTurn() {
        return false;
    }

    public void animate() {
        AnimatedVectorDrawable drawable = null;
        if (hangManCount == 0) drawable = zero;
        else if (hangManCount == 1) drawable = one;
        else if (hangManCount == 2) drawable = two;
        else if (hangManCount == 3) drawable = three;
        else if (hangManCount == 4) drawable = four;
        else if (hangManCount == 5) drawable = five;
        else if (mHangmanModel.isGameOver() && mHangmanModel.isGameWon()) drawable = saved;
        else { drawable = hang; mWordProgress.setVisibility(View.INVISIBLE); mWord.setVisibility(View.VISIBLE); mWord.setBackgroundColor(mPlayerStyle.getPrimaryColor()); }

        if (drawable != null) {
            mImageView.setImageDrawable(drawable);
            drawable.start();
        }

        hangManCount += 1;
        if (hangManCount == 7){
            hangManCount = 0;
        }

        setColor();
    }

    protected void setColor() {
        Drawable d = mImageView.getDrawable();
        if (mPlayerStyle != null) d.setTint(mPlayerStyle.getPrimaryColor());
    }

    public void guess(String s, View v) {
        if (mHangmanModel.isGameStarted()) {
            if (mHangmanModel.guess(s) ) {
                mWordProgress.setText(mHangmanModel.getWordProgress());

                if (mHangmanModel.isGameWon()) {
                    mImageView.setImageDrawable(v.getContext().getDrawable(R.drawable.hangman_6));
                    setColor();
                    hangManCount = 6;
                    animate();
                }
            }
            else {
                animate();
            }
            if (isGameWon() || isGameFull()) {
                String victor = p1.getPlayerName();
                int color = p1.getPrimaryColor();

                if (isGameWonO()) {
                    victor = p2.getPlayerName();
                    color = p2.getPrimaryColor();
                }
                Toast t = Toast.makeText(getContext(), victor + " won!", Toast.LENGTH_SHORT);
                t.getView().setBackgroundColor(color);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        }
        gameOver();
    }

    @Override
    public void reset() {

        mHangmanModel = new HangmanModel();

        hangManCount = 0;
        mImageView.setImageDrawable(getContext().getDrawable(R.drawable.avd_6_to_0));
        setColor();

        mImageView.setVisibility(View.INVISIBLE);

        mWord.setText("");
        mWord.setVisibility(View.VISIBLE);

        mWordProgress.setText("");
        mWordProgress.setVisibility(View.INVISIBLE);


        for (int i = 0; i < 26; i ++) alphabetButtons[i].setVisibility(View.VISIBLE);

        mDeleteButton.setVisibility(View.VISIBLE);

        mSendButton.setVisibility(View.INVISIBLE);

    }


    public void jumpEasterEgg(View view) {
        if (mHangmanModel.isGameWon()) {
            AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) view.getContext().getDrawable(R.drawable.avd_jump);
            mImageView.setImageDrawable(drawable);
            setColor();
            drawable.start();
        }
    }

    private void setAVDs(View v) {
        zero  = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_0_to_1);
        one   = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_1_to_2);
        two   = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_2_to_3);
        three = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_3_to_4);
        four  = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_4_to_5);
        five  = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_5_to_6);
        hang  = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_hanged);
        saved = (AnimatedVectorDrawable) v.getContext().getDrawable(R.drawable.avd_saved);
    }
    private void setDeleteButton(View v) {
        mDeleteButton = (Button) v.findViewById(R.id.deleteButton);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWord.getText().length() >= 1) mWord.setText(mWord.getText().toString().substring(0, mWord.getText().length()-1));

                if (mWord.getText().length() == 0) mSendButton.setVisibility(View.INVISIBLE);

                mListener.buttonPressed(-2);

            }
        });
    }
    protected void setSendButton(View v) {
        mSendButton = (Button) v.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteButton.setVisibility(View.INVISIBLE);
                mSendButton.setVisibility(View.INVISIBLE);
                mWord.setVisibility(View.INVISIBLE);

                mWordProgress.setVisibility(View.VISIBLE);

                mImageView.setVisibility(View.VISIBLE);

                mHangmanModel.chooseTheWord(mWord.getText().toString());
                mWordProgress.setText(mHangmanModel.getWordProgress());

                mListener.buttonPressed(-2);

            }
        });
    }
    protected void setAlphabetButtons(View v) {
        alphabetButtons[0] = (Button) v.findViewById(R.id.Q_Button);
        alphabetButtons[1] = (Button) v.findViewById(R.id.W_Button);
        alphabetButtons[2] = (Button) v.findViewById(R.id.E_Button);
        alphabetButtons[3] = (Button) v.findViewById(R.id.R_Button);
        alphabetButtons[4] = (Button) v.findViewById(R.id.T_Button);
        alphabetButtons[5] = (Button) v.findViewById(R.id.Y_Button);
        alphabetButtons[6] = (Button) v.findViewById(R.id.U_Button);
        alphabetButtons[7] = (Button) v.findViewById(R.id.I_Button);
        alphabetButtons[8] = (Button) v.findViewById(R.id.O_Button);
        alphabetButtons[9] = (Button) v.findViewById(R.id.P_Button);
        alphabetButtons[10] = (Button) v.findViewById(R.id.A_Button);
        alphabetButtons[11] = (Button) v.findViewById(R.id.S_Button);
        alphabetButtons[12] = (Button) v.findViewById(R.id.D_Button);
        alphabetButtons[13] = (Button) v.findViewById(R.id.F_Button);
        alphabetButtons[14] = (Button) v.findViewById(R.id.G_Button);
        alphabetButtons[15] = (Button) v.findViewById(R.id.H_Button);
        alphabetButtons[16] = (Button) v.findViewById(R.id.J_Button);
        alphabetButtons[17] = (Button) v.findViewById(R.id.K_Button);
        alphabetButtons[18] = (Button) v.findViewById(R.id.L_Button);
        alphabetButtons[19] = (Button) v.findViewById(R.id.Z_Button);
        alphabetButtons[20] = (Button) v.findViewById(R.id.X_Button);
        alphabetButtons[21] = (Button) v.findViewById(R.id.C_Button);
        alphabetButtons[22] = (Button) v.findViewById(R.id.V_Button);
        alphabetButtons[23] = (Button) v.findViewById(R.id.B_Button);
        alphabetButtons[24] = (Button) v.findViewById(R.id.N_Button);
        alphabetButtons[25] = (Button) v.findViewById(R.id.M_Button);



        for (int i = 0; i < 26; i++) {
            final int j = i;

            alphabetButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mHangmanModel.isGameStarted()){
                        mSendButton.setVisibility(View.VISIBLE);
                        if (mWord.getText().length() < 12) mWord.setText(mWord.getText() + alphabetButtons[j].getText().toString());
                    }
                    else if (!mHangmanModel.isGameOver() && mActive) {
                        guess(alphabetButtons[j].getText().toString(), view);
                        alphabetButtons[j].setVisibility(View.INVISIBLE);
                    }
                    mListener.buttonPressed(-2);
                }
            });
        }
    }

    public boolean isActive() {
        return mActive;
    }

    public void setActive(boolean active) {
        mActive = active;
    }

    public TextView getWord() {
        return mWord;
    }

    public TextView getWordProgress() {
        return mWordProgress;
    }

    public HangmanModel getHangmanModel() {
        return mHangmanModel;
    }

    public void setWordProgress(String s) {
        mWordProgress.setText(s);
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    public boolean started(){
        return mHangmanModel.isGameStarted();
    }

}
