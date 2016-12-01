/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DaveScof
 */
public class MainChessGame extends UtilityBelt {

    public static void main(String args[]) {



        ImageIcon icon = new AImageIcon(UtilityBelt.resLocation + "startFrame.jpg", 700, 355).getIcon();

        startFrame.setLayout(new BorderLayout());
        JLabel l = new JLabel(icon);


        singlePlayerButton.setBackground(Color.BLACK);
        multiPlayerButton.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.add(singlePlayerButton);
        panel.add(multiPlayerButton);

        startFrame.add(l, BorderLayout.NORTH);
        startFrame.add(panel, BorderLayout.SOUTH);
//        startFrame.add(multiPlayerButton, BorderLayout.SOUTH);


//        startFrame.setIconImage(icon.getImage());

//        JPanel startPanel = new JPanel();

        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(700, 600);
        startFrame.setVisible(true);
        startFrame.setResizable(false);
        startFrame.setLocation(500, 100);

        GameSelectionHandler gameHandler = new GameSelectionHandler();
        singlePlayerButton.addActionListener(gameHandler);
        multiPlayerButton.addActionListener(gameHandler);

    }

    private static class GameSelectionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == singlePlayerButton) {
                UtilityBelt.gameMultiPlayer = false;
                startFrame.dispose();
                
                GameBoard frame = new GameBoard();
                frame.setLocation(400, 5);
                frame.setSize(1000, 1000);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                frame.setResizable(false);
            }

            if (e.getSource() == multiPlayerButton) {
                UtilityBelt.gameMultiPlayer = true;
                startFrame.dispose();

                GameBoard frame = new GameBoard();

                frame.setLocation(400, 5);

                frame.setSize(1000, 1000);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);

                frame.setResizable(false);
            }
        }
    }
}
