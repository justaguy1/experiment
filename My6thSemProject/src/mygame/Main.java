package mygame;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;





public class Main   implements Runnable,KeyListener{
	
	int height,width;
	String title;
	static int count =0;
	
	JFrame frame;
	public static Canvas canvas;
	 static Graphics2D g;
	 BufferStrategy bs;
	int playerNo =0;
	
	
	
	static boolean _DOWN=false,_UP=false,DOWN=false,UP=false;
	
	//related to thread
	static boolean isRunning =true;
	
	//related to ball
	static boolean ballIsMoving=false;
	

	
	Image playerImage=null;
	
	static int clearRectCount =0;

	
	
	static GameComponents ball;
	static GameComponents player_01,player_02;
	static List<GameComponents> blocks ;
	static GameComponents block[];
	
	
	


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
		
		for(int i=0;i<15;i++)
		g.drawImage(block[i].img,block[i].x, block[i].y,block[i].width,block[i].height,null);
		

	    bs.show();
		g.dispose();

	}
	


	private synchronized void Tick() 
	{ 
		 ball.calculateDistance(player_01);
		 ball.calculateDistance(player_02);
		 
		 for(int i=0;i<15;i++)
		 ball.calculateDistance(block[i]);
		
		//System.out.println(block[0].y);
	}
	
	public void keyPressed(KeyEvent e) {
		
		
			if(e.getKeyCode()==KeyEvent.VK_W)
				UP=true;
			
			if(e.getKeyCode()==KeyEvent.VK_S)
				DOWN =true;

		
			if(e.getKeyCode()==KeyEvent.VK_UP)
				_UP=true;
			
			if(e.getKeyCode()==KeyEvent.VK_DOWN)
				_DOWN=true;
			
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
		
		
     	
		if(e.getKeyCode()==KeyEvent.VK_W)
				UP=false;
			
		if(e.getKeyCode()==KeyEvent.VK_S)				
				DOWN =false;
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
	        	_UP=false;
		
	    if(e.getKeyCode()==KeyEvent.VK_DOWN)
				_DOWN=false;
		
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
		 
		ball=new GameComponents();
		ball.initProperties(60,200,20,20,0,"icons\\green_ball.png");
		ball.initializeSpeed(4, 4);
		ball.start();
		

		player_01 =new GameComponents();
		player_01.initProperties(100, 600, 40, 80,1, "icons\\player.png");
		player_01.initializeSpeed(10, 0);
		player_01.start();
		
	
		player_02 =new GameComponents();
		player_02.initProperties(1120, 100, 40, 80,2, "icons\\player.png");
		player_02.initializeSpeed(10, 0);
		player_02.start();
		
		

		t.start();
		
		blocks =new ArrayList<GameComponents>();
		
		//obj.blocks.add(obj.ball);
		
		block=new GameComponents[15];
		
		for(int i=0;i<15;i++)
		{
			block[i]=new GameComponents();
		}
		
		block[0].initProperties(300, 200, 80, 40,10, "icons\\block_y.png");
		block[1].initProperties(400, 200, 80, 40,10, "icons\\block_y.png");
		block[2].initProperties(500, 200, 80, 40,10, "icons\\block_y.png");
		block[3].initProperties(600, 200, 80, 40,10, "icons\\block_y.png");
		block[4].initProperties(700, 200, 80, 40,10, "icons\\block_y.png");
		block[5].initProperties(400, 300, 80, 40,10, "icons\\block_y.png");
		block[6].initProperties(500, 300, 80, 40,10, "icons\\block_y.png");
		block[7].initProperties(600, 300, 80, 40,10, "icons\\block_y.png");
		block[8].initProperties(700, 300, 80, 40,10, "icons\\block_y.png");
		block[9].initProperties(500, 400, 80, 40,10, "icons\\block_y.png");
		block[10].initProperties(600, 400, 80, 40,10, "icons\\block_y.png");
		block[11].initProperties(700, 400, 80, 40,10, "icons\\block_y.png");
		block[12].initProperties(600, 500, 80, 40,10, "icons\\block_y.png");
		block[13].initProperties(700, 500, 80, 40,10, "icons\\block_y.png");
		block[14].initProperties(700, 600, 80, 40,10, "icons\\block_y.png");
		
		

	}

}
