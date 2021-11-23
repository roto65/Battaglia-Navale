package main;

import javax.swing.SwingUtilities;

import screen.Window;

public class Main {

	public static void main (String [] args) {
		
		SwingUtilities.invokeLater(() -> {
				
				Window.initWindow();
			
		});		
	}
}
