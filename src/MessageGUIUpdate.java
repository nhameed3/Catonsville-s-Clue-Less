public class MessageGUIUpdate extends Message
{
	int[] playerNewPosition;

	public MessageGUIUpdate(int messageType, int whichPlayer, int[] playerNewPosition)
	{
		super(messageType, whichPlayer);
		this.playerNewPosition = playerNewPosition;
	}

	public void setPlayerNewPosition(int[] playerNewPosition)
	{
		this.playerNewPosition = playerNewPosition;
	}

	public int[] getPlayerNewPosition()
	{
		return this.playerNewPosition;
	}
}

