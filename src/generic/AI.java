package generic;

public class AI {
	EvalTreeNode evaltree;
	EvalTreeNode current;
	
	AI(){
		evaltree = new EvalTreeNode();
	}
	
	AI(Grid g){
		evaltree = new EvalTreeNode(g, 0);
	}
	
	
}
