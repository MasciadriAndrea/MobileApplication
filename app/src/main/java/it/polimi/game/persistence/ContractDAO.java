package it.polimi.game.persistence;

import java.sql.SQLException;

/**
 * Created by Paolo on 18/12/2014.
 */
public interface ContractDAO {

    public void open() throws SQLException;
    public void close();

}
