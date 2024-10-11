package Enum;

import java.util.Random;

public enum Rarity {
    COMMON(1),
    RARE(3),
    EPIC(5),
    LEGENDARY(7);

    private static final Random random = new Random();
    public final int value;

    // Konstruktor
    private Rarity(int value) {
        this.value = value;
    }

    // Returnerar värdet av kvaliten
    public int getRarityValue() {
        return this.value;
    }

    // Returnerar kvaliten
    public String getRarityName() {
        return this.name();
    }

    // Retunerar ett slumpmässigt "rarity". Kommer användas vid skapelse av fiende,
    // items m.m.
    public static Rarity getRandomRarity() {
        Rarity[] rarities = values();
        return rarities[random.nextInt(rarities.length)];
    }

}
