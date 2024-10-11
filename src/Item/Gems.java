package Item;

import Avatar.Player;
import Enum.Rarity;
import Enum.Valuables;

public class Gems extends Item {

    public Gems(Rarity rarity, Valuables gem) {
        setName(rarity.getRarityName() + " " + gem.getGemName());
        setWeight(rarity.getRarityValue());
        setGoldValue(gem.getGemValue() * rarity.getRarityValue());
        setRarity(rarity);
    }


    public void printItem(){

    }

    @Override
    public void itemMenu(Player player) {
        boolean done = false;
        while (!done) {
            super.itemMenu(player);
            System.out.println("Vad vill du göra med " + getName() + "?");
            System.out.println("----");
            System.out.println("1. Sell " + getName());
            System.out.println("2. Gå tillbaka");
            int choice = player.getLimitInt(2);
            switch (choice) {
                case 1:
                    player.getInventory().sellItem(this, player);
                    done = true;
                    break;
                case 2:
                    done = true;
                    break;
            }
        }
    }
}
