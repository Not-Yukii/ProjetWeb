import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
            this.TempEcriture = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            this.client = client;
            this.pseudo = pseudo;
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
                String message = sc.nextLine();
                try {
                    TempEcriture.write(pseudo + ":" + message);
                    TempEcriture.newLine();
                    TempEcriture.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

            // Envoi du pseudo vers le server
            OutputStream out = client.getOutputStream();
            out.write(pseudo.getBytes());

            clientProjet clientProjet = new clientProjet(client, pseudo);
            System.out.println("Commencez à discutez :");
            clientProjet.ecouterMessage();
            clientProjet.envoyerMessage();

        } catch (IOException e) {
            // TODO Auto-generated catch block
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
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}