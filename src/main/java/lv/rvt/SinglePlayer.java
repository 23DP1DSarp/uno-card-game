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

        System.out.print("\nComputer's cards: ");
        computerCards.forEach( (n) -> {System.out.print(String.valueOf(computerCards.indexOf(n) + 1) + ". " + n + "  "); } );

        //System.out.println("Computer's cards " + computerCards);

        for(int b=0; b < computerCards.size(); b++){

          if (!computerHasValidMove()) {

            System.out.println("\nComputer has no valid cards, drawing a new one...");
            drawCardUntilValid(computerCards);

          } else if (computerCards.get(b).color == cards.get(cards.size() - 1).color) {

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
    //System.out.println("Player's cards: " + playerCards);

    System.out.println("\nEnter the cards number:");
    int playerCardString = Integer.valueOf(playerScanner.nextLine());

      while (playerCardString <= 0 || playerCardString > playerCards.size()) {
        System.out.println("Please enter value from 1 to " + playerCards.size());
        playerCardString = Integer.valueOf(playerScanner.nextLine());
      }
    

    playerCardString = playerCardString - 1;

    if (!playerHasValidMove()) {

      System.out.println("\nYou have no valid cards, drawing a new one...");
      drawCardUntilValid(playerCards);

    } else if (cards.size() == 0) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    } else if (playerCards.get(playerCardString).color == cards.get(cards.size() - 1).color) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    } else if (playerCards.get(playerCardString).number == cards.get(cards.size() - 1).number) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    }
}


/*public void randomPlayerCards() {

  while(playerCards.get(playerCards.size() - 1).color == cards.get(cards.size() - 1).color || playerCards.get(playerCards.size() - 1).number == cards.get(cards.size() - 1).number){

    card = new SinglePlayer(colors[random.nextInt(4)], numbers[random.nextInt(10)]);
    playerCards.add(card);
    
    

}

}*/

public boolean computerHasValidMove() {
  if (cards.isEmpty()) return true; // Any card is valid if no previous card exists

  Card lastCard = cards.get(cards.size() - 1);
  for (Card card : computerCards) {
      if (card.color.equals(lastCard.color) || card.number == lastCard.number) {
          return true;
      }
  }
  return false;
}

public boolean playerHasValidMove() {
  if (cards.isEmpty()) return true; // Any card is valid if no previous card exists

  Card lastCard = cards.get(cards.size() - 1);
  for (Card card : playerCards) {
      if (card.color.equals(lastCard.color) || card.number == lastCard.number) {
          return true;
      }
  }
  return false;
}

public void drawCardUntilValid(ArrayList<Card> targetPlayerCards) {
  while (true) {
      // Generate a new card
      Card newCard = new Card(colors[random.nextInt(colors.length)], numbers[random.nextInt(numbers.length)]);

      // Get the last card from the played cards
      Card lastCard = cards.get(cards.size() - 1);

      // Check if the new card is valid
      if (newCard.color.equals(lastCard.color) || newCard.number == lastCard.number) {
          targetPlayerCards.add(newCard);
          System.out.println("Drawn valid card: " + newCard);
          break;
      }
  }
}


  


}






