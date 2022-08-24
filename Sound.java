import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import java.io.File;


public class Sound {
	//THIS IS ONLY FOR TESTING SOUND EFFECTS. NOT INVOLVED IN GAME.
	public static void main(String[] args)
	{
		File theme = new File("name");
		PlaySound(theme);
	}
	
	static void PlaySound(File Sound) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			clip.start();
			
			Thread.sleep(clip.getMicrosecondLength()/1000);
		}
		catch(Exception e){
			System.out.print("Failure to run");
		}
	}
}
