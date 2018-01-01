package mygame;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	static GameComponents chest;
	static GameComponents powers[];
	static GameComponents initializer;
	
	static String player1ImgPath ="icons\\player.png";
	static String player2ImgPath="icons\\player2.png";
	static String ballImgPaths="icons\\green_ball.png";
	
	
	static String blockImgPath[] =new String[6];
	static String blockRImgPath[] =new String[6]; 
	static String playerImgPath[]=new String[4];
	static String ballImgPath[]=new String[6];
	static String powersImgPath[]=new String[6];
	
	static String backgroundimg="icons\\bk.jpg"; 
	static long tickTime=0;
	
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
	
	static void setAllImagepath()
	{
	  try
	  {
		blockImgPath[1] = "icons\\block_r.png";
		blockImgPath[2] = "icons\\block_g.jpg";
		blockImgPath[3] = "icons\\block_y.png";
		blockImgPath[4] = "icons\\block_lg.png";
		blockImgPath[5] = "icons\\block_b.png";
		
		blockRImgPath[1] = "icons\\block_r_r.png";
		blockRImgPath[2] = "icons\\block_g_r.jpg";
		blockRImgPath[3] = "icons\\block_y_r.png";
		blockRImgPath[4] = "icons\\block_lg_r.png";
		blockRImgPath[5] = "icons\\block_b_r.png";
		
		playerImgPath[1]= "icons\\player.png";
		playerImgPath[2]="icons\\player2.png";
		playerImgPath[3]="icons\\Player_frozen.png";
		
		ballImgPath[1]="icons\\green_ball.png";
		ballImgPath[2]="icons\\green_ball_light.png";
		ballImgPath[3]="icons\\green_ball_strong.png";
		ballImgPath[4]="icons\\red_ball.png";
		ballImgPath[5]="icons\\light_blue_ball.png";
		
		powersImgPath[1]="icons\\green_ball_clone.png";
		powersImgPath[2]="icons\\green_ball_clone.png";
		powersImgPath[3]="icons\\green_ball_clone.png";
		powersImgPath[4]="icons\\green_ball_clone.png";
		powersImgPath[5]="icons\\green_ball_clone.png";
		
	  }
	  catch(Exception e)
	  {
		  System.out.println("problem occured while setting array of image path at setBlockImagepath()");
	  }
	  
	
		
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

		g.drawImage(initializer.img, initializer.x, initializer.y,initializer.width,initializer.height,null);
		g.drawImage(GameComponents.ballI[ball.sp.powerLevelBall], ball.x, ball.y,ball.width,ball.height,null);
		g.drawImage(chest.img, chest.x, chest.y,chest.width,chest.height,null);
		
		g.drawImage(GameComponents.playerI[player_01.sp.powerLevelPlayer], player_01.x, player_01.y,player_01.width,player_01.height,null);
		g.drawImage(GameComponents.playerI[player_02.sp.powerLevelPlayer], player_02.x, player_02.y,player_02.width,player_02.height,null);
		
		for(int i=0;i<80;i++)
		{
			g.drawImage(GameComponents.block[block[i].blockLevel],block[i].x, block[i].y,block[i].width,block[i].height,null);
		}
		for(int i=0;i<3;i++)
		g.drawImage(GameComponents.ballI[1], powers[i].x, powers[i].y,powers[i].width,powers[i].height,null);

	    bs.show();
		g.dispose();

	}
	


	private synchronized void Tick() 
	{ 
		tickTime = System.currentTimeMillis();
		 ball.calculateCollision(player_01);
		 ball.calculateCollision(player_02);
		 
		 for(int i=0;i<80;i++)
		 ball.calculateCollision(block[i]);
		 ball.calculateCollision(chest);
		
		//System.out.println(block[0].y);
		 for(int j=0;j<3;j++)
		 {
			 if(powers[j].sp.powerIsOn==true)
			 {
				 powers[j].calculateCollision(player_01);
				 powers[j].calculateCollision(player_02);
				 for(int i=0;i<80 ;i++)
				 powers[j].calculateCollision(block[i]);
				
			 }
		 }
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
		 
		 
		  initializer =new GameComponents();
		 
		 setAllImagepath(); // set image path for every images
		 initializer.setBallImage();
		 initializer.setBlockImage();
		 initializer.setPlayerImage();
		 initializer.setPowersImage();
		 initializer.initProperties(0, 0, canvas.getWidth(), canvas.getHeight(), -1, backgroundimg);
		 
		 
		ball=new GameComponents();
		ball.initProperties(50,50,20,20,0,ballImgPaths);
		ball.initializeSpeed(4, 4);
		ball.setName("ball");
		ball.start();
		

		player_01 =new GameComponents();
		player_01.initProperties(100, 600, 20, 80,1, player1ImgPath);
		player_01.initializeSpeed(10, 0);
		player_01.setName("Player 1");
		player_01.start();
		
	
		player_02 =new GameComponents();
		player_02.initProperties(1120, 100, 20, 80,2, player2ImgPath);
		player_02.initializeSpeed(10, 0);
		player_02.setName("Player 2");
		player_02.start();
		
		

		powers =new GameComponents[6];
		for(int i=0;i<5;i++)
		{
			powers[i]=new GameComponents();
		}
		
		for(int i=0;i<3;i++)
		{
			powers[i].initProperties(-500, -500, 15, 15, 1, 4);
			powers[i].setName("powers");
			powers[i].start();
		}
		
		
		blocks =new ArrayList<GameComponents>();
		
		//obj.blocks.add(obj.ball);
		
		
		
		
		
		
		obj.stage_first();
		
		
		
		
	   
		t.start();
		

	}

	  void stage_first() {
		 
		 block=new GameComponents[80];
		 
		 for(int i=0;i<80;i++)
			{
				block[i]=new GameComponents();
			}
		 
	/*	block[0].initProperties(300, 100, 80, 40,rand(5),10 );
		block[1].initProperties(380, 100, 80, 40,rand(5),10 );
		block[2].initProperties(460, 100, 80, 40,rand(5),10 );
		block[3].initProperties(540, 100, 80, 40,rand(5),10 );
		block[4].initProperties(620, 100, 80, 40,rand(5),10 );
		block[5].initProperties(700, 100, 80, 40,rand(5),10 );
		block[6].initProperties(300, 140, 80, 40,rand(5),10 );
		block[7].initProperties(380, 140, 80, 40,rand(5),10 );
		block[8].initProperties(460, 140, 80, 40,rand(5),10 );
		block[9].initProperties(540, 140, 80, 40,rand(5),10 );
		block[10].initProperties(620, 140, 80, 40,rand(5),10 );
		//block[5].initProperties(400, 300, 80, 40,2,10 );
		
		block[5].initProperties(700, 100, 80, 40,3,10 );
		block[11].initProperties(700, 400, 80, 40,2,10 );
		block[12].initProperties(600, 500, 80, 40,1,10 );
		block[13].initProperties(700, 500, 80, 40,2,10 );
		block[14].initProperties(700, 600, 80, 40,3,10 );*/
		 
		 chest =new GameComponents();
			chest.initProperties(800, 600,100, 100,5,"icons\\chest.png");
			chest.start();
			
		int j=0;
		int k=1;
		 for(int i=0;i<80;i++)
		 {
			 k++;
			 if(i%8==0)
			 {
				 j++;
				 k=1;
			 }
			 if(i<24)
			 block[i].initProperties(220+k*80, 100+j*40, 80, 40,rand(5),10 );
			 
			 if(i>=24 && i<48)
				 block[i].initProperties(220+k*40, j*80, 40, 80,rand(5),10 );
			 
			 if(i>=48)
				 block[i].initProperties(600+k*40, j*40, 40,40,rand(5),10 );
				 
		 }
		
	}
	  
	  int rand(int value)
		{
			Random rand = new Random();

			int  n = rand.nextInt(value) + 1;
			return n;
			
		} 

}
