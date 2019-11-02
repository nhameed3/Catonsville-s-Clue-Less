/* ClientManager is for managing each connecting client in 1:1 fashion. Server creates this and calls run() 
on each new connection. Each instance of ClientManager has its own input and output streams as well as String
name.
*/

import java.io.*;
import java.net.*;

public class ClientManager implements Runnable {
	// store input and output object treams and a String name
	ObjectOutputStream out;
	ObjectInputStream in;
	String name;
		
	//public constructor takes name, in objectstream, out objectstream
	public ClientManager(String newName, ObjectOutputStream newOut, ObjectInputStream newIn) {
		this.name = newName;
		this.in = newIn;
		this.out = newOut;
	}
	
	// override run() from Runnable
	public void run() {
		try
		{
			/*
			For however long as appropriate (disconnection?) we listen for messages.
			*/
			while( true ) {
				Message inMessage = (Message) in.readObject();
				//troubleshooting message
				System.out.println(name + " received message of type " + inMessage.getType());
			}
			
		}
		/* Handle Exceptions, for now just print stackTrace 
		 * 
		 */
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
			
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
}
