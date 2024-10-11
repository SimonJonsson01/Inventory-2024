package Item;

import Avatar.*;
import Enum.Rarity;
import Interface.IUsable;

public class Consumable extends Item implements IUsable{
    private static int baseHealing = 10;
    private int healingValue;

    public Consumable(Rarity rarity) {
        setName(rarity.getRarityName() + " Health Potion");
        setGoldValue(rarity.getRarityValue() * baseHealing);
        setRarity(rarity);
        setWeight(rarity.getRarityValue());
        setHealingValue(rarity.getRarityValue() * baseHealing);
    }

    public void use(Avatar entity) { // Entity dricker potion
        //System.out.println("FÖRE HEAL: "+entity.getInventory().getItemListOf(Consumable.class).size());
        int currentHealth = entity.getHP();
        if ((currentHealth + this.getHealingValue() > entity.getMaxHP())){
            int missingHP = (entity.getMaxHP() - entity.getHP());
            System.out.println(entity.getName() + " återhämtade " + missingHP 
            + " HP och är fullständigt återhämtad!");
            entity.setHP(entity.getMaxHP());
        } else {
            System.out.println(entity.getName() + " återhämtade " + getHealingValue() + " HP!");
            entity.setHP(currentHealth += this.getHealingValue());
        }
        entity.getInventory().getItemList().remove(this); // Testad -> funkar???
        //System.out.println("EFTER HEAL: "+entity.getInventory().getItemListOf(Consumable.class).size());
    }

    @Override
    public void itemMenu(Player player) {
        boolean done = false;
        while (!done) {
            super.itemMenu(player);
            System.out.println("Restore: " + getHealingValue() + " HP");
            System.out.println("Vad vill du göra med " + getName() + "?");
            System.out.println("----");
            System.out.println("1. Use on " + player.getName());
            System.out.println("2. Gå tillbaka");
            int choice = player.getLimitInt(2);
            switch (choice) {
                case 1:
                    this.use(player);
                    done = true;
                    break;
                case 2:
                    done = true;
                    break;
            }
        }
    }

    @Override
    public String getInfo(){
        return super.getInfo() + " - +" + getHealingValue()+ " HP";
    }

    public String printConsumable(){
        return getName() + ": Restore " + getHealingValue() + " HP";
    }
    // -------- getters & setters --------
    public int getHealingValue(){
        return healingValue;
    }
    public void setHealingValue(int healingValue){
        this.healingValue = healingValue;
    }  
}
