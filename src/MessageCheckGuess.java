import java.util.ArrayList;

public class MessageCheckGuess extends Message{
		
	boolean disproven = false;
	Card incorrectCard;
	
	public MessageCheckGuess(boolean solution, Card card, int type) {
		
		super();
		this.disproven = solution;
		this.incorrectCard = card;
		this.messageType = type;
	}
	
	public boolean getDisproven(){
		return this.disproven;	
	}
	
	public String toString() {
		
		String message = "";
		
		if(disproven == true) {
			message = "Your guess was disproven by this card: " + this.incorrectCard.toString();
		}else {
			message = "Your guess could not be disproven";
		}
		
		
		return message;
	}

}
