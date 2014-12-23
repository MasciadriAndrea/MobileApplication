package it.polimi.game.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.Date;

import it.polimi.game.model.Player;

/**
 * Created by Paolo on 18/12/2014.
 */
public class PlayerDAO implements ContractDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private String[] PLAYER_TABLE_COLUMNS = DatabaseHelper.PLAYER_FIELDS;

    public PlayerDAO(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    public Player addPlayer(Player player) {

        ContentValues values = new ContentValues();

        values.put(PLAYER_TABLE_COLUMNS[1], player.getName());
        values.put(PLAYER_TABLE_COLUMNS[2], player.getPlayedGames());
        values.put(PLAYER_TABLE_COLUMNS[3], player.getWonGames());
        values.put(PLAYER_TABLE_COLUMNS[4], player.getWonGameResult());
        values.put(PLAYER_TABLE_COLUMNS[5], player.getMaxScoreResult());
        values.put(PLAYER_TABLE_COLUMNS[6], player.getLastGamePlayed().getTime());

        long playerId = db.insert(DatabaseHelper.PLAYER,null,values);

        Cursor cursor = db.query(DatabaseHelper.PLAYER,
                PLAYER_TABLE_COLUMNS,PLAYER_TABLE_COLUMNS[0] + " = "
                + playerId,null,null,null,null);

        cursor.moveToFirst();

        Player newPlayer = buildPlayer(cursor);
        cursor.close();
        return newPlayer;

    }

    private Player buildPlayer(Cursor cursor) {

        Player newPlayer = new Player();

        newPlayer.setId(cursor.getInt(0));
        newPlayer.setName(cursor.getString(1));
        newPlayer.setPlayedGames(cursor.getInt(2));
        newPlayer.setWonGames(cursor.getInt(3));
        newPlayer.setWonGameResult(cursor.getDouble(4));
        newPlayer.setMaxScoreResult(cursor.getDouble(5));
        Date date = new Date(cursor.getLong(6));
        newPlayer.setLastGamePlayed(date);

        return newPlayer;
    }
}
