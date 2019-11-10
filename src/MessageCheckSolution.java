import java.util.ArrayList;
import java.util.Arrays;

public class MessageCheckSolution extends Message{

	
	boolean correct = false;
	ArrayList<Card> incorrectCards = new ArrayList<Card>();
	
	public MessageCheckSolution(boolean solution, ArrayList<Card> cards) {
		
		super();
		this.correct = solution;
		this.incorrectCards = cards;
	}
	
	public boolean getCorrect(){
		return this.correct;	
	}
	
	public String toString() {
		
		String message = "";
		
		String str[] = new String[incorrectCards.size()]; 
		  
        // ArrayList to Array Conversion 
        for (int j = 0; j < incorrectCards.size(); j++) { 
  
            // Assign each value to String array 
            str[j] = incorrectCards.get(j).toString(); 
        }
		if(correct == true) {
			message = "Congrats!  Your accusation is correct, you win!";
		}else {
			message = "Incorrect accusation! Cards wrong: " + Arrays.deepToString(str);
		}
		
		
		return message;
	}
	
}
