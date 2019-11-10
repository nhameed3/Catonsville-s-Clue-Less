import java.util.ArrayList;

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
	
	
}
