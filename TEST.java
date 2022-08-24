	import javax.swing.*;
	import java.awt.*;
	import java.util.*;
	import java.awt.event.*;
	
	
public class TEST extends JPanel {
	
	
	ImageIcon Tie = new ImageIcon("/Users/tonyd/Downloads/Tie.png");
	
	public static void main(String[] args) 
	{
			JFrame frame = new JFrame("Galaga!");

		
			//GalagaPanel panel = new GalagaPanel();
			//frame.getContentPane().add(panel);
			frame.getContentPane().add(new TEST());
			frame.setBackground(Color.white);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(800,800);
			frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.magenta);
		g.drawRect(0,0,100,100);
		g.drawImage(Tie.getImage(),10,10,50,50,this);
		repaint();
	}
}
