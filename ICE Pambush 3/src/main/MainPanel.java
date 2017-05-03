package main;

import games.math.Vector2d;

import javax.swing.*;

import tool.GraphicTool;

import java.awt.*;

/**
 * ゲーム状況表示(メイン)
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_05_2008_Tue
 */

public class MainPanel extends JComponent{
	int width=100,height=100;
	GraphicTool gt;
	GameParameter gp;
	Graphics g;
	
	public MainPanel(GameParameter gp){
		this.gp=gp;
		this.width=gp.width; this.height=gp.height;
		setPreferredSize(new Dimension(gp.gameWidth*8, gp.gameHeight*8));
		this.gt=new GraphicTool();
	}
	
	/**表示更新*/
	public void update(){
		repaint();
	}
	
	/**表示*/
	public void paintComponent(Graphics gn){
		this.g=gn;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, gp.mapWidth*8, gp.mapHeight*8);
		
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=gp.REAL[0];j<gp.REAL[1];j++){
				if(gp.node[i][j].game_area){
					if(!gp.node[i][j].can_point) setObj(Color.GRAY,gt.FRECT,j,i,8);
					if(gp.node[i][j].wall){
						setObj(Color.BLACK,gt.FRECT,j,i,7);
						if(gp.node[i][j].base)
							setObj(Color.RED,gt.DRECT,j,i,7);
						else setObj(Color.GREEN,gt.DRECT,j,i,7);
					}
					if(gp.node[i][j].edible) setObj(Color.BLUE,gt.FRECT,j,i,8);
					if(gp.node[i][j].corner)setObj(Color.RED,gt.FRECT,j,i,6);
					if(gp.node[i][j].corner2) setObj(Color.GREEN,gt.FRECT,j,i,6);
					if(gp.node[i][j].warpSide) setObj(Color.BLUE,gt.DRECT,j,i,6);
					if(gp.node[i][j].warpSide2) setObj(Color.ORANGE,gt.DRECT,j,i,6);
					if(gp.node[i][j].pill)setObj(Color.WHITE,gt.DRECT,j,i,3);
					if(gp.node[i][j].pp)setObj(Color.WHITE,gt.DRECT,j,i,7);
					if(gp.node[i][j].item)setObj(Color.WHITE,gt.DRECT,j,i,8);
					if(gp.node[i][j].ghost[0]) setObj(Color.RED,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[1]) setObj(Color.PINK,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[2]) setObj(Color.CYAN,gt.FRECT,j,i,8);
					if(gp.node[i][j].ghost[3]) setObj(Color.ORANGE,gt.FRECT,j,i,8);
				}
				else setObj(Color.GREEN,gt.DRECT,j,i,7);
			}
		}
		
		if(gp.pac!=null){
			setObj(Color.YELLOW,gt.FRECT,(int)gp.pac.x, (int)gp.pac.y,8);
			for(int i=0;i<4;i++){
				if(gp.ghost[i]!=null)
					setObj(Color.YELLOW,gt.FRECT,gp.ghost[i],4);
			}			
			if(gp.nearest[gp.num_nearGhost]!=null){
				setLine(Color.RED,2,gp.pac,gp.nearest[gp.num_nearGhost]);
			}
			if(gp.goal!=null){
				setObj(Color.WHITE,gt.FRECT,(int)gp.goal.x,(int)gp.goal.y,8);
			}
			/*
			if(gp.study!=null){
				setObj(Color.BLUE,gt.FRECT,(int)gp.study.x,(int)gp.study.y,8);
			}
			*/
		}
	}
	
	private int shuusei(int x){
		if(!gp.realArea(x)) return (x+gp.gameWidth)%gp.mapWidth-gp.REAL[0];
		else return x-gp.REAL[0];
	}
	
	private void setObj(Color color,int num,int x,int y,int size){
		gt.setObj(g, color, num,
				shuusei(x)*8+(8-size)/2, y*8+(8-size)/2, size);
	}
	private void setObj(Color color,int num,Vector2d point,int size){
		setObj(color,num,(int)point.x,(int)point.y,size);
	}
	
	private void setObj2(Color color,int num,int x,int y,int size){
		gt.setObj(g, color, num, (shuusei(x)-size/2)*8, (y-size/2)*8, size*8);
	}
	
	private void setObj2(Color color,int num,Vector2d point,int size){
		setObj2(color,num,(int)point.x,(int)point.y,size);
	}
	
	private void setLine(Color color,int num,int x1,int y1,int x2,int y2){
		gt.setLine(g, color, num,
				shuusei(x1)*8+4, y1*8+4, shuusei(x2)*8+4, y2*8+4);
	}
	private void setLine(Color color,int num,Vector2d point1,Vector2d point2){
		if(point1!=null && point2!=null)
			setLine(color,num,(int)point1.x,(int)point1.y,(int)point2.x,(int)point2.y);
	}
}
