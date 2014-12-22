package myapplication;

import it.polimi.game.model.Bowl;
import it.polimi.game.model.Player;

import junit.framework.TestCase;

public class BowlTest extends TestCase {
    protected Bowl b1;
    protected Bowl b2;

    protected void setUp() {
        b1=new Bowl(1, 3);
        b2=new Bowl(2, 3);
    }


    public void testNumberSeeds() {
        Integer ns=b1.pullOutSeeds();
        assertEquals(3,(int)ns);
        ns=b1.pullOutSeeds();
        assertEquals(0,(int)ns);
        b1.incrementSeeds(3);
        assertEquals(3, (int) b1.getSeeds());
        b1.incrementSeeds();
        assertEquals(4, (int) b1.getSeeds());
    }

    public void testOppositeBowl(){
        b1.setOppositeBowl(b2);
        b2.setOppositeBowl(b1);
        assertEquals(b1.getOppositeBowl(),b2);
        assertEquals(b2.getOppositeBowl(),b1);
    }
}
