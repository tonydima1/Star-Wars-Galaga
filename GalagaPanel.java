import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GalagaPanel extends JPanel implements KeyListener
{

	//images
    ImageIcon space, shippic, dumbalien, pred, seekpic;
	int level = 0;
	int startintro = 0;
	int firecheck = 0;
	int levelcomplete = 0;
	//YOU NEED TO GET YOUR FILE NAMES FOR SOUNDS HERE. SEE SOURCES IN READ ME FOR YOUTUBE LINKS. WAV FILE NEEDED.
	File theme = new File("/Users/USERNAME/Downloads/Star Wars Theme (2019 Remaster) [8 Bit Tribute to John Williams & Star Wars] - 8 Bit Universe.wav");
	File themeOVER = new File("/Users/USERNAME/Downloads/Star War's Imperial March [8 Bit Tribute to David Prowse (R.I.P.) & John Williams] - 8 Bit Universe.wav");
	File themeWON = new File("/Users/USERNAME/Downloads/Star Wars Theme (2019 Remaster) [8 Bit Tribute to John Williams & Star Wars] - 8 Bit Universe.wav");
	File Xwingfire = new File("/Users/USERNAME/Downloads/Star Wars X wing fighter blaster sound effect.wav");
	
	Clip clip;
	Clip clipOVER;
	Clip clipWON;
	Clip clipxwingfire;
	//list of all objects in game
	LinkedList<Alien> masterList;

	//ship aka player
	Ship ship;
	Bullet bullet, bulletA;
	
	int dead, listlength;
	
	GalagaPanel()
	{
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(theme));
			clip.setFramePosition(0);
			clipOVER = AudioSystem.getClip();
			clipOVER.open(AudioSystem.getAudioInputStream(themeOVER));
			clipOVER.setFramePosition(0);
			clipWON = AudioSystem.getClip();
			clipWON.open(AudioSystem.getAudioInputStream(themeWON));
			clipWON.setFramePosition(0);
			clipxwingfire = AudioSystem.getClip();
			clipxwingfire.open(AudioSystem.getAudioInputStream(Xwingfire));
			clipxwingfire.setFramePosition(0);
		    //if (level == 0) {
		    //	clip.start();
		    //}
		    //else {
		    //	clip.stop();
		    //}
			//Thread.sleep(clip.getMicrosecondLength()/1000);
		}
		catch(Exception e){
			System.out.println("Failure to run");
		}
		//YOU NEED TO PUT THE SPRITE IMAGES HERE
		//load images
		space = new ImageIcon("/Users/USERNAME/Downloads/deathstar.gif");
		shippic = new ImageIcon("/Users/USERNAME/Downloads/Xwing.png");
		dumbalien = new ImageIcon("/Users/USERNAME/Downloads/TieReg.png");
		pred = new ImageIcon("/Users/USERNAME/Downloads/TieInter.png");
		seekpic = new ImageIcon("/Users/USERNAME/Downloads/TieRed.png");

		//add a ship to the game
		ship = new Ship();
		ship.setPicture(shippic);
		ship.x = 200;
		ship.y = 400;
		//add some aliens to game
		masterList = new LinkedList<Alien>();
		
		
		bullet = new Bullet();
		bulletA = new Bullet();
		bulletA.a = 1;
		bulletA.x = -20;

		UpdateThread ut = new UpdateThread(this);
		ut.start();
		
		
		//check inputs
		addKeyListener(this);
		setFocusable(true);
	}


	public void paintComponent(Graphics g)
	{
		//clear screen
		g.drawImage(space.getImage(),0,0,getWidth(),getHeight(),this);

		//draw all objects in game
		for( Alien go : masterList )
		{
			go.draw(g,this);
			listlength++;
			go.bullet.draw(g, this);
			if(!go.alive)
				dead++;
		}
		ship.draw(g, this);
		bullet.draw(g, this);
		bulletA.draw(g, this);
		if(ship.alive == false)
		{
			g.setFont(new Font("serif", Font.BOLD, 32));
			g.setColor(Color.RED);
			g.drawString("GAME OVER!", 150, 250);
		}
		if(level == 0) {
			g.setFont(new Font("serif", Font.BOLD, 48));
			g.setColor(Color.YELLOW);
			g.drawString("STAR WARS", 150, 250);
		}
		/*    *^* CHANGE IF STATEMENT BELOW if(go.size() == 1 && level == 2) makes listlength useless.
		* Change other level transition if statements accordingly
		*/
		if(dead == listlength && level == 2)
		{
			System.out.println("Level 1 Complete!");
			levelcomplete++;
		}
		/* # Adding addition levels requires the top if statement to be copy pasted with the && level == 3
		 * make sure to increment level complete and change the statement below this to the highest level.
		 * That being your latest level with enemies plus one. In my case that is 3.
				*/
		if(dead == listlength && level == 3)
		{
			levelcomplete++;
			g.setFont(new Font("serif", Font.BOLD, 48));
			g.setColor(Color.BLUE);
			g.drawString("GAME WON!", 150, 250);
			clipWON.start();
		}
		else
		{
			listlength = 0;
			dead = 0;
		}
	}


	public void update()
	{
		
		if(level == 0 && startintro == 0) {
			clip.start();
			startintro = 1;
		}
		else if (level == 1) {
			clip.stop();
			
			/* IF LINKED LIST FOR ENEMIES BEING EMPTY CAUSES PROBLEMS ADD THIS. SEE *^* for other change
			Alien a = new Alien();
			a.setPicture(dumbalien);
			masterList.add(a);
			a.x = -100;
			a.y = -100;
			a.xtmp = -100;
			a.ytmp = -100;
			a.setclock = 100000;
			*/
			
			/* Adding aliens for level 1. can be looped or randomized with a few variables. 
			Allows for complete customization of enemy formations.*/
			//does not have to be separate names like a1 and a2. Just needed to individually modify values, to cause variation in movements.
			Alien a1 = new Alien();
			a1.setPicture(dumbalien);
			masterList.add(a1);
			a1.x = 100;
			a1.y = 20;
			a1.xtmp = 100;
			a1.ytmp = 20;
			//offset RNG launch chance so they deploy at separate intervals. Similar to waves of enemies. 
			a1.setclock = 50;
			
			Alien a2 = new Alien();
			a2.setPicture(dumbalien);
			masterList.add(a2);
			a2.x = 160;
			a2.y = 20;
			a2.xtmp = 160;
			a2.ytmp = 20;
			//give enemies similar offsets to have a chance of them attacking together. keep in 0-100 range for best results.
			a2.setclock = 50;
			
			Alien a3 = new Alien();
			a3.setPicture(dumbalien);
			masterList.add(a3);
			a3.x = 280;
			a3.y = 20;
			a3.xtmp = 280;
			a3.ytmp = 20;
			a3.setclock = 90;
			
			Alien a4 = new Alien();
			a4.setPicture(dumbalien);
			masterList.add(a4);
			a4.x = 340;
			a4.y = 20;
			a4.xtmp = 340;
			a4.ytmp = 20;
			a4.setclock = 90;
			
			
			Predator p1 = new Predator();
			p1.setPicture(pred);
			p1.setPrey(ship);
			masterList.add(p1);
			p1.x = 220;
			p1.y = 20;
			p1.xtmp = 220;
			p1.ytmp = 20;
			p1.setclock = 50;
			
			Alien a5 = new Alien();
			a5.setPicture(dumbalien);
			masterList.add(a5);
			a5.x = 120;
			a5.y = 70;
			a5.xtmp = 120;
			a5.ytmp = 70;
			a5.setclock = 30;
			
			Alien a6 = new Alien();
			a6.setPicture(dumbalien);
			masterList.add(a6);
			a6.x = 180;
			a6.y = 70;
			a6.xtmp = 180;
			a6.ytmp = 70;
			a6.setclock = 30;
			
			Alien a7 = new Alien();
			a7.setPicture(dumbalien);
			masterList.add(a7);
			a7.x = 300;
			a7.y = 70;
			a7.xtmp = 300;
			a7.ytmp = 70;
			a7.setclock = 50;
			
			Alien a8 = new Alien();
			a8.setPicture(dumbalien);
			masterList.add(a8);
			a8.x = 360;
			a8.y = 70;
			a8.xtmp = 360;
			a8.ytmp = 70;
			a8.setclock = 50;
			
			
			Predator p2 = new Predator();
			p2.setPicture(pred);
			p2.setPrey(ship);
			masterList.add(p2);
			p2.x = 240;
			p2.y = 70;
			p2.xtmp = 240;
			p2.ytmp = 70;
			p2.setclock = 20;
			
			Alien a9 = new Alien();
			a9.setPicture(dumbalien);
			masterList.add(a9);
			a9.x = 200;
			a9.y = 120;
			a9.xtmp = 200;
			a9.ytmp = 120;
			
			Alien a10 = new Alien();
			a10.setPicture(dumbalien);
			masterList.add(a10);
			a10.x = 140;
			a10.y = 120;
			a10.xtmp = 140;
			a10.ytmp = 120;
			a10.setclock = 20;
			
			Alien a11 = new Alien();
			a11.setPicture(dumbalien);
			masterList.add(a11);
			a11.x = 260;
			a11.y = 120;
			a11.xtmp = 260;
			a11.ytmp = 120;
			
			
			Predator p3 = new Predator();
			p3.setPicture(pred);
			p3.setPrey(ship);
			masterList.add(p3);
			p3.x = 80;
			p3.y = 120;
			p3.xtmp = 80;
			p3.ytmp = 120;
			
			Predator p4 = new Predator();
			p4.setPicture(pred);
			p4.setPrey(ship);
			masterList.add(p4);
			p4.x = 320;
			p4.y = 120;
			p4.xtmp = 320;
			p4.ytmp = 120;
			
			//seekers for reference. Do not use s1 unless modifying. 
			//Seeker s1 = new Seeker();
			//s1.setPicture(seekpic);
			//s1.setPrey(ship);
			//masterList.add(s1);
			//s1.x = 280;
			//s1.y = 20;
			//s1.xtmp = 280;
			//s1.ytmp = 20;
			
			//Another predator off screen
			//Predator p2 = new Predator();
			//p2.setPicture(pred);
			//p2.setPrey(ship);
			//masterList.add(p2);
			
			level = 2;
		}
		if (level == 2 && levelcomplete >= 1) {
			
			/* Adding aliens for level 2. Same as one. If you want more levels. Make another if statement,
			 * Level==3 && levelcomplete >= 2. See # for another change needed.
			 * */
			
			/*
			 * Example of loop spawning
			 * for(int i=0; i<3; i++)
			{
				Alien a = new Alien();
				a.setPicture(dumbalien);
				masterList.add(a);
				a.x = i*20;
				a.y = 20;
			}*/
				
			Alien a12 = new Alien();
			a12.setPicture(dumbalien);
			masterList.add(a12);
			a12.x = 100;
			a12.y = 20;
			a12.xtmp = 100;
			a12.ytmp = 20;
			a12.setclock = 90;
			
			//accidental addition
			/*Alien a13 = new Alien();
			a13.setPicture(dumbalien);
			masterList.add(a13);
			a13.x = 220;
			a13.y = 20;
			a13.xtmp = 220;
			a13.ytmp = 20;
			a13.setclock = 50;
			*/
			
			Seeker s1 = new Seeker();
			s1.setPicture(seekpic);
			s1.setPrey(ship);
			masterList.add(s1);
			s1.x = 160;
			s1.y = 20;
			s1.xtmp = 160;
			s1.ytmp = 20;
		
			Seeker s2 = new Seeker();
			s2.setPicture(seekpic);
			s2.setPrey(ship);
			masterList.add(s2);
			s2.x = 280;
			s2.y = 20;
			s2.xtmp = 280;
			s2.ytmp = 20;
			s2.setclock = 10;
			
			Alien a14 = new Alien();
			a14.setPicture(dumbalien);
			masterList.add(a14);
			a14.x = 340;
			a14.y = 20;
			a14.xtmp = 340;
			a14.ytmp = 20;
			a14.setclock = 90;
			
			
			Predator p5 = new Predator();
			p5.setPicture(pred);
			p5.setPrey(ship);
			masterList.add(p5);
			p5.x = 220;
			p5.y = 20;
			p5.xtmp = 220;
			p5.ytmp = 20;
			p5.setclock = 50;
			
			Alien a15 = new Alien();
			a15.setPicture(dumbalien);
			masterList.add(a15);
			a15.x = 120;
			a15.y = 70;
			a15.xtmp = 120;
			a15.ytmp = 70;
			a15.setclock = 30;
			
			Alien a16 = new Alien();
			a16.setPicture(dumbalien);
			masterList.add(a16);
			a16.x = 180;
			a16.y = 70;
			a16.xtmp = 180;
			a16.ytmp = 70;
			a16.setclock = 30;
			
			Alien a17 = new Alien();
			a17.setPicture(dumbalien);
			masterList.add(a17);
			a17.x = 300;
			a17.y = 70;
			a17.xtmp = 300;
			a17.ytmp = 70;
			a17.setclock = 50;
			
			Alien a18 = new Alien();
			a18.setPicture(dumbalien);
			masterList.add(a18);
			a18.x = 360;
			a18.y = 70;
			a18.xtmp = 360;
			a18.ytmp = 70;
			a18.setclock = 50;
			
			
			Predator p6 = new Predator();
			p6.setPicture(pred);
			p6.setPrey(ship);
			masterList.add(p6);
			p6.x = 240;
			p6.y = 70;
			p6.xtmp = 240;
			p6.ytmp = 70;
			p6.setclock = 20;
			
			Alien a19 = new Alien();
			a19.setPicture(dumbalien);
			masterList.add(a19);
			a19.x = 200;
			a19.y = 120;
			a19.xtmp = 200;
			a19.ytmp = 120;
			
			Alien a20 = new Alien();
			a20.setPicture(dumbalien);
			masterList.add(a20);
			a20.x = 140;
			a20.y = 120;
			a20.xtmp = 140;
			a20.ytmp = 120;
			a20.setclock = 20;
			
			Alien a21 = new Alien();
			a21.setPicture(dumbalien);
			masterList.add(a21);
			a21.x = 260;
			a21.y = 120;
			a21.xtmp = 260;
			a21.ytmp = 120;
			
			
			Predator p7 = new Predator();
			p7.setPicture(pred);
			p7.setPrey(ship);
			masterList.add(p7);
			p7.x = 80;
			p7.y = 120;
			p7.xtmp = 80;
			p7.ytmp = 120;
			
			Predator p8 = new Predator();
			p8.setPicture(pred);
			p8.setPrey(ship);
			masterList.add(p8);
			p8.x = 320;
			p8.y = 120;
			p8.xtmp = 320;
			p8.ytmp = 120;
			
			level = 3;
			}
		
		//update all objects in game
		for( Alien go : masterList )
		{
			go.update();
			if(bullet.intersects(go))
			{
				//bullet is attached to alien object so if an alien dies but it shot it would remove it. This fixes it for one alien only.
				//Will be improved. A quick 2 kills on 2 aliens that just shot would only see one bullet remaining. 
				//you will see this minor 'issue' in game
				if(go.bullet.x > 0 && go.bullet.y > 0) {
					bulletA.y = go.bullet.y;
					bulletA.x = go.bullet.x;
				}
				if(go instanceof Seeker == true) {
					bulletA.col = Color.MAGENTA;
				}
				else {
					bulletA.col = Color.GREEN;
				}
				go.kill();
				bullet = null;
				bullet = new Bullet();
				firecheck = 0;
			}
			
			if(go.intersects(ship) && !go.attribute.equalsIgnoreCase("ship"))
			{
				ship.kill();
				System.out.println(go.attribute + " killed you");
				clipOVER.start();
			}
			if(go.bullet.intersects(ship))
			{
				ship.kill();
				System.out.println("Shot by " + go.attribute);
				clipOVER.start();
			}
			if(bulletA.intersects(ship)) {
				ship.kill();
				System.out.println("Shot down");
				clipOVER.start();
			}
			
		}
		
		//check for if you went out of bounds. 
		if (ship.x >= 560 || ship.x <= -30) {
			ship.kill();
			System.out.println("Death by turbulence ");
			clipOVER.start();
		}
		//only one bullet fired at a time. Once it is out of bounds delete and reset bullet.
		if(bullet.y <= -20) {
			bullet = null;
			bullet = new Bullet();
			firecheck = 0;
		}
		//if(bulletA.y <= 500 && bullet.y >= 0) {
		//	bulletA.y = bulletA.y + 3;
		//}
		if(bulletA.y >= 500) {
			bulletA.y = -20;
			bulletA.x = -20;
		}
		bulletA.update();
		bullet.update();
		ship.update();
		
		repaint();
	}

	public void keyPressed(KeyEvent k)
	{
		char c = k.getKeyChar();
		
		if( k.getKeyCode() == KeyEvent.VK_RIGHT )
			ship.dx = 10;
		if( k.getKeyCode() == KeyEvent.VK_LEFT )
			ship.dx = -10;
		if(c == ' ' && level == 0) {
			level++;
		}
		else if(c == ' ' && firecheck == 0)
		{
			clipxwingfire.setFramePosition(0);
			clipxwingfire.start();
			bullet.x = ship.x + 20;
			bullet.y = ship.y - 10;
			firecheck = 1;
		}
		else if(c == ' ' && firecheck == 1){
			//nothing
		}
	}

	public void keyReleased(KeyEvent k)
	{
		if( k.getKeyCode() == KeyEvent.VK_LEFT ||  k.getKeyCode() == KeyEvent.VK_RIGHT )
			ship.dx = 0;
	}

	public void keyTyped(KeyEvent k)
	{
	}
}
