package game;
import write.*;
import fileio.CardInput;

import java.util.ArrayList;

/**
 * Information about the environment cards and every spell they can cast
 */
public class Environment extends Card {
    /**
     * Default constructor used to cast the spells the Write class
     */
    public Environment() {

    }
    public Environment(final CardInput card) {
        this.setMana(card.getMana());
        this.setDescription(card.getDescription());
        this.setColors(card.getColors());
        this.setName(card.getName());
        this.setUsedAbility(false);
    }

    /**
     * Going through every card of a specific table row and decreasing its health
     */
    public void fireStorm(final ArrayList<Card> row) {
        for (int i = 0; i < row.size(); i++) {
            Card minion = row.get(i);
            minion.setHealth(minion.getHealth() - 1);
            if (minion.getHealth() <= 0) {
                row.remove(i);
                i--;
            }
        }
        setUsedAbility(true);
    }

    /**
     * Going through every card of the table row and setting its flag to frozen
     */
    public void winterFell(final ArrayList<Card> row) {
        for (Card minion : row) {
            minion.setFrozen(true);
        }
        setUsedAbility(true);
    }

    /**
     * Going through the enemy's table row and choosing the card with the max health
     * The card with the max health gets moved to the attacker row, and it is removed
     * from the enemy row
     */
    public void heartHound(final ArrayList<Card> row1, final ArrayList<Card> row2) {
        int maxHealth = 0;
        Minion minion = null;
        for (int i = 0; i < row1.size(); i++) {
            if (row1.get(i).getHealth() > maxHealth) {
                maxHealth = row1.get(i).getHealth();
                // storing the card with the max health
                minion = (Minion) row1.get(i);
            }
        }
        row2.add(minion);
        row1.remove(minion);

        setUsedAbility(true);
    }
}
