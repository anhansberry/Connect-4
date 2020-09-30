import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JOptionPane;


public class ConnectFourComponent extends JComponent
{
	GameManager gameManager;
	int gameNum;
	public ConnectFourComponent()
	{
		gameNum =0;
		gameManager = new GameManager(JOptionPane.showInputDialog("What is your name?"), 0);
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.drawRect(0, 20, 641, 551);
		g2.setColor(Color.YELLOW);
		g2.fillRect(1, 21, 640, 550);
		int[][] gameBoard = gameManager.getGameBoard();
		for(int i = 0; i < gameBoard.length; i++)
		{
			for(int j = 0; j < gameBoard[0].length; j++)
			{
				g2.setColor(Color.BLACK);
				g2.drawOval(9 + i * 90, 479 - j * 90, 81, 81);
				if(gameBoard[i][j] != 0)
				{
					if (gameBoard[i][j] == 1)
					{
						g2.setColor(Color.RED);
					}
					g2.drawOval(10 + i * 90, 480 - j * 90, 80, 80);
					g2.fillOval(10 + i * 90, 480 - j * 90, 80, 80);
				}
					else
					{
						g2.setColor(Color.WHITE);
						g2.fillOval(10 + i * 90, 480 - j * 90, 80, 80);
					}
			}
		}
		if(!gameManager.isFinished())
		{
			if(gameManager.currentPlayer().getNumber() == 1)
			{
				g2.setColor(Color.RED);
			}
			else
			{
				g2.setColor(Color.BLACK);
			}
			int id = gameManager.currentPlayer().getNumber();
			String name = gameManager.currentPlayer().getName();
			g2.drawString("Player " + id + ": " + name, 5, 15);
		}
		else
		{
			if(gameManager.getGameState()==1)
			{
				g2.setColor(Color.RED);
				g2.drawString( gameManager.getPlayer1().getName() + " has won!", 5, 15);
			}
			else if(gameManager.getGameState()==2)
			{
				g2.setColor(Color.BLACK);
				g2.drawString(gameManager.getPlayer2().getName()+ " has won!", 5, 15);
			}
			else if(gameManager.getGameState()==3)
			{
				g2.setColor(Color.BLACK);
				g2.drawString("Tie Game", 5, 15);
			}
		}
		
	}
		
	
	public void reset()
	{
		gameNum++;
		gameManager = new GameManager(gameManager.getPlayer1().getName(), gameNum);
		repaint();
	}
	
	public void select(int x)
	{
		int column = (int) (((double) x) / 90);
		if(gameManager.isValidMove(column))
		{
			gameManager.playerTurn(column);
		}
		
	}
}
