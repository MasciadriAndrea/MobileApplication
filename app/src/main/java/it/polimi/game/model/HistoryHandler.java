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

    public void setGames(){
        List<GameHistory> gameHistories=new ArrayList<GameHistory>();

        gameHistories = HistoryDAO.getInstance().getLastGameHistory();
        //TODO take g from database
        //TODO to test i will try this--- to be deleted
        //g.add(new GameHistory(PlayerHandler.getInstance().getPlayerById(2),PlayerHandler.getInstance().getPlayerById(3),3,4,new Date(33)));
        //g.add(new GameHistory(PlayerHandler.getInstance().getPlayerById(1),PlayerHandler.getInstance().getPlayerById(3),33,4,new Date(433)));
        //g.add(new GameHistory(PlayerHandler.getInstance().getPlayerById(3),PlayerHandler.getInstance().getPlayerById(2),3,24,new Date(533)));
        //TODO remove since this
        this.games=gameHistories;
    }
    public List<GameHistory> getGames(){
        return this.games;
    }
}
