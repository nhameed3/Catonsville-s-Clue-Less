import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.image.*;

// need to import Latch - Matt
import java.util.concurrent.CountDownLatch;


public class GUI extends Application
{
    public static void main(String[] args)
    {
        launch( args );
    }
    
    ////////////////////// Matt Added this chunk to enable getting reference to running GUI from Client
    /* Using this method:
     * https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
     */
    
    //create a CountDownLatch
    public static final CountDownLatch latch = new CountDownLatch(1);
    
    //declare a local GUI instance and set it to null
    public static GUI thisGUI = null;
    
    // create a public wait method for grab reference to thisGUI
    public static GUI waitForGUI() {
    	try {
    		latch.await();
    	} catch (InterruptedException e) {
    		e.printStackTrace();
    	}
    	return thisGUI;
    }
    
    // public setter which counts down our latch
    public static void setGUI( GUI newGUI) {
    	thisGUI = newGUI;
    	latch.countDown();
    }
    
    // public constructor which invokes the public setter
    public GUI() {
    	setGUI(this);
    }
    
    
    
    
    
    ////////////////////////////////////////////

    Stage stage;
    BorderPane paneMain;
    
    //TextArea for showing status messages and Log - MDS
    TextArea updateArea = new TextArea();
    TextArea playerLog = new TextArea("This is your log. You can enter notes here.");
    

    Board gameBoard = new Board();

    final int roomSize = 150;
    final int avatarSize = roomSize/6;

    // Avatar

    ImageView[] avatarImageArray = new ImageView[6];

    // Location

    Pane[] location = new Pane[21];
    
    int[] playerOriginalPosition = new int[6];

    public void start( Stage primaryStage )
    {
        stage = primaryStage;

        // Pane Board

        location[0] = new GridPane();          // Study
        location[1] = new GridPane();          // Hall
        location[2] = new GridPane();          // Lounge
        location[3] = new GridPane();          // Library
        location[4] = new GridPane();          // Billiard Room
        location[5] = new GridPane();          // Dining Room
        location[6] = new GridPane();          // Conservatory
        location[7] = new GridPane();          // Ballroom
        location[8] = new GridPane();          // Kitchen

        location[9] = new BorderPane();
        location[10] = new BorderPane();
        location[11] = new BorderPane();
        location[12] = new BorderPane();
        location[13] = new BorderPane();
        location[14] = new BorderPane();
        location[15] = new BorderPane();
        location[16] = new BorderPane();
        location[17] = new BorderPane();
        location[18] = new BorderPane();
        location[19] = new BorderPane();
        location[20] = new BorderPane();

        for (int i = 0; i < 21; i++)
        {
            location[i].setPrefHeight(roomSize);
            location[i].setPrefWidth(roomSize);
        }

        Image image00 = new Image("https://i.imgur.com/fuTDG1z.jpg", roomSize, roomSize, true, false);
        location[0].setBackground(new Background(new BackgroundImage(image00, null, null, null, null)));
        Image image01 = new Image("https://i.imgur.com/Ieeoq9V.jpg", roomSize, roomSize, true, false);
        location[1].setBackground(new Background(new BackgroundImage(image01, null, null, null, null)));
        Image image02 = new Image("https://i.imgur.com/ziL1CqI.jpg", roomSize, roomSize, true, false);
        location[2].setBackground(new Background(new BackgroundImage(image02, null, null, null, null)));
        Image image03 = new Image("https://i.imgur.com/hexYUlv.jpg", roomSize, roomSize, true, false);
        location[3].setBackground(new Background(new BackgroundImage(image03, null, null, null, null)));
        Image image04 = new Image("https://i.imgur.com/qO7Ahge.jpg", roomSize, roomSize, true, false);
        location[4].setBackground(new Background(new BackgroundImage(image04, null, null, null, null)));
        Image image05 = new Image("https://i.imgur.com/RBciXMD.jpg", roomSize, roomSize, true, false);
        location[5].setBackground(new Background(new BackgroundImage(image05, null, null, null, null)));
        Image image06 = new Image("https://i.imgur.com/nSWz0dk.jpg", roomSize, roomSize, true, false);
        location[6].setBackground(new Background(new BackgroundImage(image06, null, null, null, null)));
        Image image07 = new Image("https://i.imgur.com/AplIwKk.jpg", roomSize, roomSize, true, false);
        location[7].setBackground(new Background(new BackgroundImage(image07, null, null, null, null)));
        Image image08 = new Image("https://i.imgur.com/lkTvzzX.jpg", roomSize, roomSize, true, false);
        location[8].setBackground(new Background(new BackgroundImage(image08, null, null, null, null)));

        Image imageHHallway = new Image("https://i.imgur.com/HdCXcmd.jpg", roomSize, roomSize, true, false);
        Image imageVHallway = new Image("https://i.imgur.com/yrj6tAk.jpg", roomSize, roomSize, true, false);
        location[9].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));
        location[10].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));
        location[11].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[12].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[13].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[14].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));
        location[15].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));
        location[16].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[17].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[18].setBackground(new Background(new BackgroundImage(imageVHallway, null, null, null, null)));
        location[19].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));
        location[20].setBackground(new Background(new BackgroundImage(imageHHallway, null, null, null, null)));

        GridPane paneBoard = new GridPane();
        paneBoard.setPadding(new Insets(20));

        paneBoard.add(location[0], 0, 0);
        paneBoard.add(location[1], 2, 0);
        paneBoard.add(location[2], 4, 0);
        paneBoard.add(location[3], 0, 2);
        paneBoard.add(location[4], 2, 2);
        paneBoard.add(location[5], 4, 2);
        paneBoard.add(location[6], 0, 4);
        paneBoard.add(location[7], 2, 4);
        paneBoard.add(location[8], 4, 4);

        paneBoard.add(location[9], 1, 0);
        paneBoard.add(location[10], 3, 0);
        paneBoard.add(location[11], 0, 1);
        paneBoard.add(location[12], 2, 1);
        paneBoard.add(location[13], 4, 1);
        paneBoard.add(location[14], 1, 2);
        paneBoard.add(location[15], 3, 2);
        paneBoard.add(location[16], 0, 3);
        paneBoard.add(location[17], 2, 3);
        paneBoard.add(location[18], 4, 3);
        paneBoard.add(location[19], 1, 4);
        paneBoard.add(location[20], 3, 4);

        // Pane Main

        paneMain = new BorderPane();
        paneMain.setCenter(paneBoard);
        // add updateArea to paneMain - MDS
        updateArea.setEditable(false);
        paneMain.setBottom(updateArea);
        paneMain.setRight(playerLog);

        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clue-Less");
        primaryStage.show();

        // Initialize avatar

        avatarImageArray[0] = new ImageView(new Image("https://i.imgur.com/cCuxOfZ.jpg", avatarSize, avatarSize, true, false));
        avatarImageArray[1] = new ImageView(new Image("https://i.imgur.com/YPa4XB8.jpg", avatarSize, avatarSize, true, false));
        avatarImageArray[2] = new ImageView(new Image("https://i.imgur.com/vvOKEGg.jpg", avatarSize, avatarSize, true, false));
        avatarImageArray[3] = new ImageView(new Image("https://i.imgur.com/Bba4s3y.jpg", avatarSize, avatarSize, true, false));
        avatarImageArray[4] = new ImageView(new Image("https://i.imgur.com/ef2o1ZQ.jpg", avatarSize, avatarSize, true, false));
        avatarImageArray[5] = new ImageView(new Image("https://i.imgur.com/vj3IVGA.jpg", avatarSize, avatarSize, true, false));

        // Initialize avatar position

        playerOriginalPosition[0] = 10;
        playerOriginalPosition[1] = 19;
        playerOriginalPosition[2] = 13;
        playerOriginalPosition[3] = 11;
        playerOriginalPosition[4] = 20;
        playerOriginalPosition[5] = 16;

        setInitialAvatarPosition();
    }

    public void setInitialAvatarPosition()
    {
        for (int playerNum = 0; playerNum < 6; playerNum++)
        {
            int playerPosition = playerOriginalPosition[playerNum];

            ((BorderPane) location[playerPosition]).setCenter(avatarImageArray[playerNum]);
        }
    }

    public void setAvatarPosition(MessageGUIUpdate update)
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                for (int playerNum = 0; playerNum < 6; playerNum++)
                {
                    int playerOldPosition = playerOriginalPosition[playerNum];
                    int playerNewPosition = update.getPlayerNewPosition()[playerNum];

                    if (playerOldPosition != playerNewPosition)
                    {
                        if ((playerNewPosition < 9) & (playerOldPosition < 9))
                        {
                            int gridPosition = location[playerNewPosition].getChildren().size() + 1;
                            ((GridPane) location[playerNewPosition]).add((avatarImageArray[playerNum]), gridPosition, 0);
                            ((GridPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[playerNum]);
                        }

                        if ((playerNewPosition < 9) & (playerOldPosition >= 9))
                        {
                            int gridPosition = location[playerNewPosition].getChildren().size() + 1;
                            ((GridPane) location[playerNewPosition]).add((avatarImageArray[playerNum]), gridPosition, 0);
                            ((BorderPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[playerNum]);
                        }

                        if ((playerNewPosition >= 9) & (playerOldPosition < 9))
                        {
                            ((BorderPane) location[playerNewPosition]).setCenter(avatarImageArray[playerNum]);
                            ((GridPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[playerNum]);
                        }

                        playerOriginalPosition[playerNum] = playerNewPosition;
                    }
                }
            }
        });
    }
    
    //public method for adding new text to update area - MDs
    public void printStatus(String newStatus) {
    	//grab what was already in there
    	String oldText = updateArea.getText();
    	//concat old with new
    	updateArea.setText(oldText + "\n" + newStatus);
    }
}
