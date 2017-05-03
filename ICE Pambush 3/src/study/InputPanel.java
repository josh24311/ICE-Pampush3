package study;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import main.*;
import tool.*;
import games.math.*;
import tool.GraphicTool;

/**
 * 学習用入力パネル
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_19_2008_Tue
 */

public class InputPanel extends JComponent implements MouseListener{
	GameParameter gp;
	int bc=-1250856;
	Graphics g;
	GraphicTool gt=new GraphicTool();
	MainPanel2 mp;
	public int sx=-1,sy=-1;
	Vector2d G;
	
	/**標準コンストラクタ*/
	public InputPanel(GameParameter gp,Vector2d G){
		this.gp=gp;
		this.G=G;
		addMouseListener(this);
		setPreferredSize(new Dimension(gp.mapWidth*8,gp.mapHeight*8));
		mp=new MainPanel2(gp);
	}
	
	
	/**ドライバ用コンストラクタ*/
	public InputPanel(){
		this.gp = new GameParameter();
		gp.set(0, 0, 223, 275);
		addMouseListener(this);
		setPreferredSize(new Dimension(gp.mapWidth*8,gp.mapHeight*8));
		mp=new MainPanel2(new GameParameter());
	}
	
	/**更新*/
	public void update(){
		repaint();
	}
	
	/**ドライバ*/
	public static void main(String args[]){
		new MakeFrame().make(new InputPanel());
	}

	public void mouseClicked(MouseEvent e) {
		gp.goal=G;
		sx=e.getPoint().x/8; sy=e.getPoint().y/8;
		if(gp.node[sy][sx].can_point){
			if(gp.study==null) gp.study=new Vector2d();
			gp.study.set(sx,sy);
		}
		repaint();
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	
	/**表示*/
	public void paintComponent(Graphics gn){
		this.g=gn;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, gp.mapWidth*8, gp.mapHeight*8);
		
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				if(gp.node[i][j].game_area){
					if(!gp.node[i][j].can_point) setObj(Color.GRAY,gt.FRECT,j,i,8);
					if(gp.node[i][j].wall){
						setObj(Color.BLACK,gt.FRECT,j,i,7);
						if(gp.node[i][j].base)
							setObj(Color.RED,gt.DRECT,j,i,7);
						else setObj(Color.GREEN,gt.DRECT,j,i,7);
					}
					if(gp.node[i][j].pp_area) setObj(Color.YELLOW,gt.DRECT,j,i,7);
					if(gp.node[i][j].edible) setObj(Color.BLUE,gt.FRECT,j,i,8);
					if(gp.node[i][j].corner)setObj(Color.RED,gt.FRECT,j,i,6);
					if(gp.node[i][j].corner2) setObj(Color.GREEN,gt.FRECT,j,i,6);
					if(gp.node[i][j].warp)setObj(Color.BLUE,gt.FRECT,j,i,6);
					if(gp.node[i][j].warpSide) setObj(Color.BLUE,gt.DRECT,j,i,6);
					if(gp.node[i][j].warpSide2) setObj(Color.ORANGE,gt.DRECT,j,i,6);
					if(gp.node[i][j].pill){
						if(gp.realArea(j))
							setObj(Color.WHITE,gt.DRECT,j,i,3);
						else
							setObj(Color.YELLOW,gt.DRECT,j,i,3);
					}
					if(gp.node[i][j].pp){
						if(gp.realArea(j))
							setObj(Color.WHITE,gt.DRECT,j,i,7);
						else 
							setObj(Color.YELLOW,gt.DRECT,j,i,7);
					}
					if(gp.node[i][j].item)setObj(Color.WHITE,gt.DRECT,j,i,8);
					if(gp.node[i][j].ghost[0]) setObj(Color.RED,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[1]) setObj(Color.PINK,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[2]) setObj(Color.CYAN,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[3]) setObj(Color.ORANGE,gt.FRECT,j,i,8);
				}
				else setObj(Color.GREEN,gt.DRECT,j,i,7);
			}
		}
		
		int nx=31,ny=25;
		setObj(Color.RED,gt.FRECT,nx,ny,8);
		for(int i=0;i<4;i++)
			if(gp.node[ny][nx].nextP[i]!=null){
				//System.out.println(i);
				setObj(Color.WHITE,gt.FRECT,(int)gp.node[ny][nx].nextP[i].x,
						(int)gp.node[ny][nx].nextP[i].y,8);
			}
		
		if(gp.pac!=null){
			setObj(Color.YELLOW,gt.FRECT,(int)gp.pac.x, (int)gp.pac.y,8);
			for(int i=0;i<4;i++){
				if(gp.ghost[i]!=null)
					setObj(Color.YELLOW,gt.FRECT,gp.ghost[i],4);
				if(gp.ghost2[i]!=null)
					setObj(Color.WHITE,gt.FRECT,gp.ghost2[i],4);
			}			
			if(gp.nearest[gp.num_pill]!=null){
				setObj(Color.RED,gt.DOVAL,gp.nearest[gp.num_pill],8);
				setLine(Color.YELLOW,1,gp.pac,gp.nearest[gp.num_pill]);
			}
			if(gp.nearest[gp.num_pill2]!=null){
				setObj(Color.GREEN,gt.DOVAL,gp.nearest[gp.num_pill2],8);
				setLine(Color.YELLOW,1,gp.pac,gp.nearest[gp.num_pill2]);
			}
			if(gp.nearest[gp.num_pp]!=null){
				setObj(Color.RED,gt.DOVAL,gp.nearest[gp.num_pp],8);
				setLine(Color.YELLOW,3,gp.pac,gp.nearest[gp.num_pp]);
			}
			if(gp.nearest[gp.num_edible]!=null){
				setObj(Color.RED,gt.DOVAL,gp.nearest[gp.num_edible],8);
				setLine(Color.BLUE,2,gp.pac,gp.nearest[gp.num_edible]);
			}
			if(gp.nearest[gp.num_corner]!=null)
				setObj(Color.GREEN,gt.FRECT,gp.nearest[gp.num_corner],8);
			if(gp.nearest[gp.num_warp]!=null)
				setObj(Color.GREEN,gt.FRECT,gp.nearest[gp.num_warp],8);
			if(gp.nearest[gp.num_GaP]!=null)
				setObj(Color.BLUE,gt.FOVAL,gp.nearest[gp.num_GaP],8);
			if(gp.nearest[gp.num_nearGhost]!=null){
				setLine(Color.RED,2,gp.pac,gp.nearest[gp.num_nearGhost]);
			}
			if(gp.goal!=null){
				setObj(Color.WHITE,gt.FRECT,(int)gp.goal.x,(int)gp.goal.y,8);
			}
			if(gp.study!=null){
				setObj(Color.BLUE,gt.FRECT,(int)gp.study.x,(int)gp.study.y,8);
			}
			setObj2(Color.RED,gt.DOVAL,gp.pac,16);
			setObj2(Color.BLUE,gt.DOVAL,gp.pac,14);
		}
	}
	
	private void setObj(Color color,int num,int x,int y,int size){
		gt.setObj(g, color, num, x*8+(8-size)/2, y*8+(8-size)/2, size);
	}
	private void setObj(Color color,int num,Vector2d point,int size){
		setObj(color,num,(int)point.x,(int)point.y,size);
	}
	
	private void setObj2(Color color,int num,int x,int y,int size){
		gt.setObj(g, color, num, (x-size/2)*8, (y-size/2)*8, size*8);
	}
	
	private void setObj2(Color color,int num,Vector2d point,int size){
		setObj2(color,num,(int)point.x,(int)point.y,size);
	}
	
	private void setLine(Color color,int num,int x1,int y1,int x2,int y2){
		gt.setLine(g, color, num, x1*8+4, y1*8+4, x2*8+4, y2*8+4);
	}
	private void setLine(Color color,int num,Vector2d point1,Vector2d point2){
		if(point1!=null && point2!=null)
			setLine(color,num,(int)point1.x,(int)point1.y,(int)point2.x,(int)point2.y);
	}
	
}
