package lv.rvt;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

public static void mainGame(){
    
    Scanner scanner = new Scanner(System.in);

    System.out.println("Hello, choose your command: ");
    System.out.println("singleplayer - start the singleplayer mode");
    System.out.println("multiplayer - start the multiplayer mode");
    System.out.println("exit - exit the game\n");
    

    while (true) {

        String command = scanner.nextLine();
    

    if (command.equals("singleplayer")) {
        SinglePlayer singlePlayer = new SinglePlayer();
        Random random = new Random();
        String[] colors = {"Green", "Blue", "Yellow", "Red"};
        int[] numbers = {0,1,2,3,4,5,6,7,8,9};
        Card card;
        System.out.println( ConsoleColors.RED + "RED COLORED" + ConsoleColors.RESET + " NORMAL" );
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
                
                singlePlayer.processPlayerCards();
                System.out.print("\nCards: ");
                cards.forEach( (n) -> {System.out.print(String.valueOf(cards.indexOf(n) + 1) + ". " + n + "  "); } );
                

                if (playerCards.size() == 0) {
                    System.out.println("Player 1 won!");
                    break;
                  }

                singlePlayer.processComputerCards();
                System.out.print("\nCards: ");
                cards.forEach( (n) -> {System.out.print(String.valueOf(cards.indexOf(n) + 1) + ". " + n + "  "); } );

                if (computerCards.size() == 0) {
                    System.out.println("Computer won!");
                    break;
                  }

            }
            
            
            
            
    }
    else if (command.equals("multiplayer")) {

        System.out.println("\nComming soon!");
    
    }
    else if (command.equals("exit")) {
        scanner.close();
        break;
    } 
    else if (command.equals("help")){
        System.out.println("Hello, choose your command: ");
        System.out.println("singleplayer - start the singleplayer mode");
        System.out.println("multiplayer - start the multiplayer mode");
        System.out.println("exit - exit the game");
    }
    

    System.out.println("\nsingleplayer - start the singleplayer mode");
    System.out.println("multiplayer - start the multiplayer mode");
    System.out.println("exit - exit the game\n");
}
    
    

    System.out.println("\nBye bye");





}

}

