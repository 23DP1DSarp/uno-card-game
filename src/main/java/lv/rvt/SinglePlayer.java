package lv.rvt;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SinglePlayer {


Scanner playerScanner = new Scanner(System.in);

ArrayList<Card> playerCards = new ArrayList<Card>();

ArrayList<Card> computerCards = new ArrayList<Card>();

ArrayList<Card> cards = new ArrayList<Card>();


String[] colors = {"Green", "Blue", "Yellow", "Red"};

int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
Random random = new Random();

Card card;

public ArrayList<Card> getPlayerCards() {
    return playerCards;
  }



  public ArrayList<Card> getComputerCards() {
    return computerCards;
  }

public ArrayList<Card> getCards() {
  return cards;
}

public void processComputerCards() {

        for(int b=0; b < computerCards.size(); b++){
           
          if (computerCards.get(b).color == cards.get(cards.size() - 1).color) {

                cards.add(computerCards.get(b));
                computerCards.remove(computerCards.get(b));
                break;

          } else if (computerCards.get(b).number == cards.get(cards.size() - 1).number) {

                cards.add(computerCards.get(b));
                computerCards.remove(computerCards.get(b));
                break;

            }
        }
 
}

public void processPlayerCards() {
    
    System.out.print("\nPlayer's cards: ");
    playerCards.forEach( (n) -> {System.out.print(String.valueOf(playerCards.indexOf(n) + 1) + ". " + n + "  "); } );

    System.out.println("\nEnter the cards number:");
    int playerCardString = Integer.valueOf(playerScanner.nextLine());
    
    
    


    while (playerCardString <= 0 || playerCardString > playerCards.size()) {
      System.out.println("Please enter value from 1 to " + playerCards.size());
      System.out.println("\nEnter the cards number:");
      playerCardString = Integer.valueOf(playerScanner.nextLine());
    }

    playerCardString = playerCardString - 1;




    while (true) {

      if (playerCardString < 0 || playerCardString > playerCards.size()){

        System.out.println("Please enter value from 1 to " + playerCards.size());
        System.out.println("\nEnter the cards number:");
        playerCardString = Integer.valueOf(playerScanner.nextLine());
        playerCardString = playerCardString - 1;

      } else if (cards.size() == 0) {

        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else if (playerCards.get(playerCardString).color == cards.get(cards.size() - 1).color) {

        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else if (playerCards.get(playerCardString).number == cards.get(cards.size() - 1).number) {

        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else {

          System.out.println("\nYou can use only cards with the same color or number.");
          System.out.println("\nEnter the cards number:");
          playerCardString = Integer.valueOf(playerScanner.nextLine());
          playerCardString = playerCardString - 1;
        
      }
    }
      
    
}


public void computerHasValidMove() {

  int validCards = 0;

  if (!cards.isEmpty()){

  Card lastCard = cards.get(cards.size() - 1);

  for (Card card : computerCards) {

      if (card.color.equals(lastCard.color) || card.number == lastCard.number) {
          validCards +=1;
      } 

  }

  if (validCards == 0) {
    drawCardUntilValid(computerCards);
  }
  
  }
}


public void playerHasValidMove() {
  
  int validCards = 0;

  if (!cards.isEmpty()){

  Card lastCard = cards.get(cards.size() - 1);

  for (Card card : playerCards) {

      if (card.color.equals(lastCard.color) || card.number == lastCard.number) {
          validCards +=1;
      } 

  }

  if (validCards == 0) {
    drawCardUntilValid(playerCards);
  }
  
  }

}

public void drawCardUntilValid(ArrayList<Card> targetPlayerCards) {
  while (true) {
      
      Card newCard = new Card(colors[random.nextInt(colors.length)], numbers[random.nextInt(numbers.length)]);

      
      Card lastCard = cards.get(cards.size() - 1);

      
      if (newCard.color.equals(lastCard.color) || newCard.number == lastCard.number) {
          targetPlayerCards.add(newCard);
          System.out.println("\nDrawn valid card: " + newCard);
          break;
      } else {
          targetPlayerCards.add(newCard);
          System.out.println("\nDrawn valid card: " + newCard);
      }
  }
}


public int pointCounterComputerCards() {

    int points = 0;

      for (Card card : computerCards) {
        points = points + card.number;
      }

    return points;
}



public int pointCounterPlayerCards() {

    int points = 0;

      for (Card card : playerCards) {
        points = points + card.number;
      }

    return points;
}


}






