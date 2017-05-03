package main;

/**
 * 壁発見
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_02_2008_Tue
 */

public class FindWall {
	GameParameter gp;
	public FindWall(GameParameter gp){
		this.gp=gp;
	}
	
	/**壁設定*/
	public void setWall(){
		int now_x=0,now_y=0;
		for(int y=0;y<gp.mapHeight;y++){
			for(int x=0;x<gp.mapWidth;x++){
				now_x=0; now_y=0;
				if(x+1<gp.mapWidth && y+1<gp.mapHeight){
					if(gp.node[y][x].can_point && 
							gp.node[y][x+1].can_point &&
							gp.node[y+1][x].can_point &&
							gp.node[y+1][x+1].can_point){
						while(gp.node[y][x+now_x].can_point &&
								x+now_x<gp.mapWidth-1) now_x++;
						while(gp.node[y+now_y][x].can_point &&
								y+now_y<gp.mapHeight-1) now_y++;
						for(int i=0;i<now_y;i++)
							for(int j=0;j<now_x;j++){
								gp.node[y+i][x+j].setWall();
							}
					}
				}
			}
		}
	}
	public void setWall2(){
		boolean yes=false;
		int x=0,y=0,now=0;
		for(int i=0;i<gp.mapHeight;i++)
			for(int j=0;j<gp.mapWidth;j++)
				if(gp.node[i][j].warp){
					x=j;y=i;
					for(int k=0;k<4;k++){
						if(gp.node[i][j].nextP[k]!=null){
							yes=true; break;
						}
					}
					if(!yes){
						now=0;
						while(gp.node[y][x+now].can_point &&
								x+now>0){
							gp.node[y][x+now].setWall(); now--;
						}
						now=0;
						while(gp.node[y][x+1+now].can_point &&
								x+1+now<gp.mapWidth){
							gp.node[y][x+1+now].setWall(); now++;
						}
					}
				}
	}
	
	/**ゴーストの基地発見*/
	public void setGhostBase(){
		int num=0;
		num=0;
		for(int i=0;i<gp.height/8;i++){
			for(int j=0;j<gp.width*2/8;j++){
				if(gp.realArea(j) && i+7<gp.height/8)
					if(!gp.node[i][j].can_point){
						for(int k=0;k<8;k++)
							if(!gp.node[i][j+k].can_point && !gp.node[i+4][j+k].can_point)
								num++;
						for(int l=0;l<5;l++)
							if(!gp.node[i+l][j].can_point && !gp.node[i+l][j+7].can_point)
								num++;
					}
				
				if(num==13){
					for(int k=0;k<5;k++) for(int l=0;l<8;l++)
						// 09/08/19 丸山追加　出てくるゴースト発見用
						
						
							gp.node[i+k][j+l].setBase();
						
				}
				
				num=0;
			}
		}
	}
}
