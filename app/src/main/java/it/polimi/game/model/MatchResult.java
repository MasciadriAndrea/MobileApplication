package it.polimi.game.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.polimi.game.persistence.PlayerDAO;

public class MatchResult {
    private Player winner;
    private Player loser;
    private Integer winnerSeeds;
    private Integer looserSeeds;
    private Integer seedsPerBowl;
    private Integer bestMoveP1;
    private Integer bestMoveP2;
    private Date data;
    private Player p1,p2;

    private PlayerDAO playerDAO;

    public MatchResult(Integer seedsPerBowl,Player p1, Player p2) {

        playerDAO = PlayerDAO.getInstance(Game.getInstance().getGameActivity());
        this.seedsPerBowl = seedsPerBowl;
        this.winner=null;
        this.loser=null;
        this.winnerSeeds=0;
        this.looserSeeds=0;
        this.bestMoveP1=0;
        this.bestMoveP2=0;
        this.p1=p1;
        this.p2=p2;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        this.data=calendar.getTime();
    }

    public void storeData(Player player, Integer seeds1, Integer seeds2){
        this.winner=player;
        this.loser=p1;
        if(this.winner.equals(this.p1)) {
            this.loser=p2;
            this.winnerSeeds=seeds1;
            this.looserSeeds=seeds2;
        }else{
            this.winnerSeeds=seeds2;
            this.looserSeeds=seeds1;
        }

        this.winner.setLastGamePlayed(this.data);
        this.loser.setLastGamePlayed(this.data);
        this.winner.incrementPlayedGames();
        this.loser.incrementPlayedGames();
        this.winner.incrementWins();
        this.loser.updateWonGameResult();
        this.winner.updateWonGameResult();
        this.loser.updateMaxScoreResult(looserSeeds.doubleValue());//TODO this result must be normalized!
        this.winner.updateMaxScoreResult(winnerSeeds.doubleValue());//TODO this result must be normalized!

        //Update players
        playerDAO.updatePlayer(winner);
        playerDAO.updatePlayer(loser);
        PlayerHandler.getInstance().updateList();

        //TODO here errors->commented
        BestMovesHandler.getInstance().insertResult(p1,this.getBestMove(this.p1));//TODO maybe this value should be normalized
        BestMovesHandler.getInstance().insertResult(p2,this.getBestMove(this.p2));//TODO maybe this value should be normalized
        //TODO save this 2 players in DB and also best 10
    }

    public Integer getBestMove(Player p){
        if(p.equals(this.p1)){
            return this.getBestMoveP1();
        }else{
            return  this.getBestMoveP2();
        }
    }

    public void updateBestMove(Integer numSeeds,Player p){
            if(p.equals(this.p1)){
                if(numSeeds>this.getBestMoveP1()){
                    this.setBestMoveP1(numSeeds);
                }
            }else{
                if(numSeeds>this.getBestMoveP2()){
                    this.setBestMoveP2(numSeeds);
                }
            }
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
