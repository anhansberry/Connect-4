
public class AIPlayer extends Player
{

	public AIPlayer(String name, int number) 
	{
		super(name, number);
	}
	
	public void makeMove(int[][] gameBoard)
	{
		/*
		 * this is where the AI algorithm goes
		 * currently just chooses random column
		 */
		boolean flag = true;
		int move = 0;
		while (flag)
		{
			move = (int) (Math.random()*7);
			if(gameBoard[move][5] == 0)
			{
				flag = false;
			}
		}
		setNextMove(move);
		
		/*try {
		    Thread.sleep(1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}*/
	}
}
