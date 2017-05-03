package tool;

/**
 * User: Chota Tokuyama
 * Date: 14-November-2008
 *
 */

public class Timer {
	public long start,end,time;
	public Timer(){
	}
	public void start(){
		start=System.currentTimeMillis();
	}
	public void end(){
		end=System.currentTimeMillis();
		time=end-start;
	}
	public void set(){
	}
}
