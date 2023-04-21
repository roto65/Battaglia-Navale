package screen;

import board.Board;
import board.InventoryBoard;
import board.MapBoard;
import board.PlayerBoard;

import javax.swing.*;
import java.awt.*;


public class Window {

    private static JFrame window;

    private static PlayerBoard playerBoard;
    private static MapBoard mapBoard;
    private static InventoryBoard inventoryBoard;
    private static GridBagConstraints gridBagConstraints;

    public static void initMainWindow() {

        window = new JFrame("Battaglia Navale");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;

        playerBoard = new PlayerBoard();
        mapBoard = new MapBoard();
        inventoryBoard = new InventoryBoard();

        fase1();

        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    //Primo stato della partita: posizionamento delle navi sul campo di gioco
    public static void fase1() {
        Line line = new Line(10, Color.WHITE);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = Board.ROWS;
        window.add(inventoryBoard, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = Board.ROWS;
        window.add(line, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = Board.ROWS;
        window.add(mapBoard, gridBagConstraints);

        toggleKeyListener("add", "inventoryBoard");

        inventoryBoard.addListener(mapBoard);
        mapBoard.addListener(inventoryBoard);
    }

    //Secondo stato della partita: battaglia navale
    public static void fase2() {

        window.remove(inventoryBoard);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = Board.ROWS;

        window.add(playerBoard, gridBagConstraints);

        window.revalidate();
        window.repaint();

        toggleKeyListener("remove", "mapBoard");
        toggleKeyListener("add", "playerBoard");

    }

    public static void toggleKeyListener(String action, String target) {
        if (action.equals("add")) {
            switch (target) {
                case "playerBoard" -> window.addKeyListener(playerBoard);
                case "mapBoard" -> window.addKeyListener(mapBoard);
                case "inventoryBoard" -> window.addKeyListener(inventoryBoard);
            }
        } else if (action.equals("remove")) {
            switch (target) {
                case "playerBoard" -> window.removeKeyListener(playerBoard);
                case "mapBoard" -> window.removeKeyListener(mapBoard);
                case "inventoryBoard" -> window.removeKeyListener(inventoryBoard);
            }
        }
    }

    public static void toggleMouseListener(String action, String target) {
        if (action.equals("add")) {
            switch (target) {
                case "playerBoard" -> window.addMouseListener(playerBoard); //unica board che implementa il mouse
                case "mapBoard" -> window.addMouseListener(mapBoard);
                case "inventoryBoard" -> window.addMouseListener(inventoryBoard);
            }
        } else if (action.equals("remove")) {
            switch (target) {
                case "playerBoard" -> window.removeMouseListener(playerBoard); //unica board che implementa il mouse
                case "mapBoard" -> window.removeMouseListener(mapBoard);
                case "inventoryBoard" -> window.removeMouseListener(inventoryBoard);
            }
        }
    }

   /* public static void initSelectionWindow() {
        // TODO: 16/03/2022 inventory board + map board + cursore di qualche tipo
        // TODO: 16/03/2022 aggiungere bottoni di conferma e di reset

        JFrame window = new JFrame("Pedinamento");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;

        Button confirmButton = new Button("Confirm");
        confirmButton.addActionListener(actionEvent -> {
            initMainWindow();

            window.dispose();
        });

        Button cancelButton = new Button("Cancel");

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        window.add(confirmButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        window.add(cancelButton, gridBagConstraints);

        window.setResizable(true);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    } */
}
