package mancala2;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Driver {
	int currentMancalaPlayer;
	Board mancalaBoard;
	Agent[] agent_name;	
	int boardReshuffleInArray=0;
	Driver(String firstPlayerName, String secondPlayerName,int random_shuffle) 
	{
		currentMancalaPlayer = 0;
		boardReshuffleInArray=random_shuffle;///Choose random which player will make the 1st move
		mancalaBoard = new Board();
		mancalaBoard.initial();///initializing the board for the game
		agent_name = new Agent[2];///Total 2 player
		agent_name[0] = new Agent(firstPlayerName, 0);///1st player initializing as player 0
		agent_name[1] = new Agent(secondPlayerName, 1);///2nd player initializing as player 1
	}
	public void playMancala() throws IOException 
	{
		displayMancalaBoardConsole(); ///inital state of the board print
		while (!mancalaBoard.isGameOver()) ///isGameOver() was declare at board.java to check if the game is over
		{
			String playerNamefromArray;
			if(currentMancalaPlayer==0)///Check which player is playing and find their name
			{
				playerNamefromArray=agent_name[0].getName();	
			}else {
				playerNamefromArray=agent_name[1].getName();	
			}
			int pitNum=0;///initiatize pit no
			int setPitNumber = agent_name[currentMancalaPlayer].selectTheMove(mancalaBoard); ////select move for each agent
												///which move is appropiate for which agent and when that is declared in agent class
																							
			if(boardReshuffleInArray==1) {
				pitNum=BoardNoSelect(setPitNumber,currentMancalaPlayer);////For human agent, pitNum is modified from the user input to the actual board index
																		///set that index no
			}else {
				pitNum=setPitNumber;///set pit index no
			}
			 System.out.println(playerNamefromArray + " moved from " + setPitNumber); ///print last move
			 
			boolean checkgoAgainCall = mancalaBoard.doPitsTheMove(currentMancalaPlayer, pitNum);////check If the last piece player droped is in an empty hole
																								///on His/her side
			displayMancalaBoardConsole();///print the board

			if (checkgoAgainCall)
			{	
				//If the last piece player drop is in own Mancala ,player get a free turn.
				System.out.println(playerNamefromArray + "\'s turn again");				
			}else
			{
				if (currentMancalaPlayer == 0) 	//otherwise players turn
					currentMancalaPlayer = 1;
				else
					currentMancalaPlayer = 0;
			}
		}
			
		System.out.println("************* Game Over*************");


		if (mancalaBoard.stonesInMancala(0) > mancalaBoard.stonesInMancala(1)){						
			System.out.println(agent_name[0].getName() + " scores " +mancalaBoard.stonesInMancala(0));
			System.out.println(agent_name[1].getName() + " scores " +mancalaBoard.stonesInMancala(1));
			System.out.println(agent_name[0].getName() + " wins");
		}
		else if (mancalaBoard.stonesInMancala(0) < mancalaBoard.stonesInMancala(1)) {
			System.out.println(agent_name[0].getName() + " scores " +mancalaBoard.stonesInMancala(0));
			System.out.println(agent_name[1].getName() + " scores " +mancalaBoard.stonesInMancala(1));
			System.out.println(agent_name[1].getName() + " wins");
		}
		else {
			
			System.out.println("Tie");
		}

	}
	////Board no select upon user input on the other side. left to right--->1 to 6
	public int BoardNoSelect(int userInputasPit,int currentPlayer)
	{
		int newPits = 0;
		if(currentPlayer==0) {
			if(userInputasPit==6)
				newPits= 1;
			else if(userInputasPit==5)
				newPits= 2;
			else if (userInputasPit==4)
				newPits= 3;
			else if (userInputasPit==3)
				newPits= 4;
			else if (userInputasPit==2)
				newPits= 5;
			else if (userInputasPit==1)
				newPits= 6;
		}else {
			newPits=userInputasPit;
		}
		return newPits;

	}
	private void displayMancalaBoardConsole() 
	{
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("\n");
		String sepraterMancalaLineFiller = "";
		System.out.println("_____________________________________\n");
		System.out.print("      ");

		for (int i = 1; i <= Board.playingPits; i++) 
		{
			System.out.print(mancalaBoard.stonesInPit(1, i) + "    ");
			sepraterMancalaLineFiller += "     ";
		}
		show(1); ////computers pits print
		System.out.print(mancalaBoard.stonesInMancala(1) + "    "); 
		System.out.print(sepraterMancalaLineFiller);
		System.out.println(mancalaBoard.stonesInMancala(0));

		System.out.print("      ");

		for (int i = Board.playingPits; i >= 1; i--)
			System.out.print(mancalaBoard.stonesInPit(0, i) + "    "); 
		show(0); ////humans pits print
		System.out.println("_____________________________________");
	}

	private void show(int mancalaPlayerNum) 
	{
		if (currentMancalaPlayer == mancalaPlayerNum){  
			System.out.print("        -->> ");  ////show active player
		}else {
			System.out.print("             "); 
		}
		System.out.println(agent_name[mancalaPlayerNum].getName());
	}

	public static void main(String[] args) throws IOException 
	{
		Random random = new Random();
		int turn = random.nextInt(2); /// 1st trun is randomly chosen
		Driver playGameMancals = new Driver("Human", null, turn);///null means machine agent
		playGameMancals.playMancala(); ///call play function to start the game
	}
}
