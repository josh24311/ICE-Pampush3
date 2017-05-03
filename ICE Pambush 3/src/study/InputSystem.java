package study;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import main.GameParameter;
import tool.*;
import games.math.*;

public class InputSystem extends JComponent implements ActionListener{
	
	GameParameter gp;
	InputPanel ip;
	Button scan,ok;
	public boolean move=true;
	int file_num;
	Vector2d G;
	
	public InputSystem(GameParameter gp){
		this.gp=gp;
		file_num=0;
		set();
	}
	
	private void set(){
		scan=new Button(" スキャン "); scan.addActionListener(this);
		ok=new Button(" OK "); ok.addActionListener(this);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(scan); add(ok);
	}
	
	MakeFrame input=null;
	public void actionPerformed(ActionEvent ae) {
		if(input==null){
			input=new MakeFrame(0,gp.height+50,"Study",false,false);
		}
		if(ae.getSource()==scan && move && gp.goal!=null){
			gp.study=null;
			G=new Vector2d();
			G.set(gp.goal);
			move=false;				
			ip=new InputPanel(gp,G);
			input.make(ip);
			ip.repaint();
		}
		if(ae.getSource()==ok && !move){
			move=true;
			if(gp.study!=null){
				try {writeFile();}
				catch (Exception e) {e.printStackTrace();}
			}
			input.close();
		}
	}
	
	/**ファイル書き込み*/
	private void writeFile() throws Exception{
		File f[] = new File[3];
		f[0]=new File("data");		f[0].mkdir();
		f[1]=new File("data/map");	f[1].mkdir();
		f[2]=new File("data/pix");	f[2].mkdir();
		
		BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(
				new FileOutputStream("data/count.txt"),"MS932"));
		bw.write(""+file_num+"");
		bw.close();
		
		bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("data/pix/"+file_num+".txt"),"MS932"));
		bw.write(gp.width+"\n"+gp.height+"\n");
		for(int i=0;i<gp.height;i++){
			for(int j=0;j<gp.width;j++){
				bw.write(gp.pix[j+i*gp.width]+"\n");
			}
		}
		bw.close();
		
		bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("data/map/"+file_num+".txt"),"MS932"));
		bw.write(gp.width+"\n"+gp.height+"\n");
		bw.write((int)G.x+"\n"+(int)G.y+"\n");
		bw.write((int)gp.study.x+"\n"+(int)gp.study.y+"\n");
		//bw.write(dir1+"\n"+dir2+"\n");
		if(gp.pac!=null){
			bw.write((int)gp.pac.x+"\n"+(int)gp.pac.y+"\n");
		} else bw.write("\n\n");
		
		for(int i=0;i<4;i++){
			if(gp.ghost[i]!=null){
				bw.write((int)gp.ghost[i].x+"\n"+(int)gp.ghost[i].y+"\n");
			} else bw.write("\n\n");
		}
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				if(gp.node[i][j].pill) bw.write("1");
				else if(gp.node[i][j].pp) bw.write("2");
				else if(gp.node[i][j].can_point) bw.write("3");
				else bw.write("0");
				
				if(gp.node[i][j].ghost[0]) bw.write("1");
				else if(gp.node[i][j].ghost[1]) bw.write("2");
				else if(gp.node[i][j].ghost[2]) bw.write("3");
				else if(gp.node[i][j].ghost[3]) bw.write("4");
				else bw.write("0");
				
				boolWrite(bw,gp.node[i][j].edible);
				boolWrite(bw,gp.node[i][j].item);
				boolWrite(bw,gp.node[i][j].pp_area);
				bw.write("\n");
			}
		}
		bw.close();
		//label2.setText(" "+file_num+" .txt に保存");
		System.out.println(file_num+".txt　に保存");
		System.out.println(G.x+"  "+G.y);
		System.out.println(gp.study.x+"  "+gp.study.y);
		file_num++;
	}
	
	/**判別式によるファイル書き込み*/
	private void boolWrite(BufferedWriter bw, boolean bool) throws Exception{
		if(bool) bw.write("1");
		else bw.write("0");
	}
}
