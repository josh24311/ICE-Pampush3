package main;

import games.math.*;

/**
 * ゲームパラメータ 
 * @author ChotaTokuyama
 * @version 2.2
 * @since Dec_02_2008_Tue
 */
public class GameParameter {	
	public GameParameter(){
		
	}
	
	/**距離コストとゴーストコストの比*/
		public double costRatio;
	
	/**A*とA*2の基準*/
		public int distBorder;
	
	/**ステージ番号*/
		public int stage;
	
	/**スキャン開始座標*/
		public int left, top;
		
	/**ゲームの広さ*/
		public int width, gameWidth, height, gameHeight;
	
	/**マップの広さ*/
		public int mapWidth,mapHeight;
		
	/**リアルエリアの範囲*/
		public int REAL[];
		
	/**マップ色情報*/
		public int pix[];
		
	/**オブジェクトナンバー*/
		
		public int num_wall=0, num_back=1,num_pill=2, num_pp=3,
					num_ghost[]={4,5,6,7}, num_edible=8, num_pacman=9,
					num_corner=10,num_corner2=11,
					num_warp=12,num_warpSide=13,num_warpSide2=14,
					num_item=15,
					num_nearGhost=16,
					num_GaP=17,
					num_pill2=18,
					num_num=19,
					num_gameover=100;
	/**オブジェクト色*/
		public int 
		blue, red, yellow, purple, green, brown, white, pink,black,orange, 
		pillColor[],pacColor; 
	
	/**オブジェクト座標*/
		public Vector2d 
			pac,ghost[],ghost2[],nearest[],
			corner[],corner2[],warp[];
	
	/**ゲームマップ*/
		public Node node[][];
		
	/**オブジェクト発見*/
		public boolean find;
		
	/**パワーピルの数*/
		public int pp_num=0; 
	
	/**マップ状況把握*/
		public boolean scan[];
		
	/**コーナー・ワープ地点発見*/
		public boolean findCW;
	
	/**次コーナー地点発見*/
		public boolean findNextP;
		
	/**エディブル発見*/
		public boolean findE;
		
	/**目的地*/
		public Vector2d goal;
	
	/**コスト数値*/
		public int cost[]=new int[num_num];
	
	/**パックマンの固定*/
		public boolean pacLock;
	
	/**コーナー地点同士のリンク*/
		//public NodeLink[] link;
		
	/**松本追加*/
		public int gpd=100,gpd2=100,gpdk=100,gcount=4,g4pd=0,g4px=0,
					turn_count=0,kp=0,ke=0,pill_count,stop=0,stopk=0;
		public boolean go_pp=false;
	
	/**学習用動作方向*/
		public Vector2d study;
		
	/**スレッド化させるグループ*/
		int thread_num=2;
		public ThreadGroup[] thread;
	
	/**色値設定*/
	public void setColor(int blinky,int pinky,int inky,int sue,int pacMan,
			int edible,int pill1,int pill2,int pill3,int pill4,int back){
		//========================================================= 
		blue=inky; 
		red=blinky; 
		yellow=pacMan; 
		orange=sue; 
		purple=edible; 
		green=-16711936; 
		brown=-2189497; 
		white=-2171170; 
		pink=pinky; 
		black=back; 
		//========================================================= 
		pacColor=pacMan; 
		pillColor[0]=pill1; pillColor[1]=pill2; 
		pillColor[2]=pill3; pillColor[3]=pill4; 
		//itemColor[2]は林檎のへた,itemColor[3]は林檎の白いとこ
	}
		
	/**リセット*/
	public void reset(){
		//study=new Vector2d();
		//study.set(26,2);
		this.find=false;
		this.pac=null;
		for(int i=0;i<4;i++){
			this.ghost[i]=null;this.ghost2[i]=null;
		}
		for(int i=0;i<num_num;i++) this.nearest[i]=null;
		for(int i=0;i<this.mapHeight;i++)
			for(int j=0;j<this.mapWidth;j++)
				node[i][j].reset();
		for(int i=0;i<4;i++) scan[i]=true;
		findCW=false;
		findNextP=false;
		findE=false;
		pacLock=false;
		p_x=-1;p_y=-1;p_now=0;
	}
	
	/**
	 * 初期設定
	 * @param left スキャン開始x座標
	 * @param top　スキャン開始y座標
	 * @param width　ゲームの広さ(横)
	 * @param height　ゲームの広さ(縦)
	 */
	public void set(int left,int top,int width,int height){
		this.left=left; this.top=top;
		this.width=width; this.height=height;
		this.gameWidth=this.width/8;
		this.gameHeight=this.height/8;
		this.mapWidth=this.gameWidth*2;
		this.mapHeight=this.gameHeight;
		this.REAL=new int[2];
			this.REAL[0]=gameWidth/2+(gameWidth)%2;
			this.REAL[1]=gameWidth+gameWidth/2+(gameWidth)%2;
		
		this.scan=new boolean[4];
			
		this.pac=null;
		this.pix=new int[this.height*this.width];
		this.node=new Node[this.mapHeight][this.mapWidth];
		for(int i=0;i<this.mapHeight;i++)
			for(int j=0;j<this.mapWidth;j++)
				node[i][j]=new Node(this);
		this.stage=0;
		this.pillColor=new int[4];
		this.ghost=new Vector2d[4]; this.ghost2=new Vector2d[4];
		this.nearest=new Vector2d[num_num+2];
		corner=new Vector2d[100]; corner2=new Vector2d[100];
		warp=new Vector2d[8];
		//link=new NodeLink[100];
		thread=new ThreadGroup[thread_num];
		reset();
	}
	
	/**リアルエリア判定*/
	public boolean realArea(int x){
		return x>=REAL[0] && x<REAL[1];
	}
	/**リアルエリア判定*/
	public boolean realArea(int x,int num){
		if(num==1) return x>=REAL[0];
		else return x<REAL[1];
	}
	
	/**オブジェクト発見*/
	public void find(boolean bool){find=bool;}
	
	/**スキャン実行*/
	public void scan(int num){
		//System.out.println(num+" scaned");
		scan[num]=false;
	}
	
	int p_x=-1,p_y=-1,p_now;
	/**コーナー・ワープ地点スキャンストップ*/
	public boolean scanCW(Vector2d p){
		if(p==null){p_x=-1; p_y=-1; return true;}
		else if(p_x==-1 || p_y==-1){
			p_x=(int)p.x; p_y=(int)p.y;
		}
		else if((int)p.x!=p_x || (int)p.y!=p_y){
			p_x=(int)p.x; p_y=(int)p.y; p_now++;
		}
		if(p_now>5) return false;
		else return true;
	}
	
	/**コーナー・ワープ地点発見*/
	public void findCW(){findCW=true;}
	
	/**次コーナー地点発見*/
	public void findNextP(){findNextP=true;};
	
	/**エディブル発見*/
	public void findE(boolean bool){findE=bool;}
	
	/**パックマンの固定*/
	public void pacLock(boolean bool){pacLock=bool;}
}
