package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Hero extends Card{
    public static int maxHealth = 30;
    public static int heroShifts = 1;
    boolean attack;

    public Hero(CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = maxHealth;
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
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
