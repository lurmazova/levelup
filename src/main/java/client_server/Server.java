package client_server;

import database.DatabaseManager;
import sun.applet.Main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Constants.*;

public class Server {

    int port;
    ServerSocket server = null;
    Socket client = null;
    ExecutorService pool;
    int clientcount = 0;
    ArrayList<Socket> clientsConnected = new ArrayList<>();

    LinkedHashMap<String, String> fieldsToValues;
    DatabaseManager databaseManager;

    public static void main(String[] args) throws IOException, SQLException {
        Server serverobj = new Server(SERVER_PORT);
        serverobj.startServer();
    }

    Server (int port) throws SQLException {
        this.port = port;
        pool = Executors.newFixedThreadPool(THREADS_COUNT);
    }

    public void startServer() throws IOException {

        server = new ServerSocket(SERVER_PORT);
        System.out.println("Server started");
        while (true) {
            client = server.accept();
            clientcount++;
            clientsConnected.add(client);
            ServerThread runnable = new ServerThread(client, clientcount,this);
            pool.submit(runnable);
        }
    }

    private class ServerThread implements Runnable {
        Server server;
        Socket client;
        BufferedReader reader;
        PrintStream stream;
        Scanner scanner = new Scanner(System.in);
        int userId;
        String message;

        ServerThread(Socket client, int count, Server server) throws IOException {
            this.client=client;
            this.server=server;
            this.userId =count;
            System.out.println("Connection" + userId + " established with client " + client);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            stream = new PrintStream(client.getOutputStream());
        }

        @Override
        public void run() {
            try {
                while(true) {
                    message = reader.readLine();
                    String clientFullMessage = "Client(" + userId + "):" + message + "\n";
                    System.out.print(clientFullMessage);

                    broadcastMessages(clientFullMessage);
                    DatabaseManager dbm = saveMessageToDB("thread " + userId , "client " + userId, clientFullMessage);
                    searchMessages(dbm, MESSAGE_FIELD, clientFullMessage);

                    message = scanner.nextLine();
                    if (message.equalsIgnoreCase("bye")) {
                        stream.println("BYE");
                        System.out.println("Connection ended by server");
                        break;
                    }

                }
                reader.close();
                client.close();
                stream.close();
            }
            catch(IOException ex) {
                ex.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private void broadcastMessages(String clientFullMessage) throws IOException {
            for(Socket curClient : clientsConnected) {
                OutputStream os = curClient.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(clientFullMessage);
                bw.flush();
            }
        }

        private DatabaseManager saveMessageToDB(String threadNumber, String clientName, String message) throws SQLException {
            fieldsToValues = new LinkedHashMap<>();
            fieldsToValues.put(THREAD_NUMBER_FIELD, threadNumber);
            fieldsToValues.put(CLIENT_NAME_FIELD, clientName);
            fieldsToValues.put(MESSAGE_FIELD, message);
            databaseManager = new DatabaseManager(TABLE_NAME, fieldsToValues);

            databaseManager.createTable();
            databaseManager.insertIntoMessagesTable();
            return databaseManager;

        }

        private void searchMessages(DatabaseManager databaseManager,  String fieldName, String value) throws SQLException {
            databaseManager.searchInMessages(fieldName, value);
            databaseManager.closeConnection();
        }

    }
}