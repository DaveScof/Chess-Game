///**
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package chessgame;
//import java.util.Random;
//import java.util.*;
///**
// *
// * @author Tensae
// */
//public class Player extends ChessBoard {
//    //prey
//    //choose @ Random from edible or not prey
//    ChessBoard CB= new ChessBoard();
//    Square[] myPossible;
//    Square[] dangerous;//Oponent's kill moves for each of my Possible moves
//    //may not make any moves at all
//   
//    public void computerMove (Square selected,Square es)
//    {
//      es.highlighted=true;
//      CB.handleMove(selected,es);
//    }
//    int a;
//    public Square any (Square... myPossible)
//    {
//      this.myPossible=myPossible;
//      Square chosen;
//      Random index=new Random();
//      a=index.nextInt(myPossible.length);
//      chosen=myPossible[a];
//       return chosen;
//    } 
//    public int indexForAny()
//    {
//          return a;
//    }
//    //1st move or second can never be moving the last two pawns.. hold off as long as possible
//    //see the posible moves of White and donot fall prey to them
//    //from set of possible moves add the ones that are opposing color to array edible + not prey=>myPossible 
//    
// /*   public int turnKeeper()
//   {   
//    int T = 0;
//    String CurrentTurn = "whiteTurn";
//    do{
//       T=T++;
//       CurrentTurn=CB.turn;
//      }while (CB.turn.equals(CurrentTurn)==false);
//      
//         return T;
//    }*/
//    
//    public Square[] prey (Square [] Des)
//    {
//        //assuming they have been cheacked for validity
//        // sees the possible moves of the oponent pieces for this particular destination and returns safe destinations out of the valid 
//     
//        List <Square> Des2= new ArrayList<>();// variable Array
//       ThinkTank tt=new ThinkTank ();
//      for( int x=0;x<8;x++)
//      { for (int y=0;y<8;y++)
//        {
//          if ("white".equals(CB.square[x][y].pieceColor))  
//          {//one white piece's possible moves
//            dangerous = tt.findPossibleMoves(CB.square[x][y],square);//...dangerous array set & full
//             for (int i=0; i<Des.length; i++)
//             { for (int j=0; j<dangerous.length; j++)
//               {    if(dangerous[j].pieceColor.equals("black") || dangerous[j].occupied==false)
//                  if(Des[i].row==dangerous[j].row && Des[i].column==dangerous[j].column)
//                         Des[i].edible=true;                                               
//                   else
//                         Des[i].edible=false;
//               }
//             }
//          }
//          else
//              continue;
//        }
//      } 
//      //set all safe destinations to Des2
//          for (int l=0;l<Des.length;l++)
//          { 
//              if(Des[l].edible==false)
//                 Des2.add(Des[l]);
//              else
//                  continue;
//         }
//        
//           Square[] Destination = Des2.toArray(new Square[Des2.size()]);//set to normal array that can be returned
//           return Destination; 
//     }
//}
///* public void playing()
//    { int k=turnKeeper();
//      ThinkTank tt=new ThinkTank();
//       Square[] pawn; 
//       Square safePawn;
//      List <Square> myPawns= new ArrayList<Square>();
//        
//       if (k%2!=0)// black's turn
//      { 
//        if (k<=5)//one of the first three moves of the computer 
//        {
//          for(int x=1;x<7;x++)
//          {      
//             pawn=tt.findPossibleMoves(CB.square[1][x],CB.view());//pawns   
//             safePawn=any(pawn);
//             myPawns.add(safePawn);
//           }
//           Square[] safeRandom= myPawns.toArray(new Square[myPawns.size()]);
//           Square chosen= any(safeRandom);
//        
//          }
//       }   
//    }*/