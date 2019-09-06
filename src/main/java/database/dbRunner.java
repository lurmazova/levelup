package database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class dbRunner {
    public static void main(String[] args) throws SQLException {
        LinkedHashMap<String, String> fieldsToValues = new LinkedHashMap<>();
        fieldsToValues.put("THREAD_NUMBER", "thread_1");
        fieldsToValues.put("CLIENT_NAME", "C1");
        fieldsToValues.put("MESSAGE", "blabla");
        DatabaseManager databaseManager = new DatabaseManager("Messages", fieldsToValues, "blabla");
        databaseManager.createTable();
        databaseManager.insertIntoMessagesTable();
        databaseManager.searchInMessages("THREADS", "T1");
        databaseManager.closeConnection();
    }
}
