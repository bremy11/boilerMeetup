import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;


public class serverTestClient {

    String serverAddress = "128.10.12.137";
    
    private BufferedReader in;
    private PrintWriter out;
     

     public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
       
         Socket socket = new Socket(serverAddress, 3111);
        // Make connection and initialize streams
         try{
             
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
             
             JSONObject obj = new JSONObject();
			  obj.put("action", "GET-ALL-EVENTS");
			  obj.put("location", "lawson b146");
			  out.println(obj.toJSONString());
			  System.out.println(obj.toJSONString());
             
             // Consume the initial welcoming messages from the server
             
         }catch (IOException e)
      {  
         e.printStackTrace();
      }finally{
             socket.close();
             out.println("hello world");
         }
    }
     
     public static void main(String[] args) throws Exception {
         serverTestClient n = new serverTestClient();
        n.connectToServer();
     }
}
