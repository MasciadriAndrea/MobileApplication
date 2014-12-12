package it.polimi.game.model;

public class Bowl extends Container {
    private Bowl oppositeBowl;


    public Bowl(Integer id, Integer seeds, Player player,  Container nextContainer) {
        super(id, seeds, player, nextContainer);
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
}
