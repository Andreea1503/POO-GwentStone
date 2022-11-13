package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Minion extends Card {
    boolean attack;
//    public Minion(int mana, int attackDamage, int health, String description,
//                  ArrayList<String> colors, String name) {
//        this.mana = mana;
//        this.attackDamage = attackDamage;
//        this.health = health;
//        this.description = description;
//        this.colors = colors;
//        this.name = name;
//        this.frozen = false;
//        this.attack = false;
//    }
    public Minion(CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.frozen = false;
        this.attack = false;
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }
}
