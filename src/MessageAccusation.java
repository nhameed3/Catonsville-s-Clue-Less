
public class MessageAccusation extends Message{

	public Card sus;
	public Card rm;
	public Card weap;
	//if accusation = true it is an accusation, if false it is a guess
	public boolean accusation;
	
	public MessageAccusation(Card suspect, Card room, Card weapon, boolean accuse) {
		
		super();
		this.sus = suspect;
		this.rm = room;
		this.weap = weapon;
		this.accusation = accuse;
		
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
