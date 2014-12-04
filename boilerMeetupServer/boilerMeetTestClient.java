import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class boilerMeetTestClient {

    String serverAddress = "128.10.12.137";
    
    private BufferedReader in;
    private PrintWriter out;
     

     public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
       
         Socket socket = new Socket(serverAddress, 9899);
        // Make connection and initialize streams
         try{
             
             in = new BufferedReader(
                                     new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(), true);
             
             // Consume the initial welcoming messages from the server
             out.println("hello world");
         }finally{
             socket.close();
         }
    }
     
     public static void main(String[] args) throws Exception {
         boilerMeetTestClient n = new boilerMeetTestClient();
        n.connectToServer();
     }
}