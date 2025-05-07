package lv.rvt;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import lv.rvt.tools.Helper;

public class Game {

public static void mainGame(){
    
    Scanner scanner = new Scanner(System.in);

    System.out.println("\nHello, choose your command: ");
    System.out.println("\nS - start the singleplayer mode");
    System.out.println("M - start the multiplayer mode");
    System.out.println("L - show leaderboard");
    System.out.println("E - exit the game\n");
    

    while (true) {

        String command = scanner.nextLine().trim().toUpperCase();
    

    if (command.equals("S")) {
        SinglePlayer singlePlayer = new SinglePlayer();
        Random random = new Random();
        String[] colors = {"Green", "Blue", "Yellow", "Red"};
        int[] numbers = {0,1,2,3,4,5,6,7,8,9};
        Card card;
        

        for(int i=0; i<10; i++){

            card = new Card(colors[random.nextInt(4)], numbers[random.nextInt(10)]);
            singlePlayer.playerCards.add(card);
            

        }

        for(int i=0; i<10; i++){

            card = new Card(colors[random.nextInt(4)], numbers[random.nextInt(10)]);
            singlePlayer.computerCards.add(card);
            

        }



            ArrayList<Card> playerCards = singlePlayer.getPlayerCards();
            ArrayList<Card> computerCards = singlePlayer.getComputerCards();
            ArrayList<Card> cards = singlePlayer.getCards();
            
            while (true) {
                
                singlePlayer.playerHasValidMove();

                singlePlayer.processPlayerCards();
                System.out.print("\nCards: ");
                System.out.println(cards.getLast());
                

                if (playerCards.size() == 0) {
                    System.out.println("\nPlayer won!");
                    singlePlayer.playerWinCount += 1;
                    singlePlayer.writingIntoRecordTable();
                    singlePlayer.updatePlayerWin(singlePlayer.playerName, singlePlayer.playerPoints, singlePlayer.playerWinCount);
                    Helper.recordTable("SinglePlayerTable.csv");
                    break;
                  }

                singlePlayer.computerHasValidMove();
                singlePlayer.processComputerCards();
                for (int i = 0; i < 100; i++) {
                     System.out.println("\n");
                }
                System.out.print("\nCards: ");
                System.out.println(cards.getLast());

                if (computerCards.size() == 0) {
                    System.out.println("\nComputer won!");
                    singlePlayer.computerWinCount += 1;
                    singlePlayer.writingIntoRecordTable();
                    singlePlayer.updatePlayerWin(singlePlayer.playerName, singlePlayer.playerPoints, singlePlayer.playerWinCount);
                    Helper.recordTable("SinglePlayerTable.csv");
                    break;
                  }
            

            }
            
            
            
            
    }
    else if (command.equals("M")) {

        MultiPlayer multiPlayer = new MultiPlayer();
        
        multiPlayer.setupPlayers();      
        multiPlayer.giveInitialCards();   
        multiPlayer.startGame();          

        
    
    } else if (command.equals("L")) {


        System.out.println("Would you like to sort by points or by wins?");
        System.out.println("(P - points, W -wins)");
        String sortCommand = scanner.nextLine().trim().toUpperCase();
        Helper.sortRecordsInCsv("RecordsDataBase.csv", sortCommand);
        
        Helper.recordTable("RecordsDataBase.csv");

    } else if (command.equals("E")) {

        scanner.close();
        break;

    }
    else {

        System.out.println("Please enter S , M , L or E");
        
    }
    

    System.out.println("\nS - start the singleplayer mode");
    System.out.println("M - start the multiplayer mode");
    System.out.println("L - show leaderboard");
    System.out.println("E - exit the game\n");
}
    
    

    System.out.println("\nBye bye");





}

}

