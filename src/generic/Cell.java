package generic;

class Cell {
	
	private int orbs_count;
	private int color;
	private int limit;
	
	public Cell(){
		orbs_count = 0;
		color = -1;
		limit = 1;
	}
	
	public Cell(Cell c){
		orbs_count = c.getCount();
		color = c.getColor();
		limit = c.getLimit();
	}
	
	public Cell(int l){
		orbs_count = 0;
		color = -1;
		limit = l;
	}
	
	public int getLimit(){
		return limit;
	}
	
	public int getCount(){
		return orbs_count;
	}
	public int getColor(){
		return color;
	}
	public int setColor(int c){
		color=c;
		return color;
	}
	public boolean isStable(){
		return (limit!=orbs_count);
	}
	public int raiseCount(int col){
		color = col;
		return ++orbs_count;
	}
	public void clearCell(){
		color = -1;
		orbs_count = 0;
	}
}
