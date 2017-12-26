package mygame;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

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
	
	
	
	static boolean _left=false,_right=false,left=false,right=false;
	
	//related to thread
	static boolean isRunning =true;
	
	//related to ball
	static boolean ballIsMoving=false;
	

	
	Image playerImage=null;
	
	static int clearRectCount =0;

	
	// used for exchanging data 
	GameComponents ball;
	GameComponents player_01,player_02;
	
	


	Main(int width,int height, String title)
	{
		
		
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
			canvas.createBufferStrategy(3);
			return;
		}
		
		g=(Graphics2D) bs.getDrawGraphics();
		g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		
		g.drawImage(ball.img, ball.x, ball.y,ball.width,ball.height,null);
		
		g.drawImage(player_01.img, player_01.x, player_01.y,player_01.width,player_01.height,null);
		g.drawImage(player_02.img, player_02.x, player_02.y,player_02.width,player_02.height,null);
		
		g.drawRect(0, 0, canvas.getWidth()-1, canvas.getHeight()-1);
		g.drawRect(1, 1, canvas.getWidth()-2, canvas.getHeight()-2);
		g.drawRect(2, 2, canvas.getWidth()-3, canvas.getHeight()-3);
		

	    bs.show();
		g.dispose();

	}
	


	private synchronized void Tick() 
	{ 
		 ball.distancecalculator(player_01);
		 ball.distancecalculator(player_02);
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
			
			if(e.getKeyCode()==KeyEvent.VK_I)
				ball.y-=2;
			
			if(e.getKeyCode()==KeyEvent.VK_J)
				ball.x-=2;

		
			if(e.getKeyCode()==KeyEvent.VK_K)
				ball.y+=2;
			
			if(e.getKeyCode()==KeyEvent.VK_L)
				ball.x+=2;
			
			
		
		
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
		
		
     	
		if(e.getKeyCode()==KeyEvent.VK_D)
				right=false;
			
		if(e.getKeyCode()==KeyEvent.VK_A)				
				left =false;
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
	        	_right=false;
		
	    if(e.getKeyCode()==KeyEvent.VK_LEFT)
				_left=false;
		
	}

	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public static void main(String args[])
	{
		Main obj =new Main(1280,720,"hello");
		
		Thread t =new Thread(obj);
		
		 GameComponents.can_heigth=canvas.getHeight();
		 GameComponents.can_width=canvas.getWidth();
		 
		obj.ball=new GameComponents();
		obj.ball.initProperties(60,200,20,20,0,"icons\\green_ball.png");
		obj.ball.initializeSpeed(3, 3);
		obj.ball.start();
		

		obj.player_01 =new GameComponents();
		obj.player_01.initProperties(60, 600, 80, 40,1, "icons\\player.png");
		obj.player_01.initializeSpeed(10, 0);
		obj.player_01.start();
		
	
		obj.player_02 =new GameComponents();
		obj.player_02.initProperties(60, 100, 80, 40,2, "icons\\player.png");
		obj.player_02.initializeSpeed(10, 0);
		obj.player_02.start();
		

		t.start();

	}

}
