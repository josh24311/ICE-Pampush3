package parameter;

/**
 * 基本パラメータ
 * @author ChotaTokuyama
 * @version 2.2
 * @since Jan_21_2009
 */

public class Basic {
	/**スキャン開始位置*/
		public static int LEFT,TOP;
		
	/**ゲーム画面の広さ*/
		public static int WIDTH,HEIGHT;
		
	/**マップの広さ*/
		public static int MapW,MapH,GameW,GameH;
		
	/**リアルエリア*/
		public static int REAL[]=new int[2];
		
	/**オブジェクト番号*/
		public static int
			WALL=0,BACK=1,PILL[]={2,3,4,5},PP=6,
			GHOST[]={7,8,9,10},GHOST2[]={11,12,13,14},EDIBLE[]={15,16},
			PACMAN=17,
			ITEM=18,
			ObjNUM=19,
			CORNER=19,CORNER2=20,
			WARP=21,WarpSIDE=22,WarpSIDE2=23,
			NearG=24,
			GBase=25,
			GaP=26,
			AllNUM=27;
		
	/**色情報*/
		public static int COLOR[];
		
	/**
	 * ゲーム画面　位置・広さ設定
	 * @param left スキャン開始位置(x)
	 * @param top スキャン開始位置(Y)
	 * @param widht ゲーム画面の広さ(横)
	 * @param height ゲーム画面の広さ(縦)
	 */
	public static void setScanArea(int left,int top,int width,int height){
		LEFT=left; TOP=top; WIDTH=width; HEIGHT=height;
		GameW=WIDTH/8; GameH=HEIGHT/8; MapW=GameW*2; MapH=GameH;
		REAL[0]=GameW/2+GameW%2; REAL[1]=GameW+REAL[0];
	}
	
	public static void setObjColor(
			int pacman,int blinky,int pinky,int inky,int sue,int edible,
			int pill,int pill2,int pill3,int pill4,int back){
		
	}
}