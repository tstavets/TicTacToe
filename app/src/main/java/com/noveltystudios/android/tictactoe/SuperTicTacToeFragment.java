package com.noveltystudios.android.tictactoe;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noveltystudios.android.tictactoe.database.GameBaseHelper;

/**
 * Created by tstavets on 8/1/17.
 */

public class SuperTicTacToeFragment extends AbstractGameFragment implements AbstractStrings {

    private ImageView[] mImageViews;
    private ImageView mTurnImage, mComputerImageView;;
    private boolean twoPlayer;
    private SuperBoard mSuperBoard;
    private Computer_SuperTTT mCom;

    private View mView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_super_tic_tac_toe, container, false);

        dbHelper = new GameBaseHelper(getContext());
        mGameSaved = false;



        mCom = new Computer_SuperTTT();

        twoPlayer = true;

        mSuperBoard = new SuperBoard();

        mImageViews = new ImageView[81];

        mView = v;
        setPlayerStyles();
        setButtons();

        updateBoardTints();

        return v;
    }

    @Override
    void updateBoard() {
        mIsXTurn = mSuperBoard.isXTurn();
        setPlayerStyles();

        for (int i = 0; i < 81; i++) {
            int tileNum = mSuperBoard.getTileNumber(i);
            int boardNum = mSuperBoard.getBoardNumber(i);

            if (mSuperBoard.isTileWonX(boardNum, tileNum)) {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_x));
                mImageViews[i].getDrawable().setTint(p1.getPrimaryColor());
            }
            else if (mSuperBoard.isTileWonO(boardNum, tileNum)) {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_o));
                mImageViews[i].getDrawable().setTint(p2.getPrimaryColor());
            }
            else {
                mImageViews[i].setImageDrawable(getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines));
                mImageViews[i].getDrawable().setTint(getContext().getColor(R.color.burntSienna));
            }


            setBoardTint(i, boardNum);

        }

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
    public void reset() {
        animateResetImages();

        mSuperBoard = new SuperBoard();
        mCom.reset();

        mGameSaved = false;

        setPlayerStyles();
        updateBoardTints();

        animateTurnImage();
    }

    @Override
    public void resetNoAnim() {
        mSuperBoard = new SuperBoard();
        mCom.reset();

        setPlayerStyles();

        mGameSaved = false;

        updateBoardTints();

        updateBoard();
    }

    @Override
    public void gameOver() {
        if(mSuperBoard.isGameOver()) {
            victorAnim();
            saveGame();
            mListener.buttonPressed(-1);
        }
    }

    @Override
    public void saveGame() {
        if (!mGameSaved) {
            String victor = DRAW;
            if (mSuperBoard.isGameWon() && mSuperBoard.isXWon()) victor = p1.getPlayerName();
            else if (mSuperBoard.isGameWon() && !mSuperBoard.isXWon() && twoPlayer) victor = p2.getPlayerName();
            else if (mSuperBoard.isGameWon() && !mSuperBoard.isXWon() && !twoPlayer) victor = COMPUTER_PLAYER;

            if (twoPlayer) dbHelper.addGame(getString(R.string.SUPERTICTACTOE), p1.getPlayerName(), p2.getPlayerName(), victor);
            else dbHelper.addGame(getString(R.string.SUPERTICTACTOE), p1.getPlayerName(), COMPUTER_PLAYER, victor);
            mGameSaved = true;
        }
    }

    @Override
    public boolean isGameWon() {
        return mSuperBoard.isGameWon();
    }

    @Override
    public boolean isGameFull() {
        return mSuperBoard.isGameFull();
    }

    @Override
    public boolean isGameWonX() {
        return (mSuperBoard.isXWon() && mSuperBoard.isGameWon());
    }

    @Override
    public boolean isGameWonO() {
        return (!mSuperBoard.isXWon() && mSuperBoard.isGameWon());
    }

    @Override
    public boolean isXTurn() {
        return mSuperBoard.isXTurn();
    }


    public void moveCom() {
        if (!mSuperBoard.isGameWon() && !mSuperBoard.isGameFull() && !mSuperBoard.isXTurn()) {
            mSuperBoard = mCom.moveRanking(mSuperBoard);
            mCom.updateBoard(mSuperBoard, mSuperBoard.isXTurn());
            animateImageButtonUpdate(mSuperBoard.getLastMoveIndex());
            updateBoardTints();
            animateTurnImage();
            gameOver();
        }
    }
    public void moveComRandom() {
        if (!mSuperBoard.isGameWon() && !mSuperBoard.isGameFull()) {
            mCom.moveRandom(mSuperBoard);
            mCom.updateBoard(mSuperBoard, mSuperBoard.isXTurn());
            animateImageButtonUpdate(mSuperBoard.getLastMoveIndex());
            updateBoardTints();
            animateTurnImage();
            gameOver();
        }
    }

    public void updateBoardTints() {
        for (int i = 0; i < 81; i++) {
            int boardNum = mSuperBoard.getBoardNumber(i);

            setBoardTint(i, boardNum);

        }
    }

    private void setBoardTint(int i, int boardNum) {
        if (mSuperBoard.isBoardActive(boardNum) && !mSuperBoard.isGameOver()) {
            mImageViews[i].setAlpha((float) 1);
        }
        else {
            if (mSuperBoard.isBoardWonX(boardNum)) {
                mImageViews[i].setBackgroundColor(p1.getPrimaryColor()+ p1.getDarken());
                mImageViews[i].setAlpha((float) 0.8);
            }
            else if (mSuperBoard.isBoardWonO(boardNum)) {
                mImageViews[i].setBackgroundColor(p2.getPrimaryColor()+p2.getDarken());
                mImageViews[i].setAlpha((float) 0.8);
            }
            else {
                mImageViews[i].setBackgroundColor(Color.BLACK);
                int tileNum = mSuperBoard.getTileNumber(i);
                if (!(mSuperBoard.isTileWonO(boardNum, tileNum) || mSuperBoard.isTileWonX(boardNum, tileNum))) {
                    mImageViews[i].setAlpha((float) 0.3);
                }
            }
        }
    }

    public boolean isTwoPlayer() { return  twoPlayer; }


    @Override
    public void setButtons() {
        setTileButtonsArray();
        setTileButtonsArrayOnClickListenter();

        mComputerImageView = (ImageView) mView.findViewById(R.id.computerImageView_SuperTTT);
        mComputerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoPlayer) {
                    twoPlayer = false;
                    mComputerImageView.setImageResource(R.drawable.icon_computer);
                }
                else {
                    twoPlayer = true;
                    mComputerImageView.setImageResource(R.drawable.icon_person);
                }
            }
        });


        mTurnImage = (ImageView) mView.findViewById(R.id.turnImage_SuperTTT);
        mTurnImage.getDrawable().setTint(p1.getPrimaryColor());
        mTurnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoPlayer) {
                    if (mSuperBoard.getNumTurns() == 0)
                        mSuperBoard.setXTurn(!mSuperBoard.isXTurn());
                    animateTurnImage();
                }
                else if (!mSuperBoard.isGameWon() && !mSuperBoard.isGameFull()){
                    if (!mSuperBoard.isXTurn()) moveCom();
                    //else moveComRandom();
                }
                mListener.buttonPressed(-1);
            }
        });

    }
    private void setTileButtonsArrayOnClickListenter() {
        for (int i = 0; i < 81; i++) {
            final int j = i;
            setButtonImageToEmptySpace(mImageViews[j]);
            mImageViews[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickTile(j);
                }
            });
        }

    }

    public void onClickTile(int j) {
        if (mSuperBoard.move(j)) {
            mListener.buttonPressed(-2);
            animateImageButtonUpdate(j);
            animateTurnImage();
            if (!twoPlayer) moveCom();
        }
        gameOver();
        updateBoardTints();
        mListener.buttonPressed(j);
    }

    private void setTileButtonsArray() {

        mImageViews[0]  = (ImageView) mView.findViewById(R.id.imageButton1);
        mImageViews[1]  = (ImageView) mView.findViewById(R.id.imageButton2);
        mImageViews[2]  = (ImageView) mView.findViewById(R.id.imageButton3);
        mImageViews[3]  = (ImageView) mView.findViewById(R.id.imageButton4);
        mImageViews[4]  = (ImageView) mView.findViewById(R.id.imageButton5);
        mImageViews[5]  = (ImageView) mView.findViewById(R.id.imageButton6);
        mImageViews[6]  = (ImageView) mView.findViewById(R.id.imageButton7);
        mImageViews[7]  = (ImageView) mView.findViewById(R.id.imageButton8);
        mImageViews[8]  = (ImageView) mView.findViewById(R.id.imageButton9);

        mImageViews[9]  = (ImageView) mView.findViewById(R.id.imageButton11);
        mImageViews[10] = (ImageView) mView.findViewById(R.id.imageButton12);
        mImageViews[11] = (ImageView) mView.findViewById(R.id.imageButton13);
        mImageViews[12] = (ImageView) mView.findViewById(R.id.imageButton14);
        mImageViews[13] = (ImageView) mView.findViewById(R.id.imageButton15);
        mImageViews[14] = (ImageView) mView.findViewById(R.id.imageButton16);
        mImageViews[15] = (ImageView) mView.findViewById(R.id.imageButton17);
        mImageViews[16] = (ImageView) mView.findViewById(R.id.imageButton18);
        mImageViews[17] = (ImageView) mView.findViewById(R.id.imageButton19);

        mImageViews[18] = (ImageView) mView.findViewById(R.id.imageButton21);
        mImageViews[19] = (ImageView) mView.findViewById(R.id.imageButton22);
        mImageViews[20] = (ImageView) mView.findViewById(R.id.imageButton23);
        mImageViews[21] = (ImageView) mView.findViewById(R.id.imageButton24);
        mImageViews[22] = (ImageView) mView.findViewById(R.id.imageButton25);
        mImageViews[23] = (ImageView) mView.findViewById(R.id.imageButton26);
        mImageViews[24] = (ImageView) mView.findViewById(R.id.imageButton27);
        mImageViews[25] = (ImageView) mView.findViewById(R.id.imageButton28);
        mImageViews[26] = (ImageView) mView.findViewById(R.id.imageButton29);

        mImageViews[27] = (ImageView) mView.findViewById(R.id.imageButton31);
        mImageViews[28] = (ImageView) mView.findViewById(R.id.imageButton32);
        mImageViews[29] = (ImageView) mView.findViewById(R.id.imageButton33);
        mImageViews[30] = (ImageView) mView.findViewById(R.id.imageButton34);
        mImageViews[31] = (ImageView) mView.findViewById(R.id.imageButton35);
        mImageViews[32] = (ImageView) mView.findViewById(R.id.imageButton36);
        mImageViews[33] = (ImageView) mView.findViewById(R.id.imageButton37);
        mImageViews[34] = (ImageView) mView.findViewById(R.id.imageButton38);
        mImageViews[35] = (ImageView) mView.findViewById(R.id.imageButton39);

        mImageViews[36] = (ImageView) mView.findViewById(R.id.imageButton41);
        mImageViews[37] = (ImageView) mView.findViewById(R.id.imageButton42);
        mImageViews[38] = (ImageView) mView.findViewById(R.id.imageButton43);
        mImageViews[39] = (ImageView) mView.findViewById(R.id.imageButton44);
        mImageViews[40] = (ImageView) mView.findViewById(R.id.imageButton45);
        mImageViews[41] = (ImageView) mView.findViewById(R.id.imageButton46);
        mImageViews[42] = (ImageView) mView.findViewById(R.id.imageButton47);
        mImageViews[43] = (ImageView) mView.findViewById(R.id.imageButton48);
        mImageViews[44] = (ImageView) mView.findViewById(R.id.imageButton49);

        mImageViews[45] = (ImageView) mView.findViewById(R.id.imageButton51);
        mImageViews[46] = (ImageView) mView.findViewById(R.id.imageButton52);
        mImageViews[47] = (ImageView) mView.findViewById(R.id.imageButton53);
        mImageViews[48] = (ImageView) mView.findViewById(R.id.imageButton54);
        mImageViews[49] = (ImageView) mView.findViewById(R.id.imageButton55);
        mImageViews[50] = (ImageView) mView.findViewById(R.id.imageButton56);
        mImageViews[51] = (ImageView) mView.findViewById(R.id.imageButton57);
        mImageViews[52] = (ImageView) mView.findViewById(R.id.imageButton58);
        mImageViews[53] = (ImageView) mView.findViewById(R.id.imageButton59);

        mImageViews[54] = (ImageView) mView.findViewById(R.id.imageButton61);
        mImageViews[55] = (ImageView) mView.findViewById(R.id.imageButton62);
        mImageViews[56] = (ImageView) mView.findViewById(R.id.imageButton63);
        mImageViews[57] = (ImageView) mView.findViewById(R.id.imageButton64);
        mImageViews[58] = (ImageView) mView.findViewById(R.id.imageButton65);
        mImageViews[59] = (ImageView) mView.findViewById(R.id.imageButton66);
        mImageViews[60] = (ImageView) mView.findViewById(R.id.imageButton67);
        mImageViews[61] = (ImageView) mView.findViewById(R.id.imageButton68);
        mImageViews[62] = (ImageView) mView.findViewById(R.id.imageButton69);

        mImageViews[63] = (ImageView) mView.findViewById(R.id.imageButton71);
        mImageViews[64] = (ImageView) mView.findViewById(R.id.imageButton72);
        mImageViews[65] = (ImageView) mView.findViewById(R.id.imageButton73);
        mImageViews[66] = (ImageView) mView.findViewById(R.id.imageButton74);
        mImageViews[67] = (ImageView) mView.findViewById(R.id.imageButton75);
        mImageViews[68] = (ImageView) mView.findViewById(R.id.imageButton76);
        mImageViews[69] = (ImageView) mView.findViewById(R.id.imageButton77);
        mImageViews[70] = (ImageView) mView.findViewById(R.id.imageButton78);
        mImageViews[71] = (ImageView) mView.findViewById(R.id.imageButton79);

        mImageViews[72] = (ImageView) mView.findViewById(R.id.imageButton81);
        mImageViews[73] = (ImageView) mView.findViewById(R.id.imageButton82);
        mImageViews[74] = (ImageView) mView.findViewById(R.id.imageButton83);
        mImageViews[75] = (ImageView) mView.findViewById(R.id.imageButton84);
        mImageViews[76] = (ImageView) mView.findViewById(R.id.imageButton85);
        mImageViews[77] = (ImageView) mView.findViewById(R.id.imageButton86);
        mImageViews[78] = (ImageView) mView.findViewById(R.id.imageButton87);
        mImageViews[79] = (ImageView) mView.findViewById(R.id.imageButton88);
        mImageViews[80] = (ImageView) mView.findViewById(R.id.imageButton89);
    }
    private void setButtonImageToEmptySpace(ImageView v) {
        Drawable d = getContext().getDrawable(R.drawable.vector_tictactoe_empty_space_lines);
        d.setTint(getContext().getColor(R.color.burntSienna));
        v.setImageDrawable(d);
    }


    public void animateImageButtonUpdate(int x) {
        if(mSuperBoard.isXTurn()) {
            animateButtonImageToO(mImageViews[x]);
        }
        else {
            animateButtonImageToX(mImageViews[x]);
        }
    }
    public void animateTurnImage(){
        if(mSuperBoard.isXTurn()) {
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
    }
    private void animateButtonImageToX(ImageView v) {
        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_empty_to_x);
        d.setTint(p1.getPrimaryColor());
        v.setImageDrawable(d);
        d.start();

    }
    private void animateResetImages() {
        for (int i = 0; i < 81; i++) {
            AnimatedVectorDrawable d = null;
            if (mSuperBoard.isTileWonO(mSuperBoard.getBoardNumber(i), mSuperBoard.getTileNumber(i))) {
                d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_to_empty);
            }
            else if (mSuperBoard.isTileWonX(mSuperBoard.getBoardNumber(i), mSuperBoard.getTileNumber(i))) {
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
    }
    private void victorAnim() {
        if (mSuperBoard.isGameWon()) {
            if (mSuperBoard.isXWon()) {
                for (int i = 0; i < 81; i++) {
                    if (mSuperBoard.isTileWonX(mSuperBoard.getBoardNumber(i), mSuperBoard.getTileNumber(i))) {
                        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_x_rotate);
                        d.setTint(p1.getPrimaryColor());
                        mImageViews[i].setImageDrawable(d);
                        d.start();
                    }
                }
            }
            else {
                for (int i = 0; i < 81; i++) {
                    if (mSuperBoard.isTileWonO(mSuperBoard.getBoardNumber(i), mSuperBoard.getTileNumber(i))) {
                        AnimatedVectorDrawable d = (AnimatedVectorDrawable) getContext().getDrawable(R.drawable.avd_tictactoe_o_thicken);
                        d.setTint(p2.getPrimaryColor());
                        mImageViews[i].setImageDrawable(d);
                        d.start();
                    }
                }
            }
        }


    }
}
