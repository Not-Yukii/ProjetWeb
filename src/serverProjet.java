import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * serverProjet
 */
public class serverProjet {

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            ServerSocket connServer = new ServerSocket(7777);

            while (!connServer.isClosed()) {
                Socket client = connServer.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}