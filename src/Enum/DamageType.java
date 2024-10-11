package Enum;

import java.util.Random;

public enum DamageType {
    CLUB("BLUDGEONING"),
    BOW("PIERCING"),
    SWORD("SLASHING");

    private static final Random random = new Random();
    public final String damageType;

    // Konstruktor
    private DamageType(String damageType){
        this.damageType = damageType;
    }

    // Returnerar sorten av skada från vapnet
    public String getDamageTypeValue(){
        return this.damageType;
    }

    // Returnerar vilken sorts vapen.
    public String getDamageTypeName(){
        return this.name();
    }

    // Retunerar ett slumpmässigt "rarity". Kommer användas vid skapelse av fiende, items m.m.
    public static DamageType getRandomWeapon(){
        DamageType[] armorTypes = values();
        return armorTypes[random.nextInt(armorTypes.length)];
    }

    
}

    /* public final String damageType;

    private DamageType(String damageType){
        this.damageType = damageType;
    }
}
 */