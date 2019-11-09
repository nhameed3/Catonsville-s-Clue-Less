import java.util.Scanner;;
import java.io.*;
import java.util.*;
import java.net.*;

public class Player {

    private String userName = "";
    private String avatar = "";
    private String log = "";
    private int m_playerNum = 0;
    private boolean accusation = false;

    private int avatarAccuse = 0;
    private int roomAccuse = 0;
    private int weaponAccuse = 0;

    //Minimum of two players so max hand is 9 cards
    Card hand[] = new Card[9];

/******Key for reference*************************
    // public static final int SCARLET = 0;
    // public static final int GREEN   = 1;
    // public static final int MUSTARD = 2;
    // public static final int PLUM    = 3;
    // public static final int WHITE   = 4;
    // public static final int PEACOCK = 5;

    // public static final int STUDY     = 0;
    // public static final int HALL      = 1;
    // public static final int LOUNGE    = 2;
    // public static final int LIBRARY   = 3;
    // public static final int BILLIARDS = 4;
    // public static final int DINING    = 5;
    // public static final int KITCHEN   = 6;

    // public static final int ROPE        = 0;
    // public static final int PIPE        = 1;
    // public static final int KNIFE       = 2;
    // public static final int WRENCH      = 3;
    // public static final int CANDLESTICK = 4;
    // public static final int REVOLVER    = 5;
****************************************************** */

    public Player() {
    }

    public Player(String name, int avatarNum, int playerNum ) {
        setUserName(name); 
        setPlayerNum(playerNum);

        switch(avatarNum){
            case 0:
                avatar = "SCARLET";
            case 1:
                avatar = "GREEN";
            case 2:
                avatar = "MUSTARD";
            case 3:
                avatar = "PLUM";
            case 4:
                avatar = "WHITE";
            case 5:
                avatar = "PEACOCK";
        }
        setAvatar(avatar);
    }

//SETTERS AND GETTERS
//////////////////////////////////////////////////
    public void getHand() {
        System.out.println("Your Cards:");
        for(int i = 0; i < 9; i++){
            System.out.println(this.hand[i]);
        }
    }

    public void setHand(Card card, int index) {
        this.hand[index] = card;
    }

//////////////////////////////////////////////////
    public void getLog() {
        System.out.println("Log: " + this.log);
        System.out.println("Would you like to edit your log: (y = 1/ n = 0)");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if(choice)
            this.setLog();
    }

    public void setLog(Card card, int index) {
        System.out.println("Enter your new log.");
        Scanner input = new Scanner(System.in);
        String newLog = input.nextLine();
        this.log = newLog;
        System.out.println("Your new log: " + this.log);
    }

//////////////////////////////////////////////////
    public String getUserName() {
        System.out.println("Player::getUserName");
        return userName;
    }
    public void setUserName(String userName) {
        System.out.println("Player::setUserName");
        this.userName = userName;
    }

//////////////////////////////////////////////////
    public String getAvatar() {
        System.out.println("Player::getAvatar");
        return avatar;
    }
    public void setAvatar(String avatar) {
        System.out.println("Player::setAvatar");
        this.avatar = avatar;
    }

//////////////////////////////////////////////////
    public int getPlayerNum() {
        System.out.println("Player::getPlayerNum");
        return m_playerNum;
    }
    public void setPlayerNum(int playerNum) {
        System.out.println("Player::setPlayerNum");
        this.m_playerNum = playerNum;
    }

//////////////////////////////////////////////////
    public int getavatarAccuse() {
        System.out.println("Player::getavatarAccuse");
        return avatarAccuse;
    }
    public int getroomAccuse() {
        System.out.println("Player::getroomAccuse");
        return roomAccuse;
    }
    public int getweaponAccuse() {
        System.out.println("Player::getweaponAccuse");
        return weaponAccuse;
    }

    public int setavatarAccuse(int avatarAccuse) {
        System.out.println("Player::setavatarAccuse");
        return this.avatarAccuse = avatarAccuse;
    }
    public int setroomAccuse(int roomAccuse) {
        System.out.println("Player::setroomAccuse");
        return this.roomAccuse = roomAccuse;
    }
    public int setweaponAccuse(int weaponAccuse) {
        System.out.println("Player::setweaponAccuse");
        return this.weaponAccuse = weaponAccuse;
    }  

//////////////////////////////////////////////////
    public boolean getAccusationResult() {
        System.out.println("Player::getAccusationResult");
        return accusation;
    }
    public void setAccusationResult(boolean accusation) {
        System.out.println("Player::setAccusationResult");
        accusation = true;
    }

//METHODS Preconditions: must be players turn
//////////////////////////////////////////////////
    public Message makeAccusation(){
        System.out.println("Player::makeAccusation");
    }

    public Message makeGuess(){
        //Our software uses accuse variables for both guesses and accusations 
        System.out.println("Player::makeGuess");
    }

    public Message move(){
        System.out.println("Player::move");
    }

    public Message disprove(){
        System.out.println("Player::disprove");
    }

    public Message playerTurn(){
        while(1){
            System.out.println("Player::playerTurn");
            System.out.println("[1] Move");
            System.out.println("[2] Make Accusation");
            System.out.println("[3] View Hand");
            System.out.println("[4] View/Edit Log");
            System.out.println("[5] Disprove");

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();

            switch(choice){
            case 1:
                return this.move();
            case 2:
                return this.makeAccusation();
            case 3:
                this.getHand();
                break;
            case 4:
                this.getLog();
                break;
            case 5:
                this.disprove();
                break;
            default:
                return new Message(0,0);
            }
        }
    }
}


/* Client is the driver of the game on client computers. It takes two arguments (IP and port) and connects to the
server. It launches threads for receiving objects and sending objects to and from and the server. For now this
is also where all Player code goes?
*/
public class Client {

	public static void main(String[] args) throws IOException, ClassNotFoundException{

		// check to make sure we got the args we need
		if (args.length != 2) {
			System.err.println("Missing arugments. java PlayerClient <hostname> <port>");
			System.exit(1);
		}
				
		// parse host
		String host = args[0];
		// parse port
		int port = Integer.parseInt(args[1]);
		
		// establish the socket
		Socket gameSocket = new Socket(host, port);
		
		//create a scanner for user input
		Scanner inScn = new Scanner(System.in);
		
		//grab input and output streams
		ObjectOutputStream out = new ObjectOutputStream( gameSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream());
		
		// have a boolean flag for startGame, defaults to false
		boolean startGame = false;
		
		// track what player we are. I know this isn't how Sam wants to do it so can be overwritten
		int playerNumber;

		// we start with a while loop waiting for the game to start
		while ( startGame == false) {
			Message inMessage = (Message) in.readObject();
			// if the inMessage is type 10 it tells us what player we are
			if (inMessage.getType() == 10) {
				// store what player we are
				playerNumber = inMessage.getInt();
                
                //Create player
                Player player = new Player("name", 1, playerNumber);

				// print what player we are
				System.out.println("You are Player " + playerNumber);
				//check if we are player 1
				if( player.m_playerNum == 1) {
					//if we are player 1 we need to tell the server how many players
					System.out.println("How many players do you want to play with?");
					int maxPlayers = inScn.nextInt();
					// sent that back to the server
					Message desiredPlayers = new Message(9, 1);
					desiredPlayers.setInt(maxPlayers);
					out.writeObject(desiredPlayers);
				}
			}
			// the other message we want to look out for is the start message
			else if (inMessage.getType() == 1) {
				// print how many players
				System.out.println("Game is starting! There are " + inMessage.getInt() + " players in this game.");
				// set the start flag
				startGame = true;
			}
		}
	}
}