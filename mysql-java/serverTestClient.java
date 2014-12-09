import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;


public class serverTestClient {

    String serverAddress = "128.10.25.212";
    
    private BufferedReader in;
    private PrintWriter out;
     

     public void connectToServer() throws IOException {

        // Get the server address from a dialog box.
       
         Socket socket = new Socket(serverAddress, 3112);
        // Make connection and initialize streams
         try{
             
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
             
             JSONObject obj = new JSONObject();
             
             
             
             obj.put("location", "lawson b146");
             obj.put("id", "4");
             obj.put("name", "1");
             obj.put("position", "1");
             obj.put("location", "1");
             obj.put("description", "1");
             obj.put("startTime", "2001-05-17");
             obj.put("endTime", "2003-05-17");
             obj.put("numAttendees", "1");
			  
			  //obj.put("command", "ADD-EVENT");
			 //obj.put("command", "GET-ALL-EVENTS");
			 //obj.put("command", "GET-EVENT-INFO");
			  //obj.put("location", "lawson b146");
			  obj.put("command", "DEL-EVENT");
			  
			  out.println(obj.toJSONString());
			  //System.out.println(obj.toJSONString());
             
             // Consume the initial welcoming messages from the server
             String t;
             while((t = in.readLine()) !=null)
             {
             	System.out.println(t);
             }
             
         }catch (IOException e)
      {  
         e.printStackTrace();
      }finally{
             socket.close();
             //out.println("hello world");
         }
    }
     
     public static void main(String[] args) throws Exception {
         serverTestClient n = new serverTestClient();
        n.connectToServer();
     }
}
