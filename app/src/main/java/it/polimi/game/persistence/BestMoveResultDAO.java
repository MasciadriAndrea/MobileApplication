package it.polimi.game.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.game.model.BestMoveResult;
import it.polimi.game.model.Player;

public class BestMoveResultDAO implements ContractDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static BestMoveResultDAO singleton = null;
    private static PlayerDAO playerDAO ;
    private static String[] BEST_MOVE_RESULT_COLUMNS;

    synchronized public static BestMoveResultDAO getInstance(Context ctxt)  {
        if (singleton == null) {
            singleton = new BestMoveResultDAO(ctxt.getApplicationContext());
            try {
                singleton.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return singleton ;
    }

    private BestMoveResultDAO(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
        playerDAO = PlayerDAO.getInstance(context);

        BEST_MOVE_RESULT_COLUMNS = dbHelper.BEST_MOVE_RESULT_FIELDS;
    }

    @Override
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }


    public List<BestMoveResult> getBestMovesResult() {

        List<BestMoveResult> listBestMoves = new ArrayList<BestMoveResult>();
        Cursor cursor = db.query(DatabaseHelper.BEST_MOVE_RESULT,null,null,null,null,null,dbHelper.BEST_MOVE_RESULT_FIELDS[2]);

        int i=0;
        while (cursor.moveToNext()) {
            listBestMoves.add(i,buildBestMoveResult(cursor));
            i++;
        }

        return listBestMoves;
    }

    private BestMoveResult buildBestMoveResult(Cursor cursor) {

        BestMoveResult bestMoveResult = new BestMoveResult(cursor.getInt(0),playerDAO.getPlayerById(cursor.getInt(1)),cursor.getInt(2));

        return bestMoveResult;
    }

    public long addBestMoveResult(BestMoveResult bestMoveResult) {

        ContentValues values = new ContentValues();

        values.put(BEST_MOVE_RESULT_COLUMNS[1], bestMoveResult.getPlayer().getId());
        values.put(BEST_MOVE_RESULT_COLUMNS[2], bestMoveResult.getResult());

        long bestMoveResultId = db.insert(DatabaseHelper.BEST_MOVE_RESULT,null,values);

        return bestMoveResultId;
    }

    public boolean deleteBestMoveResult(BestMoveResult bestMoveResult) {

        String[] whereArgs = new String[]{
                bestMoveResult.getId().toString()
        };
        return db.delete(DatabaseHelper.BEST_MOVE_RESULT, BEST_MOVE_RESULT_COLUMNS[0] + " = " + whereArgs[0] ,null) > 0;
    }
}
