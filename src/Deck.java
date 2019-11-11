import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {

	
	ArrayList<Card> deckCards = new ArrayList<Card>();
	ArrayList<Card> roomCards = new ArrayList<Card>();
	ArrayList<Card> weaponCards = new ArrayList<Card>();
	ArrayList<Card> suspectCards = new ArrayList<Card>();
	ArrayList<Card> solution = new ArrayList<Card>();
	
	Random rand = new Random(); 
	Card roomCard;
	Card weaponCard;
	Card suspectCard;
	
	public Deck() {
		
		for(Card.Room room : Card.Room.values()) { 
			Card room1 = new Card(null, room, null);
		     roomCards.add(room1);
		}
		
		for(Card.Weapon weapon : Card.Weapon.values()) { 
			Card weapon1 = new Card(null, null, weapon);
		     weaponCards.add(weapon1);
		}
		
		for(Card.Suspect suspect : Card.Suspect.values()) { 
			Card suspect1 = new Card(suspect, null, null);
		     suspectCards.add(suspect1);
		}
		
		deckCards.addAll(roomCards);
		deckCards.addAll(weaponCards);
		deckCards.addAll(suspectCards);
		
		Collections.shuffle(deckCards);

	}
	
	public MessageCheckSolution checkAccusation(MessageAccusation accuse) {
		
		ArrayList<Card> incorrect = new ArrayList<Card>();
		boolean correct = true;
		if(!accuse.getRoom().equals(solution.get(0))) {
			incorrect.add(accuse.getRoom());
			correct = false;
		}
		
		if(!accuse.getWeapon().equals(solution.get(1))) {
			incorrect.add(accuse.getWeapon());
			correct = false;
		}
		
		if(!accuse.getSuspect().equals(solution.get(2))) {
			incorrect.add(accuse.getSuspect());
			correct = false;
		}
			
		MessageCheckSolution userReturn = new MessageCheckSolution(correct, incorrect, 18);
		
		return userReturn;	
		
	}
	
	public MessageCheckGuess checkGuess(MessageAccusation accuse) {
		
		Card incorrectCard = new Card();
		boolean correct = true;
		if(!(accuse.getRoom()).equals(solution.get(0))) {
			incorrectCard = accuse.getRoom();
			correct = false;
		}else if(!(accuse.getWeapon()).equals(solution.get(1))) {
			incorrectCard =accuse.getWeapon();
			correct = false;
		}else if(!(accuse.getSuspect()).equals(solution.get(2))) {
			incorrectCard = accuse.getSuspect();
			correct = false;
		}
			
		MessageCheckGuess userReturn = new MessageCheckGuess(correct, incorrectCard, 19);
		
		return userReturn;
		
	}
	
	public void createSolution(){
		
		roomCard  = roomCards.get(rand.nextInt(roomCards.size()));
		weaponCard = weaponCards.get(rand.nextInt(weaponCards.size()));
		suspectCard = suspectCards.get(rand.nextInt(suspectCards.size()));
		solution.add(roomCard);
		solution.add(weaponCard);
		solution.add(suspectCard);
		deckCards.remove(roomCard);
		deckCards.remove(weaponCard);
		deckCards.remove(suspectCard);
	}
	
	public ArrayList<MessageDeal> dealCards(int playerCount) {
		
		ArrayList<MessageDeal> dealMessages = new ArrayList<MessageDeal>();
		int numCards = deckCards.size()/playerCount;
		for(int i = 1; i<= playerCount; i++) {
			ArrayList<Card> playerCards = new ArrayList<Card>();
			for(int cards = 0; cards<=numCards; cards++) {
				if(!deckCards.isEmpty()) {
					playerCards.add(deckCards.get(0));
					deckCards.remove(0);
				}else {
					break;
				}
			}
			MessageDeal playerHand = new MessageDeal(playerCards);
			dealMessages.add(playerHand);
			
		}		
		return dealMessages;
		
	}
	
	
}
