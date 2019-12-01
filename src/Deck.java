import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;

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
	ArrayList<String> roomImages = new ArrayList<String>(); 
	ArrayList<String> suspectImages = new ArrayList<String>();
	ArrayList<String> weaponImages = new ArrayList<String>();
	
	public Deck() {
		roomImages.add("https://live.staticflickr.com/7095/27061980386_702ab2b6c8_b.jpg");
		roomImages.add("https://i.pinimg.com/originals/9a/ae/c7/9aaec777814eade1cb6bbe336b592631.jpg");
		roomImages.add("https://i.ebayimg.com/images/g/4NcAAOSwOgdYzZ32/s-l300.jpg");
		roomImages.add("https://i.ebayimg.com/images/g/5YYAAOSwuLZYzZ2w/s-l300.jpg");
		roomImages.add("https://live.staticflickr.com/7256/26507487333_581c491ca5_b.jpg");
		roomImages.add("https://live.staticflickr.com/7476/26505749944_d8ce547caa_b.jpg");
		roomImages.add("https://i.pinimg.com/236x/aa/97/b9/aa97b91255516c059461daba0cc630fd--book-wizard-clue-party.jpg");
		roomImages.add("https://i.pinimg.com/originals/6e/18/c3/6e18c36dc968fe6d727be5f1265e16c5.jpg");
		roomImages.add("https://i.pinimg.com/originals/c5/06/9d/c5069d064d8d09eaf2b561ab35d0e0ce.jpg");
		
		suspectImages.add("https://www.stjo.hn/uploads/2012/12/RevGreen.jpg");
		suspectImages.add("https://i.pinimg.com/originals/2e/ca/80/2eca80ad83aa37e405cda647c87b6f05.jpg");
		suspectImages.add("https://editorial01.shutterstock.com/wm-preview-1500/1295771i/0557a62f/cluedo-board-game-2009-shutterstock-editorial-1295771i.jpg");
		suspectImages.add("https://live.staticflickr.com/6215/6320203261_017b21f562_b.jpg");
		suspectImages.add("https://spotlightongames.com/temp/Scarlett/Scarlet86.jpg");
		suspectImages.add("https://i.dailymail.co.uk/i/pix/2016/07/05/01/32FA39B100000578-3674462-image-a-36_1467679578797.jpg");
		
		weaponImages.add("https://i.pinimg.com/originals/63/3a/03/633a03eea148367b86c55be2b674df19.jpg");
		weaponImages.add("https://i.pinimg.com/564x/0e/40/52/0e405250561a27279945232fc36af8bb.jpg");
		weaponImages.add("https://i.pinimg.com/736x/97/d1/8e/97d18e3beaa7c68f86a234ed872dcd2f.jpg");
		weaponImages.add("https://i.ebayimg.com/images/g/KQ8AAOSwTM5YzZEm/s-l300.jpg");
		weaponImages.add("https://i.pinimg.com/564x/75/d7/47/75d747aca78e2661b5c1d19d5527c046.jpg");
		weaponImages.add("https://i.pinimg.com/564x/56/d2/7e/56d27e9948503e6b749d25b14bff77ae.jpg");
		
		for(Card.Room room : Card.Room.values()) { 
			Card room1 = new Card(null, room, null, roomImages.get(0));
		     roomCards.add(room1);
		     roomImages.remove(0);
		}
		
		for(Card.Weapon weapon : Card.Weapon.values()) { 
			Card weapon1 = new Card(null, null, weapon, weaponImages.get(0));
		     weaponCards.add(weapon1);
		     weaponImages.remove(0);
		}
		
		for(Card.Suspect suspect : Card.Suspect.values()) { 
			Card suspect1 = new Card(suspect, null, null, suspectImages.get(0));
		     suspectCards.add(suspect1);
		     suspectImages.remove(0);
		}
		
		deckCards.addAll(roomCards);
		deckCards.addAll(weaponCards);
		deckCards.addAll(suspectCards);
		
		Collections.shuffle(deckCards);

	}
	
	public MessageCheckSolution checkAccusation(MessageAccusation accuse) {
		
		ArrayList<Card> incorrect = new ArrayList<Card>();
		boolean correct = true;
		//System.out.println("comparing " + accuse.getRoom().toString() + " " + solution.get(0).toString());
		if(!accuse.getRoom().toString().equals(solution.get(0).toString())) {
			incorrect.add(accuse.getRoom());
			correct = false;
		}
		//System.out.println("comparing " + accuse.getWeapon().toString() + " " + solution.get(1).toString());
		if(!accuse.getWeapon().toString().equals(solution.get(1).toString())) {
			incorrect.add(accuse.getWeapon());
			correct = false;
		}
		//System.out.println("comparing " + accuse.getSuspect().toString() + " " + solution.get(2).toString());
		if(!accuse.getSuspect().toString().equals(solution.get(2).toString())) {
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
		String str[] = new String[solution.size()]; 
        
        for (int j = 0; j < solution.size(); j++) {  
            str[j] = solution.get(j).toString(); 
        } 
        System.out.println("Solution cards are: " + Arrays.toString(str));
	}
	
	public ArrayList<MessageDeal> dealCards(int playerCount) {
		
		ArrayList<MessageDeal> dealMessages = new ArrayList<MessageDeal>();
		int numCards = (deckCards.size()/playerCount)-1;
		System.out.println("num cards is " + numCards);
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
