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
		
		//create thread to send messages
		Thread sendMessage = new Thread(new Runnable()
		{
			public void run() {
				while(true) {
					try {
						//read next int from scanner
						int messageType = inScn.nextInt();
						//troubleshooting
						System.out.println("Received command to send message of type " + messageType);
							
						// create a message to go out
						Message outMessage = new Message(messageType, 0);
						// write it to out stream
						out.writeObject(outMessage);
						// troubleshooting
						System.out.println("Sent new message of type " + outMessage.getType());
					}
					// just catch all Exceptions for now
					catch(Exception e) {
						e.printStackTrace();
					}
						
					}
				}
		});
		
		//create thread to receive messages
		Thread receiveMessage = new Thread(new Runnable() 
		{
			public void run() {
				while(true) {
					try {
					//read incoming message
					Message inMessage = (Message) in.readObject();
					//just print we got a message for now
					System.out.println("Received message from " + inMessage.getType());
					}
					// catch all Exceptions for now
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		});
		
		//start the send and receive threads
		sendMessage.start();
		receiveMessage.start();
	}
}
