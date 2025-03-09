package lv.rvt;

import java.util.*;

//import javax.smartcardio.Card;

public class Main 
{

    // Comma separeted values

   // Izlasīt persons.csv izmantojot BufferedReader objektu
    
   public static void main(String[] args) throws Exception{
           
        

    Scanner scanner = new Scanner(System.in);

    System.out.println("Hello, choose your command: ");
    System.out.println("singleplayer - start the singleplayer mode");
    System.out.println("multiplayer - start the multiplayer mode");
    System.out.println("exit - exit the game\n");
    

    while (true) {

        String command = scanner.nextLine();
    

    if (command.equals("singleplayer")) {
        ArrayList<Card> cards = new ArrayList<Card>();
        Random random = new Random();
        String[] colors = {"Green", "Blue", "Yellow", "Red"};
        int[] numbers = {0,1,2,3,4,5,6,7,8,9};
        Card card;
        for(int i=0; i<10; i++){

            card = new Card(colors[random.nextInt(4)], numbers[random.nextInt(10)]);
            cards.add(card);
            

        }

         /*  for(int a=0; a < cards.size(); a++){
            
            for(int b=1; b < cards.size(); b++){
                if (cards.get(a).color.equals(cards.get(b).color) == true) {
                    System.out.println(cards.get(a).color + cards.get(b).color);
                    System.out.println(cards.get(a).number + " " + cards.get(b).number);
                    
                    
                }
                
            }
            
        }
            */

            System.out.println("");
            System.out.println(cards);
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
