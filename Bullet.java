import java.awt.*;
import javax.swing.*;


public class Bullet extends GameObject
{
	Color col;
	int a;
	Bullet()
	{
		width = 5;
		height = 10;
		
		x = 0;
		y = 0;
		a = 0;
		attribute = "bullet";
		col = Color.RED;
	}
	
	public void update()
	{
		if (a == 0) {
			y -= 20;
		}
		else {
			y += 5;
		}
	}
	
	public void makeColor()
	{
		col = Color.GREEN;
	}
	public void makeColorMissile()
	{
		col = Color.MAGENTA;
	}
	public void draw(Graphics g, Component c)
	{
		g.setColor(col);
		g.fillRect(x, y, width, height);
	}
}