package main;

/**
 * エリア認識
 * @author ChotaTokuyama
 * @version 2.2
 * @since Dec_02_2008_Tue
 */

public class FindArea {
	//private GameParameter gp;
	public static GameParameter gp;
	
	/**スキャン位置修正*/
		public int first_y;
	
	public FindArea(GameParameter gp){
		this.gp=gp;
		this.first_y=0;
	}
	
	/**ゲームエリア認識*/
	public void gameArea(){
		boolean yes=true;
		int line[]=new int[2];
		for(int i=0;i<gp.gameHeight-30;i++){
			yes=true;
			if(!gp.node[i][gp.REAL[0]].can_point){
				for(int j=0;j<gp.gameWidth;j++){
					if(gp.node[i][j+gp.REAL[0]].can_point ||
							gp.node[i+30][j+gp.REAL[0]].can_point){
						yes=false; break;
					}
				}
				if(yes){
					first_y=(1-i)*8;
					line[0]=i;line[1]=i+30; break;
				}
			}
		}
		if(yes){
			for(int i=0;i<gp.mapHeight;i++){
				for(int j=0;j<gp.mapWidth;j++){
					if(i>=line[0] && i<=line[1]) gp.node[i][j].setGameArea();
				}
			}
		}
	}
	
	
	int nx=0,ny=0;
	int cx[]=new int[5],cy[]=new int[5];
	/**パワーピルエリア認識*/
	public void ppArea(){
		boolean find=false;
		int depth=0;
		boolean yes=true;
		for(int i=0;i<gp.mapHeight;i++){
			for(int j=0;j<gp.mapWidth;j++){
				if(gp.node[i][j].pp){
					for(int k=0;k<4;k++){
						find=false;
						if(gp.node[i][j].nextP[k]!=null){
							depth=0;
							int dir=k;
							cx[0]=j;cy[0]=i; depth++;
							cx[1]=(int)gp.node[i][j].nextP[k].x;
							cy[1]=(int)gp.node[i][j].nextP[k].y;
							while(!find && depth<5){
								if(gp.node[cy[depth]][cx[depth]].corner){
									depth++;
									find=true; break;
								}
								else if(gp.node[cy[depth]][cx[depth]].corner2){
									for(int l=0;l<4;l++){
										if(gp.node[cy[depth]][cx[depth]].nextP[l]!=null && Math.abs(dir-l)!=2){
											dir=l;
											depth++;
											cx[depth]=(int)gp.node[cy[depth-1]][cx[depth-1]].nextP[l].x;
											cy[depth]=(int)gp.node[cy[depth-1]][cx[depth-1]].nextP[l].y;
											break;
										}
									}
								}
							}
							for(int n=0;n<depth-1;n++){
								changePP(cx[n],cy[n],cx[n+1],cy[n+1]);
								gp.node[cy[n]][cx[n]].setPPArea();
							}
						}
					}
				}
			}
		}
	}
	
	private void changePP(int x1,int y1,int x2,int y2){
		int ni=0,nj=0;
		if(x1!=x2){
			if(x1<x2) nj=1;
			else nj=-1;
		}
		if(y1!=y2){
			if(y1<y2) ni=1;
			else ni=-1;
		}
		
		//System.out.println(Math.abs(x1-x2)+" "+Math.abs(y1-y2));
		for(int i=0;i<Math.abs(y1-y2)+1;i++){
			for(int j=0;j<Math.abs(x1-x2)+1;j++){
				if(!gp.node[i*ni+y1][j*nj+x1].corner)
					gp.node[i*ni+y1][j*nj+x1].setPPArea();
			}
		}
	}
}
