package it.polimi.game.model;

public abstract class Container {
    private Integer seeds;
    private Integer id;

    protected Container(Integer id, Integer seeds) {
        this.seeds = seeds;
        this.id = id;
    }

   /* public Boolean isBowl(){
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
    }*/



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

}
