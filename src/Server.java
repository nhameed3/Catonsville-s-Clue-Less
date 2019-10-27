/* Server is the main driver of the server software for handling the multiplayer aspect of Clue-less.
It listens for new connections until it is told to start the game or it reaches its maximum capacity
of 6 players. It runs each connected player in its own thread for simultaneous activity
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	// store array of connected players, caps at 6
	static ClientManager [] clientList = new ClientManager[6];
	
	//counter for clients
	static int clientCount = 0;
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException{
		//confirm arguments
		if( args.length != 1) {
			System.err.println("Invalid arugments. java GameSever <port>");
			System.exit(1);
		}
		
		// set the port
		int port = Integer.parseInt(args[0]);
		
		//set up the serversocket
		ServerSocket server = new ServerSocket( port );
		
		// boolean flag for startGame
		boolean startGame = false;
		
		// listen for new connections until we hit 6 or startGame is flagged
		while( clientCount != 6 | startGame == false) {
			
			// accept client connection as Socket game
			Socket game = server.accept();
			// troubleshooting message
			System.out.println("Accepted connection at count " + clientCount);

			// grab output and input streams
			ObjectOutputStream out = new ObjectOutputStream(game.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(game.getInputStream());
			
			//create new ClientManager
			ClientManager newClient = new ClientManager("player" + (clientCount + 1), 
					out, in);
			
			//troubleshooting emssage
			System.out.println("Created newClient at count " + (clientCount + 1));
			// create a new Thread with this ClientManager
			Thread t = new Thread(newClient);
			
			// store newClient in clientList
			clientList[clientCount] = newClient;
			
			//start the new thread
			t.start();
			
			// increment clientCount
			clientCount++;
			
		}
	}
	
	
		
	
	/* Calculate which player by dividing turn by player count and remainder
	 * is whose turn it is
	*/
	private static int nextTurn(int playerCount, int turnTracker) {
		int turn = turnTracker % playerCount;
		// if previous was 1, then next is 2
		return turn;
	}

}
