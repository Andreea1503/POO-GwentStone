package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Hero extends Card{
    public static int maxHealth = 30;
    public static int heroShifts = 1;

    boolean attack;

    public Hero() {

    }
    public Hero(CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = maxHealth;
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.attack = false;
    }

    public void subZero(ArrayList<Card> row, Hero playerHero) { //Lord Royce
        Card maxAttackCard = row.get(0);
        int maxAttack = 0;
        for (Card card : row) {
            if (card.attackDamage > maxAttack) {
                maxAttack = card.attackDamage;
                maxAttackCard = card;
            }
        }
        maxAttackCard.frozen = true;
        playerHero.attack = true;
    }

    public void lowBlow(ArrayList<Card> row, Hero playerHero) { //empress
        Card maxHealthCard = row.get(0);
        int maxHealth = 0;
        for (Card card : row) {
            if (card.health > maxHealth) {
                maxHealth = card.health;
                maxHealthCard = card;
            }
        }
        row.remove(maxHealthCard);
        playerHero.attack = true;
    }

    public void earthBorn(ArrayList<Card> row, Hero playerHero) { //king
        for (Card card : row) {
            card.health += 1;
        }
        playerHero.attack = true;
    }

    public void bloodThirst(ArrayList<Card> row, Hero playerHero) { //general
        for (Card card : row) {
            card.attackDamage += 1;
        }
        playerHero.attack = true;
    }

    @Override
    public boolean isAttack() {
        return attack;
    }

    @Override
    public void setAttack(boolean attack) {
        this.attack = attack;
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
