package task2;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) throws IOException {
        ServerSocket server_socket = new ServerSocket(22222);
        System.out.println("Server Started...");
        while (true){
            Socket Listen_socket = server_socket.accept();

            ObjectInputStream ois = new ObjectInputStream(Listen_socket.getInputStream());
            Object cMsg = null;
            try {
                cMsg = ois.readObject();
                if(cMsg.equals("even")){
                    System.out.println("Even Client Connected");
                    int str1 ;
                    BufferedReader user_input = new BufferedReader(new InputStreamReader(Listen_socket.getInputStream()));
                    str1 = Integer.parseInt(user_input.readLine());
                    if(str1%2==0){
                        System.out.println("Even Number");
                    }
                    else{
                        System.out.println("Please Enter Even Number");
                    }
                }
                else if(cMsg.equals("odd")){
                    System.out.println("Odd Client is Connected");
                    int str1 ;
                    BufferedReader user_input = new BufferedReader(new InputStreamReader(Listen_socket.getInputStream()));
                    str1 = Integer.parseInt(user_input.readLine());
                    if(str1%2!=0){
                        System.out.println("Odd Number");
                    }
                    else{
                        System.out.println("Please Enter Odd Number");
                    }

                }
                else if(cMsg.equals("square")){
                    System.out.println("Square Client is Connected");
                    int str1 ;
                    BufferedReader user_input = new BufferedReader(new InputStreamReader(Listen_socket.getInputStream()));
                    str1 = Integer.parseInt(user_input.readLine());
                    System.out.println("Square: "+(str1*str1));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
}