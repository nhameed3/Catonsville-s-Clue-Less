/* This message is designed to convey move information from player/client to server/board
 * 
 */
public class MessageMove extends Message{
  
  	public MessageMove(int givenType, int givenPlayer, int genericInt, String genericText) {
		  this.messageType = givenType;
		  this.whichPlayer = givenPlayer;
      this.genericInt = genericInt;
      this.genericText = genericText;
    }
}
