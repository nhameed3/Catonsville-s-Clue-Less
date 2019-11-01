import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	
	ArrayList<Card> deckCards = new ArrayList<Card>();
	ArrayList<Card> roomCards = new ArrayList<Card>();
	ArrayList<Card> weaponCards = new ArrayList<Card>();
	ArrayList<Card> suspectCards = new ArrayList<Card>();
	
	public Deck() {
		
		for(Card.Room room : Card.Room.values()) { 
			Card room1 = new Card(null, room, null);
		     roomCards.add(room1);
		}
		
		for(Card.Weapon weapon : Card.Weapon.values()) { 
			Card weapon1 = new Card(null, null, weapon);
		     roomCards.add(weapon1);
		}
		
		for(Card.Suspect suspect : Card.Suspect.values()) { 
			Card suspect1 = new Card(suspect, null, null);
		     roomCards.add(suspect1);
		}
		
		deckCards.addAll(roomCards);
		deckCards.addAll(weaponCards);
		deckCards.addAll(suspectCards);
		
		Collections.shuffle(deckCards);

	}
	
	
	
	
	
}
