package Avatar;

import Item.*;

public abstract class Avatar {
    private String name;
    public static final int HP_MULTIPLY = 10; 
    // Statiskt värde som mulitpliceras med fiendes "rarityValue" för att skapa deras max hp.
    private int MAX_HP;
    private int hp = MAX_HP;
    private Weapon weapon;
    private Armor armor;
    private int coin = 0;
    private Inventory inventory = new Inventory();
    private static int die = rollDie();

    public Avatar() {
    }

    public abstract void takeTurn(Avatar entity); // Metoder för avatarernas turer i spelet
    // Parametern är för motsvarande entity; player.takeTurn(enemy);

    public boolean isAlive() { // Retunerar boolean baserat på om avataren har minst 1 hp.
        if (getHP() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public static int rollDie() { // Metod för att slå och retunera en 20-sidig tärning.
        die = (int) ((Math.random() * 20) + 1);
        return die;
    }

    public void attack(Avatar entity) { // Metod för attacker mellan avatarena.
        // Parametern är för motsvarande entity; player.attack(enemy);
        rollDie();
        System.out.println();
        if (getWeapon() != null) {
            // System.out.println("------");
            int entityAC = (entity.getArmor() != null) ? entity.getArmor().getArmorClass() : 1;
            System.out
                    .println(this.getName() + " attackerar " + entity.getName() + " med " + this.getWeapon().getName());
            System.out.println("...");
            if (die >= entityAC) {
                System.out.println(
                        this.getName() + " träffade " + entity.getName() + " med " + this.getWeapon().getName());
                getWeapon().use(entity);
            } else {
                System.out.println(
                        this.getName() + " missade " + entity.getName() + "!");
            }

        } else {
            System.out.println();
            System.out.println(this.getName() + " kunde inte attackera " + entity.getName() + " eftersom " +
                    this.getName() + " hade inget vapen!");
        }

        /*
         * while (this == (Player) this) { // FUNKAR DETTA ???
         * System.out.println("Du har inget vapen.");
         * break;
         * }
         */

    }

    public void printHealthBar() {
        System.out.println(this.getName() + ": " + getHP() + "/" + getMaxHP());
    }

    // -------- getters & setters --------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getMaxHP() {
        return MAX_HP;
    }

    public void setMaxHP(int max_hp) {
        this.MAX_HP = max_hp;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coins) {
        this.coin = coins;
    }

    public int getDie(){
        return die;
    }
}
