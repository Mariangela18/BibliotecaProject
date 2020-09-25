import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client implements Serializable {
    Socket client;
    private int port;
    private String address;


    public static void main(String args[]) {
        if (args.length != 2) {
            System.out.println("Usage java MyClient <address> & <port>");
            return;
        }
        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.start();

    }

    public Client(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        System.out.println("Avvio connessione Client su---->" + address + ": " + port);

        try {
            client = new Socket(address, port);
            System.out.println("Connessione Client avviata su : " + address + ":" + port);

            PrintWriter pw = new PrintWriter(client.getOutputStream());
            Scanner server_scanner = new Scanner(client.getInputStream());
            Scanner user_Scanner = new Scanner(System.in);
            String msg_to_send;
            String msg_received;
            boolean go = true;
            int scelta;

            while (go) {
                System.out.println("----------------------");
                System.out.println("1 - Aggiungi Libro");
                System.out.println("2 - Rimuovi Libro");
                System.out.println("3 - Elenco Archivio");
                System.out.println("4 - Salva Libro");
                System.out.println("5 - Esci");
                System.out.println("----------------------");
                System.out.println("Inserisci la tua scelta-->");
                scelta = user_Scanner.nextInt();

                switch (scelta) {
                    case 1:
                        System.out.println("Inserisci codice--->");
                        String cod_archiviazione = user_Scanner.next();
                        System.out.println("Inserisci Titolo--->");
                        String titolo = user_Scanner.next();
                        System.out.println("Inserisci Autore--->");
                        String autore = user_Scanner.next();
                        System.out.println("Inserisci Casa Editrice");
                        String Casa_editrice = user_Scanner.next();

                        msg_to_send = "ADD " + cod_archiviazione + " " + titolo + " " + autore + " " + Casa_editrice;
                        System.out.println("DEBUG: Mando " + msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();

                        msg_received = server_scanner.nextLine();

                        if (msg_received.equals("ADD_OK")) {
                            System.out.println("Libro aggiunto correttamente!");
                        } else {
                            System.out.println("ERRORE: messaggio sconosciuto->" + msg_received);
                        }
                        break;

                    case 2:
                        System.out.println("Inserisci codice--->");
                        cod_archiviazione = user_Scanner.next();
                        msg_to_send = "RIMUOVI " + cod_archiviazione;
                        System.out.println("DEBUG: Mando--->" +msg_to_send);
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();

                        if (msg_received.equals("RIMUOVI_OK")) {
                            System.out.println("Libro eliminato correttamente!");
                        } else if (msg_received.equals("RIMUOVI_ERROR")) {
                            System.out.println("Errore nell'aggiunta della persona!!!");
                        } else {
                            System.out.println("ERRORE: messaggio sconosciuto->" + msg_received);
                        }
                        break;

                    case 3:
                        msg_to_send = "LIST";
                        pw.println(msg_to_send);
                        pw.flush();
                        msg_received = server_scanner.nextLine();
                        boolean listing = true;
                        if (msg_received.equals("INIZIO")) {
                            System.out.println("Ricezione lista....");
                            Thread.sleep(2000);
                            while (listing) {
                                msg_received = server_scanner.nextLine();
                                if (msg_received.equals("FINE")) {
                                    listing = false;
                                    System.out.println("Lista conclusa");
                                } else{
                                        System.out.println("Ho ricevuto--->" + msg_received);
                                    }
                                }

                        } else {
                            System.out.println("Messaggio sconosciuto: " + msg_received);
                        }
                        break;

                    case 4:

                        pw.println("SALVA");
                        pw.flush();
                        Thread.sleep(2000);

                        msg_received = server_scanner.nextLine();
                        if (msg_received.equals("SAVE_OK")) {
                            System.out.println("File Salvato correttamente");
                        } else if (msg_received.equals("SAVE_ERROR")) {
                            System.out.println("Errore nel salvataggio delfile");
                        } else {
                            System.out.println("Messaggio sconosciuto---->" + msg_received);
                        }
                        break;

                    case 5:
                        go = false;
                        System.out.println("Chiudo il client...");
                        msg_to_send = "ESCI";
                        pw.println(msg_to_send);
                        pw.flush();
                        client.close();
                        break;
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


