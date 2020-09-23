
public class Move 
{
	private int _location;
	private int _heuristic;
	public Move(int location)
	{
		_location = location;
	}
	public int getLocation()
	{
		return _location;
	}
	public int getHeuristic()
	{
		return _heuristic;
	}
	public void setHeuristic(int heuristic)
	{
		_heuristic = heuristic;
	}
	public void setLocation(int location)
	{
		_location = location;
	}
}
