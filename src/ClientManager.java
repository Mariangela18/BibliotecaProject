import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientManager implements Runnable {
    private Socket cm_socket;
    private ListaLibri list;


    public ClientManager(Socket myclient, ListaLibri list) {
        cm_socket = myclient;
        this.list = list;

    }

    @Override
    public void run() {
        String t_id = Thread.currentThread().getName();
        System.out.println("Connessione accettata da---->" + cm_socket.getRemoteSocketAddress());
        Scanner client_scanner = null;
        PrintWriter pw =null;

        try {
            client_scanner = new Scanner(cm_socket.getInputStream());
            pw = new PrintWriter(cm_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean go = true;
        while (go) {

            String message = client_scanner.nextLine();
            System.out.println("Messaggio ricevuto: " + message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Comando Ricevuto---->" + cmd);


            if (cmd.equals("ADD")) {
                Libro b = new Libro();
                String cod_archiviazione = msg_scanner.next();
                String titolo = msg_scanner.next();
                String autore = msg_scanner.next();
                String editore = msg_scanner.next();
                String stato= msg_scanner.next();
                if(stato.equals("prestato")){
                    String data_prelievo=msg_scanner.next();
                    b.setData_prelievo(data_prelievo);
                }
                b.setCod_archiviazione(cod_archiviazione);
                b.setTitolo(titolo);
                b.setAutore(autore);
                b.setCasa_editrice(editore);
                b.setStato(stato);

                list.Aggiungi(b);
                System.out.println("SERVER LOG : Libro Aggiunto--->" + b);
                pw.println("ADD_OK");
                pw.flush();
            } else if (cmd.equals("RIMUOVI")) {
                Libro b = new Libro();
                String cod_archiviazione = msg_scanner.next();

                if(list.EliminaLibro(cod_archiviazione)==true){
                    System.out.println("SERVER LOG : Libro Eliminato");
                    pw.println("RIMUOVI_OK");
                    pw.flush();
                } else {
                    pw.println("Codice non valido");
                    pw.flush();
                }
            }
            else if (cmd.equals("LIST")) {
                    pw.println("INIZIO");
                    pw.flush();
                    ArrayList<Libro> tmp;
                    tmp = list.OttieniLista();
                    for (Libro b : tmp) {
                        pw.println(b);
                        pw.flush();
                    }
                    pw.println("FINE");
                    pw.flush();
            }
            else if (cmd.equals("SALVA")) {
                try {
                    list.OrdinaElenco();
                    FileOutputStream fileOutputStream=new FileOutputStream("Archivio.txt");
                    ObjectOutputStream o_o_s= new ObjectOutputStream(fileOutputStream);
                    o_o_s.writeObject(list.toString());
                    o_o_s.close();
                    pw.println("SAVE_OK");
                    pw.flush();
                    Thread.sleep(2000);
                    System.out.println("SERVER LOG : lista salvata correttamente");
                } catch (IOException e) {
                    pw.println("SAVE_ERROR");
                    pw.flush();
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (cmd.equals("ESCI")) {
                System.out.println("Server: Chiudo la connessione con " + cm_socket.getRemoteSocketAddress());
                try {
                    cm_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
        }
            else {
                System.out.println("Comando sconosciuto! ");
                pw.println("ERROR_CMD");
                pw.flush();
            }

        }
    }
}


