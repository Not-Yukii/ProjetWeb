package main.chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            // Start the thread to listen for server messages
            new Thread(new ClientThread(reader)).start();
            
            // Here you can add the logic for sending messages to server
            // For example, you can take input from the console and send it to the server
            
        } catch (IOException e) {
            System.err.println("Unable to connect to the server.");
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

    public void closeConnection() {
        try {
            if (socket != null) socket.close();
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            System.err.println("Error while closing connection.");
            e.printStackTrace();
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        Client client = new Client("localhost", 1234);
        client.connect();
        // Example: send a message
        client.sendMessage("Hello, Server!");
        // Remember to close the connection when done
        // client.closeConnection();
    }


}