package mygame;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main_menu {
	static boolean m_click=false,startgame=false,instruction = false;;
	static Image img[]=new Image[11];
	static String[] path = 
		{
		"icons\\resume.png",
		"icons\\start.png",
		"icons\\option.png",
		"icons\\exit.png",
		"icons\\resume_active.png",
		"icons\\start_active.png",
		"icons\\option_active.png",
		"icons\\exit_active.png",
		"icons\\go_back.png",
		"icons\\how_to_play.png",
		"icons\\main_menu_bk.png"
		}; 
	static Graphics g;
	static Canvas c;	
	static JFrame frame;
	static Main_menu obj=new Main_menu();
	static Point currentmouseloc=null;

	public static void main_menu() 
	{	
		initialize();
		obj.iniimg();
		/*for(int i=0;i<4;i++)
		obj.show(img[i], 400, 100+(i*120));*/
		obj.mousehandle();
		
		while(true&&startgame==false) {
			try {
		       Thread.sleep(80);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
			try {
				currentmouseloc = c.getMousePosition();	
				System.out.println(currentmouseloc);
			if(instruction==false) {
			//	obj.show(img[10], 0, 0);
				if(currentmouseloc.getX()>405 && currentmouseloc.getX()<600) 
				{ 
					for(int k=0;k<4;k++) 
					{
						if (currentmouseloc.getY()>100+(k*120) && currentmouseloc.getY()<165+(k*120))
						{
							obj.show(img[k+4], 400, 100+((k)*120));
							
							if(m_click) 
							{
								switch(k) 
								{
								case 0:
									obj.resume_funct();
									break;
								case 1:
									obj.start_funct();
									break;
								case 2:
									obj.option_funct();
									break;
								case 3:
									obj.exit_funct();
									break;
								default:
									System.out.println("value outside of case range");								
								}
							}
							break;
						}
						else
						{
							for(int i=0;i<4;i++)
							obj.show(img[i], 400, 100+(i*120));
									
						}
					}
				}
				else
				{
					for(int i=0;i<4;i++)
					obj.show(img[i], 400, 100+(i*120));
							
				}
			}
			}catch(Exception e) {System.out.println("mouse outside windows frame");}			
		}				
	}
	
	static void initialize (){
		frame =new JFrame();
		frame.setSize(1000,700);
		frame.setTitle("Main Menu");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		c=new Canvas();
		c.setSize(1000,700);
		
		frame.add(c);
		frame.pack();
		//c.addKeyListener(this);
		//c.setFocusable(true);	
		g=c.getGraphics();
	}
		
	public void iniimg ()
	{
		try {
			
				for(int i=0;i<11;i++) 
				{
					img[i] = new ImageIcon(getClass().getResource(path[i])).getImage();
				
					System.out.println(path[i]+"\t"+img[i]);
				}
			}
		catch(Exception e)
		{
		
			System.out.println("\nimage ini ma kai error vayo | \n");
			//System.out.println("no image found at path "+path);
			System.exit(1);
		}
		//System.out.println(path);
								
	}
	
	public void show (Image img,int x,int y) 
	{
	
		g.drawImage(img,x,y,null);	
	
	}
	
	public void resume_funct()
	{
		
	}
	public void start_funct()
	{
		startgame=true;
		System.out.println(startgame);
	}
	public void option_funct ()  throws InterruptedException
	{
		instruction=true;
			obj.show(img[8], 750, 20);
			obj.show(img[9], 50, 50);
		
		
		if(m_click) 
		{Thread.sleep(1000);
			instruction=false;
		}
			
	}
	public void exit_funct()
	{
		System.exit(0);
	}
	
	public void mousehandle() {
		c.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				m_click=false;
				System.out.println("mouse staus:"+m_click);

			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				m_click=true;
				System.out.println("mouse staus:"+m_click);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}
}
