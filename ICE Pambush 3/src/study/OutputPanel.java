package study;

import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

import main.*;
import tool.*;
import games.math.*;

/**
 * 学習用出力パネル
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_19_2008_Tue
 */

public class OutputPanel extends JComponent implements ActionListener{
	
	int mode=0;
	
	public GameParameter gp;
	public MainPanel mp1;
	public MainPanel2 mp2;
	public MainPanel3 mp3;
	public PixPanel pp;
	Button next,before,ok;
	Label label;
	TextField tf;
	
	int file_count=0,file_num=0;
	int width=0,height=0;
	int sx=-1,sy=-1;
	long obj_num=0;
	boolean obj[];
	
	public OutputPanel() throws Exception{
		this.gp=new GameParameter();
		readFile();
		mp1=new MainPanel(gp); mp2=new MainPanel2(gp); mp3=new MainPanel3(gp);
		pp=new PixPanel(gp);
		set();
	}
	
	private void set(){
		Container c[]=new Container[15];
		for(int i=0;i<c.length;i++){
			c[i]=new Container();
			c[i].setLayout(new FlowLayout(FlowLayout.CENTER));
		}
		
		this.next=new Button("　次へ　"); next.addActionListener(this);
		this.before=new Button("　前へ　"); before.addActionListener(this);
		this.label=new Label(file_num+"  (0-"+file_count+")");
		this.ok=new Button("　OK　"); ok.addActionListener(this);
		this.tf=new TextField(); tf.setPreferredSize(new Dimension(40,20));
		
		c[0].add(mp1); c[1].add(mp2); c[2].add(mp3);
		c[3].add(pp);
		if(mode<3)c[4].add(c[mode]); else c[4].add(c[0]);
		c[4].add(c[3]);
		c[7].setLayout(new GridLayout(2,1));
		c[7].add(c[5]); c[7].add(c[6]);
		
		c[8].add(label);
		c[9].add(before); c[9].add(new Label(" ")); c[9].add(next);
		c[10].add(tf); c[10].add(ok);
		c[11].setLayout(new GridLayout(3,1));
		c[11].add(c[8]); c[11].add(c[9]); c[11].add(c[10]);
		
		this.setLayout(new BorderLayout());
		add(c[4],BorderLayout.NORTH);
		add(c[7],BorderLayout.CENTER);
		add(c[11],BorderLayout.SOUTH);
	}
	
	private void readFile() throws Exception{
		String s=""; int x=-1,y=-1;
		BufferedReader br = new BufferedReader( new InputStreamReader(
				new FileInputStream("data/count.txt"),"MS932"));
		file_count=Integer.parseInt(br.readLine());
		br.close();
		
		br = new BufferedReader( new InputStreamReader(
				new FileInputStream("data/pix/"+file_num+".txt"),"MS932"));
		width=Integer.parseInt(br.readLine());
		height=Integer.parseInt(br.readLine());
		gp.set(0, 0, width, height);
		for(int i=0;i<gp.height;i++){
			for(int j=0;j<gp.width;j++){
				gp.pix[j+i*gp.width]=Integer.parseInt(br.readLine());
			}
		}
		br.close();
		
		
		br = new BufferedReader( new InputStreamReader(
				new FileInputStream("data/map/"+file_num+".txt"),"MS932"));
		width=Integer.parseInt(br.readLine());
		height=Integer.parseInt(br.readLine());
		if((s=br.readLine()).length()!=0) sx=Integer.parseInt(s);
		if((s=br.readLine()).length()!=0) sy=Integer.parseInt(s);
		if(sx!=-1 && sy!=-1){
			gp.goal=new Vector2d();
			gp.goal.set(sx,sy);
		}
		if((s=br.readLine()).length()!=0) sx=Integer.parseInt(s);
		if((s=br.readLine()).length()!=0) sy=Integer.parseInt(s);
		if(sx!=-1 && sy!=-1){
			gp.study=new Vector2d();
			gp.study.set(sx,sy);
		}
		
		if((s=br.readLine()).length()!=0) x=Integer.parseInt(s);
		if((s=br.readLine()).length()!=0) y=Integer.parseInt(s);
		if(x!=-1 && y!=-1){
			gp.pac=new Vector2d();
			gp.pac.set(x, y);
		}
		x=-1;y=-1;
		for(int i=0;i<4;i++){
			if((s=br.readLine()).length()!=0) x=Integer.parseInt(s);
			if((s=br.readLine()).length()!=0) y=Integer.parseInt(s);
			if(x!=-1 && y!=-1){
				gp.ghost[i]=new Vector2d();
				gp.ghost2[i]=new Vector2d();
				gp.ghost[i].set(x, y);
				gp.ghost2[i].set((x+gp.gameWidth)%gp.mapWidth,y);
			}
			x=-1;y=-1;
		}
		
		int num=0;
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				s=br.readLine(); num=s.length();
				obj_num=Integer.parseInt(s);
				
				while(obj_num!=0){
					num--;
					if(obj_num%10!=0) setObj(j,i,num,obj_num%10);
					obj_num/=10;
				}
			}
		}
		FindArea fa = new FindArea(gp);
		FindCornerWarp fcw =  new FindCornerWarp(gp);
		FindWall fw = new FindWall(gp);
		FindNearest fn = new FindNearest(gp);
		fa.gameArea(); fcw.run(); fn.findNextP();
		fw.setWall(); fw.setWall2(); fw.setGhostBase();
		fcw.warpSide();
		fa.ppArea();		
		br.close();
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				fn.run(j, i);
			}
		}
		fn.findGhost(gp.ghost, 1);
		fn.findGhost(gp.ghost2, 2);
		fn.findGaP();
		
		if(label!=null) label.setText(file_num+"  (0-"+file_count+")");
	}
	
	private void setObj(int x,int y,int num,long obj){
		switch(num){
		case 0 :
			if(obj==1) gp.node[y][x].set2(gp.num_pill);
			if(obj==2) gp.node[y][x].set2(gp.num_pp);
			if(obj==3) gp.node[y][x].set2(gp.num_back);
			break;
		case 1 :
			if(obj==1) gp.node[y][x].set2(gp.num_ghost[0]);
			if(obj==2) gp.node[y][x].set2(gp.num_ghost[1]);
			if(obj==3) gp.node[y][x].set2(gp.num_ghost[2]);
			if(obj==4) gp.node[y][x].set2(gp.num_ghost[3]);
			break;
		case 2 : gp.node[y][x].set2(gp.num_edible); break;
		case 3 : gp.node[y][x].set2(gp.num_item); break;
		case 4 : gp.node[y][x].setPPArea(); break;
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==next){
			file_num=(file_num+1)%(file_count+1);
		}
		if(ae.getSource()==before){
			file_num=file_num-1;
			if(file_num<0)file_num=file_count;
		}
		if(ae.getSource()==ok){
			if(tf.getText().length()!=0){
				int num=Integer.parseInt(tf.getText());
				if(num<file_count+1)
					file_num=num;
				tf.setText("");
			}
		}
		try {readFile();}
		catch (Exception e) {e.printStackTrace();}
		mp1.repaint(); mp2.repaint(); mp3.repaint();
		pp.repaint();
	}
	
	/**メインメソッド*/
	public static void main(String args[]) throws Exception{
		new MakeFrame(0,0,"Study",true,false).make(new OutputPanel());
	}
}
