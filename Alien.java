import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Alien extends GameObject
{
	Random dice;
	int dx, dy, xtmp, ytmp;
	int setclock = 0;
	int cnt = 0;
	int rand = 1;
	int attackchance = 0;
	int move = 0;
	int tick = 0;
	int xtick = 0;
	int dyattack = 10;
	int dxattack = 2;
	int fire = 0;
	int attacking = 0;
	boolean shot;
	
	File attack = new File("/Users/USERNAME/Downloads/TIE Fighter roar.wav");
	File fire1 = new File("/Users/USERNAME/Downloads/Tie Fighter Firing laser (Star wars) - Sound Effect.wav");
	File fire2 = new File("/Users/USERNAME/Downloads/Star Wars Tie fighter blaster sound effect.wav");
	File firemissile = new File("/Users/USERNAME/Downloads/Star Wars Ion Cannon Sound Effect.wav");
	Clip clipmissile;
	Clip clipattack;
	Clip clipfire1;
	Clip clipfire2;
	
	Bullet bullet;
	
	Alien()
	{
		try {
			clipattack = AudioSystem.getClip();
			clipattack.open(AudioSystem.getAudioInputStream(attack));
			//clipattack.setFramePosition(10);
			clipfire1 = AudioSystem.getClip();
			clipfire1.open(AudioSystem.getAudioInputStream(fire1));
			//clipfire1.setFramePosition(0);
			clipfire2 = AudioSystem.getClip();
			clipfire2.open(AudioSystem.getAudioInputStream(fire2));
			//clipfire2.setFramePosition(0);
			clipmissile = AudioSystem.getClip();
			clipmissile.open(AudioSystem.getAudioInputStream(firemissile));
			
		}
		catch(Exception e){
			System.out.println("Failure to run");
		}
		dice = new Random();
		x = 0;
		y = 0;
		dx = 1;
		dy = 1;
		attribute = "alien";
		bullet = new Bullet();
		bullet.y = -10;
		shot = false;
		
	}

	public void update()
	{		
		cnt++;
		//modular for attack motion.
		if(cnt%(100+setclock) == 0) {
			attackchance = dice.nextInt(3);
			if(attackchance == 0) {
				move = 0;
			}
			else if (attackchance == 2){
				move = 2;
				xtick = 0;
			}
			else {
				move = 3;
				xtick = 0;
			}
		}
		if(move == 2) {
			//xtmp = x;
			//ytmp = y;
			y += dyattack;
			if (xtick < 30) {
				x += dxattack;
				xtick++;
				if(xtick == 15) {
					rand = 0;
				}
				if(xtick >= 30) {
					xtick = 60;
				}
			}
			else{
				x -= dxattack;
				xtick--;
			}
			
			
			if (y > 500) {
				y = -50;
			}
			if(x > 600) {
				x = 0;
			}
			if (x < -30) {
				x = 600;
			}
			if(y == ytmp) {
				x = xtmp;
				move = 0;
			}
		}
		else if (move == 3) {
			//xtmp = x;
			//ytmp = y;
			y += dyattack;
			if (xtick < 30) {
				x -= dxattack;
				xtick++;
				if(xtick == 15) {
					rand = 0;
				}
				if(xtick >= 30) {
					xtick = 60;
				}
			}
			else{
				x += dxattack;
				xtick--;
			}
			
			
			if (y > 500) {
				y = -50;
			}
			if(x > 600) {
				x = 0;
			}
			if (x < -30) {
				x = 600;
			}
			if(y == ytmp) {
				x = xtmp;
				move = 0;
			}
		}
		else if(move == 0)
		{
			attacking = 0;
			x += dx;
			y += dy;
			//y += dy;
			if(x > 600) {
				x = 0;
			}
			if (tick >= 15) {
				move = 1;
			}
			
			tick++;
			//if(y > 600) {
			//	y = 0;
			//}
		}
		else
		{
			x -= dx;
			y -= dy;
			if (tick <= 0) {
				move = 0;
			}
			tick--;
		}	
		
		if(alive)
		{
			bullet.makeColor();
			if(rand == 0 && !shot)
			{
				fire = dice.nextInt(2);
				if (fire == 0) {
					clipfire1.setFramePosition(0);
					clipfire1.start();
				}
				else {
					clipfire2.setFramePosition(0);
					clipfire2.start();
				}
				if (attacking == 0) {
					if(alive) {
						clipattack.setFramePosition(0);
						clipattack.start();
					}
					attacking++;
				}
				bullet.x = x + width/2;
				bullet.y = y + 5;
				shot = true;
			}
			
			if(shot)
			{
				bullet.y += 15;
				if(bullet.y >= 500)
				{
					bullet.y = -20;
					rand = 1;
					shot = false;
				}
			}
		}
		else
		{
			bullet.y = -15;
		}
		
	}
}
