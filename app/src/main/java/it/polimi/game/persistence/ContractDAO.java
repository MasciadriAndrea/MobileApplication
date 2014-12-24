package it.polimi.game.persistence;

import java.sql.SQLException;

public interface ContractDAO {

    public void open() throws SQLException;
    public void close();

}
