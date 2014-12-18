package it.polimi.game.model;

public class Tray implements  ITray{
    private Integer seeds;
    private Integer id;

    public Tray(Integer id, Integer seeds) {
            this.seeds = seeds;
            this.id = id;
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
}
