package lv.rvt;

import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import lv.rvt.tools.Helper;

public class MultiPlayer {

Scanner playerScanner = new Scanner(System.in);

Scanner playerNameString = new Scanner(System.in);

ArrayList<String> playerNames = new ArrayList<String>();

ArrayList<ArrayList<Card>> allPlayersCards = new ArrayList<>();

ArrayList<Integer> playerPointsList = new ArrayList<>();

String playerInput = "";

ArrayList<Card> cards = new ArrayList<Card>();

String[] colors = {"Green", "Blue", "Yellow", "Red"};

int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

Random random = new Random();

Card card;

int playerWinCount = 0;

int playersCount = 0;


public void setupPlayers() {
    System.out.println("Enter number of players:");
    playersCount = Integer.parseInt(playerScanner.nextLine());

    for (int i = 0; i < playersCount; i++) {
        System.out.println("Enter player " + (i + 1) + " name:");
        playerNames.add(playerNameString.nextLine());
        allPlayersCards.add(new ArrayList<Card>());
        playerPointsList.add(0);
    }
}


public void giveInitialCards() {
    for (ArrayList<Card> playerHand : allPlayersCards) {
        for (int i = 0; i < 5; i++) {
            playerHand.add(new Card(colors[random.nextInt(colors.length)], numbers[random.nextInt(numbers.length)]));
        }
    }
}

public void startGame() {
    

    boolean gameRunning = true;

    while (gameRunning) {
        for (int currentPlayerIndex = 0; currentPlayerIndex < playersCount; currentPlayerIndex++) {
            System.out.println("\n" + playerNames.get(currentPlayerIndex) + "'s turn:");

            if (!(cards.isEmpty())) {
              System.out.print("\nCards: ");
              System.out.println(cards.getLast());

              playerHasValidMove(currentPlayerIndex);
            }
            

            processPlayerCards(currentPlayerIndex);

            
            if (allPlayersCards.get(currentPlayerIndex).isEmpty()) {
                System.out.println(playerNames.get(currentPlayerIndex) + " wins!");
                writingIntoRecordTable(currentPlayerIndex);
                gameRunning = false;
                break;
            }
        }
    }
}


public void processPlayerCards(int playerIndex) {

    ArrayList<Card> playerCards = allPlayersCards.get(playerIndex);

    System.out.print("\nPlayer's cards: ");
    playerCards.forEach((n) -> {System.out.print(String.valueOf(playerCards.indexOf(n) + 1) + ". " + n + "  ");});

    System.out.println("\nEnter the cards number:");
    playerInput = playerScanner.nextLine();

    while (!isNumeric(playerInput)) {
        System.out.println("\nDon't enter letters or empty lines:");
        playerInput = playerScanner.nextLine();
    }

    int playerCardString = Integer.parseInt(playerInput) - 1;

    while (playerCardString < 0 || playerCardString >= playerCards.size()) {
        System.out.println("Please enter value from 1 to " + playerCards.size());
        System.out.println("\nEnter the cards number:");
        playerInput = playerScanner.nextLine();
        while (!isNumeric(playerInput)) {
            System.out.println("\nDon't enter letters or empty lines:");
            playerInput = playerScanner.nextLine();
        }
        playerCardString = Integer.parseInt(playerInput) - 1;
    }

    while (true) {
        if (cards.size() == 0 ||
            playerCards.get(playerCardString).color.equals(cards.get(cards.size() - 1).color) ||
            playerCards.get(playerCardString).number == cards.get(cards.size() - 1).number) {

            int newPoints = playerPointsList.get(playerIndex) + playerCards.get(playerCardString).number;
            playerPointsList.set(playerIndex, newPoints);

            cards.add(playerCards.get(playerCardString));
            playerCards.remove(playerCardString);
            break;

        } else {
            System.out.println("\nYou can use only cards with the same color or number.");
            System.out.println("\nEnter the cards number:");
            playerInput = playerScanner.nextLine();
            while (!isNumeric(playerInput)) {
                System.out.println("\nDon't enter letters or empty lines:");
                playerInput = playerScanner.nextLine();
            }
            playerCardString = Integer.parseInt(playerInput) - 1;
        }
    }
}


public void playerHasValidMove(int playerIndex) {
    ArrayList<Card> playerCards = allPlayersCards.get(playerIndex);
    int validCards = 0;

    if (!cards.isEmpty()) {
        Card lastCard = cards.get(cards.size() - 1);

        for (Card card : playerCards) {
            if (card.color.equals(lastCard.color) || card.number == lastCard.number) {
                validCards += 1;
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
            break;
        } else {
            targetPlayerCards.add(newCard);
        }
    }
}


public void writingIntoRecordTable(int winnerIndex) {

  ArrayList<ArrayList<String>> dataToWrite = new ArrayList<>();

  for (int i = 0; i < playerNames.size(); i++) {
      ArrayList<String> row = new ArrayList<>();
      row.add(playerNames.get(i));
      row.add(String.valueOf(playerPointsList.get(i)));

      
      if (i == winnerIndex) {
          row.add(String.valueOf(playerWinCount + 1));
      } else {
          row.add("0");
      }

      dataToWrite.add(row);
  }

  try {
      ArrayList<ArrayList<String>> header = new ArrayList<>();
      header.add(new ArrayList<>(List.of("Name", "Points", "Wins")));

      Helper.writeRecordTable("MultiPlayerTable.csv", header, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      Helper.writeRecordTable("MultiPlayerTable.csv", dataToWrite, StandardOpenOption.APPEND);

  } catch (IOException e) {
      e.printStackTrace();
  }
}

private boolean isNumeric(String str) {
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

}
