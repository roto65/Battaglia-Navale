package screen;

import board.InventoryBoard;
import board.MapBoard;
import board.PlayerBoard;

import javax.swing.JFrame;
import java.awt.*;

public class Window {

	public static void initWindow () {
		
		JFrame window = new JFrame("Battaglia Navale");

		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.setLayout(new BorderLayout());

		PlayerBoard playerBoard = new PlayerBoard();
		MapBoard mapBoard = new MapBoard();
		InventoryBoard inventoryBoard = new InventoryBoard();

		window.add(inventoryBoard, BorderLayout.LINE_START);
		window.add(playerBoard, BorderLayout.CENTER);
		window.add(mapBoard, BorderLayout.LINE_END);

		window.addMouseListener(playerBoard);

		window.addMouseListener(mapBoard);

		window.addKeyListener(playerBoard);

		window.addKeyListener(mapBoard);

		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}
}
