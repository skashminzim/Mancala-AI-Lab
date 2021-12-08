package mancala2;
public class Board {
	getPit[] pits;
	int stone;
	static final int playingPits = 6, totalPits = 14;
	//two rows of six playing pits and one extra hole for each player to store and score. so, 6+6+1+1=14
	Board() 
	{
		pits = new getPit[totalPits];
		
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			pits[pitNum] = new getPit();
	}
	public void initial() 
	{
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			if (pitNum % (playingPits + 1) != 0) //pitNum % (playingPits + 1) ==0 when the pit is the mancala pit.
													//Initially there is no score. so, it is empty initially
				pits[pitNum].addStones(4);//add 4 stone to each pit
	}
	public int stonesInMancala(int playerNum) 
	{
		return pits[getMancala(playerNum)].getStones();
	}

	public int stonesInPit(int playerNum, int pitNum) 
	{
		return pits[getPitNum(playerNum, pitNum)].getStones();
	}

	private int getPitNum(int playerNum, int pitNum) 
	{
		return playerNum * (playingPits + 1) + pitNum; /// As for computer, pit no is modified for easy use.
														///0*6+4=4; 1*6+4=10 //pitNum is according to user input. 
																			//Here returned value is actual index
	}

	private int getMancala(int playerNum) 
	{
		return playerNum * (playingPits + 1);
	}

	public Board makeACopyOfMancalaBoard() ///called in machine agent
	{
		Board newBoard = new Board();
		for (int pitNum = 0; pitNum < totalPits; pitNum++)
			newBoard.pits[pitNum].addStones(this.pits[pitNum].getStones());
		return newBoard;
	}
	private int getOppositePlayer(int playerNum) 
	{
		if (playerNum == 0)
			return 1;
		else
			return 0;
	}
	private int oppositePitNum(int pitNum) 
	{
		return totalPits - pitNum;
	}
	public boolean doPitsTheMove(int currentPlayerNum, int chosenPitNum) 
	{
		boolean isPitesGetCalled=false; //to mark the initial pit
		int pitNum = getPitNum(currentPlayerNum, chosenPitNum);
		int stones = pits[pitNum].removeStones();
		while (stones != 0) 
		{
			pitNum = pitNum - 1;
			if (pitNum < 0)
				pitNum = totalPits - 1;
			if (pitNum != getMancala(getOppositePlayer(currentPlayerNum))) 
			{
				pits[pitNum].addStones(1);
				stones--;
			}
			isPitesGetCalled=true;
		}
		//check if player choose stone from empty pit. isPitesGetCalled variable is false only in initial pit
		if(stones==0 && isPitesGetCalled==false) {
		
			System.out.println("Invalid! \nYou cannot select Pits without any stones in it (Pits=0). So please choose again.");
			return true;
		}
		
		if (pitNum == getMancala(currentPlayerNum))///check if last index is mancala
			return true; //retuirn true means this player will again get a turn
	//// If the last piece Player drop is in an empty hole on His/her side, Player capture that piece and any pieces in the hole directly opposite.
		if (pitNum / (playingPits + 1) == currentPlayerNum && pits[pitNum].getStones() == 1) //check if final pit is actually own pit and if it's empty
		{
			stones = pits[oppositePitNum(pitNum)].removeStones();///Remove opponents stone
			pits[getMancala(currentPlayerNum)].addStones(stones);///put that stone in own mancala
		}
		return false;
	}
	public boolean isGameOver() 
	{
		for (int player = 0; player < 2; player++) ///choose player
		{
			int stones = 0; //total temp stone number initializing 0 for each player
			for (int pitNum = 1; pitNum <= playingPits; pitNum++)///check each pit for the player
				stones += pits[getPitNum(player, pitNum)].getStones();/// store totat stone number
			if (stones == 0)////check empty
				return true;
		}
		return false;
	}


}

