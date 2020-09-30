import java.util.ArrayList;
import java.util.Collections;


public class MinimaxPlayer extends AIPlayer
{
	private int[][] tempBoard;
	public static int PLAYER_WIN = 10000000; // not the max and min values so that closer values can be considered better
	public static int OPPONENT_WIN = -10000000;
	public static int CONTINUE = 9999; // some constant. value doesn't matter

	public MinimaxPlayer(String name, int number) 
	{
		super(name, number);

	}
	public void makeMove(int[][] gameBoard)
	{
		setTempBoard(gameBoard.clone());
		Move toMake = miniMax(true, Integer.MIN_VALUE, Integer.MAX_VALUE, 8);
		int move = toMake.getLocation();
		setNextMove(move);
	}
	// recursive minimax
	// based on code from video but modified for connect4 performance
	public Move miniMax(boolean aiTurn, int alpha, int beta, int depth) 
	{
		Move myMove = new Move(0);
		Move reply;
		int check = checkVictory(aiTurn);
		if (check != CONTINUE)
		{
			Move toReturn = new Move(0);
			toReturn.setHeuristic(check*(depth+1)); // prefers values higher in tree to increase accuracy
			return toReturn;
		}
		else if(depth<= 0)
		{
			Move toReturn = new Move(0);
			int newValue = calculateHeuristic();
			toReturn.setHeuristic(newValue);
			return toReturn;
		}
		if(aiTurn)
		{
			myMove.setHeuristic(alpha);
		}
		else
		{
			myMove.setHeuristic(beta);
		}
		// randomize moves to ensure the algorithm doesnt prefer the right side of the board
		ArrayList<Integer> moveArray = new ArrayList<Integer>();
		for(int i = 0; i < 7; i++)
		{
			moveArray.add(i);
		}
		Collections.shuffle(moveArray);
		for (Integer a : moveArray)
		{
			int move = a.intValue();
			if(tempBoard[move][5]==0)
			{
				tempMove(move, aiTurn);
				reply = miniMax(!aiTurn, alpha, beta, depth-1);
				unTempMove(move);
				if((aiTurn && (reply.getHeuristic() > myMove.getHeuristic())))
				{
					myMove.setLocation(move);
					myMove.setHeuristic(reply.getHeuristic());
					alpha = reply.getHeuristic();
				}
				else if(!aiTurn && (reply.getHeuristic() < myMove.getHeuristic()))
				{
					myMove.setLocation(move);
					myMove.setHeuristic(reply.getHeuristic());
					beta = reply.getHeuristic();
				}
				if(alpha>=beta)
				{
					return myMove;
				}
			}
		}
		return myMove;
	}

	public int checkVictory(boolean isAITurn)
	{
		// checks if board is filled
		boolean isFilled = true;
		for(int i =0; i <= 6; i++)
		{
			if(tempBoard[i][5]== 0)
			{
				isFilled = false;
			}
		}
		if(isFilled)
		{
			return 0;
		}
		// checks if player 1 or player2 won
		int playerValue = 1;
		if (isAITurn)
		{
			playerValue = 2;
		}
		for(int i = 0; i < tempBoard.length; i++)
		{
			for(int j = 0; j < tempBoard[0].length; j++)
			{
				if(tempBoard[i][j] == playerValue)
				{
					int diagonal = 0;
					int vertical = 0;
					int horizontal = 0;
					int diagonal2 =0;
					for(int t = 0; t < 4; t++)
					{

						if(j <= 2)
						{
							if(tempBoard[i][j+t] == tempBoard[i][j])
							{
								vertical += 1;
							}
						}

						if(i <= 3)
						{
							if(tempBoard[i + t][j] == tempBoard[i][j])
							{
								horizontal += 1;
							}
						}

						if(j <=2 && i <= 3)
						{
							if(tempBoard[i+t][j+t] == tempBoard[i][j])
							{
								diagonal += 1;
							}
						}	
						// Changed the condition 
						if(j >= 3  && i <= 3)
						{
							if(tempBoard[i+t][j-t] == tempBoard[i][j]) // changed the incrementation
							{
								diagonal2 += 1;
							}
						}	
					}
					if(vertical == 4 || horizontal == 4 || diagonal == 4 || diagonal2 == 4)
					{
						if(isAITurn)
						{
							return PLAYER_WIN;
						}
						else
						{
							return OPPONENT_WIN;
						}
					}
				}
			}
		}
		return CONTINUE;
	}


	public void setTempBoard(int[][] newBoard)
	{
		tempBoard = copyBoard(newBoard);
	}
	public int[][] copyBoard(int[][] oldBoard)
	{
		int[][] newBoard = new int[oldBoard.length][oldBoard[0].length];
		for(int i = 0; i < newBoard.length; i++)
		{
			for(int j = 0; j < newBoard[0].length; j++)
			{
				newBoard[i][j] = oldBoard[i][j];
			}
		}
		return newBoard;
	}
	public void tempMove(int move, boolean isAITurn)
	{
		if(move != -1)
		{
			boolean flag = true;
			int i = 0;
			while (flag)
			{
				if (tempBoard[move][i] == 0) // checks if empty
				{
					int playerNum = 1;
					if(isAITurn)
					{
						playerNum = 2;
					}
					tempBoard[move][i] = playerNum;
					flag = false;
				}
				if (i >= 5) // makes sure to end if the top of the column is reached
				{
					flag = false;
				}
				i++;
			}
		}
	}
	public void unTempMove(int move)// make note that if the grid is filled when tempMove Function is called, this will still remove a piece
	{
		if(move != -1)
		{
			boolean flag = true;
			int i = 5;
			while (flag)
			{
				if (tempBoard[move][i] != 0) // checks if empty
				{
					tempBoard[move][i] = 0;
					flag = false;
				}
				if (i <= 0) // makes sure to end if the top of the column is reached
				{
					flag = false;
				}
				i--;
			}
		}
	}
	int heuristic(int playerNum)
	{
		int[][] board = copyBoard(tempBoard);
		int ones = 1;
		int twos = 1;
		int threes = 1;
		int fours = 1;
		for(int col = 0; col < 7; col++)
		{
			for(int row = 0; row < 6; row++)
			{
				if(board[col][row] == playerNum)
				{
					// first calculates how many in each direction
					int UL = 0;
					int U =0;
					int UR = 0;
					int L =0;
					int R = 0;
					int D =0;
					int DL = 0;
					int DR =0;
					// UL
					boolean flag = true;
					int j = 1; 
					while(flag) 
					{
						if((col - j >= 0) && (row + j <= 5))
						{
							if (board[col- j][row+ j] == playerNum)
							{
								UL++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// U
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(row + j <= 5)
						{
							if (board[col][row+ j] == playerNum)
							{
								U++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// UR
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col + j <= 6) && (row + j <= 5))
						{
							if (board[col + j][row+ j] == playerNum)
							{
								UR++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// L
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(col - j >= 0)
						{
							if (board[col - j][row] == playerNum)
							{
								L++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					//R
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(col + j <= 6)
						{
							if (board[col + j][row] == playerNum)
							{
								R++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// DL
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col - j >= 0) && (row - j >= 0))
						{
							if (board[col - j][row - j] == playerNum)
							{
								DL++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// D
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(row - j >= 0)
						{
							if (board[col][row - j] == playerNum)
							{
								D++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// DR
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col + j <= 6) && (row - j >= 0))
						{
							if (board[col + j][row - j] == playerNum)
							{
								DR++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// now calculates whether a connect4 is attainable in that row (C for check)
					int CUL = 0;
					int CU =0;
					int CUR = 0;
					int CL =0;
					int CR = 0;
					int CD =0;
					int CDL = 0;
					int CDR =0;
					// CUL
					 flag = true;
					j = 1; 
					while(flag) 
					{
						if((col - j >= 0) && (row + j <= 5))
						{
							if (board[col- j][row+ j] == playerNum || board[col- j][row+ j] == 0 )
							{
								CUL++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// CU
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(row + j <= 5)
						{
							if (board[col][row+ j] == playerNum || board[col][row+ j] == 0)
							{
								CU++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// CUR
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col + j <= 6) && (row + j <= 5))
						{
							if (board[col + j][row+ j] == playerNum || board[col+ j][row+ j] == 0 )
							{
								CUR++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag =false;
						}
					}
					// CL
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(col - j >= 0)
						{
							if (board[col - j][row] == playerNum|| board[col - j][row] == 0 )
							{
								CL++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					//CR
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(col + j <= 6)
						{
							if (board[col + j][row] == playerNum || board[col + j][row] == 0)
							{
								CR++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// CDL
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col - j >= 0) && (row - j >= 0))
						{
							if (board[col - j][row - j] == playerNum || board[col - j][row - j] == 0)
							{
								CDL++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// CD
					flag = true;
					j = 1; 
					while(flag) 
					{
						if(row - j >= 0)
						{
							if (board[col][row - j] == playerNum || board[col][row - j] == 0 )
							{
								CD++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					// CDR
					flag = true;
					j = 1; 
					while(flag) 
					{
						if((col + j <= 6) && (row - j >= 0))
						{
							if (board[col + j][row - j] == playerNum || board[col + j][row - j] == 0)
							{
								CDR++;
							}
							else
							{
								flag = false;
							}
							j++;
						}
						else
						{
							flag = false;
						}
					}
					int up = U + D + 1;
					int cup = CU + CD + 1;
					if (cup >= 4)
					{
						switch(up)
						{
						case 1:
							ones++;
							break;
						case 2:
							twos++;
							break;
						case 3:
							threes++;
							break;
						case 4:
							fours++;
							break;
						case 5:
							fours++;
							break;
						case 6:
							fours++;
							break;
						default:
							break;
						}
					}
					int across = L + R + 1;
					int cacross = CL +CR +1;
					if (cacross >= 4)
					{
						switch(across)
						{
						case 1:
							ones++;
							break;
						case 2:
							twos++;
							break;
						case 3:
							threes++;
							break;
						case 4:
							fours++;
							break;
						case 5:
							fours++;
							break;
						case 6:
							fours++;
							break;
						default:
							break;
						}
					}
					int diagonal1 = DL +UR + 1;
					int cdiagonal1 = CDL +CUR +1;
					if (cdiagonal1 >= 4)
					{
						switch(diagonal1)
						{
						case 1:
							ones++;
							break;
						case 2:
							twos++;
							break;
						case 3:
							threes++;
							break;
						case 4:
							fours++;
							break;
						case 5:
							fours++;
							break;
						case 6:
							fours++;
							break;
						default:
							break;
						}
					}
					int diagonal2 = DR +UL + 1;
					int cdiagonal2 = CDR +CUL +1;
					if (cdiagonal2 >= 4)
					{
						switch(diagonal2)
						{
						case 1:
							ones++;
							break;
						case 2:
							twos++;
							break;
						case 3:
							threes++;
							break;
						case 4:
							fours++;
							break;
						case 5:
							fours++;
							break;
						case 6:
							fours++;
							break;
						default:
							break;
						}
					}
				}
			}
		}
		//fours = fours/4;
		//threes = threes/3;
		//twos = twos/2;
		//int heuristic = (int)(Math.pow(fours, 4+(fours/2)) + Math.pow(threes, 3 + (threes/3)) + Math.pow(twos, 2+(twos/4)) + ones);
		//heuristic = heuristic*(ones+twos+threes+fours);
		int heuristic1 = ones + 5*twos + 25*threes + 100*fours;
		int c3 = 0;
		for(int r = 0; r < 6; r++)
		{
			if (board[2][r] == playerNum )
			{
				c3++;
			}
		}
		int c4 = 0;
		for(int r = 0; r < 6; r++)
		{
			if (board[3][r] == playerNum )
			{
				c4++;
			}
		}
		int c5 =0;
		for(int r = 0; r < 6; r++)
		{
			if (board[4][r] == playerNum )
			{
				c5++;
			}
		}
		int heuristic2 = 2*c3 + 6*c4 + 2*c5;
		heuristic2 = heuristic2/6 + 1;
		return heuristic1*heuristic2;
	}
	int calculateHeuristic()
	{
		return heuristic(2) - heuristic(1);
	}


}
