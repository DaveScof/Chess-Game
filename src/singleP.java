///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package chessgame;
//
//import java.util.ArrayList;
//import java.util.List;
////import chessgame.Player;
//import java.util.Arrays;
//
///**
// *
// * @author Tensae
// */
////
////
//public class singleP {
//
//    
////    
////    public void Tensae() {
////        List<Square> rand = new ArrayList<>();
////        Square randNormal[];
////        Square rand2[];
////        ChessBoard cb = new ChessBoard();
////        Player player = new Player();
////        Square s;
////        int sum=0;
////        int os=0;
////        Square piece= new Square();
////        
////        int d=0;
////        ThinkTank thinkTank = new ThinkTank();
////        for (int i = 0; i < 2; i++) {
////            for (int j = 0; j < 8; j++) {
////                d=sum;
////                randNormal = thinkTank.findPossibleMoves(cb.square[i][j], cb.square);
////                rand.addAll(Arrays.asList(randNormal));
////                sum=sum+1;
////            }
////            
////        }
////        if(os>d && os<=sum) 
////
////        rand2 = rand.toArray(new Square[rand.size()]);
////        s=player.any(rand2);
////for (int i=0;i<2;i++)
////{
////    for (int j=0;j<8;j++)
////    {for(int a=0;a<rand2.length;a++)
////      {    randNormal=thinkTank.findPossibleMoves(cb.square[i][j],cb.square);
////         if(randNormal[a]==s)
////             player.computerMove(randNormal[a],s);
////         cb.turn="whiteTurn";
////}}
////        //int p= player.indexForAny();
////        //System.out.printf("Rand2 . peice is %s\n", rand2[p].piece);
////        //player.computerMove(rand2[p],s);
////    }
////}
//List<Square> validMoveList;
//    Square object;
//    Player player;
////    
////  public void pickAMove(int moveNum, Square [][] square)
////
////{
////    if(moveNum==1 || moveNum==2)
////           sendToRandom(square);
////    else
////            sendToPrey(square);
////}
//      public int setPriority(Square square) 
//    { 
//      
//        switch (square.piece) 
//        {   
//           
//            case "Pawn":
//                square.prioritySet=0;
//            case "Knight":
//                square.prioritySet=1;
//            case "Bishop":
//                square.prioritySet=2;
//            case "Rook":
//                square.prioritySet=3;
//            case "Queen":
//                square.prioritySet=4;
//                 case "King":
//                square.prioritySet=5;
//            default:
//                break;
//               
//                        
//        }
//        return square.prioritySet;
//    }
////    }
////   // public void pickAMove(int moveNum, Square[][] square) {
////     
////        //if(moveNum==0 || moveNum==1)
////      //  sendToRandom(square);
////      //else
////    //      sendToPrey(square);
////   // }
//
//    public void sendToRandom(Square[][] square) {
//        
//        Square piece= new Square();
//       // List<Square> Piece= new ArrayList<>();
//         int sum=0;
//         int overseesum=0;
//        validMoveList = new ArrayList<>();
//        for (int row = 0; row < 8; row++) {
//            for (int column = 0; column < 8; column++) {
//                if ( square[row][column].occupied && square[row][column].pieceColor .equals("black")) {
//                    ThinkTank thinkTank = new ThinkTank();
//                  //  thinkTank.findPossibleMoves(square[row][column], square);
//                    if (thinkTank.findPossibleMoves(square[row][column], square).length != 0) {
//                              
//                              int d=sum;
//                        for (int i = 0; i < thinkTank.findPossibleMoves(square[row][column], square).length; i++) {
//                           
//                           if(thinkTank.findPossibleMoves(square[row][column], square)[i].occupied==false || thinkTank.findPossibleMoves(square[row][column], square)[i].pieceColor.equals("white") )
//                           {
//                               validMoveList.add(thinkTank.findPossibleMoves(square[row][column], square)[i]);
//                           sum+=1; 
//                           }
//                        }
//                        if(overseesum>d && overseesum<=sum)
//                            piece = square[row][column];
//                    }
//                }
//            }
//        }
//        Square[] validList = validMoveList.toArray(new Square[validMoveList.size()]);
//
//        /*ThinkTank tt= new ThinkTank();
//        player = new Player();
//        player.any(validList);
//         for (int i = 0; i < tt.findPossibleMoves(square[row][column], square).length; i++) {
//            if(player.any(validList)==tt.findPossibleMoves(square[row][column], square)[i])
//                
//                       
//            }
//        //return validList; */
//           player = new Player();
//           player.any(validList);
//          // overseesum= player.indexForAny();
//           player.computerMove( piece, player.any(validList));
//         ChessBoard cb=new ChessBoard();
//         cb.turn="whiteTurn";
//    }
//}
////    
////
//////      public void sendToPrey(Square[][] square) {
//////
//////        
//////        List<Square> iterateArray= new ArrayList<Square>();
//////        List<Square> randomPass= new ArrayList<Square>(); 
//////        List<Square> anyPossible= new ArrayList<Square>();
//////        List<Square> pieceForAny= new ArrayList<Square>();
//////         List<Square> CP= new ArrayList<Square>();
//////        int highestPriority=0;
//////        int leastPriority=0; 
//////        int iterates=0;
//////        ThinkTank thinkTank;
//////        int iteration=0;
//////        int totalCapture=0;
//////        int pieceWithCapture=0;
//////        //Square[] passToPrey;
//////        Square [] passToRandom= new Square[1];
//////        Square currentPiece= new Square();;
//////    
//////        for (int row = 0; row < 8; row++) {
//////            for (int column = 0; column < 8; column++) {
//////                int capture = 0;
//////                iterates+=1;
//////                if (square[row][column].pieceColor == "Black") {
//////                    
//////                    thinkTank = new ThinkTank();
//////                    //thinkTank.findPossibleMoves(square[row][column], square);
//////                    // considers only those pieces with the capability to move
//////                    if (thinkTank.findPossibleMoves(square[row][column], square).length != 0) {
//////                        
//////                        validMoveList = new ArrayList<Square>();
//////                   
//////                        // For all the possible moves of the square, the total number of possible captures is calculated
//////                        for (int i = 0; i < thinkTank.findPossibleMoves(square[row][column], square).length; i++) {
//////                            if (thinkTank.findPossibleMoves(square[row][column], square)[i].occupied == true && thinkTank.findPossibleMoves(square[row][column], square)[i].pieceColor == "White") {
//////                                capture += 1;
//////                                totalCapture+=1;
//////                                iterateArray.add(thinkTank.findPossibleMoves(square[row][column], square)[i]);
//////                                anyPossible.add(thinkTank.findPossibleMoves(square[row][column], square)[i]);
//////                                pieceForAny.add(square[row][column]);
//////                            }
//////
//////                        }
//////                        
//////                        // This is what happens to a square whose valid moves will not capture 
//////                        if (capture == 0) {
//////                            
//////                            iteration+=1;
//////                            for (int i = 0; i < thinkTank.findPossibleMoves(square[row][column], square).length; i++) {
//////                                //Because possible moves are arranged to include destinations by the first instance blockers of their own piece
//////                                // the following if statements makes sure they are not encountered
//////                                 if(thinkTank.findPossibleMoves(square[row][column], square)[i].pieceColor!="Black"|| thinkTank.findPossibleMoves(square[row][column], square)[i].occupied==false)
//////                                validMoveList.add(thinkTank.findPossibleMoves(square[row][column], square)[i]);//add these valid moves to a dynamic array
//////                            }
//////                        
//////                        Square[] noCapture = validMoveList.toArray(new Square[validMoveList.size()]);// create a simple array of the dynamic validMoveList array
//////                        player = new Player();
//////                        
//////                      //  Player wantedPlayer= new Player(); 
//////                       // if(player.prey(noCapture).length!=0) 
//////                         //   wantedPlayer=player; 
/////////                        
//////                       if(iteration==1)
//////                       {  if(player.prey(noCapture).length!=0)
//////                       { leastPriority= square[row][column].prioritySet; //on the first valid piece with no capture, leastPriority is set to it
//////                          
//////                           passToRandom[0]=square[row][column];// sets passToRandom array to the piece of the first iteration
//////                       }
//////                       else
//////                       { leastPriority=7;
//////                            passToRandom[0]=square[row][column];
//////                       }
//////                       }
//////                       // if the iteration cycles more than once, the following happens
//////                       //it outputs which piece has the smallest priority
//////                         else
//////                       {if(leastPriority>=priorityCheck(player.prey(noCapture), square[row][column]))
//////                       { leastPriority=priorityCheck(player.prey(noCapture), square[row][column]);
//////                        
//////                           passToRandom[0]=square[row][column];}
//////                       
//////                           else
//////                               leastPriority=leastPriority; 
//////                           } 
//////                         
//////                            
//////                    }
//////                        else
//////                        {      // pieceWithCapture+=1;
//////                            Square [] passToPrey= new Square[1];
//////                            
//////                                
//////                            for (int i = 0; i < thinkTank.findPossibleMoves(square[row][column], square).length; i++) {
//////                                 
//////                            if (thinkTank.findPossibleMoves(square[row][column], square)[i].occupied == true && thinkTank.findPossibleMoves(square[row][column], square)[i].pieceColor == "White") {
//////                                pieceWithCapture+=1;   
//////                                validMoveList.add(thinkTank.findPossibleMoves(square[row][column], square)[i]);         
//////                                   // thinkTank.findPossibleMoves(square[row][column], square)[i].prioritySet;
//////                            
//////                            if(pieceWithCapture==1)
//////                           // highPriority=priorityCheck(validMoveList.findPossibleMoves(square[row][column] ),) ;
//////                            { highestPriority=thinkTank.findPossibleMoves(square[row][column], square)[i].prioritySet;
//////                                   
//////                                    passToPrey[0]=thinkTank.findPossibleMoves(square[row][column], square)[i];
//////                                    currentPiece.row=row;
//////                                    currentPiece.column=column;
//////                                    
//////                                    
//////                                    
//////                                    
//////                            }
//////                            else {
//////                                if(highestPriority>=thinkTank.findPossibleMoves(square[row][column], square)[i].prioritySet)
//////                                {  highestPriority= highestPriority;
//////                                  
//////                                }
//////                                
//////                                else
//////                                { highestPriority=thinkTank.findPossibleMoves(square[row][column], square)[i].prioritySet;
//////                                   
//////                                    passToPrey[0]=thinkTank.findPossibleMoves(square[row][column], square)[i];
//////                                     currentPiece= square[row][column];
//////                                   // currentPiece.row=row;
//////                                   // currentPiece.column=column;
//////                                }
//////                            
//////                            }
//////                                   
//////                            } 
//////                            }
//////                            player= new Player();
//////                             if(player.prey(passToPrey).length!=0 )
//////                             {     
//////                                    randomPass.add(passToPrey[0]);
//////                              CP.add(currentPiece);}
//////                             else if(player.prey(passToPrey).length==0 && highestPriority>=square[row][column].prioritySet)
//////                             {randomPass.add(passToPrey[0]);
//////                             
//////                              CP.add(currentPiece);
//////                             }
//////                             //randomPass[randomPass.size()-1]=currentPiece; 
//////                        
//////                            
//////                         
//////                }
//////                
//////            }
//////        }
//////                    
//////    }
//////        }
//////        //if(totalCapture==0)
//////        //rPass is a simple array for the array that contains higher priority capture destinations for all pieces
//////         Square[] rPass = randomPass.toArray(new Square[randomPass.size()]);
//////         Square [] possibleMoves= anyPossible.toArray(new Square[anyPossible.size()]);
//////         Square [] anyPiece= pieceForAny.toArray(new Square[pieceForAny.size()]);
//////        
//////      thinkTank= new ThinkTank(); 
//////     List<Square> preyArray= new ArrayList<Square>();
//////     // creating a simple array for the all possible moves of the least priority piece   
//////     if(leastPriority!=7)
//////     {  for (int i = 0; i < thinkTank.findPossibleMoves(passToRandom[0], square).length; i++)
//////        { 
//////               preyArray.add(thinkTank.findPossibleMoves(passToRandom[0], square)[i]);  
//////               
//////        }
//////     }
//////         Square[] simplePreyArray = preyArray.toArray(new Square[preyArray.size()]);
//////         Square[] currentP = CP.toArray(new Square[CP.size()]);
//////        
//////
//////    
//////    Player play = new Player();
//////   // Player pl=new Player();
//////    //Player p=new Player();
//////    if(totalCapture==0)
//////        {
//////            
//////             play.computerMove(passToRandom[0], play.any(play.prey(simplePreyArray)));
//////               
//////        } 
//////    else{
//////    if(totalCapture!=0 && rPass.length==0 )
//////    {   
//////       play.computerMove( passToRandom[0] ,play.any(play.prey(simplePreyArray)));
//////        
//////    }
//////    else
//////       play.computerMove(currentP[play.indexForAny()],play.any(play.prey(rPass))); 
//////    }
//////     if(rPass.length==0 && leastPriority==7)
//////    {
//////        play.computerMove( play.any(anyPiece[play.indexForAny()]) ,play.any(possibleMoves));
//////    }
//////    }
//////    public int priorityCheck(Square[] objArr, Square square) {
//////        int priority;
//////        if (objArr.length != 0) {
//////            priority = square.prioritySet; 
//////        } else {
//////            priority = 7;
//////        }
//////        return priority;
//////    }
////
////  
////    
////
////}
