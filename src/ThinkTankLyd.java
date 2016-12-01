/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author DaveScof
 */
public class ThinkTankLyd {

    Square[] possibleSquares;
    int noPossibleMoves;
    
     List<Integer> moveList = new ArrayList<Integer>();
     

    public Square[] findPossibleMoves(Square selectedSquare, Square[][] square) {

        //Initializing the variables to return 0 and size 0 if no valid move;
        possibleSquares = new Square[0];
        noPossibleMoves = 0;
     
        switch (selectedSquare.piece) {
            case "Pawn":
                if ("white".equals(selectedSquare.pieceColor)) {
                    whitePawnPossibleMoves(selectedSquare, square);
                } else {
                    blackPawnPossibleMoves(selectedSquare, square);
                }
                break;
            case "Rook":
                rookPossibleMoves(selectedSquare, square);
                break;
            case "Knight":
                knightPossibleMoves(selectedSquare, square);
                break;
            case "Bishop":
                bishopPossibleMoves(selectedSquare, square);
                break;
            case "Queen":
                queenPossibleMoves(selectedSquare, square);
                break;
            case "King":
                kingPossibleMoves(selectedSquare, square);
                break;
            default:
                break;
        }
        return possibleSquares; 

        
    }

    private Square[] whitePawnPossibleMoves(Square selectedSquare, Square[][] square) {
       //Finding the valid moves for White pawn's first move 
        if (selectedSquare.row == 6) {
            for (int i = 5; i > 3; i--) {
                if (square[i][selectedSquare.column].occupied == false ) {
                    moveList.add((i * 10) + selectedSquare.column); 
                }
                else
                    break; 
            }
        }
        // Finding the valid moves for when it is not white pawn's first move
        else {
            if (square[selectedSquare.row - 1][selectedSquare.column].occupied == false) {
                moveList.add(((selectedSquare.row - 1) * 10) + selectedSquare.column); 
            }
            }
        // Confirming if the Immediate left diagonal move is valid for the White pawn
        if ( square[selectedSquare.row - 1][selectedSquare.column-1].occupied == true && square[selectedSquare.row - 1][selectedSquare.column-1].pieceColor == "Black") 
                            moveList.add((selectedSquare.row-1 )*10+(selectedSquare.column-1) );
                 // Confirming if the Immediate Right diagonal move is valid for the White pawn
        if ( square[selectedSquare.row - 1][selectedSquare.column+1].occupied == true && square[selectedSquare.row - 1][selectedSquare.column+1].pieceColor == "Black") 
                            moveList.add((selectedSquare.row-1 )*10+(selectedSquare.column+1) );

        // create a simple array from the ArrayList
         int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) 
            list[i] = iterator.next().intValue();
        // Isolate possible rows and columns of the new array and use that to initialize the iterated objects
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
                    return possibleSquares; 

    }

    private Square[] blackPawnPossibleMoves(Square selectedSquare, Square[][] square) {
       //Finding the valid moves for Black pawn's first move 
         if (selectedSquare.row == 2) {
            for (int i = 3; i < 5; i++) {
                if (square[i][selectedSquare.column].occupied == false ) {
                    moveList.add((i * 10) + selectedSquare.column); 
                }
                else
                     break; 
            }
        } 
        // Finding the valid moves for when it is not Black pawn's first move
         else {
            if (square[selectedSquare.row + 1][selectedSquare.column].occupied == false) {
                moveList.add(((selectedSquare.row + 1) * 10) + selectedSquare.column); 
            }
            }
         // Confirming if the Immediate left diagonal move is valid for the Black pawn
        if ( square[selectedSquare.row + 1][selectedSquare.column-1].occupied == true && square[selectedSquare.row +1][selectedSquare.column-1].pieceColor == "White") 
                            moveList.add((selectedSquare.row+1 )*10+(selectedSquare.column-1) );
                 // Confirming if the Immediate right diagonal move is valid for the Black pawn
        if ( square[selectedSquare.row + 1][selectedSquare.column+1].occupied == true && square[selectedSquare.row + 1][selectedSquare.column+1].pieceColor == "White") 
                            moveList.add((selectedSquare.row+1 )*10+(selectedSquare.column+1) );
            // Creates a simple array from the ArrayList
        int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) 
            list[i] = iterator.next().intValue();
        // Isolates possible rows and columns and asserts that to the iterated objects
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
                    return possibleSquares; 
        
    }

    private Square[] rookPossibleMoves(Square selectedSquare, Square[][] square) {
        // Iteration for the all possible rows larger than the one given while on the same column
        for(int r= selectedSquare.row+1; r<8; r++)
        {   // Validating moves to iterated squares when they are unoccupied 
            if(square[r][selectedSquare.column].occupied==false)
                moveList.add(r*10+ selectedSquare.column); 
            else{ 
                 // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[r][selectedSquare.column].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                moveList.add(r*10+ selectedSquare.column);
                    break; 
            }
        }
        // Iteration for the all possible rows lower than the one given while on the same column
        for(int r= selectedSquare.row-1; r>=0; r--)
        {   // Validating moves to iterated squares when they are unoccupied 
            if(square[r][selectedSquare.column].occupied==false)
                moveList.add(r*10+ selectedSquare.column); 
            else{ 
                 // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[r][selectedSquare.column].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                moveList.add(r*10+ selectedSquare.column);
                    break; 
            }
           
        }
        // Iteration for the all possible columns larger than the one given, while on the same row
        for(int c= selectedSquare.column +1; c<8; c++)
        {
             // Validating moves to iterated squares when they are unoccupied 
            if(square[selectedSquare.row][c].occupied==false)
                moveList.add(selectedSquare.row*10 + c); 
            else{
                // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row][c].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add(selectedSquare.row*10 + c); 
                        break; 
            }
        }
        // Iteration for the all possible columns lower than the one given, while on the same row
        for(int c= selectedSquare.column-1; c>=0; c--)
        { 
            // Validating moves to iterated squares when they are unoccupied 
            if(square[selectedSquare.row][c].occupied==false)
                moveList.add(selectedSquare.row*10 + c); 
            else{
                // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row][c].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add(selectedSquare.row*10 + c); 
                        break; 
            }
        }
        
        
        //Creates a simple array for the Integer ArrayList
         int[] list = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list.length; i++) 
            list[i] = iterator.next().intValue();
        //Isolates possible rows and columns and asserts that to the iterated objects
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
                    return possibleSquares; 

    }

    private Square[] knightPossibleMoves(Square selectedSquare, Square[][] square) {
      int row= selectedSquare.row; 
      int col= selectedSquare.column; 
       //Conditions to be met for a knight's valid moves
      // No blocked conditions for a knight
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if((Math.abs(row-r)==1&& Math.abs(col-c)==2)||(Math.abs(row-r)==2&& Math.abs(col-c)==1) && square[r][c].pieceColor!=square[row][col].pieceColor) 
                    moveList.add((r * 10) + c);
                }
        }
                
        //Creates a simple array for the Integer ArrayList
                int[] list = new int[moveList.size()];
                Iterator<Integer> iterator = moveList.iterator();
                for (int i = 0; i < list.length; i++) {
                    list[i] = iterator.next().intValue();

                } 
                 // Isolates possible rows and columns of the new array and uses that to initialize the iterated objects
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
                    return possibleSquares; 
    }
          

    private Square[] bishopPossibleMoves(Square selectedSquare, Square[][] square) {
        // Iteration for the Lower Right diagonal move(both row and column increasing by the same amount)
        for(int i=1; i<8; i++)
        {  // Breaks out of the for loop when a lower right diagonal move is no longer available 
            if(selectedSquare.row+i==8||selectedSquare.column+i==8)
            break; 
        else{
                // Validating moves to iterated squares when they are unoccupied
            if(square[selectedSquare.row +i][selectedSquare.column+i].occupied==false)
                moveList.add((selectedSquare.row +i)*10 +(selectedSquare.column+i));
            else
            { // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row +i][selectedSquare.column+i].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add((selectedSquare.row +i)*10 +(selectedSquare.column+i));
                break; 
            }
        }
        }
        // Iteration for the Lower Left diagonal move
        for(int i=1; i<8; i++)
        {  // Breaks out of the for loop when a lower left diagonal move is no longer available
            if(selectedSquare.row+i==8||selectedSquare.column-i+1==0)
            break; 
        else{
                 // Validating moves to iterated squares when they are unoccupied
            if(square[selectedSquare.row +i][selectedSquare.column-i].occupied==false)
                moveList.add((selectedSquare.row +i)*10 +(selectedSquare.column-i));
            else
            {   // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row +i][selectedSquare.column-i].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add((selectedSquare.row +i)*10 +(selectedSquare.column-i));
                break; 
            }
        }
        }
        // Iteration for the Upper Left diagonal move
        for(int i=1; i<8; i++)
        {   // Breaks out of the for loop when a Upper right diagonal move is no longer available
            if(selectedSquare.row-i+1==0||selectedSquare.column-i+1==0)
            break; 
        else{
            // Validating moves to iterated squares when they are unoccupied
            if(square[selectedSquare.row -i][selectedSquare.column-i].occupied==false)
                moveList.add((selectedSquare.row -i)*10 +(selectedSquare.column-i));
            else
            {   // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row -i][selectedSquare.column-i].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add((selectedSquare.row -i)*10 +(selectedSquare.column-i));
                break; 
            }
        }
        } // Iteration for the Upper Right diagonal move
        for(int i=1; i<8; i++)
        {   if(selectedSquare.row-i+1==0||selectedSquare.column+i==8)
            break; 
        else{
            // Validating moves to iterated squares when they are unoccupied
            if(square[selectedSquare.row -i][selectedSquare.column+i].occupied==false)
                moveList.add((selectedSquare.row -i)*10 +(selectedSquare.column+i));
            else
            {   // When the iterated square is occupied blocking further iteration, the following code validates  
               //this particular iterated square if it is occupied by an opponent
                if(square[selectedSquare.row -i][selectedSquare.column+i].pieceColor!=square[selectedSquare.row][selectedSquare.column].pieceColor)
                    moveList.add((selectedSquare.row -i)*10 +(selectedSquare.column+i));
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
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
                    return possibleSquares; 
    }

    private Square[] queenPossibleMoves(Square selectedSquare, Square[][] square) {
        //Validating the same valid moves of a rook from the given selected square location
        for(int iterate=0; iterate<rookPossibleMoves(selectedSquare, square).length; iterate++ )
        {
            moveList.add((rookPossibleMoves(selectedSquare, square)[iterate].row)*10 +(rookPossibleMoves(selectedSquare, square)[iterate].column)); 
        }
        //Validating the same valid moves of a bishop from the given selected square location
        for(int iterate=0; iterate<bishopPossibleMoves(selectedSquare, square).length; iterate++ )
        {
            moveList.add((bishopPossibleMoves(selectedSquare, square)[iterate].row)*10 +(bishopPossibleMoves(selectedSquare, square)[iterate].column)); 
        }
        
          //Creating a simple array for the Integer ArrayList
         int[] list = new int[moveList.size()];
                Iterator<Integer> iterator = moveList.iterator();
                for (int i = 0; i < list.length; i++) {
                    list[i] = iterator.next().intValue();

                } 
           
                // Isolating possible rows and columns of the new array and using that to initialize the iterated objects
                int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list[i]/10; 
                        column[i]=list[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
        return possibleSquares; 
    }

    private Square[] kingPossibleMoves(Square selectedSquare, Square[][] square) {
        

        
        List<Square> pieceMoveList = new ArrayList<Square>();
        Square obj= new Square(); 
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) 
            {
                if(square[r][c].occupied==true)
                { obj= new Square(); 
                    obj.row= r; 
                    obj.column=c;
                    obj.piece=square[r][c].piece;
                    obj.occupied= square[r][c].occupied;
                    obj.pieceColor=square[r][c].pieceColor;
                    obj.boardColor= square[r][c].boardColor; 
                   for(int i=0; i<findPossibleMoves(obj,square).length; i++) 
                   {
                       pieceMoveList.add(findPossibleMoves(obj, square)[i]);
                   }
                       
                   
                    
                }
               // Square [] list = new Square[pieceMoveList.size()];
                
       // Iterator<Square> iterator = pieceMoveList.iterator();
       // for (int i = 0; i < list.length; i++) {
           // list[i] = iterator.next().intValue();

        }
        }
            Square[] list = pieceMoveList.toArray(new Square[pieceMoveList.size()]);
            
            int row= selectedSquare.row; 
      int col= selectedSquare.column;
      int sum=0; 
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (((Math.abs(row - r) == 1 && c == col) || (Math.abs(col - c) == 1 && r == row) || (Math.abs(row - r) == 1) && (Math.abs(col - c) == 1)) ) {
                    for(int n=0; n<list.length; n++)
                    {
                        if(r==list[n].row && c==list[n].column)
                            sum+=1; 
                    }
                }
                if(sum==0)
                moveList.add(r*10+c);
            }
            
        }
         int[] list1 = new int[moveList.size()];
        Iterator<Integer> iterator = moveList.iterator();
        for (int i = 0; i < list1.length; i++) {
            list1[i] = iterator.next().intValue();

        }
        int [] ro= new int[list.length];
                int [] column= new int[list.length];
         
                    for(int i=0;i<list.length; i++)
                      {
                        ro[i]=list1[i]/10; 
                        column[i]=list1[i]%10; 
                        possibleSquares[i] = new Square();
                        possibleSquares[i].row = ro[i];
                        possibleSquares[i].column = column[i];
                      }
        return possibleSquares;    
    }
}
