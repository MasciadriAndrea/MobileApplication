package com.polimi.game.model;

/**
 * Created by Andrea on 18/11/2014.
 */
public class Player {
    private Integer idPlayer;
    private String name;
    private Integer numberWins;

    public Player(Integer idPlayer, String name) {
        this.idPlayer = idPlayer;
        this.name = name;
        this.numberWins = 0;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberWins() {
        return numberWins;
    }

    public void setNumberWins(Integer numberWins) {
        this.numberWins = numberWins;
    }
}
