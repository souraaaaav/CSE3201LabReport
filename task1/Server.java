package  task1;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serversocket;

    public Server(ServerSocket serverSocket){
        this.serversocket = serverSocket;
    }

    public void startServer(){
        try{
            System.out.println("Server Started");
            while(!serversocket.isClosed()){
                Socket socket = serversocket.accept();
                System.out.println("Client has connected");
                clientHandler clienthandler = new clientHandler(socket);
                Thread thread = new Thread(clienthandler);
                thread.start();

            }
        }
        catch (IOException e){

        }
    }

    public void closeServerSocket(){
        try{
            if(serversocket!=null){
                serversocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}