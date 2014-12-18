package myapplication;


import it.polimi.game.model.Board;
import it.polimi.game.model.Player;

import junit.framework.TestCase;


public class BoardTest extends TestCase {
    protected Board b;
    private Integer nIni=3;
    private Player p1;
    private Player p2;

    protected void setUp() {
        p1=new Player("Foo",1);
        p2=new Player("Bar",2);
        b=new Board(p1,p2);
    }

    public void testInitBoard() {
        Integer[] initialBoard={nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni,0};
        Integer[] boardStatus=b.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(initialBoard[i],boardStatus[i]);
        }
    }

    public void testModifiedBoard(){
        Integer[] expectedBoard={nIni,nIni+2,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni-1,0};
        int[] initialBoardP1={nIni,nIni+2,nIni,nIni,nIni,nIni,0};
        int[] initialBoardP2={nIni,nIni,nIni,nIni,nIni,nIni-1,0};
        Board b1=new Board(initialBoardP1,initialBoardP2,p1,p2);
        Integer[] boardStatus=b1.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals((Integer) expectedBoard[i],boardStatus[i]);
        }
    }

}
