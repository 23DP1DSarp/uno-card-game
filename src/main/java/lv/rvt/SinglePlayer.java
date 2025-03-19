package lv.rvt;

import java.util.ArrayList;

public class SinglePlayer{


    
ArrayList<Card> playerCards = new ArrayList<Card>();

ArrayList<Card> computerCards = new ArrayList<Card>();

ArrayList<Card> cards = new ArrayList<Card>();


public ArrayList<Card> getPlayerCards() {
    return playerCards;
  }



  public ArrayList<Card> getComputerCards() {
    return computerCards;
  }

public void processComputerCards() {

        for(int b=1; b < computerCards.size(); b++){

            if (computerCards.get(b).color.equals(cards.get(b).color)) {

                cards.add(computerCards.get(b));
                computerCards.remove(computerCards.get(b));
                break;

            } else if (computerCards.get(b).number == cards.get(b).number) {

                cards.add(computerCards.get(b));
                computerCards.remove(computerCards.get(b));
                break;

            } 
        }
 
}





}
