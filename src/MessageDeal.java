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
				for(Card card: playerCards) {
					link = card.getImage();
					//System.out.println(link);
					try {
						URL file = new File(link).toURI().toURL(); 
						Stage stage = new Stage();
						//Image image = new Image(file.toURI().toURL().toString());
						Image image = new Image(link);
						if(image.isError()) {
							System.out.println("there was an error in " + link);
						}
				        ImageView imageView = new ImageView(image);
				        //Setting the position of the image 
				        imageView.setX(50); 
				        imageView.setY(25); 
				        
				        //setting the fit height and width of the image view 
				        imageView.setFitHeight(455); 
				        imageView.setFitWidth(500); 
				        
				        //Setting the preserve ratio of the image view 
				        imageView.setPreserveRatio(true); 
				        Group root = new Group(imageView);  
				        Scene scene = new Scene(root, 500, 500);
				        stage.setTitle(card.toString());
				        stage.setWidth(500);
				        stage.setHeight(500);
				        stage.setScene(scene); 
				        stage.sizeToScene(); 
				        stage.show(); 
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			        
				}
		    }
		});
	}
}
