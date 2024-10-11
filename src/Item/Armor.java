package Item;

import Avatar.*;
import Interface.IEquippable;
import Enum.*;

public class Armor extends Item implements IEquippable {
    private int armorClass; // Total armor som rustningen ger. MAX_ARMOR är 18.
    private String armorType;

    public Armor(Rarity rarity, ArmorType armor) {
        setName(rarity.getRarityName() + " " + armor.getArmorTypeName() + " ARMOR");
        setRarity(rarity);
        setWeight(rarity.getRarityValue() + armor.getArmorTypeValue());
        setGoldValue(rarity.getRarityValue() * armor.getArmorTypeValue());
        setArmorType(armor.getArmorTypeName());
        setArmorClass(rarity.getRarityValue() + armor.getArmorTypeValue());
    }

    public void equip(Avatar entity) { // Metod för att "equipa" rustning på avatar.
        if (entity.getArmor() == null) {
            entity.setArmor(this);
            System.out.println(entity.getName() + " tar på sig " + this.getName() + "!");
        } else if (entity.getArmor() != this) {
            swap(entity);
        } else if (entity.getArmor() == this) {
            System.out.println(entity.getName() + " har redan på sig " + this.getName());
        }
    }

    public void unequip(Avatar entity) { // Metod för att ta av rustning på avatar.
        if (entity.getArmor() != this) {
            swap(entity);
        } else if (entity.getArmor() != null) {
            entity.setArmor(null);
            System.out.println(entity.getName() + " tar av sig " + this.getName() + "!");
        }
    }

    public void swap(Avatar entity) {
        boolean done = false;
        while (!done) {
            System.out.println(entity.getName() + " har redan " + entity.getArmor().getName() + " på sig!");
            System.out.println("Vill du byta rustning?");
            System.out.println(entity.getArmor().getName() + ": " + entity.getArmor().getArmorClass() + " -> "
                    + this.getName() + ": " + this.getArmorClass());
            System.out.println("1. Ja");
            System.out.println("2. Nej");
            int choice = ((Player) entity).getLimitInt(2);
            switch (choice) {
                case 1:
                    entity.setArmor(this);
                    done = true;
                    break;
                case 2:
                    done = true;
                    break;
            }
        }
    }

    /*
     * @Override
     * public void printItem(){
     * super.printItem();
     * System.out.println("Armorklass: " + getArmorClass());
     * }
     */
    @Override
    public String getInfo() {
        return super.getInfo() + " - AC: " + getArmorClass();
    }

    @Override
    public void itemMenu(Player player) {
        boolean done = false;
        while (!done) {
            super.itemMenu(player);
            System.out.println("AC: " + getArmorClass());
            try {
                System.out.println("Equipped: " + ((player.getArmor() == (this)) ? "Yes" : "No"));
            } catch (NullPointerException e) {
                System.out.println("Equipped: No");
            }
            System.out.println("----");
            System.out.println("1. Equip on " + player.getName());
            System.out.println("2. Unequip on " + player.getName());
            System.out.println("3. Sell " + getName());
            System.out.println("4. Gå tillbaka");
            int choice = player.getLimitInt(4);
            switch (choice) {
                case 1:
                    this.equip(player);
                    break;
                case 2:
                    this.unequip(player);
                    break;
                case 3:
                    try {
                        if (player.getArmor() != this || player.getWeapon() == null) {
                            player.getInventory().sellItem(this, player);
                        } else if (player.getArmor().equals(this)) {
                            System.out.println();
                            System.out.println(player.getName() + " måste ta av sig " + getName() +
                                    " innan de kan sälja den!");
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e);
                    }
                    done = true;
                    break;
                case 4:
                    done = true;
                    break;
            }
        }
    }

    // -------- getters & setters --------
    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public String getArmorType() {
        return armorType;
    }

    public void setArmorType(String armorType) {
        this.armorType = armorType;
    }

}

/*
 * public Armor(String name, int weight, int goldValue, Rarity rarity, int
 * armorValue, String armorType) {
 * super(name, weight, goldValue, rarity);
 * this.armorValue = armorValue;
 * this.armorType = armorType;
 * }
 */