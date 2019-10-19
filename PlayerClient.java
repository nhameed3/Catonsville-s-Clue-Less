import java.net.*;
import java.io.*;


public class PlayerClient {
	
	
	public static void main(String [] args) throws IOException{
		
		// check to make sure we got the args we need
		if (args.length != 2) {
			System.err.println("Missing arugments. java PlayerClient <hostname> <port>");
			System.exit(1);
		}
		
		// parse host
		String host = args[0];
		// parse port
		int port = Integer.parseInt(args[1]);
		
		// try with resources
		try (
				// create a new socket with host and port info
				Socket gameSocket = new Socket(host, port);
				
				// grab input and output object streams. always output first.
				ObjectOutputStream out = new ObjectOutputStream( gameSocket.getOutputStream() );
				ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream() );
				
				// create a input for typing, this is just an example and will be deleted for final
				BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in) );
				
				
				)
		
		{
			// declare a null String variable 
			String userInput = "";
			
			// the while loop goes until userInput is set to the string 'end'
			while( !(userInput.equals( "end" ) ) ) {
				// set the userInput to the next line entered on console
				userInput = stdIn.readLine();
				
				// store the userInput as the content of a Message, also put in source as PlayerClient and destination as GameServer
				Message newMessage = new Message(userInput, "PlayerClient", "GameServer");
				
				// write the message object to out
				out.writeObject(newMessage);
			}

			
			//create a message whose fields are null. this will tell the server to stop
			Message endMessage = new Message();
			// send the end message
			out.writeObject(endMessage);
		}
		catch( UnknownHostException e) {
			System.err.println("Unkonw host exception for " + host);
			System.err.println(e.getMessage());
		}
		catch( IOException e) {
			System.err.println(" IO Exception for connecting to " + host);
			System.err.println(e.getMessage());
		}
		
		
	}
	
	
}
