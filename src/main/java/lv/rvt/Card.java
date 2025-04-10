package lv.rvt;

public class Card {

    String color;
    int number;

    Card(String color, int number){

        this.color = color;
        this.number = number;

    }

    @Override
    public String toString() {

        switch (color) {
            case "Red":
                return ConsoleColors.RED + color + " " + number + ConsoleColors.RESET;
        
            case "Blue":
                return ConsoleColors.BLUE + color + " " + number + ConsoleColors.RESET;
            
            case "Green":
                return ConsoleColors.GREEN + color + " " + number + ConsoleColors.RESET;

            case "Yellow":
                return ConsoleColors.YELLOW + color + " " + number + ConsoleColors.RESET;
        }
        
        return color + " " + number;
        
    }

}
