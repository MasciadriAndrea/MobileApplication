package com.polimi.game.model;

public abstract class Container {
    private Integer seeds;
    private Integer id;
    private Player player;
    private Container nextContainer;

    protected Container(Integer id, Integer seeds, Player player, Container nextContainer) {
        this.seeds = seeds;
        this.id = id;
        this.player = player;
        this.nextContainer = nextContainer;
    }

    public Boolean isBowl(){
        if(this instanceof Bowl) {
            return true;
        }
        return false;
    }

    public Boolean isTray(){
        if(this instanceof Tray) {
            return true;
        }
        return false;
    }

    public Container getNextContainer() {
        return nextContainer;
    }

    public void setNextContainer(Container nextContainer) {
        this.nextContainer = nextContainer;
    }

    public void incrementSeeds(){
        this.setSeeds(this.getSeeds()+1);
    }

    public void incrementSeeds(Integer nSeeds){
        this.setSeeds(this.getSeeds()+nSeeds);
    }


    public Integer getSeeds() {
        return seeds;
    }

    public void setSeeds(Integer seeds) {
        this.seeds = seeds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
