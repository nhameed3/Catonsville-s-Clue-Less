import java.io.*;
import java.util.*;
import java.net.*;

/* Client is the driver of the game on client computers. It takes two arguments (IP and port) and connects to the
server.
*/
public class Client {

	public static void main(String[] args) throws IOException, ClassNotFoundException{

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
		MessageConnectionStatus incomingMessage = (MessageConnectionStatus) in.readObject();
		
		// call startUp and store it as outgoingMessage
		MessageConnectionStatus outgoingMessage = startUp(incomingMessage, inScn);
		
		// send output message
		out.writeObject(outgoingMessage);
		
		// create Player with info from outgoingMessage
		//Player currentPlayer = new Player(outgoingMessage.getText(), outgoingMessage.getPlayer());
		
		// run a While loop until agmeOver = true;
		/*
		while( gameOver == false) {
			gameOver = playGame(currentPlayer, in, out);
		}
		*/
	
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
		// grab the avatar array
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
		Message inMessage = (Message) in.readObject();
		
		
		// run a switch where we handle the Message as appropriate
		switch( inMessage.getType()) {
			// type 2 means its this players turn
			case 2:
			{
				/* this case should probably be expanded. Like a while loop that sends and accepts
				 * messages back and forth until the player's turn is over because
				 * players can do numerous things and the way it's written now it's like the turn starts
				 * over every action. Plus sometimes the player will be waiting to get a result back
				 * from their action like valid move or result of guess or accuse.
				 * 
				 * Or the results of move, guess, and accuse can be seperate messages
				 */
				
				// cue players turn and store result as outgoingMessage
				Message outgoingMessage = thisPlayer.playerTurn();
				out.writeObject(outgoingMessage);
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
				//thisPlayer.setHand(inMessage);
				break;
			}
			// type 10 means this is board status update
			case 10:
			{
				// do something with board status update?
				break;
			}
			// case 13 means someone else guessed and we have to disprove it
			case 13:
			{
				// case it to MessageGuAc
				inMessage = (MessageGuAc) inMessage;
				//Message outMessage = thisPlayer.disproveGuess(inMessage);
				//out.writeObject(outMessage);
				break;
			}
			// case 14 means game is over
			case 14:
			{
				gameOver = true;
				break;
			}
		}
		
		return gameOver;
		
	}
}
