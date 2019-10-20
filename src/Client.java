import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] args) {

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
				ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream() )
						
				)
		/* for now this next block runs the client side for skeletal increment */ 
		
		{
			
			// I'm creating an player array to make switch easier. This might not be best place for it in target.
			Player [] playerArray = new Player[2];
			// once we've connected we send the connection message
			Message outMessage = new Message(9,0);
			//print 
			System.out.println("Welcome to Clue-less by Catonsvillians!");
			System.out.println("Client connected to server.");
			//send it
			out.writeObject(outMessage);
			
			// read the incoming message from server
			Message inMessage = (Message) in.readObject();
			// inMessage should be type 1
			
			if ( inMessage.getType() == 1) {
				System.out.println("Client received start game message from server.");
				System.out.println("Client initializes two players and asks player 1 for first action.");
				playerArray[0] = new Player("Player1", 0, true);
				playerArray[1] = new Player("Player2", 1, false);
				
				
				
				// ask player1 for first move
				outMessage = playerArray[0].playerTurn();
				// send the players turn to the server
				System.out.println("Client sends current move to server.");
				out.writeObject(outMessage);
			}
			// if inMessage is anything other than type 1 we have an error. This shouldn't happen
			else {
				System.err.println("Client failed to receive start message from server, client exiting.");
				System.exit(1);
			}
			
			// grab next message
			inMessage = (Message) in.readObject();
			
			// start a while loop that goes until the outMessage is set to quit
			while ( outMessage.getType() != 8) {
				// create a switch that handles incoming messages
				switch( inMessage.getType())
				{
					// case 3 is players turn
					case 3:
					{
						// Print we received turn from server.
						System.out.println("Client received turn message from server.");
						System.out.println("Client asks player " + playerArray[ inMessage.getPlayer() - 1 ].getUserName() + " for their next turn.");
						// get the players turn
						outMessage = playerArray[ (inMessage.getPlayer() - 1 ) ].playerTurn();
						// print
						System.out.println("Client sends turn from " + playerArray[ inMessage.getPlayer() - 1 ].getUserName() + " to server.");
						out.writeObject(outMessage);
						// grab next inMessage. For now we aren't grabbing inMessage for case 7 so I'm putting here instead of in while loop
						inMessage = (Message) in.readObject();
						break;
					}
					// case 7 is win message
					case 7:
					{
						// Print we received win from server
						System.out.println("Client received win message from server.");
						System.out.println( playerArray[ inMessage.getPlayer() - 1].getUserName() + " wins!");
						System.out.println("Client informs server it is quitting.");
						// generate quit message
						outMessage = new Message(8,0);
						// send quit message
						out.writeObject(outMessage);
						break;
					}
				}
			}
			
			// print goodbye message
			System.out.println("Game is over. Client closing.");
			
		}

	}

}
