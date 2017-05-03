package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

/*
 * 小笹
 * ダイクストラ法による最短距離を扱うクラス
 */
public class Dijkstra {
	/** 小笹
	 * ダイクストラ法・最短距離を保持する変数
	 */
		public int[][][][] dij;
		public int stage=0;
		public int x, y;
		public boolean flag=false;
		int gameWidth=30;
		int gameHeight=34;
		
		public void update(){
			if(this.stage==0 && !flag){
				this.updateStage1();
				this.flag=true;
			}
			
			if(this.stage==1 && !flag){
				this.updateStage2();
				this.flag=true;
			}
			
			if(this.stage==2 && !flag){
				this.updateStage3();
				this.flag=true;
			}
		}
		
		public void updateStage1(){
			dij = new int[this.gameWidth][this.gameHeight][this.gameWidth][this.gameHeight];
			int i=0, j,k,l,m;
			int x=1, y=1, sx=0, sy=0, ex=0, ey=0;
			for(j=0;j<this.gameWidth;j++)
				for(k=0;k<this.gameHeight;k++)
					for(l=0;l<this.gameWidth;l++)
						for(m=0;m<this.gameHeight;m++)
							dij[j][k][l][m] = 10000;
			
			FileInputStream fi = null;
			try {
				fi = new FileInputStream("data/NodeDijkstraStage1.txt");
			} catch (FileNotFoundException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			InputStreamReader ir = null;
			try {
				ir = new InputStreamReader(fi, "MS932");
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(ir);
			String str[] = new String[this.gameWidth * this.gameHeight * this.gameWidth * this.gameHeight];
			
			search:
			for(int n=0;n<MsPacInterface.gp.mapHeight;n++){
				for(int o=0;o<MsPacInterface.gp.mapWidth;o++){
					//System.out.println("x:"+n+" y:"+o+" bool:"+FindArea.gp.node[n][o].game_area);
					if(FindArea.gp.node[n][o].game_area){
						this.x=o;
						this.y=n;
						x=this.x;
						y=this.y;
						break search;
					}
				}
			}
			
			//System.out.println("x:" + x + " y:" + y);
			
			
			try {
				while (br.ready()) { 
					String line = null;
					try {
						line = br.readLine();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} 
					str[i] = line;
					StringTokenizer st = new StringTokenizer(str[i], ",");
					while (st.hasMoreTokens()) {
						sx = Integer.parseInt(st.nextToken()) + x;
						sy = Integer.parseInt(st.nextToken()) + y;
						ex = Integer.parseInt(st.nextToken()) + x;
						ey = Integer.parseInt(st.nextToken()) + y;
						if(sx>-1 && sy>-1 && ex>-1 && ey>-1)
							dij[sx][sy][ex][ey] = Integer.parseInt(st.nextToken());
						i++;
					}
				}
			} catch (NumberFormatException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			try {
				br.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				ir.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				fi.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//System.out.println("---Dijkstra Stage1 Load OK---");
		}
		
		void updateStage2(){
			dij = new int[this.gameWidth][this.gameHeight][this.gameWidth][this.gameHeight];
			int i=0, j,k,l,m;
			int x=1, y=1, sx, sy, ex, ey;
			for(j=0;j<this.gameWidth;j++)
				for(k=0;k<this.gameHeight;k++)
					for(l=0;l<this.gameWidth;l++)
						for(m=0;m<this.gameHeight;m++)
							dij[j][k][l][m] = 10000;
			
			FileInputStream fi = null;
			try {
				fi = new FileInputStream("data/NodeDijkstraStage2.txt");
			} catch (FileNotFoundException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			InputStreamReader ir = null;
			try {
				ir = new InputStreamReader(fi, "MS932");
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(ir);
			String str[] = new String[this.gameWidth * this.gameHeight * this.gameWidth * this.gameHeight];
			
			search:
				for(int n=0;n<MsPacInterface.gp.mapHeight;n++){
					for(int o=0;o<MsPacInterface.gp.mapWidth;o++){
						if(FindArea.gp.node[n][o].game_area){
							this.x=o;
							this.y=n;
							x=this.x;
							y=this.y;
							break search;
						}
					}
				}
			
			//System.out.println("x:" + x + " y:" + y);
			
			try {
				while (br.ready()) { 
					String line = null;
					try {
						line = br.readLine();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} 
					str[i] = line;
					StringTokenizer st = new StringTokenizer(str[i], ",");
					while (st.hasMoreTokens()) {
						sx = x + Integer.parseInt(st.nextToken());
						sy = y + Integer.parseInt(st.nextToken());
						ex = x + Integer.parseInt(st.nextToken());
						ey = y + Integer.parseInt(st.nextToken());
						dij[sx][sy][ex][ey] = Integer.parseInt(st.nextToken());
						i++;
					}
				}
			} catch (NumberFormatException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			try {
				br.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				ir.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				fi.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//System.out.println("---Dijkstra Stage2 Load OK---");
		}

		void updateStage3(){
			dij = new int[this.gameWidth][this.gameHeight][this.gameWidth][this.gameHeight];
			int i=0, j,k,l,m;
			int x=1, y=1, sx, sy, ex, ey;
			for(j=0;j<this.gameWidth;j++)
				for(k=0;k<this.gameHeight;k++)
					for(l=0;l<this.gameWidth;l++)
						for(m=0;m<this.gameHeight;m++)
							dij[j][k][l][m] = 10000;
			
			FileInputStream fi = null;
			try {
				fi = new FileInputStream("data/NodeDijkstraStage3.txt");
			} catch (FileNotFoundException e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}
			InputStreamReader ir = null;
			try {
				ir = new InputStreamReader(fi, "MS932");
			} catch (UnsupportedEncodingException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(ir);
			String str[] = new String[this.gameWidth * this.gameHeight * this.gameWidth * this.gameHeight];
			
			search:
				for(int n=0;n<MsPacInterface.gp.mapHeight;n++){
					for(int o=0;o<MsPacInterface.gp.mapWidth;o++){
						if(FindArea.gp.node[n][o].game_area){
							this.x=o;
							this.y=n;
							x=this.x;
							y=this.y;
							break search;
						}
					}
				}
			
			//System.out.println("x:" + x + " y:" + y);
			
			try {
				while (br.ready()) { 
					String line = null;
					try {
						line = br.readLine();
					} catch (IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} 
					str[i] = line;
					StringTokenizer st = new StringTokenizer(str[i], ",");
					while (st.hasMoreTokens()) {
						sx = x + Integer.parseInt(st.nextToken());
						sy = y + Integer.parseInt(st.nextToken());
						ex = x + Integer.parseInt(st.nextToken());
						ey = y + Integer.parseInt(st.nextToken());
						dij[sx][sy][ex][ey] = Integer.parseInt(st.nextToken());
						i++;
					}
				}
			} catch (NumberFormatException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
			try {
				br.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				ir.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				fi.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//System.out.println("---Dijkstra Stage2 Load OK---");
		}
}
