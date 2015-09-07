package generic;

public class EvalTreeNode {
	private Grid grid;
	private Vector prev_move = new Vector();
	private double utility_value;
	private int depth;
	private final int DEPTH_MAX = 2;
	EvalTreeNode best;
	EvalTreeNode current;
	
	public EvalTreeNode(){
		grid = new Grid();
		depth = 0;
		//DEPTH_MAX = grid.getActivePlayers()*5;
		utility_value = 0.0;
		prev_move.x = -1;
		prev_move.y = -1;
	}
	
	public EvalTreeNode(Grid g, int d){
		grid = new Grid(g);
		depth = d;
		//DEPTH_MAX = grid.getActivePlayers()*5;
		utility_value = 0.0;//grid.evaluate();
		prev_move.x = -1;
		prev_move.y = -1;
	}
	
	/*public EvalTreeNode(Grid g, Vector v, int d){
		grid = new Grid(g);
		g.move(v);
		DEPTH_MAX = grid.getActivePlayers()*5;
		depth = d;
		prev_move.x = v.x;
		prev_move.y = v.y;
		
		//utility_value = evaluate(g);
	}*/
	
	public EvalTreeNode(int util_val, int d){
		grid = null;
		depth = d;
		//DEPTH_MAX = 20;
		utility_value = util_val;
		prev_move.x = -1;
		prev_move.y = -1;
	}
	
	public EvalTreeNode(EvalTreeNode etn){
		grid = new Grid(etn.grid);
		depth = etn.depth;
		//DEPTH_MAX = etn.DEPTH_MAX;
		utility_value = etn.utility_value;
	}
	
	public double getUtilVal(){
		return utility_value;
	}
	
	public Vector getMove(){
		evaluation(this, this.depth);
		return this.prev_move;
	}
	
	static int n = 0;
	public EvalTreeNode evaluation(EvalTreeNode etn, int d /*, Vector v*/){
		etn.depth = d;
		EvalTreeNode temp_best = new EvalTreeNode(-1, depth);
		EvalTreeNode temp_curr = new EvalTreeNode(etn);
		EvalTreeNode temp_next = new EvalTreeNode(etn);
		int i = 0, j = 0;
		int num_rows = etn.grid.getGridRowSize();
		int num_cols = etn.grid.getGridColSize();
		//System.out.println("Seq: " + n++);
		
		if (d == etn.DEPTH_MAX || etn.grid.isWinner()){
			etn.computeUtilVal();
			//System.out.println("Depth: " + d + "\tUtility value " + etn.getUtilVal() + "\t Color:" + etn.grid.getColor());
			return etn;
		}
		
		else if(d < DEPTH_MAX)// Not at max_depth
		{
			for(i=0; i<num_rows; i++){
				for(j=0; j<num_cols; j++){
					Vector v1 = new Vector(i,j);
					//System.out.println("In nested fors vector: " + v1.x+v1.y);
					if(temp_curr.grid.validMove(v1)){
						temp_next = new EvalTreeNode(temp_curr);
						temp_next.grid.move(v1);
						//System.out.println("Depth: " + d + "\t Vector: " + v1.x + "," +
							//	" " + v1.y + "\t PrevMove: " + prev_move.x + "," +
								//		" " + prev_move.y + "\tColor: " + grid.getColor());
						temp_next = evaluation(temp_next, d + 1 /*, v1*/);
					}
					temp_best = bestOfTwo(temp_best, temp_next, v1);
				}
			}
			return temp_best;		
		}
		
		else{
			return new EvalTreeNode(-1, DEPTH_MAX);
		}
	}
	
	public void computeUtilVal(){
		utility_value = grid.evaluate();
	}
	
	public EvalTreeNode bestOfTwo(EvalTreeNode best, EvalTreeNode curr, Vector v){
		
		if(best.utility_value == -1){
			prev_move = v;
			return curr;
		}
		
		int depth1 = curr.depth;
		int activeplayers = grid.getActivePlayers();
		//System.out.println("Active players: " + activeplayers);
		if(depth1 % activeplayers == 0) //Max_cond
		{
			//System.out.println("MAX");
			if(curr.getUtilVal() >= best.getUtilVal()){
				//System.out.println("YES1");
				prev_move = v;
				return curr;
			}
		}
		
		else //MIN_COND
		{
			//System.out.println("MIN");
			if(curr.getUtilVal() < best.getUtilVal()){
				//System.out.println("YES2");
				prev_move = v;
				return curr;
			}
		}
		//System.out.println("YES3");
		return best;
	}
	
}
