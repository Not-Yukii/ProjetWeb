package main.chat.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientThread implements Runnable {

    private BufferedReader reader;

    public ClientThread(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String messageFromServer;
            while ((messageFromServer = reader.readLine()) != null) {
                System.out.println("Server says: " + messageFromServer);
            }
        } catch (IOException e) {
            System.err.println("Error reading message from the server.");
            e.printStackTrace();
        }
    }
}
