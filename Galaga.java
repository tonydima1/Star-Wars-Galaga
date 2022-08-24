import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class Galaga extends JPanel
{
	public static void main(String [] args)
	{
		//step 1, create a frame
		JFrame frame = new JFrame("Galaga!");
		
		//step 2, create a panel, put it in frame
		GalagaPanel panel = new GalagaPanel();
		frame.getContentPane().add(panel);
		//frame.getContentPane().add(new Galaga());
		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panel.level = 0;
		//step 3, set frame size, make it visible
		frame.setSize(600,500);
		frame.setVisible(true);
		

	}
	
	//Sound testing function
	/*static void soundtester(File Sound, int play) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.setFramePosition(0);
		    if (play == 0) {
		    	clip.start();
		    }
		    else {
		    	clip.stop();
		    }
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}
		catch(Exception e){
			System.out.println("Failure to run");
		}*/
	
	

}