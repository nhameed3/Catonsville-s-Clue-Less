import java.util.Scanner;;


public class Player {

    private String userName = "";
    private String avatar = "";
    private String log = "";
    private boolean player1 = false;
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

    public Player(String name, int avatarNum, boolean player1 ) {
        setUserName(name); 
        setPlayer1(player1);

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
    public boolean isPlayer1() {
        System.out.println("Player::isPlayer1");
        return player1;
    }
    public void setPlayer1(boolean player1) {
        System.out.println("Player::isPlayer1");
        this.player1 = player1;
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
        if(isPlayer1())
            return new Message(5, 1);
        else
            return new Message(5, 0);
    }

    public Message makeGuess(){
        //Our software uses accuse variables for both guesses and accusations 
        System.out.println("Player::makeGuess");
        if(isPlayer1())
            return new Message(4, 1);
        else
            return new Message(4, 0);
    }

    public Message move(){
        System.out.println("Player::move");
        if(isPlayer1())
            return new Message(3, 1);
        else
            return new Message(3, 0);
    }

    public Message playerTurn(){
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
                break;
            default:
                return new Message(0,0);
        }

    }

}