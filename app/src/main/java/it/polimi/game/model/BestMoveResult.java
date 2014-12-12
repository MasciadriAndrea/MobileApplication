package it.polimi.game.model;


public class BestMoveResult {
    private Integer idP;
    private Integer result;

    //TODO Class methods not yet implemented


    public BestMoveResult(Integer idP, Integer result) {
        this.idP = idP;
        this.result = result;
    }

    public Integer getIdP() {
        return idP;
    }

    public void setIdP(Integer idP) {
        this.idP = idP;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
