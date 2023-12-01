import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class clientProjet {

    private Socket client;
    private BufferedReader TempLecture;
    private BufferedWriter TempEcriture;
    private String pseudo;

    public clientProjet(Socket client, String pseudo) {
        try {
            // zone en mémoire pour l'écriture
            this.TempEcriture = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.client = client;
            this.pseudo = pseudo;
            // zone en mémoire pour la lecture
            this.TempLecture = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void envoyerMessage() {
        try {
            TempEcriture.write(pseudo);
            TempEcriture.newLine();
            TempEcriture.flush();

            Scanner sc = new Scanner(System.in);
            while (client.isConnected()) {
                System.out.print("Vous : ");
                String message = sc.nextLine();
                try {
                    TempEcriture.write(pseudo + ":" + message);
                    TempEcriture.newLine();
                    TempEcriture.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 7777);
            System.out.println("Entrez votre pseudo ");
            Scanner sc = new Scanner(System.in);
            String pseudo = sc.nextLine();
    
            clientProjet clientInstance = new clientProjet(client, pseudo);
            clientInstance.ecouterMessage();
            System.out.println("Commencez à discutez :");
            clientInstance.envoyerMessage();
            
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public void ecouterMessage() {
        new Thread(new Runnable() {
            public void run() {
                String msgPourGroupe;
                while (client.isConnected()) {
                    try {
                        msgPourGroupe = TempLecture.readLine();
                        System.out.println(msgPourGroupe);
                    } catch (IOException e) {
                        break;
                    }
                }
            }
        }).start();
    }

}