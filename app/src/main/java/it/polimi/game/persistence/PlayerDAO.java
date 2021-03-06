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
import it.polimi.game.model.Player;

public class PlayerDAO implements ContractDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static PlayerDAO singleton = null;
    private static String[] PLAYER_TABLE_COLUMNS;

    synchronized public static PlayerDAO getInstance()  {
        if (singleton == null) {
            singleton = new PlayerDAO(Game.getInstance().getLoadActivity());
            try {
                singleton.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(singleton.getPlayerById(1)==null) {
                singleton.addPlayer("Megabrain");
                singleton.addPlayer("Player 1");
                singleton.addPlayer("Player 2");
            }
        }
        return singleton ;
    }

    private PlayerDAO(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
        PLAYER_TABLE_COLUMNS = dbHelper.PLAYER_FIELDS;
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
    public Player addPlayer(String playerName) {

        ContentValues values = new ContentValues();
        Integer i=0;
        values.put(PLAYER_TABLE_COLUMNS[1], playerName);
        values.put(PLAYER_TABLE_COLUMNS[2], 0);
        values.put(PLAYER_TABLE_COLUMNS[3], 0);
        values.put(PLAYER_TABLE_COLUMNS[4], i.doubleValue());
        values.put(PLAYER_TABLE_COLUMNS[5], i.doubleValue());
        values.put(PLAYER_TABLE_COLUMNS[6], 0);

        long playerId = db.insert(DatabaseHelper.PLAYER,null,values);

        Cursor cursor = db.query(DatabaseHelper.PLAYER,
                PLAYER_TABLE_COLUMNS,PLAYER_TABLE_COLUMNS[0] + " = "
                        + playerId,null,null,null,null);

        cursor.moveToFirst();

        Player newPlayer = buildPlayer(cursor);
        cursor.close();
        return newPlayer;

    }

    public Player getPlayerByName(String name){

        Cursor cursor = db.query(DatabaseHelper.PLAYER, PLAYER_TABLE_COLUMNS,
                PLAYER_TABLE_COLUMNS[1] + " = " + name, null, null, null, null);
        Player player = new Player();
        if (cursor.moveToFirst()){
            player = buildPlayer(cursor);
            return player;
        }
        else
            return null;
    }

    public Player getPlayerById(Integer id) {

        String[] whereArgs = new String[]{
                id.toString()
        };
        Cursor cursor = db.query(DatabaseHelper.PLAYER, PLAYER_TABLE_COLUMNS,
                PLAYER_TABLE_COLUMNS[0] + " = ? ", whereArgs, null, null, null);
        Player player = new Player();
        if (cursor.moveToFirst()) {
            player = buildPlayer(cursor);
            return player;
        } else
            return null;
    }

    public void dropDB(){
        db.delete(DatabaseHelper.PLAYER,null,null);
    }

    public List<Player> getLastPlayers(){

        List<Player> listPlayers = new ArrayList<Player>();
        Cursor cursor = db.query(DatabaseHelper.PLAYER,null,null,null,null,null,dbHelper.PLAYER_FIELDS[6],"5");

        int i=0;
        while (cursor.moveToNext()) {
            listPlayers.add(i,buildPlayer(cursor));
            i++;
        }

        return listPlayers;

    }

    public List<Player> getAllPlayers(){

        List<Player> listPlayers = new ArrayList<Player>();
        Cursor cursor = db.query(DatabaseHelper.PLAYER,null,null,null,null,null,dbHelper.PLAYER_FIELDS[6]+" DESC");

        int i=0;
        while (cursor.moveToNext()) {
            listPlayers.add(i,buildPlayer(cursor));
            i++;
        }

        return listPlayers;

    }


    public Integer updatePlayer (Player player){

        String strFilter = PLAYER_TABLE_COLUMNS[0]+ " = " + player.getId().toString();
        ContentValues args = new ContentValues();
        args.put(PLAYER_TABLE_COLUMNS[2], player.getPlayedGames());
        args.put(PLAYER_TABLE_COLUMNS[3], player.getWonGames());
        args.put(PLAYER_TABLE_COLUMNS[4], player.getWonGameResult());
        args.put(PLAYER_TABLE_COLUMNS[5], player.getMaxScoreResult());
        args.put(PLAYER_TABLE_COLUMNS[6], player.getLastGamePlayed().getTime());

        Integer nRows = db.update(DatabaseHelper.PLAYER, args, strFilter, null);

        return nRows;
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
