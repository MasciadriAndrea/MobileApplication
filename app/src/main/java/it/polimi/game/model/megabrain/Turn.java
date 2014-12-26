package it.polimi.game.model.megabrain;

import it.polimi.game.model.Player;

public class Turn {

    private Turn[] children;
    private Turn parent;
    private Player actualPlayer;
    private Player nextPlayer;
    private Integer selectedBowl;
    private Integer[] board;
    private Integer scoreDif;
    private Integer lostSeeds;

    public Turn(){
       this.children = null;
        this.parent = null;
        this.actualPlayer = null;
        this.nextPlayer = null;
        this.selectedBowl = null;
        this.board = null;
        this.scoreDif = null;
    }

    public Turn(Turn[] children, Turn parent, Player actualPlayer, Player nextPlayer, Integer selectedBowl, Integer[] board, Integer scoreDif) {
        this.children = children;
        this.parent = parent;
        this.actualPlayer = actualPlayer;
        this.nextPlayer = nextPlayer;
        this.selectedBowl = selectedBowl;
        this.board = board;
        this.scoreDif = scoreDif;
    }

    public Turn(Turn parent, Player actualPlayer, Integer selectedBowl) {
        this.children = null;
        this.parent = parent;
        this.actualPlayer = actualPlayer;
        this.nextPlayer = null;
        this.selectedBowl = selectedBowl;
        this.board = null;
        this.scoreDif = null;
    }

    public Turn[] getChildren() {
        return children;
    }

    public void setChildren(Turn[] children) {
        this.children = children;
    }

    public Turn getParent() {
        return parent;
    }

    public void setParent(Turn parent) {
        this.parent = parent;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public Integer getSelectedBowl() {
        return selectedBowl;
    }

    public void setSelectedBowl(Integer selectedBowl) {
        this.selectedBowl = selectedBowl;
    }

    public Integer[] getBoard() {
        return board;
    }

    public void setBoard(Integer[] board) {
        this.board = board;
    }

    public Integer getScoreDif() {
        return scoreDif;
    }

    public void setScoreDif(Integer scoreDif) {
        this.scoreDif = scoreDif;
    }

    public Integer getLostSeeds() {
        return lostSeeds;
    }

    public void setLostSeeds(Integer lostSeeds) {
        this.lostSeeds = lostSeeds;
    }
}
