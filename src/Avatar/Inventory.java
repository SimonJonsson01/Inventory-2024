package Avatar;

import java.util.ArrayList;

import Item.*;

public class Inventory /* implements Comparable<Item> */ {
    private ArrayList<Item> itemList;

    public Inventory() {
        this.itemList = new ArrayList<Item>();
    }

    public void addItem(Item item) { // Lägger till <Item> i inventory.
        itemList.add(item);
    }

    public void removeItem(Item item) { // Tar bort <Item> i inventory
        itemList.remove(item); // TESTA DENNA METODEN
    }

    public void sellItem(Item item, Avatar player) {
        //System.out.println("Money before: "+player.getCoin());
        int sellValue = item.getGoldValue();
        //System.out.println("Price: " + sellValue);
        player.setCoin(player.getCoin() + sellValue);
        System.out.println(player.getName() + " sålde " + item.getName() + " för " + sellValue + " mynt.");
        removeItem(item);
    }

    // -------- getters & setters --------

    // Retunerar hela itemList.
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public <T extends Item> ArrayList<T> getItemListOf(Class<T> subclass) {
        // Retunerar en ArrayList<T> där T är en subklass till Item,
        // t ex: Weapon, Armor, Gems m.m.
        ArrayList<T> typeList = new ArrayList<T>();
        for (Item item : itemList) {
            if (subclass.isInstance(item)) {
                typeList.add(subclass.cast(item));
            }
        }
        typeList.sort((o1, o2) -> o1.getRarity().compareTo(o2.getRarity()));
        // Metod som sorterar allt innehåll efter deras "rarity".
        return typeList;
    }

    public int getTotalWeight(){
        int totalWeight = 0;
        for(Item item : itemList){
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }

}

// -------------------------------------------------------------------------------

