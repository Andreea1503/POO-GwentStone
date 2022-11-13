package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Spells extends Minion {
    boolean usedAbility;
    public static int spellsShift = 2;

    public Spells(CardInput card) {
        super(card);
        this.usedAbility = false;
    }

    public void weakKnees(Minion card) { // The Ripper
        card.health -= spellsShift;
        usedAbility = true;
    }

    public void skyJack(Minion playerOneHealth, Minion playerTwoHealth) {
        Minion aux = playerOneHealth;
        playerOneHealth = playerTwoHealth;
        playerTwoHealth = aux;
        usedAbility = true;
    }

    public void shapeShift (Minion card) {
        int aux = card.health;
        card.health = card.attackDamage;
        card.attackDamage = aux;
        usedAbility = true;
    }

    public void godsPlan(Minion card) {
        card.health += spellsShift;
        usedAbility = true;
    }
}
