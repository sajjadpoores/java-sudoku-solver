import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;

public class Main { 
	public static Font Ftitle = new Font("Times New Roman",Font.BOLD,30);
	public static JTextField[][] cell = new JTextField[9][9];
	public static int indexi = 0;
	public static int indexj = 0;
	public static char[][] tbl = new char[9][9];
	public static void main(String[] args){
		JFrame frame = new JFrame("Sudoku Solver");
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 		
		
		JPanel rootpannel = new JPanel(new BorderLayout());
		rootpannel.setBackground(Color.white);
		
		JPanel top_pannel = new JPanel(); // top
		top_pannel.setBackground(Color.white);
		
		JLabel title_lable = new JLabel("Sudoku Solver");
		title_lable.setForeground(Color.black);
		title_lable.setFont(Ftitle);
		top_pannel.add(title_lable);
		
		JLabel about_lable = new JLabel("sajjad poores");
		about_lable.setForeground(Color.BLUE);
		top_pannel.add(about_lable);
		
		JPanel center_pannel = new JPanel(new GridLayout(9, 9)); //center
		center_pannel.setBackground(Color.white);
		
		
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				cell[i][j] = new JTextField();
				cell[i][j].setFont(Ftitle);
				cell[i][j].setColumns(1);
				center_pannel.add(cell[i][j]);
			}
		
		JPanel south_pannel = new JPanel(); // bottom
		south_pannel.setBackground(Color.white);
		
		JButton solve_btn = new JButton("Solve");
		solve_btn.setBackground(Color.white);
		solve_btn.setForeground(Color.black);
		solve_btn.setFont(Ftitle);
		
		solve_btn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(!checkchars())
					JOptionPane.showMessageDialog(new JFrame(),"you should enter 1 digit numbers (or just leave cells empty)");

				entersoduko();
				
				solve();
				
				
				for(int i=0;i<9;i++)
					for(int j=0;j<9;j++){
						if(cell[i][j].getText().equals("0"))
							cell[i][j].setForeground(Color.red);
						cell[i][j].setText(String.valueOf(tbl[i][j]));
					}
						
				
				
			}
		});
		south_pannel.add(solve_btn);
		
		JButton clean_btn = new JButton("Clean");
		clean_btn.setBackground(Color.white);
		clean_btn.setForeground(Color.black);
		clean_btn.setFont(Ftitle);
		clean_btn.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<9;i++)
					for(int j=0;j<9;j++){
						cell[i][j].setForeground(Color.black);
						cell[i][j].setText("");
					}
						
				
			}
		});
		south_pannel.add(clean_btn);
		
		rootpannel.add(south_pannel,BorderLayout.SOUTH);
		rootpannel.add(center_pannel, BorderLayout.CENTER);
		rootpannel.add(top_pannel, BorderLayout.NORTH);
		frame.add(rootpannel);
		frame.setVisible(true); // Display the frame
	}
	
	public static boolean checkchars(){
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				String str = cell[i][j].getText();
				if(str.equals(""))
					cell[i][j].setText("0");
				else if(str.length() > 1)
					return false;
				else if(str.charAt(0) < '0' || str.charAt(0) > '9')
					return false;
			}
		
		return true;
	}
	
	public static void entersoduko(){
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				tbl[i][j] = cell[i][j].getText().charAt(0);
	}
	
	public static boolean isitpossible(int i, int j,char n){
		for (int x = 0; x < 9; x++) //check row
			if (x == j)
				continue;
			else if (tbl[i][x] == n)
				return false;
		
		for (int x=0;x<9;x++) //check column
			if (x == i)
				continue;
			else if (tbl[x][j] == n)
				return false;
		
		int sx = i / 3;//square bounds
		int sy = j / 3;

		for (int x = sx * 3; x < (sx + 1) * 3; x++) // check square
			for (int y = sy * 3; y < (sy + 1) * 3; y++)
				if (x == i && y == j)
					continue;
				else if (tbl[x][y] == n)
					return false;

		return true;

	}


	
public static boolean findzero(){
	for (indexi = 0; indexi < 9;indexi++)
	for (indexj = 0; indexj < 9;indexj++)
	if (tbl[indexi][indexj] == '0')
		return true;
	return false;
}
public static boolean solve(){
	
	if (!findzero()) 
		return true; // sudoku is solved
	int i = indexi;
	int j = indexj;
	for (char n = '1'; n <= '9'; n++){
		if (isitpossible(i, j, n)){
			tbl[i][j] = n;

			if (solve())
				return true;

			tbl[i][j] = '0'; // restart
		}
	}

	//JOptionPane.showMessageDialog(new JFrame(),"Unsolvable");
	return false; // wrong decision
}
}
