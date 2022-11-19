package Game;

import fileio.ActionsInput;
import fileio.CardInput;

import java.util.ArrayList;

public class Environment extends Card {
    public static int environmentShifts = 1;
    boolean usedAbility;

    public Environment() {

    }
    public Environment(CardInput card) {
        this.mana = card.getMana();
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.usedAbility = false;
    }

    public void fireStorm(ArrayList<Card> row) {
        for (int i = 0; i < row.size(); i++) {
            Card minion = row.get(i);
            minion.health -= 1;
            if (minion.health <= 0) {
                row.remove(i);
                i--;
            }
        }
        usedAbility = true;
    }

    public void winterFell(ArrayList<Card> row) {
        for (Card minion : row) {
            minion.setFrozen(true);
        }
        usedAbility = true;
    }

    public void heartHound(ArrayList<Card> row1, ArrayList<Card> row2) {
        int maxHealth = 0;
        Minion minion = null;
        for (int i = 0; i < row1.size(); i++) {
            if (row1.get(i).getHealth() > maxHealth) {
                maxHealth = row1.get(i).getHealth();
                minion = (Minion)row1.get(i);
            }
        }
        row2.add(minion);
        row1.remove(minion);

        usedAbility = true;
    }

    @Override
    public String toString() {
        return "CardInput{"
                +  "mana="
                + mana
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