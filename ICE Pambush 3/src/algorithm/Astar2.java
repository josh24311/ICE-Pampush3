package algorithm;

import games.math.Vector2d;
import main.*;

//ÉSÅ[ÉXÉgÇ∆ÇÃãóó£Ç™àÍíËà»è„ÇÃÇ∆Ç´Ç…égópÇ∑ÇÈÅiãóó£ÉRÉXÉgÇÃÇ›ÇÃÇTê[Ç≥íTçıÅj
public class Astar2 {
	GameParameter gp;
	public Astar2(GameParameter gp){
		this.gp=gp;
	}
	
	public int run(int num){
		boolean best_direction=false;
		int direction=0;
		int cost=3000000;
		int dcost=1000000;
		int dcost1=0,dcost2=0,dcost3=0,dcost4=0,dcost5=0,dcost6=0,dcost7=0,dcost8=0,dcost9=0,dcost10=0;
		int gcost1=0,gcost2=0,gcost3=0,gcost4=0,gcost5=0,gcost6=0,gcost7=0,gcost8=0,gcost9=0,gcost10=0;
		int aaa=0,bbb=0,ccc=0,ddd=0,eee=0,fff=0,ggg=0,hhh=0,iii=0;
		int ppp1=0,ppp2=0,ppp3=0,ppp4=0,ppp5=0,ppp6=0,ppp7=0,ppp8=0,ppp9=0,ppp10=0,ppp11=0;
		int pad1=0,pad2=0,pad3=0,pad4=0,pad5=0,pad6=0,pad7=0,pad8=0,pad9=0,pad10=0;
		
		Node pacN=gp.node[(int)gp.pac.y][(int)gp.pac.x];
		Node pac1=pacN,pac2=pacN,pac3=pacN,pac4=pacN,pac5=pacN,pac6=pacN,pac7=pacN,pac8=pacN,pac9=pacN,pac10=pacN;
		
		//gcostÇÃèâä˙âª
		gp.pill_count=0;
		for(int i=0;i<gp.height/8;i++)for(int j=0;j<gp.width/8*2;j++){
			 gp.node[i][j].ghost_cost=0;gp.node[i][j].powerpill_cost=0;//gp.node[i][j].no_po=false;
			 if(gp.node[i][j].pill)gp.pill_count++;
			 /*if(gp.node[i][j].warp2_cost>0)gp.node[i][j].warp2_cost-=2000;
			//ÉèÅ[ÉvÉRÉXÉg
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
		//ÉèÅ[ÉvÇQÉRÉXÉg
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
		/*
		//ÉpÉèÅ[ÉsÉãÉRÉXÉg
		if(gp.nearest[gp.num_pp]!=null && gp.gpd>8){// && (gp.gpd>8 || gp.g4pd>50 || gp.gcount!=4)){
			Node pow_node=gp.node[(int)gp.nearest[gp.num_pp].y][(int)gp.nearest[gp.num_pp].x];
			for(int i=0;i<4;i++){
				if(pow_node.nextP[i]!=null){
					gp.node[(int)pow_node.nextP[i].y][(int)pow_node.nextP[i].x].powerpill_cost=0;			
					//gp.node[(int)pow_node.nextP[i].y][(int)pow_node.nextP[i].x].no_po=true;
				}
			}
		}
		*/
		//A*ÉAÉãÉSÉäÉYÉÄìÆçÏ
		if(gp.study==null)gp.goal=gp.nearest[num];
		else gp.goal=gp.study;
		//è¨ç˘èCê≥
		ppp1=Math.abs((int)gp.pac.x-(int)gp.goal.x)+Math.abs((int)gp.pac.y-(int)gp.goal.y);
		//ppp1=gp.pac.dist4(gp.goal);
		
		for(int i=0;i<4;i++){
			if(pacN.nextP[i]==null || best_direction)continue;// || (gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].no_po && gp.no_pl))continue;
			else{
				pac1=gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x];
				//gcost1=gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].warp_cost+gp.node[(int)pacN.nextP[i].y][(int)pacN.nextP[i].x].warp2_cost;
				if((gp.pac.x==gp.goal.x && gp.pac.y!=gp.goal.y && ((gp.pac.y>=gp.goal.y && gp.goal.y>=pacN.nextP[i].y) || (gp.pac.y<=gp.goal.y && gp.goal.y<=pacN.nextP[i].y))) || (gp.pac.x!=gp.goal.x && gp.pac.y==gp.goal.y && ((gp.pac.x>=gp.goal.x && gp.goal.x>=pacN.nextP[i].x) || (gp.pac.x<=gp.goal.x && gp.goal.x<=pacN.nextP[i].x)))){dcost=0;aaa=1;}//gcost1;aaa=1;}
				else{
					//è¨ç˘èCê≥
					//pad1=Math.abs((int)gp.pac.x-(int)pacN.nextP[i].x)+Math.abs((int)gp.pac.y-(int)pacN.nextP[i].y);
					//ppp2=Math.abs((int)pacN.nextP[i].x-(int)gp.goal.x)+Math.abs((int)pacN.nextP[i].y-(int)gp.goal.y);
					pad1=gp.pac.dist4(pacN.nextP[i]);
					ppp2=pacN.nextP[i].dist4(gp.goal);
					dcost1=ppp2+pad1-ppp1;//if(dcost1>=12)continue;
					for(int i2=0;i2<4;i2++){
						if(pac1.nextP[i2]==null || (i==0 && i2==2) || (i==1 && i2==3) || (i==2 && i2==0) || (i==3 && i2==1) || best_direction)continue;// || (gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].no_po && gp.no_pl))continue;
						else{
							pac2=gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x];
							//gcost2=gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].warp_cost+gp.node[(int)pac1.nextP[i2].y][(int)pac1.nextP[i2].x].warp2_cost;
							if((pacN.nextP[i].x==gp.goal.x && pacN.nextP[i].y!=gp.goal.y && ((pacN.nextP[i].y>=gp.goal.y && gp.goal.y>=pac1.nextP[i2].y) || (pacN.nextP[i].y<=gp.goal.y && gp.goal.y<=pac1.nextP[i2].y))) || (pacN.nextP[i].x!=gp.goal.x && pacN.nextP[i].y==gp.goal.y && ((pacN.nextP[i].x>=gp.goal.x && gp.goal.x>=pac1.nextP[i2].x) || (pacN.nextP[i].x<=gp.goal.x && gp.goal.x<=pac1.nextP[i2].x)))){dcost=1000*dcost1;bbb=1;}//+gcost1+gcost2;bbb=1;}
							else{
								//è¨ç˘èCê≥
								//pad2=Math.abs((int)pacN.nextP[i].x-(int)pac1.nextP[i2].x)+Math.abs((int)pacN.nextP[i].y-(int)pac1.nextP[i2].y);
								//ppp3=Math.abs((int)pac1.nextP[i2].x-(int)gp.goal.x)+Math.abs((int)pac1.nextP[i2].y-(int)gp.goal.y);
								pad2=pacN.nextP[i].dist4(pac1.nextP[i2]);
								ppp3=pac1.nextP[i2].dist4(gp.goal);
								dcost2=ppp3+pad2-ppp2;//if(dcost1+dcost2>=14)continue;
								for(int i3=0;i3<4;i3++){
									if(pac2.nextP[i3]==null || (i2==0 && i3==2) || (i2==1 && i3==3) || (i2==2 && i3==0) || (i2==3 && i3==1) || best_direction)continue;// || (gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].no_po && gp.no_pl))continue;
									else{
										pac3=gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x];
										//gcost3=gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp_cost+gp.node[(int)pac2.nextP[i3].y][(int)pac2.nextP[i3].x].warp2_cost;
										if((pac1.nextP[i2].x==gp.goal.x && pac1.nextP[i2].y!=gp.goal.y && ((pac1.nextP[i2].y>=gp.goal.y && gp.goal.y>=pac2.nextP[i3].y) || (pac1.nextP[i2].y<=gp.goal.y && gp.goal.y<=pac2.nextP[i3].y))) || (pac1.nextP[i2].x!=gp.goal.x && pac1.nextP[i2].y==gp.goal.y && ((pac1.nextP[i2].x>=gp.goal.x && gp.goal.x>=pac2.nextP[i3].x) || (pac1.nextP[i2].x<=gp.goal.x && gp.goal.x<=pac2.nextP[i3].x)))){dcost=1000*(dcost1+dcost2);ccc=1;}//+gcost1+gcost2+gcost3;ccc=1;}
										else{
											//è¨ç˘èCê≥
											//pad3=Math.abs((int)pac1.nextP[i2].x-(int)pac2.nextP[i3].x)+Math.abs((int)pac1.nextP[i2].y-(int)pac2.nextP[i3].y);
											//ppp4=Math.abs((int)pac2.nextP[i3].x-(int)gp.goal.x)+Math.abs((int)pac2.nextP[i3].y-(int)gp.goal.y);
											pad3=pac1.nextP[i2].dist4(pac2.nextP[i3]);
											ppp4=pac2.nextP[i3].dist4(gp.goal);
											dcost3=ppp4+pad3-ppp3;//(dcost1+dcost2+dcost3>=16)continue;
											for(int i4=0;i4<4;i4++){
												if(pac3.nextP[i4]==null || (i3==0 && i4==2) || (i3==1 && i4==3) || (i3==2 && i4==0) || (i3==3 && i4==1) || best_direction)continue;// || (gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].no_po && gp.no_pl))continue;
												else{
													pac4=gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x];
													//gcost4=gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].warp_cost+gp.node[(int)pac3.nextP[i4].y][(int)pac3.nextP[i4].x].warp2_cost;
													if((pac2.nextP[i3].x==gp.goal.x && pac2.nextP[i3].y!=gp.goal.y && ((pac2.nextP[i3].y>=gp.goal.y && gp.goal.y>=pac3.nextP[i4].y) || (pac2.nextP[i3].y<=gp.goal.y && gp.goal.y<=pac3.nextP[i4].y))) || (pac2.nextP[i3].x!=gp.goal.x && pac2.nextP[i3].y==gp.goal.y && ((pac2.nextP[i3].x>=gp.goal.x && gp.goal.x>=pac3.nextP[i4].x) || (pac2.nextP[i3].x<=gp.goal.x && gp.goal.x<=pac3.nextP[i4].x)))){dcost=1000*(dcost1+dcost2+dcost3);ddd=1;}//+gcost1+gcost2+gcost3+gcost4;ddd=1;}
													else{
														//è¨ç˘èCê≥
														//pad4=Math.abs((int)pac2.nextP[i3].x-(int)pac3.nextP[i4].x)+Math.abs((int)pac2.nextP[i3].y-(int)pac3.nextP[i4].y);
														//ppp5=Math.abs((int)pac3.nextP[i4].x-(int)gp.goal.x)+Math.abs((int)pac3.nextP[i4].y-(int)gp.goal.y);
														pad4=pac2.nextP[i3].dist4(pac3.nextP[i4]);
														ppp5=pac3.nextP[i4].dist4(gp.goal);
														dcost4=ppp5+pad4-ppp4;//(dcost1+dcost2+dcost3+dcost4>=18)continue;
														for(int i5=0;i5<4;i5++){
															if(pac4.nextP[i5]==null || (i4==0 && i5==2) || (i4==1 && i5==3) || (i4==2 && i5==0) || (i4==3 && i5==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
															else{
																pac5=gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x];
																//gcost5=gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].warp_cost+gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].warp2_cost;
																if((pac3.nextP[i4].x==gp.goal.x && pac3.nextP[i4].y!=gp.goal.y && ((pac3.nextP[i4].y>=gp.goal.y && gp.goal.y>=pac4.nextP[i5].y) || (pac3.nextP[i4].y<=gp.goal.y && gp.goal.y<=pac4.nextP[i5].y))) || (pac3.nextP[i4].x!=gp.goal.x && pac3.nextP[i4].y==gp.goal.y && ((pac3.nextP[i4].x>=gp.goal.x && gp.goal.x>=pac4.nextP[i5].x) || (pac3.nextP[i4].x<=gp.goal.x && gp.goal.x<=pac4.nextP[i5].x)))){dcost=1000*(dcost1+dcost2+dcost3+dcost4);eee=1;}//+gcost1+gcost2+gcost3+gcost4+gcost5;
																else{
																	//è¨ç˘èCê≥
																	//pad5=Math.abs((int)pac3.nextP[i4].x-(int)pac4.nextP[i5].x)+Math.abs((int)pac3.nextP[i4].y-(int)pac4.nextP[i5].y);
																	//ppp6=Math.abs((int)pac4.nextP[i5].x-(int)gp.goal.x)+Math.abs((int)pac4.nextP[i5].y-(int)gp.goal.y);
																	pad5=pac3.nextP[i4].dist4(pac4.nextP[i5]);
																	ppp6=pac4.nextP[i5].dist4(gp.goal);
																	dcost5=ppp6+pad5-ppp5;if(dcost1+dcost2+dcost3+dcost4+dcost5>=20)continue;
																	//dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5);//+gcost1+gcost2+gcost3+gcost4+gcost5;
																	for(int i6=0;i6<4;i6++){
																		if(pac5.nextP[i6]==null || (i5==0 && i6==2) || (i5==1 && i6==3) || (i5==2 && i6==0) || (i5==3 && i6==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
																		else{
																			pac6=gp.node[(int)pac5.nextP[i6].y][(int)pac5.nextP[i6].x];
																			//gcost6=gp.node[(int)pac5.nextP[i6].y][(int)pac5.nextP[i6].x].warp_cost+gp.node[(int)pac5.nextP[i6].y][(int)pac5.nextP[i6].x].warp2_cost;
																			if((pac4.nextP[i5].x==gp.goal.x && pac4.nextP[i5].y!=gp.goal.y && ((pac4.nextP[i5].y>=gp.goal.y && gp.goal.y>=pac5.nextP[i6].y) || (pac4.nextP[i5].y<=gp.goal.y && gp.goal.y<=pac5.nextP[i6].y))) || (pac4.nextP[i5].x!=gp.goal.x && pac4.nextP[i5].y==gp.goal.y && ((pac4.nextP[i5].x>=gp.goal.x && gp.goal.x>=pac5.nextP[i6].x) || (pac4.nextP[i5].x<=gp.goal.x && gp.goal.x<=pac5.nextP[i6].x)))){dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5);fff=1;}//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6;
																			else{
																				//è¨ç˘èCê≥
																				//pad6=Math.abs((int)pac4.nextP[i5].x-(int)pac5.nextP[i6].x)+Math.abs((int)pac4.nextP[i5].y-(int)pac5.nextP[i6].y);
																				//ppp7=Math.abs((int)pac5.nextP[i6].x-(int)gp.goal.x)+Math.abs((int)pac5.nextP[i6].y-(int)gp.goal.y);
																				pad6=pac4.nextP[i5].dist4(pac5.nextP[i6]);
																				ppp7=pac5.nextP[i6].dist4(gp.goal);
																				dcost6=ppp7+pad6-ppp6;if(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6>=20)continue;
																				for(int i7=0;i7<4;i7++){
																					if(pac6.nextP[i7]==null || (i6==0 && i7==2) || (i6==1 && i7==3) || (i6==2 && i7==0) || (i6==3 && i7==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
																					else{
																						pac7=gp.node[(int)pac6.nextP[i7].y][(int)pac6.nextP[i7].x];
																						//gcost7=gp.node[(int)pac6.nextP[i7].y][(int)pac6.nextP[i7].x].warp_cost+gp.node[(int)pac6.nextP[i7].y][(int)pac6.nextP[i7].x].warp2_cost;
																						if((pac5.nextP[i6].x==gp.goal.x && pac5.nextP[i6].y!=gp.goal.y && ((pac5.nextP[i6].y>=gp.goal.y && gp.goal.y>=pac6.nextP[i7].y) || (pac5.nextP[i6].y<=gp.goal.y && gp.goal.y<=pac6.nextP[i7].y))) || (pac5.nextP[i6].x!=gp.goal.x && pac5.nextP[i6].y==gp.goal.y && ((pac5.nextP[i6].x>=gp.goal.x && gp.goal.x>=pac6.nextP[i7].x) || (pac5.nextP[i6].x<=gp.goal.x && gp.goal.x<=pac6.nextP[i7].x)))){dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6);ggg=1;}//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6+gcost7;
																						else{
																							//è¨ç˘èCê≥
																							//pad7=Math.abs((int)pac5.nextP[i6].x-(int)pac6.nextP[i7].x)+Math.abs((int)pac5.nextP[i6].y-(int)pac6.nextP[i7].y);
																							//ppp8=Math.abs((int)pac6.nextP[i7].x-(int)gp.goal.x)+Math.abs((int)pac6.nextP[i7].y-(int)gp.goal.y);
																							pad7=pac5.nextP[i6].dist4(pac6.nextP[i7]);
																							ppp8=pac6.nextP[i7].dist4(gp.goal);
																							dcost7=ppp8+pad7-ppp7;if(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7>=20)continue;
																							//dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7);
																							for(int i8=0;i8<4;i8++){
																								if(pac7.nextP[i8]==null || (i7==0 && i8==2) || (i7==1 && i8==3) || (i7==2 && i8==0) || (i7==3 && i8==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
																								else{
																									pac8=gp.node[(int)pac7.nextP[i8].y][(int)pac7.nextP[i8].x];
																									//gcost8=gp.node[(int)pac7.nextP[i8].y][(int)pac7.nextP[i8].x].warp_cost+gp.node[(int)pac7.nextP[i8].y][(int)pac7.nextP[i8].x].warp2_cost;
																									if((pac6.nextP[i7].x==gp.goal.x && pac6.nextP[i7].y!=gp.goal.y && ((pac6.nextP[i7].y>=gp.goal.y && gp.goal.y>=pac7.nextP[i8].y) || (pac6.nextP[i7].y<=gp.goal.y && gp.goal.y<=pac7.nextP[i8].y))) || (pac6.nextP[i7].x!=gp.goal.x && pac6.nextP[i7].y==gp.goal.y && ((pac6.nextP[i7].x>=gp.goal.x && gp.goal.x>=pac7.nextP[i8].x) || (pac6.nextP[i7].x<=gp.goal.x && gp.goal.x<=pac7.nextP[i8].x)))){dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7);hhh=1;}//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6+gcost7+gcost8;
																									else{
																										//è¨ç˘èCê≥
																										//pad8=Math.abs((int)pac6.nextP[i7].x-(int)pac7.nextP[i8].x)+Math.abs((int)pac6.nextP[i7].y-(int)pac7.nextP[i8].y);
																										//ppp9=Math.abs((int)pac7.nextP[i8].x-(int)gp.goal.x)+Math.abs((int)pac7.nextP[i8].y-(int)gp.goal.y);
																										pad8=pac6.nextP[i7].dist4(pac7.nextP[i8]);
																										ppp9=pac7.nextP[i8].dist4(gp.goal);
																										dcost8=ppp9+pad8-ppp8;if(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8>=20)continue;
																										for(int i9=0;i9<4;i9++){
																											if(pac8.nextP[i9]==null || (i8==0 && i9==2) || (i8==1 && i9==3) || (i8==2 && i9==0) || (i8==3 && i9==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
																											else{
																												pac9=gp.node[(int)pac8.nextP[i9].y][(int)pac8.nextP[i9].x];
																												//gcost9=gp.node[(int)pac8.nextP[i9].y][(int)pac8.nextP[i9].x].warp_cost+gp.node[(int)pac8.nextP[i9].y][(int)pac8.nextP[i9].x].warp2_cost;
																												if((pac7.nextP[i8].x==gp.goal.x && pac7.nextP[i8].y!=gp.goal.y && ((pac7.nextP[i8].y>=gp.goal.y && gp.goal.y>=pac8.nextP[i9].y) || (pac7.nextP[i8].y<=gp.goal.y && gp.goal.y<=pac8.nextP[i9].y))) || (pac7.nextP[i8].x!=gp.goal.x && pac7.nextP[i8].y==gp.goal.y && ((pac7.nextP[i8].x>=gp.goal.x && gp.goal.x>=pac8.nextP[i9].x) || (pac7.nextP[i8].x<=gp.goal.x && gp.goal.x<=pac8.nextP[i9].x)))){dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8);iii=1;}//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6+gcost7+gcost8+gcost9;
																												else{
																													//è¨ç˘èCê≥
																													//pad9=Math.abs((int)pac7.nextP[i8].x-(int)pac8.nextP[i9].x)+Math.abs((int)pac7.nextP[i8].y-(int)pac8.nextP[i9].y);
																													//ppp10=Math.abs((int)pac8.nextP[i9].x-(int)gp.goal.x)+Math.abs((int)pac8.nextP[i9].y-(int)gp.goal.y);
																													pad9=pac7.nextP[i8].dist4(pac8.nextP[i9]);
																													ppp10=pac8.nextP[i9].dist4(gp.goal);
																													dcost9=ppp10+pad9-ppp9;if(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8+dcost9>=20)continue;
																													for(int i10=0;i10<4;i10++){
																														if(pac9.nextP[i10]==null || (i9==0 && i10==2) || (i9==1 && i10==3) || (i9==2 && i10==0) || (i9==3 && i10==1) || best_direction)continue;// || (gp.node[(int)pac4.nextP[i5].y][(int)pac4.nextP[i5].x].no_po && gp.no_pl))continue;
																														else{
																															pac10=gp.node[(int)pac9.nextP[i10].y][(int)pac9.nextP[i10].x];
																															//gcost10=gp.node[(int)pac9.nextP[i10].y][(int)pac9.nextP[i10].x].warp_cost+gp.node[(int)pac9.nextP[i10].y][(int)pac9.nextP[i10].x].warp2_cost;
																															if((pac8.nextP[i9].x==gp.goal.x && pac8.nextP[i9].y!=gp.goal.y && ((pac8.nextP[i9].y>=gp.goal.y && gp.goal.y>=pac9.nextP[i10].y) || (pac8.nextP[i9].y<=gp.goal.y && gp.goal.y<=pac9.nextP[i10].y))) || (pac8.nextP[i9].x!=gp.goal.x && pac8.nextP[i9].y==gp.goal.y && ((pac8.nextP[i9].x>=gp.goal.x && gp.goal.x>=pac9.nextP[i10].x) || (pac8.nextP[i9].x<=gp.goal.x && gp.goal.x<=pac9.nextP[i10].x))))dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8+dcost9);//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6+gcost7+gcost8+gcost9+gcost10;
																															else{
																																//è¨ç˘èCê≥
																																//pad10=Math.abs((int)pac8.nextP[i9].x-(int)pac9.nextP[i10].x)+Math.abs((int)pac8.nextP[i9].y-(int)pac9.nextP[i10].y);
																																//ppp11=Math.abs((int)pac9.nextP[i10].x-(int)gp.goal.x)+Math.abs((int)pac9.nextP[i10].y-(int)gp.goal.y);
																																pad10=pac8.nextP[i9].dist4(pac9.nextP[i10]);
																																ppp11=pac9.nextP[i10].dist4(gp.goal);
																																dcost10=ppp11+pad10-ppp10;if(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8+dcost9+dcost10>=20)continue;
																																dcost=1000*(dcost1+dcost2+dcost3+dcost4+dcost5+dcost6+dcost7+dcost8+dcost9+dcost10);//+gcost1+gcost2+gcost3+gcost4+gcost5+gcost6+gcost7+gcost8+gcost9+gcost10;
																															}
																														}
																														if(dcost==0)best_direction=true;
																														//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7+" "+i8+" "+i9+" "+i10+" "+"cost"+dcost);
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
																												if(dcost==0)best_direction=true;
																												if(iii==1){
																													iii=0;
																													//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7+" "+i8+" "+i9+" "+"cost"+dcost);
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
																									if(dcost==0)best_direction=true;
																									if(hhh==1){
																										hhh=0;
																										//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7+" "+i8+" "+"cost"+dcost);
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
																						if(dcost==0)best_direction=true;
																						if(ggg==1){
																							ggg=0;
																							//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+i7+" "+"cost"+dcost);
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
																			if(dcost==0)best_direction=true;
																			if(fff==1){
																				fff=0;
																				//System.out.println(i+" "+i2+" "+i3+" "+i4+" "+i5+" "+i6+" "+"cost"+dcost);
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
																if(dcost==0)best_direction=true;
																if(eee==1){
																	eee=0;
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
														}
													}
													if(dcost==0)best_direction=true;
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
										if(dcost==0)best_direction=true;
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
							if(dcost==0)best_direction=true;
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
				if(dcost==0)best_direction=true;
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
