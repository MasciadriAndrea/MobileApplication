package it.polimi.game.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHistory;
import it.polimi.game.model.PlayerHandler;

public class HistoryDAO implements ContractDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static HistoryDAO singleton = null;
    private static String[] HISTORY_TABLE_COLUMNS;

    synchronized public static HistoryDAO getInstance()  {
        if (singleton == null) {
            singleton = new HistoryDAO(Game.getInstance().getLoadActivity());
            try {
                singleton.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return singleton ;
    }

    private HistoryDAO(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
        HISTORY_TABLE_COLUMNS = dbHelper.HISTORY_FIELDS;
    }

    private GameHistory buildGameHistory(Cursor cursor) {

        GameHistory newGameHistory = new GameHistory();

        newGameHistory.setId(cursor.getInt(0));
        newGameHistory.setP1(PlayerHandler.getInstance().getPlayerById(cursor.getInt(1)));
        newGameHistory.setP2(PlayerHandler.getInstance().getPlayerById(cursor.getInt(2)));
        newGameHistory.setScore1(cursor.getInt(3));
        newGameHistory.setScore2(cursor.getInt(4));
        Date date = new Date(cursor.getLong(5));
        newGameHistory.setData(date);

        return newGameHistory;
    }

    public GameHistory addGameHistory(GameHistory gameHistory) {

        ContentValues values = new ContentValues();

        values.put(HISTORY_TABLE_COLUMNS[1], gameHistory.getP1().getId());
        values.put(HISTORY_TABLE_COLUMNS[2], gameHistory.getP2().getId());
        values.put(HISTORY_TABLE_COLUMNS[3], gameHistory.getScore1());
        values.put(HISTORY_TABLE_COLUMNS[4], gameHistory.getScore2());
        values.put(HISTORY_TABLE_COLUMNS[5], gameHistory.getData().getTime());

        long gameHistoryId = db.insert(DatabaseHelper.HISTORY,null,values);

        Cursor cursor = db.query(DatabaseHelper.HISTORY,
                HISTORY_TABLE_COLUMNS,HISTORY_TABLE_COLUMNS[0] + " = "
                        + gameHistoryId,null,null,null,null);

        cursor.moveToFirst();

        GameHistory newGameHistory = buildGameHistory(cursor);
        cursor.close();
        return newGameHistory;

    }


    public List<GameHistory> getLastGameHistory(){

        List<GameHistory> listGameHistory = new ArrayList<GameHistory>();
        Cursor cursor = db.query(DatabaseHelper.HISTORY,null,null,null,null,null,dbHelper.HISTORY_FIELDS[5],"5");

        int i=0;
        while (cursor.moveToNext()) {
            listGameHistory.add(i,buildGameHistory(cursor));
            i++;
        }

        return listGameHistory;
    }




    @Override
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }


}
