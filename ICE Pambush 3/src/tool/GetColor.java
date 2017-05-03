package tool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

/**
 * User: Chota Tokuyama
 * Date: 28-July-2008
 *
 */

public class GetColor extends JComponent implements MouseListener{
	
	int left=530,top=274;
	//int left=1820,top=200;
	int width=223,height=275;
	
	int bigSize=2;
	
	int x,y;
	
	int[][] pix;
	int[] Pixels;
	
	Graphics g;
	
	public GetColor()throws Exception{
		pix = new int[height][width];
		Pixels=new int[width*height];
		
		getPixels();
		setPreferredSize(new Dimension(width*bigSize, height*bigSize));
		MakeFrame frame = new MakeFrame(0,0,"GetColor");
		frame.make(this);
		addMouseListener(this);
	}
	
	public void mouseClicked(MouseEvent m) {
		this.x=m.getPoint().x;
		this.y=m.getPoint().y;
		
		g.setColor(new Color(pix[y/bigSize][x/bigSize]));
		g.fillRect(0, 0, 100, 100);
		System.out.println("x:"+x+"	y:"+y+"	color:"+pix[y/bigSize][x/bigSize]);
	}
	
	public void mouseEntered(MouseEvent m) {}
	public void mouseExited(MouseEvent m) {}
	public void mousePressed(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
	
	public void getPixels()throws Exception{
		Robot robot = new Robot();
		BufferedImage im = robot.createScreenCapture(new Rectangle(left, top, width, height));
        im.getRGB(0, 0, width, height, Pixels, 0, width);
        for(int i=0;i<height;i++) for(int j=0;j<width;j++)pix[i][j]=Pixels[i*width+j];
	}
	
	public void paintComponent(Graphics gn){
		this.g=gn;
		
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				g.setColor(new Color(pix[i][j]));
				g.fillRect(j*bigSize, i*bigSize, bigSize, bigSize);
			}
		}
	}
	
	public static void main(String args[])throws Exception{
		new GetColor();
	}
}
