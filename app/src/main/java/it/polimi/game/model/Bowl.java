package it.polimi.game.model;

public class Bowl implements IBowl {
    private Bowl oppositeBowl;
    private Integer seeds;
    private Integer id;


    public Bowl(Integer id, Integer seeds) {
            this.seeds = seeds;
            this.id = id;
    }

    public Integer pullOutSeeds() {
        Integer ns=this.getSeeds();
        this.setSeeds(0);
        return ns;
    }

    public Bowl getOppositeBowl() {
        return oppositeBowl;
    }

    public void setOppositeBowl(Bowl oppositeBowl) {
        this.oppositeBowl = oppositeBowl;
    }

    public void incrementSeeds(){
        this.setSeeds(this.getSeeds()+1);
    }

    public void incrementSeeds(Integer nSeeds){
        this.setSeeds(this.getSeeds()+nSeeds);
    }

    public Integer getId() {
        return id;
    }

    public Integer getSeeds() {
        return seeds;
    }

    public void setSeeds(Integer seeds) {
        this.seeds = seeds;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
