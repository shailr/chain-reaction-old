package generic;

import java.io.*;

class Game {
	
	private int grid_rowsize;
	private int grid_colsize;
	private Grid grid;
	private int no_players;
	private Player players[];
	
	public Game(){
		
		no_players = 3;
		players[0] = new Player(0,0);
		players[1] = new Player(1,0);
		grid = new Grid(no_players);
	}
	
	public Game(int human_players){
		no_players = human_players;
		
		for(int i=0; i<human_players; i++){
			players[i] = new Player(i,0);
		}
		grid = new Grid(no_players);
	}

	public Game(int human_players, int ai_players){
		no_players = human_players + ai_players;
		
		for(int i=0; i<human_players; i++){
			players[i] = new Player(i,0);
		}
		
		for (int i=human_players; i<no_players; i++){
			players[i] = new Player(i,1);
		}
		grid = new Grid(no_players);
	}
	
	public Game(int human_players, int rows, int cols){
		no_players = human_players;
		grid_rowsize = rows;
		grid_colsize = cols;
		players = new Player[human_players];
		
		for(int i=0; i<human_players; i++){
			players[i] = new Player(i,0);
		}
		grid = new Grid(no_players, grid_rowsize, grid_colsize);
	}

	public Game(int human_players, int ai_players, int rows, int cols){
		no_players = human_players + ai_players;
		grid_rowsize = rows;
		grid_colsize = cols;
		players = new Player[no_players];
		int i = 0;
		for(i=0; i<human_players; i++){
			players[i] = new Player(i,0);
		}
		
		for (i=human_players; i<no_players; i++){
			players[i] = new Player(i,1);
		}
		grid = new Grid(no_players, grid_rowsize, grid_colsize);
	}

	public Player Play(){
				
		while(!grid.isWinner()){
			int prev_col = grid.getColor();
			System.out.println("Color: " + Grid.colorcode[prev_col]);
			grid.move( players[prev_col].move(grid) );	
			grid.printGrid();
		}
		return players[grid.getColor()];
	}
	
	public static void main (String args[]) throws Exception{
		Game game;
		int humans = 0;
		int no_rows = 3;
		int no_cols = 3;
		Player winner;
		BufferedReader i = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("1 AI player.");
		System.out.println("Enter the number of Human Players: ");
		
		humans = Integer.parseInt(i.readLine());
		
		game = new Game(humans, 1, no_rows, no_cols);
		winner = game.Play();
		
		System.out.println("The winner's color is: " + Grid.colorcode[winner.getColor()]);
		
	}
}
