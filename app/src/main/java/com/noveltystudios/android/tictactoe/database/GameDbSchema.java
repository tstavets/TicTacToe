package com.noveltystudios.android.tictactoe.database;

/**
 * Created by tstavets on 7/18/17.
 */

public class GameDbSchema {

    public static final class GameTable {
        public static final String NAME = "games";

        public static final class Cols {
            public static final String GAME_TYPE = "game_type";
            public static final String PLAYER_ONE = "player_one";
            public static final String PLAYER_TWO = "player_two";
            public static final String VICTOR = "victor";
        }

    }

}
