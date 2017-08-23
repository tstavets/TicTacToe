package com.noveltystudios.android.tictactoe;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by tstavets on 7/27/17.
 */

public class TicTacToeFragment extends AbstractGameFragment implements AbstractStrings {

    private ImageView[] mButtons;
    private ImageView mTurnImage;
    private ImageButton mComputerImage;
    private Board mBoard;
    private int gameMode;
    private Computer_TTT mCom;

    private View mView;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);

        gameMode = PLAYER_VS_PLAYER;

        mBoard = new Board();
        mBoard.setXTurn(true);

        mIsXTurn = true;

        mCom = new Computer_TTT(mBoard);

        mView = v;
        setPlayerStyles();
        setButtons();
        updateBoard();


        return v;
    }

    @Override
    public void setButtons() {
            mTurnImage = (ImageView) mView.findViewById(R.id.turnImage);
            mTurnImage.setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
            mTurnImage.getDrawable().setTint(p1.getPrimaryColor());
            mTurnImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBoard.getNumTurns()==0) mBoard.setXTurn(!mBoard.isXTurn());
                    animateTurnImage();
                    mListener.buttonPressed(-1);
                }
            });


            mComputerImage = (ImageButton) mView.findViewById(R.id.computer_imageButton);
            mComputerImage.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (gameMode == PLAYER_VS_PLAYER) {
                        gameMode = PLAYER_VS_COM;
                        mComputerImage.setImageResource(R.drawable.icon_computer);
                    }
                    else if (gameMode == PLAYER_VS_COM){
                        gameMode = PLAYER_VS_PLAYER;
                        mComputerImage.setImageResource(R.drawable.icon_person);
                    }
                }
            });


            mButtons = new ImageView[9];
            mButtons[0] = (ImageView) mView.findViewById(R.id.Button0);
            mButtons[1] = (ImageView) mView.findViewById(R.id.Button1);
            mButtons[2] = (ImageView) mView.findViewById(R.id.Button2);
            mButtons[3] = (ImageView) mView.findViewById(R.id.Button3);
            mButtons[4] = (ImageView) mView.findViewById(R.id.Button4);
            mButtons[5] = (ImageView) mView.findViewById(R.id.Button5);
            mButtons[6] = (ImageView) mView.findViewById(R.id.Button6);
            mButtons[7] = (ImageView) mView.findViewById(R.id.Button7);
            mButtons[8] = (ImageView) mView.findViewById(R.id.Button8);

            for (int i = 0; i < 9; i++) {
                setButtonImageToEmptySpace(mButtons[i]);
                final int index = i;
                mButtons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        move(index);
                        mListener.buttonPressed(index);
                    }
                });
            }

    }

    @Override
    public void reset() {
        animateResetImages();

        mBoard.reset();
        mBoard.setXTurn(true);

        mGameSaved = false;

        gameMode = PLAYER_VS_PLAYER;
        mComputerImage.setImageResource(R.drawable.icon_person);


        animateTurnImage();
    }

    @Override
    public void resetNoAnim() {

        mBoard.reset();
        mBoard.setXTurn(true);

        mGameSaved = false;

        gameMode = PLAYER_VS_PLAYER;
        mComputerImage.setImageResource(R.drawable.icon_person);


        updateBoard();

    }


    @Override
    public void gameOver(){
        if(mBoard.isGameWon()) {
            saveGame();
            mListener.buttonPressed(-1);
            victorAnim();
        }
        else if (mBoard.isGameFull()) {
            saveGame();
            mListener.buttonPressed(-1);
        }
    }

    @Override
    public void saveGame() {
        if (!mGameSaved) {
            String victor = DRAW;
            if (mBoard.isGameWonByX()) victor = p1.getPlayerName();
            else if (mBoard.isGameWonByO() && !isTwoPlayer()) victor = COMPUTER_PLAYER;
            else if (mBoard.isGameWonByO()) victor = p2.getPlayerName();


            if (isTwoPlayer()) dbHelper.addGame(getString(R.string.TICTACTOE), p1.getPlayerName(), p2.getPlayerName(), victor);
            else dbHelper.addGame(getString(R.string.TICTACTOE), p1.getPlayerName(), COMPUTER_PLAYER, victor);
            mGameSaved = true;
        }
    }

    @Override
    public void updateBoard(){
        mIsXTurn = mBoard.isXTurn();
        setPlayerStyles();

        for (int i = 0; i < 9; i++) {
            if (mBoard.isTileX(i)) {
                mButtons[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
                mButtons[i].getDrawable().setTint(p1.getPrimaryColor());
            }
            else if (mBoard.isTileO(i)) {
                mButtons[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_o));
                mButtons[i].getDrawable().setTint(p2.getPrimaryColor());
            }
            else {
                mButtons[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines));
                mButtons[i].getDrawable().setTint(getContext().getColor(R.color.burntSienna));
            }
        }

        if (mBoard.isXTurn()) {
            mTurnImage.setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
            mTurnImage.getDrawable().setTint(p1.getPrimaryColor());
        }
        else {
            mTurnImage.setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_o));
            mTurnImage.getDrawable().setTint(p2.getPrimaryColor());
        }
    }

    @Override
    public boolean isGameWon() {
        return mBoard.isGameWon();
    }

    @Override
    public boolean isGameFull() {
        return mBoard.isGameFull();
    }

    @Override
    public boolean isGameWonX() {
        return mBoard.isGameWonByX();
    }

    @Override
    public boolean isGameWonO() {
        return mBoard.isGameWonByO();
    }

    @Override
    public boolean isXTurn() {
        return mBoard.isXTurn();
    }

    public void move(int index) {
        if (mBoard.move(index)) {
            mListener.buttonPressed(-2);
            animateImageButtonUpdate(index);
            animateTurnImage();
            gameOver();


            if (gameMode == PLAYER_VS_COM) {
                computerMove(MINMAX_MOVE);
                animateTurnImage();
                gameOver();
            }
        }
    }

    public void computerMove(int moveType) {
        if (!mBoard.isGameWon() && !mBoard.isGameFull() && !mBoard.isXTurn()) {
            if (moveType == MINMAX_MOVE) mBoard = mCom.neverLoseMove(mBoard);
            if (moveType == RANDOM_MOVE) mBoard = mCom.randomMove(mBoard);
            if (moveType == CYCLIC_MOVE) mBoard = mCom.cyclicMove(mBoard);
            if (moveType == SIMPLE_MOVE) mBoard = mCom.ifCanWinDoWinMove(mBoard);
            if (moveType == DEFENSE_MOVE) mBoard = mCom.dontLetThemWinMove(mBoard);
        }
        animateImageButtonUpdate(mBoard.getLastTurn());
    }

    private void setButtonImageToEmptySpace(ImageView v) {
        Drawable d = getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines);
        d.setTint(getContext().getColor(R.color.burntSienna));
        v.setImageDrawable(d);
    }


    public void animateImageButtonUpdate(int x) {
        if(mBoard.isXTurn()) {
            animateButtonImageToO(mButtons[x]);
        }
        else {
            animateButtonImageToX(mButtons[x]);
        }
    }
    public void animateTurnImage(){
        if(mBoard.isXTurn()) {
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
    private void animateButtonImageToO(ImageView v) {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_o);
        d.setTint(p2.getPrimaryColor());
        v.setImageDrawable(d);
        d.start();
        //  v.setImageDrawable(getDrawable(R.drawable.vector_tictactoe_o));
    }
    private void animateButtonImageToX(ImageView v) {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_x);
        if (d != null) {
            d.setTint(p1.getPrimaryColor());
            v.setImageDrawable(d);
            d.start();
        }
        //v.setImageDrawable(getDrawable(R.drawable.vector_tictactoe_x));
    }
    private void victorAnim() {
        if (mBoard.isGameWonByX()) {
            for (int i = 0; i < 9; i++) {
                if (mBoard.isTileX(i)) {
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_rotate);
                    d.setTint(p1.getPrimaryColor());
                    mButtons[i].setImageDrawable(d);
                    d.start();
                }
            }
        }
        else if (mBoard.isGameWonByO()) {
            for (int i = 0; i < 9; i++) {
                if (mBoard.isTileO(i)) {
                    AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_thicken);
                    d.setTint(p2.getPrimaryColor());
                    mButtons[i].setImageDrawable(d);
                    d.start();
                }
            }
        }
    }




    private void animateResetImages() {
        for (int i = 0; i < 9; i++) {
            AnimatedVectorDrawable d = null;
            if (mBoard.isTileO(i)) {
                d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_to_empty);
            }
            else if (mBoard.isTileX(i)) {
                d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_to_empty);
            }
            else {
                // d = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_tictactoe_empty_rotator);
            }
            if ( d != null) {
                d.setTint(getContext().getColor(R.color.burntSienna));
                mButtons[i].setImageDrawable(d);
                mButtons[i].setBackgroundColor(Color.BLACK);
                d.start();
            }
            else {
                Drawable drawable = getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines);
                drawable.setTint(getContext().getColor(R.color.burntSienna));
                mButtons[i].setImageDrawable(drawable);
                mButtons[i].setBackgroundColor(Color.BLACK);
            }
        }
    }

    public boolean isTwoPlayer() {
        return gameMode == PLAYER_VS_PLAYER;
    }



}
