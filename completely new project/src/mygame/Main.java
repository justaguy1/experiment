package mygame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;


import javax.swing.ImageIcon;
import javax.swing.JFrame;






public class Main   implements Runnable,KeyListener{
	
	int height,width;
	String title;
	 static int count =0;
	
	JFrame frame;
	public static Canvas canvas;
	 static Graphics2D g;
	 BufferStrategy bs;
	int playerNo =0;
	
	int player_xpos,player_ypos,player_height,player_width;
	
	static boolean _left=false,_right=false,left=false,right=false;
	
	//related to thread
	static boolean isRunning =true;
	
	//related to ball
	public static int ball_xpos,ball_ypos,ball_radius,ball_xspeed,ball_yspeed;
	public static Image ballImg =null;
	static boolean ballIsMoving =false;
	static boolean CollisionDetected=false;
	
	Image playerImage=null;
	
	static int clearRectCount =0;

	static int gamecomponentinitcount =0;
	
	GameComponents gc;
	
	
	

	
	 public void InitBall()
	{
		ball_xpos=200;
		ball_ypos=585;
		ball_radius=20;
		ball_xspeed=3;
		ball_yspeed=3;
		
		
		
		
		ballImg=this.getImage("icons\\green_ball.png");
		
	
		
		
	}
	
	void InitPlayer(int x,int y,int height, int width)
	{
		player_xpos=x;
		player_ypos=y;
		player_height=height;
		player_width=width;
		
		this.playerImage=this.getImage("icons\\player.png");
	}
	
	Main(int pNo)
	{
		
		playerNo = pNo;
		canvas.addKeyListener(this);
		canvas.setFocusable(true);
	}
	Main(int width,int height, String title,int pNo)
	{
		playerNo = pNo;
		
		frame =new JFrame(title);
		frame.setSize(width,height);
		frame.setTitle(title);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		canvas=new Canvas();
		canvas.setSize(width, height);
		
		frame.add(canvas);
		frame.pack();
		canvas.addKeyListener(this);
		canvas.setFocusable(true);
		
		
		
	}
	
	public synchronized void run()
	{
		 
		 
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
				 Render();
				 delta--;
			 }
			
		 }
		
	}
	private synchronized void Render() 
	{
		
        this.bs=canvas.getBufferStrategy();
		
		if(bs==null)
		{
			canvas.createBufferStrategy(2);
			return;
		}
		
		
		
		g=(Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		
			
			
			
			
			gc.moveBall();
			
			g.drawImage(gc.img, gc.x, gc.y,gc.width,gc.height,null);
		
		
		
		
		//g.drawImage(obj.img, obj.x, obj.y, obj.width, obj.height,null);  
		
		g.drawRect(0, 0, canvas.getWidth()-1, canvas.getHeight()-1);
		g.drawRect(1, 1, canvas.getWidth()-2, canvas.getHeight()-2);
		g.drawRect(2, 2, canvas.getWidth()-3, canvas.getHeight()-3);
		
		
	      
	       
	      
	         
	       this.bs.show();
		   this.g.dispose();

	}
	


	private synchronized void Tick() 
	{ 
	    int distance;
		if(playerNo==3) 
		 moveBall();
		 checkPlayerInput();
		 if(playerNo !=3)
		 {
		   distance =calculateDistance(player_xpos+player_width/2,player_ypos+player_height/2,ball_xpos+ball_radius/2,ball_ypos+ball_radius/2);
		   checkBallCollision(distance);
		 }
		 GameComponents.can_heigth=canvas.getHeight();
		 GameComponents.can_width=canvas.getWidth();
		 
		 

	}
	
	public synchronized void checkBallCollision(int distance)
	{
		if(CollisionDetected)
			{
			if(playerNo==1)
			{
				ball_yspeed=3;
			}
			if(playerNo==2)
			{
				ball_yspeed=-3;
			}
		}
	}
	
	int calculateDistance(int x1, int y1, int x2, int y2)
		{
		    int x=(x2-x1)*(x2-x1);
		    int y= (y2-y1)*(y2-y1);
		    
		    int distance =(int) Math.sqrt(x+y);
		    
		    calculateCollision(x1,y1,x2,y2,distance);
		    
		   // System.out.println("Distance : "+distance);
		    return  distance;
		    
		  }
	   
	void calculateCollision(int x1,int y1, int x2, int y2, int distance)
		{
			
			   if(distance<42)
			   {
				   CollisionDetected =true;
				  
			   }
			   
               else 
			    {
			    	CollisionDetected =false;
			    	
			    }
		}
	private void checkPlayerInput() {
		int max_x=canvas.getWidth();
		
		
		if(this.playerNo ==1)
		{
			if(right==true)
			{
				if(player_xpos<=max_x-player_width-10)
				{
					player_xpos+=10;
				}
			}
				
			if(left==true)
			{
	
				if(player_xpos>=10)
				{
					player_xpos-=10;
				}
			}
		}
		
		if(this.playerNo ==2)
		{
				
			if(_right==true)
			{
	
				if(player_xpos<=max_x-player_width-10)
				{
					player_xpos+=10;
				}
			}
				
			if(_left==true)
			{
				
				if(player_xpos>=10)
				{
					player_xpos-=10;
				}
			}
		}
	}
	

	public synchronized void moveBall()
	{
       int max_x=canvas.getWidth();
       int max_y=canvas.getHeight();
		
		if(ballIsMoving)
		{
	
			ball_xpos=ball_xpos+ball_xspeed;
			ball_ypos=ball_ypos+ball_yspeed;

		}
		
		
		if(ball_xpos<=10)
		{
			ball_xspeed=-ball_xspeed;
			count=0;
		
		}
		if(ball_xpos>=max_x-ball_radius)
		{
			ball_xspeed=-ball_xspeed;
			count=0;
		}
		
		if(ball_ypos<=10)
		{
			ball_yspeed=-ball_yspeed;
			count=0;
		}
		if(ball_ypos>=max_y-ball_radius)
		{
			ball_yspeed=-ball_yspeed;
			count=0;
		}
		
		
	}
	

	
	public void keyPressed(KeyEvent e) {
		
		
			if(e.getKeyCode()==KeyEvent.VK_D)
				right=true;
			
			if(e.getKeyCode()==KeyEvent.VK_A)
				left =true;

		
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				_right=true;
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				_left=true;
		
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			
			GameComponents.ballIsMoving=true;
			if(!ballIsMoving)
			{
				ballIsMoving=true;
			}
			
		}

	}
		
	

	
	public void keyReleased(KeyEvent e) {

		if(this.playerNo ==1)
		{
			if(e.getKeyCode()==KeyEvent.VK_D)
				right=false;
			
			if(e.getKeyCode()==KeyEvent.VK_A)				
				left =false;
			
			
		}
		if(this.playerNo ==2)
		{
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            _right=false;

         	if(e.getKeyCode()==KeyEvent.VK_LEFT)
			_left=false;

		}
		
	}

	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Image getImage(String path)
	{
		Image img =null;
		
		try {
			
			img = new ImageIcon(getClass().getResource(path)).getImage();
		}
		
		catch(Exception e)
		{
			System.out.println("no image found");
			System.exit(1);
		}

		return img;
	}
	
	public static void main(String args[])
	{
		Main obj =new Main(1280,720,"hello",1);
		//Main player_02 =new Main(2);
		
		//Main ball =new Main(3);
		
		//player_01.InitPlayer(60, 100, 40, 80);
		//player_02.InitPlayer(60, 600, 40, 80);
		
		
		//ball.InitBall();
		
		
		Thread t =new Thread(obj);
		//Thread t2 =new Thread(player_02);
		
		//Thread t3 =new Thread(ball);
		obj.gc=new GameComponents();
		obj.gc.initProperties(60,200,40,80,"icons\\green_ball.png");
		obj.gc.initializeSpeed(3, 3);
		
		t.start();
		//t2.start();
		//t3.start();
		
		
		
		

		
		

	}

}
