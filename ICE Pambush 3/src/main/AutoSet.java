package main;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 自動位置取得
 * @author YutaOzasa
 * @version 0.9
 * @since Jul_10_2009_Fri
 */

public class AutoSet {
	public int x,y;
	final public int w_size=28, h_size=31;
	final public int game_width=223, game_height=247;
	int game_w;
	int game_h;
	int width, height;
	
	final int wall=-18281, wall2=-14605858,wall3=-2189497;
	final int wall_num=220;
	int search_x=100, search_y=100;
	int[] pix;
	static long[][] disp;
	public static boolean find=false;
	
	
	public AutoSet() throws AWTException, IOException{
		setPar();
	}
	
	public void setPar() throws AWTException, IOException{
		Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		x=dim.width;
		y=dim.height;
		Point point = new Point(dim.width/2-x/2, dim.height/2-y/2);
		Rectangle bounds = new Rectangle(point.x, point.y, x, y);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(bounds);

		//配列確保
		pix = new int[bounds.width*bounds.height];
		disp = new long[bounds.width][bounds.height];
		
		game_w=dim.width;
		game_h=dim.height;
		
		//配列にキャプチャ画像を入れる
		image.getRGB(0, 0, bounds.width, bounds.height, pix, 0, bounds.width);
		for(int i=0;i<bounds.width;i++)
			for(int j=0;j<bounds.height;j++)
				disp[i][j]=pix[j*bounds.width+i];
		
		pix=null;
		
		width=bounds.width;
		height=bounds.height;
		searchPac();
	}
	
	public void searchPac(){

		loop:
		for(int i=0;i<width;i++)
			for(int j=0;j<height;j++){
				int temp1=0, temp2=0,temp3=0;
				if(i<=x-wall_num){
					if(disp[i][j]==wall || disp[i][j]==wall2 || disp[i][j]==wall3){
						for(int k=0;k<wall_num;k++){
							if(disp[i+k][j]==wall)
								temp1++;
							else if(disp[i+k][j]==wall2)
								temp2++;
							else if(disp[i+k][j]==wall3)
								temp3++;
								continue;
						}
					}
					else{
						continue;
					}
				}
				if(temp1>=wall_num || temp2>=wall_num || temp3>=wall_num){
					search_x=i+game_w/2-x/2+3;
					search_y=j+game_h/2-y/2-21;
					find=true;
					break loop;
				}
			}
	}
	
	public void freeArray(){
		disp=null;
	}
}




