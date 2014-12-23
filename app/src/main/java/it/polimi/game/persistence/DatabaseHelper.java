package it.polimi.game.persistence;

/**
 * Created by Paolo on 18/12/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Bantumi.db";
    private static final int SCHEMA_VERSION = 1;

    public static final String PLAYER = "Player";
    public static final String[] PLAYER_FIELDS= {"id","name","playedGames","wonGames","wonGamesResult","maxScoreResult","lastGamePlayed"};
    private static final String DATABASE_CREATE = "create table "+ PLAYER +" ("+PLAYER_FIELDS[1]+" integer primary key autoincrement,"
            +PLAYER_FIELDS[2]+" text not null,"
            +PLAYER_FIELDS[3]+" integer,"
            +PLAYER_FIELDS[4]+" integer,"
            +PLAYER_FIELDS[5]+" double,"
            +PLAYER_FIELDS[6]+" double,"
            +PLAYER_FIELDS[7]+" date)";
    private static final String DATABASE_UPGRADING = "DROP TABLE IF EXISTS " + PLAYER  ;

    private static DatabaseHelper singleton = null;

    public DatabaseHelper(Context applicationContext) {
        super(applicationContext, DATABASE_NAME, null, SCHEMA_VERSION);
    }

    synchronized static DatabaseHelper getInstance(Context ctxt) {
        if (singleton == null) {
            singleton = new DatabaseHelper(ctxt.getApplicationContext());
        }

        return (singleton);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,  int oldVersion,
                          int newVersion) {
        sqLiteDatabase.execSQL(DATABASE_UPGRADING);
        onCreate(sqLiteDatabase);
    }

    // For Database operations Lunch a Thread
}
