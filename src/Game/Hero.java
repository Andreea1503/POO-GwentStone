package Game;

import java.util.ArrayList;

public class Hero extends Card{
    public static int maxHealth = 30;
    public static int heroShifts = 1;
    boolean attack;

    public Hero(int mana, int attackDamage, String description,
                ArrayList<String> colors, String name) {
        this.mana = mana;
        this.attackDamage = attackDamage;
        this.health = maxHealth;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.attack = false;
    }

    public void subZero(ArrayList<Card> row) { //Lord Royce
        Card maxAttackCard = null;
        int maxAttack = 0;
        for (Card card : row) {
            if (card.attackDamage > maxAttack) {
                maxAttack = card.attackDamage;
                maxAttackCard = card;
            }
        }
        maxAttackCard.frozen = true;
        attack = true;
    }

    public void lowBlow(ArrayList<Card> row) { //empress
        Card maxHealthCard = null;
        int maxHealth = 0;
        for (Card card : row) {
            if (card.health > maxHealth) {
                maxHealth = card.health;
                maxHealthCard = card;
            }
        }
        row.remove(maxHealthCard);
        attack = true;
    }

    public void earthBorn(ArrayList<Card> row) { //king
        for (Card card : row) {
            card.health += heroShifts;
        }
        attack = true;
    }

    public void bloodThirst(ArrayList<Card> row) { //general
        for (Card card : row) {
            card.attackDamage += heroShifts;
        }
        attack = true;
    }
}
