package Game;

import java.util.ArrayList;

public class Minion extends Card {
    boolean attack;
    public Minion(int mana, int attackDamage, int health, String description,
                  ArrayList<String> colors, String name) {
        this.mana = mana;
        this.attackDamage = attackDamage;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.frozen = false;
        this.attack = false;
    }
}
