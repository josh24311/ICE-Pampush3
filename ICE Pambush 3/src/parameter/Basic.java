package parameter;

/**
 * ��{�p�����[�^
 * @author ChotaTokuyama
 * @version 2.2
 * @since Jan_21_2009
 */

public class Basic {
	/**�X�L�����J�n�ʒu*/
		public static int LEFT,TOP;
		
	/**�Q�[����ʂ̍L��*/
		public static int WIDTH,HEIGHT;
		
	/**�}�b�v�̍L��*/
		public static int MapW,MapH,GameW,GameH;
		
	/**���A���G���A*/
		public static int REAL[]=new int[2];
		
	/**�I�u�W�F�N�g�ԍ�*/
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
		
	/**�F���*/
		public static int COLOR[];
		
	/**
	 * �Q�[����ʁ@�ʒu�E�L���ݒ�
	 * @param left �X�L�����J�n�ʒu(x)
	 * @param top �X�L�����J�n�ʒu(Y)
	 * @param widht �Q�[����ʂ̍L��(��)
	 * @param height �Q�[����ʂ̍L��(�c)
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