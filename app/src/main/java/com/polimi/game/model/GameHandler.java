package com.polimi.game.model;

import java.util.Iterator;


public class GameHandler {
    private Integer activePlayerId;
    private Integer selectedBowlId;
    private Integer p1Id;
    private Integer p2Id;
    private Boolean isHH;
    private Boolean isGameFinished;
    private Board board;
    private MatchResult matchResult;
    private static Integer MEGABRAIN=1;
    private static Integer TIE=0;
    private static Integer ISGAMEFINISHED=3;
    private static Integer ISMYTURNAGAIN=2;
    private static Integer PERFORMSTEAL=1;
    private static Integer nSeeds=3;


    public GameHandler(Integer p1Id){
       //constructor for Human vs Megabrain mode
       Integer p2Id=this.MEGABRAIN;
       this.initGame(p1Id,p2Id,false,nSeeds);
       this.setBoard(new Board(p1Id,p2Id));
   }

    public GameHandler(Integer p1Id, Integer p2Id) {
        //constructor for H vs H mode
        this.initGame(p1Id, p2Id, true,nSeeds);
        this.setBoard(new Board(p1Id, p2Id));
    }

    public GameHandler(Integer p1Id, Integer p2Id, int[] initialBoard ) {
        //constructor for testing with initializated board
        this.initGame(p1Id,p2Id,true,initialBoard[2]);
        this.setBoard(new Board(initialBoard, p1Id, p2Id));
    }

    public void playTurn(Integer selectedBowlId){
        this.setSelectedBowlId(selectedBowlId);
        if((selectedBowlId>0)&&(selectedBowlId<=14)) {
            //if selectedBowlId inside the range of eligible containers id
            Container b = this.getBoard().getContainerById(selectedBowlId);
            if(b.isBowl()){
                //if the selected container is a Bowl
                if (b.getPlayerId().equals(this.getActivePlayerId())) {
                    //if the selected Bowl is own by activePlayer
                    if (b.getSeeds() > 0) {
                        //if the bowl is not empty
                        Integer seeds = ((Bowl) b).pullOutSeeds();
                        Container pointer = b;
                        for (int i = 1; i <= seeds; i++) {
                            pointer = pointer.getNextContainer();
                            if ((pointer.isTray()) && (!pointer.getPlayerId().equals(this.getActivePlayerId()))) {
                                pointer = pointer.getNextContainer();
                            }
                            pointer.incrementSeeds();
                        }
                        Integer gameStatus = this.getGameStatus(pointer);
                        if (!gameStatus.equals(this.ISGAMEFINISHED)) {
                            //if the game is not finished
                            if (gameStatus.equals(this.PERFORMSTEAL)) {
                                //steal seeds
                                this.stealSeeds((Bowl) pointer);
                            }
                            if (!gameStatus.equals(this.ISMYTURNAGAIN)) {
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
    }

    private void initGame(Integer p1Id, Integer p2Id,Boolean isHH,Integer initSeeds){
        this.p1Id = p1Id;
        this.p2Id = p2Id;
        this.setIsHH(isHH);
        this.setIsGameFinished(false);
        this.setActivePlayerId(p1Id);
        this.matchResult=new MatchResult(initSeeds);
    }
    private void updateMatchResult(Integer winnerId){
        this.matchResult.storeData(winnerId);
    }

    private Boolean zeroSeeds(Integer idp){
        Iterator<Container> ci=this.getBoard().getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if((c.isBowl())&&(c.getPlayerId().equals(idp))&&(c.getSeeds()>0)){
                return false;
            }
        }
        return true;
    }

    private Integer getGameStatus(Container lastPosition){
        if(this.zeroSeeds(this.getActivePlayerId())){//max priority
            return 3;// gamefinish
        }else{
            if((lastPosition.isTray())&&(lastPosition.getPlayerId().equals(this.getActivePlayerId()))){
                return 2;//again my turn
            }
            if((lastPosition.isBowl())&&(lastPosition.getSeeds().equals(1))&&(lastPosition.getPlayerId().equals(this.getActivePlayerId()))){
                //equals 1 due to pre-increment after movement
                return 1;//steal seeds
            }
        }
        return 0;//nothing happens
    }

    private void switchPlayer(){
        if (this.getActivePlayerId().equals(this.getP1Id())) {
            this.setActivePlayerId(this.getP2Id());
        }else{
            this.setActivePlayerId(this.getP1Id());
        }
    }

    private void finishGame(){
        Iterator<Container> ci=this.getBoard().getContainers().iterator();
        Integer seeds1=0;
        Integer seeds2=0;
        while (ci.hasNext()){
            Container c=ci.next();
            if(c.isBowl()){
                if(c.getPlayerId().equals(this.getP1Id())){
                    c=(Bowl)c;
                    seeds1=seeds1+(((Bowl) c).pullOutSeeds());
                }else{
                    seeds2=seeds2+(((Bowl) c).pullOutSeeds());
                }
            }
        }
        Tray t1=this.getBoard().getTrayByPlayerId(this.getP1Id());
        t1.incrementSeeds(seeds1);
        Tray t2=this.getBoard().getTrayByPlayerId(this.getP2Id());
        t2.incrementSeeds(seeds2);
        this.setIsGameFinished(true);
        if(t1.getSeeds()>t2.getSeeds()){
            this.matchResult.storeData(this.getP1Id());
        }else{
            if(t2.getSeeds()>t1.getSeeds()){
                this.matchResult.storeData(this.getP2Id());
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
        if((!this.getIsHH())&&(this.getActivePlayerId().equals(this.getP2Id()))){
            return true;
        }
        return false;
    }

    private void stealSeeds(Bowl lastPosition){
        Bowl oC=lastPosition.getOppositeBowl();
        Integer seeds=oC.pullOutSeeds();
        seeds=seeds+lastPosition.pullOutSeeds();
        this.getBoard().getTrayByPlayerId(this.getActivePlayerId()).incrementSeeds(seeds);
    }

    public Integer getActivePlayerId() {
        return activePlayerId;
    }

    public void setActivePlayerId(Integer activePlayerId) {
        this.activePlayerId = activePlayerId;
    }

    public Integer getP1Id() {
        return p1Id;
    }

    public void setP1Id(Integer p1Id) {
        this.p1Id = p1Id;
    }

    public Integer getP2Id() {
        return p2Id;
    }

    public void setP2Id(Integer p2Id) {
        this.p2Id = p2Id;
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

