package it.polimi.game.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polimi.game.persistence.HistoryDAO;

public class HistoryHandler {
    private List<GameHistory> games;
    private static HistoryHandler instance = null;

    private HistoryHandler() {
       this.setGames();
    }

    public static HistoryHandler getInstance() {
        if(instance == null) {
            instance = new HistoryHandler();
        }
        return instance;
    }

    public void addGameHistory(GameHistory gh){
        HistoryDAO.getInstance().addGameHistory(gh);
        this.setGames();
    }

    public void setGames(){
        List<GameHistory> gameHistories=new ArrayList<GameHistory>();

        gameHistories = HistoryDAO.getInstance().getLastGameHistory();
        this.games=gameHistories;
    }
    public List<GameHistory> getGames(){
        return this.games;
    }
}
