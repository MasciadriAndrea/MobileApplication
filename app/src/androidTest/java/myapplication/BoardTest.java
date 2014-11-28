package myapplication;


import com.polimi.game.model.Board;
import com.polimi.game.model.GameHandler;

import junit.framework.TestCase;


public class BoardTest extends TestCase {
    protected Board b;
    private Integer nIni=3;

    protected void setUp() {
        b=new Board(1,2);
    }

    public void testInitBoard() {
        Integer[] initialBoard={0,nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        Integer[] boardStatus=b.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(initialBoard[i],boardStatus[i]);
        }
    }

    public void testModifiedBoard(){
        int[] initialBoard={0,nIni,nIni+2,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        Board b1=new Board(initialBoard,1,2);
        Integer[] boardStatus=b1.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals((Integer) initialBoard[i],boardStatus[i]);
        }
    }

}
