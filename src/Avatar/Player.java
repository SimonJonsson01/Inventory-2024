package Avatar;

import java.util.ArrayList;
import java.util.Scanner;
import Enum.*;
import Game.*;
import Item.*;

public class Player extends Avatar {
    private static Scanner scan = new Scanner(System.in);
    private static int startHP = 80;
    private int totalCarryWeight = 150; // Ska denna variabelen användas ?
    // ? ?

    public Player(String name) {
        setName(name);
        setMaxHP(startHP);
        setHP(getMaxHP());
        setWeapon(new Weapon(Rarity.COMMON, DamageType.getRandomWeapon()));
        setArmor(new Armor(Rarity.COMMON, ArmorType.getRandomArmor()));
        getInventory().addItem(getWeapon());
        getInventory().addItem(getArmor());
        getInventory().addItem(new Consumable(Rarity.COMMON));
        
    }

    @Override
    public void takeTurn(Avatar enemy) {
        // Körs när det är spelarens tur
        // Huvudmeny för spelaren
        // I Game.java så tar den in currentCharacter (som är då player i ögonblicket).
        boolean done = false;
        while (!done) {
            Game.menuClean(1);
            System.out.println("Vad vill du göra?");
            //System.out.println("Limit: "+getTotalCarryWeight());
            //System.out.println("currentKg: "+getInventory().getTotalWeight());

            if((getTotalCarryWeight()) < getInventory().getTotalWeight() + 25 ){
                System.out.println(getName() + " bär nuvarande " +getInventory().getTotalWeight() + " kg.");
                System.out.println("Om " + getName() + "s inventoryvikt är över " + getTotalCarryWeight()
                + "kg, så blir " + getName() 
                + " överbelastad \noch kan inte fortsätta strida! " 
                + "\nSälj föremål för att minska vikten!");

            }
            System.out.println("----");
            System.out.println("1. Attackera din motståndare - Träffchans: " + getHitChance(enemy) + " %");
            System.out.println("2. Ta en återhämtningsbrygd.");
            System.out.println("3. Kolla spelarestatitik");
            System.out.println("4. Kolla din inventory");
            System.out.println("5. Sälj alla värdesaker för mynt");
            System.out.println("6. Skip turn");
            int choice = getLimitInt(6);
            switch (choice) {
                case 1:
                    this.attack(enemy);
                    done = true;
                    break;
                case 2:
                    subInventoryMenu(Consumable.class);
                    break;
                case 3:
                    statsMenu();
                    break;
                case 4:
                    inventoryMenu();
                    break;
                case 5:
                    sellAllGemsMenu();
                    break;
                case 6:
                    done = true;
                    break;
            }
        }
    }

    // Inventorymenu för spelaren, väljer vilken kategori och kommer till en submeny för den kategorin.
    public void inventoryMenu() {
        boolean done = false;
        while (!done) {
            Game.menuClean(2);
            System.out.println("------Inventory------");
            System.out.println(getName() + " har " + getInventory().getItemList().size() + " saker på sig.");
            System.out.println("Total weight: " + getInventory().getTotalWeight());
            System.out.println("Vilken kategori vill du kolla i?");
            System.out.println("1. Vapen (" + getInventory().getItemListOf(Weapon.class).size() + ")");
            System.out.println("2. Rustning (" + getInventory().getItemListOf(Armor.class).size() + ")");
            System.out.println("3. Brygder (" + getInventory().getItemListOf(Consumable.class).size() + ")");
            System.out.println("4. Värdesaker (" + getInventory().getItemListOf(Gems.class).size() + ")");
            System.out.println("5. Gå tillbaka");
            int choice = getLimitInt(5);
            switch (choice) {
                case 1:
                    subInventoryMenu(Weapon.class);
                    break;
                case 2:
                    subInventoryMenu(Armor.class);
                    break;
                case 3:
                    subInventoryMenu(Consumable.class);
                    break;
                case 4:
                    subInventoryMenu(Gems.class);
                    break;
                case 5:
                    done = true;
                    break;
            }
        }
    }

    // Submeny för spelaren, printar en lista över Items som tillhör vald kategori,
    // samt möjlighet att välja vilken Item som man vill interagera med
    public <T extends Item> void subInventoryMenu(Class<T> subClass) {
        Game.menuClean(5);
        ArrayList<T> typeList = getInventory().getItemListOf(subClass);
        System.out.println("------" + subClass.getSimpleName() + " Inventory------");
        if (!typeList.isEmpty()) {
            int i = 0;
            for (T item : typeList) {
                System.out.println((i + 1) + ". " + item.getInfo());

                i++;
            }
            System.out.println((typeList.size() + 1) + ". Gå tillbaka");
            System.out.println("Vilken vill du interagera med?:");

            int choice = getLimitInt((typeList.size() + 1));
            if (choice != (typeList.size() + 1)) {
                typeList.get(choice - 1).itemMenu(this);
            }
        } else {
            System.out.println(getName() + " har inget av denna sort på sig!");
        }
    }

    public void sellAllGemsMenu() {
        // Meny för att sammanfatta vinst för att sälja all värdesaker
        boolean confirm = false;
        ArrayList<Gems> list = getInventory().getItemListOf(Gems.class);
        System.out.println();
        if (!list.isEmpty()) {
            while (!confirm) {
                int totalSum = 0;
                System.out.println("------ Sell menu ------");
                for (Gems gem : list) {
                    System.out.println(gem.getInfo());
                    totalSum += gem.getGoldValue();
                }
                System.out.println("------");
                System.out.println("Vill du sälja alla dina värdesaker för " + totalSum + " mynt?");
                System.out.println("1. Ja");
                System.out.println("2. Nej");
                int choice = getLimitInt(2);
                switch (choice) {
                    case 1:
                        setCoin(getCoin() + totalSum);
                        getInventory().getItemList().removeAll(list);
                        System.out.println();
                        System.out.println(getName() + " sålde alla sina värdesaker för " + 
                        totalSum + " mynt!");
                        System.out.println(getName() + " har nu " + getCoin() + " mynt!");
                        confirm = true;
                        break;
                    case 2:
                        confirm = true;
                        break;
                }
            }
        } else {
            System.out.println(getName() + " har inga värdesaker på sig!");
        }
    }

    public int getHitChance(Avatar entity) { // Returnerar träffchans av fiende (i procent) DONE
        double AC = entity.getArmor().getArmorClass();
        double hitDChance = ((1 - (AC / 20)) * 100);
        int hitChance = (int) Math.round(hitDChance);
        return hitChance;
    }

    public void statsMenu() {
        System.out.println("-----------Statistik HUD-----------");
        System.out.println("Namn: " + getName());
        System.out.println("Hälsa: " + getHP() + " / " + getMaxHP() + " HP");
        if (getWeapon() != null) {
            System.out.println("Vapen: " + getWeapon().getName() + " - " + getWeapon().getAllDamageInfo());
        } else {
            System.out.println("Vapen: Inget aktivt");
        }
        if (getArmor() != null) {
            System.out.println("Rustning: " + getArmor().getName() + " - " + getArmor().getArmorClass() + " AC");
        } else {
            System.out.println("Rustning: Inget aktivt");
        }
        System.out.println("Mynt: " + getCoin());
        System.out.println("Fiender besegrade: " + Enemy.getInstances());
        System.out.println("Bärkapacitet: " + getInventory().getTotalWeight()  + " / " + getTotalCarryWeight() + " kg");
        System.out.println();
    }

    public boolean checkIfSpaceAvailable(Item item){
        if ((this.getInventory().getTotalWeight() + item.getWeight()) 
        >= this.getTotalCarryWeight()){
            return false;
        } else {
            return true;
        }

    }

    // -------- getters & setters --------
    public Scanner getScanner() { // Hämtar spelarens Scanner
        return scan;
    }

    public int getTotalCarryWeight(){
        return totalCarryWeight;
    }

    // Retunerar int mellan 1 (inklusivt) och int limit (inklusivt). T ex: limit = 7
    // retunerar 1-7.
    public int getLimitInt(int limit) {
        boolean validChoice = false;
        int choice = -1;
        while (!validChoice) {
            System.out.println("--Skriv ditt val (i nummer):--");
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                if (choice > 0 && choice < (++limit)) {
                    validChoice = true;
                } else {
                    System.out.println("Felaktigt nummer!");
                    System.out.println("Ditt val: " + choice);
                    System.out.println("Välj ett nummer mellan 1 och " + limit + "!");
                }
            } else {
                System.out.println("Fel input, prova igen!");
                System.out.println("Välj ett nummer mellan 1 och " + limit + "!");
                scan.next();
            }
        }
        return choice;
    }

    public static String setPlayerName() { // Set spelarens namn i början av spelet. Statisk p.g.a en-gång-använding
        System.out.println("Välj spelarens namn, tryck sedan Enter för att bekräfta");
        String playername = scan.nextLine();
        return playername;
    }

}
