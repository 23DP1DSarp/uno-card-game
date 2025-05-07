package lv.rvt;

import static org.junit.Assert.assertEquals;


import org.junit.Test;



   
    public class SinglePlayerTest {

    public SinglePlayer singlePlayer = new SinglePlayer();

    @Test
    public void testProcessComputerCards_validMove() {
        
        
        singlePlayer.cards.add(new Card("Red", 5));
        singlePlayer.computerCards.add(new Card("Red", 3));
        singlePlayer.computerCards.add(new Card("Green", 7));

        singlePlayer.processComputerCards();

        
        assertEquals(3, singlePlayer.computerPoints);
        assertEquals(2, singlePlayer.cards.size()); 
        assertEquals(1, singlePlayer.computerCards.size()); 
    }

    @Test
    public void testProcessComputerCards_invalidMove() {
        

        singlePlayer.cards.add(new Card("Blue", 9));
        singlePlayer.computerCards.add(new Card("Green", 7));
        singlePlayer.processComputerCards();

        
        assertEquals(1, singlePlayer.computerCards.size()); 
    }
}

