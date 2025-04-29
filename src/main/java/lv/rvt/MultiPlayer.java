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

Scanner tableSortTypeInput = new Scanner(System.in);

ArrayList<String> playerNames = new ArrayList<String>();

ArrayList<ArrayList<Card>> allPlayersCards = new ArrayList<>();

ArrayList<Integer> playerPointsList = new ArrayList<>();

String playerInput = "";

ArrayList<Card> cards = new ArrayList<Card>();

String[] colors = {"Green", "Blue", "Yellow", "Red"};

int[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

int newWins;

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
                updatePlayersWin(playerNames, playerPointsList, playerNames.get(currentPlayerIndex));
                Helper.recordTable("MultiPlayerTable.csv");
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
    ArrayList<ArrayList<String>> recordsList = new ArrayList<>();

    
    try {
        List<String[]> rawRecords = Helper.readCsv("MultiPlayerTable.csv");
        for (String[] row : rawRecords) {
            recordsList.add(new ArrayList<>(List.of(row)));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

   
    if (recordsList.isEmpty()) {
        recordsList.add(new ArrayList<>(List.of("Name", "Points", "Wins")));
    }

    ArrayList<String> header = recordsList.get(0);
    List<ArrayList<String>> recordsRows = recordsList.subList(1, recordsList.size());

    
    for (int i = 0; i < playerNames.size(); i++) {
        String name = playerNames.get(i);
        int newPoints = playerPointsList.get(i);
        newWins = (i == winnerIndex) ? 1 : 0;
        boolean found = false;

        for (ArrayList<String> row : recordsRows) {
            if (row.get(0).equalsIgnoreCase(name)) {
                int existingPoints = Integer.parseInt(row.get(1));
                int existingWins = Integer.parseInt(row.get(2));
                row.set(1, String.valueOf(existingPoints + newPoints));
                row.set(2, String.valueOf(existingWins + newWins));
                found = true;
                break;
            }
        }

        if (!found) {
            ArrayList<String> newRow = new ArrayList<>();
            newRow.add(name);
            newRow.add(String.valueOf(newPoints));
            newRow.add(String.valueOf(newWins));
            recordsRows.add(newRow);
        }
    }

    
    System.out.println("Would you like to sort the leaderboard by points or by wins?");
    System.out.println("( P = points,   W = wins )");

    String sortInput = "";
    while (true) {
        sortInput = tableSortTypeInput.nextLine().trim().toUpperCase();

        if (sortInput.equals("P")) {
            recordsRows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(1)), Integer.parseInt(a.get(1))));
            break;
        } else if (sortInput.equals("W")) {
            recordsRows.sort((a, b) -> Integer.compare(Integer.parseInt(b.get(2)), Integer.parseInt(a.get(2))));
            break;
        } else {
            System.out.println("Please enter P or W.");
        }
    }

    ArrayList<ArrayList<String>> finalData = new ArrayList<>();
    finalData.add(header);
    finalData.addAll(recordsRows);

    try {
        Helper.writeRecordTable("MultiPlayerTable.csv", finalData, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
    }

  try {
      ArrayList<ArrayList<String>> header2 = new ArrayList<>();
      header2.add(new ArrayList<>(List.of("Name", "Points", "Wins")));

      Helper.writeRecordTable("MultiPlayerTable.csv", header2, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      Helper.writeRecordTable("MultiPlayerTable.csv", finalData, StandardOpenOption.APPEND);

  } catch (IOException e) {
      e.printStackTrace();
  }
}

public void updatePlayersWin(List<String> playerNames, List<Integer> playerPointsList, String winnerName) {
    try {
        List<String[]> rawData = Helper.readCsv("RecordsDataBase.csv");
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        
        if (rawData.isEmpty()) {
            data.add(new ArrayList<>(List.of("Name", "Points", "Wins")));
        } else {
            for (String[] row : rawData) {
                data.add(new ArrayList<>(List.of(row)));
            }
        }

        ArrayList<String> header = data.get(0);
        List<ArrayList<String>> rows = data.subList(1, data.size());

        for (int i = 0; i < playerNames.size(); i++) {
            String name = playerNames.get(i);
            int points = playerPointsList.get(i);
            boolean isWinner = name.equalsIgnoreCase(winnerName);
            boolean found = false;

            for (ArrayList<String> row : rows) {
                if (row.get(0).equalsIgnoreCase(name)) {
                    int oldPoints = Integer.parseInt(row.get(1));
                    int oldWins = Integer.parseInt(row.get(2));

                    row.set(1, String.valueOf(oldPoints + points));
                    row.set(2, String.valueOf(oldWins + (isWinner ? 1 : 0)));
                    found = true;
                    break;
                }
            }

            if (!found) {
                ArrayList<String> newRow = new ArrayList<>();
                newRow.add(name);
                newRow.add(String.valueOf(points));
                newRow.add(String.valueOf(isWinner ? 1 : 0));
                rows.add(newRow);
            }
        }

        
        ArrayList<ArrayList<String>> finalData = new ArrayList<>();
        finalData.add(header);
        finalData.addAll(rows);

        Helper.writeRecordTable("RecordsDataBase.csv", finalData, StandardOpenOption.TRUNCATE_EXISTING);

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
