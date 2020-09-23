import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class ConnectFourViewer 
{
	public static void main(String args[])
	{
		final JFrame frame = new JFrame();
		frame.setSize(641,590);
		frame.setTitle("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		final ConnectFourComponent  component= new  ConnectFourComponent();
		class TurnListener implements MouseListener
		{
			
			public void mouseClicked(MouseEvent event)
			{
				if(!component.gameManager.isFinished())
				{
					if (component.gameManager.isHumanPlayerTurn())
					{
						int x = event.getX();
						component.select(x);
						component.repaint();
						if(!component.gameManager.isFinished())
						{
							component.gameManager.AITurn();
							component.repaint();
							
						}
					}
					
				}
				else
				{
					component.reset();
				}
			}
			public void mouseEntered(MouseEvent event){}
			public void mouseExited(MouseEvent event){}
			public void mousePressed(MouseEvent event){}
			public void mouseReleased(MouseEvent event){}
		}
		component.addMouseListener(new TurnListener());
		frame.add(component);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
