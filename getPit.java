package mancala2;

public class getPit {
int stones;
	
	getPit() 
	{
		this.stones = 0;
	}
	public void addStones(int stones) 
	{
		this.stones += stones;
	}

	public int removeStones() {
		int stones = this.stones;
		this.stones = 0;
		return stones;
	}
	public int getStones() 
	{
		return stones;
	}
}
