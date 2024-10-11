package Game;

import Avatar.*;
import Enum.*;

public class Game {
    private Avatar enemy;
    private Avatar player;
    private Avatar currentCharacter;
    private Avatar otherCharacter;

    public Game(String playerName) {
        this.player = new Player(playerName);
        this.enemy = new Enemy(Rarity.COMMON);
        this.currentCharacter = player;
        this.otherCharacter = enemy;

        System.out.println("Game init...");
        System.out.println("-----------------");
        gameLoop();
        // ------------- TEST AREA -------------

    }

    // UNDER SPELETS GÅNGS, SÅ ANVÄNDS currentCharacter OCH otherCharacter i plats
    // för player och enemy.
    // Prova bara använda currentCharacter OCH otherCharacter -> dum ide

    public void gameLoop() { // Metod som kör spelet tills spelaren dör.
        // Game.menuClean(5);
        while (player.isAlive() && (player.getInventory().getTotalWeight() < ((Player)player).getTotalCarryWeight())) {

            Game.menuClean(2);

            System.out.println("!-----------------" + currentCharacter.getName() + "s tur-----------------!");
            printHealthHUD();

            currentCharacter.takeTurn(otherCharacter);

            System.out.println();

            switchCharacters();

        }
        printFinalScore();
    }

    public void switchCharacters() {
        // Ska byta plats på avatarerna,
        Avatar temp = currentCharacter;
        currentCharacter = otherCharacter;
        otherCharacter = temp;
    }

    public static void menuClean(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println();
        }
    }

    public void printHealthHUD() {
        System.out.println("| " + player.getName() + ": " + player.getHP() + "/" + player.getMaxHP()
                + " VS "
                + enemy.getName() + ": " + enemy.getHP() + "/" + enemy.getMaxHP() + " |");
    }

    public void printFinalScore() {
        System.out.println("|------GAME OVER------|");
        System.out.println();
        if(player.getInventory().getTotalWeight() >= ((Player)player).getTotalCarryWeight()){
            System.out.println(player.getName() 
            + " blev överlastad och kunde inte fortsätta strida!");
            System.out.println(player.getInventory().getTotalWeight() + " / "+ ((Player)player).getTotalCarryWeight());
        } else if (!player.isAlive()) {
            System.out.println(player.getName() + " blev besegrad i strid.");
        } else {
            ((Player)player).statsMenu();
        }
        System.out.println();
        System.out.println("Name: " + player.getName());
        System.out.println("Antal saker i inventory: " + player.getInventory().getItemList().size() + " st");
        System.out.println();
        double enemyScoreMultiply = (double)1.0 + ((double)Enemy.getInstances() / 10);
        //System.out.println("enemyScoreMultiply: " + (double) enemyScoreMultiply);
        System.out.println("Antal fiender besegrade: " + Enemy.getInstances());
        System.out.println("Loot points (antal mynt "+ player.getName()+" hade): " + player.getCoin());
        double finalScore = (enemyScoreMultiply * (player.getCoin() == 0 ? 1 : player.getCoin()));
        System.out.println();
        System.out.println("Final score: " + ((int) finalScore));
        System.out.println("--------------Thanks for playing!--------------");
    }

    // -------- getters & setters --------

    public Avatar getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Avatar getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Avatar getCurrentAvatar() {
        return currentCharacter;
    }

    public <T extends Avatar> void setCurrentCharacter(T subAvatar) {
        this.currentCharacter = subAvatar;
    }
}
