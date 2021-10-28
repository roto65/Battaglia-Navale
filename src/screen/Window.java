package screen;

import javax.swing.JFrame;

public class Window {

	public static void initWindow () {
		
		JFrame window = new JFrame("Battaglia Navale");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Board board = new Board();
		
		window.add(board);
		
		window.addKeyListener(board);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
