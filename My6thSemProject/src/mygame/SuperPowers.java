package mygame;

import java.io.File;
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
	int bulletCount =0;
	boolean threeBall=false;
	
	static long timeCounter=0;
	boolean canGetPowers=true;
	static int count =0;
	
	File freeze_s= new File("sounds/freeze.wav");
	File slowdown_S= new File("sounds/slowdown.wav");
	File speedup_S= new File("sounds/speedup.wav");
	File ballclone_S= new File("sounds/ball_clone.wav");

	
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

			int  n = rand.nextInt(7) + 1;
			 //int n=7;
			switch(n)
			{
			// freezes player if struck by ball
				case 1:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					freeze=true;
					powerLevelBall=5;
					powerLevelPlayer=3;
					powerLevelPowers=1;
					GameComponents.Playsound(freeze_s);
					System.out.println("freezed");
					break;
			
					//make ball move very slow
				case 2:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isBallPower=true;
					bXSpeed=2;
					bYSpeed=2;
					powerLevelBall=3;
					powerLevelPowers=2;
					GameComponents.Playsound(slowdown_S);
					System.out.println("slow ball");
					break;
					
					
				case 3:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isPlayerPower=true;
					threeBall=true;

					powerLevelPowers=3;

					powerLevelPowers=6;
					GameComponents.Playsound(ballclone_S);

					System.out.println("three ball");
					break;	
				
					
					//makes player move very slow
				case 4:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isPlayerPower=true;
					playerSpeed=5;
					powerLevelPowers=4;
					System.out.println("slow player");
					break;
					
					//makes player move very fast
				case 5:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isPlayerPower=true;
					playerSpeed=20;
					powerLevelPowers=5;
					System.out.println("fast player");
					break;
					
					//makes ball move very fast
				case 6:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isBallPower =true;
					bXSpeed=8;
					bYSpeed=8;
					powerLevelBall=4;

					powerLevelPowers=6;

					
					GameComponents.Playsound(speedup_S);

					System.out.println("fast ball");
					
					break;
					
				case 7:
					
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isPlayerPower=true;
					powerLevelPowers=7;
					
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isPlayerPower=true;
					

					
					GameComponents.Playsound(ballclone_S);
					if(GameComponents.playerNo==1)
						Main.player_01.sp.bulletCount=5;
					else
						Main.player_02.sp.bulletCount=5;
					
					
					GameComponents.Playsound(speedup_S);

					System.out.println("bullets");
					
					break;
				default:
					powerIsOn=true;
					System.out.println("some shit happened ");
					break;
				
			}
		}
	}

}
