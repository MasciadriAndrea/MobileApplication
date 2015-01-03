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
        for(Player p:this.getPlayers()){
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }


}


