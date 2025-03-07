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
        
        return color + " " + number;
        
    }

}
