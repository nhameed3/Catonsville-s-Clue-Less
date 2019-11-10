/* MessageConnectionStatus is passed between server and client at beginning of game to detail
 * who each player is, their username, etc.
 * 
 */


public class MessageConnectionStatus extends Message{
	
	// we store which avatars are available in a boolean array length 6
	private boolean [] avatars = new boolean[6];
	
	// default constructor
	MessageConnectionStatus() {
		super();
		this.messageType = 10;
	}
	
	//constructor
	MessageConnectionStatus(int messageType, int whichPlayer, boolean[] avatars) {
		this.messageType = messageType;
		this.whichPlayer = whichPlayer;
		this.avatars = avatars;
	}
	
	
	
	// method to set the avatars
	public void setAvatars(boolean[] newAvatars) {
		this.avatars = newAvatars;
	}
	
	// method to return the avatars
	
	public boolean[] getAvatars() {
		return this.avatars;
	}
	
	
}
