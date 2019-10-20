import java.io.Serializable;

public class Message implements Serializable {
	// two instance variables: int for messageType, int for whichPlayer
	/* Message Type Key: C2S = Client to Server. S2C = Server to Client.
	1: Start (S2C)
	2: Turn (S2C)
	3: Move (C2S)
	4: Guess (C2S)
	5: Accuse (C2S)
	6: Pass (C2S)
	7: Win (S2C)
	8: Quit (C2S)
	9: Connect (C2S)	
	*/
	private int messageType;
	private int whichPlayer;
	
	//make a default constructor
	public Message() {
		this.messageType = 0;
		this.whichPlayer = 0;
	}
	
	// constructor, three arguments to fill in three Strings
	public Message(int givenType, int givenPlayer) {
		this.messageType = givenType;
		this.whichPlayer = givenPlayer;
		
	}
	// get methods to return attributes
	public int getType() {
		return messageType;
	}
	
	public int getPlayer() {
		return whichPlayer;
	}
	
	// set methods
	public void setPlayer(int newPlayer) {
		this.whichPlayer = newPlayer;
	}
	
	public void setType(int newType) {
		this.messageType = newType;
	}
}
