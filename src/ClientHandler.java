import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> ListeClients = new ArrayList<>();
    private Socket socket;
    private BufferedReader TempLecture;
    private BufferedWriter TempEcriture;
    private String pseudoClient;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.TempEcriture = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.TempLecture = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pseudoClient = TempLecture.readLine();
            ListeClients.add(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String messageClient;
        while (socket.isConnected()) {
            try {
                messageClient = TempLecture.readLine();
                diffuserMessage(messageClient);
            } catch (IOException e) {
                fermerTout(socket, TempLecture, TempEcriture);
                break;
            }
        }
    }

    private void diffuserMessage(String messageClient) {
        for (ClientHandler clientHandler : ListeClients) {
            if (!clientHandler.pseudoClient.equals(pseudoClient)) {
                try {
                    clientHandler.TempEcriture.write(messageClient);
                    clientHandler.TempEcriture.newLine();
                    clientHandler.TempEcriture.flush();
                } catch (IOException e) {
                    fermerTout(clientHandler.socket, clientHandler.TempLecture, clientHandler.TempEcriture);
                }
            }
        }
    }

    private void fermerTout(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
