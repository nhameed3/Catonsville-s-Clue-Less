import java.util.ArrayList;
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import javafx.application.Application; 
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;  

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
	
	public void displayCards() throws FileNotFoundException {
		String link = "";
		Stage stage = new Stage();
		for(Card card: playerCards) {
			link = card.getImage();
			
			FileInputStream input = new FileInputStream(link);
	        Image image = new Image(input);
	        ImageView imageView = new ImageView(image);
	        HBox hbox = new HBox(imageView);
	        Scene scene = new Scene(hbox, 200, 100);
	        stage.setTitle("ImageView");
	        stage.setWidth(415);
	        stage.setHeight(200);
	        stage.setScene(scene); 
	        stage.sizeToScene(); 
	        stage.show(); 
		}
	}
}
