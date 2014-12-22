package it.polimi.game.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerHandler {
    private List<Player> players;
    private static PlayerHandler instance = null;

    public PlayerHandler() {
        //todo download the list of player from db
        this.players=new ArrayList<Player>();
    }

    public static PlayerHandler getInstance() {
        if(instance == null) {
            instance = new PlayerHandler();
        }
        return instance;
    }


}


