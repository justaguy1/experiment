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
	
	
	
	int dx,dy; // variables used for calculating distance
	
	
	
	static boolean col=false; // varibale that indicates whether the collision has occured or not
	
	static long colTime =0; // time when collision occured
	
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
		
		 if(this.id==0)
		 {
			 moveBall();
			 
			
			 Thread.sleep(17);
		 }
		 
		 if(this.id !=0)
		 {
			 checkPlayerInput();
			 
			 Thread.sleep(17);
		 }

	}
	 
	 private void changeBallPosition(GameComponents obj) {
		
		 if(obj.id ==10)
		 {
			 obj.x=-100;
			 obj.y=-100;
		 }

		if(x-obj.x <0)
		 {
			 xspeed=-xspeed;
			System.out.println("left");
			return;
		 }
		
		 if(x-obj.x >=obj.width)
		{
			 xspeed=-xspeed;
			System.out.println("right");
			return;
		}
		 if(y-obj.y <0)
		{
			System.out.println("top");
			yspeed=-yspeed;
			return;
		}
		 if(y-obj.y>=obj.height)
		{
			System.out.println("down");
			yspeed=-yspeed;
			return;
		}
		
		
		
		 
		
		
		
		//yspeed=-yspeed;
	
		 
		 
		 
		 
	}

	private void set_dx_dy() {
		 dx=this.x+this.width/2;
		 dy=this.y+this.height/2;
		
	}

	
	 
	 int calculateDistance(GameComponents obj)
		{
		//System.out.println("X : "+obj.dx+" Y : "+obj.dy);
		 
		 
		    int x=(obj.dx-this.dx)*(obj.dx-this.dx);
		    int y= (obj.dy-this.dy)*(obj.dy-this.dy);
		    
		    int distance =(int) Math.sqrt(x+y);
		    
		    calculateCollision(distance,obj);
		    
		    
		  // System.out.println("Distance : "+distance);
		    return  distance;
		    
		  }


	private void calculateCollision( int distance,GameComponents obj) {
		
		long timeNow = System.currentTimeMillis();

		if((Math.abs(this.y-obj.y))<=Math.abs(obj.height) && (Math.abs(this.x-obj.x))<=Math.abs(obj.width) && distance <=50  && count==0)
		{
			changeBallPosition(obj);
			count++;
			colTime = System.currentTimeMillis();
			col=true;
			
			
		}
		if(timeNow -colTime >=500)
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
		
		
		if(id==1)
		{
			if(Main.UP==true)
			{
				if(y>=10)
				{
					y-=10;
				}
			}
				
			if(Main.DOWN==true)
			{
	
				if(y<=max_y-height-10)
				{
					y+=10;
				}
			}
		}
		
		if(id==2)
		{
				
			if(Main._UP==true)
			{
	
				if(y>=10)
				{
					y-=10;
				}
			}
				
			if(Main._DOWN==true)
			{
				
				if(y<=max_y-height-10)
				{
					y+=10;
				}
			}
		}
	}
}
