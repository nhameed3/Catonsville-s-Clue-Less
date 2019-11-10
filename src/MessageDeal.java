import java.util.ArrayList;

/* MessageDeal is for dealing cards from Deck, through server, through client, to player
 * 
 */
//array list of hands
public class MessageDeal extends Message{

	ArrayList<Card> playerCards = new ArrayList<Card>();
	public MessageDeal(ArrayList<Card> cards) {
		
		super();
		this.playerCards = cards;
		
	}
	
	public ArrayList<Card> getCards(){
		return this.playerCards;
	}
	
	public int getSize() {
		return this.playerCards.size();
	}
}
