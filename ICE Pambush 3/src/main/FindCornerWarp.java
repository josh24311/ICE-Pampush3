package main;

/**
 * コーナー・ワープ地点発見
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_02_2008_Tue
 */

public class FindCornerWarp{
	GameParameter gp;
	
	public FindCornerWarp(GameParameter gp){
		this.gp=gp;
	}
	
	/**実行*/
	public void run(){
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				if(gp.node[i][j].can_point){
					if(gp.realArea(j)){
						findCorner(j,i); findWarp(j,i);
					}
				}
			}
		}
		gp.findCW();
	}
	
	/**コーナー地点発見*/
	private void findCorner(int x,int y){
		int num=0;
		boolean dir[]={false,false,false,false};
		for(int i=0;i<4;i++)
			if(y+direction(i+1,0)>0 && y+direction(i+1,0)<gp.mapHeight)
				if(gp.node[y+direction(i+1,0)][x+direction(i+1,1)].can_point){
					num++; dir[i]=true;
				}
		if(num>=3){
			gp.node[y][x].set(gp.num_corner);
			gp.node[y][shuusei(x)].set(gp.num_corner);
		}
		
		else if(num==2 && !(dir[0]&&dir[2] || dir[1]&&dir[3])){
			gp.node[y][x].set(gp.num_corner2);
			gp.node[y][x].corner_cost=5000;
			gp.node[y][shuusei(x)].set(gp.num_corner2);
			gp.node[y][shuusei(x)].corner_cost=5000;
		}
	}
	
	/**ワープ地点発見*/
	private void findWarp(int x,int y){
		if(x==gp.REAL[0]){
			if(gp.node[y][x].can_point &&
					gp.node[y][gp.REAL[1]-1].can_point &&
					gp.node[y][x].game_area){
				gp.node[y][x-1].set(gp.num_warp);
				gp.node[y][gp.REAL[1]].set(gp.num_warp);
			}
		}
	}
	
	/**ワープ地点の隣*/
	public void warpSide(){
		for(int y=0;y<gp.mapHeight;y++)
			for(int x=0;x<gp.mapWidth;x++)
				if(gp.node[y][x].warp){
					if(gp.node[y][gp.REAL[0]].can_point) {
						gp.node[y][gp.REAL[0]].warpSide=true;
						gp.node[y][gp.REAL[0]+1].warpSide2=true;
					}
					if(gp.node[y][gp.REAL[1]-1].can_point){
						gp.node[y][gp.REAL[1]-1].warpSide=true;
						gp.node[y][gp.REAL[1]-2].warpSide2=true;
					}
				}
	}
	
	/**座標修正*/
	private int shuusei(int x){
		return (x+gp.gameWidth)%gp.mapWidth;
	}
	
	/**方向設定*/
	private int direction(int dir,int num){
		int i=0,j=0;
		switch(dir){
			case 1: i=-1; j= 0; break;	case 2: i= 0; j= 1; break;
			case 3: i= 1; j= 0; break;	case 4: i= 0; j=-1; break;
			case 5: i=-1; j= 1; break;	case 6: i= 1; j= 1; break;
			case 7: i= 1; j=-1; break;	case 8: i=-1; j=-1; break;
		}
		if(num==0) return i;
		else return j;
	}
}
