/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DaveScof
 */
import javax.swing.*;

public class AImageIcon extends UtilityBelt {

    private ImageIcon icon;

    public AImageIcon(String location, int width, int height) {
        icon = new ImageIcon(location);
        icon = resizeIcon(icon, width, height);
    }

    public ImageIcon getIcon() {
        return icon;
    }
}