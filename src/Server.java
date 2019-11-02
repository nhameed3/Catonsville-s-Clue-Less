/* Server is the main driver of the server software for handling the multiplayer aspect of Clue-less.
It listens for new connections until it is told to start the game or it reaches its maximum capacity
of 6 players. It runs each connected player in its own thread for simultaneous activity
*/

import java.io.*;
import java.net.*;
import java.util.*;

/*Main Driver is class
 * 
 */
public class Server{
	
	public static void main (String [] args) throws IOException, ClassNotFoundException {
		//confirm arguments
		if( args.length != 1) {
			System.err.println("Invalid arugments. java GameSever <port>");
			System.exit(1);
		}
				
		// set the port
		int port = Integer.parseInt(args[0]);
				
		//set up the serversocket
		ServerSocket server = new ServerSocket( port );
		
		/* set up variables to track how many players are connected and how many
		 * players the server is waiting for which defaults to 6
		 */
		
		int clientCount = 0;
		int maxPlayers = 6;
		
		// create ArrayList for storing connections
		ArrayList<ConnectionManager> clientList = new ArrayList<ConnectionManager>();
		
		// listen for new connections until we hit 6 or startGame is flagged
		while( clientCount < maxPlayers) {
					
			// accept client connection as Socket game
			Socket game = server.accept();
			// troubleshooting message
			System.out.println("Accepted connection at count " + clientCount);

			// grab output and input streams
			ObjectOutputStream out = new ObjectOutputStream(game.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(game.getInputStream());
			
			//create new ClientManager
			ConnectionManager newClient = new ConnectionManager("player" + (clientCount + 1), 
					out, in);
			
			//troubleshooting emssage
			System.out.println("Created newClient at count " + (clientCount + 1));
			
			// store newClient in clientList
			clientList.add(newClient);

			
			// increment clientCount
			clientCount++;
			
			// create a new message to be sent to client, type 10 (connnectionStatus), -1 for server
			Message connectionMessage = new Message(10, -1);
			// set the genericInt field to clientCount
			connectionMessage.setInt(clientCount);
			//send the connectionMessage
			newClient.sendMessage(connectionMessage);
			
			// if this is the first player we need to ask them how many players we should wait for
			if ( clientCount == 1) {
				Message newMessage = newClient.getMessage();
				maxPlayers = newMessage.getInt();
			}
		}
		
		// Now we have all the connections we need and can start the game
		System.out.println("Start game now");
		
		// send out the start message to all players
		for( ConnectionManager c : clientList) {
			Message startMessage = new Message(1, -1);
			startMessage.setInt(clientCount);
			c.sendMessage(startMessage);
		}
		
		//
	}
}

class ConnectionManager{
	// store input and output object streams and a String name, a boolean flag
		ObjectOutputStream out;
		ObjectInputStream in;
		String name;
		
		// have a class attribute that is incomingMEssage that can be returned when client asks
		Message inMessage;
		
		//public constructor takes name, in objectstream, out objectstream
		public ConnectionManager(String newName, ObjectOutputStream newOut, ObjectInputStream newIn) {
			this.name = newName;
			this.in = newIn;
			this.out = newOut;
		}

		// method to send a message to client, returns void for now
		public void sendMessage( Message newMessage) {
			try {
			out.writeObject(newMessage);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// method to get whatever newMessage came in from client
		public Message getMessage() {
			try {
				Message newMessage = (Message) in.readObject();
				return newMessage;
			}
			catch( Exception e) {
				e.printStackTrace();
				// for we should return a message with an error code but not putting that in right now
				return new Message();
			}
		}
}