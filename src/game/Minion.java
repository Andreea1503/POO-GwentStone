package game;
import write.*;
import fileio.CardInput;

/**
 * Information about the minion cards
 */
public class Minion extends Card {

    public Minion() {

    }
    public Minion(final CardInput card) {
        this.setMana(card.getMana());
        this.setAttackDamage(card.getAttackDamage());
        this.setHealth(card.getHealth());
        this.setDescription(card.getDescription());
        this.setColors(card.getColors());
        this.setName(card.getName());
        this.setFrozen(false);
        this.setAttack(false);
    }
}
