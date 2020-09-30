
public class Move 
{
	private int currentLocation;
	private int currentHeuristic;
	public Move(int location)
	{
		currentLocation = location;
	}
	public int getLocation()
	{
		return currentLocation;
	}
	public int getHeuristic()
	{
		return currentHeuristic;
	}
	public void setHeuristic(int heur)
	{
		currentHeuristic = heuristic;
	}
	public void setLocation(int locat)
	{
		currentLocation = location;
	}
}
