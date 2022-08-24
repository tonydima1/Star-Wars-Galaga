
public class Seeker extends Alien
{
	double t;
	GameObject prey;
	
	
	//Seeker, moves around in sine wave, shoots bullets that follow you
	
	Seeker()
	{
		cnt = 0;
		xtmp = 0;
		ytmp = 0;
		x = 0;
		y = 0;
		rand = 1;
		cnt = 0;
		move = 0;
		dx = 1;
		dy = 1;
		dyattack = 5;
		xtick = 0;
		attackchance = 0;
		attribute = "Seeker";
		attacking = 0;
		setclock = 0;
		bullet.makeColorMissile();
	}
	
	public void setPrey(GameObject o)
	{
		prey = o;
	}
	
	public void update()
	{		
		cnt++;
		//modular for attack motion. add a timer to main
		//if(cnt%2 != 1 && move == 0 && tick >= 15)
		if(cnt%(200+setclock) == 0) {
			attackchance = dice.nextInt(2);
			if(attackchance == 0) {
				move = 0;
			}
			else {
				move = 2;
				//xtick = 0;
			}
		}
		if(move == 2) {
			if (y <= 200 && y >= 100) {
				rand = 0;
			}
			if(xtick == 0) {
				xtick++;
				x = xtmp + (int) (50 *Math.cos(t));
				y += dyattack;
				t+= .2;
				if(y > 500) {
					y = -50;
					move = 3;
				}
			}
			else {
				xtick = 0;
				x = xtmp + (int) (50 *Math.cos(t));
				y += dyattack;
				t+= .2;
				if(y > 500) {
					y = -50;
					move = 3;
					x = xtmp;
				}
			}	
			
		}
		else if(move == 3) {
			y += dyattack;
			if(y <= ytmp + 10 && y >= ytmp - 10){
				y = ytmp;
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
			if(rand == 0 && !shot)
			{
				clipmissile.setFramePosition(0);
				clipmissile.start();
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
				bullet.y += 10;
				if(prey.x < bullet.x)
				{
					bullet.x -= 4;
				}
				if(prey.x > bullet.x)
				{
					bullet.x += 4;
				}
				if(bullet.y >= 500)
				{
					bullet.y = -50;
					shot = false;
					rand = 1;
				}
			}
		}
		else
		{
			bullet.y = -15;
		}
		
	}
}