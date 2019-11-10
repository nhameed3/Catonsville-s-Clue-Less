import java.util.ArrayList;
import java.util.Arrays;

public class MessageCheckSolution extends Message{

	
	boolean correct = false;
	ArrayList<Card> incorrectCards = new ArrayList<Card>();
	
	public MessageCheckSolution(boolean solution, ArrayList<Card> cards, int type) {
		
		super();
		this.correct = solution;
		this.incorrectCards = cards;
		this.messageType = type;
	}
	
	public boolean getCorrect(){
		return this.correct;	
	}
	
	public String toString() {
		
		String message = "";
		
		String str[] = new String[incorrectCards.size()]; 
		  
		if(correct == true) {
			message = "Congrats!  Your accusation is correct, you win!";
		}else {
			for (int j = 0; j < incorrectCards.size(); j++) { 
				  
	            		str[j] = incorrectCards.get(j).toString(); 
	        }
			message = "Incorrect accusation! Cards wrong: " + Arrays.toString(str);
		}
		
		
		return message;
	}
	
}