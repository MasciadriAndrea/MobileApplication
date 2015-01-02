package it.polimi.game.model.megabrain;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import it.polimi.game.model.Bowl;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

/**
 * Created by Andrea and Anna on 20/12/2014.
 */
public class LogicHandler {
    private static LogicHandler instance;
    private Integer id;
    private Integer maxScoreP;

    public Integer getId(){
        this.id++;
        return this.id;
    }

    public static LogicHandler getInstance(){
        if (instance==null){
            instance=new LogicHandler();
        }
        return instance;
    }

    protected LogicHandler() {
        this.id=0;
        this.maxScoreP= Game.getInstance().getnSeeds()*12;
    }

    public static void main(String[] args) throws IOException {
        /*FileWriter fw=new FileWriter("tree.txt");
        BufferedWriter bw=new BufferedWriter(fw);
        Player p1=new Player("Megabrain",1);
        Player p2=new Player("Andrea",2);
        Integer[] board={3,3,3,3,3,3,0,3,3,3,3,3,3,0};
        Integer nSeeds=3;

//        for(Integer i=1;i<7;i++){
//            LogicHandler.getInstance().recursivePlay(board,p1,p1,p2,i,nSeeds,0,LogicHandler.getInstance().getId(),bw,6);
//        }

        Turn turn = LogicHandler.getInstance().computeStatesTree(board, p2, p1, p2, 7, 3, null, 3);

        String anna = "hey Andrea";
//        System.out.println("number of valid configurations generated: "+LogicHandler.getInstance().id.toString());
//        bw.close();*/
       // System.out.println(Math.max(null, 5));
       // System.out.println(Math.min(null, -5));
    }


    public void recursivePlay(Integer[] board,Player activePlayer,Player p1,Player p2,Integer selectedBowlId,Integer nSeeds,Integer idParent,Integer id,BufferedWriter bw,Integer level) throws IOException {
        if(level>0) {
            level--;
            int[] iniP1 = {0, 0, 0, 0, 0, 0, 0};
            int[] iniP2 = {0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < 7; i++) {
                iniP1[i] = board[i];
                iniP2[i] = board[i + 7];
            }
            GameHandler gh = new GameHandler(p1, p2, iniP1, iniP2, nSeeds);
            gh.setActivePlayer(activePlayer);
            Boolean isEmpty = false;
            for (Bowl bowl : gh.getBoard().getSemiBoardByPlayer(activePlayer).getBowls()) {
                if ((bowl.getId().equals(selectedBowlId)) && (bowl.getSeeds().equals(0))) {
                    isEmpty = true;
                }
            }
            if (!isEmpty) {
                gh.playTurn(selectedBowlId);
                Integer[] boardAfter = gh.getBoard().getBoardStatus();
                Player nextActivePlayer = gh.getActivePlayer();
                Boolean gameFinish = gh.getIsGameFinished();
                //TODO
                String boardStr = "";
                for (int i = 0; i < 14; i++) {
                    boardStr = boardStr + boardAfter[i].toString() + "-";
                }
                System.out.println(idParent.toString() + ";" + id.toString() + ";" + nextActivePlayer.getId().toString() + ";" + boardStr);
                saveOutput(boardStr, idParent, id, nextActivePlayer.getId(), bw);
                if ((!gameFinish)&&(level>0)) {
                    Integer[] moves = {};
                    if (nextActivePlayer.equals(p1)) {
                        moves = new Integer[]{1, 2, 3, 4, 5, 6};
                    } else {
                        moves = new Integer[]{7, 8, 9, 10, 11, 12};
                    }
                    for (Integer i : moves) {
                        recursivePlay(boardAfter, nextActivePlayer, p1, p2, i, nSeeds, id, this.getId(), bw,level);
                    }
                }
            }
        }
    }

    public Integer megabrainSelectBowlPosition(Integer[] board,Player activePlayer,Player p1,Player p2,Integer nSeeds,Integer level){
        Turn root=new Turn();
        root.setActualPlayer(activePlayer);
        root.setBoard(board);
        root.setScoreDif(0);
        Integer[] moves = {};
        Turn[] children = new Turn[]{null, null, null, null, null, null};
        if (activePlayer.equals(p1)) {//if megabrain (activePlayer) is p1
            moves = new Integer[]{1, 2, 3, 4, 5, 6};
        } else {
            moves = new Integer[]{7, 8, 9, 10, 11, 12};
        }
        for (Integer i=0;i<6;i++) {
            children[i]=computeStatesTree(board, activePlayer, p1, p2, moves[i], nSeeds, root, level);
        }
        root.setChildren(children);
        this.recursiveCutChildren(root);
        int maxScore=-maxScoreP;
        int position= 0;
        Integer[] score={-maxScoreP,-maxScoreP,-maxScoreP,-maxScoreP,-maxScoreP,-maxScoreP};
        for(Integer i=0;i<6;i++){
            score[i]=recursiveGetMaxScore(root.getChildren()[i]);
            //Log.v("LogicHandler","position "+i.toString()+" has maximum score over one leaf equal to "+score[i].toString());
            if(maxScore < score[i]){
                maxScore = score[i];
                position = i;
            }
        }
        if(maxScore==-maxScoreP){
            Integer start;
            if (activePlayer.equals(p1)) {
                start=0;
            }else{
                start=7;
            }
            for(int i=start;i<6+start;i++){
                if(board[i]>0){
                    position=i-start;
                }
            }
        }
        return position;
    }

    public void recursiveCutChildren(Turn node){
        if(node!=null) {
            Turn[] children=node.getChildren();
            Boolean hasChild=false;
            for(Integer i=0;i<6;i++){
                if(children[i]!=null){
                    hasChild=true;
                }
            }
            if(hasChild){
                Integer[] score={maxScoreP,maxScoreP,maxScoreP,maxScoreP,maxScoreP,maxScoreP};
                if(!node.getActualPlayer().getId().equals(1)){//if it is not Megabrain
                    int minScore=maxScoreP;
                    int position= 0;
                    for(Integer i=0;i<6;i++){
                       score[i]=recursiveGetMinScore(node);
                        if(minScore > score[i]){
                            minScore = score[i];
                            position = i;
                        }
                    }
                    for(Integer i=0;i<6;i++){
                        if(score[i]>minScore){
                            node.getChildren()[i]=null;
                        }
                    }
                }
                for(Integer i=0;i<6;i++){
                    recursiveCutChildren(children[i]);
                }

            }
        }

    }

    public Integer recursiveGetMaxScore(Turn node){
        Integer maxScore=-maxScoreP;
        Integer score;
        Boolean isLeaf=false;
        if(node!=null){
            for(Integer i=0;i<6;i++){
                if(node.getChildren()[i]!=null){
                    score=recursiveGetMaxScore(node.getChildren()[i]);
                    isLeaf=true;
                }else{
                    score=-maxScoreP;
                }
                if(score>maxScore){
                    maxScore=score;
                }
            }
            if(isLeaf){
                Integer[] board=node.getBoard();
                return node.getScoreDif();
            }
        }
        return maxScore;
    }

    public Integer recursiveGetMinScore(Turn node){
        Integer minScore=maxScoreP;
        Integer score;
        Boolean isLeaf=false;
        if(node!=null){
            for(Integer i=0;i<6;i++){
                if(node.getChildren()[i]!=null){
                    isLeaf=true;
                    score=recursiveGetMinScore(node.getChildren()[i]);
                }else{
                    score=maxScoreP;
                }
                if(score<minScore){
                    minScore=score;
                }
            }
            if(isLeaf){
                Integer[] board=node.getBoard();
                return node.getScoreDif();
            }
        }
        return minScore;
    }

    public Turn computeStatesTree(Integer[] board,Player activePlayer,Player p1,Player p2,Integer selectedBowlId,Integer nSeeds,Turn parent,Integer level) {
        if(level>0) {

        Turn turn = new Turn(parent, activePlayer, selectedBowlId);

            level--;
            int[] iniP1 = {0, 0, 0, 0, 0, 0, 0};
            int[] iniP2 = {0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < 7; i++) {
                iniP1[i] = board[i];
                iniP2[i] = board[i + 7];
            }
            GameHandler gh = new GameHandler(p1, p2, iniP1, iniP2, nSeeds);
            gh.setActivePlayer(activePlayer);
            Integer selectedPointer,ns;
            if(activePlayer.equals(p1)){
                selectedPointer=selectedBowlId-1;
                ns=iniP1[selectedPointer];
            }else{
                selectedPointer=selectedBowlId-7;
                ns=iniP2[selectedPointer];
            }
            Integer lostSeeds=ns+selectedPointer-6;
            turn.setLostSeeds(lostSeeds);
            Boolean isEmpty = false;
            for (Bowl bowl : gh.getBoard().getSemiBoardByPlayer(activePlayer).getBowls()) {
                if ((bowl.getId().equals(selectedBowlId)) && (bowl.getSeeds().equals(0))) {
                    isEmpty = true;
                }
            }
            if (!isEmpty) {
                gh.playTurn(selectedBowlId);
                Integer[] boardAfter = gh.getBoard().getBoardStatus();

                turn.setBoard(boardAfter);
                turn.setScoreDif(getScoreDif(p1, p2, gh,turn));

                Player nextActivePlayer = gh.getActivePlayer();

                turn.setNextPlayer(nextActivePlayer);

                Boolean gameFinish = gh.getIsGameFinished();

                Turn[] children = new Turn[]{null, null, null, null, null, null};

                if ((!gameFinish)&&(level>0)) {
                    Integer[] moves = {};
                    if (nextActivePlayer.equals(p1)) {
                        moves = new Integer[]{1, 2, 3, 4, 5, 6};
                    } else {
                        moves = new Integer[]{7, 8, 9, 10, 11, 12};
                    }
                    for (int i = 0; i< 6; i++) {
                        children[i] = computeStatesTree(boardAfter, nextActivePlayer,p1, p2, moves[i], nSeeds,turn, level);
                    }
                }

                turn.setChildren(children);
                return turn;
            }
        }
        return null;
    }

    //TODO Anna! Change function to get the best choice
    private Integer getScoreDif(Player p1, Player p2, GameHandler gh,Turn turn){
        int scoreDif;
        if (p1.getId().equals(1)){//player 1 is megabrain
            scoreDif = gh.getBoard().getSemiBoardByPlayer(p1).getTray().getSeeds() - gh.getBoard().getSemiBoardByPlayer(p2).getTray().getSeeds();
            scoreDif+= gh.getBoard().getSemiBoardByPlayer(p1).getNumberOfNonEmptyBowl();
            //scoreDif=scoreDif*2;
            //scoreDif-= turn.getLostSeeds();
        } else {
            scoreDif = gh.getBoard().getSemiBoardByPlayer(p2).getTray().getSeeds() - gh.getBoard().getSemiBoardByPlayer(p1).getTray().getSeeds();
            scoreDif+= gh.getBoard().getSemiBoardByPlayer(p2).getNumberOfNonEmptyBowl();
            //scoreDif=scoreDif*2;
            //scoreDif-= turn.getLostSeeds();
        }

        return scoreDif;
    }

    public void saveOutput(String board, Integer idP, Integer id, Integer idNextP,BufferedWriter bw) throws IOException {
        bw.write(idP.toString()+";"+id.toString()+";"+idNextP.toString()+";"+board+"\n");
    }
}

