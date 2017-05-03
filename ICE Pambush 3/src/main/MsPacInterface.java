package main;

import tool.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import algorithm.*;
import study.*;

/**
 * 
 * @author ChotaTokuyama
 * @version 2.2
 * @since Dec_02_2008_Tue
 */

public class MsPacInterface {
	
	/***/
	public ES es;
	
	/***/
	int
		blinky = -65536,
		pinky = -18210,
		inky = -16711714,
		sue = -18361,
		pacMan = -256,
		edible = -14605858,
		pill1 = -2171170,
		pill2 = -256,
		pill3 = -65536,
		pill4 = -2171170,
		back = -16777216;
	
	/***/
	private int left=600,top=300;
	private int width=223,height=275;
	
	/***/
		public boolean demo=false;
	
	/***/
		public static boolean study=false;
		
	/***/
		public static Dijkstra dij;
		
	/***/
		public AutoSet auto;
		
	/***/
		public static GameParameter gp;
		
	/***/
		public FindObject fo;
		
	/***/
		public FindCornerWarp fcw;
	
	/***/
		public FindWall fw;
		
	/***/
		public FindNearest fn;
		
	/***/
		public static FindArea fa;
	
	/***/
		public MainPanel mp;
		
	/***/
		public MainPanel2 mp2;
	
	/***/
		public MainPanel3 mp3;
		
	/***/
		public DirectionComponent dc;
		
	/***/
		public InputSystem is;
		
	/***/
		public Agent agent;
	
	/***/
		public PacMover pm;
		
	/***/
		public ThreadGroup tg[];
		
	/***/
	private void set(){
		gp.set(left, top, width, height);
		gp.setColor(blinky,pinky,inky,sue,pacMan,edible,
				pill1,pill2,pill3,pill4,back);
	}
		
	public MsPacInterface()throws Exception{
		
		/***/
		this.auto = new AutoSet();
		left=auto.search_x;
		top=auto.search_y;
		/**                     */
		
		this.robot = new Robot();
		this.gp = new GameParameter();
		this.fo = new FindObject(gp);
		this.fcw = new FindCornerWarp(gp);
		this.fw = new FindWall(gp);
		this.fn = new FindNearest(gp);
		this.fa = new FindArea(gp);
		this.agent = new Agent(gp);
		this.pm = new PacMover();
		this.tg = new ThreadGroup[10];
		this.dij = new Dijkstra();
		this.es = new ES();
		set();
	}
	
	/***/
	private void setFrame(){
		int FL=31;
		this.mp = new MainPanel(gp);
		this.mp2 = new MainPanel2(gp);
		this.mp3 = new MainPanel3(gp);
		this.dc = new DirectionComponent();
		this.is = new InputSystem(gp);
		MakeFrame frame = 
			new MakeFrame(0, 0, "MsPacInterface", true, false);
		frame.make(mp);
		if(!study){
			MakeFrame direction = 
				new MakeFrame(gp.width+FL, 0, "MsPacInterface", true, false);
			direction.make(dc);
		}
		else{
			MakeFrame s =
				new MakeFrame(gp.width+FL, 0, "Study", true, false);
			s.make(is);
		}
		if(demo){
			MakeFrame frame2 = 
				new MakeFrame(0, gp.height+FL,
						"MsPacInterface2", true, false);
			frame2.make(mp2);
			MakeFrame frame3 = 
				new MakeFrame(0, gp.height*2+FL*2,
						"MsPacInterface3", true, false);
			frame3.make(mp3);
		}
	}
	
	/***/
	Robot robot;
	
	/***/
	public void getPixels()throws Exception{
		BufferedImage im =
			robot.createScreenCapture(
					new Rectangle(gp.left+fo.first_x, gp.top+fo.first_y,
							gp.width, gp.height));
		im.getRGB(0, 0, gp.width, gp.height, gp.pix, 0, gp.width);
	}
	
	/***/
	public int scanFirst()throws Exception{
		int score;
		getPixels();
		score = fo.run();
		//System.out.println(score);
		return score;
	}
	
	/***/
	public void startThread(int num){
		this.tg[num]=new ThreadGroup(this,num);
		this.tg[num].run();
	}
	
	/***/
	public static void main(String args[]) throws Exception{
		Timer time = new Timer();
		MsPacInterface mpi = new MsPacInterface();
		mpi.setFrame();
		
	
		while(!mpi.auto.find){
			mpi.auto.setPar();
			mpi.gp.left=mpi.auto.search_x;
			mpi.gp.top=mpi.auto.search_y;
		}
		mpi.auto.freeArray();
		
		mpi.gp.costRatio = 41.046112199812974;
		mpi.gp.distBorder = 8;
		while(true){
			time.start();
			mpi.scanFirst();
			
			if(mpi.gp.find){
				if(mpi.gp.scan[0]){
					mpi.gp.scan(0);
					mpi.fw.setWall();
					mpi.startThread(0);
					dij.update();
					//System.out.println("scan");
				}
				
				if(mpi.gp.scanCW(mpi.gp.pac)){
					mpi.fcw.run();
					mpi.fn.findNextP();
				}
				if(mpi.gp.pac!=null){
					if(mpi.gp.scan[1] && mpi.gp.findCW && !mpi.gp.scanCW(mpi.gp.pac)){
						mpi.gp.scan(1);
						mpi.startThread(3);
						mpi.startThread(4);
						mpi.startThread(5);
						mpi.startThread(6);
						mpi.startThread(7);
					}
					
					for(int i=0;i<mpi.gp.mapHeight;i++){
						for(int j=0;j<mpi.gp.mapWidth;j++){
							mpi.fn.run(j, i);
						}
					}
					mpi.fn.findGhost(mpi.gp.ghost, 1);
					mpi.fn.findGhost(mpi.gp.ghost2, 2);
					mpi.fn.findGaP();
					mpi.agent.decide();
				}
				
				mpi.pm.move(mpi.agent.direction,mpi.is.move);
				mpi.dc.update(mpi.agent.direction);
			}
			else{
				mpi.agent.direction=0;
			}
			
			mpi.mp.update();
			if(mpi.demo){
				mpi.mp2.update();
				mpi.mp3.update();
				
			}
			
			time.end();
			//System.out.println(time.time);
			Thread.sleep(50);
		}
	}
}
