package it.polimi.game.model.megabrain;

import android.util.Log;

import it.polimi.game.model.Bowl;
import it.polimi.game.model.Game;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

public class Megabrain {
    private static Megabrain instance;
    private Integer id;
    private Player p1,p2,megabrain;
    private Integer nSeeds;
    private Integer maxScore,minScore;
    private static int levelMegabrain=5;
    private static int levelPlayer=2;
    private static double weight1=0.01;
    private static double weight2=0.1;


    public static Megabrain getInstance(){
        if (instance==null){
            instance=new Megabrain();
        }
        return instance;
    }

    public void initializate(Player p1,Player p2,Player megabrain, Integer nSeeds){
        this.p1=p1;
        this.p2=p2;
        this.nSeeds=nSeeds;
        this.id=0;
        this.megabrain=megabrain;
        this.maxScore=nSeeds*12;
        this.minScore=-maxScore;
      // this.maxScoreP= Game.getInstance().getnSeeds()*12;
    }

    public Turn buildTree(Integer[] board,Player activePlayer,Integer selectedBowlId,Turn idParent,int level){
        if(level>0) {
            //TODO Log.v("Megabrain","level "+Integer.toString(level));
            Turn turn = new Turn(idParent, activePlayer, selectedBowlId);
            int[] iniP1 = {0, 0, 0, 0, 0, 0, 0};
            int[] iniP2 = {0, 0, 0, 0, 0, 0, 0};
            for (int i = 0; i < 7; i++) {
                iniP1[i] = board[i];
                iniP2[i] = board[i + 7];
            }
            GameHandler gh = new GameHandler(p1, p2, iniP1, iniP2, nSeeds,Game.getInstance().getGh().getIsFastGame());
            gh.setActivePlayer(activePlayer);
            Integer selectedPointer,ns;
            if(activePlayer.equals(p1)){
                selectedPointer=selectedBowlId-1;
                 //selectedPointer=selectedBowlId;
                ns=iniP1[selectedPointer];
            }else{
                 selectedPointer=selectedBowlId-7;
                //selectedPointer=selectedBowlId;
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
                    if(!nextActivePlayer.equals(activePlayer)){
                        level--;
                    }
                    if(nextActivePlayer.equals(megabrain)) {
                        for (int i = 0; i < 6; i++) {
                           // Log.v("Megabrain", "megabrain sceglie "+moves[i].toString());
                            Integer start;
                            if (nextActivePlayer.equals(p1)) {//if megabrain (activePlayer) is p1
                                start=0;
                            } else {
                                start=7;
                            }
                            if(boardAfter[i+start]>0){
                                children[i] = buildTree(boardAfter, nextActivePlayer, moves[i], turn, level);
                            }else{
                                children[i]=null;
                            }
                        }
                    }else{
                        Turn root=new Turn();
                        root.setActualPlayer(nextActivePlayer);
                        root.setBoard(boardAfter);
                        root.setScoreDif(0);
                        Integer[] movesP = {};
                        Turn[] childrenP = new Turn[]{null, null, null, null, null, null};
                        //TODO
                        if (nextActivePlayer.equals(p1)) {//if megabrain (activePlayer) is p1
                            movesP = new Integer[]{1, 2, 3, 4, 5, 6};
                        } else {
                            movesP = new Integer[]{7, 8, 9, 10, 11, 12};
                        }
                        for (Integer i=0;i<6;i++) {
                            //TODO Log.v("Megabrain -> player","start build player tree");
                            Integer levelP=Math.min(level,this.levelPlayer);
                            childrenP[i]=buildTree(boardAfter, nextActivePlayer, movesP[i], root, levelP);
                            //TODO Log.v("Megabrain -> player","finish build player tree");
                        }
                        root.setChildren(childrenP);
                        Integer levelP=Math.min(level,this.levelPlayer);
                        Integer whatPlayerHaveChoose=evaluateTree(root,levelP).getPosition();
                       // Log.v("Megabrain", "player sceglie "+whatPlayerHaveChoose.toString());
                        Integer whatPlayerHaveChooseIndex=0;
                        if(whatPlayerHaveChoose==null){
                            whatPlayerHaveChoose=whatPlayerHaveChoose;
                        }else {
                            if (whatPlayerHaveChoose > 5) {
                                whatPlayerHaveChooseIndex = whatPlayerHaveChoose - 6;
                            }
                            children[whatPlayerHaveChooseIndex] = buildTree(boardAfter, nextActivePlayer, whatPlayerHaveChoose, turn, level);
                        }
                    }
                }else{
                  //  Log.v("Megabrain","stop growing ->finish game or level 0");
                }

                turn.setChildren(children);
                return turn;
            }
            return null;
        }
        else{
           // Log.v("Megabrain","stop growing -> level 0");
        }
        return null;

    }

    public ScorePos evaluateTree(Turn node,Integer level){
        Boolean isLeaf=true;
        if(node!=null){
            for(Integer i=0;i<6;i++){
                if(node.getChildren()[i]!=null){
                    isLeaf=false;
                }
            }
            if(level==0){
                isLeaf=true;
            }
            if(isLeaf){
               //Log.v("mb","leaf and the score is -> "+node.getScoreDif());
               return new ScorePos(node.getSelectedBowl(),node.getScoreDif());
            }else {
               //call this function for every child
                Integer[] childrenScore;
                Integer score;
                Integer position=1;
                if(node.getActualPlayer().equals(megabrain)){
                    score=minScore;
                }else{
                    score=maxScore;
                }
                childrenScore=new Integer[]{score, score, score, score, score, score};
                //TODO
                String str="";
                for(Integer i=0;i<6;i++){
                    if(node.getChildren()[i]!=null){
                        Integer levelnew;
                        if(!node.getActualPlayer().equals(node.getChildren()[i].getActualPlayer())){
                            levelnew=level-1;
                        }else{
                            levelnew=level;
                        }
                        childrenScore[i]=evaluateTree(node.getChildren()[i],levelnew).getScore();
                    }

                }
                //TODO
                str="";
                for(int j=0;j<childrenScore.length;j++){
                    str+=childrenScore[j].toString()+";";
                }
                //Log.v("mb","player: "+node.getActualPlayer().getName()+" score of his child: "+str);
                for(Integer foo=0;foo<childrenScore.length;foo++) {

                    if (node.getActualPlayer().equals(megabrain)) {
                        //I want to maximise

                        if (score < childrenScore[foo]) {
                            //TODO
                            //Log.v("m", "yeeeeeah is more score: " + score.toString() + " child score " + childrenScore[foo].toString());
                            score = childrenScore[foo];
                            position = foo;
                        } else {
                            //Log.v("m", "nooooo it's not more score: " + score.toString() + " child score " + childrenScore[foo].toString());
                        }
                    } else {
                        //I want to minimize
                        if (score > childrenScore[foo]) {
                            //TODO
                            //Log.v("m", "yeeeeeah is less score: " + score.toString() + " child score " + childrenScore[foo].toString());
                            score = childrenScore[foo];
                            position = foo;
                        } else {
                          //  Log.v("m", "nooo it's not less score: " + score.toString() + " child score " + childrenScore[foo].toString());
                        }
                    }
                }
                    Integer[] moves = {};
                    if (node.getActualPlayer().equals(p1)) {//if megabrain (activePlayer) is p1
                        moves = new Integer[]{1, 2, 3, 4, 5, 6};
                    } else {
                        moves = new Integer[]{7, 8, 9, 10, 11, 12};
                    }
                    //Log.v("mb","he choose ->" +position.toString());
                    return new ScorePos(moves[position],score);

            }
        }
        return new ScorePos(node.getSelectedBowl(),node.getScoreDif());
    }

    public Integer megabrainSelectBowlPosition(Integer[] board,Player activePlayer){
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
            children[i]=buildTree(board, activePlayer, moves[i], root, this.levelMegabrain);
        }
        root.setChildren(children);
        return evaluateTree(root,-1).getPosition();
    }

    private Integer getScoreDif(Player p1, Player p2, GameHandler gh,Turn turn){
        int scoreDif;
        if (p1.getId().equals(1)){//player 1 is megabrain
            scoreDif = gh.getBoard().getSemiBoardByPlayer(p1).getTray().getSeeds() - gh.getBoard().getSemiBoardByPlayer(p2).getTray().getSeeds();
            //scoreDif-= (gh.getBoard().getSemiBoardByPlayer(p1).getNumberOfNonEmptyBowl())*this.weight1;
            //scoreDif=scoreDif*2;
            scoreDif+= turn.getLostSeeds()*this.weight2;
        } else {
            scoreDif = gh.getBoard().getSemiBoardByPlayer(p2).getTray().getSeeds() - gh.getBoard().getSemiBoardByPlayer(p1).getTray().getSeeds();
            //scoreDif+= gh.getBoard().getSemiBoardByPlayer(p2).getNumberOfNonEmptyBowl()*this.weight1;
            //scoreDif=scoreDif*2;
            scoreDif+= turn.getLostSeeds()*this.weight2;
        }

        return scoreDif;
    }

    public Integer getId(){
        this.id++;
        return this.id;
    }

    public class ScorePos{
        private Integer position;
        private Integer score;

        public ScorePos(Integer position, Integer score) {
            this.position = position;
            this.score = score;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getPosition() {
            return position;
        }

        public void setPosition(Integer position) {
            this.position = position;
        }
    }
}
