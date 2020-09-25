import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    ServerSocket socket;
    Socket server_socket;
    private int port;
    int client_id=0;
    ListaLibri libri=new ListaLibri();


    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("Usage java MyServer <port>");
            return;
        }
        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
    }


    public Server(int port) {
        System.out.println("Inizializzo il Server sulla porta : " + port);
        this.port = port;
    }

    public void start() {

        try {
            System.out.println("Avvio Server su porta : " + port);
            socket = new ServerSocket(port);
            System.out.println("Server avviato su porta : " + port);

            while (true) {
                System.out.println("Ascolto su porta: " + port);
                server_socket = socket.accept();
                System.out.println(client_id+"---> Connessione accettata da---->" + server_socket.getRemoteSocketAddress());
                ClientManager cm= new ClientManager(server_socket, libri);
                Thread t= new Thread(cm, "client_"+client_id);
                client_id++;
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Impossibile avviare Server su porta " + port);
            e.printStackTrace();
        }

    }
}
