package Enum;

import java.util.Random;

public enum Valuables {
    GOLDBAR(4),
    EMERALD(6),
    RUBY(8),
    DIAMOND(12);

    private static final Random random = new Random();
    public final int value;

    // Konstruktor
    private Valuables(int value){
        this.value = value;
    }

    // Returnerar värdet av kvaliten
    public int getGemValue(){
        return this.value;
    }

    // Returnerar kvaliten
    public String getGemName(){
        return this.name();
    }

    // Retunerar ett slumpmässigt "rarity". Kommer användas vid skapelse av fiende, items m.m.
    public static Valuables getRandomGem(){
        Valuables[] gems = values();
        return gems[random.nextInt(gems.length)];
    }

}