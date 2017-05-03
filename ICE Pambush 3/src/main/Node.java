package main;

import games.math.Vector2d;

/**
 * �m�[�h�f�[�^ 
 * @author�@ChotaTokuyama
 * @version 2.2
 * @since�@�c����_02_2008_Tue
 */

public class Node {
	
	/**�ʂ邱�Ƃ��ł���|�C���g*/
		public boolean can_point;
	
	/**�Q�[���G���A*/
		public boolean game_area;
		
	/**�p���[�s���G���A*/
		public boolean pp_area;
		
	/**�I�u�W�F�N�g�̑���*/
		public boolean 
			pacman,
			pill, pp, back, corner,corner2, warp, warpSide, noWarp, warpSide2,
			ghost[]=new boolean[4], edible,wall, item, base;
	
	/**�|�C���g�ԍ�*/
	public int corner_num,corner2_num,warp_num;
	
	/**���̕���|�C���g*/
		public Vector2d nextP[]=new Vector2d[4];
		
	/**�R�X�g�l*/
		public int cost;
		
	/**�R�X�g�l*/
	public int ghost_cost,powerpill_cost,corner_cost,warp_cost,warp2_cost;
	
	GameParameter gp;
	public Node(GameParameter gp){
		this.gp=gp;
		reset();
	}
	
	/**���Z�b�g*/
	public void reset(){
		pacman=false;
		can_point=false;
		game_area=false; pp_area=false;
		pill=false;pp=false;back=false;corner=false;corner2=false;
		warp=false;warpSide=false;warpSide2=false;noWarp=false;
		edible=false;wall=false;item=false;
		for(int i=0;i<4;i++) ghost[i]=false;
		wall=false;
		base=false;
	}
	
	/**�I�u�W�F�N�g�ݒu*/
	public void set(int num){
		if(!wall){
			if(num==gp.num_back) setOnly();
			else if(num==gp.num_pill){setOnly(); pill=true;}
			else if(num==gp.num_pp){setOnly(); pp=true;}
			else if(num==gp.num_pacman){setOnly();}
			else if(num==gp.num_edible && can_point){
				edible=true;
				for(int i=0;i<4;i++) ghost[i]=false;
				noWarp=false;
			}
			else if(num==gp.num_item) item=true;
			else if(num==gp.num_corner){
				corner=true; corner2=false; 
			}
			else if(num==gp.num_corner2){
				corner=false; corner2=true;
			}
			else if(num==gp.num_warp) warp=true;
			else for(int i=0;i<4;i++)
					if(num==gp.num_ghost[i] && can_point && !item){
						ghost[i]=true;
						if(warp)noWarp=true;
						for(int j=0;j<4;j++) if(j!=i) ghost[j]=false;
					}
		}
	}
	
	/**�I�u�W�F�N�g�ݒu(�w�K�p)*/
	public void set2(int num){
		can_point=true;
		if(num==gp.num_pill) pill=true;
		if(num==gp.num_pp) pp=true;
		for(int i=0;i<4;i++){
			if(num==gp.num_ghost[i]) ghost[i]=true;
		}
		if(num==gp.num_edible) edible=true;
		if(num==gp.num_item) item=true;
	}
	
	/**�I�u�W�F�N�g�ݒu(���̑��폜)*/
	private void setOnly(){
		this.can_point=true;
		pill=false;pp=false;edible=false;item=false;
		for(int i=0;i<4;i++)
			if(!noWarp)ghost[i]=false;
		noWarp=false;
		for(int i=0;i<4;i++) if(ghost[i]) noWarp=true;
	}
	
	/**�Q�[���G���A�ݒ�*/
	public void setGameArea(){
		this.game_area=true;
	}
	
	/**�p���[�s���G���A�ݒ�*/
	public void setPPArea(){
		this.pp_area=true;
	}
	
	/**�ǐݒ�*/
	public void setWall(){
		can_point=false;
		pill=pp=back=corner=corner2=warp=warpSide=edible=item=false;
		for(int i=0;i<4;i++)ghost[i]=false;
		wall=true;
	}
	
	/**�S�[�X�g�̊�n�ݒ�*/
	public void setBase(){
		setWall();
		base=true;
	}
}
