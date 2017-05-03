package algorithm;
/***/
import games.math.Vector2d;
import main.*;

public class Astar {
	GameParameter gp;
	public Astar(GameParameter gp){
		this.gp=gp;
	}
	
	Vector2d Ghost;
	public int run(int num){
		//boolean best_direction=false;
		
		int dcostbase = 1000;
		double gcostbase = dcostbase * 10 * gp.costRatio;
		int direction=0;
		int cost=3000000;
		int dcost=1000000;
		int dcost1=0,dcost2=0,dcost3=0,dcost4,dcost5,gcost1=0,gcost2=0,gcost3=0,gcost4=0,gcost5=0;
		int aaa=0,bbb=0,ccc=0,ddd=0;
		int ppp1=0,ppp2=0,ppp3=0,ppp4=0,ppp5=0,ppp6=0,pad1=0,pad2=0,pad3=0,pad4=0,pad5=0;
		boolean deepg1=false,deepg2=false,deepg3=false,deepg4=false,deepg5=false;
		int masu1,masu2,masu3;
		int mpac,mghost=100,gmoved;
		int gpp[] = new int[8];//ゴーストの進行方向と逆向き
		int gppc[] = new int[8];//ゴーストの進行方向と逆向きを保存
		Vector2d[] gc = {
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0),
				new Vector2d(0,0)
		};//それぞれのゴーストの座標を保存
		Node pacN=gp.node[(int)gp.pac.y][(int)gp.pac.x];
		Node pac1=pacN,pac2=pacN,pac3=pacN,pac4=pacN,pac5=pacN;
		
		//gcostの初期化
		gp.pill_count=0;
		for(int i=0;i<gp.height/8;i++)for(int j=0;j<gp.width/8*2;j++){
			gp.node[i][j].ghost_cost=0;gp.node[i][j].powerpill_cost=0;//gp.node[i][j].no_po=false;
			if(gp.node[i][j].pill)gp.pill_count++;
			//if(gp.node[i][j].corner && gp.gpd<=5)gp.node[i][j].corner_cost=100000;else gp.node[i][j].corner_cost=2000;
			/*if(gp.node[i][j].warp2_cost>0)gp.node[i][j].warp2_cost-=2000;
			//ワープコスト
			if(gp.node[i][j].warp && j<=27){
				if(gp.node[i][j].nextP[3]!=null){
					Vector2d l_warp=gp.node[i][j].nextP[3];
					gp.node[(int)l_warp.y][(int)l_warp.x].warp_cost=1000*(gp.g4px-23*gp.gcount);
				}
			}
			if(gp.node[i][j].warp && j>=28){
				if(gp.node[i][j].nextP[1]!=null){
					Vector2d r_warp=gp.node[i][j].nextP[1];
					gp.node[(int)r_warp.y][(int)r_warp.x].warp_cost=1000*(30*gp.gcount-gp.g4px);
				}
			}*/
		}
		gp.pill_count=gp.pill_count/2;
		//System.out.println(gp.pill_count);
		/*
		//ワープ２コスト
		if(gp.nearest[gp.num_warp]!=null){
			Node ww=gp.node[(int)gp.nearest[gp.num_warp].y][(int)gp.nearest[gp.num_warp].x];	
			if(gp.pac.y==gp.nearest[gp.num_warp].y && ww.nextP[3].x<gp.pac.x && gp.pac.x<ww.nextP[1].x){
				if(gp.kp==1 && ww.nextP[3].x<=27)gp.node[(int)ww.nextP[3].y][(int)ww.nextP[3].x].warp2_cost=100000;
				else if(gp.kp==1 && ww.nextP[3].x>=28)gp.node[(int)ww.nextP[3].y][(int)ww.nextP[3].x-27].warp2_cost=100000;
				else if(gp.kp==3 && ww.nextP[1].x<=27)gp.node[(int)ww.nextP[1].y][(int)ww.nextP[1].x+27].warp2_cost=100000;
				else if(gp.kp==3 && ww.nextP[1].x>=28)gp.node[(int)ww.nextP[1].y][(int)ww.nextP[1].x].warp2_cost=100000;
			}
		}
		*/
		//ゴーストコスト	
		for(int i=0;i<8;i++){
			if(i<4)Ghost=gp.ghost[i];
			else Ghost=gp.ghost2[i%4];
			if(Ghost==null)continue;
			Node ghn=gp.node[(int)Ghost.y][(int)Ghost.x];
			gp.node[(int)Ghost.y][(int)Ghost.x].ghost_cost+=500000;
			//if(gp.node[(int)Ghost.y+1][(int)Ghost.x]!=null)gp.node[(int)Ghost.y+1][(int)Ghost.x].ghost_cost+=500000;
			//if(gp.node[(int)Ghost.y-1][(int)Ghost.x]!=null)gp.node[(int)Ghost.y-1][(int)Ghost.x].ghost_cost+=500000;
			//if(gp.node[(int)Ghost.y][(int)Ghost.x+1]!=null)gp.node[(int)Ghost.y][(int)Ghost.x+1].ghost_cost+=500000;
			//if(gp.node[(int)Ghost.y][(int)Ghost.x-1]!=null)gp.node[(int)Ghost.y][(int)Ghost.x-1].ghost_cost+=500000;
			//System.out.println((int)gp.ghost[i].y+" "+(int)gp.ghost[i].x);
			//if(gc[i].x<Ghost.x && gc[i].y==Ghost.y)gpp[i]=3;
			//else if(gc[i].x>Ghost.x && gc[i].y==Ghost.y)gpp[i]=1;
			//else if(gc[i].x==Ghost.x && gc[i].y<Ghost.y)gpp[i]=0;
			//else if(gc[i].x==Ghost.x && gc[i].y>Ghost.y)gpp[i]=2;
			//else if(gc[i].x==Ghost.x && gc[i].y==Ghost.y)gpp[i]=gppc[i];
			for(int j=0;j<4;j++){
				Node ghn1=ghn,ghn2=ghn,ghn3=ghn;
				if(ghn.nextP[j]==null)continue;
				else if(ghn.nextP[j]!=null){
					ghn1=gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x];
					//小笹修正
					//masu1=Math.abs((int)ghn.nextP[j].x-(int)Ghost.x)+Math.abs((int)ghn.nextP[j].y-(int)Ghost.y);
					masu1=ghn.nextP[j].dist4(Ghost);
					//if(pacN.nextP[j]!=null){
					if(pacN.nextP[j]!=null && ((gp.pac.x!=Ghost.x && gp.pac.y==Ghost.y) || (gp.pac.x==Ghost.x && gp.pac.y!=Ghost.y)) && ((j==3 && gp.pac.x>Ghost.x && Ghost.x>pacN.nextP[3].x) || (j==1 && gp.pac.x<Ghost.x && Ghost.x<pacN.nextP[1].x) || (j==0 && gp.pac.y>Ghost.y && Ghost.y>pacN.nextP[0].y) || (j==2 && gp.pac.y<Ghost.y && Ghost.y<pacN.nextP[2].y)))gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x].ghost_cost+=2000000;//}
					//else if(gpp[i]==j)gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x].ghost_cost+=500000;//500000/(masu1*masu1); 
					else gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x].ghost_cost+=gcostbase/(masu1*masu1);
					for(int j2=0;j2<4;j2++){
						if(ghn1.nextP[j2]==null || (j==2 && j2==0) || (j==0 && j2==2) || (j==3 && j2==1) || (j==1 && j2==3))continue;
						else if(ghn1.nextP[j2]!=null){
							ghn2=gp.node[(int)ghn1.nextP[j2].y][(int)ghn1.nextP[j2].x];
							//小笹修正
							//masu2=Math.abs((int)ghn1.nextP[j2].x-(int)ghn.nextP[j].x)+Math.abs((int)ghn1.nextP[j2].y-(int)ghn.nextP[j].y);
							masu2=ghn1.nextP[j2].dist4(ghn.nextP[j]);
							
							//if(gpp[i]==j)gp.node[(int)ghn1.nextP[j2].y][(int)ghn1.nextP[j2].x].ghost_cost+=100000;//500000/((masu1+masu2)*(masu1+masu2));
							gp.node[(int)ghn1.nextP[j2].y][(int)ghn1.nextP[j2].x].ghost_cost+=gcostbase/((masu1+masu2)*(masu1+masu2));
							/*
	    					for(int j3=0;j3<4;j3++){
	    						if(ghn2.nextP[j3]==null || (j2==2 && j3==0) || (j2==0 && j3==2) || (j2==3 && j3==1) || (j2==1 && j3==3))continue;
	    						else if(ghn2.nextP[j3]!=null){
			    					ghn3=gp.node[(int)ghn2.nextP[j3].y][(int)ghn2.nextP[j3].x];
	    							masu3=Math.abs((int)ghn2.nextP[j3].x-(int)ghn1.nextP[j2].x)+Math.abs((int)ghn2.nextP[j3].y-(int)ghn1.nextP[j2].y);
			    					if(gpp[i]==j)gp.node[(int)ghn2.nextP[j3].y][(int)ghn2.nextP[j3].x].ghost_cost+=1000000/((masu1+masu2+masu3)*(masu1+masu2+masu3));
					    			else gp.node[(int)ghn2.nextP[j3].y][(int)ghn2.nextP[j3].x].ghost_cost+=1000000/((masu1+masu2+masu3)*(masu1+masu2+masu3));	
			    				}
	    					}
	    					*/
	    				}
	    			}
	    		}
	    	}
	    	//gc[i]=Ghost;//gh[i]の値を保存
	    	//gppc[i]=gpp[i];//gp[i]の値を保存
		}
		
		/*
		//ゴーストコスト代理
		for(int i=0;i<4;i++){
			if(gp.ghost[i]==null)continue;
			Node ghn=gp.node[(int)gp.ghost[i].y][(int)gp.ghost[i].x];
	    	gp.node[(int)gp.ghost[i].y][(int)gp.ghost[i].x].ghost_cost+=1000000;
	    	for(int j=0;j<4;j++){
	    		Node ghn1=ghn,ghn2=ghn,ghn3=ghn;
	    		if(ghn.nextP[j]==null)continue;
	    		else if(ghn.nextP[j]!=null){
	    			ghn1=gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x];
	    			masu1=Math.abs((int)ghn.nextP[j].x-(int)gp.ghost[i].x)+Math.abs((int)ghn.nextP[j].y-(int)gp.ghost[i].y);
	    			gp.node[(int)ghn.nextP[j].y][(int)ghn.nextP[j].x].ghost_cost+=100000;//100000/(masu1*masu1);
	    			for(int j2=0;j2<4;j2++){
	    				if(ghn1.nextP[j2]==null || (j==2 && j2==0) || (j==0 && j2==2) || (j==3 && j2==1) || (j==1 && j2==3))continue;
	    				else if(ghn1.nextP[j2]!=null){
	    					ghn2=gp.node[(int)ghn1.nextP[j2].y][(int)ghn1.nextP[j2].x];
	    					masu2=Math.abs((int)ghn1.nextP[j2].x-(int)ghn.nextP[j].x)+Math.abs((int)ghn1.nextP[j2].y-(int)ghn.nextP[j].y);
	    					gp.node[(int)ghn1.nextP[j2].y][(int)ghn1.nextP[j2].x].ghost_cost+=10000;//100000/((masu1+masu2)*(masu1+masu2));
	    					for(int j3=0;j3<4;j3++){
	    						if(ghn2.nextP[j3]==null || (j2==2 && j3==0) || (j2==0 && j3==2) || (j2==3 && j3==1) || (j2==1 && j3==3))continue;
	    						else if(ghn2.nextP[j3]!=null){
			    					ghn3=gp.node[(int)ghn2.nextP[j3].y][(int)ghn2.nextP[j3].x];
	    							masu3=Math.abs((int)ghn2.nextP[j3].x-(int)ghn1.nextP[j2].x)+Math.abs((int)ghn2.nextP[j3].y-(int)ghn1.nextP[j2].y);
			    					gp.node[(int)ghn2.nextP[j3].y][(int)ghn2.nextP[j3].x].ghost_cost+=1000;//100000/((masu1+masu2+masu3)*(masu1+masu2+masu3));	
			    				}
	    					}
	    				}
	    			}
	    		}
	    	}
		}
		 */
		
		//パワーピルコスト
		if(gp.nearest[gp.num_pp]!=null && gp.gpd>8){// && (gp.gpd>8 || gp.g4pd>50 || gp.gcount!=4)){
			Node pow_node=gp.node[(int)gp.nearest[gp.num_pp].y][(int)gp.nearest[gp.num_pp].x];
			for(int i=0;i<4;i++){
				if(pow_node.nextP[i]!=null){
					gp.node[(int)pow_node.nextP[i].y][(int)pow_node.nextP[i].x].powerpill_cost=0;			
					//gp.node[(int)pow_node.nextP[i].y][(int)pow_node.nextP[i].x].no_po=true;
				}
			}
		}
		
		//A*アルゴリズム動作
		if(gp.study==null)gp.goal=gp.nearest[num];
		else gp.goal=gp.study;
		//小笹修正
		//ppp1=Math.abs((int)gp.pac.x-(int)gp.goal.x)+Math.abs((int)gp.pac.y-(int)gp.goal.y);
		ppp1=gp.pac.dist4(gp.goal);
		for(int i=0;i<4;i++){
			if(pacN.nextP[i]==null)continue;// || best_direction)continue;// || (gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].no_po && gp.no_pl))continue;
			else{
				pac1=gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x];
				gcost1=gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].powerpill_cost+gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].ghost_cost+gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].corner_cost;//+gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].warp_cost+gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].warp2_cost;
				if((gp.pac.x==gp.goal.x && gp.pac.y!=gp.goal.y && ((gp.pac.y>=gp.goal.y && gp.goal.y>=pacN.nextP[i].y) || (gp.pac.y<=gp.goal.y && gp.goal.y<=pacN.nextP[i].y))) || (gp.pac.x!=gp.goal.x && gp.pac.y==gp.goal.y && ((gp.pac.x>=gp.goal.x && gp.goal.x>=pacN.nextP[i].x) || (gp.pac.x<=gp.goal.x && gp.goal.x<=pacN.nextP[i].x))) && (gp.pill_count<=5 || gp.go_pp==true)){dcost=gcost1;aaa=1;}
				else{
					if((gp.pac.x==gp.goal.x && gp.pac.y!=gp.goal.y && ((gp.pac.y>=gp.goal.y && gp.goal.y>=pacN.nextP[i].y) || (gp.pac.y<=gp.goal.y && gp.goal.y<=pacN.nextP[i].y))) || (gp.pac.x!=gp.goal.x && gp.pac.y==gp.goal.y && ((gp.pac.x>=gp.goal.x && gp.goal.x>=pacN.nextP[i].x) || (gp.pac.x<=gp.goal.x && gp.goal.x<=pacN.nextP[i].x)))){deepg1=true;}
					//小笹修正
					//pad1=Math.abs((int)gp.pac.x-(int)pacN.nextP[i].x)+Math.abs((int)gp.pac.y-(int)pacN.nextP[i].y);
					//ppp2=Math.abs((int)pacN.nextP[i].x-(int)gp.goal.x)+Math.abs((int)pacN.nextP[i].y-(int)gp.goal.y);
					pad1=gp.pac.dist4(pacN.nextP[i]);
					ppp2=pacN.nextP[i].dist4(gp.goal);
					//挟み撃ち処理
					/*mpac=pad1;
					for(int j=0;j<4;j++){
						if(gp.ghost[j]==null)continue;
						gmoved=pacN.nextP[i].dist4(gp.ghost[j]);
						if(mghost>=gmoved)mghost=gmoved;
					}
					if(mghost<=mpac)dcost1=300+(mpac-mghost)*30;*/
					dcost1=ppp2+pad1-ppp1;//if(dcost1>=12)continue;
					for(int i2=0;i2<4;i2++){
						if(pac1.nextP[i2]==null || (i==0 && i2==2) || (i==1 && i2==3) || (i==2 && i2==0) || (i==3 && i2==1))continue;// || best_direction)continue;// || (gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].no_po && gp.no_pl))continue;
						else{
							pac2=gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x];
							gcost2=gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].powerpill_cost+gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].ghost_cost+gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].corner_cost;//+gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].warp_cost+gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].warp2_cost;
							if((pacN.nextP[i].x==gp.goal.x && pacN.nextP[i].y!=gp.goal.y && ((pacN.nextP[i].y>=gp.goal.y && gp.goal.y>=pac1.nextP[i2].y) || (pacN.nextP[i].y<=gp.goal.y && gp.goal.y<=pac1.nextP[i2].y))) || (pacN.nextP[i].x!=gp.goal.x && pacN.nextP[i].y==gp.goal.y && ((pacN.nextP[i].x>=gp.goal.x && gp.goal.x>=pac1.nextP[i2].x) || (pacN.nextP[i].x<=gp.goal.x && gp.goal.x<=pac1.nextP[i2].x))) && (gp.pill_count<=5 || gp.go_pp==true)){dcost=dcostbase*dcost1+(gcost1+gcost2);bbb=1;}
							else{
								if((pacN.nextP[i].x==gp.goal.x && pacN.nextP[i].y!=gp.goal.y && ((pacN.nextP[i].y>=gp.goal.y && gp.goal.y>=pac1.nextP[i2].y) || (pacN.nextP[i].y<=gp.goal.y && gp.goal.y<=pac1.nextP[i2].y))) || (pacN.nextP[i].x!=gp.goal.x && pacN.nextP[i].y==gp.goal.y && ((pacN.nextP[i].x>=gp.goal.x && gp.goal.x>=pac1.nextP[i2].x) || (pacN.nextP[i].x<=gp.goal.x && gp.goal.x<=pac1.nextP[i2].x)))){deepg2=true;}
								//小笹修正
								//pad2=Math.abs((int)pacN.nextP[i].x-(int)pac1.nextP[i2].x)+Math.abs((int)pacN.nextP[i].y-(int)pac1.nextP[i2].y);
								//ppp3=Math.abs((int)pac1.nextP[i2].x-(int)gp.goal.x)+Math.abs((int)pac1.nextP[i2].y-(int)gp.goal.y);
								pad2=pacN.nextP[i].dist4(pac1.nextP[i2]);
								ppp3=pac1.nextP[i2].dist4(gp.goal);
								/*mpac=pad1+pad2;
								for(int j=0;j<4;j++){
									if(gp.ghost[j]==null)continue;
									gmoved=pac1.nextP[i2].dist4(gp.ghost[j]);
									if(mghost>=gmoved)mghost=gmoved;
								}
								if(mghost<=mpac)dcost2=300+(mpac-mghost)*30;*/
								dcost2=ppp3+pad2-ppp2;//if(dcost2>=12)continue;
								for(int i3=0;i3<4;i3++){
									if(pac2.nextP[i3]==null || (i2==0 && i3==2) || (i2==1 && i3==3) || (i2==2 && i3==0) || (i2==3 && i3==1))continue;// || best_direction)continue;// || (gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].no_po && gp.no_pl))continue;
									else{
										pac3=gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x];
										gcost3=gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].powerpill_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].ghost_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].corner_cost;//+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp2_cost;
										if((pac1.nextP[i2].x==gp.goal.x && pac1.nextP[i2].y!=gp.goal.y && ((pac1.nextP[i2].y>=gp.goal.y && gp.goal.y>=pac2.nextP[i3].y) || (pac1.nextP[i2].y<=gp.goal.y && gp.goal.y<=pac2.nextP[i3].y))) || (pac1.nextP[i2].x!=gp.goal.x && pac1.nextP[i2].y==gp.goal.y && ((pac1.nextP[i2].x>=gp.goal.x && gp.goal.x>=pac2.nextP[i3].x) || (pac1.nextP[i2].x<=gp.goal.x && gp.goal.x<=pac2.nextP[i3].x))) && (gp.pill_count<=5 || gp.go_pp==true)){dcost=dcostbase*(dcost1+dcost2)+(gcost1+gcost2+gcost3);ccc=1;}
										else{
											if((pac1.nextP[i2].x==gp.goal.x && pac1.nextP[i2].y!=gp.goal.y && ((pac1.nextP[i2].y>=gp.goal.y && gp.goal.y>=pac2.nextP[i3].y) || (pac1.nextP[i2].y<=gp.goal.y && gp.goal.y<=pac2.nextP[i3].y))) || (pac1.nextP[i2].x!=gp.goal.x && pac1.nextP[i2].y==gp.goal.y && ((pac1.nextP[i2].x>=gp.goal.x && gp.goal.x>=pac2.nextP[i3].x) || (pac1.nextP[i2].x<=gp.goal.x && gp.goal.x<=pac2.nextP[i3].x)))){deepg3=true;}
											//小笹修正
											//pad3=Math.abs((int)pac1.nextP[i2].x-(int)pac2.nextP[i3].x)+Math.abs((int)pac1.nextP[i2].y-(int)pac2.nextP[i3].y);
											//ppp4=Math.abs((int)pac2.nextP[i3].x-(int)gp.goal.x)+Math.abs((int)pac2.nextP[i3].y-(int)gp.goal.y);
											pad3=pac1.nextP[i2].dist4(pac2.nextP[i3]);
											ppp4=pac2.nextP[i3].dist4(gp.goal);
											/*mpac=pad1+pad2+pad3;
											for(int j=0;j<4;j++){
												if(gp.ghost[j]==null)continue;
												gmoved=pac2.nextP[i3].dist4(gp.ghost[j]);
												if(mghost>=gmoved)mghost=gmoved;
											}
											if(mghost<=mpac)dcost3=300+(mpac-mghost)*30;*/
											dcost3=ppp4+pad3-ppp3;//if(dcost3>=12)continue;
											for(int i4=0;i4<4;i4++){
												if(pac3.nextP[i4]==null || (i3==0 && i4==2) || (i3==1 && i4==3) || (i3==2 && i4==0) || (i3==3 && i4==1))continue;// || best_direction)continue;// || (gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].no_po && gp.no_pl))continue;
												else{
													pac4=gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x];
													gcost4=gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].powerpill_cost+gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].ghost_cost+gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].corner_cost;//+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp2_cost;
													if((pac2.nextP[i3].x==gp.goal.x && pac2.nextP[i3].y!=gp.goal.y && ((pac2.nextP[i3].y>=gp.goal.y && gp.goal.y>=pac3.nextP[i4].y) || (pac2.nextP[i3].y<=gp.goal.y && gp.goal.y<=pac3.nextP[i4].y))) || (pac2.nextP[i3].x!=gp.goal.x && pac2.nextP[i3].y==gp.goal.y && ((pac2.nextP[i3].x>=gp.goal.x && gp.goal.x>=pac3.nextP[i4].x) || (pac2.nextP[i3].x<=gp.goal.x && gp.goal.x<=pac3.nextP[i4].x))) && (gp.pill_count<=5 || gp.go_pp==true)){dcost=dcostbase*(dcost1+dcost2+dcost3)+(gcost1+gcost2+gcost3+gcost4);ddd=1;}
													else{
														if((pac2.nextP[i3].x==gp.goal.x && pac2.nextP[i3].y!=gp.goal.y && ((pac2.nextP[i3].y>=gp.goal.y && gp.goal.y>=pac3.nextP[i4].y) || (pac2.nextP[i3].y<=gp.goal.y && gp.goal.y<=pac3.nextP[i4].y))) || (pac2.nextP[i3].x!=gp.goal.x && pac2.nextP[i3].y==gp.goal.y && ((pac2.nextP[i3].x>=gp.goal.x && gp.goal.x>=pac3.nextP[i4].x) || (pac2.nextP[i3].x<=gp.goal.x && gp.goal.x<=pac3.nextP[i4].x)))){deepg4=true;}
														//小笹修正
														//pad4=Math.abs((int)pac2.nextP[i3].x-(int)pac3.nextP[i4].x)+Math.abs((int)pac2.nextP[i3].y-(int)pac3.nextP[i4].y);
														//ppp5=Math.abs((int)pac3.nextP[i4].x-(int)gp.goal.x)+Math.abs((int)pac3.nextP[i4].y-(int)gp.goal.y);
														pad4=pac2.nextP[i3].dist4(pac3.nextP[i4]);
														ppp5=pac3.nextP[i4].dist4(gp.goal);
														/*mpac=pad1+pad2+pad3+pad4;
														for(int j=0;j<4;j++){
															if(gp.ghost[j]==null)continue;
															gmoved=pac3.nextP[i4].dist4(gp.ghost[j]);
															if(mghost>=gmoved)mghost=gmoved;
														}
														if(mghost<=mpac)dcost4=300+(mpac-mghost)*30;*/
														dcost4=ppp5+pad4-ppp4;//if(dcost3>=12)continue;
														for(int i5=0;i5<4;i5++){
															if(pac4.nextP[i5]==null || (i4==0 && i5==2) || (i4==1 && i5==3) || (i4==2 && i5==0) || (i4==3 && i5==1))continue;// || best_direction)continue;// || (gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].no_po && gp.no_pl))continue;
															else{
																pac5=gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x];
																gcost5=gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].powerpill_cost+gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].ghost_cost+gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].corner_cost;//+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp2_cost;
																if((pac3.nextP[i4].x==gp.goal.x && pac3.nextP[i4].y!=gp.goal.y && ((pac3.nextP[i4].y>=gp.goal.y && gp.goal.y>=pac4.nextP[i5].y) || (pac3.nextP[i4].y<=gp.goal.y && gp.goal.y<=pac4.nextP[i5].y))) || (pac3.nextP[i4].x!=gp.goal.x && pac3.nextP[i4].y==gp.goal.y && ((pac3.nextP[i4].x>=gp.goal.x && gp.goal.x>=pac4.nextP[i5].x) || (pac3.nextP[i4].x<=gp.goal.x && gp.goal.x<=pac4.nextP[i5].x))) && (gp.pill_count<=5 || gp.go_pp==true))dcost=dcostbase*(dcost1+dcost2+dcost3+dcost4)+(gcost1+gcost2+gcost3+gcost4+gcost5);
																else{
																	if((pac3.nextP[i4].x==gp.goal.x && pac3.nextP[i4].y!=gp.goal.y && ((pac3.nextP[i4].y>=gp.goal.y && gp.goal.y>=pac4.nextP[i5].y) || (pac3.nextP[i4].y<=gp.goal.y && gp.goal.y<=pac4.nextP[i5].y))) || (pac3.nextP[i4].x!=gp.goal.x && pac3.nextP[i4].y==gp.goal.y && ((pac3.nextP[i4].x>=gp.goal.x && gp.goal.x>=pac4.nextP[i5].x) || (pac3.nextP[i4].x<=gp.goal.x && gp.goal.x<=pac4.nextP[i5].x)))){deepg5=true;}
																	//小笹修正
																	//pad5=Math.abs((int)pac3.nextP[i4].x-(int)pac4.nextP[i5].x)+Math.abs((int)pac3.nextP[i4].y-(int)pac4.nextP[i5].y);
																	//ppp6=Math.abs((int)pac4.nextP[i5].x-(int)gp.goal.x)+Math.abs((int)pac4.nextP[i5].y-(int)gp.goal.y);
																	pad5=pac3.nextP[i4].dist4(pac4.nextP[i5]);
																	ppp6=pac4.nextP[i5].dist4(gp.goal);
																	/*mpac=pad1+pad2+pad3+pad4+pad5;
																	for(int j=0;j<4;j++){
																		if(gp.ghost[j]==null)continue;
																		gmoved=pac4.nextP[i5].dist4(gp.ghost[j]);
																		if(mghost>=gmoved)mghost=gmoved;
																	}
																	if(mghost<=mpac)dcost5=300+(mpac-mghost)*30;*/
																	dcost5=ppp6+pad5-ppp5;//if(dcost3>=12)continue;
																	if(deepg1==true){dcost1=dcost2=dcost3=dcost4=dcost5=0;}
																	else if(deepg2==true){dcost2=dcost3=dcost4=dcost5=0;}
																	else if(deepg3==true){dcost3=dcost4=dcost5=0;}
																	else if(deepg4==true){dcost4=dcost5=0;}
																	else if(deepg5==true){dcost5=0;}
																	dcost=dcostbase*(dcost1+dcost2+dcost3+dcost4+dcost5)+(gcost1+gcost2+gcost3+gcost4+gcost5);
																}
															}
															//if(dcost==0)best_direction=true;
															//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+"cost"+dcost);
															if(cost>dcost){
																cost=dcost;
																//System.out.println(gp.turn_count);
																if(gp.kp!=i || pacN.corner2 || gp.turn_count>=3)
																	direction=i+1;
																else direction=i2+1;
																gp.ke=i;
															}
														}
													}
													//if(dcost==0)best_direction=true;
													if(ddd==1){
														ddd=0;
														//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+"cost"+dcost);
														if(cost>dcost){
															cost=dcost;
															//System.out.println(gp.turn_count);
															if(gp.kp!=i || pacN.corner2 || gp.turn_count>=3)
																direction=i+1;
															else direction=i2+1;
															gp.ke=i;
														}
													}
												}
											}
										}
										//if(dcost==0)best_direction=true;
										if(ccc==1){
											ccc=0;
											//System.out.println(i+" "+i2+" "+i3+" "+"cost"+dcost);
											if(cost>dcost){
												cost=dcost;
												//System.out.println(gp.turn_count);
												if(gp.kp!=i || pacN.corner2 || gp.turn_count>=3)
													direction=i+1;
												else direction=i2+1;
												gp.ke=i;
											}
										}
									}
								}
							}
							//if(dcost==0)best_direction=true;
							if(bbb==1){
								bbb=0;
								//System.out.println(i+" "+i2+" "+"cost"+dcost);
								if(cost>dcost){
									cost=dcost;
									//System.out.println(gp.turn_count);
									if(gp.kp!=i || pacN.corner2 || gp.turn_count>=3)
										direction=i+1;
									else direction=i2+1;
									gp.ke=i;
								}
							}
						}
					}
				}
				//if(dcost==0)best_direction=true;
				if(aaa==1){
					aaa=0;
					//System.out.println(i+" "+"cost"+dcost);
					if(cost>dcost){
						cost=dcost;
						direction=i+1;
						gp.ke=i;
					}
				}
			}
		}
		gp.kp=gp.ke;
		return direction;
	}
}
