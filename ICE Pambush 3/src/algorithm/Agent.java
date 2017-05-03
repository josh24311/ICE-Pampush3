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
		//int kp=1;//�P�O�̐i�s����
		
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
		
		//�ł��߂��p���[�s���ɍł��߂��S�[�X�g�Ƃ̋���
		if(Exist(gp.num_pp)){
			for(int i=0;i<4;i++){
				if(gp.ghost[i]==null)continue;
				//�����C��
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
		//���ꂼ��̃I�u�W�F�N�g�Ƃ̍��W�����𑪒�
		//if(Exist(gp.num_pp) && Exist(gp.num_nearGhost))god=(int)gp.pac.dist3(gp.nearest[gp.num_pp],gp.nearest[gp.num_nearGhost]);
		//�����C��
		//if(Exist(gp.num_pp))pod=(int)gp.pac.dist2(gp.nearest[gp.num_pp]);
		//if(Exist(gp.num_edible))epd=(int)gp.pac.dist2(gp.nearest[gp.num_edible]);
		//if(Exist(gp.num_nearGhost))gp.gpd=(int)gp.pac.dist2(gp.nearest[gp.num_nearGhost]);
		if(Exist(gp.num_pill))pid=(int)gp.pac.dist4(gp.nearest[gp.num_pill]);
		if(Exist(gp.num_pp))pod=(int)gp.pac.dist4(gp.nearest[gp.num_pp]);
		if(Exist(gp.num_edible))epd=(int)gp.pac.dist4(gp.nearest[gp.num_edible]);
		if(Exist(gp.num_nearGhost))gp.gpd=(int)gp.pac.dist4(gp.nearest[gp.num_nearGhost]);

		
		//���z��Ԃ��܂܂Ȃ��ł��߂��S�[�X�g�Ƃ̋���
		gp.gpd2=100;
		for(int i=0;i<4;i++){
			if(gp.ghost[i]==null)continue;
			//�����C��
			//gp.gpdk=Math.abs((int)gp.pac.x-(int)gp.ghost[i].x)+Math.abs((int)gp.pac.y-(int)gp.ghost[i].y);
			gp.gpdk=gp.pac.dist4(gp.ghost[i]);
			if(gp.gpdk<gp.gpd2)gp.gpd2=gp.gpdk;
		}
		
		//�i�s�����̐��𐔂���
		gp.turn_count=0;
		for(int i=0;i<4;i++){
			if(pacN.nextP[i]!=null)gp.turn_count++;
		}
		//�S�[�X�g�Ƃ̍��v�����ƟB�̊O�ɏo�Ă���S�[�X�g�̐����v�Z
		for(int i=0;i<4;i++){
			if(gp.ghost[i]==null){gp.gcount--;continue;}
			gp.g4px+=gp.ghost[i].x;
			//�����C��
			//gp.g4pd+=(int)gp.pac.dist2(gp.ghost[i]);
			gp.g4pd+=(int)gp.pac.dist4(gp.ghost[i]);
		}
		
		//�S�[�X�g���߂��ƔF�����鋗���̕ω�
		int length=0;
		if(gp.scan[1])length=3; else length=gp.distBorder;
		
		//���쌈��
		/*
		//�S�[�X�g���S�̂��Ȃ��Ƃ��̓���
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
		//�҂���������
		//System.out.println(gp.gpd+" "+pod+" "+god);
		
		if(gp.study==null && pod<=5 && gp.gpd2>=4 && god>=6 && gp.gpd2<=length && Exist(gp.num_pp) && gp.realArea((int)gp.nearest[gp.num_pp].x) && gp.stage==0){
			direction=0;wait=true;gp.stop=1;//System.out.println("matibuse");
		}
		
		else if(gp.study==null && pod<=3 && gp.gpd2>=4 && god>=4  && gp.gpd2<=length+length && Exist(gp.num_pp) && gp.realArea((int)gp.nearest[gp.num_pp].x) && gp.stage==1){
			direction=0;wait=true;gp.stop=1;//System.out.println("matibuse");
		}
		
		//System.out.println(gp.gpd+" "+pod+" "+god);
		//���[�v��1�[�����ɂ���Ƃ��̓���i�ǂɂԂ���܂Ői�s�����������Ȃ��j
		/*
		else if(gp.nearest[gp.num_warp]!=null){
		Node warpN=gp.node[(int)gp.nearest[gp.num_warp].y][(int)gp.nearest[gp.num_warp].x];
		if(gp.pac.y==gp.nearest[gp.num_warp].y && (int)warpN.nextP[3].x+2<gp.pac.x && gp.pac.x<(int)warpN.nextP[1].x-2)direction=gp.kp;
		*/
		//Astar��p���铮��
		
		else if(gp.gpd<=length){
			//�ł��߂��p���[�s���֌�����
			if(Hantei(1) && gp.gpd2<=8){// && gp.gcount==4 && gp.g4pd<=50){
				//gp.no_pl=false;
				gp.go_pp=true;
				if(gp.stop==1 && (gp.gpd2<=4 || god<=6) && gp.stage==0){direction=as2.run(gp.num_pp);gp.stop=1;}
				else if(gp.stop==1 && (gp.gpd2<=4 || god<=4) && gp.stage==1){direction=as2.run(gp.num_pp);gp.stop=1;}
				else if(pod<=4){direction=as2.run(gp.num_pp);gp.stop=0;}
				else direction=as.run(gp.num_pp);gp.stop=0;
			}
		
			//�ł��߂��G�f�B�u���Ɍ�����
			else if(Exist(gp.num_edible) && epd<=10){
				gp.stop=0;
				direction=as.run(gp.num_edible);
			}
		
			//�ł��߂��s���֌�����
			else if(Exist(gp.num_pill)){// && (turn_count>=3 || pacN.nextP[kp-1]!=null)){
				gp.stop=0;
				direction=as.run(gp.num_pill);
			}
		}
		/*
		//�ł��߂����[�v�n�_�֌�����
		else if(Exist(gp.num_warp) && gp.gpd<=6 && gp.nearest[gp.num_nearGhost]!=null){
			if(!gp.RealArea((int)gp.nearest[gp.num_nearGhost].x))
				direction=as.run(gp.num_pill);
			else direction=as.run(gp.num_warp);
		}
		*/
		//Astar2��p���铮��
		else{
			gp.stop=0;
			//�ł��߂��G�f�B�u���֌�����
			if(Exist(gp.num_edible) && epd<=10){
				direction=as2.run(gp.num_edible);
			}
		
			//�ł��߂��s���֌�����
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
		//�O�̓�����J��Ԃ�
		else {
			direction=kp;
		}
		//�i�s�����̕ۑ�
		kp=direction;
		*/
		//}
		return direction;
	}
	
	private boolean Hantei(int num){
		switch(num){
		//�p���[�s��
		case 1: return Exist(gp.num_pp) && gp.nearest[gp.num_edible]==null;
		default: return false;
		}
	}
	
	private boolean Exist(int num){
		return gp.nearest[num]!=null;
	}
}
