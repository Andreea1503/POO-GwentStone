package Game;

import fileio.CardInput;

import java.util.ArrayList;

public class Spells extends Minion {
    public static int spellsShift = 2;

    public Spells() {

    }
    public Spells(CardInput card) {
        super(card);
        this.usedAbility = false;
    }

    public void weakKnees(Card card) { // The Ripper
        card.attackDamage -= 2;
        if (card.attackDamage < 0) {
            card.attackDamage = 0;
        }
        usedAbility = true;
    }

    public void skyJack(Card playerOne, Card playerTwo) {
        Integer aux = playerOne.health;
        playerOne.health = playerTwo.health;
        playerTwo.health = aux;
        usedAbility = true;
    }

    public void shapeShift (Card card, ArrayList<Card> row) {
        Integer aux = card.health;
        card.health = card.attackDamage;
        card.attackDamage = aux;
        usedAbility = true;
        if (card.health <= 0) {
            row.remove(card);
        }
    }

    public void godsPlan(Card card) {
        card.health += 2;
        usedAbility = true;
    }
}
