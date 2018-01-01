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
	int powerLevelPlayer=1;
	int powerLevelBall=1;
	int powerLevelPowers=1;
	boolean threeBall=false;
	
	static long timeCounter=0;
	boolean canGetPowers=true;
	static int count =0;
	
	void resetAllPowerUps()
	{
			canGetPowers=true;
			threeBall=false;
		 	freeze=false;
			// use is moving boolean
			
		 	 isBallPower=false;
			 isPlayerPower=false;
			
			
			// time used to check amout of time passed after user get power
			powerTime=0;
			if(this.bXSpeed<0)
				this.bXSpeed=-4;
			else
				this.bXSpeed=4;
			
			if(this.bYSpeed<0)
				this.bYSpeed=-4;
			else
				this.bYSpeed=4;
			
			playerSpeed=10;
			powerIsOn=false;
			
			powerLevelPlayer=1;
			powerLevelBall=1;
			
		
			powerLevelPowers=1;
			
			
			
	}
	
	void gainRandomPower()
	{
	
		
		
		if(Main.tickTime-timeCounter >10000)
		{
			canGetPowers=true;
			count=0;
		}
		
		if(canGetPowers==true && count ==0)
		{
			count++;
			timeCounter = System.currentTimeMillis();
			canGetPowers=false;
			Random rand = new Random();

			int  n = rand.nextInt(6) + 1;
			
			switch(n)
			{
			// freezes player if struck by ball
				case 1:
					
					powerIsOn=true;
					freeze=true;
					powerLevelBall=5;
					powerLevelPlayer=3;
					powerLevelPowers=1;
					
					System.out.println("freezed");
					break;
			
					//make ball move very slow
				case 2:
					resetAllPowerUps();
					powerIsOn=true;
					isBallPower=true;
					bXSpeed=2;
					bYSpeed=2;
					powerLevelBall=3;
					powerLevelPowers=2;
					System.out.println("slow ball");
					break;
					
					
				case 3:
					resetAllPowerUps();
					powerIsOn=true;
					isPlayerPower=true;
					threeBall=true;
					powerLevelPowers=3;
					System.out.println("three ball");
					break;	
				
					
					//makes player move very slow
				case 4:
					resetAllPowerUps();
					powerIsOn=true;
					isPlayerPower=true;
					playerSpeed=5;
					powerLevelPowers=4;
					System.out.println("slow player");
					break;
					
					//makes player move very fast
				case 5:
					resetAllPowerUps();
					powerIsOn=true;
					isPlayerPower=true;
					playerSpeed=20;
					powerLevelPowers=5;
					System.out.println("fast player");
					break;
					
					//makes ball move very fast
				case 6:
					resetAllPowerUps();
					powerIsOn=true;
					isBallPower =true;
					bXSpeed=8;
					bYSpeed=8;
					powerLevelBall=4;
					powerLevelPowers=6;
					System.out.println("fast ball");
					
					break;
				default:
					powerIsOn=true;
					System.out.println("some shit happened ");
					break;
				
			}
		}
	}

}
