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

    public void weakKnees(Card cardAttacked, Card cardAttacker) { // The Ripper
        cardAttacked.attackDamage -= 2;
        if (cardAttacked.attackDamage < 0) {
            cardAttacked.attackDamage = 0;
        }
        cardAttacker.usedAbility = true;
    }

    public void skyJack(Card cardAttacker, Card cardAttacked) {
        Integer aux = cardAttacker.health;
        cardAttacker.health = cardAttacked.health;
        cardAttacked.health = aux;
        cardAttacker.usedAbility = true;
    }

    public void shapeShift (Card cardAttacked, ArrayList<Card> row, Card cardAttacker) {
        Integer aux = cardAttacked.health;
        cardAttacked.health = cardAttacked.attackDamage;
        cardAttacked.attackDamage = aux;
        cardAttacker.usedAbility = true;
        if (cardAttacked.health <= 0) {
            row.remove(cardAttacked);
        }
    }

    public void godsPlan(Card cardAttacked, Card cardAttacker) {
        cardAttacked.health += 2;
        cardAttacker.usedAbility = true;
    }
}
