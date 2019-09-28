package database;

import utils.Constants;

import java.sql.SQLException;

public class DBRunner {
    public static void main(String[] args) throws SQLException {
        DatabaseManager databaseManager = new DatabaseManager(Constants.TABLE_NAME);
        databaseManager.dropTable();
        databaseManager.createTable();
    }
}
