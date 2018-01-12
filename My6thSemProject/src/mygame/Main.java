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
	
		
	static boolean _DOWN=false,_UP=false,DOWN=false,UP=false;
	static boolean fire1=false,fire2=false;
	
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
	static GameComponents chest[];
	static GameComponents powers[];
	static GameComponents initializer;
	static GameComponents bullet1[];
	static GameComponents bullet2[];
	static GameComponents powerUp[];
	static GameComponents player1block;
	static GameComponents player2block;
	
	
	static String player1ImgPath ="icons\\player1.png";
	static String player2ImgPath="icons\\player2.png";
	static String ballImgPaths="icons\\green_ball.png";
		
	static String blockImgPath[] =new String[6];
	static String blockRImgPath[] =new String[6]; 
	static String playerImgPath[]=new String[5];
	static String ballImgPath[]=new String[6];
	static String powersImgPath[]=new String[6];
	static String powerUps[]=new String[8];
	static String player1blocks[]=new String[4];
	static String player2blocks[]=new String[4]; 
	
	static String backgroundimg="icons\\bk.jpg"; 
	static long tickTime=0;
	
	static int block_num=500;
	static int block_counter=0;
	//static int block_gap=2;
	
	static boolean gameJustStarted=true;
	
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
		blockImgPath[2] = "icons\\block_g.png";
		blockImgPath[3] = "icons\\block_y.png";
		blockImgPath[4] = "icons\\block_lg.png";
		blockImgPath[5] = "icons\\block_b.png";
		
		blockRImgPath[1] = "icons\\block_r_r.png";
		blockRImgPath[2] = "icons\\block_g_r.png";
		blockRImgPath[3] = "icons\\block_y_r.png";
		blockRImgPath[4] = "icons\\block_lg_r.png";
		blockRImgPath[5] = "icons\\block_b_r.png";
		
		playerImgPath[1]= "icons\\player1.png";
		playerImgPath[2]="icons\\player2.png";
		playerImgPath[3]="icons\\Player1_frozen.png";
		playerImgPath[4]="icons\\Player2_frozen.png";
		
		
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
		
		powerUps[1]="icons\\slow.png";
		powerUps[2]="icons\\fast.png";
		powerUps[3]="icons\\slow.png";
		powerUps[4]="icons\\fire.png";
		powerUps[5]="icons\\three_ball.png";
		powerUps[6]="icons\\what.png";
		powerUps[7]="icons\\what_grey.png";
		
		player1blocks[3]="icons\\player1_life_block_1.png";
		player1blocks[2]="icons\\player1_life_block_2.png";
		player1blocks[1]="icons\\player1_life_block_3.png";
		
		player2blocks[3]="icons\\player2_life_block_1.png";
		player2blocks[2]="icons\\player2_life_block_2.png";
		player2blocks[1]="icons\\player2_life_block_3.png";
		
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
		
		for(int i=0;i<3;i++)
		g.drawImage(chest[i].img, chest[i].x, chest[i].y,chest[i].width,chest[i].height,null);
		
		g.drawImage(GameComponents.playerI[player_01.sp.powerLevelPlayer], player_01.x, player_01.y,player_01.width,player_01.height,null);
		g.drawImage(GameComponents.playerI[player_02.sp.powerLevelPlayer], player_02.x, player_02.y,player_02.width,player_02.height,null);
		
		for(int i=0;i<block_num;i++)
		{
			if(block[i].height<block[i].width)
			g.drawImage(GameComponents.block[block[i].blockLevel],block[i].x, block[i].y,block[i].width,block[i].height,null);
			else if(block[i].height>block[i].width)
				g.drawImage(GameComponents.blockR[block[i].blockLevel],block[i].x, block[i].y,block[i].width,block[i].height,null);
				
		}
		for(int i=0;i<3;i++)
		g.drawImage(GameComponents.ballI[1], powers[i].x, powers[i].y,powers[i].width,powers[i].height,null);

		for(int i=0;i<5;i++)
		{
			if(bullet1[i].x >0 && bullet1[i].y>0)
			{
				g.drawImage(bullet1[i].img, bullet1[i].x, bullet1[i].y,bullet1[i].width,bullet1[i].height,null);
				
			}
			
			if(bullet2[i].x >0 && bullet2[i].y>0)
			{
				if(bullet2[i].x >0 && bullet2[i].y>0)
				{
					g.drawImage(bullet2[i].img, bullet2[i].x, bullet2[i].y,bullet2[i].width,bullet2[i].height,null);
					
				}
				
			}
			
			
			
		}
		
		for(int i=0;i<6;i++)
		{
			if(powerUp[i].x>0)
			{
				g.drawImage(GameComponents.powerUp[i], powerUp[i].x, powerUp[i].y,powerUp[i].width,powerUp[i].height,null);
			}
		}
		
		g.drawImage(GameComponents.player1block[player1block.blockLevel], player1block.x,player1block.y,player1block.width,player1block.height,null);
		g.drawImage(GameComponents.player2block[player2block.blockLevel], player2block.x,player2block.y,player2block.width,player2block.height,null);
		
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.BLUE);
		
		
		g.drawString("player 1 : "+player_01.score, 400, 40);
		g.drawString("player 2 : "+player_02.score,800, 40);
		
		
		g.setFont(new Font("Arial",Font.BOLD,60));
	
		gameOver(g);
	    bs.show();
		g.dispose();

	}
	


	

	private void gameOver(Graphics2D g2) {
		if(ball.x<=10 )
		{
			g.drawString("gameOver player 2 wins ", 250, 400);
			
			ballIsMoving=false;
			GameComponents.ballIsMoving=false;
			gameJustStarted=false;
			
			
			
		}
	//	System.out.println(ball.x-(canvas.getWidth()-30));
		
		if(ball.x>canvas.getWidth()-30)
		{
			g.drawString("gameOver player 1 wins ", 250, 400);
			ballIsMoving=false;
			GameComponents.ballIsMoving=false;
			gameJustStarted=false;
			
			
		}
		
	}

	private synchronized void Tick() 
	{ 
		tickTime = System.currentTimeMillis();
		 ball.calculateCollision(player_01);
		 ball.calculateCollision(player_02);
		 ball.calculateCollision(player1block);
		 ball.calculateCollision(player2block);
		 for(int i=0;i<block_num;i++)
		 ball.calculateCollision(block[i]);
		 for(int i=0;i<3;i++)
		 ball.calculateCollision(chest[i]);
		
		//System.out.println(block[0].y);
		 for(int j=0;j<3;j++)
		 {
			 if(powers[j].sp.powerIsOn==true)
			 {
				 powers[j].calculateCollision(player_01);
				 powers[j].calculateCollision(player_02);
				 for(int i=0;i<block_num ;i++)
				 powers[j].calculateCollision(block[i]);
				
			 }
		 }
		 
		 for(int i=0;i<5;i++)
		 {
			 if(bullet1[i].x >0 && bullet1[i].y>0)
			 {
				 bullet1[i].x+=8;
				 bullet1[i].calculateCollision(player_02);
				for(int j=0;j<block_num;j++)
				 bullet1[i].calculateCollision(block[j]);
				 
				 if(bullet1[i].x>Main.canvas.getWidth())
				 {
					 bullet1[i].x=-100;
				 }
			 }
			 

			 if(bullet2[i].x >0 && bullet2[i].y>0)
			 {
				 bullet2[i].x-=8;
				 bullet2[i].calculateCollision(player_02);
				 for(int j=0;j<block_num ;j++)
					 bullet2[i].calculateCollision(block[j]);
				 
				 if(bullet2[i].x>Main.canvas.getWidth())
				 {
					 bullet2[i].x=-100;
				 }
			 }
			 
			 
			  getpowers();
		 }
	}
	
	private void getpowers() {
		
	//	System.out.println(GameComponents.playerNo);
		for(int i=0;i<6;i++)
		{
			if(powerUp[i].x>0)
			{
				if(GameComponents.playerNo==1)
				{
					powerUp[i].x--;
					powerUp[i].calculateCollision(player_01);
					powerUp[i].calculateCollision(player_02);
				}
				else if(GameComponents.playerNo==2)
					{
						powerUp[i].x++;
						powerUp[i].calculateCollision(player_02);
						powerUp[i].calculateCollision(player_01);
						
						if(powerUp[i].x>Main.canvas.getWidth()+10)
						{
							powerUp[i].x=-100;
						}
							
					}
					
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
				ball.y-=10;
			
			if(e.getKeyCode()==KeyEvent.VK_J)
				ball.x-=12;

		
			if(e.getKeyCode()==KeyEvent.VK_K)
				ball.y+=12;
			
			if(e.getKeyCode()==KeyEvent.VK_L)
				ball.x+=12;
			
			if(e.getKeyCode()==KeyEvent.VK_D)
				fire1=true;
			
			if(e.getKeyCode()==KeyEvent.VK_LEFT)
				fire2=true;
		
			if(e.getKeyCode()==KeyEvent.VK_E)
				ball.sp.gainRandomPower();
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			
			GameComponents.ballIsMoving=true;
			if(!ballIsMoving)
			{
				ballIsMoving=true;
				
				if(gameJustStarted==false)
				{
				
					if(GameComponents.playerNo==2)
					{
						ball.x=player_01.dx+10;
						ball.y=player_01.dy;
						GameComponents.playerNo=2;
						player_01.score=0;
						player_02.score=0;
						block_counter=0;
						
						stage_first();
					}
					if(GameComponents.playerNo==1)
					{
						ball.x=player_02.dx-30;
						ball.y=player_02.dy;
						player_01.score=0;
						player_02.score=0;
						GameComponents.playerNo=1;
						block_counter=0;
						stage_first();
					}
				}
			}
		
			
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			System.exit(0);

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
		
	    if(e.getKeyCode()==KeyEvent.VK_D)
			fire1=false;
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
			fire2=false;
	}

	
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public static void main(String args[])
	{
		if(Main_menu.startgame==false)
		Main_menu.main_menu();
		
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
		player_01.sp.bulletCount=4;
		player_01.start();
		
	
		player_02 =new GameComponents();
		player_02.initProperties(1120, 100, 20, 80,2, player2ImgPath);
		player_02.initializeSpeed(10, 0);
		player_02.setName("Player 2");
		player_02.start();
		
		

		powers =new GameComponents[6];  // three balls
		powerUp=new GameComponents[6];
		for(int i=0;i<6;i++)
		{
			powers[i]=new GameComponents();
			powerUp[i]=new GameComponents();
		}
		
		for(int i=0;i<6;i++)
		{
			powerUp[i].initProperties(-100, -100, 40, 40, 1, 20);
			powerUp[i].start();
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
		 
		 block=new GameComponents[block_num];
		 
		
	for(int i=0;i<block_num;i++)
		block[i]=new GameComponents();
	
	
		chest=new GameComponents[3];
		for(int i=0;i<3;i++)
		 chest[i] =new GameComponents();
		
	
			
			
		
			
		bullet1=new GameComponents[5];
		bullet2=new GameComponents[5];
		
		for(int i=0;i<5;i++)
		{
			bullet1[i]=new GameComponents();
			bullet2[i]=new GameComponents();
		}
		
		for(int i=0;i<5;i++)
		{
			
			
			bullet1[i].initProperties(-100, -100, 40, 40, 6, "icons\\fireball.png");
			bullet2[i].initProperties(-100, -100, 40, 40, 7, "icons\\fireballR.png");
			bullet1[i].start();
			bullet2[i].start();
		}
			
	
		
		player1block=new GameComponents();
		player2block=new GameComponents();
		
		player1block.initProperties(10, 0, 20, 720, 3, 10);
		player2block.initProperties(canvas.getWidth()-30, 0, 20, 720, 3, 10);
		
		
		
		levelSelecter();
	
	}
		
		
			 
	  private void hand_bonus() {
		  int x=530,y=30;
		  createLevel(530, 48, 45, 70, 4, 3, 40, 5);
		  
		  createLevel(390, 250, 55, 65, 1, 1, 0, 0);
		  createLevel(420, 315, 61, 60, 1, 1, 0, 0);
		  
		  createLevel(x-50, y+240, 71, 65, 5, 1, 0, 0);
		  createLevel(x-50, y+300, 71, 70, 5, 1, 0, 0);
		  createLevel(x+5, y+370, 71, 70, 4, 1, 0, 0);
		  createLevel(x+40, y+440, 71, 70, 3, 1, 0, 0);
			  
		  createLevel(610, 540, 40, 80, 3, 1, 1, 1);
	}

	private void level_p() {
		  int x=500,y=130;
		  createLevel(x+50, y, 51, 50, 1, 1, 0, 0);
		  createLevel(x+25, y+50, 51, 50, 2, 1, 0, 0);
		  createLevel(x, y+100, 51, 50, 3, 1, 0, 0);

		  createLevel(x-25, y+150, 51, 50, 4, 1, 0, 0);
		  createLevel(x-50, y+200, 51, 50, 5, 1, 0, 0);
		  
		  createLevel(x-360, y+265, 100, 20, 4, 1, 0, 0);
		  createLevel(x+130, y+265, 100, 20, 4, 1, 0, 0);
	  
		  createLevel(x-50, y+300, 51, 50, 5, 1, 0, 0);
		  createLevel(x-25, y+350, 51, 50, 4, 1, 0, 0);

		  createLevel(x, y+400, 51, 50, 3, 1, 0, 0);
		  createLevel(x+25, y+450, 51, 50, 2, 1, 0, 0);
		  createLevel(x+50, y+500, 51, 50, 1, 1, 0, 0);
		  
		  chest[0].initProperties(x+40, (y+255), 90,40,5,"icons\\chest.png");
			chest[0].start();
	  }
	  
	  private void strip() {
		  createLevel(240, 180, 80, 30, 10, 5, 0, 50);
		  chest[0].initProperties(600, 290,90, 50,5,"icons\\chest.png");
			chest[0].start();
	  }
	 private void stageWaterMelon() {
		
		createLevel(200, 100, 80, 40, 10, 3, 1, 1);
		createLevel(280, 220, 80, 40, 8, 2, 1, 1);
		createLevel(360, 320, 80, 40, 6, 2, 1, 1);
		
		
		
	}

	private void stageSimple() {
		  createLevel(240,50,80,40,6, 7,50,50);
			
			chest[0].initProperties(700, 185,100, 40,5,"icons\\chest.png");
			chest[0].start();
			
			
			chest[1].initProperties(430, 455,100, 40,5,"icons\\chest.png");
			chest[1].start();
		
	}

	private void stageHell() {
		  createLevel(280,40,40,80,2, 3,100,1);
			createLevel(320,140,100,40,1, 1,1,1);
			
			createLevel(480,140,40,80,1, 3,1,1);
			createLevel(520,140,100,40,1, 3,1,60);
			
			createLevel(320,140,100,40,1, 1,1,1);
			
			createLevel(640,200,40,80,1, 3,1,1);
			chest[0].initProperties(680, 400,100, 40,5,"icons\\chest.png");
			chest[0].start();
			
			createLevel(800,250,40,80,1, 3,1,1);
			chest[1].initProperties(840, 450,100, 40,5,"icons\\chest.png");
			chest[1].start();
		
	}

	int rand(int value)
		{
			Random rand = new Random();

			int  n = rand.nextInt(value) + 1;
			return n;
			
		}
	  
	  public void createLevel(int x, int y,int width, int height,int total_hor_tiles,int total_ver_tiles,int block_gap_x,int block_gap_y)
	  {
		  
		  System.out.println("block counter : "+block_counter);
		  int loop_count=0;
		 
		  int rangex =block_counter+total_hor_tiles;
		  int rangey =block_counter+total_ver_tiles;
		 
		  System.out.println("rangeX : "+rangex);
		  System.out.println("rangey : "+rangey);
		  

		  int p=block_counter;
		  
		  
		  for(int i=p;i<rangex;i++)
		  {
			  for(int j=0;j<total_ver_tiles;j++)
			  {
				  block[block_counter].initProperties(x+(width+block_gap_x)*loop_count, y+(height+block_gap_y)*(j), width, height,rand(5),10 );
				  
				  block_counter++;
			  }
			  loop_count++;
			  
			 
		  }
		  //block_counter=p;
		  System.out.println(block_counter);
		 
	  }
	  
	  void levelSelecter()
	  {
		  int n =rand(6);
		  
		  switch(n)
		  {
		  case 1:
			  hand_bonus();
			  break;
			  
		  case 2:
			  level_p();
			  break;
			  
		  case 3:
			  strip();
			  break;
			  
		  case 4:
			  stageWaterMelon();
			  break;
			  
		  case 5:
			  stageSimple();
			  break;
			  
		  case 6:
			  stageHell();
			  break;
			  
		   default:
			   hand_bonus();
			  
		  }
	  }

}
