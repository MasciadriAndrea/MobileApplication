package myapplication;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import java.sql.SQLException;
import java.util.Date;

import it.polimi.game.model.BestMoveResult;
import it.polimi.game.model.Player;
import it.polimi.game.persistence.BestMoveResultDAO;
import it.polimi.game.persistence.PlayerDAO;

/**
 * Created by Paolo on 28/12/2014.
 */
public class DBSaveBestMoveResultTest extends AndroidTestCase {

    private BestMoveResultDAO bestMoveResultDAO;
    private PlayerDAO playerDAO;
    private static final String name = "paolo";
    private static final Integer playedGames = 1;
    private static final Integer wonGames = 1;
    private static final Double wonGameResult = 1.0;
    private static final Double maxScoreResult = 1.0;
    private static final Date lastGamePlayed = new Date(System.currentTimeMillis());
    private static final Integer result = 10;

    public void setUp() throws SQLException {
        RenamingDelegatingContext context
                = new RenamingDelegatingContext(getContext(), "test_");
        bestMoveResultDAO = BestMoveResultDAO.getInstance(context);
        playerDAO = PlayerDAO.getInstance(context);
        bestMoveResultDAO.open();
        playerDAO.open();
    }

    public void testAddBestMoveResult(){
        BestMoveResult newBestMoveResult = setBestMoveResult();
        Long id = bestMoveResultDAO.addBestMoveResult(newBestMoveResult);
        newBestMoveResult.setId(id.intValue());
        Boolean ok = bestMoveResultDAO.deleteBestMoveResult(newBestMoveResult);
        assertTrue(ok);
    }

    private BestMoveResult setBestMoveResult() {
        Player player = playerDAO.addPlayer(setPlayer());
        return  new BestMoveResult(player,result);
    }

    public Player setPlayer(){
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
        bestMoveResultDAO.close();
        super.tearDown();
    }
}
