package by.slizh.lab_2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TeamDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FootballTables.db";

    public static final String TEAMS_TABLE_NAME = "teams";
    public static final String FAVORITES_TABLE_NAME = "favorites";

    //TEAMS
    public static final String TEAM_ID_KEY = "teamId";
    public static final String COMPETITION_ID_KEY = "competitionId";
    public static final String COMPETITION_NAME_KEY = "competitionName";
    public static final String COMPETITION_CODE_KEY = "competitionCode";
    public static final String TEAM_NAME_KEY = "teamName";
    public static final String LOGO_URL_KEY = "logoUrl";
    public static final String PLAYED_GAMES_KEY = "playedGames";
    public static final String WON_KEY = "won";
    public static final String DRAW_KEY = "draw";
    public static final String LOST_KEY = "lost";
    public static final String POINTS_KEY = "points";
    public static final String GOALS_FOR_KEY = "goalsFor";
    public static final String GOALS_AGAINST_KEY = "goalsAgainst";
    public static final String GOAL_DIFFERENCE_KEY = "goalDifference";
    public static final String POSITION_KEY = "position";

    //FAVORITES
    public static final String USER_ID_KEY = "userId";
    public static final String FAVORITE_TEAM_ID_KEY = "favoriteTeamId";

    private static final String SQL_CREATE_TEAMS_TABLE =
            "CREATE TABLE " + TEAMS_TABLE_NAME + "(" +
                    TEAM_ID_KEY + " INTEGER PRIMARY KEY, " +
                    COMPETITION_ID_KEY + " INTEGER, " +
                    COMPETITION_NAME_KEY + " TEXT, " +
                    COMPETITION_CODE_KEY + " TEXT, " +
                    TEAM_NAME_KEY + " TEXT, " +
                    LOGO_URL_KEY + " TEXT, " +
                    PLAYED_GAMES_KEY + " INTEGER, " +
                    WON_KEY + " INTEGER, " +
                    DRAW_KEY + " INTEGER, " +
                    LOST_KEY + " INTEGER, " +
                    POINTS_KEY + " INTEGER, " +
                    GOALS_FOR_KEY + " INTEGER, " +
                    GOALS_AGAINST_KEY + " INTEGER, " +
                    GOAL_DIFFERENCE_KEY + " INTEGER, " +
                    POSITION_KEY + " INTEGER)";

    private static final String SQL_CREATE_FAVORITES_TABLE =
            "CREATE TABLE " + FAVORITES_TABLE_NAME + "(" +
                    USER_ID_KEY + " TEXT, " +
                    FAVORITE_TEAM_ID_KEY + " INT, " +
                    "PRIMARY KEY (" + USER_ID_KEY + ", " + FAVORITE_TEAM_ID_KEY + "), " +
                    "FOREIGN KEY(" + FAVORITE_TEAM_ID_KEY + ") REFERENCES " + TEAMS_TABLE_NAME + "(" + TEAM_ID_KEY + "))";

    private static final String SQL_DROP_TEAMS_TABLE =
            "DROP TABLE IF EXISTS " + TEAMS_TABLE_NAME + ";";
    private static final String SQL_DROP_FAVORITES_TABLE =
            "DROP TABLE IF EXISTS " + FAVORITES_TABLE_NAME + "; ";

    public TeamDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TEAMS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_FAVORITES_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_TEAMS_TABLE);
        onCreate(sqLiteDatabase);
    }
}
