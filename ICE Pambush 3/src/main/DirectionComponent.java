package main;

import javax.swing.*;
import java.awt.*;
import tool.*;

/**
 * ゲーム状況表示(メイン)
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　July_28_2008_Tue
 */

public class DirectionComponent extends JComponent{
	int size=120;
	int dir=0;
	
	JButton button = new JButton("Go");
	
	public DirectionComponent(){
		setPreferredSize(new Dimension(size, size));
	}
	
	public DirectionComponent(int size){
		this.size=size; this.size=size;
		setPreferredSize(new Dimension(size, size));
	}
	
	public void update(int dir){
		this.dir=dir;
		repaint();
	}
	
	public void paintComponent(Graphics gg){
		Graphics2D g = (Graphics2D) gg;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size, size);
		int[] x = new int[3];
	    int[] y = new int[3];
		
		g.setColor(Color.CYAN);
		if(dir==0){g.fillRect(size/3,size/3,size/3,size/3);}
		else {
            g.rotate((dir-1) * 90 * Math.PI / 180, size/2, size / 2);
            x[0] = size/3; y[0] = size/3;
            x[1] = size/2; y[1] = 0;
            x[2] = 2 * size/3; y[2] = size/3;
            g.fillPolygon(x, y, 3);
        }
	}
	
	public static void main(String args[]) throws Exception{
		MakeFrame frame = new MakeFrame();
		DirectionComponent dc = new DirectionComponent();
		frame.make(dc);
		for(int i=0;i<100;i++){
			dc.update(i%5);
			Thread.sleep(300);
		}
	}
}
