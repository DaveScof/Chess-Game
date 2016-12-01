/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DaveScof
 */
import java.awt.*;
import javax.swing.*;

public class GameBoard extends JFrame {

//    private String location = "D:\\Engineering and related stuff\\My programs\\Java\\ChessGame\\Image Resources\\";
    private String location = "D:\\Engineering and related stuff\\My programs\\Java\\ChessGame\\Image Resources"; 
    private ChessBoard chessBoard;
    // North Label and Image
    private AImageIcon northImage;
    private JLabel northLabel;
    //South Label and Image
    private AImageIcon southImage;
    private JLabel southLabel;
    //East Label and Image
    private AImageIcon eastImage;
    private JLabel eastLabel;
    //West Label and Image
    private AImageIcon westImage;
    private JLabel westLabel;

    public GameBoard() {
        
        super("Chess Masters");
        setLayout(new BorderLayout());
        // Add ChessBoard to the Center of the layout
        chessBoard = new ChessBoard();
        add(chessBoard, BorderLayout.CENTER);

//        Add the North image through a label
        northImage = new AImageIcon(UtilityBelt.resLocation + "north3.jpg", 1000, 50);
        northLabel = new JLabel(northImage.getIcon());
        add(northLabel, BorderLayout.NORTH);

//        Add the south Image
        southImage = new AImageIcon(UtilityBelt.resLocation + "north3.jpg", 1000, 50);
        southLabel = new JLabel(southImage.getIcon());
        add(southLabel, BorderLayout.SOUTH);

//        Add the East Image
        eastImage = new AImageIcon(UtilityBelt.resLocation + "north3.jpg", 50, 1000);
        eastLabel = new JLabel(eastImage.getIcon());
        add(eastLabel, BorderLayout.EAST);

//        Add the West Image
        westImage = new AImageIcon(UtilityBelt.resLocation + "north3.jpg", 50, 1000);
        westLabel = new JLabel(westImage.getIcon());
        add(westLabel, BorderLayout.WEST);
    }
}