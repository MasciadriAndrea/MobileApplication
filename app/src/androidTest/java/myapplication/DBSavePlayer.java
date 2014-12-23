package myapplication;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.TestCase;

import java.sql.SQLException;
import java.util.Date;
import it.polimi.game.model.Player;
import it.polimi.game.persistence.PlayerDAO;

/**
 * Created by Paolo on 23/12/2014.
 */
public class DBSavePlayer extends AndroidTestCase {

    private PlayerDAO playerDAO;
    private static final String name = "paolo";
    private static final Integer playedGames = 1;
    private static final Integer wonGames = 1;
    private static final Double wonGameResult = 1.0;
    private static final Double maxScoreResult = 1.0;
    private static final Date lastGamePlayed = new Date(System.currentTimeMillis());

    public void setUp() throws SQLException {
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        playerDAO = new PlayerDAO(context);
        playerDAO.open();
    }

    public void testAddPlayer(){
        Player dbPlayer = playerDAO.addPlayer(setPlayer());
        Player staticPlayer = setPlayer();
        assertEquals(staticPlayer.getName(),dbPlayer.getName());

    }

    private Player setPlayer(){
        Player newPlayer = new Player();
        newPlayer.setName(name);
        newPlayer.setPlayedGames(playedGames);
        newPlayer.setWonGames(wonGames);
        newPlayer.setWonGameResult(wonGameResult);
        newPlayer.setMaxScoreResult(maxScoreResult);
        newPlayer.setLastGamePlayed(lastGamePlayed);
        return newPlayer;

    }

    public void tearDown() throws Exception{
        playerDAO.close();
        super.tearDown();
    }

}


