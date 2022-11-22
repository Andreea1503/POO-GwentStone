package game;

import write.*;
import java.util.ArrayList;
/**
 * Information about all the types of cards and attack actions
 */
public class Card {
    private Integer mana;
    private Integer attackDamage;
    private Integer health;
    private String description;
    private ArrayList<String> colors = new ArrayList<>();
    private String name;
    private boolean frozen;

    /**
     * Used to verify if a card used its ability
     */
    private boolean usedAbility;

    /**
     * Used to verify if a card used its attack
     */
    private boolean attack;

    public Card() {

    }

    /**
     * Attack enemy cards on the table
     */
    public void attackCard(final Card attackedCard, final Card attackerCard,
                           final ArrayList<Card> tableRow) {
        attackedCard.setHealth(attackedCard.getHealth() - attackerCard.getAttackDamage());

        /**
         * if the enemy's health goes below 0, the card is removed from the table
         */
        if (attackedCard.getHealth() <= 0) {
            tableRow.remove(attackedCard);
        }

        attackerCard.setAttack(true);
    }

    /**
     * Attack enemy hero
     * @return true if the enemy dies and false otherwise
     */
    public boolean attackHero(final Card playerHero, final Card cardAttacker) {
        playerHero.setHealth(playerHero.getHealth() - cardAttacker.getAttackDamage());

        if (playerHero.getHealth() <= 0) {
            return true;
        }

        cardAttacker.setAttack(true);
        return false;
    }

    /**
     * Getter for card's mana
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * Setter for card's mana
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Getter for card's attack damage
     * @return attack damage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Setter for card's attack damage
     * @param attackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Getter for card's health
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter for card's health
     * @param health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Getter for card's description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for card's description
     * @param description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Getter for card's colors
     * @return colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Setter for card's colors
     * @param colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * Getter for card's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for card's name
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter for card's frozen attribute
     * @return frozen
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Setter for card's frozen attribute
     * @param frozen
     */
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * Getter for card's used ability attribute
     * @return usedAbility
     */
    public boolean isUsedAbility() {
        return usedAbility;
    }

    /**
     * Setter for card's used ability attribute
     * @param usedAbility
     */
    public void setUsedAbility(final boolean usedAbility) {
        this.usedAbility = usedAbility;
    }

    /**
     * Getter for card's attack attribute
     * @return attack
     */
    public boolean isAttack() {
        return attack;
    }

    /**
     * Setter for card's attack attribute
     * @param attack
     */
    public void setAttack(final boolean attack) {
        this.attack = attack;
    }
}
