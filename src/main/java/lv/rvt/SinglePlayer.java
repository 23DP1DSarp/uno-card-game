package lv.rvt;

import java.util.ArrayList;
import java.util.Scanner;

public class SinglePlayer{


Scanner playerScanner = new Scanner(System.in);

ArrayList<Card> playerCards = new ArrayList<Card>();

ArrayList<Card> computerCards = new ArrayList<Card>();

ArrayList<Card> cards = new ArrayList<Card>();


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

        System.out.println("Computer's cards " + computerCards);

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

    System.out.println("Player's cards: " + playerCards);

    System.out.println("Enter the cards number:");
    int playerCardString = Integer.valueOf(playerScanner.nextLine());


    if (cards.size() == 0) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    } else if (playerCards.get(playerCardString).color == cards.get(cards.size() - 1).color) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    } else if (playerCards.get(playerCardString).number == cards.get(cards.size() - 1).number) {

      cards.add(playerCards.get(playerCardString));
      playerCards.remove(playerCards.get(playerCardString));

    } else {

      System.out.println("You dont have the correct cards");

    } 
}
}






