package generic;

import java.io.*;

class Player {
	
	private int color;
	//private int id = 0; ID for External Network Player
	private int type; // 0 - Human, 1 - AI, 2 - External Network
	private final int HUMAN = 0;
	private final int AI = 1;
	

	public Player(){
		color = -1;
		//id++;
		type = 0;
	}
	
	public Player(int col, int ty){
		color = col;
		//id++;
		type = ty;
	}
	
	public int setColor(int c){
		color = c;
		return color;
	}
	
	public int getColor(){
		return color;
	}
	
	public Vector move(Grid g){
		try{
			if(type == HUMAN){
				return requestHuman();
			}
			
			else if(type == AI){
			 	return requestAI(g);
				}
			
			/* 
			  else {
			  	return requestNetwork();
			  }
			 */
			
			return new Vector();
		}
		catch(IOException e){
			return new Vector();
		}

	}

	public Vector requestHuman() throws IOException {
		System.out.println("Enter the x coordinate, then y coordinate: ");
		BufferedReader i = new BufferedReader(new InputStreamReader(System.in));
		int x = Integer.parseInt(i.readLine());
		int y = Integer.parseInt(i.readLine());
		
		return new Vector(x,y);
	}
	
	public Vector requestAI(Grid g) throws IOException {
		EvalTreeNode etn = new EvalTreeNode(g, 0);
		//System.out.println("Jai ho");
		Vector v = etn.getMove();
		//System.out.println("Jai ho1");
		System.out.println("Move received from AI: " + v.x + "," + v.y);
		
		return v;
	}

}
