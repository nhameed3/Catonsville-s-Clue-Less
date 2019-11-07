import java.io.Serializable;

public class Message implements Serializable {
	// four instance variables: int for messageType, int for whichPlayer, int for generic int that can be used based on contetx
	// String for generic string that can be used based on context
	/* Message Type Key: C2S = Client to Server. S2C = Server to Client.
	1: Start (S2C)
	2: Turn (S2C)
	3: Move (C2S)
	4: Guess (C2S)
	5: Accuse (C2S)
	6: Pass (C2S)
	7: Win (S2C)
	8: Quit (C2S)
	9: Deal (S2C)
	10: Connection Status (S2C and C2S)
	11: status update (print to screen) (S2C)
	
	0: not used but is initialized to so can use it for error checking for now
	*/
	
	
	protected int messageType;
	protected int whichPlayer;
	protected int genericInt;
	protected String genericText;
	
	//make a default constructor
	public Message() {
		this.messageType = 0;
		this.whichPlayer = -1;
	}
	
	// constructor, two int arguments to set type and which player
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
	
	public int getInt() {
		return genericInt;
	}
	
	public String getText() {
		return genericText;
	}
	
	// set methods
	public void setPlayer(int newPlayer) {
		this.whichPlayer = newPlayer;
	}
	
	public void setType(int newType) {
		this.messageType = newType;
	}
	
	public void setInt(int newInt) {
		this.genericInt = newInt;
	}
	
	public void setText(String newText) {
		this.genericText = newText;
	}
}
