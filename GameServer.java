import java.io.*;
import java.net.*;

public class GameServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		//confirm arguments
		if( args.length != 1) {
			System.err.println("Invalid arugments. java GameSever <port>");
			System.exit(1);
		}
		
		
		// set the port
		int port = Integer.parseInt(args[0]);
		
		// try with resources
		try (
				//establish server socket
				ServerSocket server = new ServerSocket( port );
				// accept incoming client socket requests
				Socket game = server.accept();
				
				// grab output and input streams
				ObjectOutputStream out = new ObjectOutputStream(game.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(game.getInputStream());		
				)
		{
			
			// load the first message from in into incomingMessage
			Message incomingMessage = (Message) in.readObject();
			
			// go through this loop until a null object is sent
			while (incomingMessage.getContent() != null) {
				// Print the contents of the first message
				System.out.println("Received Message: " + incomingMessage.getContent());
				// set incoming message to the next message received
				incomingMessage = (Message) in.readObject();
				
			}		
		}
		// error handling, for now just print info
		catch(IOException e) {
			System.err.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}
		
		catch(ClassNotFoundException e) {
			System.err.println("ClassNotFoundException: " + e.getMessage());
			e.printStackTrace();
		}
		

	}

}
