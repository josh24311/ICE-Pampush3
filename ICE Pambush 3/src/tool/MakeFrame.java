package tool;

/**
 * User: Chota Tokuyama
 * Date: 11_September-2008
 *
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class MakeFrame extends JFrame implements WindowListener{
	int left=0,top=0;
	String title="";
	boolean exit=true;
	boolean ResizeAble=true;
	
	public MakeFrame(){}
	
	public MakeFrame(int left,int top){
		this.left=left;this.top=top;
	}
	
	public MakeFrame(String title){
		this.title=title;
	}
	
	public MakeFrame(boolean exit){
		this.exit=exit;
	}
	
	public MakeFrame(int left,int top,String title){
		this(left,top); 
		this.title=title;
	}
	
	public MakeFrame(int left,int top,boolean exit){
		this(left,top); 
		this.exit=exit;
	}
	
	public MakeFrame(int left,int top,String title,boolean exit){
		this(left,top,title); 
		this.exit=exit;
	}
	
	public MakeFrame(int left,int top,String title,boolean exit,boolean ResizeAble){
		this(left,top,title,exit);
		this.ResizeAble=ResizeAble;
	}
	
	public void make(){
		setLocation(left,top);
		setTitle(title);
		setVisible(true);
		if(exit==true) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(ResizeAble);
	}
	
	public void make(JComponent jc){
		setLocation(left,top);
		setTitle(title);
		add(jc);
		pack();
		setVisible(true);
		if(exit) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(ResizeAble);
	}
	
	public void make(JComponent jc1,JComponent jc2,int type){
		setLocation(left,top);
		setTitle(title);
		
		Container c[]=new Container[2];
		for(int i=0;i<c.length;i++){
			c[i]=new Container();
			c[i].setLayout(new FlowLayout(FlowLayout.CENTER));
		}
		c[0].add(jc1); c[1].add(jc2);
		if(type==1) setLayout(new FlowLayout());
		else setLayout(new GridLayout(2,1));
		add(c[0]);
		add(c[1]);
		//jc1.setLayout(new GridLayout(1,1));
		//jc2.setLayout(new GridLayout(1,1));
		pack();
		setVisible(true);
		if(exit) setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void close(){
		//System.out.println("yes");
		setVisible(false);
	}
	public void windowActivated(WindowEvent arg0) {
	}
	public void windowClosed(WindowEvent arg0) {
	}
	public void windowClosing(WindowEvent arg0) {
	}
	public void windowDeactivated(WindowEvent arg0) {
	}
	public void windowDeiconified(WindowEvent arg0) {
	}
	public void windowIconified(WindowEvent arg0) {
	}
	public void windowOpened(WindowEvent arg0) {
	}
}
