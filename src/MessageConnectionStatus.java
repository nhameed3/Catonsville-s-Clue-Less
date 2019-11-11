/* MessageConnectionStatus is passed between server and client at beginning of game to detail
 * who each player is, their username, etc.
 * 
 */

import java.util.*;

public class MessageConnectionStatus extends Message{
	
	// we store which avatars are available in an arrayList of integers
	
	private boolean[] avatars;
	
	// default constructor
	MessageConnectionStatus() {
		super();
		this.messageType = 10;
		this.avatars = new boolean[6];
	}
	
	//constructor
	MessageConnectionStatus(int messageType, int whichPlayer, boolean[] avatars) {
		this.messageType = messageType;
		this.whichPlayer = whichPlayer;
		this.avatars = avatars;
	}
	
	
	
	// method to set the avatars
	public void setAvatars(boolean [] newAvatars) {
		this.avatars = newAvatars;
	}
	
	// method to return the avatars
	
	public boolean[] getAvatars() {
		return this.avatars;
	}
}
