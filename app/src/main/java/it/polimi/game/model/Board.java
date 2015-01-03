package it.polimi.game.model;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private List<SemiBoard> playerElements;

    public Board(Player p1, Player p2) {
        this.buildBoard(p1,p2);
    }

    public Board(int[] initialBoardP1,int[] initialBoardP2, Player p1, Player p2) {
        this.buildBoard(initialBoardP1,initialBoardP2,p1,p2);
    }

    public SemiBoard getSemiBoardByPlayer(Player p){
        for (SemiBoard sm:this.playerElements){
            if (sm.getPlayer().equals(p)){
                return sm;
            }
        }return null;
    }

    public Integer[] getBoardStatus(){
        Integer[] res={0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int i=0;
        for(SemiBoard sb:this.playerElements){
            for(Bowl bowl:sb.getBowls()){
                res[i]=bowl.getSeeds();
                i++;
            }
            res[i]=sb.getTray().getSeeds();
            i++;
        }
        return res;
    }

    public Tray getTrayByPlayer(Player player){
        return this.getSemiBoardByPlayer(player).getTray();
    }

    private void buildBoard(Player p1, Player p2){
        Integer nIni=Game.getInstance().getnSeeds();
        int[] initialBoardDefault={nIni,nIni,nIni,nIni,nIni,nIni,0};
        this.buildBoard(initialBoardDefault,initialBoardDefault, p1, p2);
    }

    private void buildBoard(int[] initialBoardP1,int[] initialBoardP2, Player p1, Player p2){
        List bowlsP1=new ArrayList<Bowl>();
        List bowlsP2=new ArrayList<Bowl>();
        for (Integer i=0;i<initialBoardP1.length-1;i++){
           Bowl bowlp1=new Bowl(i+1,initialBoardP1[i]);
           Bowl bowlp2=new Bowl((12-i),initialBoardP2[5-i]);
           bowlp1.setOppositeBowl(bowlp2);
           bowlp2.setOppositeBowl(bowlp1);
           bowlsP1.add(bowlp1);
           bowlsP2.add(0,bowlp2);
        }
        Tray t1=new Tray(0,initialBoardP1[6]);
        Tray t2=new Tray(0,initialBoardP2[6]);
        this.playerElements=new ArrayList<SemiBoard>();
        playerElements.add(new SemiBoard(bowlsP1,t1,p1));
        playerElements.add(new SemiBoard(bowlsP2,t2,p2));
    }

}
