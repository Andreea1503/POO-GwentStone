package Game;

import java.util.ArrayList;

public class Card {
    public static int maxIndex = 5;
    Integer mana;
    Integer attackDamage;
    Integer health;
    String description;
    ArrayList<String> colors = new ArrayList<>();
    String name;

    boolean frozen;
    boolean usedAbility;
    boolean attack;

    public Card() {

    }

    public void attackCard(Card attackedCard, Card attackerCard, ArrayList<Card> tableRow) {
        attackedCard.health -= attackerCard.attackDamage;

        if (attackedCard.health <= 0) {
            tableRow.remove(attackedCard);
        }

        attackerCard.attack = true;
    }

    public boolean attackHero(Card playerHero, Card cardAttacker) {
        playerHero.health -= cardAttacker.attackDamage;

        if (playerHero.health <= 0) {
            return true;
        }

        cardAttacker.attack = true;
        return false;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isUsedAbility() {
        return usedAbility;
    }

    public void setUsedAbility(boolean usedAbility) {
        this.usedAbility = usedAbility;
    }

    public boolean isAttack() {
        return attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }
}
