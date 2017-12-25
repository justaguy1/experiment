package mygame;

import java.awt.Image;

import javax.swing.ImageIcon;

public class GameComponents implements Runnable {
	
	int x,y,height,width;  // initial position and sized of player or ball
	Image img=null;  // image for ball or player 
	public static boolean ballIsMoving=false; // checks whether ball is moving 
	
	static  int can_width,can_heigth; // current canvas height and width
	
	int xspeed,yspeed;  // speed at which player or ball moves
	int id;
	Thread t; //thread for all components in this class
	boolean isRunning=false; //check whether the given thread is currently running
	
	static int count=0;
	
	int xa=0;
	
	
	public void initProperties(int x, int y, int width, int height,int id,String ImagePath)
	{
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.id=id;
		initializeImage(ImagePath);
		
		System.out.println("x : "+ x);
		
	
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
				System.out.println("no image found");
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
		System.out.println("x : "+this.x);
	
		
		
		if(ballIsMoving)
		{
			x=x+xspeed;
			y=y+yspeed;
		}

		
		if(x<=10)
		{
		  xspeed=-xspeed;
			count=0;
		
		}
		if(x>=can_width-width)
		{
		    xspeed=-xspeed;
			count=0;
		}
		
		if(y<=10)
		{
			yspeed=-yspeed;
			count=0;
		}
		if(y>=can_heigth-height)
		{
			yspeed=-yspeed;
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
				 Tick();
				 
				 delta--;
			 }
			
		 }
		 stop();
		
	}
	
	 private synchronized void Tick() {
		 if(this.id==0)
		 {
			 moveBall();
		 }
		 
		 if(this.id !=0)
		 {
			 checkPlayerInput();
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
		int max_x=can_width;
		
		
		if(id==1)
		{
			if(Main.right==true)
			{
				if(x<=max_x-width-10)
				{
					x+=10;
				}
			}
				
			if(Main.left==true)
			{
	
				if(x>=10)
				{
					x-=10;
				}
			}
		}
		
		if(id==2)
		{
				
			if(Main._right==true)
			{
	
				if(x<=max_x-width-10)
				{
					x+=10;
				}
			}
				
			if(Main._left==true)
			{
				
				if(x>=10)
				{
					x-=10;
				}
			}
		}
	}
}
