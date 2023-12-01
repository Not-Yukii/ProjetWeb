package main.chat.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Serveur {
    private ServerSocket serverSocket;
    private final Set<ClientHandler> clientHandlers = new HashSet<>();

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error in server operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void broadcastMessage(String message, ClientHandler excludeUser) {
        for (ClientHandler aClient : clientHandlers) {
            if (aClient != excludeUser) {
                aClient.sendMessage(message);
            }
        }
    }

    public void removeUser(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        System.out.println("The user has been removed.");
    }

    public static void main(String[] args) {
        Serveur server = new Serveur();
        server.start(1234);
    }
}
