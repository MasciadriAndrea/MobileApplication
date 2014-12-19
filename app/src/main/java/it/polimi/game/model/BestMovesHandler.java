package it.polimi.game.model;

import java.util.ArrayList;
import java.util.List;

public class BestMovesHandler {
    private static BestMovesHandler instance = null;
    private List<BestMoveResult> tenBest;


    public static BestMovesHandler getInstance() {
        if(instance == null) {
            instance = new BestMovesHandler();
        }
        return instance;
    }

    public BestMovesHandler() {
        this.tenBest = new ArrayList<BestMoveResult>();
        //TODO obtain this from DB
    }

    public void insertResult(Integer idp,Integer nSeedCollected){
        Integer result=nSeedCollected;//TODO this result maybe should be normalized!
        int pos=0;
        int where=11;
        Boolean found=false;
        for(BestMoveResult bmr:this.tenBest){
            if((bmr.getResult()<result)&&(!found)){
                found=true;
                where=pos;
            }
            pos++;
        }
        if(where<11){
            this.tenBest.add(where,new BestMoveResult(idp,result));
        }
        //TODO save/update in the db the first 10 element
    }
}
