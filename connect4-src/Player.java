
public class Player 
{
	private String _name;
	private int _number;
	// 0-6 indicate column number, -1 indicates no move;
	private int _move;
	
	public Player(String name, int number)
	{
		_name = name;
		_number = number;
		_move = -1;
	}
	public void makeMove(int[][] gameBoard)
	{
		// this is where an AI algorithm would be in AIPlayer 
	}
	public void setNextMove(int columnNumber)
	{
		_move = columnNumber;

	}
	public int getMove()
	{
		return _move;
	}
	
	public int getNumber()
	{
		return _number;
	}
	public String getName()
	{
		return _name;
	}

	public String toString(){
		return "Name: "+ this._name+ "\nMove: "+ this._move + "\nNumber: "+ this._number; // added to string
	}

}
