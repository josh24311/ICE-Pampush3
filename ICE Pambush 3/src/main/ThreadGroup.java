package main;

/**
 * スレッドグループ
 * @author ChotaTokuyama
 * @version 2.2
 * @since Dec_02_2008_Tue
 */

public class ThreadGroup extends Thread{
	MsPacInterface mpi;
	int num;
	public boolean go=true;
	public ThreadGroup(MsPacInterface mpi,int num){
		this.mpi=mpi; this.num=num;
	}
	
	/**実行*/
	public void run(){
		go=false;
		switch(num){
		case 0:
			mpi.fa.gameArea(); break;
		case 1: 
			mpi.fcw.run(); break;
		case 2:
			mpi.fn.findNextP(); break;
		case 3:
			mpi.fw.setWall(); break;
		case 4:
			mpi.fw.setWall2(); break;
		case 5:
			mpi.fcw.warpSide(); break;
		case 6:
			mpi.fw.setGhostBase(); break;
		case 7:
			mpi.fa.ppArea(); break;
			//mpi.fcw.setNum(); break;
		}
	}
}
