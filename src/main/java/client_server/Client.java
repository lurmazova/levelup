package client_server;

import java.io.*;
import java.net.*;

import static utils.Constants.SERVER_PORT;

public class Client {

    public static void main(String args[]) throws Exception
    {
        Socket socket = new Socket("127.0.0.1",SERVER_PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintStream printStream = new PrintStream(socket.getOutputStream());
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String message;
        while (true) {
            System.out.print("Client: ");
            message = stdin.readLine();
            printStream.println(message);
            if (message.equalsIgnoreCase("BYE"))
            {
                System.out.println("Connection ended by client");
                break;
            }
            message=reader.readLine();
            System.out.print("Server: " + message + "\n");

        }
        socket.close();
        reader.close();
        printStream.close();
        stdin.close();
    }
}