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
			sendToAll(clientList, startMessage, 7);
		}

		
		// start the board. We should probably send a status message here, especially for gui?
		Board gameBoard = new Board();
		
		// start the deck
		Deck gameDeck = new Deck();
		gameDeck.createSolution();
		
		//boolean for gameOver
		boolean gameOver = false;
		
		//ask the deck for Deal. again in a block so that the array is limited scope
		{
			ArrayList<MessageDeal> dealArray = gameDeck.dealCards(clientCount);
			// deal the cards
			for(int i = 0; i < clientCount; i++) {
				//grab one of the MessageDeal
				MessageDeal currentDeal = dealArray.get(i);
				//set the type but this should be done by Deck not here
				currentDeal.setType(9);
				//send the MessageDeal
				clientList.get(i).sendMessage(currentDeal);
			}
		}
		
		// have a tracker for how many eliminated players there are
		int eliminatedCount = 0;
		
		// set up array to track eliminated players
		boolean [] eliminatedPlayers = new boolean[maxPlayers];
		
		// start a while loop that cycles through each players turn until game is over
		while( gameOver == false) {
			// calculate whose turn it is
			int currentPlayer = turnCount % maxPlayers;
			// iterate turnCount
			turnCount++;
			// send out a board status
			{
				MessageGUIUpdate boardUpdate = gameBoard.getGUIUpdate();
				sendToAll(clientList, boardUpdate, 7);
				/* Old status update info
				Message statusUpdate = new Message(11,-1);
				statusUpdate.setText(gameBoard.getStatus());
				sendToAll(clientList, statusUpdate, 7);
				*/
			}
			
			// have a 2 element boolean array for turnResults
			boolean [] turnResults = new boolean[2];
			
			
			
			// check to see if currentPlayer is eliminated
			if ( eliminatedPlayers[currentPlayer] == false ) {
				// send out a message that it's someones turn
				{
					Message statusMessage = new Message(11, -1);
					statusMessage.setText("It's " + clientList.get(currentPlayer).getName() + " turn!");
					sendToAll(clientList, statusMessage, 7);
				}
				// store the results of the turn in turnResults and start their turn
				turnResults = playerTurn(gameBoard, gameDeck, clientList, turnResults, currentPlayer);
				// check if game is over
				if( turnResults[0] == true) {
					gameOver = true;
				}
				// check if player is eliminated
				if( turnResults[1] == true) {
					// register player as eliminated
					eliminatedPlayers[currentPlayer] = true;
					eliminatedCount++;
				}
				
				//check if there's only 1 player still left
				if( eliminatedCount == (maxPlayers - 1) ) {
					//game is over
					gameOver = true;
					//if only 1 player left they win, figure out who it is
					int winningPlayer;
					for( int i = 0; i < maxPlayers; i++) {
						if( eliminatedPlayers[i] == false) {
							//if they're not eliminated they win
							winningPlayer = i;
							//send a win Message
							Message winningMessage = new Message(7, -1);
							winningMessage.setText("You win!");
							clientList.get(winningPlayer).sendMessage(winningMessage);
							//send a status message to everyone
							Message winnerMessage = new Message(11, -1);
							winnerMessage.setText(clientList.get(winningPlayer).getName() + " wins the game!");
							sendToAll(clientList, winnerMessage, 7);
						}
					}
				}
			}
			
			

		}
		
		// once the game is over we send out the gameOver message
		{
			Message gameOverMessage = new Message(14, -1);
			sendToAll(clientList, gameOverMessage, 7);
		}
		
		//close the game
		System.exit(0);
	}
	
	/*Below write all the methods for handling things that will need to be done repeatedly or should be seperate modules.
	 * Includes gathering Connections at start and processing turns including Move, Guess, Accuse
	 */
	
	/*This method handles gathering the connections, storing them in the ArrayList, and asking Player1 how many
	players will there be
	*/
	private static void gatherConnections(ServerSocket server) throws IOException, ClassNotFoundException{
		// create the boolean avatars array
		boolean [] avatars = new boolean[6];
		
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
			
			//start newClient thread
			Thread t = new Thread(newClient);
			t.start();
			
			// create a new message to be sent to client, type 10 (connnectionStatus), -1 for server
			MessageConnectionStatus connectionMessage = new MessageConnectionStatus(10, -1, avatars);
			
			// set the genericInt field to clientCount
			connectionMessage.setInt(clientCount);

			//send the connectionMessage
			newClient.sendMessage(connectionMessage);
			
			// receive ConnectionStatusMessage back and update avatars and overwrite maxPlayers if this is client 1
			{
				MessageConnectionStatus incomingMessage = (MessageConnectionStatus) newClient.getMessage();
				// troubleshooting
				System.out.println("Message received by main of type " + incomingMessage.getType() +  "from avatar# " + incomingMessage.getPlayer());
				// update the avatars
				avatars = incomingMessage.getAvatars();
				// if this was player 1 we need to update maxPlayers
				if (clientCount == 1) {
					maxPlayers = incomingMessage.getInt();
				}
				
				// set the players name
				newClient.setName(incomingMessage.getText());
				
			}
		}
	}
	
	// guess method
	private static void guess(Message guessMessage, Board gameBoard, ArrayList<ConnectionManager> clientList, int currentPlayer) {
			// cast guessMessage to MessageAccusation
			MessageAccusation guessMessageLocal = (MessageAccusation) guessMessage;
			
			// boolean for guessDisproven
			boolean guessDisproven = false;
			
			// ask the board to process the guess
			Message guessCheck = gameBoard.processGuess((MessageAccusation) guessMessage);
			
			// store the guess info in Strings for the status message
			String suspect = guessMessageLocal.getSuspect().toString();
			String weapon = guessMessageLocal.getWeapon().toString();
			String room = guessMessageLocal.getRoom().toString();
			
			// create a message to store the result of the guess
			MessageCheckGuess guessResult;
			
			//is the guess a valid one? guessCheck 21 is invalid, 20 is valid
			if( guessCheck.getType() == 21) {
				//if the guess is an invalid one we send it to the User and we're done here
				clientList.get(currentPlayer).sendMessage(guessCheck);
				//send status to rest of game
				{
					Message statusUpdate = new Message(11, -1);
					statusUpdate.setText(clientList.get(currentPlayer).getName() + " tried to make an invalid guess.");
					sendToAll(clientList, statusUpdate, 7);
				}
			}else {
				clientList.get(currentPlayer).sendMessage(guessCheck);
				// send out status message to everyone else
				{
					//send out the new board status
					MessageGUIUpdate boardStatusUpdate = gameBoard.getGUIUpdate();
					sendToAll(clientList, boardStatusUpdate, 7);
					
					Message statusMessage = new Message(11, -1);
					statusMessage.setText(clientList.get(currentPlayer).getName() + " has made a guess. They guessed \nCharacter: " + suspect + "\n"
							+ "Weapon: " + weapon + "\nRoom: " + room);
					sendToAll(clientList, statusMessage, 7);
				}
				/*We loop through the client list but it's a weird loop. We start at 
				 * currentPlayer+1 (the next player), we want to go through the loop as many times
				 * as their are players in the game which we get from clientList.size(). But 
				 * we have to add that to currentPlayer to get the end of the loop.
				 */
				
				
				
				for( int i = (currentPlayer + 1); i < (currentPlayer + clientList.size() ); i++) {
					// because we can loop from last player to first player we need to reset i back to 0 and then
					if( guessDisproven == false) {
						if ( i >= clientList.size() ) {
							i = i - clientList.size();
						}
						
						// now we do the guess thing on this currentClient
						// send the Guess message
						clientList.get(i).sendMessage(guessMessage);
						// receive the result
						guessResult = (MessageCheckGuess) clientList.get(i).getMessage();
						//is the guess disproven?
						if( guessResult.getDisproven() == true) {
							//we set guessDisproven to true
							guessDisproven = true;
							// we send the original player the message disproving the guess
							clientList.get(currentPlayer).sendMessage(guessResult);
							// we sent update message to everyone else
							{
								Message statusMessage = new Message(11, -1);
								statusMessage.setText(clientList.get(i).getName() + " disproved the guess!");
								sendToAll(clientList, statusMessage, 7);
							}
						}
					}
				}
					
				/* if guessDisproven is still false that means no one disproved the guess. Send back
				 * a Message of guessDisproven with status false from the server. For now this is a generic
				 * Message but this probably needs to be updated once Pete adds this class. I'm using genericInt
				 * set to 0 to mean no one disproved it
				 */
					
				if( guessDisproven == false) {
					MessageCheckGuess serverGuessResult = new MessageCheckGuess(true, null, 19);
					serverGuessResult.setPlayer(-1);
					clientList.get(currentPlayer).sendMessage(serverGuessResult);
					// send out a message to everyone else status update
					{
						Message statusMessage = new Message(11, -1);
						statusMessage.setText(clientList.get(currentPlayer).getName() + "'s guess was not disproven by anyone.");
						sendToAll(clientList, statusMessage, 7);
					}
				}
			}		
		}
	
	// move method
	private static void move(Message moveMessage, Board gameBoard, ArrayList<ConnectionManager> clientList, int currentPlayer) {
		// pass move request from player into board and accept a Message back
		Message moveResult = gameBoard.processMove(moveMessage);
		
		// send out a status message
		{
			
			//send a board update
			MessageGUIUpdate boardUpdate = gameBoard.getGUIUpdate();
			sendToAll(clientList, boardUpdate, 7);
			Message statusUpdate = new Message(11, -1);
			statusUpdate.setText(clientList.get(currentPlayer).getName() + " has moved!");
			sendToAll(clientList, statusUpdate, 7);
		}
		// send the Message back to client
		clientList.get(currentPlayer).sendMessage(moveResult);
		
	}
	
	// playerTurn method
	
	private static boolean[] playerTurn(Board gameBoard, Deck gameDeck, ArrayList<ConnectionManager> clientList, boolean [] turnResults, int currentPlayer) {
		//have a flag for tracking turnOver
		boolean turnOver = false;
		
		// store the turnOver locally
		boolean []  localResults = turnResults;
		
		
		
		
		// grab reference to currentClient
		ConnectionManager currentClient = clientList.get(currentPlayer);
		
		// create a starTurn Message
		Message yourTurn = new Message(2, -1);
		// send the message
		currentClient.sendMessage(yourTurn);
		
		// start a while loop that goes until turnOver is flagged
		while( turnOver == false) {
			// get the players action back
			Message playerAction = currentClient.getMessage();
			
			// run a switch for what do with whatever player did
			switch( playerAction.getType() ) {
				//case 3 is move
				case 3:
				{
					move(playerAction, gameBoard, clientList, currentPlayer);
					break;
				}
				// case 4 is player wants to guess
				case 4:
				{
					// call guess method
					guess(playerAction, gameBoard, clientList, currentPlayer);
					break;
				
				}
				// case 5 is player wants to accuse
				case 5:
				{
					MessageCheckSolution accusationResult = gameDeck.checkAccusation((MessageAccusation) playerAction);
					//parse success. if genericInt = 1 it's a win. this may need to be integrated into whatever Pete codes
					if (accusationResult.getCorrect()) {
						// send result to player
						clientList.get(currentPlayer).sendMessage((MessageCheckSolution) accusationResult);
						
						// send win message to user
						{
							
							Message winMessage = new Message(7, -1);
							currentClient.sendMessage(winMessage);
						}
						
						// send status message to everyone
						{
							Message statusUpdate = new Message(11,-1);
							statusUpdate.setText(currentClient.getName() + " solved the crime and wins!");
							sendToAll(clientList, statusUpdate, 7);
						}
						
						//send lose message to everyone but winner
						{
							Message loseMessage = new Message(8, -1);
							loseMessage.setText(currentClient.getName() + " solved the crime so you lose!");
							sendToAll(clientList, loseMessage, currentPlayer);
						}
						
						//set turnResult[0] to true to indicate turn is over
						localResults[0] = true;
					}
					// if it's not 1 it's a lose
					else {
						// send result to player
						System.out.println("Sending result to player");
						clientList.get(currentPlayer).sendMessage(accusationResult);

						{
							Message loseMessage = new Message(15, -1);
							loseMessage.setText("Your guess was wrong so you are eliminated!");
							currentClient.sendMessage(loseMessage);
							
						}
	
						{
							Message statusUpdate = new Message(11,-1);
							statusUpdate.setText(currentClient.getName() + "was wrong and they are out of the game!");
							sendToAll(clientList, statusUpdate, 7);
						}
						//set turnResult[1] to true to indicate player is eliminated
						localResults[1] = true;
						
					}
					// if a player accuses the turn is over because they are either out or they win
					turnOver = true;
					break;
					
				}
				// case 6 is pass
				case 6:
				{
					// if they pass the turn is over
					turnOver = true;
					break;
				}
			}
		}
		
		
		// return turnResults
		return localResults;
		
	}
	
	/* write a method to send a message to all connected clients. The third argument, an int allows
	 * the method to skip one client, this is important during a players turn to not break
	 * the communication loop between server and client. To not use this feature just pass
	 * a int that wouldn't be in clientList like 7 (since max is 7)
	 */
	private static void sendToAll(ArrayList<ConnectionManager> clientList, Message outMessage, int skipClient) {
		for ( int i = 0; i < clientList.size(); i++) {
			// we send the message as long as i doesn't = skipClint
			if( i != skipClient) {
				clientList.get(i).sendMessage(outMessage);
			}
		}
	}
}



class ConnectionManager implements Runnable{
	// store input and output object streams and a String name, a boolean flag
		ObjectOutputStream out;
		ObjectInputStream in;
		String name;
		boolean hasMessage = false;
		
		// have a class attribute that is incomingMEssage that can be returned when client asks
		Message inMessage;
		
		//public constructor takes name, in objectstream, out objectstream
		public ConnectionManager(String newName, ObjectOutputStream newOut, ObjectInputStream newIn) {
			this.name = newName;
			this.in = newIn;
			this.out = newOut;
		}
		
		public void run(){
			try {
				while( true ) {
					inMessage = (Message) in.readObject();
					hasMessage = true;
					// trouble shooting
					System.out.println("Thread received message " + inMessage.getType() + " from " + this.name);
				}
			}
			// catch exception and print stack trace
			catch(Exception e) {
				e.printStackTrace();
				
			}
		}

		// method to send a message to client, returns void for now
		public void sendMessage( Message newMessage) {
			try {
			out.writeObject(newMessage);
			out.flush();
			//troubleshooting
			System.out.println("CM sent message of type " + newMessage.getType() + "to player " + this.name);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// method to get whatever newMessage came in from client
		public Message getMessage() {
			try {
				//error checking
				while ( hasMessage == false) {
					Thread.sleep(1000);
				}
				System.out.println("CM returned message of type " + inMessage.getType() + " from " + this.name);
				hasMessage = false;
				return inMessage;
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