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

    public Player(String name, int playerNum ) {
        setUserName(name); 
        setPlayerNum(playerNum);
        
        switch(playerNum){
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



    // The user is asked who what and where 
    // and a message containing their guess is returned.
    public Message makeAccusation(){
        System.out.println("Player::makeAccusation");
                //Get the suspect suggestion from user
        int invalid = 1; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] REV_GREEN");
            System.out.println("[2] COLONEL_MUSTARD");
            System.out.println("[3] MRS_PEACOCK");
            System.out.println("[4] PROFESSOR_PLUM");
            System.out.println("[5] MISS_SCARLET");
            System.out.println("[6] MRS_WHITE");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    personGuess.setSuspect(REV_GREEN);
                    invalid = 0;
                    break;
                case 2:
                    personGuess.setSuspect(COLONEL_MUSTARD);
                    invalid = 0;
                    break;
                case 3:
                    personGuess.setSuspect(MRS_PEACOCK);
                    invalid = 0;
                    break;
                case 4:
                    personGuess.setSuspect(PROFESSOR_PLUM);
                    invalid = 0;
                    break;
                case  5:
                    personGuess.setSuspect(MISS_SCARLET);
                    invalid = 0;
                    break;
                case 6:
                    personGuess.setSuspect(MRS_WHITE);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the room suggestion from user
        invalid = 1; 
        while(invalid){
            System.out.println("Where do you think they did it?");
            System.out.println("[1] BALLROOM");
            System.out.println("[2] BILLIARD_ROOM");
            System.out.println("[3] CONSERVATORY");
            System.out.println("[4] DINING_ROOM");
            System.out.println("[5] HALL");
            System.out.println("[6] KITCHEN");
            System.out.println("[7] LIBRARY");
            System.out.println("[8] LOUNGE");
            System.out.println("[9] STUDY");
            
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    roomGuess.setRoom(BALLROOM);
                    invalid = 0;
                    break;
                case 2:
                    roomGuess.setRoom(BILLIARD_ROOM);
                    invalid = 0;
                    break;
                case 3:
                    roomGuess.setRoom(CONSERVATORY);
                    invalid = 0;
                    break;
                case 4:
                    roomGuess.setRoom(DINING_ROOM);
                    invalid = 0;
                    break;
                case  5:
                    roomGuess.setRoom(HALL);
                    invalid = 0;
                    break;
                case 6:
                    roomGuess.setRoom(KITCHEN);
                    invalid = 0;
                    break;
                case 7:
                    roomGuess.setRoom(LIBRARY);
                    invalid = 0;
                    break;
                case 8:
                    roomGuess.setRoom(LOUNGE);
                    invalid = 0;
                    break;
                case 9:
                    roomGuess.setRoom(STUDY);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the weapon suggestion from user
        invalid = 1; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] CANDLE_STICK");
            System.out.println("[2] DAGGER");
            System.out.println("[3] LEAD");
            System.out.println("[4] PIPE");
            System.out.println("[5] REVOLVER");
            System.out.println("[6] ROPE");
            System.out.println("[7] WRENCH");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    weaponGuess.setWeapon(CANDLE_STICK);
                    invalid = 0;
                    break;
                case 2:
                    weaponGuess.setWeapon(DAGGER);
                    invalid = 0;
                    break;
                case 3:
                    weaponGuess.setWeapon(LEAD);
                    invalid = 0;
                    break;
                case 4:
                    weaponGuess.setWeapon(PIPE);
                    invalid = 0;
                    break;
                case  5:
                    weaponGuess.setWeapon(REVOLVER);
                    invalid = 0;
                    break;
                case 6:
                    weaponGuess.setWeapon(ROPE);
                    invalid = 0;
                    break;
                case 7:
                    weaponGuess.setWeapon(WRENCH);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        Message message = new Message (personGuess, roomGuess, weaponGuess);
        return message; 
    }






    // Called directly from client.
    //The user can either pass or guess. The user is asked who what and where 
    // and a message containing their guess is returned.
    public Message makeGuess(){
        Card personGuess = new Card();
        Card weaponGuess = new Card();
        Card roomGuess = new Card();

        //Our software uses accuse variables for both guesses and accusations 
        System.out.println("Player::makeGuess");
        //returns guess or pass message
        System.out.println("Enter [1] to pass instead of guessing any other key to guess");
        // System.out.println("[1] Guess");
        // System.out.println("[2] Pass");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if(choice == 1)
            return new Message(0);      //pass message
        
        //Get the suspect suggestion from user
        int invalid = 1; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] REV_GREEN");
            System.out.println("[2] COLONEL_MUSTARD");
            System.out.println("[3] MRS_PEACOCK");
            System.out.println("[4] PROFESSOR_PLUM");
            System.out.println("[5] MISS_SCARLET");
            System.out.println("[6] MRS_WHITE");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    personGuess.setSuspect(REV_GREEN);
                    invalid = 0;
                    break;
                case 2:
                    personGuess.setSuspect(COLONEL_MUSTARD);
                    invalid = 0;
                    break;
                case 3:
                    personGuess.setSuspect(MRS_PEACOCK);
                    invalid = 0;
                    break;
                case 4:
                    personGuess.setSuspect(PROFESSOR_PLUM);
                    invalid = 0;
                    break;
                case  5:
                    personGuess.setSuspect(MISS_SCARLET);
                    invalid = 0;
                    break;
                case 6:
                    personGuess.setSuspect(MRS_WHITE);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the room suggestion from user
        invalid = 1; 
        while(invalid){
            System.out.println("Where do you think they did it?");
            System.out.println("[1] BALLROOM");
            System.out.println("[2] BILLIARD_ROOM");
            System.out.println("[3] CONSERVATORY");
            System.out.println("[4] DINING_ROOM");
            System.out.println("[5] HALL");
            System.out.println("[6] KITCHEN");
            System.out.println("[7] LIBRARY");
            System.out.println("[8] LOUNGE");
            System.out.println("[9] STUDY");
            
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    roomGuess.setRoom(BALLROOM);
                    invalid = 0;
                    break;
                case 2:
                    roomGuess.setRoom(BILLIARD_ROOM);
                    invalid = 0;
                    break;
                case 3:
                    roomGuess.setRoom(CONSERVATORY);
                    invalid = 0;
                    break;
                case 4:
                    roomGuess.setRoom(DINING_ROOM);
                    invalid = 0;
                    break;
                case  5:
                    roomGuess.setRoom(HALL);
                    invalid = 0;
                    break;
                case 6:
                    roomGuess.setRoom(KITCHEN);
                    invalid = 0;
                    break;
                case 7:
                    roomGuess.setRoom(LIBRARY);
                    invalid = 0;
                    break;
                case 8:
                    roomGuess.setRoom(LOUNGE);
                    invalid = 0;
                    break;
                case 9:
                    roomGuess.setRoom(STUDY);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the weapon suggestion from user
        invalid = 1; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] CANDLE_STICK");
            System.out.println("[2] DAGGER");
            System.out.println("[3] LEAD");
            System.out.println("[4] PIPE");
            System.out.println("[5] REVOLVER");
            System.out.println("[6] ROPE");
            System.out.println("[7] WRENCH");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    weaponGuess.setWeapon(CANDLE_STICK);
                    invalid = 0;
                    break;
                case 2:
                    weaponGuess.setWeapon(DAGGER);
                    invalid = 0;
                    break;
                case 3:
                    weaponGuess.setWeapon(LEAD);
                    invalid = 0;
                    break;
                case 4:
                    weaponGuess.setWeapon(PIPE);
                    invalid = 0;
                    break;
                case  5:
                    weaponGuess.setWeapon(REVOLVER);
                    invalid = 0;
                    break;
                case 6:
                    weaponGuess.setWeapon(ROPE);
                    invalid = 0;
                    break;
                case 7:
                    weaponGuess.setWeapon(WRENCH);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        Message message = new Message (personGuess, roomGuess, weaponGuess);
        return message;        
    }



    //Asks user which direction they want to move in and returns message with that movement
    public Message move(){
        System.out.println("Player::move");
        while(1){
            System.out.println("[1] UP");
            System.out.println("[2] DOWN");
            System.out.println("[3] LEFT");
            System.out.println("[4] RIGHT");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    return new Message(up);
                case 2:
                    return new Message(down);
                case 3:
                    return new Message(left);
                case 4:
                    return new Message(right);
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }
    }


    //Called directly from Client. Compares the cards within the message to players hand.
    public Message disprove(Message message){
        System.out.println("Player::disprove");
        Card personDisprove = new Card();
        Card weaponDisprove = new Card();
        Card roomDisprove = new Card();
        for(int i = 0; i < 9; i++){
            if(this.hand[i] == message.personAccused)
                personDisprove = message.personAccused;
            if(this.hand[i] == message.weaponAccused)
                weaponDisprove = message.weaponAccused;
            if(this.hand[i] == message.roomAccused)
                roomDisprove = message.roomAccused;
        }

        while(1){
            if(personDisprove != NULL)
                System.out.println("[1] Disprove with: "+personDisprove);
            if(weaponDisprove != NULL)
                System.out.println("[2] Disprove with: "+weaponDisprove);
            if(roomDisprove != NULL)
                System.out.println("[3] Disprove with: "+roomDisprove);

            if(roomDisprove != NULL && weaponDisprove != NULL && personDisprove != NULL){
                System.out.println("You cannot disprove this guess");
                return new Message(0);      //pass message
            }

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    return new Message(personDisprove);
                case 2:
                    return new Message(weaponDisprove);
                case 3:
                    return new Message(roomDisprove);
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }
    }



    //Called directly from client
    public Message playerTurn(){
        while(1){
            System.out.println("Player::playerTurn");
            System.out.println("[1] Move");
            System.out.println("[2] Make Accusation");
            System.out.println("[3] View Hand");
            System.out.println("[4] View/Edit Log");
            System.out.println("[5] Pass");
            //System.out.println("[5] Disprove");       

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
                return new Message(0);      //pass message
            default:
                System.out.println("INVALID ENTRY TRY AGAIN");
                break;
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