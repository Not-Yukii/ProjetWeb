import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> ListeClients = new ArrayList<>();
    private Socket socket; 
    private BufferedReader TempLecture; 
    private BufferedWriter TempEcriture; 
    private String pseudoClient; 


    public ClientHandler(Socket socket){
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
        // Tant que le client est encore connecté, on affiche et on lui demande de rentrez son message
        for(ClientHandler clientHandler : ListeClients){
            System.out.println(clientHandler.toString());
            if (!clientHandler.pseudoClient.equals(pseudoClient)) {
                try {
                    messageClient = TempLecture.readLine();
                    clientHandler.TempEcriture.write(messageClient);
                    clientHandler.TempEcriture.newLine();
                    clientHandler.TempEcriture.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        String listeString = "";
        for (int i = 0; i < ListeClients.size() ; i++)
            listeString += "" + ListeClients.get(i) + "\n";
        return listeString;
    }
    public static void main(String[] args) {
        
    }
}
