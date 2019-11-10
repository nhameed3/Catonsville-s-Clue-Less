import java.io.*;
import java.util.*;
import java.net.*;

/* Client is the driver of the game on client computers. It takes two arguments (IP and port) and connects to the
server.
*/
public class Client {

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		// store player number (set by user when picking characters)
		int playerNum;
		
		// check to make sure we got the args we need
		if (args.length != 2) {
			System.err.println("Missing arugments. java PlayerClient <hostname> <port>");
			System.exit(1);
		}
				
		// parse host
		String host = args[0];
		// parse port
		int port = Integer.parseInt(args[1]);
		
		// establish the socket
		Socket gameSocket = new Socket(host, port);
		
		//create a scanner for user input
		Scanner inScn = new Scanner(System.in);
		
		// boolean flag for gameOver
		boolean gameOver = false;
		
		//grab input and output streams
		ObjectOutputStream out = new ObjectOutputStream( gameSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream());
		
		// accept connectionStatus message from Server
		MessageConnectionStatus incomingMessage = (MessageConnectionStatus) getMessage(in);
		
		// call startUp and store it as outgoingMessage
		MessageConnectionStatus outgoingMessage = startUp(incomingMessage, inScn);
		
		// what player is this
		playerNum = outgoingMessage.getPlayer();
		
		// send output message
		sendMessage(outgoingMessage, out, playerNum);

		//create Player with info from outgoingMessage
		Player currentPlayer = new Player(outgoingMessage.getText(), outgoingMessage.getPlayer());
		
		// run a While loop until agmeOver = true;
		
		while( gameOver == false) {
			gameOver = playGame(currentPlayer, in, out);
		}
		
	
	}
	
	
	/* startUp takes the MessageConnectionStatus from Server and creates a return MEssageConnectionStatus
	 * 
	 */
	private static MessageConnectionStatus startUp( MessageConnectionStatus inMessage, Scanner inScn) {
		// create outgoing Message
		MessageConnectionStatus outMessage = new MessageConnectionStatus();
		
		// ask the player their username
		System.out.println("Please enter a username.");
		outMessage.setText( inScn.nextLine());
		
		// are we player 1?
		if( inMessage.getInt() == 1) {
			// if this is 1st client we ask how many players to wait for
			System.out.println("You are the 1st player to connect. How many players do you wish to play with?");
			// store users response as the genericInt in outgoingMessage
			outMessage.setInt(inScn.nextInt());
		}
		// grab the avatar array from inMessage
		boolean [] tempAvatars = inMessage.getAvatars();
		
		// what player does user want to be
		System.out.println("Which character do you want to be?");
		for( int i = 0; i < 6; i++) {
			
			// check if that character is available
					
			if (tempAvatars[i] == false) {
				switch( i ) {
					case 0:
					{
						System.out.println("0: Miss Scarlett");
						break;
					}
					case 1:
					{
						System.out.println("1: Reverend Green");
						break;
					}
					case 2:
					{
						System.out.println("2: Colonel Mustard");
						break;
					}
					case 3:
					{
						System.out.println("3: Professor Plum");
						break;
					}
					case 4:
					{
						System.out.println("4: Mrs. White");
						break;
					}
					case 5:
					{
						System.out.println("5: Mrs. Peacock");
						break;
					}
				}
			}
					
			
		}
		//grab the players choice
		int playerChoice = inScn.nextInt();
				
		//set that element to true
		tempAvatars[playerChoice] = true;
		
		// set whichPlayer to playerChoice
		outMessage.setPlayer(playerChoice);
		// write the new array to outMessage
		outMessage.setAvatars(tempAvatars);
		
		return outMessage;
		
	}
	
	/* 
	 * 
	 */
	private static boolean playGame(Player thisPlayer, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException{
		// store a boolean flag that we return
		boolean gameOver = false;
				
		
		// receive a message from the server
		Message inMessage = getMessage(in);
		
		
		// run a switch where we handle the Message as appropriate
		switch( inMessage.getType()) {
			// type 2 means its this players turn
			case 2:
			{
				// invoke the processTurn method that returns boolean for gameOver?
				processTurn(thisPlayer, in, out);
				break;
			}
			// type 11 means its a status message, for now print to screen
			case 11:
			{
				System.out.println(inMessage.getText());
				break;
			}
			// type 9 means this is a deal message, pass to player
			case 9:
			{
				//cast to MessageDeal
				inMessage = (MessageDeal) inMessage;
				thisPlayer.setHand((MessageDeal) inMessage);
				thisPlayer.getHand();
				break;
			}
			// type 10 means this is board status update
			case 10:
			{
				// do something with board status update?
				break;
			}
			// case 13 means someone else guessed and we have to disprove it
			case 4:
			{
				// cast it to MessageAccusation
				Message guessResult = thisPlayer.disprove( (MessageAccusation) inMessage);
				sendMessage(guessResult, out, thisPlayer.getPlayerNum());
				out.writeObject(guessResult);
				break;
			}
			// case 14 means game is over
			case 14:
			{
				gameOver = true;
				break;
			}
			// case 7 means we win
			case 7:
			{
				System.out.println("You win!");
				break;
			}
			// case 8 means we lose
			case 8:
			{
				System.out.println("You lose!");
				break;
			}
		}
		
		return gameOver;
		
	}
	
	// write a method that processes a players turn
	private static void processTurn(Player thisPlayer, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException{
		// boolean for turnOver
		boolean turnOver = false;
		// boolean for gameOver, only sets to true is acc
		// run this loop until turnOver is true
		while( turnOver == false) {
			// get a Message for the start of the players turn
			Message firstAction = thisPlayer.playerTurn();
			
			// is it a move?
			if (firstAction.getType() == 3 ) {
				// send that to the client
				sendMessage(firstAction, out, thisPlayer.getPlayerNum());
				// get Message back
				Message moveResult = getMessage(in);
				// check to see if it was a valid move
				if ( moveResult.getType() == 16 ) {
					// if it was a valid move can they make a guess?
					if ( moveResult.getInt() == 1 ) {
						// after a valid move they're only going down one of these cases and then turn is over
						turnOver = true;
						Message guessMessage = thisPlayer.makeGuess();
						// did player make a guess, an accusation, or pass?
						switch( guessMessage.getType() ) {
							//case 4 means they made a guess
							case 4:
							{		
								//send guesMessage to server
								sendMessage(guessMessage, out, thisPlayer.getPlayerNum());
								
								// receive result back
								MessageCheckGuess resultMessage = (MessageCheckGuess) getMessage(in);
								
								//send result to player and store return in finalAct
								Message finalAct = thisPlayer.getGuessResult(resultMessage);
								
								// did player Accuse?
								if ( finalAct.getType() == 5) {
									
									//send accusage method to server
									sendMessage(finalAct, out, thisPlayer.getPlayerNum());
									System.out.println("Send accusation to Server.");
									// get result
									Message accuseResult = (Message) getMessage(in);
									{
										boolean gotResult = false;
										while( gotResult == false) {
											if( accuseResult.getType() == 18) {
												gotResult = true;
											}
										}
									}
									System.out.println("Received result from Server");
									// pass result to Player
									thisPlayer.getAccuseResult((MessageCheckSolution) accuseResult);
								}
								break;
							}
							// case 5 means they are making an accusation
							case 5:
							{
								//send accusage method to server
								sendMessage(guessMessage, out, thisPlayer.getPlayerNum());
								// get result
								MessageCheckSolution accuseResult = (MessageCheckSolution) getMessage(in);
								// pass result to Player
								thisPlayer.getAccuseResult(accuseResult);
								break;
							}
							// case 6 means they're passing
							case 6:
							{
								// send it to Server
								Message passMessage = new Message(6, thisPlayer.getPlayerNum());
								sendMessage(passMessage, out, thisPlayer.getPlayerNum());
								break;
							}		
						}
					}
					// if they can't make a guess they can only pass or accuse
					else {
						// turn is over
						turnOver = true;
						// ask player if they want to guess or accuse
						Message finalAct = thisPlayer.makeAccusation();
						// run a switch based on accusation or guess
						switch( finalAct.getType() ) {
							// if its an accusation its type 5
							case 5: 
							{
								//send accusage method to server
								sendMessage(finalAct, out, thisPlayer.getPlayerNum());
								// get result
								
								MessageCheckSolution accuseResult = (MessageCheckSolution) getMessage(in);
								// pass result to Player
								thisPlayer.getAccuseResult(accuseResult);
								break;
							}
							// the other option is a pass
							case 6:
							{
								// send it to Server
								Message passMessage = new Message(6, thisPlayer.getPlayerNum());
								sendMessage(passMessage, out, thisPlayer.getPlayerNum());
								break;
							}
						}
					}
				}
				else {
				// if it wasn't a valid move we do nothing and this loop repeats
				System.out.println("Invalid move requested, starting turn over again");
				}
			}
			// is it a accusation?
			else if( firstAction.getType() == 5) {
				//turn is over
				turnOver = true;
				// send accusation to server
				sendMessage(firstAction, out, thisPlayer.getPlayerNum());
				// get result
				
				MessageCheckSolution accuseResult = (MessageCheckSolution) getMessage(in);
				// pass result to Player
				thisPlayer.getAccuseResult(accuseResult);
			}
			// if its not a move or accusation its a pass
			else {
				// pass means turn is over
				turnOver = true;
				// send it to Server
				Message passMessage = new Message(6, thisPlayer.getPlayerNum());
				sendMessage(passMessage, out, thisPlayer.getPlayerNum());
			}
		}
	}
	
	private static void sendMessage(Message outMessage, ObjectOutputStream out, int thisPlayer) throws IOException{
		System.out.println("Send Message of Type " + outMessage.getType());
		out.writeObject(outMessage);
		out.flush();
	}
	
	private static Message getMessage(ObjectInputStream in) throws IOException, ClassNotFoundException{
		Message inMessage = (Message) in.readObject();
		System.out.println("Received Message of type " + inMessage.getType());
		return inMessage;
	}
}
