import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.text.*;
import javafx.scene.image.*;

public class GUI extends Application
{
    public static void main(String[] args)
    {
        launch( args );
    }

    int m_playerNum = 0;

    Stage stage;
    BorderPane paneMain;

    Board gameBoard = new Board();

    final int roomSize = 150;
    final int avatarSize = roomSize/6;

    // Avatar

    ImageView[] avatarImageArray = new ImageView[6];

    // Location

    Pane[] location = new Pane[21];

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

        Image image00 = new Image("/Location/STUDY.JPEG", roomSize, roomSize, true, false);
        location[0].setBackground(new Background(new BackgroundImage(image00, null, null, null, null)));
        Image image01 = new Image("/Location/HALL.JPEG", roomSize, roomSize, true, false);
        location[1].setBackground(new Background(new BackgroundImage(image01, null, null, null, null)));
        Image image02 = new Image("/Location/LOUNGE.JPEG", roomSize, roomSize, true, false);
        location[2].setBackground(new Background(new BackgroundImage(image02, null, null, null, null)));
        Image image03 = new Image("/Location/LIBRARY.JPEG", roomSize, roomSize, true, false);
        location[3].setBackground(new Background(new BackgroundImage(image03, null, null, null, null)));
        Image image04 = new Image("/Location/BILLIARD_ROOM.JPEG", roomSize, roomSize, true, false);
        location[4].setBackground(new Background(new BackgroundImage(image04, null, null, null, null)));
        Image image05 = new Image("/Location/DINING_ROOM.JPEG", roomSize, roomSize, true, false);
        location[5].setBackground(new Background(new BackgroundImage(image05, null, null, null, null)));
        Image image06 = new Image("/Location/CONSERVATORY.JPEG", roomSize, roomSize, true, false);
        location[6].setBackground(new Background(new BackgroundImage(image06, null, null, null, null)));
        Image image07 = new Image("/Location/BALLROOM.JPEG", roomSize, roomSize, true, false);
        location[7].setBackground(new Background(new BackgroundImage(image07, null, null, null, null)));
        Image image08 = new Image("/Location/KITCHEN.JPEG", roomSize, roomSize, true, false);
        location[8].setBackground(new Background(new BackgroundImage(image08, null, null, null, null)));

        Image imageHHallway = new Image("/Location/HALLWAY_HORIZONTAL.jpeg", roomSize, roomSize, true, false);
        Image imageVHallway = new Image("/Location/HALLWAY_VERTICAL.jpeg", roomSize, roomSize, true, false);
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

        // Pane Menu start

        Button btnStartMenu = new Button();
        btnStartMenu.setOnAction(e -> btnBackMenu_Click(primaryStage));
        btnStartMenu.fire();

        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Clue-Less");
        primaryStage.show();

        // Initialize avatar

        avatarImageArray[0] = new ImageView(new Image("/Suspect/MISS_SCARLETT.jpeg", avatarSize, avatarSize, true, false));
        avatarImageArray[1] = new ImageView(new Image("/Suspect/MR_GREEN.jpeg", avatarSize, avatarSize, true, false));
        avatarImageArray[2] = new ImageView(new Image("/Suspect/COLONEL_MUSTARD.jpeg", avatarSize, avatarSize, true, false));
        avatarImageArray[3] = new ImageView(new Image("/Suspect/PROFESSOR_PLUM.jpeg", avatarSize, avatarSize, true, false));
        avatarImageArray[4] = new ImageView(new Image("/Suspect/MRS_WHITE.jpeg", avatarSize, avatarSize, true, false));
        avatarImageArray[5] = new ImageView(new Image("/Suspect/MRS_PEACOCK.jpeg", avatarSize, avatarSize, true, false));

        // Initialize avatar position

        setInitialAvatarPosition();
    }

    public void setInitialAvatarPosition()
    {
        for (int playerNum = 0; playerNum < 6; playerNum++)
        {
            int playerPosition = gameBoard.getPlayerPosition(playerNum);

            if (playerPosition < 9)
            {
                int gridPosition = location[playerPosition].getChildren().size();
                ((GridPane) location[playerPosition]).add((avatarImageArray[playerNum]), gridPosition, 0);
            }
            else
            {
                ((BorderPane) location[playerPosition]).setCenter(avatarImageArray[playerNum]);
            }
        }
    }

    public void setAvatarPosition(int playerNewPosition, int playerOldPosition, int m_playerNum)
    {
        if ((playerNewPosition < 9) & (playerOldPosition < 9))
        {
            int gridPosition = location[playerNewPosition].getChildren().size() + 1;
            ((GridPane) location[playerNewPosition]).add((avatarImageArray[m_playerNum]), gridPosition, 0);
            ((GridPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[m_playerNum]);
        }

        if ((playerNewPosition < 9) & (playerOldPosition >= 9))
        {
            int gridPosition = location[playerNewPosition].getChildren().size() + 1;
            ((GridPane) location[playerNewPosition]).add((avatarImageArray[m_playerNum]), gridPosition, 0);
            ((BorderPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[m_playerNum]);
        }

        if ((playerNewPosition >= 9) & (playerOldPosition < 9))
        {
            ((BorderPane) location[playerNewPosition]).setCenter(avatarImageArray[m_playerNum]);
            ((GridPane) location[playerOldPosition]).getChildren().remove(avatarImageArray[m_playerNum]);
        }
    }

    public void btnMove_Click(Stage primaryStage)
    {
        Button btnUp = new Button("Up");
        btnUp.setPrefWidth(120);
        btnUp.setOnAction(e -> btnUp_Click());
        Button btnDown = new Button("Down");
        btnDown.setPrefWidth(120);
        btnDown.setOnAction(e -> btnDown_Click());
        Button btnLeft = new Button("Left");
        btnLeft.setPrefWidth(120);
        btnLeft.setOnAction(e -> btnLeft_Click());
        Button btnRight = new Button("Right");
        btnRight.setPrefWidth(120);
        btnRight.setOnAction(e -> btnRight_Click());
        Button btnSecretPassage = new Button("Secret Passage");
        btnSecretPassage.setPrefWidth(120);
        btnSecretPassage.setOnAction(e -> btnSecretPassage_Click());

        BorderPane paneControlMove = new BorderPane();
        paneControlMove.setTop(btnUp);
        paneControlMove.setAlignment(btnUp, Pos.CENTER);
        paneControlMove.setMargin(btnUp, new Insets(5));
        paneControlMove.setCenter(btnSecretPassage);
        paneControlMove.setAlignment(btnSecretPassage, Pos.CENTER);
        paneControlMove.setMargin(btnSecretPassage, new Insets(5));
        paneControlMove.setBottom(btnDown);
        paneControlMove.setAlignment(btnDown, Pos.CENTER);
        paneControlMove.setMargin(btnDown, new Insets(5));
        paneControlMove.setLeft(btnLeft);
        paneControlMove.setAlignment(btnLeft, Pos.CENTER);
        paneControlMove.setMargin(btnLeft, new Insets(5));
        paneControlMove.setRight(btnRight);
        paneControlMove.setAlignment(btnRight, Pos.CENTER);
        paneControlMove.setMargin(btnRight, new Insets(5));
        paneControlMove.setPadding(new Insets(10));

        Button btnBackMenu = new Button("Back to menu");
        btnBackMenu.setMaxWidth(150);
        btnBackMenu.setMinWidth(150);
        btnBackMenu.setOnAction(e -> btnBackMenu_Click(primaryStage));

        VBox paneMove = new VBox();
        paneMove.setPrefHeight(80);
        paneMove.setAlignment(Pos.CENTER);
        paneMove.getChildren().addAll(paneControlMove, btnBackMenu);
        paneMove.setSpacing(100);
        paneMove.setMaxWidth(450);
        paneMove.setMinWidth(450);

        paneMain.setRight(paneMove);
        primaryStage.show();
    }

    public void btnAccusation_Click(Stage primaryStage)
    {
        Label lblSuspect = new Label("Suspect");
        RadioButton rdoRevGreen = new RadioButton("Rev Green");
        RadioButton rdoColonelMustard = new RadioButton("Colonel Mustard");
        RadioButton rdoMrsPeacock = new RadioButton("Mrs Peacock");
        RadioButton rdoProfessorPlum = new RadioButton("Professor Plum");
        RadioButton rdoMissScarlett = new RadioButton("Miss Scarlett");
        RadioButton rdoMrsWhite = new RadioButton("Mrs White");
        rdoRevGreen.setSelected(true);
        ToggleGroup groupSuspect = new ToggleGroup();
        rdoRevGreen.setToggleGroup(groupSuspect);
        rdoColonelMustard.setToggleGroup(groupSuspect);
        rdoMrsPeacock.setToggleGroup(groupSuspect);
        rdoProfessorPlum.setToggleGroup(groupSuspect);
        rdoMissScarlett.setToggleGroup(groupSuspect);
        rdoMrsWhite.setToggleGroup(groupSuspect);
        VBox paneSuspect = new VBox(lblSuspect, rdoRevGreen, rdoColonelMustard, rdoMrsPeacock, rdoProfessorPlum, rdoMissScarlett, rdoMrsWhite);
        paneSuspect.setSpacing(5);

        Label lblWeapon = new Label("Weapon");
        RadioButton rdoCandleStick = new RadioButton("Candle Stick");
        RadioButton rdoDagger = new RadioButton("Dagger");
        RadioButton rdoLeadPipe = new RadioButton("Lead Pipe");
        RadioButton rdoRevolver = new RadioButton("Revolver");
        RadioButton rdoRope = new RadioButton("Rope");
        RadioButton rdoWrench = new RadioButton("Wrench");
        rdoCandleStick.setSelected(true);
        ToggleGroup groupWeapon = new ToggleGroup();
        rdoCandleStick.setToggleGroup(groupWeapon);
        rdoDagger.setToggleGroup(groupWeapon);
        rdoLeadPipe.setToggleGroup(groupWeapon);
        rdoRevolver.setToggleGroup(groupWeapon);
        rdoRope.setToggleGroup(groupWeapon);
        rdoWrench.setToggleGroup(groupWeapon);
        VBox paneWeapon = new VBox(lblWeapon, rdoCandleStick, rdoDagger, rdoLeadPipe, rdoRevolver, rdoRope, rdoWrench);
        paneWeapon.setSpacing(5);

        Label lblLocation = new Label("Location");
        RadioButton rdoBallroom = new RadioButton("Ballroom");
        RadioButton rdoBilliardRoom = new RadioButton("Billiard Room");
        RadioButton rdoConservatory = new RadioButton("Conservatory");
        RadioButton rdoDiningRoom = new RadioButton("Dining Room");
        RadioButton rdoHall = new RadioButton("Hall");
        RadioButton rdoKitchen = new RadioButton("Kitchen");
        RadioButton rdoLibrary = new RadioButton("Library");
        RadioButton rdoLounge = new RadioButton("Lounge");
        RadioButton rdoStudy = new RadioButton("Study");
        rdoBallroom.setSelected(true);
        ToggleGroup groupLocation = new ToggleGroup();
        rdoBallroom.setToggleGroup(groupLocation);
        rdoBilliardRoom.setToggleGroup(groupLocation);
        rdoConservatory.setToggleGroup(groupLocation);
        rdoDiningRoom.setToggleGroup(groupLocation);
        rdoHall.setToggleGroup(groupLocation);
        rdoKitchen.setToggleGroup(groupLocation);
        rdoLibrary.setToggleGroup(groupLocation);
        rdoLounge.setToggleGroup(groupLocation);
        rdoStudy.setToggleGroup(groupLocation);
        VBox paneLocation = new VBox(lblLocation, rdoBallroom, rdoBilliardRoom, rdoConservatory,rdoDiningRoom, rdoHall, rdoKitchen, rdoLibrary, rdoLounge, rdoStudy);
        paneLocation.setSpacing(5);

        HBox paneAccusationChoice = new HBox(20, paneSuspect, paneWeapon, paneLocation);
        paneAccusationChoice.setPadding(new Insets(20));
        paneAccusationChoice.setAlignment(Pos.CENTER);

        Button btnConfirmAccusation = new Button("Confirm Accusation");
        btnConfirmAccusation.setMaxWidth(150);
        btnConfirmAccusation.setMinWidth(150);
        btnConfirmAccusation.setOnAction(e -> btnConfirmAccusation_Click());

        Button btnBackMenu = new Button("Back to menu");
        btnBackMenu.setMaxWidth(150);
        btnBackMenu.setMinWidth(150);
        btnBackMenu.setOnAction(e -> btnBackMenu_Click(primaryStage));

        HBox paneAccusationDecision = new HBox(20, btnConfirmAccusation, btnBackMenu);
        paneAccusationDecision.setPadding(new Insets(20));
        paneAccusationDecision.setAlignment(Pos.CENTER);

        VBox paneAccusation = new VBox(20, paneAccusationChoice, paneAccusationDecision);
        paneAccusation.setAlignment(Pos.CENTER);
        paneAccusation.setMaxWidth(450);
        paneAccusation.setMinWidth(450);

        paneMain.setRight(paneAccusation);
        primaryStage.show();
    }

    public void btnHand_Click()
    {}

    public void btnLog_Click()
    {}

    public void btnPass_Click()
    {}

    public void btnUp_Click()
    {
        int playerOldPosition = gameBoard.getPlayerPosition(m_playerNum);

        Message message = new Message(3, m_playerNum);
        message.setInt(1);

        Message returnMessage = gameBoard.processMove(message);

        if (returnMessage.getType() == 16)
        {
            int playerNewPosition = gameBoard.getPlayerPosition(m_playerNum);

            setAvatarPosition(playerNewPosition, playerOldPosition, m_playerNum);
        }
    }

    public void btnDown_Click()
    {
        int playerOldPosition = gameBoard.getPlayerPosition(m_playerNum);

        Message message = new Message(3, m_playerNum);
        message.setInt(2);

        Message returnMessage = gameBoard.processMove(message);

        if (returnMessage.getType() == 16)
        {
            int playerNewPosition = gameBoard.getPlayerPosition(m_playerNum);

            setAvatarPosition(playerNewPosition, playerOldPosition, m_playerNum);
        }
    }

    public void btnLeft_Click()
    {
        int playerOldPosition = gameBoard.getPlayerPosition(m_playerNum);

        Message message = new Message(3, m_playerNum);
        message.setInt(3);

        Message returnMessage = gameBoard.processMove(message);

        if (returnMessage.getType() == 16)
        {
            int playerNewPosition = gameBoard.getPlayerPosition(m_playerNum);

            setAvatarPosition(playerNewPosition, playerOldPosition, m_playerNum);
        }
    }

    public void btnRight_Click()
    {
        int playerOldPosition = gameBoard.getPlayerPosition(m_playerNum);

        Message message = new Message(3, m_playerNum);
        message.setInt(4);

        Message returnMessage = gameBoard.processMove(message);

        if (returnMessage.getType() == 16)
        {
            int playerNewPosition = gameBoard.getPlayerPosition(m_playerNum);

            setAvatarPosition(playerNewPosition, playerOldPosition, m_playerNum);
        }
    }

    public void btnSecretPassage_Click()
    {
        int playerOldPosition = gameBoard.getPlayerPosition(m_playerNum);

        Message message = new Message(3, m_playerNum);
        message.setInt(5);

        Message returnMessage = gameBoard.processMove(message);

        if (returnMessage.getType() == 16)
        {
            int playerNewPosition = gameBoard.getPlayerPosition(m_playerNum);

            setAvatarPosition(playerNewPosition, playerOldPosition, m_playerNum);
        }
    }

    public void btnBackMenu_Click(Stage primaryStage)
    {
        Button btnMove = new Button("Move");
        btnMove.setMaxWidth(150);
        btnMove.setMinWidth(150);
        btnMove.setOnAction(e -> btnMove_Click(primaryStage));
        Button btnAccusation = new Button("Make Accusation");
        btnAccusation.setMaxWidth(150);
        btnAccusation.setMinWidth(150);
        btnAccusation.setOnAction(e -> btnAccusation_Click(primaryStage));
        Button btnHand = new Button("View Hand");
        btnHand.setMaxWidth(150);
        btnHand.setMinWidth(150);
        btnHand.setOnAction(e -> btnHand_Click());
        Button btnLog = new Button("View/Edit Log");
        btnLog.setMaxWidth(150);
        btnLog.setMinWidth(150);
        btnLog.setOnAction(e -> btnLog_Click());
        Button btnPass = new Button("Pass");
        btnPass.setMaxWidth(150);
        btnPass.setMinWidth(150);
        btnPass.setOnAction(e -> btnPass_Click());

        VBox paneMenu = new VBox();
        paneMenu.setSpacing(20);
        paneMenu.getChildren().addAll(btnMove, btnAccusation, btnHand, btnLog, btnPass);
        paneMenu.setAlignment(Pos.CENTER);
        paneMenu.setPadding(new Insets(20));
        paneMenu.setMaxWidth(450);
        paneMenu.setMinWidth(450);

        paneMain.setRight(paneMenu);
        primaryStage.show();
    }

    public void btnConfirmAccusation_Click()
    {}
}