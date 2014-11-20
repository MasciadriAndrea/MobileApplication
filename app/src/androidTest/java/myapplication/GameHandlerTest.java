package myapplication;
import com.polimi.game.model.GameHandler;

import junit.framework.TestCase;

/**
 * Created by Andrea on 20/11/2014.
 */

public class GameHandlerTest extends TestCase {
    protected GameHandler gh;
    private Integer nIni=3;

    protected void setUp() {
       gh=new GameHandler(1,2);
    }



    public void testInitBoard() {
        Integer[] initialBoard={0,nIni,nIni,nIni,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        Integer[] board=gh.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(initialBoard[i],board[i]);
        }
    }

    public void testActivePlayer(){
        gh.playTurn(10);//bowl 10 can not be choosen from player1->nothing happen
        assertEquals(1,(int) gh.getActivePlayerId());//player 1 must play
        gh.playTurn(5);//player 1 play---> now active player change
        assertEquals(2,(int) gh.getActivePlayerId());
    }

    public void testTryGameNormal(){
        Integer[] expectedBoard={0,nIni+1,nIni+1,nIni+1,nIni-3,nIni,nIni,0,nIni,nIni,nIni,nIni,nIni,nIni};
        gh.playTurn(5);
        Integer[] board=gh.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(expectedBoard[i],board[i]);
        }
    }

    public void testTryGamePlayAgain(){
        Integer[] expectedBoard={2,5,0,0,nIni,nIni,nIni,0,nIni,nIni,nIni,nIni,4,4};
        assertEquals(1,(int) gh.getActivePlayerId());//player 1 must play
        gh.playTurn(4);
        assertEquals(1,(int) gh.getActivePlayerId()); //player 1 must play again
        gh.playTurn(3);
        assertEquals(2,(int) gh.getActivePlayerId());//now is turn of player 2
        Integer[] board=gh.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(expectedBoard[i],board[i]);
        }
    }

    public void testTryGameStalSeeds(){
        Integer[] expectedBoard={5,4,0,4,4,0,nIni,0,4,4,4,0,0,4};
        assertEquals(1,(int) gh.getActivePlayerId());//player 1 must play
        gh.playTurn(3);
        assertEquals(2,(int) gh.getActivePlayerId()); //player 2 must play
        gh.playTurn(12);
        assertEquals(1,(int) gh.getActivePlayerId());//player 1 must play
        gh.playTurn(6);//and he steals!!!!!!!
        assertEquals(2,(int) gh.getActivePlayerId()); //player 2 must play
        Integer[] board=gh.getBoardStatus();
        for(int i=0;i<14;i++){
            assertEquals(expectedBoard[i],board[i]);
        }
    }


    public void testEndGame(){
        int[] initialBoard= {3,1,0,0,0,0,0,1,0,0,3,0,1,2};
        Integer[] expectedBoard={4,0,0,0,0,0,0,7,0,0,0,0,0,0};
        GameHandler ghF=new GameHandler(1,2,initialBoard);
        ghF.playTurn(2);
        Integer[] board=ghF.getBoardStatus();
        assertTrue(ghF.getIsGameFinished());
        assertEquals((int) ghF.getWinnerId(),2);
        for(int i=0;i<14;i++){
            assertEquals(expectedBoard[i],board[i]);
        }
    }
}


