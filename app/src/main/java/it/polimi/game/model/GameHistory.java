package it.polimi.game.model;

import java.util.Date;

public class GameHistory {
    private Player p1;
    private Player p2;
    private Integer score1;
    private Integer score2;
    private Date data;

    public GameHistory(Player p1, Player p2, Integer score1, Integer score2, Date data) {
        this.p1 = p1;
        this.p2 = p2;
        this.score1 = score1;
        this.score2 = score2;
        this.data = data;
    }

    public GameHistory() {
        super();
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public Integer getScore1() {
        return score1;
    }

    public void setScore1(Integer score1) {
        this.score1 = score1;
    }

    public Integer getScore2() {
        return score2;
    }

    public void setScore2(Integer score2) {
        this.score2 = score2;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
