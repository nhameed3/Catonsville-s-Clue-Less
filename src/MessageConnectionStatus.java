/* This is a child class from Message to convey how many people are connected to the server
 * 
 */


public class MessageConnectionStatus extends Message{
	
	/* Needs an additional attribute to count how many players are connected */
	protected int playerCount = 0;

	
	// override the constructor to take 1 argument, newPlayercount. messagetype is 10 and 
	// this message is sent from the server so whichPlayer is -1
	public MessageConnectionStatus(int newPlayerCount) {
		this.messageType = 10;
		this.whichPlayer = -1;
		this.playerCount = newPlayerCount;
	}
	
	// get for playerCount
	public int getPlayerCount() {
		return this.playerCount;
	}
	
}
