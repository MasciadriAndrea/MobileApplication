package it.polimi.game.model;

public class MatchResult {
    private Player winner;
    private Integer p1Seeds;
    private Integer p2Seeds;
    private Integer seedsPerBowl;
    private Integer bestMoveP1;
    private Integer bestMoveP2;
    private String data;

    public MatchResult(Integer seedsPerBowl) {
        this.seedsPerBowl = seedsPerBowl;
        this.winner=null;
        this.p1Seeds=0;
        this.p2Seeds=0;
        this.bestMoveP1=0;
        this.bestMoveP2=0;
        //TODO full data field this.data
    }
    public void storeData(Player player){
        this.winner=player;
        // /TODO calculate indexes and update database
    }
    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Integer getP1Seeds() {
        return p1Seeds;
    }

    public void setP1Seeds(Integer p1Seeds) {
        this.p1Seeds = p1Seeds;
    }

    public Integer getP2Seeds() {
        return p2Seeds;
    }

    public void setP2Seeds(Integer p2Seeds) {
        this.p2Seeds = p2Seeds;
    }

    public Integer getSeedsPerBowl() {
        return seedsPerBowl;
    }

    public void setSeedsPerBowl(Integer seedsPerBowl) {
        this.seedsPerBowl = seedsPerBowl;
    }

    public Integer getBestMoveP1() {
        return bestMoveP1;
    }

    public void setBestMoveP1(Integer bestMoveP1) {
        this.bestMoveP1 = bestMoveP1;
    }

    public Integer getBestMoveP2() {
        return bestMoveP2;
    }

    public void setBestMoveP2(Integer bestMoveP2) {
        this.bestMoveP2 = bestMoveP2;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
