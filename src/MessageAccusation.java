
public class MessageAccusation extends Message{

	public Card sus;
	public Card rm;
	public Card weap;
	
	public MessageAccusation(Card suspect, Card room, Card weapon) {
		
		super();
		this.sus = suspect;
		this.rm = room;
		this.weap = weapon;
		
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
