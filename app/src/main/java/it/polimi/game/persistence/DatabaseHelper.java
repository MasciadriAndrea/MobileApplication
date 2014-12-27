package it.polimi.game.persistence;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bantumi.db";
    private static final int SCHEMA_VERSION = 1;
    public static String PLAYER = "Player";
    public String[] PLAYER_FIELDS;
    private String DATABASE_CREATE;
    private String INSERT_MEGABRAIN;
    private String DATABASE_UPGRADING;

    private static DatabaseHelper singleton = null;

    public DatabaseHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, SCHEMA_VERSION);
        PLAYER_FIELDS= new String[]{"id", "name", "playedGames", "wonGames", "wonGamesResult", "maxScoreResult", "lastGamePlayed"};
        DATABASE_CREATE = "create table "+ PLAYER +" ("+PLAYER_FIELDS[0]+" integer primary key autoincrement,"
                +PLAYER_FIELDS[1]+" text unique not null,"
                +PLAYER_FIELDS[2]+" integer,"
                +PLAYER_FIELDS[3]+" integer,"
                +PLAYER_FIELDS[4]+" double,"
                +PLAYER_FIELDS[5]+" double,"
                +PLAYER_FIELDS[6]+" date);";
        //INSERT_MEGABRAIN = new String("insert into " + PLAYER + " values (1,\"MEGABRAIN\",0,0,0.0,0.0,0 );");
        DATABASE_UPGRADING = "DROP TABLE IF EXISTS " + PLAYER  ;
    }

    synchronized static DatabaseHelper getInstance(Context ctxt) {
        if (singleton == null) {
            singleton = new DatabaseHelper(ctxt.getApplicationContext());

        }

        return (singleton);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE + INSERT_MEGABRAIN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion,
                          int newVersion) {
        sqLiteDatabase.execSQL(DATABASE_UPGRADING);
        onCreate(sqLiteDatabase);
    }

    // For Database operations Lunch a Thread
}
