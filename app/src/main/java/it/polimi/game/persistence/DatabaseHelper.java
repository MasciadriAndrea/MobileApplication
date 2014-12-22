package it.polimi.game.persistence;

/**
 * Created by Paolo on 18/12/2014.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "";
    private static final String DATABASE_CREATE = "";
    private static final int SCHEMA_VERSION = 1;
    private static final String DATABASE_UPGRADING = "" ;
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
