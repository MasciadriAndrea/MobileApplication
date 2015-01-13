package it.polimi.game.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.game.persistence.PlayerDAO;

public class PlayerHandler {
    private List<Player> players;
    private static PlayerHandler instance = null;
    PlayerDAO playerDAO=null;

    private PlayerHandler() {
        playerDAO = PlayerDAO.getInstance();
        try {
            playerDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.setPlayers(playerDAO.getAllPlayers());
    }

    public Player getPlayerById(int idP){
        for(Player p: players){
            if((int) p.getId()==idP){
                return p;
            }
        }return null;
    }

    public static PlayerHandler getInstance() {
        if(instance == null) {
            instance = new PlayerHandler();
        }
        return instance;
    }
    public void updateList(){

        this.setPlayers(playerDAO.getAllPlayers());
    }


    //Return all players excluded Megabrain, Player1, Player2
    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<Player>();
        for (Player player : this.players){
            if ((player.getId() != 2)&&(player.getId() != 3)){
                playerList.add(player);
            }
        }
        return playerList;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}


