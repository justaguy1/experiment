package mygame;

import java.awt.Image;

import javax.swing.ImageIcon;

public class GameComponents implements Runnable {
	
	int x,y,height,width;  // initial position and sized of player or ball
	Image img=null;  // image for ball or player 
	public static boolean ballIsMoving=false; // checks whether ball is moving 
	
	static  int can_width,can_heigth; // current canvas height and width
	
	int xspeed,yspeed;  // speed at which player or ball moves
	
	Thread t; //thread for all components in this class
	
	static int count=0;
	
	int xa=0;
	
	
	public void initProperties(int x, int y, int width, int height,String ImagePath)
	{
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
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
	
		
		
		
		x=x+xspeed;
		y=y+yspeed;

		
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
		
		return;
	}
	
	 void start()
	 {
		 t=new Thread(this);
		 t.start();
	 }
}
