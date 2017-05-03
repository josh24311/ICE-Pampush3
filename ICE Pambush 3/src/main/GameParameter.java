package main;

import games.math.*;

/**
 * �Q�[���p�����[�^ 
 * @author ChotaTokuyama
 * @version 2.2
 * @since Dec_02_2008_Tue
 */
public class GameParameter {	
	public GameParameter(){
		
	}
	
	/**�����R�X�g�ƃS�[�X�g�R�X�g�̔�*/
		public double costRatio;
	
	/**A*��A*2�̊*/
		public int distBorder;
	
	/**�X�e�[�W�ԍ�*/
		public int stage;
	
	/**�X�L�����J�n���W*/
		public int left, top;
		
	/**�Q�[���̍L��*/
		public int width, gameWidth, height, gameHeight;
	
	/**�}�b�v�̍L��*/
		public int mapWidth,mapHeight;
		
	/**���A���G���A�͈̔�*/
		public int REAL[];
		
	/**�}�b�v�F���*/
		public int pix[];
		
	/**�I�u�W�F�N�g�i���o�[*/
		
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
	/**�I�u�W�F�N�g�F*/
		public int 
		blue, red, yellow, purple, green, brown, white, pink,black,orange, 
		pillColor[],pacColor; 
	
	/**�I�u�W�F�N�g���W*/
		public Vector2d 
			pac,ghost[],ghost2[],nearest[],
			corner[],corner2[],warp[];
	
	/**�Q�[���}�b�v*/
		public Node node[][];
		
	/**�I�u�W�F�N�g����*/
		public boolean find;
		
	/**�p���[�s���̐�*/
		public int pp_num=0; 
	
	/**�}�b�v�󋵔c��*/
		public boolean scan[];
		
	/**�R�[�i�[�E���[�v�n�_����*/
		public boolean findCW;
	
	/**���R�[�i�[�n�_����*/
		public boolean findNextP;
		
	/**�G�f�B�u������*/
		public boolean findE;
		
	/**�ړI�n*/
		public Vector2d goal;
	
	/**�R�X�g���l*/
		public int cost[]=new int[num_num];
	
	/**�p�b�N�}���̌Œ�*/
		public boolean pacLock;
	
	/**�R�[�i�[�n�_���m�̃����N*/
		//public NodeLink[] link;
		
	/**���{�ǉ�*/
		public int gpd=100,gpd2=100,gpdk=100,gcount=4,g4pd=0,g4px=0,
					turn_count=0,kp=0,ke=0,pill_count,stop=0,stopk=0;
		public boolean go_pp=false;
	
	/**�w�K�p�������*/
		public Vector2d study;
		
	/**�X���b�h��������O���[�v*/
		int thread_num=2;
		public ThreadGroup[] thread;
	
	/**�F�l�ݒ�*/
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
		//itemColor[2]�͗ь�̂ւ�,itemColor[3]�͗ь�̔����Ƃ�
	}
		
	/**���Z�b�g*/
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
	 * �����ݒ�
	 * @param left �X�L�����J�nx���W
	 * @param top�@�X�L�����J�ny���W
	 * @param width�@�Q�[���̍L��(��)
	 * @param height�@�Q�[���̍L��(�c)
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
	
	/**���A���G���A����*/
	public boolean realArea(int x){
		return x>=REAL[0] && x<REAL[1];
	}
	/**���A���G���A����*/
	public boolean realArea(int x,int num){
		if(num==1) return x>=REAL[0];
		else return x<REAL[1];
	}
	
	/**�I�u�W�F�N�g����*/
	public void find(boolean bool){find=bool;}
	
	/**�X�L�������s*/
	public void scan(int num){
		//System.out.println(num+" scaned");
		scan[num]=false;
	}
	
	int p_x=-1,p_y=-1,p_now;
	/**�R�[�i�[�E���[�v�n�_�X�L�����X�g�b�v*/
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
	
	/**�R�[�i�[�E���[�v�n�_����*/
	public void findCW(){findCW=true;}
	
	/**���R�[�i�[�n�_����*/
	public void findNextP(){findNextP=true;};
	
	/**�G�f�B�u������*/
	public void findE(boolean bool){findE=bool;}
	
	/**�p�b�N�}���̌Œ�*/
	public void pacLock(boolean bool){pacLock=bool;}
}
