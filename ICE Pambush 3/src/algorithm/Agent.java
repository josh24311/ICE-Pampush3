package algorithm;

import games.math.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import main.*;

/**
 * User: Chota Tokuyama
 * Date: 14-October-2008
 */

public class Agent{
	GameParameter gp;
	Astar as;
	Astar2 as2;
	public int direction=0;
	boolean wait=false;
	
	public Agent(GameParameter gp){
		//this.start = new Vector2d(start);
		//this.goal = new Vector2d(goal);
		this.gp=gp;
		this.as=new Astar(this.gp);
		this.as2=new Astar2(this.gp);
	}
	
	public int decide(){//pac=start,nearest[gp.num_pill]=goal
		//Vector2d kc = new Vector2d(0,0);
		//int kp=1;//１つ前の進行方向
		
		int epd=100,pod=100,god=100,godd=100,pid=100;
		Node pacN=gp.node[(int)gp.pac.y][(int)gp.pac.x];
		
		wait=false;
		gp.go_pp=false;
		gp.gpd=100;
		gp.g4pd=0;
		gp.g4px=0;
		gp.gcount=4;
		//gp.stop=gp.stopk;
		//gp.no_pl=true;
		
		//最も近いパワーピルに最も近いゴーストとの距離
		if(Exist(gp.num_pp)){
			for(int i=0;i<4;i++){
				if(gp.ghost[i]==null)continue;
				//小笹修正
				//godd=(int)gp.pac.dist3(gp.nearest[gp.num_pp],gp.ghost[i]);
				godd=(int)gp.pac.dist4(gp.nearest[gp.num_pp],gp.ghost[i]);
				//if(i==0)System.out.println("1"+godd);
				//if(i==1)System.out.println("2"+godd);
				//if(i==2)System.out.println("3"+godd);
				//if(i==3)System.out.println("4"+godd);
				if(godd<god){
					god=godd;
				}
			}
		}
		//それぞれのオブジェクトとの座標距離を測定
		//if(Exist(gp.num_pp) && Exist(gp.num_nearGhost))god=(int)gp.pac.dist3(gp.nearest[gp.num_pp],gp.nearest[gp.num_nearGhost]);
		//小笹修正
		//if(Exist(gp.num_pp))pod=(int)gp.pac.dist2(gp.nearest[gp.num_pp]);
		//if(Exist(gp.num_edible))epd=(int)gp.pac.dist2(gp.nearest[gp.num_edible]);
		//if(Exist(gp.num_nearGhost))gp.gpd=(int)gp.pac.dist2(gp.nearest[gp.num_nearGhost]);
		if(Exist(gp.num_pill))pid=(int)gp.pac.dist4(gp.nearest[gp.num_pill]);
		if(Exist(gp.num_pp))pod=(int)gp.pac.dist4(gp.nearest[gp.num_pp]);
		if(Exist(gp.num_edible))epd=(int)gp.pac.dist4(gp.nearest[gp.num_edible]);
		if(Exist(gp.num_nearGhost))gp.gpd=(int)gp.pac.dist4(gp.nearest[gp.num_nearGhost]);

		
		//仮想空間を含まない最も近いゴーストとの距離
		gp.gpd2=100;
		for(int i=0;i<4;i++){
			if(gp.ghost[i]==null)continue;
			//小笹修正
			//gp.gpdk=Math.abs((int)gp.pac.x-(int)gp.ghost[i].x)+Math.abs((int)gp.pac.y-(int)gp.ghost[i].y);
			gp.gpdk=gp.pac.dist4(gp.ghost[i]);
			if(gp.gpdk<gp.gpd2)gp.gpd2=gp.gpdk;
		}
		
		//進行方向の数を数える
		gp.turn_count=0;
		for(int i=0;i<4;i++){
			if(pacN.nextP[i]!=null)gp.turn_count++;
		}
		//ゴーストとの合計距離と檻の外に出ているゴーストの数を計算
		for(int i=0;i<4;i++){
			if(gp.ghost[i]==null){gp.gcount--;continue;}
			gp.g4px+=gp.ghost[i].x;
			//小笹修正
			//gp.g4pd+=(int)gp.pac.dist2(gp.ghost[i]);
			gp.g4pd+=(int)gp.pac.dist4(gp.ghost[i]);
		}
		
		//ゴーストが近いと認識する距離の変化
		int length=0;
		if(gp.scan[1])length=3; else length=gp.distBorder;
		
		//動作決定
		/*
		//ゴーストが４体いないときの動作
		if(gp.gcount>=1 && gp.gcount<=2){
			if(gp.gpd<=8){
				if(gp.nearest[gp.num_pill2]==null)direction=as.run(gp.num_pill);
				else if(!gp.realArea((int)gp.nearest[gp.num_pill2].x))
					direction=as.run(gp.num_warp);
				else direction=as.run(gp.num_pill2);
			}
			else{
				if(gp.nearest[gp.num_pill2]==null)direction=as2.run(gp.num_pill);
				else if(!gp.realArea((int)gp.nearest[gp.num_pill2].x))
	    			direction=as2.run(gp.num_warp);
				else direction=as2.run(gp.num_pill2);
			}
		}
		*/
		//System.out.println(gp.gpd);
		//待ち伏せ動作
		//System.out.println(gp.gpd+" "+pod+" "+god);
		
		if(gp.study==null && pod<=5 && gp.gpd2>=4 && god>=6 && gp.gpd2<=length && Exist(gp.num_pp) && gp.realArea((int)gp.nearest[gp.num_pp].x) && gp.stage==0){
			direction=0;wait=true;gp.stop=1;//System.out.println("matibuse");
		}
		
		else if(gp.study==null && pod<=3 && gp.gpd2>=4 && god>=4  && gp.gpd2<=length+length && Exist(gp.num_pp) && gp.realArea((int)gp.nearest[gp.num_pp].x) && gp.stage==1){
			direction=0;wait=true;gp.stop=1;//System.out.println("matibuse");
		}
		
		//System.out.println(gp.gpd+" "+pod+" "+god);
		//ワープが1深さ内にあるときの動作（壁にぶつかるまで進行方向をかえない）
		/*
		else if(gp.nearest[gp.num_warp]!=null){
		Node warpN=gp.node[(int)gp.nearest[gp.num_warp].y][(int)gp.nearest[gp.num_warp].x];
		if(gp.pac.y==gp.nearest[gp.num_warp].y && (int)warpN.nextP[3].x+2<gp.pac.x && gp.pac.x<(int)warpN.nextP[1].x-2)direction=gp.kp;
		*/
		//Astarを用いる動作
		
		else if(gp.gpd<=length){
			//最も近いパワーピルへ向かう
			if(Hantei(1) && gp.gpd2<=8){// && gp.gcount==4 && gp.g4pd<=50){
				//gp.no_pl=false;
				gp.go_pp=true;
				if(gp.stop==1 && (gp.gpd2<=4 || god<=6) && gp.stage==0){direction=as2.run(gp.num_pp);gp.stop=1;}
				else if(gp.stop==1 && (gp.gpd2<=4 || god<=4) && gp.stage==1){direction=as2.run(gp.num_pp);gp.stop=1;}
				else if(pod<=4){direction=as2.run(gp.num_pp);gp.stop=0;}
				else direction=as.run(gp.num_pp);gp.stop=0;
			}
		
			//最も近いエディブルに向かう
			else if(Exist(gp.num_edible) && epd<=10){
				gp.stop=0;
				direction=as.run(gp.num_edible);
			}
		
			//最も近いピルへ向かう
			else if(Exist(gp.num_pill)){// && (turn_count>=3 || pacN.nextP[kp-1]!=null)){
				gp.stop=0;
				direction=as.run(gp.num_pill);
			}
		}
		/*
		//最も近いワープ地点へ向かう
		else if(Exist(gp.num_warp) && gp.gpd<=6 && gp.nearest[gp.num_nearGhost]!=null){
			if(!gp.RealArea((int)gp.nearest[gp.num_nearGhost].x))
				direction=as.run(gp.num_pill);
			else direction=as.run(gp.num_warp);
		}
		*/
		//Astar2を用いる動作
		else{
			gp.stop=0;
			//最も近いエディブルへ向かう
			if(Exist(gp.num_edible) && epd<=10){
				direction=as2.run(gp.num_edible);
			}
		
			//最も近いピルへ向かう
			else if(Exist(gp.num_pill)){// && (turn_count>=3 || pacN.nextP[kp-1]!=null)){
				if(!Exist(gp.num_pill2))direction=as2.run(gp.num_pill);
				else if(pod>20 && pid<=10)direction=as2.run(gp.num_pill);
				else direction=as2.run(gp.num_pill2);
				//else direction=as2.run(gp.num_pill);
			}
		}
		if(direction==0) //System.out.println("no");
		if(direction==0 && !wait && (pacN.corner || pacN.corner2)){
			for(int i=0;i<4;i++)
				if(pacN.nextP[i]!=null){
					//System.out.println("error "+i+" "+gp.pac);
					direction=i+1; break;
				}
		}
		//System.out.println(gp.stop+" "+gp.stopk);
		//gp.stopk=gp.stop;
		/*
		//前の動作を繰り返す
		else {
			direction=kp;
		}
		//進行方向の保存
		kp=direction;
		*/
		//}
		return direction;
	}
	
	private boolean Hantei(int num){
		switch(num){
		//パワーピル
		case 1: return Exist(gp.num_pp) && gp.nearest[gp.num_edible]==null;
		default: return false;
		}
	}
	
	private boolean Exist(int num){
		return gp.nearest[num]!=null;
	}
}
