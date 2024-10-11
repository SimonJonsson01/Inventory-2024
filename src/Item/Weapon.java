package Item;

import Avatar.*;
import Interface.IEquippable;
import Interface.IUsable;
import Enum.*;

public class Weapon extends Item implements IEquippable, IUsable {
    private int damageMin;
    private int damageMax;
    private String damageType;

    public Weapon(Rarity rarity, DamageType weapon) { // Startvapen
        setName(rarity.getRarityName() + " " + weapon.getDamageTypeName());
        setWeight(rarity.getRarityValue());
        setGoldValue(rarity.getRarityValue() * getWeight());
        setRarity(rarity);
        setDamageMin(rarity.getRarityValue());
        setDamageMax(rarity.getRarityValue() * 2);
        setDamageType(weapon.getDamageTypeValue());
    }

    public void use(Avatar entity) { // Attackmetod som ska fungera för både spelaren och fienden.
        int critChance = (int) ((Math.random() * 6) + 1);
        int extraDamage;
        System.out.println();
        if (critChance == 6) {
            System.out.println("CRITICAL HIT! +5 DMG");
            extraDamage = 5;
        } else {
            extraDamage = 0;
        }
        int finalDamage = getRandomDamage() + extraDamage;
        int entityHP = entity.getHP();
        entity.setHP(entityHP - finalDamage);
        System.out.println(entity.getName() + ": -" + finalDamage + " HP");
    }

    public int getRandomDamage() { // Slumpar fram nummer mellan damageMin och damageMax t ex: 5-8.
        int range = (getDamageMax() - getDamageMin() + 1);
        int finalDamage = (int) (Math.random() * range) + getDamageMin();
        // System.out.println(getDamageMin() + "-" + getDamageMax());
        // System.out.println(finalDamage);
        return finalDamage;
    }

    public void equip(Avatar entity) { // Metod som gör vapnet "aktiv".
    if (entity.getWeapon() == null) {
        entity.setWeapon(this);
        System.out.println(entity.getName() + " tar fram " + this.getName() + "!");
    } else if (entity.getWeapon() != this) {
        swap(entity);
    } else if (entity.getWeapon() == this) {
        System.out.println(entity.getName() + " har redan " + this.getName() + " aktiv!");
    }
    }

    public void unequip(Avatar entity) { // Metod som "lägger undan" vapnet.
        if (entity.getWeapon() != null) {
            entity.setWeapon(null);
            System.out.println(entity.getName() + " la undan " + this.getName() + "!");
        } else if (entity.getWeapon() != this) {
            swap(entity);
        }
    }

    public void swap(Avatar entity) {
        boolean done = false;
        while (!done) {
            System.out.println(entity.getName() + " har redan " + entity.getWeapon().getName() + " aktiv!");
            System.out.println("Vill du byta vapen?");
            System.out.println(entity.getWeapon().getName() + ": " + entity.getWeapon().getAllDamageInfo() + " -> "
                    + this.getName() + ": " + this.getAllDamageInfo());
            System.out.println("1. Ja");
            System.out.println("2. Nej");
            int choice = ((Player) entity).getLimitInt(2);
            switch (choice) {
                case 1:
                    entity.setWeapon(this);
                    done = true;
                    break;
                case 2:
                    done = true;
                    break;
            }
        }
    }

    @Override
    public String getInfo() {
        return super.getInfo() + " - " + getDamageMin() + "-" + getDamageMax() + " " + getDamageType();
    }

    @Override
    public void itemMenu(Player player) {
        boolean done = false;
        while (!done) {
            super.itemMenu(player);
            System.out.println("DMG: " + getAllDamageInfo());
            try {
                System.out.println("Equipped: " + ((player.getWeapon().equals(this)) ? "Yes" : "No"));
            } catch (NullPointerException e) {
                System.out.println("Equipped: No");
            }
            System.out.println("Vad vill du göra med " + getName() + "?");
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
                        if (player.getWeapon() != this || player.getWeapon() == null) {
                            player.getInventory().sellItem(this, player);
                        } else if (player.getWeapon().equals(this)) {
                            System.out.println();
                            System.out.println(player.getName() + " måste lägga undan " + getName() +
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
    public int getDamageMin() {
        return damageMin;
    }

    public void setDamageMin(int damageMin) {
        this.damageMin = damageMin;
    }

    public int getDamageMax() {
        return damageMax;
    }

    public void setDamageMax(int damageMax) {
        this.damageMax = damageMax;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public String getAllDamageInfo() {
        return getDamageMin() + "-" + getDamageMax() + " " + getDamageType();
    }

}
