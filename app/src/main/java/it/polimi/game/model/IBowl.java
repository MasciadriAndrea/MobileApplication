package it.polimi.game.model;

public interface IBowl {
    Integer pullOutSeeds();
    Bowl getOppositeBowl();
    void incrementSeeds();
    void incrementSeeds(Integer nSeeds);
}
