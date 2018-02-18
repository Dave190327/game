import java.util.ArrayList;

public class World2 {
	ArrayList <Integer> deckP1,deckP2;
	int lineCount=10,playerTurn=0;
	int[][][] grid=new int[lineCount][lineCount][10];
	int[][] hands=new int[2][5];
	int[][] playersInfo=new int[5][2];
	int[] cost={10,20,30,50};
	public void init(){
		playersInfo[0][0]=-1;playersInfo[0][1]=-1;//cardSelected
		playersInfo[1][0]=100;playersInfo[1][1]=100;//matter
		deckP1=new ArrayList<Integer>();
		deckP2=new ArrayList<Integer>();
		int[] x={1,1,1,1,1,1,1,2,3};
		playersInfo[0][0]=-1;playersInfo[0][1]=-1;
		for(int y=0;y<x.length;y++){
			deckP1.add(x[y]);
			deckP2.add(x[y]);
		}
		drawCards();
	}
	public void drawCards(){
		for(int y=0;y<2;y++){
			for(int x=0;x<5;x++){
				hands[y][x]=0;
			}
		}
		for(int x=0;x<5;x++){
			if(deckP1.size()!=0){
				hands[0][x]=deckP1.get(0);
				deckP1.remove(0);
			}
			if(deckP2.size()!=0){
				hands[1][x]=deckP2.get(0);
				deckP2.remove(0);
			}
		}
	}
	public void nextTurn(){
		if(playerTurn==0){
			playerTurn=1;
		}else{
			playerTurn=0;
			for(int x=0;x<lineCount;x++){
				for(int y=0;y<lineCount;y++){
					if(grid[x][y][0]==2){playersInfo[1][0]+=10;}
					if(grid[x][y][1]==2){playersInfo[1][1]+=10;}
				}
			}
			System.out.println(playersInfo[1][0]+" "+playersInfo[1][1]);
			drawCards();
		}
	}
	public void setTile(int x,int y){
		if(grid[x][y][0]==0 & grid[x][y][1]==0){
			if(hands[playerTurn][playersInfo[0][playerTurn]]==1){
				grid[x][y][playerTurn]=1;
				hands[playerTurn][playersInfo[0][playerTurn]]=0;
			}
		}else{
			if(grid[x][y][0]==1|grid[x][y][1]==1){
				if(hands[playerTurn][playersInfo[0][playerTurn]]==2){
					grid[x][y][playerTurn]=2;
					hands[playerTurn][playersInfo[0][playerTurn]]=0;
				}
				if(hands[playerTurn][playersInfo[0][playerTurn]]==3){
					grid[x][y][playerTurn]=3;
					hands[playerTurn][playersInfo[0][playerTurn]]=0;
				}
			}
			if(grid[x][y][0]!=0|grid[x][y][1]!=0){
				if(hands[playerTurn][playersInfo[0][playerTurn]]==4){
					grid[x][y][0]=0;grid[x][y][1]=0;
					hands[playerTurn][playersInfo[0][playerTurn]]=0;
				}
				
			}
		}
	}
}