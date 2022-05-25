package screen;

import board.InventoryBoard;
import board.MapBoard;
import board.PlayerBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class Window {

    private static JFrame window;

    private static PlayerBoard playerBoard;
    private static MapBoard mapBoard;
    private static InventoryBoard inventoryBoard;

    public static void initMainWindow() { // TODO: 16/03/2022 player board + map board

        window = new JFrame("Battaglia Navale");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;

        //playerBoard = new PlayerBoard();
        mapBoard = new MapBoard();
        inventoryBoard = new InventoryBoard();

        Line line = new Line(10, Color.WHITE);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        window.add(inventoryBoard, gridBagConstraints);

        //window.add(playerBoard, BorderLayout.CENTER);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        window.add(line, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        window.add(mapBoard, gridBagConstraints);

        //window.addMouseListener(playerBoard);
        //window.addMouseListener(mapBoard);

        //window.addKeyListener(playerBoard);
        //window.addKeyListener(mapBoard);
        toggleKeyListener("add", "inventoryBoard");

        inventoryBoard.addListener(mapBoard);
        mapBoard.addListener(inventoryBoard);


        window.setResizable(false);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

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

    public static void initSelectionWindow() {
        // TODO: 16/03/2022 inventory board + map board + cursore di qualche tipo
        // TODO: 16/03/2022 aggiungere bottoni di conferma e di reset

        JFrame window = new JFrame("Pedinamento");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;

        Button confirmButton = new Button("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initMainWindow();

                window.dispose();
            }
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
    }
}
