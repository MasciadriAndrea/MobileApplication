package com.polimi.game.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Andrea on 21/11/2014.
 */
public class Board {
    private ArrayList<Container> containers;
    private static Integer nIni=3;

    public Board(Integer p1Id, Integer p2Id) {
        this.buildBoard(p1Id,p2Id);
    }

    public Board(int[] initialBoard, Integer p1Id, Integer p2Id) {
        this.buildBoard(initialBoard,p1Id,p2Id);
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
        Integer[] res={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
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

    private void buildBoard(Integer p1Id, Integer p2Id){
        int[] initialBoard={0,nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        this.buildBoard(initialBoard, p1Id, p2Id);
    }

    private void buildBoard(int[] initialBoard, Integer p1Id, Integer p2Id){
        int[] opposite={0,8,14,13,12,11,10,9,1,7,6,5,4,3,2};
        ArrayList cont=new ArrayList<Container>();
        this.containers=cont;
        Tray t1=new Tray(1,initialBoard[0],p1Id,null);
        Container c= (Container) t1;
        cont.add(c);
        for(Integer i=2; i<=7;i++){
            c=(Container) new Bowl(i,initialBoard[i-1],p1Id,c);
            cont.add(c);
        }
        c=(Container) new Tray(8,initialBoard[7],p2Id,c);
        cont.add(c);
        for(Integer i=9; i<=14;i++){
            Bowl d=new Bowl(i,initialBoard[i-1],p2Id,c);
            Bowl oC=(Bowl) getContainerById(opposite[i]);
            oC.setOppositeBowl(d);
            d.setOppositeBowl(oC);
            c=(Container) d;
            cont.add(c);
        }
        t1.setNextContainer(c);
    }

    public ArrayList getContainers(){
        return this.containers;
    }
}
