package lv.rvt;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import lv.rvt.tools.Helper;

public class SinglePlayer {


Scanner playerScanner = new Scanner(System.in);

Scanner playerNameString = new Scanner(System.in);

Scanner tableSortTypeInput = new Scanner(System.in);

String playerName;

String playerInput = "";

String sortInput;

ArrayList<Card> playerCards = new ArrayList<Card>();

ArrayList<Card> computerCards = new ArrayList<Card>();

ArrayList<Card> cards = new ArrayList<Card>();


String[] colors = {"Green", "Blue", "Yellow", "Red"};

int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
Random random = new Random();

Card card;

int computerPoints = 0;

int playerPoints = 0;

int playerWinCount = 0;

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
                
              computerPoints = computerPoints + computerCards.get(b).number;
                cards.add(computerCards.get(b));
                computerCards.remove(computerCards.get(b));
                break;

          } else if (computerCards.get(b).number == cards.get(cards.size() - 1).number) {

                computerPoints = computerPoints + computerCards.get(b).number;
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
    playerInput = playerScanner.nextLine();

    while (playerInput.isBlank()) {
      System.out.println("\nDon't enter letters or empty lines:");
      playerInput = playerScanner.nextLine();
    }


    int playerCardString = Integer.valueOf(playerInput);
    
    
    


    while (playerCardString < 0 || playerCardString > playerCards.size()) {
      System.out.println("Please enter value from 1 to " + playerCards.size());
      System.out.println("\nEnter the cards number:");
      playerInput = playerScanner.nextLine();
    }


    playerCardString = Integer.valueOf(playerInput);
    playerCardString = playerCardString - 1;




    while (true) {

      while (playerInput.isBlank()) {
        System.out.println("\nDon't enter letters or empty lines:");
        playerInput = playerScanner.nextLine();
      }
  

      playerCardString = Integer.valueOf(playerInput);
      playerCardString = playerCardString - 1;

      if (playerCardString < 0 || playerCardString > playerCards.size()){

        System.out.println("Please enter value from 1 to " + playerCards.size());
        System.out.println("\nEnter the cards number:");
        playerInput = playerScanner.nextLine();
        

      } else if (cards.size() == 0) {

        playerPoints = playerPoints + playerCards.get(playerCardString).number;
        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else if (playerCards.get(playerCardString).color == cards.get(cards.size() - 1).color) {

        playerPoints = playerPoints + playerCards.get(playerCardString).number;
        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else if (playerCards.get(playerCardString).number == cards.get(cards.size() - 1).number) {

        playerPoints = playerPoints + playerCards.get(playerCardString).number;
        cards.add(playerCards.get(playerCardString));
        playerCards.remove(playerCards.get(playerCardString));
        break;

      } else {

          System.out.println("\nYou can use only cards with the same color or number.");
          System.out.println("\nEnter the cards number:");
          playerInput = playerScanner.nextLine();
        
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
          //System.out.println("\nDrawn valid card: " + newCard);
          break;
      } else {
          targetPlayerCards.add(newCard);
          //System.out.println("\nDrawn valid card: " + newCard);
      }
  }
}




public void writingIntoRecordTable() {


    ArrayList<ArrayList<String>> dataToWrite = new ArrayList<>();

    dataToWrite.clear();

    System.out.println("player" + playerPoints);
    System.out.println("Computer" + computerPoints);
    
    ArrayList<String> row1 = new ArrayList<>();
    row1.clear();

    row1.add("Computer");
    row1.add(String.valueOf(computerPoints));
    dataToWrite.add(row1);

    
    System.out.println("Enter your name:");
    playerName = playerNameString.nextLine();

    String score = String.valueOf(String.valueOf(playerPoints));

    String wins = String.valueOf(String.valueOf(playerWinCount));



    // Read RecordsDataBase
    ArrayList<ArrayList<String>> recordsList = new ArrayList<>();
    try {
        List<String[]> recordsRaw = Helper.readCsv("RecordsDataBase.csv");
        for (String[] row : recordsRaw) {
            recordsList.add(new ArrayList<>(List.of(row)));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (recordsList.isEmpty()) {
        // If database is empty, create header
        recordsList.add(new ArrayList<>(List.of("Name", "Points", "Wins")));
    }

    ArrayList<String> headerRecord = recordsList.get(0);
    List<ArrayList<String>> recordsRows = recordsList.subList(1, recordsList.size());

    boolean found = false;
    int playerWins = 0;

    ArrayList<String> row2 = new ArrayList<>();
    row2.clear();

    row2.add(playerName);
    row2.add(score);
    row2.add(wins);
    dataToWrite.add(row2);



    System.out.println("Would you like to sort the leaderboard by points or by wins?:");
    System.out.println("( P = points,   W = wins )");
    
      while (true) {

        sortInput = tableSortTypeInput.nextLine();

        if (sortInput.equals("P")) {
            // sort by points
          dataToWrite.sort((a, b) -> {
            int pointsA = Integer.parseInt(a.get(1));
            int pointsB = Integer.parseInt(b.get(1));
            return Integer.compare(pointsB, pointsA);
        });
        break;
      } else if (sortInput.equals("W")) {
            // sort by wins
              dataToWrite.sort((a, b) -> {
                int winsA = Integer.parseInt(a.get(1));
                int winsB = Integer.parseInt(b.get(1));
                return Integer.compare(winsB, winsA);
            });
            break;
      } else {
            System.out.println("Please enter P or W.");
      } 

    }
     
    
    
   try {

        ArrayList<ArrayList<String>> header = new ArrayList<>();
        header.clear();

        header.add(new ArrayList<>(List.of("Name", "Points", "Wins")));
        Helper.writeRecordTable("SinglePlayerTable.csv", header, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Helper.writeRecordTable("SinglePlayerTable.csv", dataToWrite, StandardOpenOption.APPEND);

    } catch (IOException e) {
        e.printStackTrace();
    }

  }


  



public void updatePlayerWin(String playerName, int points, int playerWins) {
  try {
      
      List<String[]> rawData = Helper.readCsv("RecordsDataBase.csv");

      if (rawData.isEmpty()) {
          System.out.println("CSV is empty or missing header.");
          return;
      }

      
      ArrayList<ArrayList<String>> data = new ArrayList<>();
      for (String[] row : rawData) {
          data.add(new ArrayList<>(List.of(row)));
      }

      
      ArrayList<String> header = data.get(0);
      List<ArrayList<String>> rows = data.subList(1, data.size());

      boolean found = false;

      // Search and update win count
      for (ArrayList<String> row : rows) {
          if (row.get(0).equalsIgnoreCase(playerName)) {
              int wins = Integer.parseInt(row.get(2));
              int pointsCount = Integer.parseInt(row.get(1));
              row.set(2, String.valueOf(wins + playerWinCount));
              row.set(1, String.valueOf(pointsCount + playerPoints));
              found = true;
              break;
          }
      }

      // Add new player if not found
      if (!found) {
          ArrayList<String> newRow = new ArrayList<>();
          newRow.add(playerName); // name
          newRow.add(String.valueOf(points));         // points
          newRow.add(String.valueOf(playerWins));         // wins
          rows.add(newRow);
      }

      // Rebuild data (header + rows)
      ArrayList<ArrayList<String>> finalData = new ArrayList<>();
      finalData.add(header);
      finalData.addAll(rows);

      // Write updated data to CSV (overwrite)
      Helper.writeRecordTable("RecordsDataBase.csv", finalData, StandardOpenOption.TRUNCATE_EXISTING);

  } catch (IOException e) {
      e.printStackTrace();
  }
}





}






