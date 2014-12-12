package myapplication;

import it.polimi.game.model.Player;
import it.polimi.game.model.Tray;

import junit.framework.TestCase;

public class TrayTest extends TestCase {
    protected Tray t1;
    private Player p1;

    protected void setUp() {
        p1=new Player("Foo",1);
        t1=new Tray(1,0,p1,null);
    }


    public void testNumberSeeds() {
        Integer ns=t1.getSeeds();
        assertEquals(0,(int)ns);
        t1.setSeeds(3);
        ns=t1.getSeeds();
        assertEquals(3,(int)ns);
        t1.incrementSeeds();
        assertEquals(4, (int) t1.getSeeds());
    }

    public void testIsTray() {
        assertTrue(t1.isTray());
        assertFalse(t1.isBowl());
    }

}
