package client_server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServerNew {
    public int port;
    public ServerSocket serverSocket;
    public Thread serverThread;
    ArrayList<Thread> threads = new ArrayList<>();
    BlockingQueue<SocketProcessor> q = new LinkedBlockingQueue<SocketProcessor>();

    public ChatServerNew(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    void run() {
        System.out.println("Server started on port " + port);
        serverThread = Thread.currentThread();
        while (true) {
            Socket socket = getNewConnection();
            if (socket != null) {
                try {
                    final SocketProcessor processor = new SocketProcessor(socket);
                    final Thread thread = new Thread(processor);
                    thread.start();
                    System.out.println("Thread accepted: " + thread.getName());
                    threads.add(thread);
                    q.offer(processor);
                } catch (IOException ignored) {
                }
            }
        }
    }

    private Socket getNewConnection() {
        Socket s = null;
        try {
            s = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public class SocketProcessor implements Runnable {
        Socket socket;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;

        public SocketProcessor(Socket socket) throws IOException {
            this.socket = socket;
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    line = bufferedReader.readLine();

                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (line == null) {
                        try {
                            socket.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        for (SocketProcessor socketProcessor : q) {
                            try {
                                socketProcessor.send(line);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                }
            }
        }

        public synchronized void send(String line) throws IOException {
            try {
                    bufferedWriter.write(line);
                    bufferedWriter.write("\n");
                    bufferedWriter.flush();

            } catch (IOException e) {
                socket.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new ChatServerNew(8080).run();
    }
}

