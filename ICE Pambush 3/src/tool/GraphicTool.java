package tool;

/**
 * User: Chota Tokuyama
 * Date: 29-October-2008
 */

import java.awt.*;

public class GraphicTool {
	
	public GraphicTool(){}
	
	public int FRECT=0,DRECT=1,FOVAL=2,DOVAL=3;
	
	public void setObj(Graphics g,Color color,int num,int x,int y,int size_w,int size_h){
		g.setColor(color);
		switch(num){
		case 0: g.fillRect(x, y, size_w, size_h);break;
		case 1: g.drawRect(x, y, size_w, size_h);break;
		case 2: g.fillOval(x, y, size_w, size_h);break;
		case 3: g.drawOval(x, y, size_w, size_h);break;
		}
	}
	
	public void setObj(Graphics g,Color color,int num,int x,int y,int size){
		setObj(g, color, num, x, y, size, size);
	}
	
	public void setObj(int lineSize,Graphics g,Color color,int num,int x,int y,int size_w,int size_h){
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(lineSize));
		setObj(g, color, num, x, y, size_w, size_h);
		g2.setStroke(new BasicStroke(1));
	}
	
	public void setObj(int lineSize,Graphics g,Color color,int num,int x,int y,int size){
		setObj(lineSize,g, color, num, x, y, size, size);
	}
	
	public void setLine(Graphics g,Color color,int num,int x1,int y1,int x2,int y2){
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(num));
		g2.setColor(color);
		g2.drawLine(x1, y1, x2, y2);
		g2.setStroke(new BasicStroke(1));
	}
}
