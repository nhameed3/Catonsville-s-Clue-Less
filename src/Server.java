import java.io.*;
import java.net.*;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException{
		//confirm arguments
		if( args.length != 1) {
			System.err.println("Invalid arugments. java GameSever <port>");
			System.exit(1);
		}
				
		// set the port
		int port = Integer.parseInt(args[0]);
				
		// try with resources
		try (
			//establish server socket
			ServerSocket server = new ServerSocket( port );
			// accept incoming client socket requests
			Socket game = server.accept();
						
			// grab output and input streams
			ObjectOutputStream out = new ObjectOutputStream(game.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(game.getInputStream());		
			)
		
		/* For Skeletal Increment this next block is what runs the server side */
		{
			// try an int for turn tracking
			int turnTracker = 0;
			
			// grab the first message coming in from client
			Message inMessage = (Message) in.readObject();
			
			// we run the while loop until we get a quit message which is 8
			while( inMessage.getType() != 8 ) {
				System.out.println(inMessage.getPlayer());
				
				// use a switch construct to respond appropriately to messages from client
				switch( inMessage.getType() ) {
					
					// type 9 is connect so we send back start which is 1
					case 9:
					{
						//create a message of type 1
						Message outMessage = new Message(1, 1);
						// print to out
						System.out.println("Server received connection from client. Server starts game.");
						System.out.println("Server initializes deck to create solution and deal cards.");
						System.out.println("Server initializes board to initialize and setup game board.");
						System.out.println("Server tells client to initialize players and start player 1 turn.");
						turnTracker++;
						// send out message
						out.writeObject(outMessage);
						break;
					}
					
					// type 3 is player move
					case 3:
					{
						// print 
						System.out.println("Server received a move request for player " + (inMessage.getPlayer()+1));
						System.out.println("Server checks with board to validate and update.");
						System.out.println("Server tells client start next players turn.");
						
						// create a message for next turn
						Message outMessage = nextTurn(inMessage, turnTracker);
						turnTracker++;
		
						// send the message
						out.writeObject(outMessage);
						break;
					}
					
					// type 4 is player guess
					case 4:
					{
						// print 
						System.out.println("Server received a guess request for player " + (inMessage.getPlayer()+1) );
						System.out.println("Server calls guess method to run guess algorithm");
						System.out.println("Server tells client results of guess and to start next players turn");
						
						// create a message for next turn
						Message outMessage = nextTurn(inMessage, turnTracker);
						turnTracker++;
		
						// send the message
						out.writeObject(outMessage);
						break;

					}
					
					// type 5 is player accuse
					case 5:
					{
						// print 
						System.out.println("Server received an accusation request from player " + (inMessage.getPlayer()+1));
						System.out.println("Server calls solution to determine result of accusation.");
						System.out.println("Server informs client that player" + inMessage.getPlayer() + " wins.");
						
						// create outMessage of win
						Message outMessage = new Message(7, inMessage.getPlayer());
						// send win message
						out.writeObject(outMessage);
						break;
					}
					// type 6 is player passes
					case 6:
					{
						// print
						System.out.println("Server received a pass request from player " + (inMessage.getPlayer()+1));
						System.out.println("Server informs client to start next players turn.");
						
						// create a message for next turn
						Message outMessage = nextTurn(inMessage, turnTracker);
						//send it
						out.writeObject(outMessage);
						turnTracker++;
						break;
					}
				}
				
				// once we get through handling the old message we receive the new one
				inMessage = (Message) in.readObject();
			}
			
			//if we've exited the loop we got a quit request
			System.out.println("Server received quit request.");
			System.out.println("Server shuts down.");
			System.exit(0);
		}
	}
	
	/* Write a method to process the next turn decision. For now its just check who was last and go to the other
	 * but needs to be updated for target 
	 * */
	private static Message nextTurn(Message oldMessage, int turnTracker) {
		Message returnMessage = new Message(2,( turnTracker % 2));
		// if previous was 1, then next is 2
		return returnMessage;
	}

}
