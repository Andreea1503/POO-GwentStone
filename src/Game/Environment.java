package Game;

import java.util.ArrayList;

public class Environment extends Card {
    public static int environmentShifts = 1;
    boolean usedAbility;

    public Environment(int mana, String description, ArrayList<String>
                       colors, String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.usedAbility = false;
    }

    public void fireStorm(ArrayList<Card> row) {
        for (Card minion : row) {
            minion.health -= environmentShifts;
        }
        usedAbility = true;
    }

    public void winterFell(ArrayList<Card> row) {
        for (Card minion : row) {
            minion.frozen = true;
        }
        usedAbility = true;
    }

    public void heartHound(ArrayList<Card> row1, ArrayList<Card> row2) {
        int maxHealth = 0;
        int index = 0;
        Minion minion = null;
        for (int i = 0; i < row1.size(); i++) {
            if (row1.get(i).health > maxHealth) {
                maxHealth = row1.get(i).health;
                index = i;
                minion = (Minion) row1.get(i);
            }
        }

        if (row2.size() <= maxIndex) {
            row2.add(maxIndex - index, minion);
            row1.remove(index);
        }

        usedAbility = true;
    }
}