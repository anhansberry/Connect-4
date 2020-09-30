
public class GameManager 
{
	private int[][] gameBoard;
	boolean isHumanPlayerTurn; // excessively long name makes purpose clear
	private Player player1;
	private boolean isFinished;
	int gameState;
	private Player player2; // player2 is an AIPlayer but is held as a Player for polymorphism
	public GameManager(String playerName, int firstTurn)
	{
		player1 = new Player(playerName, 1); // a player1 piece is represented by "1" in the array
		// set AI class being used 
		player2 = new MinimaxPlayer("AI", 2); // a player 2 piece is represented by "2" in the array
		//player2 = new AdvancedAIPlayer("AI",2); 
		//player2 = new AIPlayer("AI",2);
		isFinished = false;
		gameBoard = new int[7][6]; // gameboard, 7 wide, 6 high. Default values should be zero
		gameState = 0;
		if(firstTurn%2 == 0)
		{
			isHumanPlayerTurn = true;
		}
		else
		{
			isHumanPlayerTurn = false;
			AITurn();
		}
	}
	public boolean isHumanPlayerTurn()
	{
		return isHumanPlayerTurn;
	}
	
	public Player currentPlayer()
	{
		if (isHumanPlayerTurn())
		{
			return player1;
		}
		else
		{
			return player2;
		}
	}
	public int[][] getGameBoard()
	{
		return gameBoard;
	}
	
	public void updateGameState() // places the move at the proper place
	{
		Player currentPlayer = currentPlayer();
		int column = currentPlayer.getMove();
		// iterates through the column to find the right spot for the piece
		if(column != -1)
		{
			boolean flag = true;
			int i = 0;
			while (flag)
			{
				if (gameBoard[column][i] == 0) // checks if empty
				{
					gameBoard[column][i] = currentPlayer.getNumber();
					flag = false;
				}
				if (i >= 5) // makes sure to end if the top of the column is reached
				{
					flag = false;
				}
				i++;
			}
		}
		// checks if board is filled
		boolean isFilled = true;
		for(int i =0; i <= 6; i++)
		{
			if(gameBoard[i][5]== 0)
			{
				isFilled = false;
			}
		}
		if(isFilled)
		{
			gameState = 3;
			isFinished = true;
		}
		// checks if player 1 or player2 won
		for(int i = 0; i < gameBoard.length; i++)
		{
			for(int j = 0; j < gameBoard[0].length; j++)
			{
				if(gameBoard[i][j] != 0)
				{
					int diagonal = 0;
					int vertical = 0;
					int horizontal = 0;
					int diagonal2 =0;
					for(int t = 0; t < 4; t++)
					{
						
						if(j <= 2)
						{
							if(gameBoard[i][j+t] == gameBoard[i][j])
							{
								vertical += 1;
							}
						}
						
						if(i <= 3)
						{
							if(gameBoard[i+t][j] == gameBoard[i][j])
							{
								horizontal += 1;
							}
						}
						
						if(j <=2 && i <= 3)
						{
							if(gameBoard[i+t][j+t] == gameBoard[i][j])
							{
								diagonal += 1;
							}
						}	
						// Changed the condition 
						if(j >= 3  && i <= 3)
						{
							if(gameBoard[i+t][j-t] == gameBoard[i][j]) // changed the incrementation
							{
								diagonal2 += 1;
							}
						}	
					}
					if(vertical == 4 || horizontal == 4 || diagonal == 4 || diagonal2 == 4)
					{
						if(isHumanPlayerTurn())
						{
							gameState = 1;
						}
						else
						{
							gameState =2;
						}
						isFinished = true;
					}
				}
			}

		}
	}
	
	public void playerTurn(int column) // called when human player chooses a move location, has both players take their turns
	{
		player1.setNextMove(column);
		// calculate effects of human player's move
		updateGameState();
		// isHumanPlayerTurn = false;
		//it doest make sense to have this here because the funcion returns void
	}
	public void AITurn()
	{
		player2.makeMove(gameBoard);
		// calculate effects of AI player's move
		updateGameState();
		// isHumanPlayerTurn = true;
		//It doest make sense to have this here because the funcion returns void
	}
	public boolean isValidMove(int column) // determines if a move can be made by checking whether the topmost slot is open
	{
		if (column > 6)
		{
			return false;
		}
		if(gameBoard[column][5] == 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean isFinished()
	{
		return isFinished;
	}
	
	public int getGameState() //returns 0 for no, 1 for player1 victory, 2 for player2 victory, and 3 for tie
	{
		return gameState;
	}
	public Player getPlayer1()
	{
		return player1;
	}
	public Player getPlayer2()
	{
		return player2;
	}
}
