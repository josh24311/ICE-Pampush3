package main;

/**
 * �{�^������
 * @author�@ChotaTokuyama
 * @version 2.2
 * @since�@Dec_02_2008_Mon
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class PacMover {
	/**���{�b�g�N���X*/ private Robot robot;
	/**�{�^���ԍ�*/ private int now,next;
	/**���씻��*/ public boolean move=true;
	public PacMover()throws Exception{
			this.robot=new Robot();
			this.next=KeyEvent.VK_UP;
			this.now=KeyEvent.VK_UP;
	}
	
	/**�{�^������*/
	public void move(int direction,boolean can){
		
		//System.out.println(direction);
		switch(direction){
		default: break;
		case 1: next=KeyEvent.VK_UP; break;
		case 2: next=KeyEvent.VK_RIGHT; break;
		case 3: next=KeyEvent.VK_DOWN; break;
		case 4: next=KeyEvent.VK_LEFT; break;
		}
		
		if(now!=next || !can){
			//System.out.println("������");
			robot.keyRelease(KeyEvent.VK_UP);
			robot.keyRelease(KeyEvent.VK_RIGHT);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyRelease(KeyEvent.VK_LEFT);
		}
		if(can){
			//System.out.println("������");
			robot.keyPress(next);
		}
		now=next;
	}
}
