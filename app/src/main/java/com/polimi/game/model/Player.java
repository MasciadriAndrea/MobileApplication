package com.polimi.game.model;

import java.util.Date;

public class Player {

    //TODO Class methods not yet implemented

    private Integer id;
    private String name;
    private Integer playedGames;
    private Integer wonGames;
    private Double wonGameResult;
    private Double maxScoreResult;
    private Date lastGamePlayed;

    public Player(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.playedGames = null;
        this.wonGames = null;
        this.wonGameResult = null;
        this.maxScoreResult = null;
        this.lastGamePlayed = null;
    }

    public Player(Integer id, String name, Integer playedGames, Integer wonGames, Double wonGameResult, Double maxScoreResult, Date lastGamePlayed) {
        this.id = id;
        this.name = name;
        this.playedGames = playedGames;
        this.wonGames = wonGames;
        this.wonGameResult = wonGameResult;
        this.maxScoreResult = maxScoreResult;
        this.lastGamePlayed = lastGamePlayed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(Integer playedGames) {
        this.playedGames = playedGames;
    }

    public Integer getWonGames() {
        return wonGames;
    }

    public void setWonGames(Integer wonGames) {
        this.wonGames = wonGames;
    }

    public Double getWonGameResult() {
        return wonGameResult;
    }

    public void setWonGameResult(Double wonGameResult) {
        this.wonGameResult = wonGameResult;
    }

    public Double getMaxScoreResult() {
        return maxScoreResult;
    }

    public void setMaxScoreResult(Double maxScoreResult) {
        this.maxScoreResult = maxScoreResult;
    }

    public Date getLastGamePlayed() {
        return lastGamePlayed;
    }

    public void setLastGamePlayed(Date lastGamePlayed) {
        this.lastGamePlayed = lastGamePlayed;
    }
}
