package com.noveltystudios.android.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by tstavets on 7/11/17.
 */

public class SuperTicTacToeHangmanFragment extends AbstractGameFragment {

    private boolean mStarted;

    private ImageView[] mImageViews;
    private ArrayList<Integer> mXTiles, mOTiles, mXBoards, mOBoards;
    private ImageView mTurnImage;
    private boolean mXTurn;
    private int p1HangmanCount, p2HangmanCount;

    private PlayerMoveListener mListener;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_super_tic_tac_toe_hangman_board, container, false);

        p1HangmanCount = 0; p2HangmanCount = 0;
        mXTurn = true;
        mXTiles = new ArrayList<>(0);
        mOTiles = new ArrayList<>(0);
        mXBoards = new ArrayList<>(0);
        mOBoards = new ArrayList<>(0);

        setPlayerStyles();
        setImageViews(v);
        setTurnImage(v);
        attatchStyleColors();
        deactivateAll();



        return v;
    }


    private void move(int boardNum, int tileNum, int index, View view) {
        mListener.tileTapped(boardNum, tileNum, index);
    }
    public void deactivateAll() {
        for (int i = 0; i < mImageViews.length; i++) {
            mImageViews[i].setBackgroundColor(getContext().getColor(R.color.gray1));
            mImageViews[i].setAlpha((float) 0.3);
        }
    }
    public void activate(int boardNum, int playerNum) {
        PlayerStyle temp = null;
        if (playerNum == 1)  temp = p1;
        else temp = p2;

        if (boardNum == 0) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i].setAlpha((float) 1);
                //mImageViews[i].setBackgroundColor(temp.getTextColor());
            }
        }
        else if (boardNum == 1) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 9].setAlpha((float) 1);
                //mImageViews[i + 9].setBackgroundColor(temp.getTextColor());
            }
        }
        else if (boardNum == 2) {
                mImageViews[64].setAlpha((float) 1);
                //mImageViews[64].setBackgroundColor(temp.getTextColor());
        }
        else if (boardNum == 3) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 18].setAlpha((float) 1);
                //mImageViews[i + 18].setBackgroundColor(temp.getTextColor());
            }
        }
       else if (boardNum == 4) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 27].setAlpha((float) 1);
                //mImageViews[i + 27].setBackgroundColor(temp.getTextColor());
            }
        }
        else if (boardNum == 5) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 36].setAlpha((float) 1);
                //mImageViews[i + 36].setBackgroundColor(temp.getTextColor());
            }
        }
        else if (boardNum == 6) {
                mImageViews[63].setAlpha((float) 1);
                //mImageViews[63].setBackgroundColor(temp.getTextColor());
        }
        else if (boardNum == 7) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 45].setAlpha((float) 1);
                //mImageViews[i + 45].setBackgroundColor(temp.getTextColor());
            }
        }
        else if (boardNum == 8) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 54].setAlpha((float) 1);
                //mImageViews[i + 54].setBackgroundColor(temp.getTextColor());
            }
        }
    }
    public void setBoardToVictorColor(int boardNum, int playerNum) {
        PlayerStyle temp = null; ArrayList<Integer> boardKeeper;
        if (playerNum == 1) {
            temp = p1;
            boardKeeper = mXBoards;
        }
        else {
            temp = p2;
            boardKeeper = mOBoards;
        }

        if (boardNum == 0) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i].setAlpha((float) 0.8);
                boardKeeper.add(i);
            }
        }
        else if (boardNum == 1) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 9].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 9].setAlpha((float) 0.8);
                boardKeeper.add(i + 9);
            }
        }
        else if (boardNum == 2) {
            mImageViews[64].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
            mImageViews[64].setAlpha((float) 0.8);
            boardKeeper.add(64);
        }
        else if (boardNum == 3) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 18].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 18].setAlpha((float) 0.8);
                boardKeeper.add(i + 18);
            }
        }
        if (boardNum == 4) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 27].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 27].setAlpha((float) 0.8);
                boardKeeper.add(i + 27);
            }
        }
        if (boardNum == 5) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 36].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 36].setAlpha((float) 0.8);
                boardKeeper.add(i + 36);
            }
        }
        if (boardNum == 6) {
            mImageViews[63].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
            mImageViews[63].setAlpha((float) 0.8);
            boardKeeper.add(64);
        }
        if (boardNum == 7) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 45].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 45].setAlpha((float) 0.8);
                boardKeeper.add(i + 45);
            }
        }
        if (boardNum == 8) {
            for (int i = 0; i < 9; i++) {
                mImageViews[i + 54].setBackgroundColor(temp.getPrimaryColor()+temp.getDarken());
                mImageViews[i + 54].setAlpha((float) 0.8);
                boardKeeper.add(i + 54);
            }
        }
    }
    public void setXTurn(boolean xTurn) {mXTurn = xTurn;}

    @Override
    void updateBoard() {
        mIsXTurn = isXTurn();
        setPlayerStyles();

        for (int i = 0; i < 62; i++) {

            if (mXTiles.contains(i)) {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
                mImageViews[i].getDrawable().setTint(p1.getPrimaryColor());
            }
            else if (mOTiles.contains(i)) {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_o));
                mImageViews[i].getDrawable().setTint(p2.getPrimaryColor());
            }
            else {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines));
                mImageViews[i].getDrawable().setTint(getContext().getColor(R.color.burntSienna));
            }

            if (mXBoards.contains(i)) {
                mImageViews[i].setBackgroundColor(p1.getPrimaryColor()+p1.getDarken());
                mImageViews[i].setAlpha((float) 0.8);
            }
            else if (mOBoards.contains(i)) {
                mImageViews[i].setBackgroundColor(p2.getPrimaryColor()+p2.getDarken());
                mImageViews[i].setAlpha((float) 0.8);
            }
        }

        mImageViews[63].setImageDrawable(mImageViews[63].getDrawable());
        mImageViews[63].getDrawable().setTint(p1.getPrimaryColor());

        mImageViews[64].setImageDrawable(mImageViews[64].getDrawable());
        mImageViews[64].getDrawable().setTint(p2.getPrimaryColor());


        if (mIsXTurn) {
            mTurnImage.setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
            mTurnImage.getDrawable().setTint(p1.getPrimaryColor());
        }
        else {
            mTurnImage.setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_o));
            mTurnImage.getDrawable().setTint(p2.getPrimaryColor());
        }
    }

    @Override
    public void resetNoAnim() {
        p1HangmanCount = 0; p2HangmanCount = 0;
        mXTurn = true;
        mStarted = false;

        resetNoAnim();
        //  mImageViews[63].setImageDrawable(getActivity().getDrawable(R.drawable.avd_6_to_0));
        mImageViews[63].getDrawable().setTint(p1.getPrimaryColor());
        //  mImageViews[64].setImageDrawable(getActivity().getDrawable(R.drawable.avd_6_to_0));
        mImageViews[64].getDrawable().setTint(p2.getPrimaryColor());

        mXTiles = new ArrayList<>(0);
        mOTiles = new ArrayList<>(0);
        mXTiles = new ArrayList<>(0);
        mOTiles = new ArrayList<>(0);
        mXBoards = new ArrayList<>(0);
        mOBoards = new ArrayList<>(0);

        deactivateAll();
    }

    @Override
    public void gameOver() {

    }

    @Override
    public void saveGame() {
    }

    @Override
    public void setButtons() {

    }

    @Override
    public boolean isGameWon() {
        return false;
    }

    @Override
    public boolean isGameFull() {
        return false;
    }

    @Override
    public boolean isGameWonX() {
        return false;
    }

    @Override
    public boolean isGameWonO() {
        return false;
    }

    @Override
    public boolean isXTurn() {
        return false;
    }

    private void setImageViews(View v) {
        mImageViews = new ImageView[65];

        mImageViews[0] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton1);
        mImageViews[1] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton2);
        mImageViews[2] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton3);
        mImageViews[3] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton11);
        mImageViews[4] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton12);
        mImageViews[5] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton13);
        mImageViews[6] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton21);
        mImageViews[7] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton22);
        mImageViews[8] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton23);

        mImageViews[9] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton4);
        mImageViews[10] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton5);
        mImageViews[11] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton6);
        mImageViews[12] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton14);
        mImageViews[13] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton15);
        mImageViews[14] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton16);
        mImageViews[15] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton24);
        mImageViews[16] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton25);
        mImageViews[17] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton26);

        mImageViews[18] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton31);
        mImageViews[19] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton32);
        mImageViews[20] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton33);
        mImageViews[21] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton41);
        mImageViews[22] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton42);
        mImageViews[23] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton43);
        mImageViews[24] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton51);
        mImageViews[25] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton52);
        mImageViews[26] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton53);

        mImageViews[27] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton34);
        mImageViews[28] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton35);
        mImageViews[29] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton36);
        mImageViews[30] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton44);
        mImageViews[31] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton45);
        mImageViews[32] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton46);
        mImageViews[33] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton54);
        mImageViews[34] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton55);
        mImageViews[35] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton56);

        mImageViews[36] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton37);
        mImageViews[37] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton38);
        mImageViews[38] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton39);
        mImageViews[39] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton47);
        mImageViews[40] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton48);
        mImageViews[41] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton49);
        mImageViews[42] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton57);
        mImageViews[43] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton58);
        mImageViews[44] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton59);

        mImageViews[45] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton64);
        mImageViews[46] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton65);
        mImageViews[47] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton66);
        mImageViews[48] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton74);
        mImageViews[49] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton75);
        mImageViews[50] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton76);
        mImageViews[51] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton84);
        mImageViews[52] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton85);
        mImageViews[53] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton86);

        mImageViews[54] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton67);
        mImageViews[55] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton68);
        mImageViews[56]= (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton69);
        mImageViews[57] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton77);
        mImageViews[58] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton78);
        mImageViews[59] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton79);
        mImageViews[60] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton87);
        mImageViews[61] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton88);
        mImageViews[62] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanButton89);

        mImageViews[63] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanPlayerOneHangman);
        mImageViews[63].setImageDrawable(v.getContext().getDrawable(R.drawable.avd_6_to_0));

        mImageViews[64] = (ImageView) v.findViewById(R.id.SuperTicTacToeHangmanPlayerTwoHangman);
        mImageViews[64].setImageDrawable(v.getContext().getDrawable(R.drawable.avd_6_to_0));

        int index = 0;
        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(0, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(1, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(3, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(4, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(5, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(7, j, k, v);
                }
            });

            index++;
        }

        for (int i = 0; i < 9; i++) {
            final int j = i;
            final int k = index;
            mImageViews[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    move(8, j, k, v);
                }
            });

            index++;
        }

        mImageViews[63].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.hangmanTapped(0);
            }
        });

        mImageViews[64].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.hangmanTapped(2);
            }
        });

        for (int i = 0; i < 65; i++) {
            mImageViews[i].getDrawable().setTint(getContext().getColor(R.color.burntSienna));
        }

    }
    private void setTurnImage(View v) {
        mTurnImage = (ImageView) v.findViewById(R.id.turnImage_SuperTTTHM);
        mTurnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mStarted){
                    animateTurnImage();
                    mListener.firstPlayerChanged();
                }

                if (mXTurn) {
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_to_x);
                    d.setTint(p1.getPrimaryColor());
                    mTurnImage.setImageDrawable(d);
                    d.start();
                }
                else {
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_to_o);
                    d.setTint(p2.getPrimaryColor());
                    mTurnImage.setImageDrawable(d);
                    d.start();
                }
            }
        });
    }
    private AnimatedVectorDrawable getHangAVD(int hangmanCount) {
        if (hangmanCount == 0) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_0_to_1);
        if (hangmanCount == 1) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_1_to_2);
        if (hangmanCount == 2) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_2_to_3);
        if (hangmanCount == 3) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_3_to_4);
        if (hangmanCount == 4) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_4_to_5);
        if (hangmanCount == 5) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_5_to_6);
        if (hangmanCount == 6) return  (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_hanged);
        return null;
    }
    public void attatchStyleColors() {
        for (int i = 0; i < 63; i++) {
            mImageViews[i].getDrawable().setTint(getContext().getColor(R.color.burntSienna));
        }
        mImageViews[63].getDrawable().setTint(p1.getPrimaryColor());
        mImageViews[64].getDrawable().setTint(p2.getPrimaryColor());

        if (mXTurn) {
            mTurnImage.getDrawable().setTint(p1.getPrimaryColor());
        }
        else {
            mTurnImage.getDrawable().setTint(p2.getPrimaryColor());
        }
    }


    public void animateTileToX(int index) {
        mListener.buttonPressed(-2);
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_x);
        d.setTint(p1.getPrimaryColor());
        mImageViews[index].setImageDrawable(d);
        d.start();

        mXTiles.add(index);
    }
    public void animateTileToO(int index) {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_o);
        d.setTint(p2.getPrimaryColor());
        mImageViews[index].setImageDrawable(d);
        d.start();

        mOTiles.add(index);
    }
    public void animateTurnImage() {
        if (!mXTurn) {
            AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_to_x);
            d.setTint(p1.getPrimaryColor());
            mTurnImage.setImageDrawable(d);
            d.start();
        }
        else {
            AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_to_o);
            d.setTint(p2.getPrimaryColor());
            mTurnImage.setImageDrawable(d);
            d.start();
        }
        mXTurn = !mXTurn;
    }
    public void animateP1Hangman() {
        AnimatedVectorDrawable d = getHangAVD(p1HangmanCount);
        d.setTint(p2.getPrimaryColor());
        mImageViews[64].setImageDrawable(d);
        d.start();
        p1HangmanCount++;
    }
    public void animateP2Hangman() {
        AnimatedVectorDrawable d = getHangAVD(p2HangmanCount);
        d.setTint(p1.getPrimaryColor());
        mImageViews[63].setImageDrawable(d);
        d.start();
        p2HangmanCount++;
    }
    public void animateP1HangmanWin() {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_saved);
        d.setTint(p1.getPrimaryColor());
        mImageViews[64].setImageDrawable(d);
        d.start();
    }
    public void animateP2HangmanWin() {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_saved);
        d.setTint(p2.getPrimaryColor());
        mImageViews[63].setImageDrawable(d);
        d.start();
    }
    public void animateResetImages() {
        for (int i = 0; i < 63; i++) {
            AnimatedVectorDrawable d = null;
            if (mOTiles.indexOf(i) > -1) {
                d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_to_empty);
            }
            else if (mXTiles.contains(i)) {
                d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_to_empty);
            }
            else {
                // d = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_tictactoe_empty_rotator);
            }
            if ( d != null) {
                d.setTint(getContext().getColor(R.color.burntSienna));
                mImageViews[i].setImageDrawable(d);
                mImageViews[i].setBackgroundColor(Color.BLACK);
                d.start();
            }
            else {
                Drawable drawable = getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines);
                drawable.setTint(getContext().getColor(R.color.burntSienna));
                mImageViews[i].setImageDrawable(drawable);
                mImageViews[i].setBackgroundColor(Color.BLACK);

            }
        }
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_6_to_0);
        d.setTint(p1.getPrimaryColor());
        mImageViews[63].setImageDrawable(d);
        mImageViews[63].setBackgroundColor(Color.BLACK);
        d.start();

        AnimatedVectorDrawable d2 = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_6_to_0);
        d2.setTint(p2.getPrimaryColor());
        mImageViews[64].setImageDrawable(d2);
        mImageViews[64].setBackgroundColor(Color.BLACK);
        d2.start();
    }
    public void resetImagesNoAnim() {
        for (int i = 0; i < 63; i++) {
                Drawable drawable = getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines);
                drawable.setTint(getContext().getColor(R.color.burntSienna));
                mImageViews[i].setImageDrawable(drawable);
                mImageViews[i].setBackgroundColor(Color.BLACK);
        }
        Drawable d = getContext().getDrawable(R.drawable.hangman_0);
        d.setTint(p1.getPrimaryColor());
        mImageViews[63].setImageDrawable(d);
        mImageViews[63].setBackgroundColor(Color.BLACK);

        Drawable d2 = getContext().getDrawable(R.drawable.hangman_0);
        d2.setTint(p2.getPrimaryColor());
        mImageViews[64].setImageDrawable(d2);
        mImageViews[64].setBackgroundColor(Color.BLACK);
    }

    public void setP1HangmanCount(int p1HangmanCount) {
        this.p1HangmanCount = p1HangmanCount;
    }

    public void setP2HangmanCount(int p2HangmanCount) {
        this.p2HangmanCount = p2HangmanCount;
    }


    public void setStarted(boolean started) {
        mStarted = started;
    }
    public boolean isStarted() {
        return mStarted;
    }

    public void reset() {
        p1HangmanCount = 0; p2HangmanCount = 0;
        mXTurn = true;
        mStarted = false;

        animateResetImages();
      //  mImageViews[63].setImageDrawable(getActivity().getDrawable(R.drawable.avd_6_to_0));
        mImageViews[63].getDrawable().setTint(p1.getPrimaryColor());
      //  mImageViews[64].setImageDrawable(getActivity().getDrawable(R.drawable.avd_6_to_0));
        mImageViews[64].getDrawable().setTint(p2.getPrimaryColor());

        mXTiles = new ArrayList<>(0);
        mOTiles = new ArrayList<>(0);
        mXTiles = new ArrayList<>(0);
        mOTiles = new ArrayList<>(0);
        mXBoards = new ArrayList<>(0);
        mOBoards = new ArrayList<>(0);

        deactivateAll();
    }

    public void victorAnim(boolean xIsVictor) {
        if (xIsVictor) {
            for (int i = 0; i < mXTiles.size(); i++) {
                AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_rotate);
                d.setTint(p1.getPrimaryColor());
                mImageViews[mXTiles.get(i)].setImageDrawable(d);
                d.start();
            }
        }
        else {
            for (int i = 0; i < mOTiles.size(); i++) {
                AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_thicken);
                d.setTint(p2.getPrimaryColor());
                mImageViews[mOTiles.get(i)].setImageDrawable(d);
                d.start();
            }
        }
    }



    public interface PlayerMoveListener {
        public void hangmanTapped(int num);

        public void activate(int num);

        public void deactivate(int num);

        public void tileTapped(int boardNum, int tileNum, int index);

        public void firstPlayerChanged();

        public void buttonPressed(int i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PlayerMoveListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }

}
