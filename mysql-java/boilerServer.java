
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//sql database setup commands 
//CREATE DATABASE boilerMeetup
//CREATE TABLE events (id INTEGER, name VARCHAR(60), position VARCHAR(256), location VARCHAR(256), description VARCHAR(256), startTime TIMESTAMP, endTime TIMESTAMP, numAttendees INTEGER);

///////////////////////////// Mutlithreaded Server /////////////////////////////

public class boilerServer 
{
   final static int port = 3111;
   /**
    *Usage is useless now that we will be using JSON objects
    */
   /*static void printUsage() {
   	System.out.println("In another window type:");
   	System.out.println("telnet sslabXX.cs.purdue.edu " + port);
	System.out.println("GET-ALL-EVENTS");
	System.out.println("GET-EVENT-INFO|id");
	System.out.println("ADD-EVENT");
   }*/

   public static void main(String[] args )
   {  
      try
      {  
         //printUsage();
         int i = 1;
         ServerSocket s = new ServerSocket(port);
	 while (true)
         {  
            Socket incoming = s.accept();
            System.out.println("Spawning " + i);
            Runnable r = new ThreadedHandler(incoming);
            Thread t = new Thread(r);
            t.start();
            i++;
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }
}

/**
   This class handles the client input for one server socket connection. 
*/
class ThreadedHandler implements Runnable
{ 
   final static String ServerUser = "root";
   final static String ServerPassword = "1827";

   public ThreadedHandler(Socket i)
   { 
      incoming = i; 
   }

   public static Connection getConnection() throws SQLException, IOException
   {
      Properties props = new Properties();
      FileInputStream in = new FileInputStream("database.properties");
      props.load(in);
      in.close();
      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null)
        System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      System.out.println("url="+url+" user="+ServerUser+" password="+ServerPassword);

      return DriverManager.getConnection( url, ServerUser, ServerPassword);
   }


   /**
    *This function will send all of the current events
    *  that are in the db to populate the app's map.
    *  Here we use JSON objects to store and send the data
    */
   void getAllEvents( String [] args, PrintWriter out) {

      Connection conn=null;
      try
      {
	int numEvents = 0;
	conn = getConnection();
        Statement q1 = conn.createStatement();
	Statement q2 = conn.createStatement();
	
	ResultSet r1 = stat.executeQuery("SELECT COUNT(id) FROM events");
	ResultSet r2 = stat.executeQuery( "SELECT * FROM events");
	
	//Create a JSON Obj
	JSONObject obj = new JSONObject();
	
	//Get the current number of events
	while(r1.next()) {
		numEvents = Integer.parseInt(r1.getString(1));
	}
	
	//send the events to the app
	while(r2.next()) {
		obj.put("eventCount", numEvents);
		obj.put("id",  Integer.parseInt(r2.getString(1)) );
		obj.put("name", r2.getString(2));
		obj.put("position", r2.getString(3));
		obj.put("location", r2.getString(4));
		obj.put("description", r2.getString(5));
		 //might have to handle these differently since these are timestamps
		obj.put("startTime", r2.getString(6));
		obj.put("endTime", r2.getString(7));
		////
		obj.put("numAttendees", Integer.parseInt(r2.getString(8)) );
		out.println(obj.toJSONStirng());
	}

	result.close();

      }
      catch (Exception e) {
	System.out.println(e.toString());
	out.println(e.toString());
      }
      finally
      {
	try {
         if (conn!=null) conn.close();
	}
	catch (Exception e) {
	}
      }
   }

   /**
    *This will return the information for a specific event
    */
   void getEventInfo( String [] args, PrintWriter out) {

      Connection conn=null;
      try
      {
	conn = getConnection();

	PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM events WHERE name LIKE ?");
	pstmt.setString(1, args[1]); 
	ResultSet result = pstmt.executeQuery();

	while(result.next()) {
		obj.put("eventCount", numEvents);
		obj.put("id",  Integer.parseInt(result.getString(1)) );
		obj.put("name", result.getString(2));
		obj.put("position", result.getString(3));
		obj.put("location", result.getString(4));
		obj.put("description", result.getString(5));
		 //might have to handle these differently since these are timestamps
		obj.put("startTime", result.getString(6));
		obj.put("endTime", result.getString(7));
		////
		obj.put("numAttendees", Integer.parseInt(result.getString(8)) );
		out.println(obj.toJSONStirng());
	}
	result.close();
      }
      catch (Exception e) {
	System.out.println(e.toString());
	out.println(e.toString());
      }
      finally
      {
	try {
         if (conn!=null) conn.close();
	}
	catch (Exception e) {
	}
      }
   }   
   
   /**
    *This will add a new pet to the database
    */
   void addEvent(String[] args, PrintWriter out) {
   	Connection conn = null;

	try{
		conn = getConnection();
		conn.setAutoCommit(true);
		String sql = "INSERT INTO events VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		String[] petInfo = args[1].split(",");
	
		for(int i = 0; i < petInfo.length; i++) {
			pstmt.setString(i + 1, petInfo[i]);
		}
		pstmt.executeUpdate();
	}
	catch(Exception e) {
		System.out.println(e.toString());
		out.println(e.toString());
	}
      	finally {
		try {
         		if (conn!=null) conn.close();
		}
		catch (Exception e) {
		}
      }
   }

   void handleRequest( InputStream inStream, OutputStream outStream) 
   {
        Scanner in = new Scanner(inStream);         
        PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

	// Get parameters of the call
	String request = "fail";
	while(in.hasNextLine()){
    	request=in.nextLine();
    //...
	}
	
	//System.out.println(request);
	
	Object obj = null;
	JSONObject jso = (JSONObject) obj;
	
	JSONParser parser = new JSONParser();
	
//BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
	try{
		obj = parser.parse(request);
	}catch(Exception e)
	{
		System.out.println("HERE" + e.toString());
		out.println(e.toString());
	}
	
	JSONObject jsonObject = (JSONObject) obj;
	System.out.println(jsonObject.toJSONString());
	String req = (String) jsonObject.get("command");

	/**
	 *The JSON Object has the following fields:
	 *	command
	 * 	id
	 *	name
	 *	position
	 *	location
	 *	description
	 *	startTime
	 *	endTime
	 *	numAttendees
         */

	try {
		// Do the operation
		if (req.equals("GET-ALL-EVENTS")) {
			getAllEvents(args, out);
		}
		else if (request.equals("GET-EVENT-INFO")) {
			getEventInfo(args, out);
		}
		else if (request.equals("GET-CNT")) {
			getCount(args, out);
		}
		else if (request.equals("ADD-EVENT")) {
			addEvent(args, out);
		}
		else if (request.equals("DEL-EVENT")) {
			deleteEvent(args, out);
		}
	}
	catch (Exception e) {		
		System.out.println(requestSyntax);
		out.println(requestSyntax);

		System.out.println(e.toString());
		out.println(e.toString());
	}
   }

   public void run() {  
      try
      {  
         try
         {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
	    handleRequest(inStream, outStream);

         }
      	 catch (IOException e)
         {  
            e.printStackTrace();
         }
         finally
         {
            incoming.close();
         }
      }
      catch (IOException e)
      {  
         e.printStackTrace();
      }
   }

   private Socket incoming;
}
