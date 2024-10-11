package Enum;

import java.util.Random;

public enum ArmorType {
    LIGHT(6),
    MEDUIM(8),
    HEAVY(10);

    private static final Random random = new Random();
    public final int value;

    // Konstruktor
    private ArmorType(int value){
        this.value = value;
    }

    // Returnerar rustningens värde
    public int getArmorTypeValue(){
        return this.value;
    }

    // Returnerar typen av rustningen
    public String getArmorTypeName(){
        return this.name();
    }

    // Retunerar ett slumpmässigt "rustning".
    public static ArmorType getRandomArmor(){
        ArmorType[] armorTypes = values();
        return armorTypes[random.nextInt(armorTypes.length)];
    }

    
}
