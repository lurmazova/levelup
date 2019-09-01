package client_server;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    final Socket socket;
    final BufferedReader socketReader;
    final BufferedWriter socketWriter;
    final BufferedReader userInput;


    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        userInput = new BufferedReader(new InputStreamReader(System.in));
        new Thread(new Receiver()).start();
    }

    public void run() throws IOException {
        System.out.println("Type phrase(s) (hit Enter to exit):");
        while (true) {
            String userString = null;
            try {
                userString = userInput.readLine();
            } catch (IOException ignored) {}
            if (userString == null || userString.length() == 0 || socket.isClosed()) {
                close();
                break;
            } else {
                try {
                    socketWriter.write(userString + "\n");
                    socketWriter.flush();
                } catch (IOException e) {
                    close();
                }
            }
        }
    }


    public synchronized void close() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
                System.exit(0);
        }
    }

    public static void main(String[] args)  {
        try {
            new ChatClient("localhost", 8080).run();
        } catch (IOException e) {
            System.out.println("Unable to connect. Server not running?");
        }
    }


    private class Receiver implements Runnable{
        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    line = socketReader.readLine();
                } catch (IOException e) {
            }
        }
    }
}
}
