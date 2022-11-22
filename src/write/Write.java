package write;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creating the json output
 */
public class Write {
    public Write() {

    }
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Creating an objectNode that contains the data for a card
     * @param card
     * @return
     */
    public ObjectNode writeCard(final Card card) {
        try {
            ObjectNode cardOut = mapper.createObjectNode();
            cardOut.put("mana", card.getMana());
            if (!(card instanceof Environment)) {
                if (!(card instanceof Hero)) {
                    cardOut.put("attackDamage", card.getAttackDamage());
                }
                cardOut.put("health", card.getHealth());
            }
            cardOut.put("description", card.getDescription());

            ArrayNode colors = mapper.createArrayNode();

            for (String color : card.getColors()) {
                colors.add(color);
            }

            cardOut.set("colors", colors);
            cardOut.put("name", card.getName());

            return cardOut;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Writing the player deck
     * @param deck
     * @param action
     * @param output
     */
    public void writeDeck(final ArrayList<Card> deck, final ActionsInput action,
                          final ArrayNode output) {
        try {
            ObjectNode decksOutput = mapper.createObjectNode();

            decksOutput.put("command", action.getCommand());
            decksOutput.put("playerIdx", action.getPlayerIdx());

            ArrayNode deckOut = mapper.createArrayNode();
            outputDeck(deck, deckOut);

            decksOutput.set("output", deckOut);

            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(Arrays.asList(decksOutput));

            output.add(decksOutput);
        } catch (Exception e) {
        }
    }

    /**
     * Writing the card
     * @param card
     * @param action
     * @param output
     */
    public void outputCard(final Card card, final ActionsInput action, final ArrayNode output) {
        try {
            ObjectNode cardOutput = mapper.createObjectNode();

            cardOutput.put("command", action.getCommand());
            cardOutput.put("playerIdx", action.getPlayerIdx());

            cardOutput.set("output", writeCard(card));

            output.add(cardOutput);
        } catch (Exception e) {
        }
    }

    /**
     * Writing the turn of the player
     * @param action
     * @param command
     * @param output
     */
    public void getPlayerTurn(final ActionsInput action, final int command,
                              final ArrayNode output) {
        try {
            ObjectNode turnOutput = mapper.createObjectNode();

            turnOutput.put("command", action.getCommand());
            turnOutput.put("output", command);

            output.add(turnOutput);
        } catch (Exception e) {
        }
    }

    /**
     * Verifying if the environment can't be placed and if it can't be placed,
     * write the error in the output
     * @param card
     * @param output
     * @param player
     * @param action
     * @return
     */
    public boolean placeCard(final Card card, final ArrayNode output, final Player player,
                             final ActionsInput action) {
        try {

            if (card instanceof Environment) {
                ObjectNode placeCardOutput = mapper.createObjectNode();
                placeCardOutput.put("command", action.getCommand());
                placeCardOutput.put("handIdx", action.getHandIdx());
                placeCardOutput.put("error", "Cannot place environment card on table.");
                output.add(placeCardOutput);
                return true;
            } else if (player.getMana() < card.getMana()) {
                ObjectNode placeCardOutput = mapper.createObjectNode();
                placeCardOutput.put("command", action.getCommand());
                placeCardOutput.put("handIdx", action.getHandIdx());
                placeCardOutput.put("error", "Not enough mana to place card on table.");
                output.add(placeCardOutput);
                return true;
            } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                    || card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                if (player.getFrontRow().size() >= 5) {
                    ObjectNode placeCardOutput = mapper.createObjectNode();
                    placeCardOutput.put("command", action.getCommand());
                    placeCardOutput.put("handIdx", action.getHandIdx());
                    placeCardOutput.put("error", "Cannot place card on table since row is full.");
                    output.add(placeCardOutput);
                    return true;
                }
            } else if (card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                    || card.getName().equals("The Cursed One")
                    || card.getName().equals("Disciple")) {
                if (player.getBackRow().size() >= 5) {
                    ObjectNode placeCardOutput = mapper.createObjectNode();
                    placeCardOutput.put("command", action.getCommand());
                    placeCardOutput.put("handIdx", action.getHandIdx());
                    placeCardOutput.put("error", "Cannot place card on table since row is full.");
                    output.add(placeCardOutput);
                    return true;
                }
            }

        } catch (Exception e) {
        }

        return false;
    }

    /**
     * Adding a deck of cards to an arrayNode
     * @param deck
     * @param out
     */
    private void outputDeck(final ArrayList<Card> deck, final ArrayNode out) {
        for (Card card : deck) {
            out.add(writeCard(card));
        }
    }

    /**
     * Writing the cards that are placed on the player's hand
     * @param hand
     * @param action
     * @param output
     */
    public void getCardsFromHand(final ArrayList<Card> hand, final ActionsInput action,
                                 final ArrayNode output) {
        try {
            ObjectNode cardFromHand = mapper.createObjectNode();

            cardFromHand.put("command", action.getCommand());
            cardFromHand.put("playerIdx", action.getPlayerIdx());

            ArrayNode handOut = mapper.createArrayNode();
            outputDeck(hand, handOut);

            cardFromHand.set("output", handOut);

            output.add(cardFromHand);
        } catch (Exception e) {
        }
    }

    /**
     * Writing all the cards that are placed on the table
     * @param player1
     * @param player2
     * @param action
     * @param output
     */
    public void getCardsOnTheTable(final Player player1, final Player player2,
                                   final ActionsInput action, final ArrayNode output) {
        try {
            ObjectNode decksOutput = mapper.createObjectNode();

            decksOutput.put("command", action.getCommand());

            ArrayNode deckOut = mapper.createArrayNode();

            ArrayNode backRowPlayer2 = mapper.createArrayNode();
            outputDeck(player2.getBackRow(), backRowPlayer2);
            deckOut.add(backRowPlayer2);

            ArrayNode frontRowPlayer2 = mapper.createArrayNode();
            outputDeck(player2.getFrontRow(), frontRowPlayer2);
            deckOut.add(frontRowPlayer2);

            ArrayNode frontRowPlayer1 = mapper.createArrayNode();
            outputDeck(player1.getFrontRow(), frontRowPlayer1);
            deckOut.add(frontRowPlayer1);

            ArrayNode backRowPlayer1 = mapper.createArrayNode();
            outputDeck(player1.getBackRow(), backRowPlayer1);
            deckOut.add(backRowPlayer1);

            decksOutput.set("output", deckOut);

            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(Arrays.asList(decksOutput));

            output.add(decksOutput);
        } catch (Exception e) {
        }
    }

    /**
     * Writing the mana that the player has at that moment
     * @param action
     * @param command
     * @param output
     */
    public void getPlayerMana(final ActionsInput action, final int command,
                              final ArrayNode output) {
        try {
            ObjectNode manaOutput = mapper.createObjectNode();

            manaOutput.put("command", action.getCommand());
            manaOutput.put("playerIdx", action.getPlayerIdx());
            manaOutput.put("output", command);

            output.add(manaOutput);
        } catch (Exception e) {
        }
    }

    /**
     * Searching for the card at that position and if it is available,
     * write it, otherwise write an error
     * @param player1
     * @param player2
     * @param action
     * @param output
     */
    public void getThePositionOfTheCard(final Player player1, final Player player2,
                                        final ActionsInput action, final ArrayNode output) {
        ObjectNode positionOfCard = mapper.createObjectNode();

        positionOfCard.put("command", action.getCommand());

        if (action.getX() == 0) {
            if (player2.getBackRow().size() > 0 && action.getY() < player2.getBackRow().size()) {
                positionOfCard.set("output", writeCard(player2.getBackRow().get(action.getY())));
            } else {
                positionOfCard.put("output", "No card available at that position.");
            }
        } else if (action.getX() == 1) {
            if (player2.getFrontRow().size() > 0 && action.getY() < player2.getFrontRow().size()) {
                positionOfCard.set("output", writeCard(player2.getFrontRow().get(action.getY())));
            } else {
                positionOfCard.put("output", "No card available at that position.");
            }
        } else if (action.getX() == 2) {
            if (player1.getFrontRow().size() > 0 && action.getY() < player1.getFrontRow().size()) {
                positionOfCard.set("output", writeCard(player1.getFrontRow().get(action.getY())));
            } else {
                positionOfCard.put("output", "No card available at that position.");
            }
        } else {
            if (player1.getBackRow().size() > 0 && action.getY() < player1.getBackRow().size()) {
                positionOfCard.set("output", writeCard(player1.getBackRow().get(action.getY())));
            } else {
                positionOfCard.put("output", "No card available at that position.");
            }
        }
        positionOfCard.put("x" , action.getX());
        positionOfCard.put("y" , action.getY());

        output.add(positionOfCard);
    }

    /**
     * Writing the environment cards that exist on the player's hand
     * @param player
     * @param action
     * @param output
     */
    public void getEnvironmentCardsInHand(final Player player, final ActionsInput action,
                                          final ArrayNode output) {
        try {
            ObjectNode environmentCardInHand = mapper.createObjectNode();

            environmentCardInHand.put("command", action.getCommand());

            environmentCardInHand.put("playerIdx", action.getPlayerIdx());

            ArrayNode playerHand = mapper.createArrayNode();

            for (Card card : player.getPlayerHand()) {
                if (card instanceof Environment) {
                    playerHand.add(writeCard(card));
                }
            }
            environmentCardInHand.set("output", playerHand);

            output.add(environmentCardInHand);
        } catch (Exception e) {
        }
    }

    /**
     * Writing the error when the environment card can't be used
     * @param action
     * @param output
     * @param error
     */
    public void environmentCardError(final ActionsInput action, final ArrayNode output,
                                     final String error) {
        ObjectNode placeCardOutput = mapper.createObjectNode();
        placeCardOutput.put("command", action.getCommand());
        placeCardOutput.put("handIdx", action.getHandIdx());
        placeCardOutput.put("affectedRow", action.getAffectedRow());
        placeCardOutput.put("error", error);
        output.add(placeCardOutput);
    }

    /**
     * Getting the player's row placed at that position
     * @param player1
     * @param player2
     * @param affectedRow
     * @return
     */
    private ArrayList<Card> getRow(final Player player1, final Player player2,
                                   final Integer affectedRow) {
        if (affectedRow == 0) {
            return player2.getBackRow();
        } else if (affectedRow == 1) {
            return player2.getFrontRow();
        } else if (affectedRow == 2) {
            return player1.getFrontRow();
        } else if (affectedRow == 3) {
            return player1.getBackRow();
        }
        return null;
    }

    /**
     * Getting the player's turn
     * @param player1
     * @param player2
     * @param game
     * @return
     */
    private Player getTurn(final Player player1, final Player player2, final Game game) {
        if (game.getTurn() == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * Use the environment card for an entire row of the table, and if it can't
     * be used, write an error
     * @param hand
     * @param player1
     * @param player2
     * @param action
     * @param output
     * @param game
     */
    public void useEnvironmentCard(final ArrayList<Card> hand, final Player player1,
                                   final Player player2, final ActionsInput action,
                                   final ArrayNode output, final Game game) {
        if (!(hand.get(action.getHandIdx()) instanceof Environment)) {
            environmentCardError(action, output, "Chosen card is not of type environment.");
            return;
        }

        if (game.getTurn() == 1 && player1.getMana() < hand.get(action.getHandIdx()).getMana()) {
            environmentCardError(action, output, "Not enough mana to use environment card.");
            return;
        } else if (game.getTurn() == 2 && player2.getMana()
                    < hand.get(action.getHandIdx()).getMana()) {
            environmentCardError(action, output, "Not enough mana to use environment card.");
            return;
        }

        if ((action.getAffectedRow() == 0 || action.getAffectedRow() == 1)
                && game.getTurn() != 1) {
            environmentCardError(action, output, "Chosen row does not belong to the enemy.");
            return;
        } else if ((action.getAffectedRow() == 2 || action.getAffectedRow() == 3)
                    && game.getTurn() != 2) {
            environmentCardError(action, output, "Chosen row does not belong to the enemy.");
            return;
        }

        if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")
                && action.getAffectedRow() == 0 && player1.getBackRow().size() == 5) {
            environmentCardError(action, output,
                            "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")
                    && action.getAffectedRow() == 1 && player1.getFrontRow().size() == 5) {
            environmentCardError(action, output,
                            "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")
                    && action.getAffectedRow() == 2 && player2.getFrontRow().size() == 5) {
            environmentCardError(action, output,
                            "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")
                    && action.getAffectedRow() == 3 && player2.getBackRow().size() == 5) {
            environmentCardError(action, output,
                            "Cannot steal enemy card since the player's row is full.");
            return;
        }

        Environment environmentCard = new Environment();

        if (hand.get(action.getHandIdx()).getName().equals("Firestorm")) {
            environmentCard.fireStorm(getRow(player1, player2, action.getAffectedRow()));
            Player player = getTurn(player1, player2, game);
            player.setMana(player.getMana() - hand.get(action.getHandIdx()).getMana());
            hand.remove(action.getHandIdx());
        } else if (hand.get(action.getHandIdx()).getName().equals("Winterfell")) {
            environmentCard.winterFell(getRow(player1, player2, action.getAffectedRow()));
            Player player = getTurn(player1, player2, game);
            player.setMana(player.getMana() - hand.get(action.getHandIdx()).getMana());
            hand.remove(action.getHandIdx());
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")) {
            environmentCard.heartHound(getRow(player1, player2, action.getAffectedRow()),
                                        getRow(player1, player2, (3 - action.getAffectedRow())));
            Player player = getTurn(player1, player2, game);
            player.setMana(player.getMana() - hand.get(action.getHandIdx()).getMana());
            hand.remove(action.getHandIdx());
        }
    }

    /**
     * Getting all the cards that have the frozen attribute true from the table
     * @param player1
     * @param player2
     * @param action
     * @param output
     */
    public void getFrozenCardsOnTheTable(final Player player1, final Player player2,
                                         final ActionsInput action, final ArrayNode output) {
        ObjectNode frozenCard = mapper.createObjectNode();

        frozenCard.put("command", action.getCommand());

        ArrayNode frozenCards = mapper.createArrayNode();

        for (Card card : player2.getBackRow()) {
            if (card.isFrozen()) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player2.getFrontRow()) {
            if (card.isFrozen()) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player1.getFrontRow()) {
            if (card.isFrozen()) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player1.getBackRow()) {
            if (card.isFrozen()) {
                frozenCards.add(writeCard(card));
            }
        }

        frozenCard.set("output", frozenCards);

        output.add(frozenCard);
    }

    /**
     * Write the error for when the card can't attack
     * @param action
     * @param output
     * @param error
     */
    public void attackCardError(final ActionsInput action, final ArrayNode output,
                                final String error) {
        ObjectNode attackCardOutput = mapper.createObjectNode();
        attackCardOutput.put("command", action.getCommand());

        ObjectNode attacked = mapper.createObjectNode();
        attacked.put("x", action.getCardAttacked().getX());
        attacked.put("y", action.getCardAttacked().getY());
        attackCardOutput.set("cardAttacked", attacked);

        ObjectNode attacker = mapper.createObjectNode();
        attacker.put("x", action.getCardAttacker().getX());
        attacker.put("y", action.getCardAttacker().getY());
        attackCardOutput.set("cardAttacker", attacker);

        attackCardOutput.put("error", error);
        output.add(attackCardOutput);
    }

    /**
     * Attacking the card at a position, and if it can't, write an error
     * @param player1
     * @param player2
     * @param action
     * @param output
     * @param game
     */
    public void attackCard(final Player player1, final Player player2, final ActionsInput action,
                           final ArrayNode output, final Game game) {
        if ((action.getCardAttacked().getX() == 0 || action.getCardAttacked().getX() == 1)
                && game.getTurn() != 1) {
            attackCardError(action, output, "Attacked card does not belong to the enemy.");
            return;
        } else if ((action.getCardAttacked().getX() == 2 || action.getCardAttacked().getX() == 3)
                    && game.getTurn() != 2) {
            attackCardError(action, output, "Attacked card does not belong to the enemy.");
            return;
        }

        Player player = new Player();
        Card attackerCard = player.getPlayerCard(player1, player2, action.getCardAttacker().getX(),
                                                 action.getCardAttacker().getY());
        if (attackerCard.isAttack() || attackerCard.isUsedAbility()) {
            attackCardError(action, output, "Attacker card has already attacked this turn.");
            return;
        }

        if (attackerCard.isFrozen()) {
            attackCardError(action, output, "Attacker card is frozen.");
            return;
        }

        Card attackedCard = player.getPlayerCard(player1, player2, action.getCardAttacked().getX(),
                                                 action.getCardAttacked().getY());

        ArrayList<Card> row = getRow(player1, player2, action.getCardAttacked().getX());

        ArrayList<Card> searchForTank = new ArrayList<>();
        if (game.getTurn() == 1) {
            searchForTank = getRow(player1, player2, 1);
        } else {
            searchForTank = getRow(player1, player2, 2);
        }
        boolean isTank = false;
        boolean attackedCardIsTank = false;

        for (int i = 0; i < searchForTank.size(); i++) {
            if (searchForTank.get(i).getName().equals("Goliath")
                    || searchForTank.get(i).getName().equals("Warden")) {
                isTank = true;
                if (searchForTank.get(i).equals(attackedCard) && isTank) {
                    attackedCardIsTank = true;
                }
            }
        }
        if (isTank && !attackedCardIsTank) {
            attackCardError(action, output, "Attacked card is not of type 'Tank'.");
            return;
        }

        Card card = new Card();
        card.attackCard(attackedCard, attackerCard, row);

    }

    /**
     * Use the spells cards abilities described in the environment class or
     * write an error
     * @param player1
     * @param player2
     * @param game
     * @param action
     * @param output
     */
    public void useCardAbility(final Player player1, final Player player2, final Game game,
                               final ActionsInput action, final ArrayNode output) {
        Player player = new Player();
        Card attackerCard = player.getPlayerCard(player1, player2, action.getCardAttacker().getX(),
                                                 action.getCardAttacker().getY());
        Card attackedCard = player.getPlayerCard(player1, player2, action.getCardAttacked().getX(),
                                                 action.getCardAttacked().getY());

        if (attackerCard.isFrozen()) {
            attackCardError(action, output, "Attacker card is frozen.");
            return;
        }

        if (attackerCard.isAttack() || attackerCard.isUsedAbility()) {
            attackCardError(action, output, "Attacker card has already attacked this turn.");
            return;
        }

        if (attackerCard.getName().equals("Disciple")) {
            if ((action.getCardAttacked().getX() == 2 || action.getCardAttacked().getX() == 3)
                    && (action.getCardAttacker().getX() == 0
                    || action.getCardAttacker().getX() == 1)) {
                attackCardError(action, output,
                            "Attacked card does not belong to the current player.");
                return;
            } else if ((action.getCardAttacked().getX() == 0
                        || action.getCardAttacked().getX() == 1)
                        && (action.getCardAttacker().getX() == 2
                        || action.getCardAttacker().getX() == 3)) {
                attackCardError(action, output,
                            "Attacked card does not belong to the current player.");
                return;
            }
        }

        if (attackerCard.getName().equals("The Ripper") || attackerCard.getName().equals("Miraj")
                || attackerCard.getName().equals("The Cursed One")) {
            if ((action.getCardAttacked().getX() == 0 || action.getCardAttacked().getX() == 1)
                    && (action.getCardAttacker().getX() == 0
                    || action.getCardAttacker().getX() == 1)) {
                attackCardError(action, output, "Attacked card does not belong to the enemy.");
                return;
            } else if ((action.getCardAttacked().getX() == 2
                    || action.getCardAttacked().getX() == 3)
                    && (action.getCardAttacker().getX() == 2
                    || action.getCardAttacker().getX() == 3)) {
                attackCardError(action, output, "Attacked card does not belong to the enemy.");
                return;
            }
        }

        if (!attackerCard.getName().equals("Disciple")) {
            ArrayList<Card> searchForTank = new ArrayList<>();
            if (game.getTurn() == 1) {
                searchForTank = getRow(player1, player2, 1);
            } else {
                searchForTank = getRow(player1, player2, 2);
            }
            boolean isTank = false;
            boolean attackedCardIsTank = false;

            for (int i = 0; i < searchForTank.size(); i++) {
                if (searchForTank.get(i).getName().equals("Goliath")
                        || searchForTank.get(i).getName().equals("Warden")) {
                    isTank = true;
                    if (searchForTank.get(i).equals(attackedCard) && isTank) {
                        attackedCardIsTank = true;
                    }
                }
            }
            if (isTank && !attackedCardIsTank) {
                attackCardError(action, output, "Attacked card is not of type 'Tank'.");
                return;
            }
        }

        Spells spells = new Spells();
        if (attackerCard.getName().equals("The Ripper")) {
            spells.weakKnees(attackedCard, attackerCard);
        } else if (attackerCard.getName().equals("Miraj")) {
            spells.skyJack(attackerCard, attackedCard);
        } else if (attackerCard.getName().equals("The Cursed One")) {
            spells.shapeShift(attackedCard, getRow(player1, player2,
                                action.getCardAttacked().getX()), attackerCard);
        } else {
            spells.godsPlan(attackedCard, attackerCard);
        }
    }

    /**
     * Write an error for when the player's hero can't attack
     * @param action
     * @param output
     * @param error
     */
    public void attackHeroError(final ActionsInput action, final ArrayNode output,
                                final String error) {
        ObjectNode attackCardOutput = mapper.createObjectNode();
        attackCardOutput.put("command", action.getCommand());

        ObjectNode attacker = mapper.createObjectNode();
        attacker.put("x", action.getCardAttacker().getX());
        attacker.put("y", action.getCardAttacker().getY());
        attackCardOutput.set("cardAttacker", attacker);

        attackCardOutput.put("error", error);
        output.add(attackCardOutput);
    }

    /**
     * Verifing if the player's hero can be attacked by a card, otherwise
     * writing an error
     * @param player1
     * @param player2
     * @param game
     * @param action
     * @param output
     * @return
     */
    public Player useAttackHero(final Player player1, final Player player2, final Game game,
                                final ActionsInput action, final ArrayNode output) {
        Player player = new Player();
        Card attackerCard = player.getPlayerCard(player1, player2, action.getCardAttacker().getX(),
                                                 action.getCardAttacker().getY());
        Card playerHero;

        if (action.getCardAttacker().getX() == 0 || action.getCardAttacker().getX() == 1) {
            playerHero = player1.getPlayerHero();
        } else {
            playerHero = player2.getPlayerHero();
        }

        if (attackerCard.isFrozen()) {
            attackHeroError(action, output, "Attacker card is frozen.");
            return null;
        }

        if (attackerCard.isAttack() || attackerCard.isUsedAbility()) {
            attackHeroError(action, output, "Attacker card has already attacked this turn.");
            return null;
        }

        ArrayList<Card> searchForTank = new ArrayList<>();

        if (game.getTurn() == 1) {
            searchForTank = getRow(player1, player2, 1);
        } else {
            searchForTank = getRow(player1, player2, 2);
        }

        boolean isTank = false;

        for (int i = 0; i < searchForTank.size(); i++) {
            if (searchForTank.get(i).getName().equals("Goliath")
                    || searchForTank.get(i).getName().equals("Warden")) {
                isTank = true;
            }
        }
        if (isTank) {
            attackHeroError(action, output, "Attacked card is not of type 'Tank'.");
            return null;
        }

        Hero hero = new Hero();
        boolean isDead = hero.attackHero(playerHero, attackerCard);
        if (isDead) {
            ObjectNode gameEnded = mapper.createObjectNode();
            if (action.getCardAttacker().getX() == 0 || action.getCardAttacker().getX() == 1) {
                gameEnded.put("gameEnded", "Player two killed the enemy hero.");
                output.add(gameEnded);
                return player2;
            } else {
                gameEnded.put("gameEnded", "Player one killed the enemy hero.");
                output.add(gameEnded);
                return player1;
            }
        }
        return null;
    }

    /**
     * Writing the error for when the hero can't use its ability
     * @param action
     * @param output
     * @param error
     */
    public void heroAbilityError(final ActionsInput action, final ArrayNode output,
                                 final String error) {
        ObjectNode heroOutput = mapper.createObjectNode();
        heroOutput.put("command", action.getCommand());
        heroOutput.put("affectedRow", action.getAffectedRow());
        heroOutput.put("error", error);
        output.add(heroOutput);
    }

    /**
     * Use the player's hero ability, described in the hero class, and it can't
     * write an error
     * If the player's enemy hero dies when the ability is issues on him,
     * an additional output is added, including what player wins
     * @param player1
     * @param player2
     * @param game
     * @param action
     * @param output
     */
    public void useHeroAbility(final Player player1, final Player player2, final Game game,
                               final ActionsInput action, final ArrayNode output) {
        Player player = getTurn(player1, player2, game);
        Hero playerHero = player.getPlayerHero();

        if (player.getMana() < playerHero.getMana()) {
            heroAbilityError(action, output, "Not enough mana to use hero's ability.");
            return;
        }

        if (playerHero.isAttack()) {
            heroAbilityError(action, output, "Hero has already attacked this turn.");
            return;
        }

        if (playerHero.getName().equals("Lord Royce")
                || playerHero.getName().equals("Empress Thorina")) {
            if (game.getTurn() == 1 && (action.getAffectedRow() == 2
                    || action.getAffectedRow() == 3)) {
                heroAbilityError(action, output, "Selected row does not belong to the enemy.");
                return;
            } else if (game.getTurn() == 2 && (action.getAffectedRow() == 0
                    || action.getAffectedRow() == 1)) {
                heroAbilityError(action, output, "Selected row does not belong to the enemy.");
                return;
            }
        } else if (playerHero.getName().equals("General Kocioraw")
                || playerHero.getName().equals("King Mudface")) {
            if (game.getTurn() == 1 && (action.getAffectedRow() == 0
                    || action.getAffectedRow() == 1)) {
                heroAbilityError(action, output,
                        "Selected row does not belong to the current player.");
                return;
            } else if (game.getTurn() == 2 && (action.getAffectedRow() == 2
                    || action.getAffectedRow() == 3)) {
                heroAbilityError(action, output,
                        "Selected row does not belong to the current player.");
                return;
            }
        }

        Hero hero = new Hero();
        if (playerHero.getName().equals("Lord Royce")) {
            hero.subZero(getRow(player1, player2, action.getAffectedRow()), playerHero);
            player.setMana(player.getMana() - playerHero.getMana());
        } else if (playerHero.getName().equals("Empress Thorina")) {
            hero.lowBlow(getRow(player1, player2, action.getAffectedRow()), playerHero);
            player.setMana(player.getMana() - playerHero.getMana());
        } else if (playerHero.getName().equals("King Mudface")) {
            hero.earthBorn(getRow(player1, player2, action.getAffectedRow()), playerHero);
            player.setMana(player.getMana() - playerHero.getMana());
        } else if (playerHero.getName().equals("General Kocioraw")) {
            hero.bloodThirst(getRow(player1, player2, action.getAffectedRow()), playerHero);
            player.setMana(player.getMana() - playerHero.getMana());
        }

    }

    /**
     * Write the output for the winning player
     * @param playerWins
     * @param action
     * @param output
     */
    public void playerWins(final Integer playerWins, final ActionsInput action,
                           final ArrayNode output) {
        ObjectNode heroOutput = mapper.createObjectNode();
        heroOutput.put("command", action.getCommand());
        heroOutput.put("output", playerWins);
        output.add(heroOutput);
    }

    /**
     * Getter for the object mapper
     * @return mapper
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Setter for the object mapper
     * @param mapper
     */
    public static void setMapper(final ObjectMapper mapper) {
        Write.mapper = mapper;
    }
}
