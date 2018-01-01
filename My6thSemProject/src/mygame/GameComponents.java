package mygame;

import java.awt.Image;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;




public class GameComponents implements Runnable {		//test push for DISCORD notify system
	
	int x,y,height,width;  // initial position and sized of player or ball
	Image img=null;  // image for ball or player 
	public static boolean ballIsMoving=false; // checks whether ball is moving 
	
	static  int can_width,can_heigth; // current canvas height and width
	
	int xspeed,yspeed;  // speed at which player or ball moves
	int id;
	Thread t; //thread for all components in this class
	boolean isRunning=false; //check whether the given thread is currently running
	
	static int count=0;
	
	int blockLevel;
	
	int dx,dy; // variable that sets coordinates to center
	
   // static int ballSpeed=4;
	
	
	static boolean col=false; // varibale that indicates whether the collision has occured or not
	
	static long colTime =0; // time when collision occured
	
	static long timeNow=0;
	static Image block[];
	static Image blockR[];
	static Image playerI[];
	static Image [] ballI;
	static Image [] powersI;
	String name;
	int powerLevel=1;
	
	 SuperPowers sp=new SuperPowers();
	File bounce = new File("sounds/bounce.wav");
	File freeze_s= new File("sounds/freeze.wav");
		
	public void initProperties(int x, int y, int width, int height,int id,String ImagePath)
	{
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.id=id;
		dx=width/2+this.x;
		dy=height/2+this.y;
		
		
		initializeImage(ImagePath);
		
	}
	void setName(String Name)
	{
		name=Name;
	}
	
	public  void setBlockImage()
	{
		block=new Image[6];
		blockR=new Image[6];
		
		
		try 
		{
		for(int i=1;i<6;i++)
		{
			block[i]=new ImageIcon(getClass().getResource(Main.blockImgPath[i])).getImage();
			blockR[i]=new ImageIcon(getClass().getResource(Main.blockRImgPath[i])).getImage();
		}
	
		}
		catch(Exception e)
		{
			System.out.println("error occured while setting array of bloc image");
		}
	}
	public void setPlayerImage()
	{
		playerI=new Image[4];
		try 
		{
		for(int i=1;i<4;i++)
		playerI[i]=new ImageIcon(getClass().getResource(Main.playerImgPath[i])).getImage();
	
		}
		catch(Exception e)
		{
			System.out.println("error occured while setting array of player image");
		}
		
	}
	
	public void setBallImage()
	{
		ballI=new Image[6];
		try 
		{
		for(int i=1;i<6;i++)
		ballI[i]=new ImageIcon(getClass().getResource(Main.ballImgPath[i])).getImage();
	
		}
		catch(Exception e)
		{
			System.out.println("error occured while setting array of ball image");
		}
	}
	
	public void setPowersImage()
	{
		powersI=new Image[6];
		try 
		{
		for(int i=1;i<6;i++)
		powersI[i]=new ImageIcon(getClass().getResource(Main.powersImgPath[i])).getImage();
	
		}
		catch(Exception e)
		{
			System.out.println("error occured while setting array of powers image");
		}
	}
	
	
	
	
	public void initProperties(int x, int y, int width, int height,int level,int id)
	{
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.id=id;
		dx=width/2+this.x;
		dy=height/2+this.y;
		
		
		
		blockLevel =level;
		
	}
	
	
	
	public void initializeImage(String path)
	{
		if (this.img != null)
		{
			return;
		}
		else
		{
			try {
				
				this.img = new ImageIcon(getClass().getResource(path)).getImage();
			}
			
			catch(Exception e)
			{
		
				System.out.println("no image found at path "+path);
				System.exit(1);
			}
		}
		
		
	}
	
	public void initializeSpeed(int xspeed, int yspeed) 
	{
		this.xspeed=xspeed;
		this.yspeed=yspeed;
	}
	public   void moveBall()
	{

		
		if(ballIsMoving)
		{		
			x=x+sp.bXSpeed;
			y=y+sp.bYSpeed;
		}

		
		if(x<=10)
		{
		  //xspeed=-xspeed;
			sp.bXSpeed=-sp.bXSpeed;
			count=0;
		
		}
		if(x>=can_width-width)
		{
		    
		    //xspeed=-xspeed;
		    sp.bXSpeed=-sp.bXSpeed;
			count=0;
		}
		
		if(y<=10)
		{
			//yspeed=-yspeed;
			sp.bYSpeed=-sp.bYSpeed;
			count=0;
		}
		if(y>=can_heigth-height)
		{
			//yspeed=-yspeed;
			sp.bYSpeed=-sp.bYSpeed;
			count=0;
		}
		
		
	}

	
	public void run() {
		
		 int fps=60;
		 double timePerTick=1000000000/fps;
		 double delta=0;
		 long now;
		 long lastTime=System.nanoTime();
		 while(isRunning)
		 {
			 now =System.nanoTime();
			 delta+=(now-lastTime)/timePerTick;
			 lastTime=now;
			 
			 if(delta>=1)
			 {
				 try {
					Tick();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 delta--;
			 }
			
		 }
		 stop();
		
	}
	
	 private synchronized void Tick() throws InterruptedException {
		 
		 set_dx_dy();
		
		 
			 if(this.id==4 && sp.powerIsOn==true)
			 moveBall();
			
			 if(this.id==0)
				 moveBall();
			
		 
		 
		 if(this.id !=0)
		 {
			 checkPlayerInput();
			 
			 //Thread.sleep(17);
		 }
		 if(this.id !=4)
		 removePower();
		 Thread.sleep(17);
	}
	 
	 private void removePower() {
		
		// System.out.println("i am "+name);
		if(this.sp.powerIsOn==false)
			return;
		else if(sp.powerTime==0)
		{
					
			Playsound(freeze_s);
								
			sp.powerTime=System.currentTimeMillis();
			
		}
		
		if(timeNow -sp.powerTime >=10000)
		{
			this.sp.resetAllPowerUps();
		}

	}

	private void changeBallPosition(GameComponents obj) {
		
		float w=(width+obj.width)/2;
		float h=(height+obj.height)/2;
		
		float disY =dy-obj.dy;
		float disX =dx-obj.dx;
		
		float wy =w*disY;
		float hx =h*disX;
		
		
		if(obj.id ==1 || obj.id==2)
		{
			if(obj.sp.powerIsOn==false)
			{
				obj.sp.powerIsOn=true;
				obj.sp.freeze=this.sp.freeze;
				obj.sp.playerSpeed=this.sp.playerSpeed;
				obj.sp.powerLevelPlayer=sp.powerLevelPlayer;
			}
			
			
		}
		
		
		if(obj.id !=10)
		{
			obj.sp.freeze=this.sp.freeze;
			if(obj.sp.freeze==false)		
				Playsound(bounce);

			if(obj.id==5)
			{
				sp.gainRandomPower();
				
				for(int i=0;i<3;i++)
				{
					Main.powers[i].sp.powerIsOn=sp.powerIsOn;
					Main.powers[i].sp.powerLevelPowers=sp.powerLevelPowers;
					Main.powers[i].sp.threeBall=sp.threeBall;
				}
				if(Main.powers[1].x<0 && Main.powers[1].y<0 && Main.powers[1].sp.threeBall==true)
				{
					Main.powers[0].x=getRandom(300, 800);
					Main.powers[0].y=getRandom(400,600);
					Main.powers[1].x=getRandom(300, 800);
					Main.powers[1].y=getRandom(400,600);
					Main.powers[2].x=getRandom(300, 800);
					Main.powers[2].y=getRandom(400,600);
					
				}
				return;
			}
			
			if (wy > hx)
				{  
					if (wy > -hx)
					{
			           System.out.println("down");
			           sp.bYSpeed=-sp.bYSpeed;
					}
			        else
			        {
			           System.out.println("left");
			           sp.bXSpeed=-sp.bXSpeed;
			           return;
			        }
				}
		    else
		    {
			        if (wy > -hx)
			        {
			        	 System.out.println("right");
			        	 sp.bXSpeed=-sp.bXSpeed;
			        }
			           
			        else
			        {
			        	 System.out.println("top");
			        	 sp.bYSpeed=-sp.bYSpeed;
			        }
		    }
		            /* at the bottom */
			 //obj.sp.freeze=this.sp.freeze;
	
		}
		else
		{
			
		//	Playsound(bounce);

			 if(obj.blockLevel==1)
				{
					 obj.x=-4000;
					 obj.y=-4000;
					
				}
			 else
			 {
				 obj.blockLevel--;
			 }
			 

			 if (wy > hx)
				{  
					if (wy > -hx)
					{
			           System.out.println("down");
			           sp.bYSpeed=-sp.bYSpeed;
					}
			        else
			        {
			           System.out.println("left");
			           sp.bXSpeed=-sp.bXSpeed;
			           return;
			        }
				}
		    else
		    {
			        if (wy > -hx)
			        {
			        	 System.out.println("right");
			        	 sp.bXSpeed=-sp.bXSpeed;
			        }
			           
			        else
			        {
			        	 System.out.println("top");
			        	 sp.bYSpeed=-sp.bYSpeed;
			        }
		    }
				
					
		}			
		//yspeed=-yspeed;		 
	}


	private void set_dx_dy() {
		 dx=this.x+this.width/2;
		 dy=this.y+this.height/2;
		
	}
	
	 



	public void calculateCollision(GameComponents obj) {
		
		 timeNow = System.currentTimeMillis();

		/*if((Math.abs(this.y-obj.y))<=Math.abs(obj.height) && (Math.abs(this.x-obj.x))<=Math.abs(obj.width) && distance <=50  && count==0)
		{
			changeBallPosition(obj);
			count++;
			colTime = System.currentTimeMillis();
			col=true;
			
			
		}
		if(timeNow -colTime >=200)
		{
			count=0;
			col=false;
		}*/
		 
		 if (x < obj.x + obj.width &&
				  x +width > obj.x &&
				  y < obj.y + obj.height &&
				  height + y > obj.y && count==0) {
				   
			 
			 
			 	changeBallPosition(obj);
			 	count++;
				colTime = System.currentTimeMillis();
				col=true;
				}
		 if(timeNow -colTime >=100)
			{
				count=0;
				col=false;
			}
				
	}

	void start()
	 {
		if(isRunning)
			return;
		isRunning=true;
		 t=new Thread(this);
		 t.start();
		 
	 }
	public synchronized void stop()
	{
		if(!isRunning)
		return;
		
		isRunning=false;
		try 
		{
			t.join();
		} 
		
		catch (InterruptedException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	private void checkPlayerInput() {
		int max_y=Main.canvas.getHeight();
		
		
		if(id==1 && sp.freeze==false)
		{
			if(Main.UP==true)
			{
				if(y>=10)
				{
					
					y-=sp.playerSpeed;
				}
			}
				
			if(Main.DOWN==true)
			{
	
				if(y<=max_y-height-10)
				{
					
					y+=sp.playerSpeed;
				}
			}
		}
		
		if(id==2 && sp.freeze==false)
		{
				
			if(Main._UP==true)
			{
	
				if(y>=sp.playerSpeed)
				{
					y-=sp.playerSpeed;
				}
			}
				
			if(Main._DOWN==true)
			{
				
				if(y<=max_y-height-10)
				{
					y+=sp.playerSpeed;
				}
			}
		}
	}
	
	
	private void Playsound(File Sound) {		//bounce sound playing function
		try {
			
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(Sound));
			//System.out.println("sound:");
			clip.start();
			
			//Thread.sleep(clip.getMicrosecondLength()/1000);
		}
		catch(Exception e){
			System.out.println("error");
		}
		
	}
	
	int getRandom(int min ,int max)
	{
		Random rand = new Random();

		int  n = rand.nextInt(max) + 1;

		return clamp(min,max,n);
	}
	int clamp(int min ,int max,int value)
	{
		if(value<=min)
			value=min;
		else if(value>=max)
			value=max;
		
		return value;
	}

}
