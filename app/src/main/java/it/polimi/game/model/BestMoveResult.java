package it.polimi.game.model;


public class BestMoveResult {
    private Integer id;
    private Player player;
    private Integer result;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public BestMoveResult(Player player, Integer result) {

        this.player = player;
        this.result = result;
    }

    public BestMoveResult(Integer id, Player player, Integer result) {

        this.id = id;
        this.player = player;
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
