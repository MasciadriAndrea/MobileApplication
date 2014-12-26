package it.polimi.game.model;

import java.util.List;

public class SemiBoard {

    private List<Bowl> bowls;
    private Tray tray;
    private Player player;

    public SemiBoard(List<Bowl> bowls, Tray tray,Player player) {
        this.player = player;
        this.bowls=bowls;
        this.tray=tray;
    }

    public List<Bowl> getBowls() {
        return bowls;
    }

    public Integer getNumberOfNonEmptyBowl(){
        int notEmpty=0;
        for(Bowl bowl:this.getBowls()){
            if(bowl.getSeeds()>0){
                notEmpty++;
            }
        }
        return notEmpty;
    }

    public void setBowls(List<Bowl> bowls) {
        this.bowls = bowls;
    }

    public Tray getTray() {
        return tray;
    }

    public void setTray(Tray tray) {
        this.tray = tray;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
