package screen;

import javax.swing.JFrame;
import java.awt.*;

public class Window {

	public static void initWindow () {
		
		JFrame window = new JFrame("Battaglia Navale");

		window.setLayout(new BorderLayout());
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Board playerBoard = new Board(BoardConstants.PLAYER_BOARD);
		Board mapBoard = new Board(BoardConstants.MAP_BOARD);

		window.add(playerBoard, BorderLayout.LINE_START);
		window.add(mapBoard, BorderLayout.LINE_END);


		
		window.addKeyListener(playerBoard);
		window.addKeyListener(mapBoard);

		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
