import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
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
		
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	String link = "";
		    	Group group = new Group();
		    	HBox box = new HBox();
		    	box.setFillHeight(true);
				for(Card card: playerCards) {
					link = card.getImage();
					//group.setAutoSizeChildren(true);
					Image image = new Image(link);
					if(image.isError()) {
						System.out.println("there was an error in " + link);
					}
					ImageView imageView = new ImageView(image);
					imageView.setX(100); 
					imageView.setY(250);
					imageView.setFitHeight(250); 
					imageView.setFitWidth(150); 
					group.getChildren().add(imageView);
					box.getChildren().add(imageView);
					//imageView.setPreserveRatio(true); 
					//group.setAutoSizeChildren(true); 
				}
				Scene scene = new Scene(box, 1400, 250); 
				Stage stage = new Stage();
				stage.setWidth(1400);
		        stage.setHeight(250);
		        stage.setScene(scene); 
		        stage.sizeToScene(); 
	            stage.setScene(scene); 
	            stage.show();
		    }
		});
	}
}
