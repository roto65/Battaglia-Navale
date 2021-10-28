package main;

import javax.swing.SwingUtilities;

import screen.Window;

public class Main {

	public static void main (String [] args) {
		
		SwingUtilities.invokeLater(new Runnable () {
			
			public void run () {
				
				Window.initWindow();
			}
		});
	}
}
