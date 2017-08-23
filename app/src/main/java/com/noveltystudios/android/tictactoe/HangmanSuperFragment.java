package com.noveltystudios.android.tictactoe;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.widget.Button;

/**
 * Created by tstavets on 7/12/17.
 */

public class HangmanSuperFragment extends HangmanGameFragment {

    private HangmanListener mHangmanListener;

    @Override
    protected void setAlphabetButtons(View v) {
        super.setAlphabetButtons(v);
        for (int i = 0; i < 26; i++) {
            final int j = i;

            alphabetButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.buttonPressed(-1);
                    if (!mHangmanModel.isGameStarted()){
                        mSendButton.setVisibility(View.VISIBLE);
                        if (mWord.getText().length() < 12) {
                            mWord.setText(mWord.getText() + alphabetButtons[j].getText().toString());
                        }
                    }
                    else if (!mHangmanModel.isGameOver() && mActive) {
                        alphabetButtons[j].setVisibility(View.INVISIBLE);

                        mHangmanListener.letterGuessed(alphabetButtons[j].getText().toString(), j);
                    }
                }
            });
        }
    }


    public void animateWin() {
        AnimatedVectorDrawable drawable = saved;
        mImageView.setImageDrawable(drawable);
        drawable.start();
        setColor();


    }

    @Override
    protected void setSendButton(View v) {
        mSendButton = (Button) v.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.buttonPressed(-1);
                mDeleteButton.setVisibility(View.INVISIBLE);
                mSendButton.setVisibility(View.INVISIBLE);
                mWord.setVisibility(View.INVISIBLE);

                mWordProgress.setVisibility(View.VISIBLE);

                mImageView.setVisibility(View.VISIBLE);

                mHangmanModel.chooseTheWord(mWord.getText().toString());
                mWordProgress.setText(mHangmanModel.getWordProgress());
                mHangmanListener.wordMade(mWord.getText().toString(), playerNum);
            }
        });
    }

    public interface HangmanListener {
        public void letterGuessed(String guess, int index);
        public void wordMade(String word, int INDEX);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mHangmanListener = (HangmanListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }


}
