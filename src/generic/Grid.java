package generic;

class Grid {

	private int grid_rowsize;
	private int grid_colsize;
	private Cell grid[][];
	private int color;
	private int num_players;
	private int move_count;
	private int[] color_count;
	public static String[] colorcode = {"Red","Blue","Green","Yellow","Orange","Pink","Violet","Indigo"}; 
	
	public Grid(){
		num_players = 2;
		move_count = 0;
		grid_rowsize = 10;
		grid_colsize = 10;
		color = 0;
		color_count = new int[num_players];
		for(int i=0;i<num_players;i++){
			color_count[i]=0;
		}
		grid = new Cell[grid_rowsize][grid_colsize];
		for(int j=0;j<grid_rowsize;j++)
		{
			for(int i=0;i<grid_colsize;i++)
			{
				grid[j][i]=new Cell(getLimit(new Vector(j,i)));
			}
		}
	}
	
	public Grid(int numplay){
		num_players = numplay;
		move_count = 0;
		grid_rowsize = 10;
		grid_colsize = 10;
		color = 0;
		color_count = new int[num_players];
		for(int i=0;i<num_players;i++){
			color_count[i]=0;
		}
		grid = new Cell[grid_rowsize][grid_colsize];
		for(int j=0;j<grid_rowsize;j++)
		{
			for(int i=0;i<grid_colsize;i++)
			{
				grid[j][i]=new Cell(getLimit(new Vector(j,i)));
			}
		}
	}
	
	public Grid(int numplay, int rows, int cols){
		num_players = numplay;
		move_count = 0;
		grid_rowsize = rows;
		grid_colsize = cols;
		color = 0;
		color_count = new int[num_players];
		for(int i=0;i<num_players;i++){
			color_count[i]=0;
		}
		grid = new Cell[grid_rowsize][grid_colsize];
		for(int j=0;j<grid_rowsize;j++)
		{
			for(int i=0;i<grid_colsize;i++)
			{
				grid[j][i]=new Cell(getLimit(new Vector(j,i)));
			}
		}
	}
	
	public Grid(int rows, int cols){
		num_players = 2;
		move_count = 0;
		grid_rowsize = rows;
		grid_colsize = cols;
		color = 0;
		color_count = new int[num_players];
		for(int i=0;i<num_players;i++){
			color_count[i]=0;
		}
		grid = new Cell[grid_rowsize][grid_colsize];
		for(int j=0;j<grid_rowsize;j++)
		{
			for(int i=0;i<grid_colsize;i++)
			{
				grid[j][i]=new Cell(getLimit(new Vector(j,i)));
			}
		}
	}
	
	public Grid(Grid g) {
		
		num_players = g.getNumPlayers();
		move_count = g.getMoveCount();
		grid_rowsize = g.getGridRowSize();
		grid_colsize = g.getGridColSize();
		color = g.getColor();
		color_count = new int[num_players];
		for(int i=0;i<num_players;i++){
			color_count[i]=g.getColorCount(i);
		}
		grid = new Cell[grid_rowsize][grid_colsize];
		for(int j=0;j<grid_rowsize;j++)
		{
			for(int i=0;i<grid_colsize;i++)
			{
				grid[j][i]=new Cell(g.getCell(new Vector(j,i)));
			}
		}
	}
	
	public Cell getCell(Vector v){
		return grid[v.x][v.y];
	}

	class AddOrbThread implements Runnable {
		
		Thread th; 
		private Vector pos;
		
		public AddOrbThread(Vector v){
			th = new Thread(this);
			pos = v;
			th.start();
			
			try {
					th.join();
			} 
			catch (InterruptedException e) {
                e.printStackTrace();
			}
		}
		
		public void run(){
			//System.out.println("Inside thread"+th.getId());
			if(validCell(pos)){
				Cell cur_cell = grid[pos.x][pos.y];
				if(cur_cell.getCount() != 0){
					color_count[cur_cell.getColor()]--;
				}
				
				cur_cell.raiseCount(color);
				color_count[color]++;
				if(!grid[pos.x][pos.y].isStable()){
					split(pos);
				}
			}
		}
	}
	
	public int getMoveCount(){
		return move_count;
	}
	
	public int getNumPlayers(){
		return num_players;
	}

	public int setColor(int c){
		color = c;
		return color;
	}
	
	public int getColor(){
		return color;
	}


	public int getActivePlayers(){
		int activeplayers = 0;
		if(move_count > num_players){
			for(int i=0; i < num_players; i++){
				if(color_count[i] > 0)
				{
					activeplayers++;
				}
			}
			return activeplayers;
		}		
		return num_players;
		
	}
	
	public int getGridRowSize(){
		return grid_rowsize;
	}
	
	public int getGridColSize(){
		return grid_colsize;
	}
	
	public boolean validMove(Vector v){		
		//System.out.println("In validMove, cellColor and gridcolor are " + grid[v.x][v.y].getColor() + color);
		if( grid[v.x][v.y].getColor() == color || grid[v.x][v.y].getColor() == -1 )
		{
			return true;
		}			
		return false;
	}
	
	public boolean validCell(Vector v){		
		if( v.x >= 0 && v.x <= grid_rowsize-1 && v.y >= 0 && v.y <= grid_colsize-1 )
		{
			return true;
		}			
		return false;
	}
	
    public int getLimit(Vector v)
    {
    	int limit = 4;
		if (v.x == 0 || v.x == grid_rowsize - 1)
		{
			limit--;
		}
		
		if (v.y == 0 || v.y == grid_colsize - 1)
		{
			limit--;
		}
		return limit;
    }
	
	public int getColorCount (int player_color)
	{
		return color_count[player_color];
	}
	
	private void split(Vector v){
		grid[v.x][v.y].clearCell();
		
		if(!isWinner()){
			color_count[color]--;
			AddOrbThread thread_inc1 = new AddOrbThread(new Vector(v.x-1,v.y));
			AddOrbThread thread_inc2 = new AddOrbThread(new Vector(v.x+1,v.y));
			AddOrbThread thread_inc3 = new AddOrbThread(new Vector(v.x,v.y-1));
			AddOrbThread thread_inc4 = new AddOrbThread(new Vector(v.x,v.y+1));
		}
		
	}
	
	public boolean addOrb(Vector v){
		if(validCell(v) && validMove(v)){
			if(grid[v.x][v.y].getCount()==0)
			{
				color_count[color]++;
			}
			grid[v.x][v.y].raiseCount(color);
			if(!grid[v.x][v.y].isStable()){
				split(v);
			} 
			return true;
		}
		return false;
	}
	
	public void move(Vector v){
		if(addOrb(v)){
			move_count++;
			do{
				color = ++color % num_players;				
			} while(color_count[color] == 0 && move_count > num_players - 1);
		}
	}

	public void printGrid(){
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length; j++){
				if(grid[i][j].getColor()==-1)
				{
					System.out.print(grid[i][j].getCount()+"  ");
				}
				
				else
				{
					System.out.print(grid[i][j].getCount()+""+colorcode[grid[i][j].getColor()]+" ");
				}
			}
			System.out.println("\n");
		}
		System.out.println("-----------------------------------------------------");
		
		for (int k=0; k<num_players; k++){
			System.out.println(colorcode[k]+ " " + color_count[k]);
		}
	}
	
	public boolean isWinner(){
		
		if(move_count > num_players){
			int activeplayers = 0;
			for(int i=0; i < num_players; i++){
				if(color_count[i] > 0)
				{
					activeplayers++;
				}
			}
			if(activeplayers > 1){
				return false;
			}
			return true;
		}		
		
		
		return false;
		
	}
	
	public double evaluate(){
		double w1 = 0.5;
		double w2= 0.3; 
		double w3= 0.2;
		
		double heur1, heur2, heur3;
		heur1 = heuristic1(); // Ratio of stable cells
		heur2 = heuristic2(); // Ratio of acquired cells
		heur3 = heuristic3(); // Ratio of acquired orbs
		
		if(heur3 == 0.0){
			return 0;
			}
		
		else if(heur3 == 1.0){
			return 1;
			}
		
		else{
			double ret_value = w1*heur1 + w2*heur2 + w3*heur3;
			//System.out.println("Total Heuristics calculation... value: " + ret_value);
			return ret_value;
			}
		
		
	}
	
	public double heuristic3(){
		int total_orbs = 0;
		int acquired_orbs = 0;
		
		for(int i=0; i<grid_rowsize; i++){
			for(int j=0; j<grid_colsize; j++){
				Vector v = new Vector(i,j);
				total_orbs = total_orbs + getCell(v).getCount();
				
				
				if(getCell(v).getColor() == color){
					acquired_orbs = acquired_orbs + getCell(v).getCount();					
				}
			}
		}

		double ret_value = (double)acquired_orbs/total_orbs;
		//System.out.println("Heuristics3 calculation... value: " + ret_value);
		return ret_value;
	}
	
	public double heuristic2(){
		int total_cells = grid_rowsize * grid_colsize;
		int acquired_cells = color_count[color];

		double ret_value = (double)acquired_cells/total_cells;
		//System.out.println("Heuristics2 calculation... value: " + ret_value);
		return ret_value;
	}
	
	public double heuristic1(){
		int total_cells = grid_rowsize * grid_colsize;
		int stable_cells = 0;
				
		for(int i=0; i<grid_rowsize; i++){
			for(int j=0; j<grid_colsize; j++){
				Vector v = new Vector(i,j);
				
				if( getCell(v).getCount() == getCell(v).getLimit()-1 ){
					stable_cells++;
				}
				else{
					stable_cells++;
					boolean stable = true;
					
					Vector v1 = new Vector(v.x-1, v.y);					
					if( validCell(v1) && stable){
						if(getCell(v).getLimit() - getCell(v).getCount() < getCell(v1).getLimit() - getCell(v1).getCount()){
							stable_cells--;
							stable = false;
						}
					}
					
					v1.x = v.x + 1;					
					if( validCell(v1) && stable ){
						if(getCell(v).getLimit() - getCell(v).getCount() < getCell(v1).getLimit() - getCell(v1).getCount()){
							stable_cells--;
							stable = false;
						}
					}
					
					v1.x = v.x;
					v1.y = v.y - 1;							
					if( validCell(v1) && stable ){
						if(getCell(v).getLimit() - getCell(v).getCount() < getCell(v1).getLimit() - getCell(v1).getCount()){
							stable_cells--;
							stable = false;
						}
					}
					
					v1. y = v.y + 1;				
					if( validCell(v1) && stable ){
						if(getCell(v).getLimit() - getCell(v).getCount() < getCell(v1).getLimit() - getCell(v1).getCount()){
							stable_cells--;
							stable = false;
						}
					}
				}
			}
		}
		
		double ret_value = (double)stable_cells/total_cells;
		//System.out.println("Heuristics1 calculation... value: " + ret_value);
		return ret_value;		
	}
	
	/*
	public static void main(String arg[]){
		Grid game = new Grid();
		game.printGrid();
		for(int i=0;i<50;i++){
			game.addOrb(new Vector(1,1));
			game.printGrid();
		}
	}
	*/
	
}
