package main;

import games.math.Vector2d;

import javax.swing.*;

import tool.GraphicTool;

import java.awt.*;

/**
 * ゲーム状況表示(パックマン中心)
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_05_2008_Tue
 */

public class MainPanel3 extends JComponent{
	int width=100,height=100;
	GraphicTool gt;
	GameParameter gp;
	Graphics g;
	
	public MainPanel3(GameParameter gp){
		this.gp=gp;
		this.width=gp.width; this.height=gp.height;
		setPreferredSize(new Dimension(gp.gameWidth*8, gp.mapHeight*8));
		this.gt=new GraphicTool();
	}
	
	/**表示更新*/
	public void update(){
		repaint();
	}
	
	private int px=0;
	/**表示*/
	public void paintComponent(Graphics gn){
		this.g=gn;
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, gp.gameWidth*8, gp.mapHeight*8);
		
		if(gp.pac==null) px=0;
		else px=(int)gp.pac.x-gp.gameWidth/2;
		
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=px;j<px+gp.gameWidth;j++){
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
		
		int nx=13,ny=10;
		setObj(Color.RED,gt.FRECT,nx,ny,8);
		for(int i=0;i<4;i++)
			if(gp.node[ny][nx].nextP[i]!=null){
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
			
			setObj2(Color.RED,gt.DOVAL,gp.pac,16);
			setObj2(Color.BLUE,gt.DOVAL,gp.pac,14);
		}
	}
	
	private int shuusei(int x){
		return x-px;
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
