package main;

import java.util.Random;
import java.lang.Math;
import java.io.*;

/**
 * (3+3)ES学習用
 * @author takashi ashida
 *
 */
public class ES {
	
	/**総世代数*/
	public int generation = 15;
	/**プレイ回数*/
	public int numPlay = 10;
	/**乱数生成用*/
	Random rand = new Random();
	/**世代(0~2が親、3~5が子)*/
	Solution sol[] = new Solution[6];
	/**各世代の個体数*/
	int numSol = 6;
	/**分散の刻み幅*/
	Solution stepSize = new Solution();
	/**分散*/
	Solution variance = new Solution();
	/**ファイル書きこみ用*/
	FileWriter fw;
	BufferedWriter bw;
	PrintWriter pw;
	int gen_number=2;
	
	/**初期化コンストラクタ*/
	public ES(){
		for(int i=0;i<numSol;i++){
			sol[i] = new Solution();
		}
	}
	
	/**最初の親世代を生成する。*/
	public void setParent(){
		
		sol[0].costRatio = 50;
		sol[0].border = 8;
		sol[0].score = 0;
		
		sol[1].costRatio = 62.26;
		sol[1].border = 12;
		sol[1].score = 0;
		
		sol[2].costRatio = 44.44;
		sol[2].border = 8;
		sol[2].score = 0;
		
		for(int i=0;i<3;i++){
			System.out.println(sol[i].costRatio+" "+sol[i].border);
		}
		
		variance.costRatio = 10;
		variance.border = 4;
		
	}
	
	/**親世代から子世代を生成する*/
	public void setOffspring(){
		
		variance.costRatio = variance.costRatio * Math.exp(stepSize.costRatio * rand.nextGaussian());
		variance.border = variance.border * (int)Math.exp(stepSize.border * rand.nextGaussian());
		
		for(int i=3;i<numSol;i++){
			sol[i].costRatio = sol[0].costRatio + variance.costRatio * rand.nextGaussian();
			if(sol[i].costRatio < 1 || sol[i].costRatio > 100){
				i--;
				continue;
			}
				
			sol[i].border = sol[0].border + (int)((double)variance.border * rand.nextGaussian());
			if(sol[i].border < 5 || sol[i].border > 20){
				i--;
				continue;
			}
			
			sol[i].score = 0;
			System.out.println(sol[i].costRatio+" "+sol[i].border);
		}
		
	}
	
	
	/**実行結果より今世代をスコアの高い順に並び変える。（親世代の部分に高いものが集まればいい）*/
	void resetParents(){
		Solution tmp;
		
		if(gen_number>20)
			this.outputOC(false);
		
		for(int i=0;i<numSol-3;i++){
			for(int j=numSol-1;j>i;j--){
				if(sol[j].score>sol[j-1].score){
					tmp=sol[j];
					sol[j]=sol[j-1];
					sol[j-1]=tmp;
				}
			}
		}
		
		for(int i=0;i<3;i++){
			sol[i].score = 0;
			System.out.println(sol[i].costRatio+" "+sol[i].border);
		}
		
		this.outputNewGen();
		++this.gen_number;
	}
	
	/**ファイルオープン・クローズ*/
	void outputOC(boolean flag){
		if(flag){
			try {
				fw = new FileWriter("ES_Generation.txt",true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		
		if(!flag){
			try {
				pw.close();
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}
	
	/**ある世代のパラメータ書きこみ*/
	void outputNewGen(){
		this.outputOC(true);
		pw.print("Generation "+gen_number + ":\n");
		pw.print(" Individual 1:"+"\n");
		pw.print("              "+"CostRatio:"+this.sol[0].costRatio+"\n");
		pw.print("              "+"Border:"+this.sol[0].border+"\n");
		pw.print("              "+"AverageScore:"+this.sol[0].score+"\n\n");
		pw.print(" Individual 2:\n");
		pw.print("              "+"CostRatio:"+this.sol[1].costRatio+"\n");
		pw.print("              "+"Border:"+this.sol[1].border+"\n");
		pw.print("              "+"AverageScore:"+this.sol[1].score+"\n\n");
		pw.print(" Individual 3:\n");
		pw.print("              "+"CostRatio:"+this.sol[2].costRatio+"\n");
		pw.print("              "+"Border:"+this.sol[2].border+"\n");
		pw.print("              "+"AverageScore:"+this.sol[2].score+"\n\n\n\n\n\n");
		this.outputOC(false);
	}
	/*
	public static void main(String args[]) throws Exception{
		
		ES es = new ES();
		
		es.setParent();
		
		for(int i=0;i<es.generation;i++ ){
		
			es.setOffspring();
		
			for(int j=0;j<es.numPlay;j++){
				for(int k =0; k<es.numSol;k++){
					//ここでプログラムを実行して平均スコアを取得する。
				}
			}
			
			es.resetParents();
			
			//System.out.println(es.sol[0].score + " " + es.sol[1].score + " " + es.sol[2].score);
		}
	}*/
	
}