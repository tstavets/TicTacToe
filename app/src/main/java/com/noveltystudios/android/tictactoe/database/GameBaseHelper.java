package com.noveltystudios.android.tictactoe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.noveltystudios.android.tictactoe.AbstractStrings;
import com.noveltystudios.android.tictactoe.database.GameDbSchema.GameTable;

/**
 * Created by tstavets on 7/18/17.
 */

public class GameBaseHelper extends SQLiteOpenHelper implements AbstractStrings {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "gameBase.db";


    public GameBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GameTable.NAME + "(" +
        " _id integer primary key autoincrement, " +
        GameTable.Cols.GAME_TYPE + ", " +
        GameTable.Cols.PLAYER_ONE + ", " +
        GameTable.Cols.PLAYER_TWO + ", " +
        GameTable.Cols.VICTOR +
        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    private Cursor queryGames(String whereClause, String[] whereArgs, SQLiteDatabase db) {
        Cursor cursor = db.query(
                GameTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, //group by
                null, //having
                null //orderBy
        );
        return cursor;
    }

    public void addGame(String gameType, String p1, String p2, String victor) {
        ContentValues values = new ContentValues();
        values.put(GameDbSchema.GameTable.Cols.GAME_TYPE, gameType);
        values.put(GameDbSchema.GameTable.Cols.PLAYER_ONE, p1);
        values.put(GameDbSchema.GameTable.Cols.PLAYER_TWO, p2);
        values.put(GameDbSchema.GameTable.Cols.VICTOR, victor);

        SQLiteDatabase db = getWritableDatabase();

        db.insert(GameDbSchema.GameTable.NAME, null, values);
        db.close();
    }

    public void resetPlayerData(String playerName) {
        SQLiteDatabase db = getWritableDatabase();


        db.execSQL("UPDATE " + GameTable.NAME + " SET " + GameTable.Cols.PLAYER_ONE + " = " + "'" + NO_NAME_PLAYER + "'" + " WHERE " + GameTable.Cols.PLAYER_ONE + "=\"" + playerName + "\";");
        db.execSQL("UPDATE " + GameTable.NAME + " SET " + GameTable.Cols.PLAYER_TWO + " = " + "'" + NO_NAME_PLAYER + "'" + " WHERE " + GameTable.Cols.PLAYER_TWO + "=\"" + playerName + "\";");
    }

    public void resetDataBase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + GameTable.NAME);
        onCreate(db);
        db.close();
    }

    public String getRecord(String playerName) {
        int hmW = 0; int hmL = 0;
        int tttW = 0; int tttL = 0; int tttD = 0;
        int stttW = 0; int stttL = 0; int stttD = 0;
        int sttthmW = 0; int sttthmL = 0; int sttthmD = 0;

        int count = 0;


        Cursor cursor = queryGames(null, null, getWritableDatabase());


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                count++;
                if (cursor.getString(cursor.getColumnIndex(GameTable.Cols.PLAYER_ONE)).equals(playerName) || cursor.getString(cursor.getColumnIndex(GameTable.Cols.PLAYER_TWO)).equals(playerName)) {
                    String game = cursor.getString(cursor.getColumnIndex(GameTable.Cols.GAME_TYPE));
                    String victor = cursor.getString(cursor.getColumnIndex(GameTable.Cols.VICTOR));
                    if (game.equals("Hangman")) {
                        if (victor.equals(playerName)) hmW++;
                        else hmL++;
                    }
                    else if (game.equals("Tic Tac Toe")) {
                        if (victor.equals(playerName)) tttW++;
                        else if (victor.equals(DRAW)) tttD++;
                        else tttL++;
                    }
                    else if (game.equals("Super Tic Tac Toe")) {
                        if (victor.equals(playerName)) stttW++;
                        else if (victor.equals(DRAW)) stttD++;
                        else stttL++;
                    }
                    else if (game.equals("Super Tic Tac Toe Hangman")) {
                        if (victor.equals(playerName)) sttthmW++;
                        else if (victor.equals(DRAW)) sttthmD++;
                        else sttthmL++;
                    }
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        String ans = playerName + " Stats: \n" +
                "HANGMAN ~  Wins: " + hmW + ", Losses: " + hmL + "\n" +
                "Tic Tac Toe ~  Wins: " + tttW + ", Losses: " + tttL + ", Draws: " + tttD + "\n" +
                "Super Tic Tac Toe ~  Wins: " + stttW + ", Losses: " + stttL + ", Draws: " + stttD + "\n" +
                "Super Tic Tac Toe Hangman ~  Wins: " + sttthmW + ", Losses: " + sttthmL + ", Draws: " + sttthmD + "\n" +
                "Totals ~  Wins: " + (hmW + stttW + tttW + sttthmW) + ", Losses: " + (hmL + tttL + stttL + sttthmL) + ", Draws: " + (tttD + stttD + sttthmD) + "\n" +
                "Total Games Played: " + (hmW + hmL + stttD + sttthmD + sttthmL + sttthmW + stttL + stttW + tttD + tttL + tttW);



        return ans;
    }

    public String getQuickRecord(String playerName) {
        int W = 0; int L = 0; int D = 0;

        int count = 0;


        Cursor cursor = queryGames(null, null, getWritableDatabase());


        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                count++;
                if (cursor.getString(cursor.getColumnIndex(GameTable.Cols.PLAYER_ONE)).equals(playerName) || cursor.getString(cursor.getColumnIndex(GameTable.Cols.PLAYER_TWO)).equals(playerName)) {
                    String victor = cursor.getString(cursor.getColumnIndex(GameTable.Cols.VICTOR));

                    if (victor.equals(playerName)) W++;
                    else if (victor.equals(DRAW)) D++;
                    else L++;

                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        String ans = playerName + " Quick Stats: \n" +
                "Wins: " + W + ", Losses: " + L + ", Draws: " + D;


        return ans;
    }

}
