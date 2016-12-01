/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DaveScof
 */
public class Square {

    int row;
    int column;
    boolean occupied;
    String piece;
    String pieceColor;
    String boardColor;
    boolean edible;
    boolean coveringKing;
    boolean highlighted;
    boolean jumpedTo;
    boolean jumpedToTaker;
    int prioritySet;

    @Override
    public String toString() {
        return String.format("Square " + row + ", " + column + "  BoardColor = %s, Piece = %s %s, Ocuupied = %s", this.boardColor, this.pieceColor, this.piece, this.occupied);
    }
}
