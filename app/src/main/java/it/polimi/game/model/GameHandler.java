package it.polimi.game.model;

import java.util.Iterator;


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
       Integer p2Id=this.MEGABRAIN;
       this.initGame(p1,p2,false,nSeeds);
       this.setBoard(new Board(p1,p2));
   }

    public GameHandler(Player p1, Player p2) {
        //constructor for H vs H mode
        this.initGame(p1, p2, true,nSeeds);
        this.setBoard(new Board(p1, p2));
    }

    public GameHandler(Player p1, Player p2, int[] initialBoard ) {
        //constructor for testing with initializated board
        this.initGame(p1,p2,true,initialBoard[2]);
        this.setBoard(new Board(initialBoard, p1, p2));
    }

    public void playTurn(Integer selectedBowlId){
        this.setSelectedBowlId(selectedBowlId);
        if((selectedBowlId>0)&&(selectedBowlId<=14)) {
            //if selectedBowlId inside the range of eligible containers id
            Container b = this.getBoard().getContainerById(selectedBowlId);
            if(b.isBowl()){
                //if the selected container is a Bowl
                if (b.getPlayer().equals(this.getActivePlayer())) {
                    //if the selected Bowl is own by activePlayer
                    if (b.getSeeds() > 0) {
                        //if the bowl is not empty
                        Integer seeds = ((Bowl) b).pullOutSeeds();
                        Container pointer = b;
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
        Iterator<Container> ci=this.getBoard().getContainers().iterator();
        while (ci.hasNext()){
            Container c=ci.next();
            if((c.isBowl())&&(c.getPlayer().equals(player))&&(c.getSeeds()>0)){
                return false;
            }
        }
        return true;
    }

    private Integer getGameStatus(Container lastPosition){
        if(this.zeroSeeds(this.getActivePlayer())){//max priority
            return 3;// gamefinish
        }else{
            if((lastPosition.isTray())&&(lastPosition.getPlayer().equals(this.getActivePlayer()))){
                return 2;//again my turn
            }
            if((lastPosition.isBowl())&&(lastPosition.getSeeds().equals(1))&&(lastPosition.getPlayer().equals(this.getActivePlayer()))){
                //equals 1 due to pre-increment after movement
                return 1;//steal seeds
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
        Iterator<Container> ci=this.getBoard().getContainers().iterator();
        Integer seeds1=0;
        Integer seeds2=0;
        while (ci.hasNext()){
            Container c=ci.next();
            if(c.isBowl()){
                if(c.getPlayer().equals(this.getP1())){
                    c=(Bowl)c;
                    seeds1=seeds1+(((Bowl) c).pullOutSeeds());
                }else{
                    seeds2=seeds2+(((Bowl) c).pullOutSeeds());
                }
            }
        }
        Tray t1=this.getBoard().getTrayByPlayer(this.getP1());
        t1.incrementSeeds(seeds1);
        Tray t2=this.getBoard().getTrayByPlayer(this.getP2());
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

