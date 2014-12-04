import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A TCP server that runs on port 9090.  When a client connects, it
 * sends the client the current date and time, then closes the
 * connection with that client.  Arguably just about the simplest
 * server you can write.
 */
public class boilerMeetServer {

    /**
     * Runs the server.
     */
    private static class requestHandler extends Thread {
        
        private Socket socket;
        private int clientNumber;

        public requestHandler(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            //log("New connection with client# " + clientNumber + " at " + socket);
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                   System.out.println(input);
                }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, error");
                }
                log("Connection with client# " + clientNumber + " closed");
            }

        }
        private void log(String message) {
            System.out.println(message);
        }

    }
                
    
    public static void main(String[] args) throws IOException {
        
        
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(9899);
        try {
            while (true) {
                new requestHandler(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }

    }
}