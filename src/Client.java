import java.io.*;
import java.util.*;
import java.net.*;

/* Client is the driver of the game on client computers. It takes two arguments (IP and port) and connects to the
server. It launches threads for receiving objects and sending objects to and from and the server. For now this
is also where all Player code goes?
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
		
		//grab input and output streams
		ObjectOutputStream out = new ObjectOutputStream( gameSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream());
		
		// have a boolean flag for startGame, defaults to false
		boolean startGame = false;
		
		// track what player we are. I know this isn't how Sam wants to do it so can be overwritten
		int playerNumber;
		
		// we start with a while loop waiting for the game to start
		while ( startGame == false) {
			Message inMessage = (Message) in.readObject();
			// if the inMessage is type 10 it tells us what player we are
			if (inMessage.getType() == 10) {
				// store what player we are
				playerNumber = inMessage.getInt();
				// print what player we are
				System.out.println("You are Player " + playerNumber);
				//check if we are player 1
				if( inMessage.getInt() == 1) {
					//if we are player 1 we need to tell the server how many players
					System.out.println("How many players do you want to play with?");
					int maxPlayers = inScn.nextInt();
					// sent that back to the server
					Message desiredPlayers = new Message(9, 1);
					desiredPlayers.setInt(maxPlayers);
					out.writeObject(desiredPlayers);
				}
			}
			// the other message we want to look out for is the start message
			else if (inMessage.getType() == 1) {
				// print how many players
				System.out.println("Game is starting! There are " + inMessage.getInt() + " players in this game.");
				// set the start flag
				startGame = true;
			}
		}
	}
}
