package main;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import games.math.Vector2d;

import java.awt.Robot;

/**
 * オブジェクト発見
 * @author　ChotaTokuyama
 * @version 2.2
 * @since　Dec_03_2008_Tue
 */

public class FindObject {
	private GameParameter gp;
	
	/**スキャン開始誤差修正 x座標*/ public int first_x;
	/**スキャン開始誤差修正 y座標*/ public int first_y;
	/**パックマン発見*/ private boolean p_saw;
	/**ピルの数*/ private int p_num;
	/**エディブルの発見*/ private boolean e_saw;
	/**パワーピルの発見*/ public boolean pp;
	/**ステージ変更パラメータ*/ private int nextStage=0;
	/**ゴースト発見*/ boolean ghost[]=new boolean[4];
	/**ゴースト固定*/ boolean g_lock[]=new boolean[4];
	/**小笹追加*/ boolean mapcheck=false;
	
	/**@param gp ゲームパラメータ*/
	public FindObject(GameParameter gp){
		this.gp=gp;
		p_saw=false;
	}
	
	/**状況のリセット*/
	private void reset(){
		p_num=0;
		p_saw=false;
		e_saw=false;
		for(int i=0;i<4;i++){
			ghost[i]=false;
		}
	}
	
	/**マップ状況のリセット*/
	private void mapReset(){
		p_saw=false;
		pp=false;
		first_x=(first_x+1)%8; first_y=(first_y+2)%8;
		nextStage++;
		if(nextStage>8){gp.stage=(gp.stage+1)%3;nextStage=0;MsPacInterface.dij.stage=gp.stage;mapcheck=false;MsPacInterface.dij.flag=false;}
		gp.reset();
	}
	
	/**実行*/
	public int run(){
		int can_point = 0;
		int score =0 ;
		
		if(MsPacInterface.study){
			if(gp.study!=null && gp.pac!=null){
				if((int)gp.pac.x==(int)gp.study.x && (int)gp.pac.y==(int)gp.study.y)
					gp.study=null;
			}
		}
		reset();
		for(int i=0;i<gp.height-gp.height%8;i+=8){
			for(int j=0;j<gp.width-gp.width%8;j+=8){
				pixCount(j,i);
				set(j,i,judgeObject());
				if(gp.node[i/8][j/8].can_point) can_point++;
			}
		}
		gp.findE(e_saw);
		for(int i=0;i<4;i++) if(!ghost[i] && !g_lock[i]){
			gp.ghost[i]=null; gp.ghost2[i]=null;
		}
	//===========================================================
	//丸山追加
		if(GO1==true && GO2==true && GO3==true && GO4==true
				&& GO5==true && GO6==true && GO7==true && GO8==true && time_count>=200){
			score = ScreenShot(Character_count);
			time_count=0;
			Character_count++;
		}
		/*
		if(time_count==10){
			key_Press(1);
		}
		else if(time_count==100){
			key_Release(1);
		}*/
		
		if(time_count<200){
			time_count++;
		}
		GO1=false;GO2=false;GO3=false;GO4=false;GO5=false;GO6=false;GO7=false;GO8=false;
	//============================================================
		if(can_point>gp.gameHeight*gp.gameWidth/2) mapReset();
		if(p_num==0)
			if(gp.pac!=null)
				judgeReset();
			else mapReset();
		return score;
	}
	
	/**リセット判定*/
	private void judgeReset(){
		boolean reset=true;
		int x=0,y=0;
		for(int i=0;i<gp.mapHeight;i++){ for(int j=gp.REAL[0];j<gp.REAL[1];j++){
			if(gp.node[i][j].pill){
				for(int k=0;k<9;k++){
					switch(k){
					case 0: x= 0; y=0;
					case 1: x= 0; y=-1; break; case 2: x= 1; y= 0; break;
					case 3: x= 0; y= 1; break; case 4: x=-1; y= 0; break;
					case 5: x= 1; y=-1; break; case 6: x= 1; y= 1; break;
					case 7: x=-1; y= 1; break; case 8: x=-1; y=-1; break;
					}
					if(i+y>=0 && i+y<gp.mapHeight){
						if(gp.node[i+y][j+x].edible || gp.node[i+y][j+x].item ||
								(i+y==(int)gp.pac.y && j+x==(int)gp.pac.x)){
							reset=false; break;
						}
						for(int l=0;l<4;l++)
							if(gp.node[i+y][j+x].ghost[l]){
								//System.out.println("yes");
								reset=false; break;
							}
					}if(!reset)break;
				}
			}if(!reset) break;
		}if(!reset) break;
		}if(reset){
			mapReset();
		}
	}
	
	int now_x=0,now_y=0;
	boolean yes
	,GO1=false,GO2=false,GO3=false,GO4=false,GO5=false,GO6=false,GO7=false,GO8=false;
	int px=-1,py=-1,pn=0,Character_count=0;
	/**オブジェクトの設置*/
	private void set(int x,int y,int num){
		now_x=x/8;now_y=y/8; yes=false;
		if(num==gp.num_back){
			if(!gp.node[now_y][shuusei(now_x,1)].pp) yes=true;
		}
		else if(num==gp.num_pill){
			yes=true;
			}
		else if(num==gp.num_pp && gp.find){
			yes=true; pp=true;
		}
		//============================================================
		//丸山追加
		else if(num==gp.num_gameover){
			if(GO1==false){
				GO1=true;
			}
			else if(GO2==false){
				GO2=true;
			}
			else if(GO3==false){
				GO3=true;
			}
			else if(GO4==false){
				GO4=true;
			}
			else if(GO5==false){
				GO5=true;
			}
			else if(GO6==false){
				GO6=true;
			}
			else if(GO7==false){
				GO7=true;
			}
			else if(GO8==false){
				GO8=true;
			}
		}
		//=========================================================
		else if(num==gp.num_pacman && gp.node[now_y][0].game_area &&
					gp.node[now_y][shuusei(now_x,1)].can_point && !p_saw){
			yes=true;
			p_saw=true;
			if(gp.pac==null){
				gp.pac=new Vector2d();
			}
			if(!gp.pacLock) gp.pac.set(shuusei(now_x,1),now_y);
			if(gp.node[now_y][shuusei(now_x,1)].warpSide){
				if(!gp.pacLock){
					if(now_x<gp.gameWidth/2)
						gp.pac.set(shuusei(now_x,1)+25,now_y);
					else gp.pac.set(shuusei(now_x,1)-25,now_y);
				}
				gp.pacLock(true);
			}
			else if(!gp.node[now_y][shuusei(now_x,1)].warpSide2 && 
					!gp.node[now_y][shuusei(now_x,1)].warp){
				gp.pacLock(false);
			}
		}
		else if(num==gp.num_edible){
			yes=true; 
			if(!e_saw){
				if(gp.pac!=null){
					if(gp.nearest[gp.num_pp]!=null){
						if(gp.realArea((int)gp.nearest[gp.num_pp].x) &&
								//小笹修正
								//gp.pac.dist2(gp.nearest[gp.num_pp])<4){
								gp.pac.dist4(gp.nearest[gp.num_pp])<4){
							gp.node[(int)gp.nearest[gp.num_pp].y]
							        [(int)gp.nearest[gp.num_pp].x].set(gp.num_back);
							gp.node[(int)gp.nearest[gp.num_pp].y]
							        [((int)gp.nearest[gp.num_pp].x+gp.gameWidth)%gp.mapWidth]
							         .set(gp.num_back);
						}
					}
				}
			}
			e_saw=true;
		}
		else if(num==gp.num_item && gameArea(now_y)){
			if(now_y+1<gp.gameHeight){
				if(gp.node[now_y][shuusei(now_x,1)].can_point){
					yes=true;
				}
				if(gp.node[now_y+1][shuusei(now_x,1)].can_point){
					gp.node[now_y+1][shuusei(now_x,1)].set(num);
					gp.node[now_y+1][shuusei(now_x,2)].set(num);
				}
			}
		}
		else if(num==gp.num_wall){
			gp.node[now_y][shuusei(now_x,1)].reset();
			gp.node[now_y][shuusei(now_x,2)].reset();
		}
		else for(int i=0;i<4;i++)
			if(num==gp.num_ghost[i] && gameArea(now_y) &&
					gp.node[now_y][shuusei(now_x,1)].can_point &&
					!gp.node[now_y][shuusei(now_x,1)].item){
				yes=true;
				if(gp.node[now_y][shuusei(now_x,1)].warpSide) g_lock[i]=true;
				else g_lock[i]=false;
				ghost[i]=true;
				if(gp.ghost[i]==null)
					gp.ghost[i]=new Vector2d();
				if(gp.ghost2[i]==null)
					gp.ghost2[i]=new Vector2d();
				if(gp.pac!=null){
					if((int)gp.pac.x!=shuusei(now_x,1) || (int)gp.pac.y!=now_y){
						gp.ghost[i].set(shuusei(now_x,1), now_y);
						gp.ghost2[i].set(shuusei(now_x,2), now_y);
					}
				}
				else{
					gp.ghost[i].set(shuusei(now_x,1), now_y);
					gp.ghost2[i].set(shuusei(now_x,2), now_y);
				}
			}
		if(yes){
			gp.node[now_y][shuusei(now_x,1)].set(num);
			gp.node[now_y][shuusei(now_x,2)].set(num);
		}
		
	}
	
	/**ゲームエリア*/
	private boolean gameArea(int y){
		return gp.node[y][0].game_area;
	}
	
	/**x座標修正*/
	private int shuusei(int x,int num){
		if(num==1) return (x+gp.REAL[0]);
		else{
			if(x<gp.gameWidth/2) return x+gp.REAL[1];
			else return x-gp.gameWidth/2;
		}
	}
	
	private int
		blue=0,red=0,yellow=0,orange=0,purple=0, 
		green=0,brown=0,white=0,pink=0,black=0,
		p_x[]=new int[4],p_y[]=new int[4],	time_count=200;
	//time_count,apple_b,apple_w追加
	private int now=0;
	
	/**ピクセルごとの色数カウント*/
	private void pixCount(int x,int y){
		int pill=0;
		p_x=new int[4];
		p_y=new int[4];
		blue=0;red=0;yellow=0;orange=0;purple=0; 
		green=0;brown=0;white=0;pink=0;black=0;
		
		for(int i=0;i<8;i++) for(int j=0;j<8;j++){
			//=============================================
			//2009/7/20丸山追加
			now=x+j+(y+i)*gp.width;
			if(gp.pix[now]==gp.white)white++;
			if(gp.pix[now]==gp.black)black++;
			if(gp.pix[now]==gp.red)red++;
			if(gp.pix[now]==gp.blue)blue++;
			if(gp.pix[now]==gp.yellow)yellow++;
			if(gp.pix[now]==gp.green)green++;
			if(gp.pix[now]==gp.pink)pink++;
			if(gp.pix[now]==gp.brown)brown++;
			if(gp.pix[now]==gp.purple)purple++;
			if(gp.pix[now]==gp.orange)orange++;
			//==============================================
			if(gp.pix[now]==gp.pacColor || gp.pix[now]==gp.pillColor[gp.stage]){
				if(gp.pix[now]==gp.pillColor[gp.stage]){
					if(pill<4){
						p_x[pill]=j; p_y[pill]=i;
					}
					pill++;
				}
			}
			
		}
	}
	
	/**オブジェクト判定*/
	private int judgeObject(){
		//================================================================
		//2009/7/20丸山追加
		if(black==64)
			return gp.num_back;
				switch (gp.stage){
			case 0:
				if(white==4 && black==60){
					if(judgePill(p_x,p_y)) return gp.num_pill;
				}
				else if(white==52 && black==12){return gp.num_pp;}
				break;
			case 1:
				if(yellow==4 && black==60){
					if(judgePill(p_x,p_y)) return gp.num_pill;
				}
				else if(yellow==52 && black==12){return gp.num_pp;}
				break;
			case 2:
				if(red==4 && black==60){
					if(judgePill(p_x,p_y)) return gp.num_pill;
				}
				else if(red==52 && black==12){return gp.num_pp;}
				break;
			case 3:
				if(white==4 && black==60){
					if(judgePill(p_x,p_y)) return gp.num_pill;
				}
				else if(white==52 && black==12){return gp.num_pp;}
				break;
		}
		if(yellow>30){return gp.num_pacman;}
		else if(purple>35){return gp.num_edible;}
		else if(green>1){return gp.num_item;}//蜜柑や洋ナシ用
		else if(brown>1 && brown<8 ){return gp.num_item;}//サクランボ用(要調整)
		else if((red >1 && red >20) && (brown>1 && brown<7) && white<5 ){return gp.num_item;}
		if(red>34){
			return gp.num_ghost[0];
		}
		else if(red >25){
			return gp.num_gameover;
		}
		if(pink>34){
			return gp.num_ghost[1];
		}
		if(blue>34){
			return gp.num_ghost[2];
		}
		if(orange>34){
			return gp.num_ghost[3];
		}
		//================================================================
		return -1;
	}
	
	/**ピルの発見条件*/
	private boolean judgePill(int[] p_x,int[] p_y){
		if(
			p_x[0]>1 && p_x[0]<6 && p_y[0]>1 && p_y[0]<6 &&
			p_x[1]==p_x[0]+1 && p_x[2]==p_x[0] && p_x[3]==p_x[1] &&
			p_y[1]==p_y[0] && p_y[2]==p_y[0]+1 && p_y[3]==p_y[2]){
			p_num++;
			if(gp.find && (p_x[0]!=3 || p_y[0]!=3)){
				first_x=0;first_y=0;
				mapReset();
			}
			else if(p_x[0]==3 && p_y[0]==3){ gp.find(true); }
			if(p_num==1){
				first_x=first_x-3+p_x[0]; first_y=first_y-3+p_y[0];
			}
			return true;
		}
		else return false;
	}
	
	//=======================================================================
	
	/**スクリーンショット用*///0808芦田改変スコア取得
	private int ScreenShot(int x)
	{
		
		int white = -2171170;
		int score = 0;
		int scoreCount[][] = new int[5][3];
		
		int scoreWidth = 40;
		int scoreHeight = 7;
		int scoreCheck[] = new int[scoreWidth*scoreHeight];
		Robot robot;
		String s;
		s="test"+x+".jpeg";
		
		try 
		{
			
			robot = new Robot();
		
		} 
		catch (AWTException ex) 
		{
		
			ex.printStackTrace();
			return 0;
		}
		
		// 範囲を指定してキャプチャ
		Rectangle bounds = new Rectangle(gp.left+11, gp.top+4, scoreWidth, scoreHeight);
		BufferedImage image = robot.createScreenCapture(bounds);
		
		image.getRGB(0, 0, scoreWidth, scoreHeight, scoreCheck, 0, 40);
		
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<3;j++)
			{
				scoreCount[i][j] = 0;
			}
		}
		
		/*スコア数値化用のチェック----------------------------*/
		for(int j=0;j<3;j++)
		{
			for(int i=0;i<scoreWidth;i++)
			{
				if(i<9)
				{
					if(scoreCheck[j*scoreWidth+i] == white)
					{
						scoreCount[0][j]++;
					}
				}
				else if(i<17)
				{
					if(scoreCheck[j*scoreWidth+i] == white)
					{
						scoreCount[1][j]++;
					}
				}
				else if(i<25)
				{
					if(scoreCheck[j*scoreWidth+i] == white)
					{
						scoreCount[2][j]++;
					}
				}
				else if(i<33)
				{
					if(scoreCheck[j*scoreWidth+i] == white)
					{
						scoreCount[3][j]++;
					}
				}
				else
				{
					if(scoreCheck[j*scoreWidth+i] == white)
					{
						scoreCount[4][j]++;
					}
				}
			}
		}
		/*------------------------------------------------*/
		
		/*スコアの数値化----------------------------------------*/
		for(int i=0;i<5;i++)
		{
			score += setnumber(scoreCount[i]) * Math.pow(10, 4-i);
		}
		//System.out.println(score);
		/*----------------------------------------------------*/
		
		/*スコア画像の外部出力--------------------------------
		 try {
		
		 ImageIO.write(image , "jpeg" , new File(s));
	
		 } catch (IOException e) {
			
		 // TODO 自動生成された catch ブロック
		 e.printStackTrace();
		 }
		 --------------------------------------------------*/
		return score;
	}
	
	/**スコア用数値化*/
	private int setnumber(int scoreCount[])
	{
		
		int number = 0;
		
		if(scoreCount[0] == 3 && scoreCount[1] == 3 && scoreCount[2] == 4)
		{
			number =0;
		}
		else if(scoreCount[0] == 2 && scoreCount[1] == 3 && scoreCount[2] == 2)
		{
			number =1;
		}
		else if(scoreCount[0] == 5 && scoreCount[1] == 4 && scoreCount[2] == 3)
		{
			number =2;
		}
		else if(scoreCount[0] == 6 && scoreCount[1] == 2 && scoreCount[2] == 2)
		{
			number =3;
		}
		else if(scoreCount[0] == 3 && scoreCount[1] == 4 && scoreCount[2] == 4)
		{
			number =4;
		}
		else if(scoreCount[0] == 6 && scoreCount[1] == 2 && scoreCount[2] == 6)
		{
			number =5;
		}
		else if(scoreCount[0] == 4 && scoreCount[1] == 2 && scoreCount[2] == 2)
		{
			number =6;
		}
		else if(scoreCount[0] == 7 && scoreCount[1] == 4 && scoreCount[2] == 2)
		{
			number =7;
		}
		else if(scoreCount[0] == 4 && scoreCount[1] == 3 && scoreCount[2] == 4)
		{
			number =8;
		}
		else if(scoreCount[0] == 5 && scoreCount[1] == 4 && scoreCount[2] == 4)
		{
			number =9;
		}
		
		return number;
	}
	
	
	//自動1押し用
	public void key_Press(int button)
	{
		Robot robot = null;
		
		if(button == 1)
		{
			try 
			{
				
				// Robotクラスのインスタンスを作成
				robot = new Robot();
				
				// 「1」を押す
				robot.keyPress(KeyEvent.VK_1);
				
			} 
			catch (AWTException e) 
			{
				
				// プラットフォーム構成が低レベル入力制御を 許可しない場合の処理
				e.printStackTrace();
			}
		}
		else if(button == 5)
		{
			try 
			{
				
				// Robotクラスのインスタンスを作成
				robot = new Robot();
				
				// 「1」を押す
				robot.keyPress(KeyEvent.VK_5);
				
			} 
			catch (AWTException e) 
			{
				
				// プラットフォーム構成が低レベル入力制御を 許可しない場合の処理
				e.printStackTrace();
			}
		}
	}
	
	public void key_Release(int button)
	{
		Robot robot = null;
		
		if(button == 1)
		{
			try 
			{
				
				// Robotクラスのインスタンスを作成
				robot = new Robot();
				
				// 「2」を押す
				robot.keyRelease(KeyEvent.VK_1);
				
			} 
			catch (AWTException e) 
			{
				
				// プラットフォーム構成が低レベル入力制御を 許可しない場合の処理
				e.printStackTrace();
				
			}
		}
		else if(button == 5)
		{
			try 
			{
				
				// Robotクラスのインスタンスを作成
				robot = new Robot();
				
				// 「2」を押す
				robot.keyRelease(KeyEvent.VK_5);
				
			} 
			catch (AWTException e) 
			{
				
				// プラットフォーム構成が低レベル入力制御を 許可しない場合の処理
				e.printStackTrace();
				
			}
		}
	}
}