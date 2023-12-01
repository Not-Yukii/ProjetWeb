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
                Socket  client = connServer.accept();

                //Afficher le client connecté 
                InputStream clientInput = client.getInputStream();
                byte[] pseudoClient = new byte [1024];
                clientInput.read(pseudoClient);
                String pseudoClientString = new String(pseudoClient);

                System.out.println(pseudoClientString +" a rejoint la discussion");

                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
    } 
    catch (IOException e) {
			e.printStackTrace();
		}
}
}