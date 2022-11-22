package game;
import write.*;

import fileio.CardInput;

import java.util.ArrayList;

/**
 * Information about the hero cards and the spells they can cast
 */
public class Hero extends Card {
    public static final int MAX_HEALTH = 30;
    private boolean attack;

    public Hero() {

    }
    public Hero(final CardInput card) {
        this.setMana(card.getMana());
        this.setAttackDamage(card.getAttackDamage());
        this.setHealth(MAX_HEALTH);
        this.setDescription(card.getDescription());
        this.setColors(card.getColors());
        this.setName(card.getName());
        this.attack = false;
    }

    /**
     * Search for the card with the maximum attack damage in the specified row and set its frozen
     * attribute to true
     * @param row
     * @param playerHero
     */
    public void subZero(final ArrayList<Card> row, final Hero playerHero) {
        Card maxAttackCard = row.get(0);
        int maxAttack = 0;
        for (Card card : row) {
            if (card.getAttackDamage() > maxAttack) {
                maxAttack = card.getAttackDamage();
                // storing the maxium attack damage card from the row
                maxAttackCard = card;
            }
        }
        maxAttackCard.setFrozen(true);
        playerHero.setAttack(true);
    }

    /**
     * Searching for the card with the maximum health and deleting it from the
     * specified row
     * @param row
     * @param playerHero
     */
    public void lowBlow(final ArrayList<Card> row, final Hero playerHero) {
        Card maxHealthCard = row.get(0);
        int maxHealth = 0;
        for (Card card : row) {
            if (card.getHealth() > maxHealth) {
                maxHealth = card.getHealth();
                // storing the maximum health card from the row
                maxHealthCard = card;
            }
        }
        row.remove(maxHealthCard);
        playerHero.setAttack(true);
    }

    /**
     * Adding +1 health for every card from the specified row
     * @param row
     * @param playerHero
     */
    public void earthBorn(final ArrayList<Card> row, final Hero playerHero) {
        for (Card card : row) {
            card.setHealth(card.getHealth() + 1);
        }
        playerHero.setAttack(true);
    }

    /**
     * Adding +1 attack damage for every card from the specified row
     * @param row
     * @param playerHero
     */
    public void bloodThirst(final ArrayList<Card> row, final Hero playerHero) {
        for (Card card : row) {
            card.setAttackDamage(card.getAttackDamage() + 1);
        }
        playerHero.setAttack(true);
    }

    /**
     * Getter for the attack attribute of the card
     * @return attack
     */
    @Override
    public boolean isAttack() {
        return attack;
    }

    /**
     * Setter for the attack attribute of the card
     * @param attack
     */
    @Override
    public void setAttack(final boolean attack) {
        this.attack = attack;
    }
}
