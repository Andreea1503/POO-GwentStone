package game;
import write.*;
import fileio.CardInput;

import java.util.ArrayList;

/**
 * Information about the card spells
 */
public class Spells extends Minion {
    public Spells() {

    }
    public Spells(final CardInput card) {
        super(card);
        this.setUsedAbility(false);
    }

    /**
     * Decreasing with 2 the attack damage for the attacked card and if it's
     * negative, setting it to 0
     * @param cardAttacked
     * @param cardAttacker
     */
    public void weakKnees(final Card cardAttacked, final Card cardAttacker) {
        cardAttacked.setAttackDamage(cardAttacked.getAttackDamage() - 2);
        if (cardAttacked.getAttackDamage() < 0) {
            cardAttacked.setAttackDamage(0);
        }
        cardAttacker.setUsedAbility(true);
    }

    /**
     * Changing the attacker card's health with the one of the attack card
     * @param cardAttacker
     * @param cardAttacked
     */
    public void skyJack(final Card cardAttacker, final Card cardAttacked) {
        Integer aux = cardAttacker.getHealth();
        cardAttacker.setHealth(cardAttacked.getHealth());
        cardAttacked.setHealth(aux);
        cardAttacker.setUsedAbility(true);
    }

    /**
     * Changing the attack damage to the attacked card with its health
     * if it goes below 0, the card is removed from the table
     * @param cardAttacked
     * @param row
     * @param cardAttacker
     */
    public void shapeShift(final Card cardAttacked, final ArrayList<Card> row,
                            final Card cardAttacker) {
        Integer aux = cardAttacked.getHealth();
        cardAttacked.setHealth(cardAttacked.getAttackDamage());
        cardAttacked.setAttackDamage(aux);
        cardAttacker.setUsedAbility(true);
        if (cardAttacked.getHealth() <= 0) {
            row.remove(cardAttacked);
        }
    }

    /**
     * Increasing the health of the attacked card with 2
     * @param cardAttacked
     * @param cardAttacker
     */
    public void godsPlan(final Card cardAttacked, final Card cardAttacker) {
        cardAttacked.setHealth(cardAttacked.getHealth() + 2);
        cardAttacker.setUsedAbility(true);
    }
}
