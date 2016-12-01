/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 *
 * @author DaveScof
 */
public class ChessBoard extends JPanel {

// The button array displayed on the Chessboard JPanel
    JButton[][] b = new JButton[8][8];
//    
//    
// Creating the "Square"array that will hold every information for each of the 64 squares
    Square[][] square;
//    
//A Square variable used to store the currently selected square information 
    Square selectedSquare;
//    
//A Square variable used to store the square selected for moving the current square properties
    Square moveToSquare;
//    
// Variables that are used to know whose turn it is in the game
    final String whiteTurn = "white";
    final String blackTurn = "black";
    String turn;
//    
//ThinkTank is a class that handles where each piece can or can't go 
    ThinkTank thinkTank = new ThinkTank();

//an enum that lets us know if any piece on the chessboard has been selected or not
    enum ClickStatus {

        Selected, NotSelected
    };
    ClickStatus clickStatus;
// A Square array that holds values of all the possible squares available for the selected piece
    Square possibleSquare[];
    Square otherPossibleSquare[];
// Frames and buttons used to construct the frme that is displayed when a pawn reaches the end of the corresponing rows. The user then choses which of the higher pieces he wants his pawn to be converted to
    JFrame choiceFrame = new JFrame("Choose a Piece");
    JButton rookW = new JButton();
    JButton knightW = new JButton();
    JButton bishopW = new JButton();
    JButton queenW = new JButton();
    JButton rookB = new JButton();
    JButton knightB = new JButton();
    JButton bishopB = new JButton();
    JButton queenB = new JButton();
//Variables used to handle the enpassant rule of the pawn
    int jumpedToStatus = 0;
    Square jumpedToSquare = new Square();
    Square jumpedToTakerSquare;
//Booleans used to check if these particular pieces have moved from their original positions when handling the "Castling" move
    boolean whiteKingMoved = false;
    boolean blackKingMoved = false;
    boolean leftWhiteRookMoved = false;
    boolean rightWhiteRookMoved = false;
    boolean leftBlackRookMoved = false;
    boolean rightBlackRookMoved = false;
    //variables to handle Check and Check Mate
    boolean kingInCheck = false;      //Handled in handleMove() and handleSelect ()
    Square checkCauseSquare = new Square();
    Square checkedSquare = new Square();
    boolean shouldSelect = true;
    int r, c;
    boolean checkMate = false;
    boolean thereIsAPossmove;
    int rn, cn;
    boolean dangerunless = false;

    public ChessBoard() {

        selectedSquare = new Square();
        moveToSquare = new Square();

        square = new Square[8][8];
        //Initializing the Square and button array
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                square[i][j] = new Square();
                b[i][j] = new JButton();
            }
        }
        //Setting up the GridLayout
        setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j] = new JButton();
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                add(b[i][j]);
            }
        }
        // Registering Event handler to every button, Note that there's only one handler for all buttons!

        ButtonsHandler handler = new ButtonsHandler();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                (b[i][j]).addActionListener(handler);
            }
        }

//        b[0][0].setIcon(icon1.getIcon());

        //Setting up the Square Class
        Color y = new Color(89, 9, 27);
        setBackground(y);

        initializeSquare();
        initializeBoard();



        turn = whiteTurn;
        clickStatus = ClickStatus.NotSelected;

    }
    //Button Handler Class

    //Private class that handles the pressing of the chess piece buttons
    private class ButtonsHandler implements ActionListener {

        //variables used to find the row and the column of the pressed button
        int bRow = 0;
        int bColumn = 0;

//        public int turnKeeper() {
//            int T = 0;
//            String CurrentTurn = "whiteTurn";
////    do{
////       T=T++;
////       CurrentTurn=CB.turn;
////      }while (CB.turn.equals(CurrentTurn)==false);
//            T = T++;
//            return T;
//        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // To get the row and column of the pressed button and set the values to bRow and bColumn
            int secondBreak = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (e.getSource() == b[i][j]) {
                        bRow = i;
                        bColumn = j;
                        secondBreak = 1;
                        break;
                    }
                }
                if (secondBreak == 1) {
                    break;
                }
            }

//          

            // If status is NotSelected which means a piece has not been selected already, then the pressedButton is set to the selecetdSquare variable (or it would be set to the moveToSquare)
            if (clickStatus == ClickStatus.NotSelected) {
//              
                selectedSquare = square[bRow][bColumn];

                //pass selectedSquare to handleSelection method for selection handling
                if (selectedSquare.occupied && selectedSquare.pieceColor.equals(turn)) {
                    //preHandle the selection for endangering king by move senario

                    //beacause selected square is the right color and is occupied
                    handleSelection();
                    clickStatus = ClickStatus.Selected;
                }
//            }
//              
            } //If status is Selected, then the button pressed should be passed to the moveToSquare variable, while the selectedSquare is intact
            else {
                moveToSquare = square[bRow][bColumn];

                handleMove(selectedSquare, moveToSquare);
                clickStatus = ClickStatus.NotSelected;
                //Thanks to Tensae
                if (moveToSquare.occupied && moveToSquare.pieceColor.equals(selectedSquare.pieceColor)) {
                    actionPerformed(e);
                }
            }
//            
            if (!UtilityBelt.gameMultiPlayer && turn.equals(blackTurn)) {
                JOptionPane.showMessageDialog(null, "Computer's Move");
                turn = whiteTurn;

            }
        }
    }

    //method for initializing the chessboard according to the Square array
    public final void initializeBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                b[i][j].setIcon(new AImageIcon(UtilityBelt.resLocation + square[i][j].pieceColor + square[i][j].piece + square[i][j].boardColor + ".jpg", 109, 109).getIcon());
            }
        }
    }

    //Method for initializing the Square array
    public final void initializeSquare() {
        //Initialize all the Color variables
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    square[i][j].boardColor = "White";
                } else {
                    square[i][j].boardColor = "Black";
                }
                square[i][j].jumpedToTaker = false;
                square[i][j].highlighted = false;
                square[i][j].jumpedTo = false;
                square[i][j].row = i;
                square[i][j].column = j;
            }
        }

        //Initialize row 2 to row 4
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j < 8; j++) {
                square[i][j].piece = null;
                square[i][j].occupied = false;
                square[i][j].row = i;
                square[i][j].column = j;

            }
        }

// Initialize black pawns

        for (int j = 0; j < 8; j++) {
            square[1][j].piece = "Pawn";
            square[1][j].pieceColor = "black";
            square[1][j].occupied = true;
            square[1][j].row = 1;
            square[1][j].column = j;
        }

// Initialize white Pawns
        for (int j = 0; j < 8; j++) {
            square[6][j].piece = "Pawn";
            square[6][j].pieceColor = "white";
            square[6][j].occupied = true;
            square[6][j].row = 6;
            square[6][j].column = j;
        }

// Initialize black Rooks


        for (int j = 0; j < 8; j += 7) {
            square[0][j].piece = "Rook";
            square[0][j].pieceColor = "black";
            square[0][j].occupied = true;
            square[0][j].row = 0;
            square[0][j].column = j;
        }

// Initialize white Rooks
        for (int j = 0; j < 8; j += 7) {
            square[7][j].piece = "Rook";
            square[7][j].pieceColor = "white";
            square[7][j].occupied = true;
            square[7][j].row = 7;
            square[7][j].column = j;
        }

//Initialize Black Knights yeaaaaa
        for (int j = 1; j < 8; j += 5) {
            square[0][j].piece = "Knight";
            square[0][j].pieceColor = "black";
            square[0][j].occupied = true;
            square[0][j].row = 0;
            square[0][j].column = j;
        }

//Initialize Black Bishops

        for (int j = 2; j < 8; j += 3) {
            square[0][j].piece = "Bishop";
            square[0][j].pieceColor = "black";
            square[0][j].occupied = true;
            square[0][j].row = 0;
            square[0][j].column = j;
        }

//// Initialize Black King
//
//
        square[0][4].piece = "King";
        square[0][4].pieceColor = "black";
        square[0][4].occupied = true;
        square[0][4].row = 0;
        square[0][4].column = 4;

// Initialize Black Queen
        square[0][3].piece = "Queen";
        square[0][3].pieceColor = "black";
        square[0][3].occupied = true;
        square[0][3].row = 0;
        square[0][3].column = 3;


//Initialize White Night
        for (int j = 1; j < 8; j += 5) {
            square[7][j].piece = "Knight";
            square[7][j].pieceColor = "white";
            square[7][j].occupied = true;
            square[7][j].row = 7;
            square[7][j].column = j;
        }

//Initialize White Bishops
        for (int j = 2; j < 8; j += 3) {
            square[7][j].piece = "Bishop";
            square[7][j].pieceColor = "white";
            square[7][j].occupied = true;
            square[7][j].row = 7;
            square[7][j].column = j;
        }

//Initialize White King
        square[7][4].piece = "King";
        square[7][4].pieceColor = "white";
        square[7][4].occupied = true;
        square[7][4].row = 7;
        square[7][4].column = 4;

//Initialize White Queen
        square[7][3].piece = "Queen";
        square[7][3].pieceColor = "white";
        square[7][3].occupied = true;
        square[7][3].row = 7;
        square[7][3].column = 3;

//        For testing only
//        int tr = 4;
//        int tc = 4;
//
//        square[tr][tc].piece = "Queen";
//        square[tr][tc].pieceColor = "white";
//        square[tr][tc].occupied = true;


    }

    //Method for handling piece selectoin
    public void handleSelection() {

        String sPiece = selectedSquare.piece;
        String sPieceColor = selectedSquare.pieceColor;
        boolean sOccupied = selectedSquare.occupied;

        square[selectedSquare.row][selectedSquare.column].piece = null;
        square[selectedSquare.row][selectedSquare.column].occupied = false;
        square[selectedSquare.row][selectedSquare.column].pieceColor = null;

        boolean endangersKing = checkIfKingFound(sPieceColor, sPiece);

        square[selectedSquare.row][selectedSquare.column].piece = sPiece;
        square[selectedSquare.row][selectedSquare.column].occupied = sOccupied;
        square[selectedSquare.row][selectedSquare.column].pieceColor = sPieceColor;

        boolean shouldSelect2 = true;
        if (endangersKing) {
            shouldSelect2 = false;
        }

        thereIsAPossmove = false;

        shouldSelect = true;
        if (kingInCheck) {
            shouldSelect = checkHandler();
        }

        if (selectedSquare.occupied && ((kingInCheck && shouldSelect) || (!kingInCheck && shouldSelect2))) {

            if (dangerunless) {
                Highlighting(selectedSquare, square[rn][cn]);
                square[rn][cn].highlighted = true;
            } else {
                //find possible moves for selected Square

                possibleSquare = thinkTank.findPossibleMoves(selectedSquare, square);

                //Highlight the possible moves
                Highlighting(selectedSquare, possibleSquare);
                for (int i = 0; i < possibleSquare.length; i++) {
                    int prow = possibleSquare[i].row;
                    int pcol = possibleSquare[i].column;
                    if (!square[prow][pcol].occupied || (square[prow][pcol].occupied && !square[prow][pcol].pieceColor.equals(selectedSquare.pieceColor))) {

                        square[possibleSquare[i].row][possibleSquare[i].column].highlighted = true;
                    }
                }

                //Highlight castling move
                boolean whiteLeftCastlingPossible = (!whiteKingMoved && !leftWhiteRookMoved);
                boolean whiteRightCastlingPossible = (!whiteKingMoved && !rightWhiteRookMoved);
                boolean blackLeftCastlingPossible = (!blackKingMoved && !leftBlackRookMoved);
                boolean blackRightCastlingPossible = (!blackKingMoved && !rightBlackRookMoved);

                boolean whiteLeftClear = true;
                boolean whiteRightClear = true;
                boolean blackLeftClear = true;
                boolean blackRightClear = true;


                for (int i = 0; i < 3; i++) {
                    if (square[7][i + 1].occupied) {
                        whiteLeftClear = false;
                    }
                }

                for (int i = 0; i < 2; i++) {
                    if (square[7][i + 5].occupied) {
                        whiteRightClear = false;
                    }
                }

                for (int i = 0; i < 3; i++) {
                    if (square[0][i + 1].occupied) {
                        blackLeftClear = false;
                    }
                }
                for (int i = 0; i < 2; i++) {
                    if (square[0][i + 5].occupied) {
                        blackRightClear = false;
                    }
                }

                Square[] castlingSquare = new Square[1];
                castlingSquare[0] = new Square();

                switch (selectedSquare.piece) {
                    case "King":
                        if ("white".equals(selectedSquare.pieceColor) && whiteLeftClear && whiteLeftCastlingPossible) {
                            castlingSquare[0].row = 7;
                            castlingSquare[0].column = 2;
                            Highlighting(selectedSquare, castlingSquare);
                            square[7][2].highlighted = true;

                        }
                        if ("white".equals(selectedSquare.pieceColor) && whiteRightClear && whiteRightCastlingPossible) {
                            castlingSquare[0].row = 7;
                            castlingSquare[0].column = 6;
                            Highlighting(selectedSquare, castlingSquare);
                            square[7][6].highlighted = true;
                        }

                        if ("black".equals(selectedSquare.pieceColor) && blackLeftClear && blackLeftCastlingPossible) {
                            castlingSquare[0].row = 0;
                            castlingSquare[0].column = 2;
                            Highlighting(selectedSquare, castlingSquare);
                            square[0][2].highlighted = true;
                        }
                        if ("black".equals(selectedSquare.pieceColor) && blackRightClear && blackRightCastlingPossible) {
                            castlingSquare[0].row = 0;
                            castlingSquare[0].column = 6;
                            Highlighting(selectedSquare, castlingSquare);
                            square[0][6].highlighted = true;
                        }

                        break;
                    default:
                }
            }

        }
    }

    //Method for Highlighting the possible moves
    public void Highlighting(Square selectedSquare, Square... possibleSquare)//clicked and where it could possibleSqaures-ibly move to
    {
        Square[] possibleSqaures;
        int br = selectedSquare.row;
        int bc = selectedSquare.column;
        possibleSqaures = possibleSquare;

        b[br][bc].setIcon(new AImageIcon(UtilityBelt.resLocation + selectedSquare.pieceColor + selectedSquare.piece + "Highlighted.jpg", 109, 109).getIcon());

        int prow;
        int pcol;


        for (int k = 0; k < possibleSquare.length; k++) {
//            if(possibleSquare[k].occupied)


            prow = possibleSquare[k].row;
            pcol = possibleSquare[k].column;

            if (!square[prow][pcol].occupied || (square[prow][pcol].occupied && !square[prow][pcol].pieceColor.equals(selectedSquare.pieceColor))) {

                if (square[prow][pcol].occupied == false) {
                    b[prow][pcol].setIcon(new AImageIcon(UtilityBelt.resLocation + "nullnullHighlighted3.jpg", 109, 109).getIcon());
                } else {
                    b[prow][pcol].setIcon(new AImageIcon(UtilityBelt.resLocation + square[prow][pcol].pieceColor + square[prow][pcol].piece + "Highlighted.jpg", 109, 109).getIcon());
                }
            }
        }
    }

    //Method for handling the move of a piece from "selectedSquare" to "moveTosquare"
    public void handleMove(Square selectedSquare, Square moveToSquare) {

        if (moveToSquare.highlighted) {

            kingInCheck = false;
            move(selectedSquare, moveToSquare);

            //Check if King is "check"ed by current move
            Square[] nextPossibleSquares = thinkTank.findPossibleMoves(square[moveToSquare.row][moveToSquare.column], square);

            for (int i = 0; i < nextPossibleSquares.length; i++) {
                if (square[nextPossibleSquares[i].row][nextPossibleSquares[i].column].occupied && "King".equals(square[nextPossibleSquares[i].row][nextPossibleSquares[i].column].piece) && !square[nextPossibleSquares[i].row][nextPossibleSquares[i].column].pieceColor.equals(moveToSquare.pieceColor)) {
                    kingInCheck = true;
                    checkedSquare.row = nextPossibleSquares[i].row;
                    checkedSquare.column = nextPossibleSquares[i].column;
                    checkedSquare.pieceColor = square[nextPossibleSquares[i].row][nextPossibleSquares[i].column].pieceColor;
                    checkCauseSquare.row = moveToSquare.row;
                    checkCauseSquare.column = moveToSquare.column;
                    checkCauseSquare.piece = square[moveToSquare.row][moveToSquare.column].piece;
                }
            }
            if (turn.equals(whiteTurn)) {
                turn = blackTurn;
            } else {
                turn = whiteTurn;
            }
        }
        drawAround();
    }

    //Method for redrawing the chessboard efficiently (reDraw only affected squares)
    public void drawAround() {
        square[rn][cn].highlighted = false;
        square[r][c].highlighted = false;
        b[selectedSquare.row][selectedSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[selectedSquare.row][selectedSquare.column].pieceColor + square[selectedSquare.row][selectedSquare.column].piece + square[selectedSquare.row][selectedSquare.column].boardColor + ".jpg", 109, 109).getIcon());
        b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());


        for (int i = 0; i < possibleSquare.length; i++) {
            square[possibleSquare[i].row][possibleSquare[i].column].highlighted = false;
            b[possibleSquare[i].row][possibleSquare[i].column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[possibleSquare[i].row][possibleSquare[i].column].pieceColor + square[possibleSquare[i].row][possibleSquare[i].column].piece + square[possibleSquare[i].row][possibleSquare[i].column].boardColor + ".jpg", 109, 109).getIcon());
        }
        if (selectedSquare.column < 7) {
            b[selectedSquare.row][selectedSquare.column + 1].setIcon(new AImageIcon(UtilityBelt.resLocation + square[selectedSquare.row][selectedSquare.column + 1].pieceColor + square[selectedSquare.row][selectedSquare.column + 1].piece + square[selectedSquare.row][selectedSquare.column + 1].boardColor + ".jpg", 109, 109).getIcon());
        }
        if (selectedSquare.column > 0) {
            b[selectedSquare.row][selectedSquare.column - 1].setIcon(new AImageIcon(UtilityBelt.resLocation + square[selectedSquare.row][selectedSquare.column - 1].pieceColor + square[selectedSquare.row][selectedSquare.column - 1].piece + square[selectedSquare.row][selectedSquare.column - 1].boardColor + ".jpg", 109, 109).getIcon());
        }


        //Redraws Castling Squares just in case
        b[7][2].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][2].pieceColor + square[7][2].piece + square[7][2].boardColor + ".jpg", 109, 109).getIcon());
        b[7][6].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][6].pieceColor + square[7][6].piece + square[7][6].boardColor + ".jpg", 109, 109).getIcon());
        b[0][2].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][2].pieceColor + square[0][2].piece + square[0][2].boardColor + ".jpg", 109, 109).getIcon());
        b[0][6].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][6].pieceColor + square[0][6].piece + square[0][6].boardColor + ".jpg", 109, 109).getIcon());

        if (kingInCheck) {
            b[square[r][c].row][square[r][c].column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[r][c].pieceColor + square[r][c].piece + square[r][c].boardColor + ".jpg", 109, 109).getIcon());
            b[checkedSquare.row][checkedSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[checkedSquare.row][checkedSquare.column].pieceColor + square[checkedSquare.row][checkedSquare.column].piece + "Check.jpg", 109, 109).getIcon());
            b[square[rn][cn].row][square[rn][cn].column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[rn][cn].pieceColor + square[rn][cn].piece + square[rn][cn].boardColor + ".jpg", 109, 109).getIcon());

        } else {
            b[checkedSquare.row][checkedSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[checkedSquare.row][checkedSquare.column].pieceColor + square[checkedSquare.row][checkedSquare.column].piece + square[checkedSquare.row][checkedSquare.column].boardColor + ".jpg", 109, 109).getIcon());
            b[checkedSquare.row][checkedSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[checkedSquare.row][checkedSquare.column].pieceColor + square[checkedSquare.row][checkedSquare.column].piece + square[checkedSquare.row][checkedSquare.column].boardColor + ".jpg", 109, 109).getIcon());
            b[square[rn][cn].row][square[rn][cn].column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[rn][cn].pieceColor + square[rn][cn].piece + square[rn][cn].boardColor + ".jpg", 109, 109).getIcon());

        }
    }

    //Method used by handle move method
    public void move(Square source, Square Des) {

        if ("white".equals(selectedSquare.pieceColor) && "King".equals(selectedSquare.piece)) {
            whiteKingMoved = true;

        }
        if ("white".equals(selectedSquare.pieceColor) && "Rook".equals(selectedSquare.piece) && source.column == 0) {
            leftWhiteRookMoved = true;

        }

        if ("white".equals(selectedSquare.pieceColor) && "Rook".equals(selectedSquare.piece) && source.column == 7) {
            rightWhiteRookMoved = true;
        }
        if ("black".equals(selectedSquare.pieceColor) && "King".equals(selectedSquare.piece)) {
            blackKingMoved = true;
        }
        if ("black".equals(selectedSquare.pieceColor) && "Rook".equals(selectedSquare.piece) && source.column == 0) {
            leftBlackRookMoved = true;
        }
        if ("black".equals(selectedSquare.pieceColor) && "Rook".equals(selectedSquare.piece) && source.column == 7) {
            rightBlackRookMoved = true;
        }


        square[Des.row][Des.column].piece = source.piece;
        square[Des.row][Des.column].pieceColor = source.pieceColor;
        square[Des.row][Des.column].highlighted = false;


        if (source.jumpedToTaker && (moveToSquare.column == jumpedToSquare.column) && (source.column != moveToSquare.column)) {
            if ("white".equals(source.pieceColor)) {
                jumpedToSquare.piece = null;
                jumpedToSquare.pieceColor = null;
                jumpedToSquare.occupied = false;

            } else {
                jumpedToSquare.piece = null;
                jumpedToSquare.pieceColor = null;
                jumpedToSquare.occupied = false;
            }
        }



        jumpedToSquare.jumpedTo = false;

        //Handles Castling Move of Kings


        if ("King".equals(source.piece) && Math.abs(source.column - Des.column) == 2) {

            if (Des.row == 7 & Des.column == 2) {

                square[7][3].piece = "Rook";
                square[7][3].pieceColor = "white";
                square[7][3].occupied = true;

                square[7][0].piece = null;
                square[7][0].pieceColor = null;
                square[7][0].occupied = false;
                square[7][0].jumpedTo = false;

                b[7][3].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][3].pieceColor + square[7][3].piece + square[7][3].boardColor + ".jpg", 109, 109).getIcon());
                b[7][0].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][0].pieceColor + square[7][0].piece + square[7][0].boardColor + ".jpg", 109, 109).getIcon());

            }
            if (Des.row == 7 & Des.column == 6) {

                square[7][5].piece = "Rook";
                square[7][5].pieceColor = "white";
                square[7][5].occupied = true;

                square[7][7].piece = null;
                square[7][7].pieceColor = null;
                square[7][7].occupied = false;
                square[7][7].jumpedTo = false;

                b[7][5].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][5].pieceColor + square[7][5].piece + square[7][5].boardColor + ".jpg", 109, 109).getIcon());
                b[7][7].setIcon(new AImageIcon(UtilityBelt.resLocation + square[7][7].pieceColor + square[7][7].piece + square[7][7].boardColor + ".jpg", 109, 109).getIcon());

            }

            if (Des.row == 0 & Des.column == 2) {

                square[0][3].piece = "Rook";
                square[0][3].pieceColor = "black";
                square[0][3].occupied = true;

                square[0][0].piece = null;
                square[0][0].pieceColor = null;
                square[0][0].occupied = false;
                square[0][0].jumpedTo = false;

                b[0][3].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][3].pieceColor + square[0][3].piece + square[0][3].boardColor + ".jpg", 109, 109).getIcon());
                b[0][0].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][0].pieceColor + square[0][0].piece + square[0][0].boardColor + ".jpg", 109, 109).getIcon());

            }

            if (Des.row == 0 & Des.column == 6) {

                square[0][5].piece = "Rook";
                square[0][5].pieceColor = "black";
                square[0][5].occupied = true;

                square[0][7].piece = null;
                square[0][7].pieceColor = null;
                square[0][7].occupied = false;
                square[0][7].jumpedTo = false;

                b[0][5].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][5].pieceColor + square[0][5].piece + square[0][5].boardColor + ".jpg", 109, 109).getIcon());
                b[0][7].setIcon(new AImageIcon(UtilityBelt.resLocation + square[0][7].pieceColor + square[0][7].piece + square[0][7].boardColor + ".jpg", 109, 109).getIcon());

            }

        }
        if ("Pawn".equals(source.piece) && Math.abs(Des.row - source.row) == 2) {

            jumpedToSquare = square[Des.row][Des.column];


            if (moveToSquare.column > 0 && square[Des.row][Des.column - 1].occupied) {
                jumpedToTakerSquare = square[Des.row][Des.column - 1];
                jumpedToTakerSquare.jumpedToTaker = true;
            }
            if (moveToSquare.column < 7 && square[Des.row][Des.column + 1].occupied) {
                jumpedToTakerSquare = square[Des.row][Des.column + 1];
                jumpedToTakerSquare.jumpedToTaker = true;
            }

            if (Des.column < 7 && "Pawn".equals(square[Des.row][Des.column + 1].piece)) {
                square[Des.row][Des.column].jumpedTo = true;
            }
            if (Des.column > 0 && "Pawn".equals(square[Des.row][Des.column - 1].piece)) {
                square[Des.row][Des.column].jumpedTo = true;
            }
        } else {
            square[Des.row][Des.column].jumpedTo = false;
        }

        if ("Pawn".equals(source.piece)) {

            ChoiceButtonsHandlerBlack handler3 = new ChoiceButtonsHandlerBlack();
            ChoiceButtonsHandlerWhite handler2 = new ChoiceButtonsHandlerWhite();

            if ("white".equals(source.pieceColor)) {

                if (Des.row == 0) {


                    choiceFrame.setSize(400, 400);
                    choiceFrame.setLayout(new GridLayout(2, 2));

                    rookW.setIcon(new AImageIcon(UtilityBelt.resLocation + "whiteRook" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    knightW.setIcon(new AImageIcon(UtilityBelt.resLocation + "whiteKnight" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    bishopW.setIcon(new AImageIcon(UtilityBelt.resLocation + "whiteBishop" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    queenW.setIcon(new AImageIcon(UtilityBelt.resLocation + "whiteQueen" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());


                    rookW.addActionListener(handler2);
                    knightW.addActionListener(handler2);
                    bishopW.addActionListener(handler2);
                    queenW.addActionListener(handler2);

//
//                    rookB.removeActionListener(handler3);
//                    knightB.removeActionListener(handler3);
//                    bishopB.removeActionListener(handler3);
//                    queenB.removeActionListener(handler3);

                    Color background = new Color(2, 31, 55);
                    rookW.setBackground(background);
                    knightW.setBackground(background);
                    bishopW.setBackground(background);
                    queenW.setBackground(background);

                    choiceFrame.remove(rookB);
                    choiceFrame.remove(knightB);
                    choiceFrame.remove(bishopB);
                    choiceFrame.remove(queenB);


                    choiceFrame.add(rookW);
                    choiceFrame.add(knightW);
                    choiceFrame.add(bishopW);
                    choiceFrame.add(queenW);



                    choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    choiceFrame.setLocationRelativeTo(this);
                    choiceFrame.setVisible(true);
                }
                if (source.column < 7) {
                    if (square[source.row][source.column + 1].jumpedTo && Des.column == source.column + 1 && !square[source.row - 1][source.column + 1].occupied) {
                        square[source.row][source.column + 1].piece = null;
                        square[source.row][source.column + 1].pieceColor = null;
                        square[source.row][source.column + 1].occupied = false;
                        square[source.row][source.column + 1].jumpedTo = false;

                    }
                }
                if (source.column > 0) {
                    if (square[source.row][source.column - 1].jumpedTo && Des.column == source.column - 1 && !square[source.row - 1][source.column - 1].occupied) {
                        square[source.row][source.column - 1].piece = null;
                        square[source.row][source.column - 1].pieceColor = null;
                        square[source.row][source.column - 1].occupied = false;
                        square[source.row][source.column - 1].jumpedTo = false;

                    }
                }
            }
            if ("black".equals(source.pieceColor)) {

                if (Des.row == 7) {




                    choiceFrame.setSize(400, 400);
                    choiceFrame.setLayout(new GridLayout(2, 2));



                    rookB.setIcon(new AImageIcon(UtilityBelt.resLocation + "blackRook" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    knightB.setIcon(new AImageIcon(UtilityBelt.resLocation + "blackKnight" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    bishopB.setIcon(new AImageIcon(UtilityBelt.resLocation + "blackBishop" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());
                    queenB.setIcon(new AImageIcon(UtilityBelt.resLocation + "blackQueen" + square[Des.row][Des.column].boardColor + ".jpg", 150, 150).getIcon());

//                    rookB.removeActionListener(handler2);
//                    knightB.removeActionListener(handler2);
//                    bishopB.removeActionListener(handler2);
//                    queenB.removeActionListener(handler2);

                    rookB.addActionListener(handler3);
                    knightB.addActionListener(handler3);
                    bishopB.addActionListener(handler3);
                    queenB.addActionListener(handler3);


                    Color background = new Color(2, 31, 55);
                    rookB.setBackground(background);
                    knightB.setBackground(background);
                    bishopB.setBackground(background);
                    queenB.setBackground(background);

                    choiceFrame.remove(rookW);
                    choiceFrame.remove(knightW);
                    choiceFrame.remove(bishopW);
                    choiceFrame.remove(queenW);

                    choiceFrame.add(rookB);
                    choiceFrame.add(knightB);
                    choiceFrame.add(bishopB);
                    choiceFrame.add(queenB);


                    choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    choiceFrame.setLocationRelativeTo(this);
                    choiceFrame.setVisible(true);
                }

                if (source.column < 7) {
                    if (square[source.row][source.column + 1].jumpedTo && square[Des.row][Des.column].column == source.column + 1 && !square[source.row + 1][source.column + 1].occupied) {

                        square[source.row][source.column + 1].piece = null;
                        square[source.row][source.column + 1].pieceColor = null;
                        square[source.row][source.column + 1].occupied = false;
                        square[source.row][source.column + 1].jumpedTo = false;

                    }
                }
                if (source.column > 0) {
                    if (square[source.row][source.column - 1].jumpedTo && Des.column == source.column - 1 && !square[source.row + 1][source.column - 1].occupied) {
                        square[source.row][source.column - 1].piece = null;
                        square[source.row][source.column - 1].pieceColor = null;
                        square[source.row][source.column - 1].occupied = false;
                        square[source.row][source.column - 1].jumpedTo = false;


                    }
                }
            }

        }

        square[Des.row][Des.column].occupied = true;

        source.piece = null;
        source.pieceColor = null;
        source.jumpedTo = false;
        source.occupied = false;
    }

    //Class for handling the pawn reaches row end scenario
    public class ChoiceButtonsHandlerWhite implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == rookW) {

                square[moveToSquare.row][moveToSquare.column].piece = "Rook";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "white";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();

            }

            if (e.getSource() == knightW) {

                square[moveToSquare.row][moveToSquare.column].piece = "Knight";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "white";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
            if (e.getSource() == bishopW) {

                square[moveToSquare.row][moveToSquare.column].piece = "Bishop";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "white";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
            if (e.getSource() == queenW) {

                square[moveToSquare.row][moveToSquare.column].piece = "Queen";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "white";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
        }
    }

    //Class for handling the pawn reaches row end scenario
    public class ChoiceButtonsHandlerBlack implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == rookB) {

                square[moveToSquare.row][moveToSquare.column].piece = "Rook";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "black";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();

            }

            if (e.getSource() == knightB) {

                square[moveToSquare.row][moveToSquare.column].piece = "Knight";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "black";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
            if (e.getSource() == bishopB) {

                square[moveToSquare.row][moveToSquare.column].piece = "Bishop";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "black";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
            if (e.getSource() == queenB) {

                square[moveToSquare.row][moveToSquare.column].piece = "Queen";
                square[moveToSquare.row][moveToSquare.column].pieceColor = "black";
                square[moveToSquare.row][moveToSquare.column].occupied = true;

                b[moveToSquare.row][moveToSquare.column].setIcon(new AImageIcon(UtilityBelt.resLocation + square[moveToSquare.row][moveToSquare.column].pieceColor + square[moveToSquare.row][moveToSquare.column].piece + square[moveToSquare.row][moveToSquare.column].boardColor + ".jpg", 109, 109).getIcon());

                choiceFrame.dispose();
            }
        }
    }

    public boolean checkHandler() {
        boolean returnValue = false;
        if ("King".equals(selectedSquare.piece)) {
            returnValue = true;
        } else {

            Square[] nextpossibleSquares = thinkTank.findPossibleMoves(selectedSquare, square);


            for (int i = 0; i < nextpossibleSquares.length; i++) {

                if (nextpossibleSquares[i].row == checkCauseSquare.row && nextpossibleSquares[i].column == checkCauseSquare.column) {
                    thereIsAPossmove = true;
                    Highlighting(selectedSquare, square[checkCauseSquare.row][checkCauseSquare.column]);
                    square[checkCauseSquare.row][checkCauseSquare.column].highlighted = true;
                }
            }
//           
            if (coversKing(nextpossibleSquares)) {
                Highlighting(selectedSquare, square[r][c]);
                square[r][c].highlighted = true;
                thereIsAPossmove = true;
            }
        }
        return returnValue;
    }

    boolean coversKing(Square[] nextpossibleSquares) {
        boolean returnValue = false;

        for (int i = 0; i < nextpossibleSquares.length; i++) {
            int row = nextpossibleSquares[i].row, column = nextpossibleSquares[i].column;

            if (square[row][column].occupied && square[row][column].pieceColor.equals(selectedSquare.pieceColor)) {
                continue;
            }
            boolean save = square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].occupied;

            String sPiece = square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].piece;
            String sPieceColor = square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].pieceColor;

            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].occupied = true;

            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].pieceColor = selectedSquare.pieceColor;
            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].piece = selectedSquare.piece;

            boolean kingFound = false;
            Square[] checkCauserPossibles = thinkTank.findPossibleMoves(checkCauseSquare, square);




            for (int j = 0; j < checkCauserPossibles.length; j++) {

                if (square[checkCauserPossibles[j].row][checkCauserPossibles[j].column].occupied && square[checkCauserPossibles[j].row][checkCauserPossibles[j].column].pieceColor.equals(selectedSquare.pieceColor) && "King".equals(square[checkCauserPossibles[j].row][checkCauserPossibles[j].column].piece)) {
                    kingFound = true;
                    break;
                }
            }

            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].occupied = save;
            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].piece = sPiece;
            square[nextpossibleSquares[i].row][nextpossibleSquares[i].column].pieceColor = sPieceColor;

            if (!kingFound) {
                otherPossibleSquare = new Square[1];
                returnValue = true;
                r = nextpossibleSquares[i].row;
                c = nextpossibleSquares[i].column;
                possibleSquare[0] = square[r][c];

                break;
            }
        }

        return returnValue;
    }

    public boolean checkIfKingFound(String pieceColor, String piece) {
        boolean returnValue = false;
        //Going to be passsed as current square to find possible moves of current square
        Square newSquare = new Square();
        newSquare.pieceColor = pieceColor;
        newSquare.occupied = true;
        newSquare.row = selectedSquare.row;
        newSquare.column = selectedSquare.column;
        newSquare.piece = piece;

        Square[] currentPoss = thinkTank.findPossibleMoves(newSquare, square);
//        for (Square uff : currentPoss) {
//        }
        int kingFoundCount = 0;
        int trueCurrPossLength = 0;
        boolean kingFound = false;
        for (int x = 0; x < currentPoss.length; x++) {

            boolean breakCr = false;
            int row = currentPoss[x].row;
            int col = currentPoss[x].column;
            
            System.out.printf("%s %s\n", row,col);
            if (square[row][col].occupied && square[row][col].pieceColor.equals(pieceColor)) {
                continue;
            }
            System.out.printf("True Move\n");
            trueCurrPossLength++;
            String sPiece = square[row][col].piece;
            String sPieceColor = square[row][col].pieceColor;
            boolean sOccupied = square[row][col].occupied;

            square[row][col].piece = piece;
            square[row][col].occupied = true;
            square[row][col].pieceColor = pieceColor;

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    kingFound = false;
                    if (square[i][j].occupied && !square[i][j].pieceColor.equals(pieceColor)) {

                        Square[] poss = thinkTank.findPossibleMoves(square[i][j], square);

                        for (int z = 0; z < poss.length; z++) {
                            if ("King".equals(square[poss[z].row][poss[z].column].piece) && square[poss[z].row][poss[z].column].pieceColor.equals(pieceColor)) {

                                rn = i;
                                cn = j;
                                otherPossibleSquare = new Square[1];
                                otherPossibleSquare[0] = square[rn][cn];
                                kingFound = true;
                                breakCr = true;
                                break;
                            }
                        }
                        if (breakCr) {
                            break;
                        }
                    }
                }
                if (breakCr) {
                    break;
                }
            }
            square[row][col].piece = sPiece;
            square[row][col].occupied = sOccupied;
            square[row][col].pieceColor = sPieceColor;
            if (kingFound) {
                kingFoundCount++;
            }
        }


        System.out.printf("True Current possible length = %s, kingFoundCount= %s\n", trueCurrPossLength, kingFoundCount);
        if (kingFoundCount == trueCurrPossLength) {
            returnValue = true;
            dangerunless = false;
        }
        if (kingFoundCount == trueCurrPossLength - 1) {
            dangerunless = true;
        }
        return returnValue;
    }
}