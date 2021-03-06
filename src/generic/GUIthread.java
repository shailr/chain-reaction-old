package generic;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUIthread extends JFrame {
	
	int i=0;
	int gridrowsize = 5, gridcolsize = 5;
	int gridsize = gridrowsize * gridcolsize;
	static JPanel panel;
	int[] clicks = new int[gridsize];
	JButton[] buttons = new JButton[gridsize];
	
	class ThrIncrement implements Runnable {
		
		Thread th1; 
		private int row_num;
		private int col_num;
		
		public ThrIncrement (int r, int c){
			th1 = new Thread(this);
			row_num = r;
			col_num = c;
			th1.start();
		}
		
		
		public void run(){
				if (row_num >= 0 && row_num <= (gridrowsize - 1) 
						&& col_num >= 0 && col_num <= (gridcolsize - 1))
				{
					int limit = 4;
					int but_num;
			
					but_num = row_num * gridrowsize + col_num;
			
					if (row_num == 0 || row_num == gridrowsize - 1)
					{
						limit--;
					}
			
					if (col_num == 0 || col_num == gridcolsize - 1)
					{
						limit--;
					}
					
					if (++clicks[but_num] == limit)
					{
						clicks[but_num] = 0;
						explode(but_num);
					}
			
					buttons[but_num].setText("" + clicks[but_num]);
					System.out.println("Inside " + th1.getId());
				}	
		}
	}
	
	public GUIthread() {
		
		
		panel=new JPanel(new GridLayout(gridrowsize, gridcolsize));
		
		for(i = 0; i < gridsize; i++) {
			
			clicks[i]=0;
			buttons[i] = new JButton("0");
			buttons[i].setSize(200, 200);
			buttons[i].setActionCommand("" + i);
			
			buttons[i].addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
					
					String choice = e.getActionCommand();
					int butnum = Integer.parseInt(choice);
					int limit = 4; // No. of orbs at which explosion occurs.
					int row, col; // Row, col of button to determine individual limit.
					
					row = (butnum) / gridrowsize;
					col = (butnum) % gridcolsize;
					
					if (row == 0 || row == gridrowsize - 1)
					{
						limit--;
					}
					
					if (col == 0 || col == gridcolsize - 1)
					{
						limit--;
					}
					
					if (++clicks[butnum] == limit)
					{
						clicks[butnum] = 0;
						explode(butnum);
					}
					
					buttons[butnum].setText("" + clicks[butnum]);
					
				}
				
			});
			
			panel.add(buttons[i]);
		}
			
	}
	
	public void explode(int but_num){
		
		int limit = 4; // No. of orbs at which explosion occurs.
		int row, col; // Row, col of button to determine individual limit.
		
		row = (but_num) / gridrowsize;
		col = (but_num) % gridcolsize;
		
		ThrIncrement thread_inc1 = new ThrIncrement(row-1,col);
		ThrIncrement thread_inc2 = new ThrIncrement(row,col-1);
		ThrIncrement thread_inc3 = new ThrIncrement(row+1,col);
		ThrIncrement thread_inc4 = new ThrIncrement(row,col+1);
		/*
		Thread thread1 = new Thread(thread_inc1, "Gunjan");
		Thread thread2 = new Thread(thread_inc2, "Shreyas");
		Thread thread3 = new Thread(thread_inc3, "Sarvesh");
		Thread thread4 = new Thread(thread_inc4, "Shail");
		*/
		
	}
	
	public static void main(String[]args){
		
		GUIthread frame=new GUIthread();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
	}
		
}