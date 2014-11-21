package myapplication;

import com.polimi.game.model.Bowl;

import junit.framework.TestCase;

/**
 * Created by Andrea on 20/11/2014.
 */
public class BowlTest extends TestCase {
    protected Bowl b1;
    protected Bowl b2;

    protected void setUp() {
        b1=new Bowl(1, 3, 1, null);
        b2=new Bowl(2,3,2, b1);
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

    public void testIsBowl() {
        assertTrue(b1.isBowl());
        assertFalse(b1.isTray());
    }

    public void testNextContainer(){
        b1.setNextContainer(b2);
        assertEquals(b1.getNextContainer(),b2);
        assertEquals(b2.getNextContainer(),b1);
    }

    public void testOppositeBowl(){
        b1.setOppositeBowl(b2);
        b2.setOppositeBowl(b1);
        assertEquals(b1.getOppositeBowl(),b2);
        assertEquals(b2.getOppositeBowl(),b1);
    }
}
