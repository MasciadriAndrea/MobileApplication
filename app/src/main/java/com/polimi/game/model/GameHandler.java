package com.polimi.game.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andrea on 18/11/2014.
 */
public class GameHandler {
    private Integer activePlayerId;
    private Integer p1Id;
    private Integer p2Id;
    private Integer selectedBowlId;
    private ArrayList<Container> containers;
    private static Integer nIni=3;

    public Integer megabrainSelectBowlId(){
        //TODO just for MEGABRAIN
        //in the GameState we will have a loop of game
        //and there we can decide if the selected bowl
        //should come from UI or MEGABRAIN
        return null;
    }
    public ArrayList getContainers(){
        return this.containers;
    }

    public GameHandler(Integer p1Id, Integer p2Id) {
        this.p1Id = p1Id;
        this.p2Id = p2Id;
        ArrayList cointeiners=this.buildBoard();
    }

    public ArrayList buildBoard(){
        int[] initialBoard={0,nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        return buildBoard(initialBoard);
    }

    public ArrayList buildBoard(int[] initialBoard){
        int[] opposite={0,8,14,13,12,11,10,9,1,7,6,5,4,3,2};
        ArrayList coint=new ArrayList<Container>();
        Tray t1=new Tray(1,initialBoard[0],p2Id,null);
        Container c= (Container) t1;
        coint.add(c);
        for(Integer i=2; i<=7;i++){
            c=(Container) new Bowl(i,initialBoard[i-1],p2Id,c);
            coint.add(c);
        }
        c=(Container) new Tray(8,initialBoard[7],p1Id,c);
        coint.add(c);
        for(Integer i=9; i<=14;i++){
            Bowl d=new Bowl(i,initialBoard[i-1],p1Id,c);
            Bowl oC=(Bowl) getContainerById(opposite[i]);
            oC.setOppositeBowl(d);
            d.setOppositeBowl(oC);
            c=(Container) d;
            coint.add(c);
        }
        t1.setNextContainer(c);
        return coint;
    }

    public Container getNextContainer(Integer idActualBowl){
       Iterator<Container> ci=this.getContainers().iterator();
       while (ci.hasNext()){
           Container c=ci.next();
           if(c.getId().equals(idActualBowl)){
               return c.getNextContainer();
           }
       }
        return null;
    }

    public Integer[] getBoardStatus(){
        Integer[] res={};
        for(Integer i=1;i<=14;i++){
            res[i-1]=this.getContainerById(i).getSeeds();
        }
        return res;
    }

    public Container getContainerById(Integer idContainer){
        Iterator<Container> ci=this.getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if(c.getId().equals(idContainer)){
                return c;
            }
        }
        return null;
    }

    public Boolean zeroSeeds(Integer idp){
        Iterator<Container> ci=this.getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if((c.isBowl())&&(c.getPlayerId().equals(idp))&&(c.getSeeds()>0)){
                return false;
            }
        }
        return true;
    }

    public Integer getGameStatus(Container lastPosition){
        if(zeroSeeds(this.getActivePlayerId())){//max priority
            return 3;// gamefinish
        }else{
            if((lastPosition.isTray())&&(lastPosition.getPlayerId().equals(this.getActivePlayerId()))){
                return 2;//again my turn
            }
            if((lastPosition.isBowl())&&(lastPosition.getSeeds().equals(1))){
                return 1;//steal seeds
            }
        }
        return 0;//nothing happens
    }

    public void switchPlayer(){
        if (this.getActivePlayerId()==this.getP1Id()) {
            this.setActivePlayerId(this.getP2Id());
        }else{
            this.setActivePlayerId(this.getP1Id());
        }
    }

    public void finishGame(){
        //TODO require the real player name and update statistic and so
    }

    public Tray getTrayByPlayerId(Integer playerId){
        Iterator<Container> ci=this.getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if((c instanceof Tray)&&(c.getPlayerId().equals(playerId))){
                return (Tray)c;
            }
        }
        return null;
    }

    public void stealSeeds(Bowl lastPosition){
        Bowl oC=lastPosition.getOppositeBowl();
        Integer seeds=oC.extractSeeds();
        seeds=seeds+lastPosition.extractSeeds();
        this.getTrayByPlayerId(this.getActivePlayerId()).incrementSeeds(seeds);
    }

    public Integer getActivePlayerId() {
        return activePlayerId;
    }

    public void setActivePlayerId(Integer activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public Integer getP1Id() {
        return p1Id;
    }

    public void setP1Id(Integer p1Id) {
        this.p1Id = p1Id;
    }

    public Integer getP2Id() {
        return p2Id;
    }

    public void setP2Id(Integer p2Id) {
        this.p2Id = p2Id;
    }

    public Integer getSelectedBowlId() {
        return selectedBowlId;
    }

    public void setSelectedBowlId(Integer selectedBowlId) {
        this.selectedBowlId = selectedBowlId;
    }
}
