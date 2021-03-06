package it.polimi.game.model;

import android.Manifest;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.polimi.core.Assets;
import it.polimi.core.GameMainActivity;
import it.polimi.game.model.megabrain.LogicHandler;
import it.polimi.game.model.megabrain.Turn;
import it.polimi.game.persistence.PlayerDAO;


public class GameHandler {
    private Player activePlayer;
    private Integer selectedBowlId;
    private Player p1;
    private Player p2;
    private Boolean isHH;
    private Boolean isGameFinished;
    private Board board;
    private Boolean isFastGame;
    private MatchResult matchResult;
    private static Integer MEGABRAIN=1;
    public static Player TIE=new Player("TIE",0);
    private static Integer ISGAMEFINISHED=3;
    private static Integer ISMYTURNAGAIN=2;
    private static Integer PERFORMSTEAL=1;
    private Integer nSeeds;

    public GameHandler(Player p1,Boolean isFastGame){
       //constructor for Human vs Megabrain mode
        nSeeds=Game.getInstance().getnSeeds();
       Player p2=PlayerHandler.getInstance().getPlayerById(MEGABRAIN);
        this.initGame(p1,p2,false,nSeeds,isFastGame);
        this.setBoard(new Board(p1,p2));


   }

    public Boolean graphicsOn(){
        if((this.equals(Game.getInstance().getGh()))&&(Game.getInstance().getGraphic()))
            return true;
        return false;
    }

    public GameHandler(Player p1, Player p2, Boolean isFastGame) {
        //constructor for H vs H mode
        nSeeds=Game.getInstance().getnSeeds();
        this.initGame(p1, p2, true,nSeeds, isFastGame);
        this.setBoard(new Board(p1, p2));
    }

    public GameHandler(Player p1, Player p2, int[] initialBoardP1,int[] initialBoardP2,int iniS, Boolean isFastGame ) {
        //constructor for testing with initializated board
        this.initGame(p1,p2,true,iniS, isFastGame);
        this.setBoard(new Board(initialBoardP1,initialBoardP2, p1, p2));
    }

    public void updatePositionBee(Integer bowlId){
        if(this.equals(Game.getInstance().getGh())){

            if(activePlayer.equals(p1)){
                Game.getInstance().setxBee1(Game.getInstance().getxBowl()[bowlId]);
                Game.getInstance().setyBee1(Game.getInstance().getyBowl()[bowlId]);
            }else{
                Game.getInstance().setxBee2(Game.getInstance().getxBowl()[bowlId]);
                Game.getInstance().setyBee2(Game.getInstance().getyBowl()[bowlId]);
            }}
        while(!Game.getInstance().beesInPosition()){}
    }

    public void updatePositionBee(){
        if(this.equals(Game.getInstance().getGh())){

            if(activePlayer.equals(p1)){
                Game.getInstance().setxBee1(Game.getInstance().getxTray()[0]);
                Game.getInstance().setyBee1(Game.getInstance().getyTray()[0]);
            }else{
                Game.getInstance().setxBee2(Game.getInstance().getxTray()[1]);
                Game.getInstance().setyBee2(Game.getInstance().getyTray()[1]);
            }}
        while(!Game.getInstance().beesInPosition()){}
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
                        if(this.equals(Game.getInstance().getGh())){
                            Game.getInstance().makeUnPlayable();
                        }
                        //update position bee
                         if(graphicsOn()){
                            updatePositionBee(selectedBowlId-1);
                        }
                        int position = currentBowl.getId();
                        if(activePlayer.equals(p1)){
                            position--;
                        }
                        List<Seed> seedsImg=Game.getInstance().getSeedsByPosition(position,this);
                        //if the bowl is not empty
                        SemiBoard sbAP=this.getBoard().getSemiBoardByPlayer(this.getActivePlayer());
                        SemiBoard sbOP=this.getBoard().getSemiBoardByPlayer(this.getInactivePlayer());
                        Integer seedsInTrayFirst=sbAp.getTray().getSeeds();
                        Integer seeds = currentBowl.pullOutSeeds();
                        Bowl pointer = currentBowl;
                        Integer start=sbAP.getBowls().indexOf(currentBowl)+1;
                        Boolean finishedInTA=false;
                        while(seeds>0){
                            for(Bowl bowl:sbAP.getBowls().subList(start,sbAP.getBowls().size())){
                                if(seeds>0){
                                    if(graphicsOn()){
                                        updatePositionBee(bowl.getId()-1);
                                    }
                                    position = bowl.getId();
                                    if(activePlayer.equals(p1)){
                                        position--;
                                    }
                                    moveSeed(seedsImg,position);
                                    playSound(Assets.seedID);
                                    bowl.incrementSeeds();
                                    seeds--;
                                    pointer=bowl;
                                }
                            }
                            if(seeds>0){
                                if(graphicsOn()){
                                    updatePositionBee();
                                    position=6;
                                    if(activePlayer.equals(p2)){
                                        position=13;
                                    }
                                    moveSeed(seedsImg,position);
                                    playSound(Assets.seedID);
                                }
                                finishedInTA=true;
                                sbAP.getTray().incrementSeeds();
                                seeds--;
                            }
                            if (seeds>0) finishedInTA=false;
                            for(Bowl bowl:sbOP.getBowls()){
                                if(seeds>0){
                                    if(graphicsOn()){
                                        updatePositionBee(bowl.getId()-1);
                                    }
                                    position = bowl.getId();
                                    if(activePlayer.equals(p2)){
                                        position--;
                                    }
                                    moveSeed(seedsImg,position);
                                    playSound(Assets.seedID);
                                    bowl.incrementSeeds();
                                    seeds--;
                                    pointer=bowl;
                                }
                            }
                            start=0;
                        }

                        Integer gameStatus = this.getGameStatus(pointer,finishedInTA);
                            if (gameStatus.equals(this.PERFORMSTEAL)) {
                                //steal seeds
                                this.stealSeeds(pointer);
                                //pay attention if now the game is finished!
                                if(this.zeroSeeds(this.getP1())){
                                    gameStatus=this.ISGAMEFINISHED;
                                }
                                if(this.zeroSeeds(this.getP2())){
                                    gameStatus=this.ISGAMEFINISHED;
                                }
                            }
                        Integer seedsInTrayAfter=sbAp.getTray().getSeeds();//Play again will be computed in different moves
                        if(this.equals(Game.getInstance().getGh())){
                            this.getMatchResult().updateBestMove(seedsInTrayAfter-seedsInTrayFirst,this.getActivePlayer());
                        }
                        if (!gameStatus.equals(this.ISGAMEFINISHED)) {
                            //if the game is not finished
                            if(this.equals(Game.getInstance().getGh())){
                                Game.getInstance().makePlayable();
                            }
                            if (!gameStatus.equals(this.ISMYTURNAGAIN)){
                                this.switchPlayer();

                            }if (this.isMegabrainTurn()) {
                                this.playTurn(this.megabrainSelectBowlId());
                            }
                        } else {
                            this.finishGame();
                        }
                        //else nothing is happen and the player must play again
                    }
                }
            }

    }

    private void moveSeed(List<Seed> seedsImg,Integer position){
        if(seedsImg.size()>0) {
            if (this.equals(Game.getInstance().getGh())) {
                seedsImg.get(0).updatePosition(position);
                seedsImg.remove(0);
            }
        }
    }

    private void initGame(Player p1, Player p2,Boolean isHH,Integer initSeeds, Boolean isFastGame){
        this.p1 = p1;
        this.p2 = p2;
        this.setIsHH(isHH);
        this.setIsGameFinished(false);
        this.setIsFastGame(isFastGame);
        int r=(int) Math.round(Math.random());
        this.setActivePlayer(p1);
        if((r==0)&&(!this.isHH)){
            this.setActivePlayer(p2);
        }
        this.matchResult=new MatchResult(initSeeds,p1,p2);
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

    private Integer getGameStatus(Bowl lastPosition,Boolean finishedInTA){
        //max priority
        if((!finishedInTA)&&(lastPosition.getSeeds().equals(1))&&(this.getBoard().getSemiBoardByPlayer(this.getActivePlayer()).getBowls().contains(lastPosition))){
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

        Player win=null;
        if(t1.getSeeds()>t2.getSeeds()){
            win=this.getP1();
        }else{
            if(t2.getSeeds()>t1.getSeeds()){
                win=this.getP2();
            }else{
                win=this.TIE;
            }
        }
        if(t2.getSeeds()+t1.getSeeds()!=(Game.getInstance().getnSeeds()*12)){
            System.out.println("!!!!ERROR: total amount of seeds different from expected!!!!!!!!!!!!");
        }
        if(this.equals(Game.getInstance().getGh())) {
            Game.getInstance().makePlayable();
            this.matchResult.storeData(win, t1.getSeeds(), t2.getSeeds());
        }
         // update all the result in matchResult, player and bestmoves
         playSound(Assets.winID);
    }

    private void playSound(int sound){
        if(this.equals(Game.getInstance().getGh())){
            Assets.playSound(sound);
        }
    }

    public Integer megabrainSelectBowlId(){
        Integer sbp= LogicHandler.getInstance().megabrainSelectBowlPosition(this.getBoard().getBoardStatus(),this.getActivePlayer(),this.p1,this.p2,nSeeds,5);
        Integer[] moves = {};
        if (activePlayer.equals(p1)) {
        //if megabrain (activePlayer) is p1
            moves = new Integer[]{1, 2, 3, 4, 5, 6};
        } else {
            moves = new Integer[]{7, 8, 9, 10, 11, 12};
        }
        Log.v("Megabrain","MB want to select bowl n: "+moves[sbp].toString());
        return moves[sbp];
    }

    private Boolean isMegabrainTurn(){
        if((!this.getIsHH())&&(this.getActivePlayer().getId().equals(MEGABRAIN))){
            return true;
        }
        return false;
    }

    private void stealSeeds(Bowl lastPosition){
        Bowl oC=lastPosition.getOppositeBowl();
        Integer seeds=0;int position;
        List<Seed> seedsImg1=new ArrayList<Seed>();
        if(oC.getSeeds()>0) {
            if (graphicsOn()) {
                updatePositionBee(oC.getId() - 1);
            }
            seeds = oC.pullOutSeeds();
            playSound(Assets.stealID);

            position = oC.getId();
            if (activePlayer.equals(p2)) {
                position--;
            }
             seedsImg1 = Game.getInstance().getSeedsByPosition(position, this);
        }
        if(graphicsOn()){
            updatePositionBee(lastPosition.getId() - 1);
        }
        seeds=seeds+lastPosition.pullOutSeeds();
        position = lastPosition.getId();
        if(activePlayer.equals(p1)){
            position--;
        }
        List<Seed> seedsImg2=Game.getInstance().getSeedsByPosition(position,this);
        if(graphicsOn()){
            updatePositionBee();
        }
        this.getBoard().getTrayByPlayer(this.getActivePlayer()).incrementSeeds(seeds);
        position = 6;
        if(activePlayer.equals(p2)){
            position=13;
        }
        moveSeed(seedsImg1,position);
        playSound(Assets.seedID);
        moveSeed(seedsImg2,position);
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

    public Boolean getIsFastGame() {
        return isFastGame;
    }

    public void setIsFastGame(Boolean isFastGame) {
        this.isFastGame = isFastGame;
    }
}

