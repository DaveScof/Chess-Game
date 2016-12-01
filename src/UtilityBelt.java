/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DaveScof
 */
import java.awt.Image;
import javax.swing.*;

public class UtilityBelt {

    public static boolean gameMultiPlayer;
    public static String resLocation = "/home/dave-5cof/Documents/Java Programs/ChessGame/Image Resources/";
    static JButton singlePlayerButton = new JButton(new AImageIcon(UtilityBelt.resLocation + "singlePlayer.jpg", 300, 200).getIcon());
    static JButton multiPlayerButton = new JButton(new AImageIcon(UtilityBelt.resLocation + "multiPlayer.jpg", 300, 200).getIcon());

   static  JFrame startFrame = new JFrame("Chess Masters");
    public ImageIcon resizeIcon(ImageIcon iconOld, int width, int height) {

        Image image = iconOld.getImage();
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);
        return newIcon;

    }
}
