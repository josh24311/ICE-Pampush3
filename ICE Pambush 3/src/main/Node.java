package main;

import games.math.Vector2d;

/**
 * ノードデータ 
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Ｄｅｃ_02_2008_Tue
 */

public class Node {
	
	/**通ることができるポイント*/
		public boolean can_point;
	
	/**ゲームエリア*/
		public boolean game_area;
		
	/**パワーピルエリア*/
		public boolean pp_area;
		
	/**オブジェクトの存在*/
		public boolean 
			pacman,
			pill, pp, back, corner,corner2, warp, warpSide, noWarp, warpSide2,
			ghost[]=new boolean[4], edible,wall, item, base;
	
	/**ポイント番号*/
	public int corner_num,corner2_num,warp_num;
	
	/**次の分岐ポイント*/
		public Vector2d nextP[]=new Vector2d[4];
		
	/**コスト値*/
		public int cost;
		
	/**コスト値*/
	public int ghost_cost,powerpill_cost,corner_cost,warp_cost,warp2_cost;
	
	GameParameter gp;
	public Node(GameParameter gp){
		this.gp=gp;
		reset();
	}
	
	/**リセット*/
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
	
	/**オブジェクト設置*/
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
	
	/**オブジェクト設置(学習用)*/
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
	
	/**オブジェクト設置(その他削除)*/
	private void setOnly(){
		this.can_point=true;
		pill=false;pp=false;edible=false;item=false;
		for(int i=0;i<4;i++)
			if(!noWarp)ghost[i]=false;
		noWarp=false;
		for(int i=0;i<4;i++) if(ghost[i]) noWarp=true;
	}
	
	/**ゲームエリア設定*/
	public void setGameArea(){
		this.game_area=true;
	}
	
	/**パワーピルエリア設定*/
	public void setPPArea(){
		this.pp_area=true;
	}
	
	/**壁設定*/
	public void setWall(){
		can_point=false;
		pill=pp=back=corner=corner2=warp=warpSide=edible=item=false;
		for(int i=0;i<4;i++)ghost[i]=false;
		wall=true;
	}
	
	/**ゴーストの基地設定*/
	public void setBase(){
		setWall();
		base=true;
	}
}
