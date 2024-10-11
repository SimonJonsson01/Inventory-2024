package Interface;

import Avatar.*;

public abstract interface IEquippable {
    void equip(Avatar entity);
    void unequip(Avatar entity);
    void swap(Avatar entity);
}
