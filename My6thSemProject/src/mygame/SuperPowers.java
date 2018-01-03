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
					
					Main.powerUp[n].x=Main.ball.x;
					Main.powerUp[n].y=Main.ball.y;
					
					canGetPowers=false;
					powerIsOn=true;
					Main.powerUp[n].sp.isPlayerPower=true;
					Main.powerUp[n].sp.threeBall=true;

					

					powerLevelPowers=n;
					GameComponents.Playsound(ballclone_S);

					System.out.println("three ball");
					break;	
				
					
					//makes player move very slow
				case 2:
					Main.powerUp[n].x=Main.ball.x;
					Main.powerUp[n].y=Main.ball.y;
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					Main.powerUp[n].sp.isPlayerPower=true;
					Main.powerUp[n].sp.playerSpeed=5;
					Main.powerUp[n].sp.powerLevelPowers=n;
					System.out.println("slow player");
					break;
					
					//makes player move very fast
				case 3:
					Main.powerUp[n].x=Main.ball.x;
					Main.powerUp[n].y=Main.ball.y;
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					Main.powerUp[n].sp.isPlayerPower=true;
					Main.powerUp[n].sp.playerSpeed=20;
					Main.powerUp[n].sp.powerLevelPowers=n;
					System.out.println("fast player");
					break;
					
					//makes ball move very fast
				case 4:
					Main.powerUp[n].x=Main.ball.x;
					Main.powerUp[n].y=Main.ball.y;
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					Main.powerUp[n].sp.isBallPower =true;
					Main.powerUp[n].sp.bXSpeed=8;
					Main.powerUp[n].sp.bYSpeed=8;
					powerLevelBall=4;

					powerLevelPowers=6;

					
					GameComponents.Playsound(speedup_S);

					System.out.println("fast ball");
					
					break;
					
				case 5:
					Main.powerUp[n].x=Main.ball.x;
					Main.powerUp[n].y=Main.ball.y;
					resetAllPowerUps();
					
					powerIsOn=true;
					Main.powerUp[n].sp.isPlayerPower=true;
					Main.powerUp[n].sp.powerLevelPowers=7;
					
					
					canGetPowers=false;

					
					GameComponents.Playsound(ballclone_S);
					if(GameComponents.playerNo==1)
						Main.player_01.sp.bulletCount=5;
					else
						Main.player_02.sp.bulletCount=5;
					
					
					GameComponents.Playsound(speedup_S);

					System.out.println("bullets");
					
					break;
					
					case 6:
					
					
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					freeze=true;
					powerLevelBall=5;
					powerLevelPlayer=3;
					powerLevelPowers=n;
					GameComponents.Playsound(freeze_s);
					System.out.println("freezed");
					break;
			
					//make ball move very slow
				case 7:
					resetAllPowerUps();
					canGetPowers=false;
					powerIsOn=true;
					isBallPower=true;
					bXSpeed=2;
					bYSpeed=2;
					powerLevelBall=3;
					powerLevelPowers=n;
					GameComponents.Playsound(slowdown_S);
					System.out.println("slow ball");
					break;
					
				 default:
					powerIsOn=true;
					System.out.println("some shit happened ");
					break;
				
			}
		}
	}

}
