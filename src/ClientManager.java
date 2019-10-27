import java.io.*;
import java.net.*;

public class ClientManager implements Runnable {
	ObjectOutputStream out;
	ObjectInputStream in;
	String name;
		
	//public constructor takes name, in objectstream, out objectstream
	public ClientManager(String newName, ObjectOutputStream newOut, ObjectInputStream newIn) {
		this.name = newName;
		this.in = newIn;
		this.out = newOut;
	}
	
	public void run() {
		try
		{
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
}
