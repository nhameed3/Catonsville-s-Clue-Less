/* Server is the main driver of the server software for handling the multiplayer aspect of Clue-less.
It listens for new connections until it is told to start the game or it reaches its maximum capacity
of 6 players. It runs each connected player in its own thread for simultaneous activity
*/

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * 
 */
public class Server{
	
	// define variables
	
	// clientCount: how many clients are connected
	static int clientCount = 0;
	
	// maxPlayers: how many Players are going to be int he game, defaults to 6 but player 1 can change
	static int maxPlayers = 6;
	
	// clientList: ArrayList of ConnectionManager to store the connected clients
	static ArrayList<ConnectionManager> clientList = new ArrayList<ConnectionManager>();
	
	// turnCount: int to track how many turns we have
	static int turnCount = 0;
	
	// boolean for gameOver
	static boolean gameOver = false;
	
	
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
		
		// gather the connections. This is how all connection and what not are started.
		gatherConnections(server);
		
		// Now we have all the connections we need and can start the game
		System.out.println("Start game now");
		
		// send out the start message to all players. Putting into a block so startMessage doesn't stick around forever
		{
			// create startMessage
			Message startMessage = new Message(1, -1);
			// send startMessage to all clients
			sendToAll(clientList, startMessage);
		}

		
		// start the board. We should probably send a status message here, especially for gui?
		Board gameBoard = new Board();
		
		// start the deck
		Deck gameDeck = new Deck();
		
		// ask the deck for Deal. again in a block so that the array is limited scope
		{
			// MessageDeal [] dealArray = gameDeck.deal();
			// deal the cards to the players with specialized method
			//dealCards(clientList, dealArray);
		}
		
		
		// start a while loop that cycles through each players turn until game is over
		while( !gameOver) {
			// calculate whose turn it is
			int currentPlayer = turnCount % maxPlayers;
			// create array to track whose out of the game. All start as false
			boolean [] eliminatedPlayers = new boolean[maxPlayers];
			
			// check to see if currentPlayer is eliminated
			if ( eliminatedPlayers[currentPlayer] == false ) {
				gameOver = playerTurn(gameBoard, gameDeck, clientList, currentPlayer);
				turnCount++;
			}

		}
		
		// once the game is over we send out the gameOver message
		{
			Message gameOverMessage = new Message(14, -1);
			sendToAll(clientList, gameOverMessage);
		}
	}
	
	/*Below write all the methods for handling things that will need to be done repeatedly or should be seperate modules.
	 * Includes gathering Connections at start and processing turns including Move, Guess, Accuse
	 */
	
	/*This method handles gathering the connections, storing them in the ArrayList, and asking Player1 how many
	players will there be
	*/
	private static void gatherConnections(ServerSocket server) throws IOException, ClassNotFoundException{
		while( clientCount < maxPlayers) {
			
			// create boolean array for tracking avatars
			boolean [] avatars = new boolean[6];
			
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
			MessageConnectionStatus connectionMessage = new MessageConnectionStatus(10, -1, avatars);
			
			// set the genericInt field to clientCount
			connectionMessage.setInt(clientCount);

			//send the connectionMessage
			newClient.sendMessage(connectionMessage);
			
			// receive ConnectionStatusMessage back and update avatars and overwrite maxPlayers if this is client 1
			{
				MessageConnectionStatus incomingMessage = (MessageConnectionStatus) newClient.getMessage();
				// update the avatars
				avatars = incomingMessage.getAvatars();
				// if this was player 1 we need to update maxPlayers
				if (clientCount == 1) {
					maxPlayers = incomingMessage.getInt();
				}
				
			}
		}
	}
	
	// guess method
	private static boolean guess(Message guessMessage, Board gameBoard, ArrayList<ConnectionManager> clientList, int currentPlayer) {
			// cast guessMessage to MessageGuAc
			guessMessage = (MessageGuAc) guessMessage;
		
	}
	
	// move method
	private static boolean move(Message moveMessage, Board gameBoard, ArrayList<ConnectionManager> clientList, int currentPlayer) {
		// case moveMessage to MessageMove
		moveMessage = (MessageMove) moveMessage;
		
	}
	
	// accuse method should return true if succesful or false if unsuccesufl
	
	private static boolean accuse(Message accuseMessage, Board gameBoard, ArrayList<ConnectionManager> clientList, int currentPlayer) {
		// cast to MessageGuAc
		accuseMessage = (MessageGuAc) accuseMessage;
		
		// boolean for success
		boolean success;
		
		// return whether we win or not
		return success;
	}
	
	// playerTurn method
	
	private static boolean playerTurn(Board gameBoard, Deck gameDeck, ArrayList<ConnectionManager> clientList, int currentPlayer) {
		//have two flags, one for tracking if turn is over the for tracking if game is over. will return game is over
		boolean gameOver = false;
		boolean turnOver = false;
		
		// grab reference to currentClient
		ConnectionManager currentClient = clientList.get(currentPlayer);
		
		// start a while loop that goes until turnOver is flagged
		while( turnOver == false) {
			// create a starTurn Message
			Message yourTurn = new Message(2, -1);
			// send the message
			currentClient.sendMessage(yourTurn);;
			// get the players action back
			Message playerAction = currentClient.getMessage();
			// run a switch for what do with whatever player did
			switch( playerAction.getType() ) {
				//case 3 is move
				case 3:
				{
					// call move
					move(playerAction);	
				}
				// case 4 is player wants to guess
				case 4:
				{
					// call guess method
					guess(playerAction, gameBoard, clientList, currentPlayer);
				
				}
				// case 5 is player wnats to accuse
				
				case 5:
				{
					gameOver = accuse(playerAction);
					
					// if a player accuses the turn is over because they are either out or they win
					turnOver = true;
				}
			}
		}
		
		
		// return gameOver
		return gameOver;
		
	}
	
	// write a method to send a message to all connected clients
	private static void sendToAll(ArrayList<ConnectionManager> clientList, Message outMessage) {
		for ( ConnectionManager c : clientList ) {
			c.sendMessage(outMessage);
		}
	}
	
	// write a method to deal out cards
	private static void dealCards(ArrayList<ConnectionManager> clientList, MessageDeal [] dealArray) {
		// just iterate through both arrays sending the cards out
		for( int i = 0; i < maxPlayers; i++) {
			ConnectionManager currentClient = clientList.get(i);
			currentClient.sendMessage(dealArray[i]);
		}
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
		
		// setters and getters
		public String getName() {
			return this.name;
		}
		
		// set name
		public void setName(String newName) {
			this.name = newName;
		}
	
}