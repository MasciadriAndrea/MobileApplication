package it.polimi.game.model;

import android.Manifest;

import java.util.Iterator;
import java.util.List;


public class GameHandler {
    private Player activePlayer;
    private Integer selectedBowlId;
    private Player p1;
    private Player p2;
    private Boolean isHH;
    private Boolean isGameFinished;
    private Board board;
    private MatchResult matchResult;
    private static Integer MEGABRAIN=1;
    private static Player TIE=null;
    private static Integer ISGAMEFINISHED=3;
    private static Integer ISMYTURNAGAIN=2;
    private static Integer PERFORMSTEAL=1;
    private static Integer nSeeds=3;


    public GameHandler(Player p1){
       //constructor for Human vs Megabrain mode
       Player p2=new Player("MEGABRAIN", this.MEGABRAIN);
       this.initGame(p1,p2,false,nSeeds);
       this.setBoard(new Board(p1,p2));
   }

    public GameHandler(Player p1, Player p2) {
        //constructor for H vs H mode
        this.initGame(p1, p2, true,nSeeds);
        this.setBoard(new Board(p1, p2));
    }

    public GameHandler(Player p1, Player p2, int[] initialBoardP1,int[] initialBoardP2,int iniS ) {
        //constructor for testing with initializated board
        this.initGame(p1,p2,true,iniS);
        this.setBoard(new Board(initialBoardP1,initialBoardP2, p1, p2));
    }

    public void playTurn(Integer selectedBowlId){
        this.setSelectedBowlId(selectedBowlId);
        Bowl currentBowl=null;
        if((selectedBowlId>0)&&(selectedBowlId<=12)) {
            //if selectedBowlId inside the range of eligible containers id
            SemiBoard sbAp=this.getBoard().getSemiBoardByPlayer(this.getActivePlayer());
            for (Bowl bowl:sbAp.getBowls()){
                if(bowl.getId().equals(selectedBowlId)){
                    currentBowl=bowl;
                }
            }
            if(currentBowl!= null){
              //if the selected Bowl is own by activePlayer
                    if (currentBowl.getSeeds() > 0) {
                        //if the bowl is not empty
                        Integer seeds = currentBowl.pullOutSeeds();
                        Bowl pointer = currentBowl;

                        SemiBoard sbAP=this.getBoard().getSemiBoardByPlayer(this.getActivePlayer());
                        SemiBoard sbOP=this.getBoard().getSemiBoardByPlayer(this.getInactivePlayer());
                        Integer start=sbAP.getBowls().indexOf(currentBowl);
                        Boolean finishedInTA=false;
                        Boolean finishedInTO=false;
                        while(seeds>0){
                            finishedInTO=false;
                            for(Bowl bowl:sbAP.getBowls().subList(start+1,sbAP.getBowls().size())){
                                if(seeds>0){
                                    bowl.incrementSeeds();
                                    seeds--;
                                    pointer=bowl;
                                }
                            }
                            if(seeds>0){
                                finishedInTA=true;
                                sbAP.getTray().incrementSeeds();
                                seeds--;
                            }
                            if (seeds>0) finishedInTA=false;
                            for(Bowl bowl:sbOP.getBowls()){
                                if(seeds>0){
                                    bowl.incrementSeeds();
                                    seeds--;
                                    pointer=bowl;
                                }
                            }
                            if(seeds>0){
                                finishedInTO=true;
                                sbOP.getTray().incrementSeeds();
                                seeds--;
                            }
                            start=1;
                        }


                        Boolean skip=false;
                        Integer gameStatus = this.getGameStatus(pointer,finishedInTA,finishedInTO);
                            if (gameStatus.equals(this.PERFORMSTEAL)) {
                                //steal seeds
                                this.stealSeeds(pointer);
                                //pay attention if now the game is finished!
                                if(this.zeroSeeds(this.getActivePlayer())){
                                    gameStatus=this.ISGAMEFINISHED;
                                }
                            }
                        if (!gameStatus.equals(this.ISGAMEFINISHED)) {
                            //if the game is not finished
                            if (!gameStatus.equals(this.ISMYTURNAGAIN)){
                                this.switchPlayer();
                                if (this.isMegabrainTurn()) {
                                    this.playTurn(this.megabrainSelectBowlId());
                                }
                            }
                        } else {
                            this.finishGame();
                        }
                        //else nothing is happen and the player must play again
                    }
                }
            }

    }

    private void initGame(Player p1, Player p2,Boolean isHH,Integer initSeeds){
        this.p1 = p1;
        this.p2 = p2;
        this.setIsHH(isHH);
        this.setIsGameFinished(false);
        this.setActivePlayer(p1);
        this.matchResult=new MatchResult(initSeeds);
    }
    private void updateMatchResult(Player winner){
        this.matchResult.storeData(winner);
    }

    private Boolean zeroSeeds(Player player){
        List<Bowl> ci=this.getBoard().getSemiBoardByPlayer(player).getBowls();
        for (Bowl bowl:ci){
            if(bowl.getSeeds()>0){
                return false;
            }
        }
        return true;
    }

    private Integer getGameStatus(Bowl lastPosition,Boolean finishedInTA, Boolean finishedInTO){
        //max priority
        if((!finishedInTA)&&(!finishedInTO)&&(lastPosition.getSeeds().equals(1))&&(this.getBoard().getSemiBoardByPlayer(this.getActivePlayer()).getBowls().contains(lastPosition))){
            //equals 1 due to pre-increment after movement
            return 1;//steal seeds
        }
        if(this.zeroSeeds(this.getActivePlayer())){
            return 3;// gamefinish
        }else {
            if (finishedInTA) {
                return 2;//again my turn
            }
        }
        return 0;//nothing happens
    }

    private void switchPlayer(){
        if (this.getActivePlayer().equals(this.getP1())) {
            this.setActivePlayer(this.getP2());
        }else{
            this.setActivePlayer(this.getP1());
        }
    }

    private void finishGame(){
        List<Bowl> bowlsP1=this.getBoard().getSemiBoardByPlayer(getP1()).getBowls();
        List<Bowl> bowlsP2=this.getBoard().getSemiBoardByPlayer(getP2()).getBowls();
        Integer seeds1=0;
        Integer seeds2=0;
        for(Bowl bowl:bowlsP1){
            seeds1=seeds1+bowl.pullOutSeeds();
        }
        Tray t1=this.getBoard().getSemiBoardByPlayer(this.getP1()).getTray();
        t1.incrementSeeds(seeds1);
        for(Bowl bowl:bowlsP2){
            seeds2=seeds2+bowl.pullOutSeeds();
        }
        Tray t2=this.getBoard().getSemiBoardByPlayer(this.getP2()).getTray();
        t2.incrementSeeds(seeds2);
        this.setIsGameFinished(true);
        if(t1.getSeeds()>t2.getSeeds()){
            this.matchResult.storeData(this.getP1());
        }else{
            if(t2.getSeeds()>t1.getSeeds()){
                this.matchResult.storeData(this.getP2());
            }else{
                this.matchResult.storeData(this.TIE);
            }
        }
        //TODO require the real player name and update statistic and so
    }

    private Integer megabrainSelectBowlId(){
        //TODO just for MEGABRAIN
        //in the GameState we will have a loop of game
        //and there we can decide if the selected bowl
        //should come from UI or MEGABRAIN
        return null;
    }

    private Boolean isMegabrainTurn(){
        if((!this.getIsHH())&&(this.getActivePlayer().equals(this.getP2()))){
            return true;
        }
        return false;
    }

    private void stealSeeds(Bowl lastPosition){
        Bowl oC=lastPosition.getOppositeBowl();
        Integer seeds=oC.pullOutSeeds();
        seeds=seeds+lastPosition.pullOutSeeds();
        this.getBoard().getTrayByPlayer(this.getActivePlayer()).incrementSeeds(seeds);
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getInactivePlayer(){
        if(this.getP1().equals(this.getActivePlayer())){
            return this.getP2();
        }
        return this.getP1();
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
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

    public Integer getSelectedBowlId() {
        return selectedBowlId;
    }

    public void setSelectedBowlId(Integer selectedBowlId) {
        this.selectedBowlId = selectedBowlId;
    }
    public Boolean getIsGameFinished() {
        return isGameFinished;
    }

    public void setIsGameFinished(Boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public Boolean getIsHH() {
        return isHH;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(MatchResult matchResult) {
        this.matchResult = matchResult;
    }

    public void setIsHH(Boolean isHH) {
        this.isHH = isHH;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}

