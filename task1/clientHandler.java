package  task1;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class clientHandler implements Runnable{

    public static ArrayList<clientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private  String clientUserName;

    clientHandler(Socket socket){
        try{
            this.socket= socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Server "+clientUserName + " has entered the chat");
        } catch (Exception e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        for(clientHandler ClientHandler : clientHandlers){
            try{
                if(!ClientHandler.clientUserName.equals(clientUserName)){
//                    String[] msg=messageToSend.split(",");
//                    if(ClientHandler.clientUserName.equals(msg[0])){
                    ClientHandler.bufferedWriter.write(clientUserName+" : "+messageToSend);
                    ClientHandler.bufferedWriter.newLine();
                    ClientHandler.bufferedWriter.flush();
//                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void removeEverything(){
        clientHandlers.remove(this);
        broadcastMessage("Server "+ clientUserName+ " has left the chat");
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeEverything();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
