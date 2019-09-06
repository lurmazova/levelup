package client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Constants.SERVER_PORT;
import static utils.Constants.THREADS_COUNT;

public class Server {

    int port;
    ServerSocket server = null;
    Socket client = null;
    ExecutorService pool;
    int clientcount = 0;

    public static void main(String[] args) throws IOException {
        Server serverobj = new Server(SERVER_PORT);
        serverobj.startServer();
    }

    Server (int port) {
        this.port =  port;
        pool = Executors.newFixedThreadPool(THREADS_COUNT);
    }

    public void startServer() throws IOException {

        server=new ServerSocket(SERVER_PORT);
        System.out.println("Server started");
        while (true)
        {
            client = server.accept();
            clientcount++;
            ServerThread runnable= new ServerThread(client, clientcount,this);
            pool.submit(runnable);
        }

    }

    private static class ServerThread implements Runnable {
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
                while(true){
                    message = reader.readLine();

                    System.out.print("Client(" + userId + "):" + message + "\n");
                    System.out.print("Server: ");
                    message = scanner.nextLine();
                    if (message.equalsIgnoreCase("bye"))
                    {
                        stream.println("BYE");
                        System.out.println("Connection ended by server");
                        break;
                    }
                    stream.println(message);
                }
                reader.close();
                client.close();
                stream.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
}