package main;

import games.math.Vector2d;

/**
 * 最も近いオブジェクト発見
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Dec_03_2008_Tue
 */

public class FindNearest {
	GameParameter gp;
	
	/**オブジェクトまでの長さ*/ double Length[];
	/**ワープまでの長さ*/ double warp;
	/**ワープの長さ*/ double WarpLength=5;
	
	public FindNearest(GameParameter gp){
		this.gp=gp;
		this.Length=new double[gp.num_num];
		//reset();
	}
	
	/**リセット*/
	private void reset(){
		for(int i=0;i<gp.num_num;i++){
			if(i!=gp.num_warp && i!=gp.num_corner) gp.nearest[i]=null;
			this.Length[i]=-1.0;
		}
	}
	
	/**実行*/
	public void run(int x,int y){
		if(x==0&&y==0) reset();
		if(gp.node[y][x].pill){
			set(x,y,gp.num_pill);
			if(!gp.node[y][x].pp_area) set(x,y,gp.num_pill2);
		}
		else if(gp.node[y][x].pp) set(x,y,gp.num_pp);
		if(gp.node[y][x].edible) set(x,y,gp.num_edible);
		if(gp.node[y][x].corner) set(x,y,gp.num_corner);
		if(gp.node[y][x].warp && !gp.node[y][x].noWarp) setWarp(x,y);
	}
	
	/**次コーナー地点発見*/
	public void findNextP(){
		int i=0,j=0,nx=0,ny=0,now=0;
		
		for(int y=0;y<gp.mapHeight;y++) for(int x=0;x<gp.mapWidth;x++){
			for(int k=0;k<4;k++){
				 switch(k){
				 case 0: i=-1; j= 0; break; case 1: i= 0; j= 1; break;
				 case 2: i= 1; j= 0; break; case 3: i= 0; j=-1; break;
				 }
				 if(x+j>=0 && x+j<gp.width/8*2 && y+i>=0 && y+i<gp.height/8 &&
						 gp.node[y][x].can_point){
					 gp.node[y][x].nextP[k]=new Vector2d(); now=0;
					 while(gp.node[y+i*now][x+j*now].can_point &&
							 x+j*now>=0 && x+j*now<gp.width/8*2 && 
							 y+i*now>=0 && y+i*now<gp.height/8){
						 nx=x+now*j; ny=y+now*i;
						 now++;
						 if(x+j*now<0 || x+j*now>=gp.width/8*2 || 
								 y+i*now<0 || y+i*now>=gp.height/8)break;
						 else if(gp.node[y+i*now][x+j*now].corner
								 /*gp.node[y+i*now][x+j*now].warp*/){
							 nx=x+now*j;ny=y+now*i; break;
							 }
					 }
					 if(nx==x && ny==y ||
							 (!gp.node[ny][nx].corner &&
									 !gp.node[ny][nx].corner2))
						 gp.node[y][x].nextP[k]=null;
					 else {
						 gp.node[y][x].nextP[k].set(nx, ny);
					 }
				 }
				 else gp.node[y][x].nextP[k]=null;
			 }
		}
	}
	
	double leng=0;
	void set(int x, int y, int num){
		if(gp.nearest[gp.num_warp]!=null && !gp.realArea(x) && !gp.node[y][x].warp)
			leng=gp.nearest[gp.num_warp].dist4(x, y)+
					warp;
		else leng=gp.pac.dist4(x, y);
		if(Length[num]>leng || Length[num]==-1.0){
			gp.nearest[num]=new Vector2d();
			Length[num]=leng;
			gp.nearest[num].set(x, y);
		}
	}
	
	void setWarp(int x,int y){
		//小笹修正
		//leng=gp.pac.dist(x, y);
		leng=gp.pac.dist4(x, y);
		if(Length[gp.num_warp]>leng || Length[gp.num_warp]==-1.0){
			gp.nearest[gp.num_warp]=new Vector2d();
			Length[gp.num_warp]=leng;
			gp.nearest[gp.num_warp].set(x, y);
			//小笹修正
			//warp=gp.pac.dist2(gp.nearest[gp.num_warp])+WarpLength;
			warp=gp.pac.dist4(gp.nearest[gp.num_warp])+WarpLength;
		}
	}
	
	public void findGhost(Vector2d[] ghost,int num){
		double leng=0;
		double plus_warp=0;
		boolean yes=false;
		if(gp.pac!=null){
			if(num==1) yes=true;
			else if(gp.nearest[gp.num_warp]!=null) yes=true;
		}
		if(yes){
			for(int i=0;i<4;i++) if(ghost[i]!=null){
				if(!gp.node[(int)ghost[i].y][(int)ghost[i].x].warp)
					plus_warp=warp;
				//小笹修正
				//leng=gp.pac.dist2(ghost[i])+plus_warp;
				leng=gp.pac.dist4(ghost[i])+plus_warp;
				
				if(Length[gp.num_nearGhost]>leng || Length[gp.num_nearGhost]==-1.0){
					gp.nearest[gp.num_nearGhost]=new Vector2d();
					Length[gp.num_nearGhost]=leng;
					gp.nearest[gp.num_nearGhost].set(ghost[i]);
				}
			}
		}
		
	}
	
	public void findGaP(){
		double leng=0;
		if(gp.nearest[gp.num_pp]!=null){
			for(int i=0;i<4;i++){
				if(gp.ghost[i]!=null){
					//小笹修正
					//leng=gp.ghost[i].dist(gp.nearest[gp.num_pp]);
					leng=gp.ghost[i].dist4(gp.nearest[gp.num_pp]);
					
					if(Length[gp.num_GaP]>leng || Length[gp.num_GaP]==0.0){
						gp.nearest[gp.num_GaP]=new Vector2d();
						Length[gp.num_GaP]=leng;
						gp.nearest[gp.num_GaP].set(gp.ghost[i]);
					}
				}
			}
		}
	}
}
