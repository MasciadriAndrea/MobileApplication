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

    public List<BestMoveResult> getTenBest(){
        return this.tenBest;
    }

    public void insertResult(Player pl,Integer nSeedCollected){
        Integer result=nSeedCollected;//TODO this result maybe should be normalized!
        //1 add in right position
        Boolean find=false;
        Integer position=0;
        Integer insertPosition=10;
        for(BestMoveResult bm:tenBest) {
            if((bm.getResult()<result)&&(!find)) {
                find=true;
                insertPosition=position;
            }
            position++;
        }
        if ((position < 10)&&(!find)) {
            insertPosition = position;
        }
        if((insertPosition<10)||(position<10)){
            BestMoveResult bnew=new BestMoveResult(pl,result);
            tenBest.add(insertPosition,bnew);
            BestMoveResultDAO.getInstance(Game.getInstance().getGameActivity()).addBestMoveResult(new BestMoveResult(pl,result));
            //2 truncate tenBest (size max=10)
            if(tenBest.size()>10) {
                BestMoveResult eleventh=tenBest.get(10);
                BestMoveResultDAO.getInstance(Game.getInstance().getGameActivity()).deleteBestMoveResult(eleventh);
                tenBest.remove(10);
            }
        }

        tenBest = bestMoveResultDAO.getBestMovesResult();

    }
}
