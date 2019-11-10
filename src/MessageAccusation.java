
public class MessageAccusation extends Message{

	public Card sus;
	public Card rm;
	public Card weap;
	public int type;
	
	public MessageAccusation(Card suspect, Card room, Card weapon, int type) {
		
		super();
		this.sus = suspect;
		this.rm = room;
		this.weap = weapon;
		//if type = 0, it is a guess, if 1, an accusation
		this.messageType = type;
		
	}
	
	public Card getSuspect() {
		return this.sus;
	}
	
	public Card getRoom() {
		return this.rm;
	}
	
	public Card getWeapon() {
		return this.weap;
	}

	
	
}
