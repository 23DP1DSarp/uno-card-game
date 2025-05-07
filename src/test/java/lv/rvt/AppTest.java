package lv.rvt;

import static org.junit.Assert.assertEquals;


import org.junit.Test;


public class AppTest 
{
   
    public class SinglePlayerTest {

    private SinglePlayer singlePlayer;

    
    public void setUp() {
        singlePlayer = new SinglePlayer();
        
        singlePlayer.cards.add(new Card("Red", 5));
        singlePlayer.computerCards.add(new Card("Red", 3));
        singlePlayer.computerCards.add(new Card("Green", 7));
    }

    @Test
    public void testProcessComputerCards_validMove() {
        singlePlayer.processComputerCards();

        
        assertEquals(3, singlePlayer.computerPoints);
        assertEquals(1, singlePlayer.cards.size()); 
        assertEquals(1, singlePlayer.computerCards.size()); 
    }

    @Test
    public void testProcessComputerCards_invalidMove() {
        
        singlePlayer.cards.add(new Card("Blue", 9));
        singlePlayer.processComputerCards();

        
        assertEquals(1, singlePlayer.computerCards.size()); 
    }
}
}
