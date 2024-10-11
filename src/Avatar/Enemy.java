package Avatar;

import java.util.ArrayList;

import Enum.*;
import Item.*;

public class Enemy extends Avatar {
    private Rarity difficulty;
    private static int instances = 0; // Hur många gånger enemy har "respawned".

    public Enemy(Rarity rarity) { //
        setDifficulty(rarity);
        setName(rarity.getRarityName() + " ENEMY");
        setMaxHP(HP_MULTIPLY * rarity.value);
        setHP(getMaxHP());
        setWeapon(new Weapon(rarity, DamageType.getRandomWeapon()));
        setArmor(new Armor(rarity, ArmorType.getRandomArmor()));
        getInventory().addItem(getWeapon());
        getInventory().addItem(getArmor());
        getInventory().addItem(new Consumable(Rarity.getRandomRarity()));
        setCoin(rarity.getRarityValue() * 10);
        addLoot(rarity);
    }

    @Override
    public void takeTurn(Avatar player) {
        System.out.println();
        if (isAlive()) {
            calculateChoice(player);
        } else {
            // System.out.println("DEAD :(");

            // Ökar spelarens Max HP med 1.
            setInstances(getInstances() + 1);
            player.setMaxHP(player.getMaxHP() + 1);

            // Säger att fienden är besegrad.
            dyingLogic(player);
            System.out.println();
            // Skickar över fiendens inventory + extra föremål till spelaren
            dropLoot(player);
            System.out.println();
            // Bytar ut den besegrade fienden mot en ny fiende.
            replaceEnemy();
            System.out.println();
            System.out.println("New enemy appears, it's a " + getName() + "!");
        }
    }

    public void dyingLogic(Avatar player) {
        // Hanterar vad som händer när fienden dör.
        // Tidiare hantade denna metod all "dödslogik", säger nu bara att fienden är
        // besegrad.
        System.out.println("-----VICTORY!!!-----");
        System.out.println(this.getName() + " is defeated!");
    }

    public void heal() {
        // Metod för att fienden ska kunna återhämta HP med en brygd.
        System.out.println(this.getName() + " tar en dryck!");
        ArrayList<Consumable> consumableList = getInventory().getItemListOf(Consumable.class);
        //System.out.println("heal.list.size:" + consumableList.size());
        if (!consumableList.isEmpty()) {
            Consumable potion = consumableList.get(0);
            potion.use(this);
        }
    }

    public void calculateChoice(Avatar player) { // Beräknar om vad fienden ska göra.
        // Denna är mest för att kolla om fienden ska en dryck innan en attack mot
        // spelaren.
        // rarity.getRarityValue() ska kunna öka fiendes chans att ta en dryck när de
        // har låg HP. Mellan 20 - 80 %.
        // Ytterligare krav som kollar om fiendens hp är under hälften av deras max hp.
        ArrayList<Consumable> list = getInventory().getItemListOf(Consumable.class);
        int dieTotal = 100;
        int chance = 0 + (this.getDifficulty().getRarityValue() * 10);
        int roll = (int) (Math.random() * dieTotal);
        if (roll < chance && !list.isEmpty() && getHP() <= (getMaxHP() / 2)) {
            heal();
        } else {
            attack(player);
        }
    }

    public void addLoot(Rarity rarity) { // Metod för att lägga till extra värdesaker hos fiende
        int lootAmount = rarity.getRarityValue();
        for (int i = 0; i < lootAmount; i++) {
            getInventory().addItem(new Gems(Rarity.getRandomRarity(), Valuables.getRandomGem()));
        }
    }

    public void dropLoot(Avatar player) { // Metod för att överföra <Item> från fiende till spelare.
        // Denna metod ska köras när fienden dör.
        // ConcurrentModificationException - Occurs when an element is added or removed
        // from iterables.

        // Spelaren får fiendens mynt
        int droppedCoins = this.getCoin();
        player.setCoin(player.getCoin() + droppedCoins);

        // Spelaren får en brygd med rarity matchande fiende, som inte var i fiendes
        // Inventory.
        // Detta för att kunna få ta i brygder från fiende oavsett om de drack något
        // under striden.
        Consumable tempConsumable = new Consumable(this.getDifficulty());
        if (((Player) player).checkIfSpaceAvailable(tempConsumable)) {
            System.out.println("Hittade " + tempConsumable.getName());
            player.getInventory().addItem(tempConsumable);
        }

        // Extra loot,
        Item extraLoot = createExtraLoot();
        if (((Player) player).checkIfSpaceAvailable(extraLoot)) {
            System.out.println("Hittade " + extraLoot.getName());
            player.getInventory().addItem(extraLoot);
        }

        // Plockar allt från fiendens Inventory och ger det till spelaren.
        for (Item item : this.getInventory().getItemList()) {
            if (!((Player) player).checkIfSpaceAvailable(item)) {
                System.out.println("Hittade " + item.getName()
                        + ", \nKunde inte plocka upp den utan att bli överbelastad, \nsälj föremål för att minska vikten!");
                System.out.println();
            } else {
                System.out.println("Hittade " + item.getName());
                player.getInventory().addItem(item);
            }
        }
        // Sedan rensar fiendens Inventory.
        this.getInventory().getItemList().clear();
    }

    public Item createExtraLoot() {
        // Metod för möjlighet för extra loot för spelaren.
        // Kan retunera allt mellan vapen, rustning, drycker och värdesaker.
        // Resultatet kommer från en 20-sidig tärning, en d20.
        // En 20 och en 1 ger unika föremål.
        Item loot;
        rollDie();
        int result = getDie();
        if (result == 20) {
            loot = new Armor(Rarity.LEGENDARY, ArmorType.HEAVY);
            loot.setName("The One True Armor");
            loot.setGoldValue(loot.getGoldValue() * 2);
            ((Armor) loot).setArmorClass(((Armor) loot).getArmorClass() + 1);
        } else if (result > 15) {
            loot = new Armor(Rarity.getRandomRarity(), ArmorType.getRandomArmor());
        } else if (result <= 15 && result > 10) {
            loot = new Weapon(Rarity.getRandomRarity(), DamageType.getRandomWeapon());
        } else if (result <= 10 && result > 5) {
            loot = new Consumable(Rarity.getRandomRarity());
        } else if (result == 1) {
            loot = new Gems(Rarity.RARE, Valuables.GOLDBAR);
            loot.setName("Odd rock?");
        } else {
            loot = new Gems(Rarity.getRandomRarity(), Valuables.getRandomGem());
        }
        return loot;
    }

    public void replaceEnemy() {
        // Metod som bytar ut allt i Enemy till en "ny" Enemy, likt "Ship of Theseus".
        if (!this.getInventory().getItemList().isEmpty()) {
            this.getInventory().getItemList().clear();
        }
        Rarity rarity;
        if (instances < 3) {
            rarity = Rarity.RARE;
        } else if (instances < 5) {
            rarity = Rarity.EPIC;
        } else {
            rarity = Rarity.getRandomRarity();
        }
        setDifficulty(rarity);
        setName(rarity.getRarityName() + " ENEMY");
        setMaxHP(HP_MULTIPLY * rarity.value);
        setHP(getMaxHP());
        setWeapon(new Weapon(rarity, DamageType.getRandomWeapon()));
        setArmor(new Armor(rarity, ArmorType.getRandomArmor()));
        getInventory().addItem(getWeapon());
        getInventory().addItem(getArmor());
        for (int i = 0; i < (rarity.getRarityValue() / 2); i++) {
            getInventory().addItem(new Consumable(Rarity.getRandomRarity()));
        }
        setCoin(rarity.getRarityValue() * 10);
        addLoot(rarity);
    }

    // -------- getters & setters --------

    public Rarity getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Rarity rarity) {
        this.difficulty = rarity;
    }

    public static int getInstances() {
        return Enemy.instances;
    }

    public static void setInstances(int instance) {
        Enemy.instances = instance;
    }

}
