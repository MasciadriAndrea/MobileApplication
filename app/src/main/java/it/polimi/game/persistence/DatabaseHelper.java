package it.polimi.game.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bantumi.db";
    private static final int SCHEMA_VERSION = 1;
    public static String PLAYER = "Player";
    public static String BEST_MOVE_RESULT = "BestMoveResult";
    public String[] PLAYER_FIELDS;
    public String[] BEST_MOVE_RESULT_FIELDS;
    private String DATABASE_CREATE_PLAYER;
    private String DATABASE_CREATE_BEST_MOVE_RESULT;
    private String DATABASE_UPGRADING;

    private static DatabaseHelper singleton = null;

    private DatabaseHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, SCHEMA_VERSION);

        PLAYER_FIELDS = new String[]{"id", "name", "playedGames", "wonGames", "wonGamesResult", "maxScoreResult", "lastGamePlayed"};
        BEST_MOVE_RESULT_FIELDS = new  String[]{"id", "idPlayer", "result"};

        DATABASE_CREATE_PLAYER = "create table "+ PLAYER +" ("+PLAYER_FIELDS[0]+" integer primary key autoincrement,"
                +PLAYER_FIELDS[1]+" text unique not null,"
                +PLAYER_FIELDS[2]+" integer,"
                +PLAYER_FIELDS[3]+" integer,"
                +PLAYER_FIELDS[4]+" double,"
                +PLAYER_FIELDS[5]+" double,"
                +PLAYER_FIELDS[6]+" date);";

        DATABASE_CREATE_BEST_MOVE_RESULT ="create table "+ BEST_MOVE_RESULT +" ("+ BEST_MOVE_RESULT_FIELDS[0]+" integer primary key autoincrement,"
                + BEST_MOVE_RESULT_FIELDS[1]+" integer,"
                + BEST_MOVE_RESULT_FIELDS[2]+" integer not null, FOREIGN KEY ("+BEST_MOVE_RESULT_FIELDS[1]+") REFERENCES "+ PLAYER +" ("+ PLAYER_FIELDS[0] +"));";
        DATABASE_UPGRADING = "DROP TABLE IF EXISTS " + PLAYER + ";" +
                "DROP TABLE IF EXISTS " + BEST_MOVE_RESULT +";";
    }

    synchronized static DatabaseHelper getInstance(Context ctxt) {
        if (singleton == null) {
            singleton = new DatabaseHelper(ctxt);

        }

        return (singleton);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_PLAYER);
        sqLiteDatabase.execSQL(DATABASE_CREATE_BEST_MOVE_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion,
                          int newVersion) {
        sqLiteDatabase.execSQL(DATABASE_UPGRADING);
        onCreate(sqLiteDatabase);
    }

}
