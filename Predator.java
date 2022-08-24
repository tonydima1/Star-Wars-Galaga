
public class Predator extends Alien
{
	GameObject prey;
	int cont = 0;

	//Predator, follows you
	Predator()
	{
		xtmp = 0;
		ytmp = 0;
		x = 0;
		y = 0;
		rand = 1;
		cnt = 0;
		move = 0;
		dx = 1;
		dy = 1;
		attackchance = 0;
		setclock = 0;
		attacking = 0;
		attribute = "Predator";
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
			if (y <= 300) {
				if (y <= 300 && y >= 200) {
					rand = 0;
				}
				if(prey.x < x) 
				{
					x -= 5;
				}
				if(prey.x > x)
				{
					x += 5;
				}
				if(prey.y > y)
				{
					y += 3;
				}
				if(prey.y < y)
				{
					y -= 3;
				}
			}
			else {
				y += 3;
				move = 3;
			}
		}
		else if (move == 3) {
			y += 3;
			if (y > 500){
				y = -50;
				x = xtmp;
				cont = 1;
			}
			if(y <= ytmp + 10 && y >= ytmp - 10 && cont == 1){
				y = ytmp;
				move = 0;
				cont = 0;
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
					//clipfire1.setFramePosition(0);
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
				bullet.y += 5;
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