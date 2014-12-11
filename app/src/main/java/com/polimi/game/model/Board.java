package com.polimi.game.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private ArrayList<Container> containers;
    private static Integer nIni=3;

    public Board(Player p1, Player p2) {
        this.buildBoard(p1,p2);
    }

    public Board(int[] initialBoard, Player p1, Player p2) {
        this.buildBoard(initialBoard,p1,p2);
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

    public Tray getTrayByPlayer(Player player){
        Iterator<Container> ci=this.getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if((c instanceof Tray)&&(c.getPlayer().equals(player))){
                return (Tray)c;
            }
        }
        return null;
    }

    private void buildBoard(Player p1, Player p2){
        int[] initialBoard={0,nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        this.buildBoard(initialBoard, p1, p2);
    }

    private void buildBoard(int[] initialBoard, Player p1, Player p2){
        int[] opposite={0,8,14,13,12,11,10,9,1,7,6,5,4,3,2};
        ArrayList cont=new ArrayList<Container>();
        this.containers=cont;
        Tray t1=new Tray(1,initialBoard[0],p1,null);
        Container c= (Container) t1;
        cont.add(c);
        for(Integer i=2; i<=7;i++){
            c=(Container) new Bowl(i,initialBoard[i-1],p1,c);
            cont.add(c);
        }
        c=(Container) new Tray(8,initialBoard[7],p2,c);
        cont.add(c);
        for(Integer i=9; i<=14;i++){
            Bowl d=new Bowl(i,initialBoard[i-1],p2,c);
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
