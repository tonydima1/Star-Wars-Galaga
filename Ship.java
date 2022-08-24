
public class Ship extends GameObject
{

	//ship velocity
	int dx;
	
	Ship()
	{
		attribute = "ship";
	}
	public void update()
	{
		//if(x >= 0 && x <= 500) { 
		x+=dx;
		//}
		//else {
		//	x-=dx;
		//}
	}

}