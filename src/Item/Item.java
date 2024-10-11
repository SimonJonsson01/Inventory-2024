package Item;

import Avatar.*;
import Enum.Rarity;
import Game.Game;

public abstract class Item {
    private String name;
    private int weight;
    private int goldValue;
    private Rarity rarity;

    public Item() {
    } // Tom konstruktor,

    // Meny för när man har valt detta Item i Inventory
    // Alla subklasser ska ha unika interaktioner baserat på subklassen.
    public void itemMenu(Player player){
        Game.menuClean(5);
        System.out.println("----" + getName() + "----");
        System.out.println("Vikt: " + getWeight() + " kg");
        System.out.println("Värde: " + getGoldValue() + " (i mynt)");
        System.out.println("Kvalitet: " + getRarity());
    }  

    public String getInfo() { // Returnerar värden för <Item>.
        return getName() + " - " + getWeight() + " kg - " + getGoldValue() + " mynt";
    } 

    /* public void printItemDisplayed(int index) { // Printar ut värde för <Item>.
        System.out.println(index + ". " + getName() + " - " + getGoldValue() + " mynt");       
    } */

    // -------- getters & setters --------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getGoldValue() {
        return goldValue;
    }

    public void setGoldValue(int goldValue) {
        this.goldValue = goldValue;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

}
