package it.polimi.game.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.game.persistence.BestMoveResultDAO;

public class BestMovesHandler {
    private static BestMovesHandler instance = null;
    private List<BestMoveResult> tenBest;
    private BestMoveResultDAO bestMoveResultDAO;


    public static BestMovesHandler getInstance() {
        if(instance == null) {

            instance = new BestMovesHandler();
        }
        return instance;
    }

    private BestMovesHandler() {
        // TODO check if right context of initialization
        bestMoveResultDAO = BestMoveResultDAO.getInstance(Game.getInstance().getGameActivity());
        try {
            bestMoveResultDAO.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tenBest = new ArrayList<BestMoveResult>();
        this.tenBest = bestMoveResultDAO.getBestMovesResult();
        //TODO obtain this from DB --- ok
    }

    public void insertResult(Player pl,Integer nSeedCollected){
        Integer result=nSeedCollected;//TODO this result maybe should be normalized!
        int pos=0;
        int where=10;
        Boolean found=false;
        for(BestMoveResult bmr:this.tenBest){
            if((bmr.getResult()<result)&&(!found)){
                found=true;
                where=pos;
            }
            pos++;
        }
        if(where<11){
            this.tenBest.add(where,new BestMoveResult(pl,result));
        }
        //TODO save/update in the db the first 10 element
        //if added new one in top ten , add it in db and delete the previous 10th
        if ((found)&&(where<11)){
            bestMoveResultDAO.addBestMoveResult(new BestMoveResult(pl,result));
            boolean b = bestMoveResultDAO.deleteBestMoveResult(tenBest.get(10));
            tenBest = bestMoveResultDAO.getBestMovesResult();
        }
    }
}
