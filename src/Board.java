import java.util.ArrayList; 

public class Board
{
	private class Location
	{
		int locationNumber;
		String locationName;
		boolean isHallway;
		boolean isOccupied;

		public Location( int locationNumber, String locationName, boolean isHallway, boolean isOccupied )
		{
			this.locationNumber = locationNumber;
			this.locationName = locationName;
			this.isHallway = isHallway;
			this.isOccupied = isOccupied;
		}

		public String getLocationName()
		{
			return this.locationName;
		}

		public boolean getIsHallway()
		{
			return this.isHallway;
		}

		public boolean getIsOccupied()
		{
			return this.isOccupied;
		}

		public void setIsOccupied( boolean isOccupiedStatus )
		{
			this.isOccupied = isOccupiedStatus;
		}			
	}

	Location[] locationArray = new Location[21];
	int[] playerPosition = new int[6];
	boolean[][] locationMatrix = new boolean[21][21];

	public Board()
	{
		locationArray[0] = new Location( 0, "Study", false, false );
		locationArray[1] = new Location( 1, "Hall", false, false );
		locationArray[2] = new Location( 2, "Lounge", false, false );
		locationArray[3] = new Location( 3, "Library", false, false );
		locationArray[4] = new Location( 4, "Billiard Room", false, false );
		locationArray[5] = new Location( 5, "Dining Room", false, false );
		locationArray[6] = new Location( 6, "Conservatory", false, false );
		locationArray[7] = new Location( 7, "Ballroom", false, false );
		locationArray[8] = new Location( 8, "Kitchen", false, false );
		locationArray[9] = new Location( 9, "Hallway 1", true, false );
		locationArray[10] = new Location( 10, "Hallway 2", true, true );
		locationArray[11] = new Location( 11, "Hallway 3", true, true );
		locationArray[12] = new Location( 12, "Hallway 4", true, false );
		locationArray[13] = new Location( 13, "Hallway 5", true, true );
		locationArray[14] = new Location( 14, "Hallway 6", true, false );
		locationArray[15] = new Location( 15, "Hallway 7", true, false );
		locationArray[16] = new Location( 16, "Hallway 8", true, true );
		locationArray[17] = new Location( 17, "Hallway 9", true, false );
		locationArray[18] = new Location( 18, "Hallway 10", true, false );
		locationArray[19] = new Location( 19, "Hallway 11", true, true );
		locationArray[20] = new Location( 20, "Hallway 12", true, true );

		playerPosition[0] = 10;
		playerPosition[1] = 13;
		playerPosition[2] = 20;
		playerPosition[3] = 19;
		playerPosition[4] = 16;
		playerPosition[5] = 11;

		locationMatrix[0][0] = false;	 	locationMatrix[0][11] = true;
		locationMatrix[0][1] = false;		locationMatrix[0][12] = false; 
		locationMatrix[0][2] = false;		locationMatrix[0][13] = false; 
		locationMatrix[0][3] = false;		locationMatrix[0][14] = false; 
		locationMatrix[0][4] = false; 		locationMatrix[0][15] = false; 
		locationMatrix[0][5] = false; 		locationMatrix[0][16] = false; 
		locationMatrix[0][6] = false; 		locationMatrix[0][17] = false; 
		locationMatrix[0][7] = false; 		locationMatrix[0][18] = false; 
		locationMatrix[0][8] = true; 		locationMatrix[0][19] = false; 
		locationMatrix[0][9] = true; 		locationMatrix[0][20] = false; 
		locationMatrix[0][10] = false; 

		locationMatrix[1][0] = false;	 	locationMatrix[1][11] = false;
		locationMatrix[1][1] = false;		locationMatrix[1][12] = true; 
		locationMatrix[1][2] = false;		locationMatrix[1][13] = false; 
		locationMatrix[1][3] = false;		locationMatrix[1][14] = false; 
		locationMatrix[1][4] = false; 		locationMatrix[1][15] = false; 
		locationMatrix[1][5] = false; 		locationMatrix[1][16] = false; 
		locationMatrix[1][6] = false; 		locationMatrix[1][17] = false; 
		locationMatrix[1][7] = false; 		locationMatrix[1][18] = false; 
		locationMatrix[1][8] = false; 		locationMatrix[1][19] = false; 
		locationMatrix[1][9] = true; 		locationMatrix[1][20] = false; 
		locationMatrix[1][10] = true; 
		
		locationMatrix[2][0] = false;	 	locationMatrix[2][11] = false;
		locationMatrix[2][1] = false;		locationMatrix[2][12] = false; 
		locationMatrix[2][2] = false;		locationMatrix[2][13] = true; 
		locationMatrix[2][3] = false;		locationMatrix[2][14] = false; 
		locationMatrix[2][4] = false; 		locationMatrix[2][15] = false; 
		locationMatrix[2][5] = false; 		locationMatrix[2][16] = false; 
		locationMatrix[2][6] = true; 		locationMatrix[2][17] = false; 
		locationMatrix[2][7] = false; 		locationMatrix[2][18] = false; 
		locationMatrix[2][8] = false; 		locationMatrix[2][19] = false; 
		locationMatrix[2][9] = false; 		locationMatrix[2][20] = false; 
		locationMatrix[2][10] = true; 

		locationMatrix[3][0] = false;	 	locationMatrix[3][11] = true;
		locationMatrix[3][1] = false;		locationMatrix[3][12] = false; 
		locationMatrix[3][2] = false;		locationMatrix[3][13] = false; 
		locationMatrix[3][3] = false;		locationMatrix[3][14] = true; 
		locationMatrix[3][4] = false; 		locationMatrix[3][15] = false; 
		locationMatrix[3][5] = false; 		locationMatrix[3][16] = true; 
		locationMatrix[3][6] = false; 		locationMatrix[3][17] = false; 
		locationMatrix[3][7] = false; 		locationMatrix[3][18] = false; 
		locationMatrix[3][8] = false; 		locationMatrix[3][19] = false; 
		locationMatrix[3][9] = false; 		locationMatrix[3][20] = false; 
		locationMatrix[3][10] = false;

		locationMatrix[4][0] = false;	 	locationMatrix[4][11] = false;
		locationMatrix[4][1] = false;		locationMatrix[4][12] = true; 
		locationMatrix[4][2] = false;		locationMatrix[4][13] = false; 
		locationMatrix[4][3] = false;		locationMatrix[4][14] = true; 
		locationMatrix[4][4] = false; 		locationMatrix[4][15] = true; 
		locationMatrix[4][5] = false; 		locationMatrix[4][16] = false; 
		locationMatrix[4][6] = false; 		locationMatrix[4][17] = true; 
		locationMatrix[4][7] = false; 		locationMatrix[4][18] = false; 
		locationMatrix[4][8] = false; 		locationMatrix[4][19] = false; 
		locationMatrix[4][9] = false; 		locationMatrix[4][20] = false; 
		locationMatrix[4][10] = false;

		locationMatrix[5][0] = false;	 	locationMatrix[5][11] = false;
		locationMatrix[5][1] = false;		locationMatrix[5][12] = false; 
		locationMatrix[5][2] = false;		locationMatrix[5][13] = true; 
		locationMatrix[5][3] = false;		locationMatrix[5][14] = false; 
		locationMatrix[5][4] = false; 		locationMatrix[5][15] = true; 
		locationMatrix[5][5] = false; 		locationMatrix[5][16] = false; 
		locationMatrix[5][6] = false; 		locationMatrix[5][17] = false; 
		locationMatrix[5][7] = false; 		locationMatrix[5][18] = true; 
		locationMatrix[5][8] = false; 		locationMatrix[5][19] = false; 
		locationMatrix[5][9] = false; 		locationMatrix[5][20] = false; 
		locationMatrix[5][10] = false;

		locationMatrix[6][0] = false;	 	locationMatrix[6][11] = false;
		locationMatrix[6][1] = false;		locationMatrix[6][12] = false; 
		locationMatrix[6][2] = true;		locationMatrix[6][13] = false; 
		locationMatrix[6][3] = false;		locationMatrix[6][14] = false; 
		locationMatrix[6][4] = false; 		locationMatrix[6][15] = false; 
		locationMatrix[6][5] = false; 		locationMatrix[6][16] = true; 
		locationMatrix[6][6] = false; 		locationMatrix[6][17] = false; 
		locationMatrix[6][7] = false; 		locationMatrix[6][18] = false; 
		locationMatrix[6][8] = false; 		locationMatrix[6][19] = true; 
		locationMatrix[6][9] = false; 		locationMatrix[6][20] = false; 
		locationMatrix[6][10] = false;

		locationMatrix[7][0] = false;	 	locationMatrix[7][11] = false;
		locationMatrix[7][1] = false;		locationMatrix[7][12] = false; 
		locationMatrix[7][2] = false;		locationMatrix[7][13] = false; 
		locationMatrix[7][3] = false;		locationMatrix[7][14] = false; 
		locationMatrix[7][4] = false; 		locationMatrix[7][15] = false; 
		locationMatrix[7][5] = false; 		locationMatrix[7][16] = false; 
		locationMatrix[7][6] = false; 		locationMatrix[7][17] = true; 
		locationMatrix[7][7] = false; 		locationMatrix[7][18] = false; 
		locationMatrix[7][8] = false; 		locationMatrix[7][19] = true; 
		locationMatrix[7][9] = false; 		locationMatrix[7][20] = true; 
		locationMatrix[7][10] = false;

		locationMatrix[8][0] = true;	 	locationMatrix[8][11] = false;
		locationMatrix[8][1] = false;		locationMatrix[8][12] = false; 
		locationMatrix[8][2] = false;		locationMatrix[8][13] = false; 
		locationMatrix[8][3] = false;		locationMatrix[8][14] = false; 
		locationMatrix[8][4] = false; 		locationMatrix[8][15] = false; 
		locationMatrix[8][5] = false; 		locationMatrix[8][16] = false; 
		locationMatrix[8][6] = false; 		locationMatrix[8][17] = false; 
		locationMatrix[8][7] = false; 		locationMatrix[8][18] = true; 
		locationMatrix[8][8] = false; 		locationMatrix[8][19] = false; 
		locationMatrix[8][9] = false; 		locationMatrix[8][20] = true; 
		locationMatrix[8][10] = false;

		locationMatrix[9][0] = true;	 	locationMatrix[9][11] = false;
		locationMatrix[9][1] = true;		locationMatrix[9][12] = false; 
		locationMatrix[9][2] = false;		locationMatrix[9][13] = false; 
		locationMatrix[9][3] = false;		locationMatrix[9][14] = false; 
		locationMatrix[9][4] = false; 		locationMatrix[9][15] = false; 
		locationMatrix[9][5] = false; 		locationMatrix[9][16] = false; 
		locationMatrix[9][6] = false; 		locationMatrix[9][17] = false; 
		locationMatrix[9][7] = false; 		locationMatrix[9][18] = false; 
		locationMatrix[9][8] = false; 		locationMatrix[9][19] = false; 
		locationMatrix[9][9] = false; 		locationMatrix[9][20] = false; 
		locationMatrix[9][10] = false;

		locationMatrix[10][0] = false;	 	locationMatrix[10][11] = false;
		locationMatrix[10][1] = true;		locationMatrix[10][12] = false; 
		locationMatrix[10][2] = true;		locationMatrix[10][13] = false; 
		locationMatrix[10][3] = false;		locationMatrix[10][14] = false; 
		locationMatrix[10][4] = false; 		locationMatrix[10][15] = false; 
		locationMatrix[10][5] = false; 		locationMatrix[10][16] = false; 
		locationMatrix[10][6] = false; 		locationMatrix[10][17] = false; 
		locationMatrix[10][7] = false; 		locationMatrix[10][18] = false; 
		locationMatrix[10][8] = false; 		locationMatrix[10][19] = false; 
		locationMatrix[10][9] = false; 		locationMatrix[10][20] = false; 
		locationMatrix[10][10] = false;

		locationMatrix[11][0] = true;	 	locationMatrix[11][11] = false;
		locationMatrix[11][1] = false;		locationMatrix[11][12] = false; 
		locationMatrix[11][2] = false;		locationMatrix[11][13] = false; 
		locationMatrix[11][3] = true;		locationMatrix[11][14] = false; 
		locationMatrix[11][4] = false; 		locationMatrix[11][15] = false; 
		locationMatrix[11][5] = false; 		locationMatrix[11][16] = false; 
		locationMatrix[11][6] = false; 		locationMatrix[11][17] = false; 
		locationMatrix[11][7] = false; 		locationMatrix[11][18] = false; 
		locationMatrix[11][8] = false; 		locationMatrix[11][19] = false; 
		locationMatrix[11][9] = false; 		locationMatrix[11][20] = false; 
		locationMatrix[11][10] = false;

		locationMatrix[12][0] = false;	 	locationMatrix[12][11] = false;
		locationMatrix[12][1] = true;		locationMatrix[12][12] = false; 
		locationMatrix[12][2] = false;		locationMatrix[12][13] = false; 
		locationMatrix[12][3] = false;		locationMatrix[12][14] = false; 
		locationMatrix[12][4] = true; 		locationMatrix[12][15] = false; 
		locationMatrix[12][5] = false; 		locationMatrix[12][16] = false; 
		locationMatrix[12][6] = false; 		locationMatrix[12][17] = false; 
		locationMatrix[12][7] = false; 		locationMatrix[12][18] = false; 
		locationMatrix[12][8] = false; 		locationMatrix[12][19] = false; 
		locationMatrix[12][9] = false; 		locationMatrix[12][20] = false; 
		locationMatrix[12][10] = false;

		locationMatrix[13][0] = false;	 	locationMatrix[13][11] = false;
		locationMatrix[13][1] = false;		locationMatrix[13][12] = false; 
		locationMatrix[13][2] = true;		locationMatrix[13][13] = false; 
		locationMatrix[13][3] = false;		locationMatrix[13][14] = false; 
		locationMatrix[13][4] = false; 		locationMatrix[13][15] = false; 
		locationMatrix[13][5] = true; 		locationMatrix[13][16] = false; 
		locationMatrix[13][6] = false; 		locationMatrix[13][17] = false; 
		locationMatrix[13][7] = false; 		locationMatrix[13][18] = false; 
		locationMatrix[13][8] = false; 		locationMatrix[13][19] = false; 
		locationMatrix[13][9] = false; 		locationMatrix[13][20] = false; 
		locationMatrix[13][10] = false;

		locationMatrix[14][0] = false;	 	locationMatrix[14][11] = false;
		locationMatrix[14][1] = false;		locationMatrix[14][12] = false; 
		locationMatrix[14][2] = false;		locationMatrix[14][13] = false; 
		locationMatrix[14][3] = true;		locationMatrix[14][14] = false; 
		locationMatrix[14][4] = true; 		locationMatrix[14][15] = false; 
		locationMatrix[14][5] = false; 		locationMatrix[14][16] = false; 
		locationMatrix[14][6] = false; 		locationMatrix[14][17] = false; 
		locationMatrix[14][7] = false; 		locationMatrix[14][18] = false; 
		locationMatrix[14][8] = false; 		locationMatrix[14][19] = false; 
		locationMatrix[14][9] = false; 		locationMatrix[14][20] = false; 
		locationMatrix[14][10] = false;

		locationMatrix[15][0] = false;	 	locationMatrix[15][11] = false;
		locationMatrix[15][1] = false;		locationMatrix[15][12] = false; 
		locationMatrix[15][2] = false;		locationMatrix[15][13] = false; 
		locationMatrix[15][3] = false;		locationMatrix[15][14] = false; 
		locationMatrix[15][4] = true; 		locationMatrix[15][15] = false; 
		locationMatrix[15][5] = true; 		locationMatrix[15][16] = false; 
		locationMatrix[15][6] = false; 		locationMatrix[15][17] = false; 
		locationMatrix[15][7] = false; 		locationMatrix[15][18] = false; 
		locationMatrix[15][8] = false; 		locationMatrix[15][19] = false; 
		locationMatrix[15][9] = false; 		locationMatrix[15][20] = false; 
		locationMatrix[15][10] = false;

		locationMatrix[16][0] = false;	 	locationMatrix[16][11] = false;
		locationMatrix[16][1] = false;		locationMatrix[16][12] = false; 
		locationMatrix[16][2] = false;		locationMatrix[16][13] = false; 
		locationMatrix[16][3] = true;		locationMatrix[16][14] = false; 
		locationMatrix[16][4] = false; 		locationMatrix[16][15] = false; 
		locationMatrix[16][5] = false; 		locationMatrix[16][16] = false; 
		locationMatrix[16][6] = true; 		locationMatrix[16][17] = false; 
		locationMatrix[16][7] = false; 		locationMatrix[16][18] = false; 
		locationMatrix[16][8] = false; 		locationMatrix[16][19] = false; 
		locationMatrix[16][9] = false; 		locationMatrix[16][20] = false; 
		locationMatrix[16][10] = false;

		locationMatrix[17][0] = false;	 	locationMatrix[17][11] = false;
		locationMatrix[17][1] = false;		locationMatrix[17][12] = false; 
		locationMatrix[17][2] = false;		locationMatrix[17][13] = false; 
		locationMatrix[17][3] = false;		locationMatrix[17][14] = false; 
		locationMatrix[17][4] = true; 		locationMatrix[17][15] = false; 
		locationMatrix[17][5] = false; 		locationMatrix[17][16] = false; 
		locationMatrix[17][6] = false; 		locationMatrix[17][17] = false; 
		locationMatrix[17][7] = true; 		locationMatrix[17][18] = false; 
		locationMatrix[17][8] = false; 		locationMatrix[17][19] = false; 
		locationMatrix[17][9] = false; 		locationMatrix[17][20] = false; 
		locationMatrix[17][10] = false;

		locationMatrix[18][0] = false;	 	locationMatrix[18][11] = false;
		locationMatrix[18][1] = false;		locationMatrix[18][12] = false; 
		locationMatrix[18][2] = false;		locationMatrix[18][13] = false; 
		locationMatrix[18][3] = false;		locationMatrix[18][14] = false; 
		locationMatrix[18][4] = false; 		locationMatrix[18][15] = false; 
		locationMatrix[18][5] = true; 		locationMatrix[18][16] = false; 
		locationMatrix[18][6] = false; 		locationMatrix[18][17] = false; 
		locationMatrix[18][7] = false; 		locationMatrix[18][18] = false; 
		locationMatrix[18][8] = true; 		locationMatrix[18][19] = false; 
		locationMatrix[18][9] = false; 		locationMatrix[18][20] = false; 
		locationMatrix[18][10] = false;

		locationMatrix[19][0] = false;	 	locationMatrix[19][11] = false;
		locationMatrix[19][1] = false;		locationMatrix[19][12] = false; 
		locationMatrix[19][2] = false;		locationMatrix[19][13] = false; 
		locationMatrix[19][3] = false;		locationMatrix[19][14] = false; 
		locationMatrix[19][4] = false; 		locationMatrix[19][15] = false; 
		locationMatrix[19][5] = false; 		locationMatrix[19][16] = false; 
		locationMatrix[19][6] = true; 		locationMatrix[19][17] = false; 
		locationMatrix[19][7] = true; 		locationMatrix[19][18] = false; 
		locationMatrix[19][8] = false; 		locationMatrix[19][19] = false; 
		locationMatrix[19][9] = false; 		locationMatrix[19][20] = false; 
		locationMatrix[19][10] = false;

		locationMatrix[20][0] = false;	 	locationMatrix[20][11] = false;
		locationMatrix[20][1] = false;		locationMatrix[20][12] = false; 
		locationMatrix[20][2] = false;		locationMatrix[20][13] = false; 
		locationMatrix[20][3] = false;		locationMatrix[20][14] = false; 
		locationMatrix[20][4] = false; 		locationMatrix[20][15] = false; 
		locationMatrix[20][5] = false; 		locationMatrix[20][16] = false; 
		locationMatrix[20][6] = false; 		locationMatrix[20][17] = false; 
		locationMatrix[20][7] = true; 		locationMatrix[20][18] = false; 
		locationMatrix[20][8] = true; 		locationMatrix[20][19] = false; 
		locationMatrix[20][9] = false; 		locationMatrix[20][20] = false; 
		locationMatrix[20][10] = false;
	}

	public int getPlayerPosition( int playerNumber )
	{
		return playerPosition[playerNumber];
	}
	
	public void setPlayerPosition( int playerNumber, int playerNewPosition )
	{
		this.playerPosition[playerNumber] = playerNewPosition;
	}

	public ArrayList<Integer> getPossiblePositions( int playerCurrentPosition )
	{
		ArrayList<Integer> possiblePositionsArray = new ArrayList<Integer>();

		for( int i = 0; i < 21; i++ )
		{
			if( this.locationMatrix[playerCurrentPosition][i] )
			{
				possiblePositionsArray.add( i );
			}
		}	 

		return possiblePositionsArray;
	}

	public ArrayList<Integer> getValidPositions( ArrayList<Integer> possiblePositionsArray )
	{
		ArrayList<Integer> validPositionsArray = new ArrayList<Integer>();

		for( int possiblePosition: possiblePositionsArray )
		{
			Location temp = this.locationArray[possiblePosition];
		
			if( ( !temp.getIsOccupied() ) || ( !temp.getIsHallway() ) )
			{
				validPositionsArray.add( possiblePosition );
			}
		}

		return validPositionsArray;
	}

	public void displayStatus()
	{
		String positionName;

		for( int i = 0; i < 6; i++ )
		{
			positionName = this.locationArray[playerPosition[i]].getLocationName();

			System.out.println( "Player " + ( i+1 ) + " is at " + positionName + "." );
		}
	}		
}
