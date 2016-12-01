/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author DaveScof
 */
public class ThinkTank {

    Square[] possibleSquares;
    boolean first = true;
    int noPossibleMoves;
    List<Integer> moveList;

   
    public Square[] findPossibleMoves(Square selectedSquare, Square[][] square) {
        //Initializing the variables to return 0 and size 0 if no valid move;

        noPossibleMoves = 0;
        switch (selectedSquare.piece) {
            case "Pawn": {
                if ("white".equals(selectedSquare.pieceColor)) {
                    whitePawnPossibleMoves(selectedSquare, square);
                } else {
                    blackPawnPossibleMoves(selectedSquare, square);
                }
                break;
            }
            case "Rook": {
                rookPossibleMoves(selectedSquare, square);
                break;
            }
            case "Knight": {
                knightPossibleMoves(selectedSquare, square);
                break;
            }
            case "Bishop": {
                bishopPossibleMoves(selectedSquare, square);
                break;
            }
            case "Queen": {
                queenPossibleMoves(selectedSquare, square);
                break;
            }
            case "King": {

                kingPossibleMoves(selectedSquare, square);
                break;
            }
            default:
                break;
        }

        return possibleSquares;
    }

    private Square[] whitePawnPossibleMoves(Square selectedSquare, Square[][] square) {

        int diagonalLeft = 0;
        int diagonalRight = 0;
        int jumpLeft = 0;
        int jumpRight = 0;


        if (selectedSquare.row == 6) {
            for (int i = 5; i > 3; i--) {

                if (square[i][selectedSquare.column].occupied == false) {
                    noPossibleMoves++;
                } else {
                    break;
                }
            }

        } else {
            if (square[selectedSquare.row - 1][selectedSquare.column].occupied == false) {
                noPossibleMoves++;
            }
        }

        if (selectedSquare.column > 0 && selectedSquare.column < 7) {

            if ("black".equals(square[selectedSquare.row - 1][selectedSquare.column - 1].pieceColor)) {
                diagonalLeft++;
            }
            if ("black".equals(square[selectedSquare.row - 1][selectedSquare.column + 1].pieceColor)) {
                diagonalRight++;
            }
        } else {
            if (selectedSquare.column == 0) {
                if ("black".equals(square[selectedSquare.row - 1][selectedSquare.column + 1].pieceColor)) {
                    diagonalRight++;
                }
            } else {
                if ("black".equals(square[selectedSquare.row - 1][selectedSquare.column - 1].pieceColor)) {
                    diagonalLeft++;
                }
            }
        }

        if (selectedSquare.row == 3) {

            if (selectedSquare.column > 0 && selectedSquare.column < 7) {


                if ("black".equals(square[3][selectedSquare.column - 1].pieceColor) && square[3][selectedSquare.column - 1].jumpedTo) {
                    jumpLeft++;
                }
                if ("black".equals(square[3][selectedSquare.column + 1].pieceColor) && square[3][selectedSquare.column + 1].jumpedTo) {
                    jumpRight++;
                }
            } else {
                if (selectedSquare.column == 0) {
                    if ("black".equals(square[3][selectedSquare.column + 1].pieceColor) && square[3][selectedSquare.column + 1].jumpedTo) {
                        jumpRight++;
                    }
                } else {
                    if ("black".equals(square[3][selectedSquare.column - 1].pieceColor) && square[3][selectedSquare.column - 1].jumpedTo) {
                        jumpLeft++;
                    }
                }
            }
        }

        // Reinitializing possibleSquares array

        possibleSquares = new Square[noPossibleMoves + diagonalRight + diagonalLeft + jumpLeft + jumpRight];

        if (noPossibleMoves != 0) {

            for (int i = 0; i < noPossibleMoves; i++) {
                possibleSquares[i] = new Square();
                possibleSquares[i].row = selectedSquare.row - i - 1;
                possibleSquares[i].column = selectedSquare.column;
            }
        }
        if (diagonalLeft != 0) {

            possibleSquares[noPossibleMoves] = new Square();
            possibleSquares[noPossibleMoves].row = selectedSquare.row - 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column - 1;

            noPossibleMoves++;

        }
        if (diagonalRight != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row - 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column + 1;

            noPossibleMoves++;

        }

        if (jumpRight != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row - 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column + 1;

            noPossibleMoves++;
        }

        if (jumpLeft != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row - 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column - 1;

            noPossibleMoves++;
        }

        return possibleSquares;
    }

    private Square[] blackPawnPossibleMoves(Square selectedSquare, Square[][] square) {

        int diagonalLeft = 0;
        int diagonalRight = 0;
        int jumpLeft = 0;
        int jumpRight = 0;

        if (selectedSquare.row == 1) {
            for (int i = 2; i < 4; i++) {

                if (square[i][selectedSquare.column].occupied == false) {
                    noPossibleMoves++;
                } else {
                    break;
                }
            }

        } else {
            if (square[selectedSquare.row + 1][selectedSquare.column].occupied == false) {
                noPossibleMoves++;
            }
        }

        if (selectedSquare.column > 0 && selectedSquare.column < 7) {

            if ("white".equals(square[selectedSquare.row + 1][selectedSquare.column - 1].pieceColor)) {
                diagonalLeft++;
            }
            if ("white".equals(square[selectedSquare.row + 1][selectedSquare.column + 1].pieceColor)) {
                diagonalRight++;
            }


        } else {
            if (selectedSquare.column == 0) {
                if ("white".equals(square[selectedSquare.row + 1][selectedSquare.column + 1].pieceColor)) {
                    diagonalRight++;
                }
            } else {
                if ("white".equals(square[selectedSquare.row + 1][selectedSquare.column - 1].pieceColor)) {
                    diagonalLeft++;
                }
            }
        }

        if (selectedSquare.row == 4) {

            if (selectedSquare.column > 0 && selectedSquare.column < 7) {

                if ("white".equals(square[4][selectedSquare.column - 1].pieceColor) && square[4][selectedSquare.column - 1].jumpedTo) {
                    jumpLeft++;
                }
                if ("white".equals(square[4][selectedSquare.column + 1].pieceColor) && square[4][selectedSquare.column + 1].jumpedTo) {
                    jumpRight++;
                }
            } else {
                if (selectedSquare.column == 0) {
                    if ("white".equals(square[4][selectedSquare.column + 1].pieceColor) && square[4][selectedSquare.column + 1].jumpedTo) {
                        jumpRight++;
                    }
                } else {
                    if ("white".equals(square[4][selectedSquare.column - 1].pieceColor) && square[4][selectedSquare.column - 1].jumpedTo) {
                        jumpLeft++;
                    }
                }
            }
        }

        // Reinitializing possibleSquares array
        possibleSquares = new Square[noPossibleMoves + diagonalRight + diagonalLeft + jumpLeft + jumpRight];
        if (noPossibleMoves != 0) {

            for (int i = 0; i < noPossibleMoves; i++) {
                possibleSquares[i] = new Square();
                possibleSquares[i].row = selectedSquare.row + i + 1;
                possibleSquares[i].column = selectedSquare.column;
            }
        }
        if (diagonalLeft != 0) {

            possibleSquares[noPossibleMoves] = new Square();
            possibleSquares[noPossibleMoves].row = selectedSquare.row + 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column - 1;

            noPossibleMoves++;

        }
        if (diagonalRight != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row + 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column + 1;

            noPossibleMoves++;
        }

        if (jumpRight != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row + 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column + 1;

            noPossibleMoves++;
        }

        if (jumpLeft != 0) {

            possibleSquares[noPossibleMoves] = new Square();

            possibleSquares[noPossibleMoves].row = selectedSquare.row + 1;
            possibleSquares[noPossibleMoves].column = selectedSquare.column - 1;

            noPossibleMoves++;
        }

        return possibleSquares;
    }

    private Square[] rookPossibleMoves(Square selectedSquare, Square[][] square) {
        moveList = new ArrayList<>();
        // Iteration for the all possible rows larger than the one given while on the same column
        for (int r = selectedSquare.row + 1; r < 8; r++) {   // Validating moves to iterated squares when they are unoccupied 
            if (square[r][selectedSquare.column].occupied == false) {
                moveList.add(r * 10 + selectedSquare.column);
            } else {
                // When the iterated square is occupied blocking further iteration, the following code validates  
                //this particular iterated square if it is occupied by an opponent
                moveList.add(r * 10 + selectedSquare.column);
                break;
            }
        }
        // Iteration for the all possible rows lower than the one given while on the same column
        for (int r = selectedSquare.row - 1; r >= 0; r--) {   // Validating moves to iterated squares when they are unoccupied 
            if (square[r][selectedSquare.column].occupied == false) {
                moveList.add(r * 10 + selectedSquare.column);
            } else {
                // When the iterated square is occupied blocking further iteration, the following code validates  
                //this particular iterated square if it is occupied by an opponent
                moveList.add(r * 10 + selectedSquare.column);
                break;
            }

        }
        // Iteration for the all possible columns larger than the one given, while on the same row
        for (int c = selectedSquare.column + 1; c < 8; c++) {
            // Validating moves to iterated squares when they are unoccupied 
            if (square[selectedSquare.row][c].occupied == false) {
                moveList.add(selectedSquare.row * 10 + c);
            } else {
                // When the iterated square is occupied blocking further iteration, the following code validates  
                //this particular iterated square if it is occupied by an opponent
                moveList.add(selectedSquare.row * 10 + c);
                break;
            }
        }
        // Iteration for the all possible columns lower than the one given, while on the same row
        for (int c = selectedSquare.column - 1; c >= 0; c--) {
            // Validating moves to iterated squares when they are unoccupied 
            if (square[selectedSquare.row][c].occupied == false) {
                moveList.add(selectedSquare.row * 10 + c);
            } else {
                // When the iterated square is occupied blocking further iteration, the following code validates  
                //this particular iterated square if it is occupied by an opponent
                moveList.add(selectedSquare.row * 10 + c);
                break;
            }
        }


        //Creates a simple array for the Integer ArrayList
        int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) {
            list[i] = iterator.next().intValue();
        }
        //Isolates possible rows and columns and asserts that to the iterated objects
        int[] ro = new int[list.length];
        int[] column = new int[list.length];
        possibleSquares = new Square[list.length];

        for (int i = 0; i < list.length; i++) {
            ro[i] = list[i] / 10;
            column[i] = list[i] % 10;
            possibleSquares[i] = new Square();
            possibleSquares[i].row = ro[i];
            possibleSquares[i].column = column[i];
        }
        return possibleSquares;


    }

    private Square[] knightPossibleMoves(Square selectedSquare, Square[][] square) {
        moveList = new ArrayList<>();
        int row = selectedSquare.row;
        int col = selectedSquare.column;
        //Conditions to be met for a knight's valid moves
        // No blocked conditions for a knight
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (((Math.abs(row - r) == 1 && Math.abs(col - c) == 2) || (Math.abs(row - r) == 2 && Math.abs(col - c) == 1)) /*&& square[r][c].pieceColor != square[row][col].pieceColor*/) {
                    moveList.add((r * 10) + c);
                }
            }
        }

        //Creates a simple array for the Integer ArrayList
        int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) {
            list[i] = iterator.next().intValue();

        }
        // Isolates possible rows and columns of the new array and uses that to initialize the iterated objects
        int[] ro = new int[list.length];
        int[] column = new int[list.length];

        possibleSquares = new Square[list.length];

        for (int i = 0; i < list.length; i++) {
            ro[i] = list[i] / 10;
            column[i] = list[i] % 10;
            possibleSquares[i] = new Square();
            possibleSquares[i].row = ro[i];
            possibleSquares[i].column = column[i];
        }

        return possibleSquares;
    }

    private Square[] bishopPossibleMoves(Square selectedSquare, Square[][] square) {

        moveList = new ArrayList<>();

        // Iteration for the Lower Right diagonal move(both row and column increasing by the same amount)
        for (int i = 1; i < 8; i++) {  // Breaks out of the for loop when a lower right diagonal move is no longer available 
            if (selectedSquare.row + i == 8 || selectedSquare.column + i == 8) {
                break;
            } else {
                // Validating moves to iterated squares when they are unoccupied
                if (square[selectedSquare.row + i][selectedSquare.column + i].occupied == false) {
                    moveList.add((selectedSquare.row + i) * 10 + (selectedSquare.column + i));
                } else { // When the iterated square is occupied blocking further iteration, the following code validates  
                    //this particular iterated square if it is occupied by an opponent
                    moveList.add((selectedSquare.row + i) * 10 + (selectedSquare.column + i));
                    break;
                }
            }
        }
        // Iteration for the Lower Left diagonal move
        for (int i = 1; i < 8; i++) {  // Breaks out of the for loop when a lower left diagonal move is no longer available
            if (selectedSquare.row + i == 8 || selectedSquare.column - i + 1 == 0) {
                break;
            } else {
                // Validating moves to iterated squares when they are unoccupied
                if (square[selectedSquare.row + i][selectedSquare.column - i].occupied == false) {
                    moveList.add((selectedSquare.row + i) * 10 + (selectedSquare.column - i));
                } else {   // When the iterated square is occupied blocking further iteration, the following code validates  
                    //this particular iterated square if it is occupied by an opponent
                    moveList.add((selectedSquare.row + i) * 10 + (selectedSquare.column - i));
                    break;
                }
            }
        }
        // Iteration for the Upper Left diagonal move
        for (int i = 1; i < 8; i++) {   // Breaks out of the for loop when a Upper right diagonal move is no longer available
            if (selectedSquare.row - i + 1 == 0 || selectedSquare.column - i + 1 == 0) {
                break;
            } else {
                // Validating moves to iterated squares when they are unoccupied
                if (square[selectedSquare.row - i][selectedSquare.column - i].occupied == false) {
                    moveList.add((selectedSquare.row - i) * 10 + (selectedSquare.column - i));
                } else {   // When the iterated square is occupied blocking further iteration, the following code validates  
                    //this particular iterated square if it is occupied by an opponent
                    moveList.add((selectedSquare.row - i) * 10 + (selectedSquare.column - i));
                    break;
                }
            }
        } // Iteration for the Upper Right diagonal move
        for (int i = 1; i < 8; i++) {
            if (selectedSquare.row - i + 1 == 0 || selectedSquare.column + i == 8) {
                break;
            } else {
                // Validating moves to iterated squares when they are unoccupied
                if (square[selectedSquare.row - i][selectedSquare.column + i].occupied == false) {
                    moveList.add((selectedSquare.row - i) * 10 + (selectedSquare.column + i));
                } else {   // When the iterated square is occupied blocking further iteration, the following code validates  
                    //this particular iterated square if it is occupied by an opponent
                    moveList.add((selectedSquare.row - i) * 10 + (selectedSquare.column + i));
                    break;
                }
            }
        }
        //Creating a simple array for the Integer ArrayList
        int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) {
            list[i] = iterator.next().intValue();

        }
        // Isolating possible rows and columns of the new array and using that to initialize the iterated objects
        int[] ro = new int[list.length];
        int[] column = new int[list.length];
        possibleSquares = new Square[list.length];
        for (int i = 0; i < list.length; i++) {
            ro[i] = list[i] / 10;
            column[i] = list[i] % 10;
            possibleSquares[i] = new Square();
            possibleSquares[i].row = ro[i];
            possibleSquares[i].column = column[i];
        }
        return possibleSquares;

    }

    private Square[] queenPossibleMoves(Square selectedSquare, Square[][] square) {


        List<Integer> moveListQueen = new ArrayList<>();


        //Validating the same valid moves of a rook from the given selected square location

        for (int i = 0; i < rookPossibleMoves(selectedSquare, square).length; i++) {
            moveListQueen.add((rookPossibleMoves(selectedSquare, square)[i].row) * 10 + (rookPossibleMoves(selectedSquare, square)[i].column));

        }
        //Validating the same valid moves of a bishop from the given selected square location
        for (int iterate = 0; iterate < bishopPossibleMoves(selectedSquare, square).length; iterate++) {
            moveListQueen.add((bishopPossibleMoves(selectedSquare, square)[iterate].row) * 10 + (bishopPossibleMoves(selectedSquare, square)[iterate].column));
        }

        //Creating a simple array for the Integer ArrayList
        int[] list = new int[moveListQueen.size()];
        Iterator<Integer> iterator = moveListQueen.iterator();
        for (int i = 0; i < list.length; i++) {
            list[i] = iterator.next().intValue();

        }

        // Isolating possible rows and columns of the new array and using that to initialize the iterated objects
        int[] ro = new int[list.length];
        int[] column = new int[list.length];
        possibleSquares = new Square[list.length];
        for (int i = 0; i < list.length; i++) {
            ro[i] = list[i] / 10;
            column[i] = list[i] % 10;
            possibleSquares[i] = new Square();
            possibleSquares[i].row = ro[i];
            possibleSquares[i].column = column[i];
        }
        return possibleSquares;
    }

    private Square[] kingPossibleMoves(Square selectedSquare, Square[][] square) {


        List<Square> dangerSquaresList = new ArrayList<>();

        int row = selectedSquare.row;
        int col = selectedSquare.column;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (square[r][c].occupied == true && !square[r][c].pieceColor.equals(square[selectedSquare.row][selectedSquare.column].pieceColor) && !"King".equals(square[r][c].piece) && !"Pawn".equals(square[r][c].piece)) {

                    Square obj;
                    obj = square[r][c];
                    Square[] poss = findPossibleMoves(obj, square);

                    dangerSquaresList.addAll(Arrays.asList(poss));

                }

                if ((square[r][c].occupied == true) && (c > 0) && (c < 7) && (!square[r][c].pieceColor.equals(square[selectedSquare.row][selectedSquare.column].pieceColor))) {
                    if ("Pawn".equals(square[r][c].piece) && "white".equals(square[r][c].pieceColor)) {
                        dangerSquaresList.add(square[r - 1][c - 1]);
                        dangerSquaresList.add(square[r - 1][c + 1]);
                    }
                    if ("Pawn".equals(square[r][c].piece) && "black".equals(square[r][c].pieceColor)) {
                        dangerSquaresList.add(square[r + 1][c - 1]);
                        dangerSquaresList.add(square[r + 1][c + 1]);
                    }
                }

                if (c == 0 && (square[r][c].occupied == true) && (c > 0) && (!square[r][c].pieceColor.equals(square[selectedSquare.row][selectedSquare.column].pieceColor))) {
                    if ("Pawn".equals(square[r][c].piece) && "white".equals(square[r][c].pieceColor)) {

                        dangerSquaresList.add(square[r - 1][c + 1]);
                    }
                    if ("Pawn".equals(square[r][c].piece) && "black".equals(square[r][c].pieceColor)) {
                        dangerSquaresList.add(square[r + 1][c + 1]);
                    }
                }
                if (c == 7 && (square[r][c].occupied == true) && (c > 0) && (!square[r][c].pieceColor.equals(square[selectedSquare.row][selectedSquare.column].pieceColor))) {
                    if ("Pawn".equals(square[r][c].piece) && "white".equals(square[r][c].pieceColor)) {

                        dangerSquaresList.add(square[r - 1][c - 1]);
                    }
                    if ("Pawn".equals(square[r][c].piece) && "black".equals(square[r][c].pieceColor)) {
                        dangerSquaresList.add(square[r + 1][c - 1]);
                    }
                }

                if (first) {

                    if (square[r][c].occupied == true && !square[r][c].pieceColor.equals(square[selectedSquare.row][selectedSquare.column].pieceColor) && "King".equals(square[r][c].piece)) {
                        first = false;
                        Square obj;
                        obj = square[r][c];
                        Square[] poss = kingPossibleMoves(obj, square);


                        dangerSquaresList.addAll(Arrays.asList(poss));
                    }

                }

            }
        }
        first = true;

        Square[] dangerSquares = dangerSquaresList.toArray(new Square[dangerSquaresList.size()]);


        Square[] possSq;
        possSq = kingMoves(dangerSquares, selectedSquare, square);
        possibleSquares = kingMoves(dangerSquares, selectedSquare, square);

        return possSq;
    }

    public Square[] kingMoves(Square[] dangerSquares, Square selectedSquare, Square[][] square) {

        Square[] kingMoves;

        List<Integer> noPossMoves = new ArrayList<>();
        int row = selectedSquare.row;
        int col = selectedSquare.column;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                int sum = 0;
                if ((Math.abs(row - r) == 1 && c == col) || (Math.abs(col - c) == 1 && r == row) || ((Math.abs(row - r) == 1) && (Math.abs(col - c) == 1))) {

                    for (int n = 0; n < dangerSquares.length; n++) {
                        if (r == dangerSquares[n].row && c == dangerSquares[n].column) {
                            sum += 1;
                        }
                    }
                    if (sum == 0) {
                        noPossMoves.add(r * 10 + c);
                    }
                }

            }
        }

        int[] list1 = new int[noPossMoves.size()];

        Iterator<Integer> iterator = noPossMoves.iterator();

        for (int i = 0; i < list1.length; i++) {
            list1[i] = iterator.next().intValue();
        }

        int[] ro = new int[list1.length];

        int[] column = new int[list1.length];

        kingMoves = new Square[list1.length];

        for (int i = 0; i < list1.length; i++) {
            ro[i] = list1[i] / 10;
            column[i] = list1[i] % 10;
            kingMoves[i] = new Square();
            kingMoves[i].row = ro[i];
            kingMoves[i].column = column[i];
        }

        return kingMoves;
    }
}
