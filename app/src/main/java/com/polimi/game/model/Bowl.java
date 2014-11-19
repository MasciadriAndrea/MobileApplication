package com.polimi.game.model;

/**
 * Created by Andrea on 18/11/2014.
 */
public class Bowl extends Container {
    private Bowl oppositeBowl;

    public Bowl(Integer id, Integer seeds, Integer playerId,  Container nextContainer) {
        super(id, seeds, playerId, nextContainer);
    }

    public Integer extractSeeds() {
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
