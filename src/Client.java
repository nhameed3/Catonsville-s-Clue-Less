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
		
		// what player does user want to be
		System.out.println("Which character do you want to be?");
		for( int i = 0; i < 6; i++) {
			// grab the avatar array
			boolean [] tempAvatars = inMessage.getAvatars();
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
					
			//grab the players choice
			int playerChoice = inScn.nextInt();
					
			//set that element to true
			tempAvatars[playerChoice] = true;
			
			// set whichPlayer to playerChoice
			outMessage.setPlayer(playerChoice);
			// write the new array to outMessage
			outMessage.setAvatars(tempAvatars);
		}
		
		return outMessage;
		
	}
	
	/* 
	 * 
	 */
	private static boolean playGame(Player thisPlayer, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException{
		// receive a message from the server
		Message inMessage = (Message) in.readObject();
		
		// store a boolean flag that we return
		boolean gameOver = false;
		
		// run a switch where we handle the Message as appropriate
		switch( inMessage.getType()) {
		// type 2 means its this players turn
		case 2:
		{
			// cue players turn and store result as outgoingMessage
			Message outgoingMessage = thisPlayer.playerTurn();
			
		}
		}
		
	}
}
