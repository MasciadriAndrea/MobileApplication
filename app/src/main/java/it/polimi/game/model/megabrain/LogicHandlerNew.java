package it.polimi.game.model.megabrain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import it.polimi.game.model.Bowl;
import it.polimi.game.model.GameHandler;
import it.polimi.game.model.Player;

/**
 * Created by Andrea and Anna on 20/12/2014.
 */
public class LogicHandlerNew {
    private static LogicHandlerNew instance;
    private Integer id;

    public Integer getId(){
        this.id++;
        return this.id;
    }

    public static LogicHandlerNew getInstance(){
        if (instance==null){
            instance=new LogicHandlerNew();
        }
        return instance;
    }

    protected LogicHandlerNew() {
        this.id=0;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fw=new FileWriter("tree.txt");
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write("end \n");
        Player p1=new Player("Anna",1);
        Player p2=new Player("Andrea",2);
        Integer[] board={3,3,3,3,3,3,0,3,3,3,3,3,3,0};
        Integer nSeeds=3;
        for(Integer i=1;i<7;i++){
            LogicHandlerNew.getInstance().recursivePlay(board,p1,p1,p2,i,nSeeds,0,LogicHandlerNew.getInstance().getId(),bw,11,"",p1);
        }
        System.out.println("number of valid configurations generated: "+ LogicHandlerNew.getInstance().id.toString());
        bw.close();
    }


    public void recursivePlay(Integer[] board,Player activePlayer,Player p1,Player p2,Integer selectedBowlId,Integer nSeeds,Integer idParent,Integer id,BufferedWriter bw,Integer level,String path,Player actualWinner) throws IOException {
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
                //System.out.println("P"+activePlayer.getId().toString()+"Choice"+selectedBowlId.toString());
                gh.playTurn(selectedBowlId);
                Integer[] boardAfter = gh.getBoard().getBoardStatus();
                Player nextActivePlayer = gh.getActivePlayer();
                Boolean gameFinish = gh.getIsGameFinished();
                //TODO
                String boardStr = "";
                for (int i = 0; i < 14; i++) {
                    boardStr = boardStr + boardAfter[i].toString() + "-";
                }
                //System.out.println(idParent.toString() + ";" + id.toString() + ";" + nextActivePlayer.getId().toString() + ";" + boardStr);
                path+=idParent.toString() + ";" + id.toString() + ";" + nextActivePlayer.getId().toString() + ";" + boardStr+" \n";
                //saveOutput(boardStr, idParent, id, nextActivePlayer.getId(), bw);
                Player oldWinner=actualWinner;
                actualWinner=p2;
                if(gh.getBoard().getSemiBoardByPlayer(gh.getP1()).getTray().getSeeds()>gh.getBoard().getSemiBoardByPlayer(gh.getP2()).getTray().getSeeds()){
                    actualWinner=p1;
                }
                if ((!gameFinish)&&(level>0)) {
                    Integer[] moves = {};
                    if (nextActivePlayer.equals(p1)) {
                        moves = new Integer[]{1, 2, 3, 4, 5, 6};
                    } else {
                        moves = new Integer[]{7, 8, 9, 10, 11, 12};
                    }
                    for (Integer i : moves) {
                        recursivePlay(boardAfter, nextActivePlayer, p1, p2, i, nSeeds, id, this.getId(), bw, level, path, actualWinner);
                    }
                }
                if(gameFinish){
                   if((!oldWinner.equals(gh.getMatchResult().getWinner()))&&(!gh.getMatchResult().getWinner().equals(gh.TIE))) {
                       path += "end \n";
                       saveOutput(path, bw);
                       System.out.println(gh.getMatchResult().getWinner().getName()+"at level "+level);
                   }
                }
            }
        }
    }

    public void saveOutput(String board, Integer idP, Integer id, Integer idNextP,BufferedWriter bw) throws IOException {
        bw.write(idP.toString()+";"+id.toString()+";"+idNextP.toString()+";"+board+" \n");
    }

    public void saveOutput(String board,BufferedWriter bw) throws IOException {
        bw.write(board);
    }
}

