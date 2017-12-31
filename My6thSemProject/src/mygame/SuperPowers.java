package mygame;

import java.util.Random;

public class SuperPowers {
	
	 boolean freeze=false;
	// use is moving boolean
	
	 boolean isBallPower=false;
	 boolean isPlayerPower=false;
	 boolean powerIsOn=false;
	
	
	
	// time used to check amout of time passed after user get power
	long powerTime=0;
	
	
	int playerSpeed =10;
	
	int bXSpeed=4;
	int bYSpeed=4;
	
	void resetAllPowerUps()
	{
		
		 	freeze=false;
			// use is moving boolean
			
		 	 isBallPower=false;
			 isPlayerPower=false;
			
			
			// time used to check amout of time passed after user get power
			powerTime=0;
			bXSpeed=4;
			bYSpeed=4;
			
			playerSpeed=10;
			powerIsOn=false;
		
			
			
	}
	
	void gainRandomPower()
	{
	

		Random rand = new Random();

		int  n = rand.nextInt(5) + 1;
		
		switch(n)
		{
		// freezes player if struck by ball
			case 1:
				
				powerIsOn=true;
				freeze=true;
				System.out.println("freezed");
				break;
		
				//make ball move very slow
			case 2:
				resetAllPowerUps();
				powerIsOn=true;
				isBallPower=true;
				bXSpeed=2;
				bYSpeed=2;
				System.out.println("slow ball");
				break;
				
				//makes ball move very fast
			case 3:
				resetAllPowerUps();
				powerIsOn=true;
				isBallPower =true;
				bXSpeed=8;
				bYSpeed=8;
				System.out.println("fast ball");
				
				break;
				
				//makes player move very slow
			case 4:
				resetAllPowerUps();
				powerIsOn=true;
				isPlayerPower=true;
				playerSpeed=5;
				System.out.println("slow player");
				break;
				
				//makes player move very fast
			case 5:
				resetAllPowerUps();
				powerIsOn=true;
				isPlayerPower=true;
				playerSpeed=20;
				System.out.println("fast player");
				break;
			default:
				powerIsOn=true;
				System.out.println("some shit happened ");
				break;
				
				/*if (x < obj.x + obj.width &&
						  x +width > obj.x &&
						  y < obj.y + obj.height &&
						  height + y > obj.y && count==0) {
						   */
		}
	}

}
