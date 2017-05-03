package main;

import javax.swing.*;
import java.awt.*;

/**
 * PIXレベルパネル
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_22_2008_Tue
 */

public class PixPanel extends JComponent{
	GameParameter gp;
	
	public PixPanel(GameParameter gp){
		this.gp=gp;
		setPreferredSize(new Dimension(gp.gameWidth*8, gp.gameHeight*8));
	}
	
	public void paintComponent(Graphics g){
		for(int i=0;i<gp.height;i++){
			for(int j=0;j<gp.width;j++){
				g.setColor(new Color(gp.pix[j+i*gp.width]));
				g.fillRect(j, i, 1, 1);
			}
		}
	}
}
