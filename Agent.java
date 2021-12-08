package mancala2;

import java.io.IOException;
import java.util.Scanner;
public class Agent {
	String Player;
	int mancalaPlayerNum;
	Agent(String name, int playerNum) 
	{
		this.Player = name;
		this.mancalaPlayerNum = playerNum;
	}

	public String getName() 
	{
		if (Player == null)
			return "Computer";
		else
			return Player;
	}

	public int selectTheMove(Board board) throws IOException
	{

		int pitNum = 0;
		/////////////Human agent
		if (Player != null) ////in Driver class, machine agent is assigned to null value
		{ 
			try {

				Scanner scanner = new Scanner(System.in);
				System.out.print("\nIt's "+ Player + "\'s turn!\nPlease insert a pit number ([1,6]) :->   "); // User Input section
				scanner:
					while(scanner.hasNext()) {
						if(scanner.hasNextInt()){
							pitNum = scanner.nextInt();
							if((pitNum>0 && pitNum<7))
							{
								//scanner.close();
								break scanner;/// end taking input and proceed
								
							}else {///in case of input out of range
								System.out.print( "ERROR: Invalid Input\nPlease select between ([1,6]) :->   ");
							}
							}
						else{///in case of non integer value						
							System.out.print( "Only integer between 1 to 6 is valid input.\nPlease insert a pit number ([1,6]) :->   ");
							scanner.next();///take valid input again
						}
					
				}

			} catch (NumberFormatException ex) {
				System.err.println("Something wrong" + ex.getMessage()+"\n");
			}	
			return pitNum; 
		}
		else {
			////////////////Machine agent
						int bestMove = -1000; // best move initial neg infinity
						int maxNewStones = -1000; // temp variable to check best move neg infinity
						int rememberMove = -1000;//remember move to gain opponents stone neg infinity
			for (pitNum = 1; pitNum <= Board.playingPits; pitNum++)  
			{
				if (board.stonesInPit(mancalaPlayerNum, pitNum) != 0)//as no need to work on nonempty pits 
				{
					Board testBoard = board.makeACopyOfMancalaBoard(); 	  // Make a copy of the Board
					boolean save_for_now = testBoard.doPitsTheMove(mancalaPlayerNum, pitNum); ////possibility to catch oz stones																								
					if (save_for_now==true)
						rememberMove = pitNum; 
					// check how many stones this move added to our mancala.
					//testBoard.stonesInMancala(mancalaPlayerNum) represents the gathered stones in mancala for tested moves
					int newStones = testBoard.stonesInMancala(mancalaPlayerNum) - board.stonesInMancala(mancalaPlayerNum); 
					if (newStones > maxNewStones) ///max function to keep the best move to achieve high score
					{ 
						maxNewStones = newStones;
						bestMove = pitNum; 
					}
			}
			}
			if (maxNewStones > 1) //for gaining more than one score, return best possible move
				return bestMove;
			else if (rememberMove != -1000)//if more than stone not possible, then check if it can take opponents stone
				return rememberMove;
			else
				return bestMove; // last best possible way. 1 or 0 stone score gain
		
	}
	}
}
