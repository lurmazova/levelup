package database;
import com.sun.tools.javac.util.Assert;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.DB_URL;

public class DatabaseManager {
    public String tableName;
    public HashMap<String, String> fieldsToValues;
    Connection connection = DriverManager.getConnection(DB_URL);

    public DatabaseManager(String tableName,
                           HashMap<String, String> fieldsToValues,
                           String searchTerm) throws SQLException {
        this.tableName = tableName;
        this.fieldsToValues = fieldsToValues;
    }


    public void createTable() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + this.tableName +
                    " (ID INT auto_increment, " +
                    "THREAD_NUMBER VARCHAR(50) NOT NULL, " +
                    "CLIENT_NAME VARCHAR(50), " +
                    "MESSAGE VARCHAR(100))");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertIntoMessagesTable() throws SQLException {
        Statement statement = connection.createStatement();
        StringBuilder fields = new StringBuilder();
        StringBuilder values = new StringBuilder();

        fieldsToValues.forEach((key,value) ->
        {
           fields.append(key);
           fields.append(", ");
           values.append(value);
           values.append(", ");
        });
        String fields_res = fields.substring(0, fields.length()-2);
        String values_res = values.substring(0, values.length()-2);

        statement.executeUpdate("INSERT INTO " +
        this.tableName +
        "(" + fields_res + ") VALUES (" + values_res + ")");

        checkSQLWarnings(statement);
    }

    public void searchInMessages(String fieldName, String value) {
        try (PreparedStatement prepared =
                     connection.prepareStatement("SELECT " +
                             fieldName +
                             " FROM " + this.tableName +
                             " WHERE " + fieldName + " = ?")) {
            prepared.setString(1, value);

            try (ResultSet results = prepared.executeQuery()) {
                boolean found = false;

                while (results.next()) {
                    String login = results.getString(fieldName);
                    System.out.println("Search result for " + fieldName +
                    " is " + value);
                    found = true;
                }

                if (!found) {
                    System.out.println("Result not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    private void checkSQLWarnings(Statement statement) throws SQLException {
        Assert.checkNull(statement.getWarnings(), "The SQL operation was not successful");
        System.out.println("The SQL operation was successful");
    }
}



