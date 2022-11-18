package Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

import java.util.ArrayList;
import java.util.Arrays;


public class Write {
    // create the json file for the output
    // create ObjectMapper instance
    public Write() {

    }
    static ObjectMapper mapper = new ObjectMapper();
    public static ObjectNode writeCard(Card card) {
        // create json objects
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

    public static void writeDeck(ArrayList<Card> deck, ActionsInput action, ArrayNode output) {
        // create json objects
        try {
            ObjectNode decksOutput = mapper.createObjectNode();

            decksOutput.put("command", action.getCommand());
            decksOutput.put("playerIdx", action.getPlayerIdx());

            ArrayNode deckOut = mapper.createArrayNode();
            for (Card card : deck) {
                deckOut.add(writeCard(card));
            }

            decksOutput.set("output", deckOut);

            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(Arrays.asList(decksOutput));
//
//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
//            System.out.println(decksOutput);

//            return decksOutput;
            output.add(decksOutput);
        } catch (Exception e) {
        }
    }

    public static void outputCard(Card card, ActionsInput action, ArrayNode output) {
        // create json objects
        try {
            ObjectNode cardOutput = mapper.createObjectNode();

            cardOutput.put("command", action.getCommand());
            cardOutput.put("playerIdx", action.getPlayerIdx());

            cardOutput.set("output", writeCard(card));

//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cardOutput);
//            System.out.println(json);

            output.add(cardOutput);
        } catch (Exception e) {
        }
    }

    public static void getPlayerTurn(ActionsInput action, int command, ArrayNode output) {
        // create json objects
        try {
            ObjectNode turnOutput = mapper.createObjectNode();

            turnOutput.put("command", action.getCommand());

            turnOutput.put("output", command);

//            String json = mapper.writeWithDefaultPrettyPrinter().writeValueAsString(turnOutput)
            output.add(turnOutput);
        } catch (Exception e) {
        }
    }

    public static boolean placeCard(Card card, ArrayNode output, Player player,
                                boolean errorOccurred, ActionsInput action) {
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
            } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj") ||
                        card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                if (player.frontRow.size() >= 5) {
                    ObjectNode placeCardOutput = mapper.createObjectNode();
                    placeCardOutput.put("command", action.getCommand());
                    placeCardOutput.put("handIdx", action.getHandIdx());
                    placeCardOutput.put("error", "Cannot place card on table since row is full.");
                    errorOccurred = true;
                    output.add(placeCardOutput);
                    return true;
                }
            } else if (card.getName().equals("Sentinel") || card.getName().equals("Berserker") ||
                        card.getName().equals("The Cursed One") || card.getName().equals("Disciple")) {
                if (player.backRow.size() >= 5) {
                    ObjectNode placeCardOutput = mapper.createObjectNode();
                    placeCardOutput.put("command", action.getCommand());
                    placeCardOutput.put("handIdx", action.getHandIdx());
                    placeCardOutput.put("error", "Cannot place card on table since row is full.");
                    errorOccurred = true;
                    output.add(placeCardOutput);
                    return true;
                }
            }

        } catch (Exception e) {
        }

        return false;
    }

    public static void getCardsFromHand(ArrayList<Card> hand, ActionsInput action, ArrayNode output) {
        try {
            ObjectNode cardFromHand = mapper.createObjectNode();

            cardFromHand.put("command", action.getCommand());
            cardFromHand.put("playerIdx", action.getPlayerIdx());

            ArrayNode handOut = mapper.createArrayNode();

            for (Card card : hand) {
                handOut.add(writeCard(card));
            }
            cardFromHand.set("output", handOut);

//            ArrayNode arrayNode = mapper.createArrayNode();
//            arrayNode.addAll(Arrays.asList(cardFromHand));

            output.add(cardFromHand);
        } catch (Exception e) {
        }
    }

    public static void getCardsOnTheTable(Player player1, Player player2, ActionsInput action, ArrayNode output) {
        try {
            ObjectNode decksOutput = mapper.createObjectNode();

            decksOutput.put("command", action.getCommand());

            ArrayNode deckOut = mapper.createArrayNode();

            ArrayNode backRowPlayer2 = mapper.createArrayNode();
            for (Card card : player2.backRow) {
                backRowPlayer2.add(writeCard(card));
            }
            deckOut.add(backRowPlayer2);

            ArrayNode frontRowPlayer2 = mapper.createArrayNode();
            for (Card card : player2.frontRow) {
                frontRowPlayer2.add(writeCard(card));
            }
            deckOut.add(frontRowPlayer2);

            ArrayNode frontRowPlayer1 = mapper.createArrayNode();
            for (Card card : player1.frontRow) {
                frontRowPlayer1.add(writeCard(card));
            }
            deckOut.add(frontRowPlayer1);

            ArrayNode backRowPlayer1 = mapper.createArrayNode();
            for (Card card : player1.backRow) {
                backRowPlayer1.add(writeCard(card));
            }
            deckOut.add(backRowPlayer1);

            decksOutput.set("output", deckOut);

            ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.addAll(Arrays.asList(decksOutput));
//
//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
//            System.out.println(decksOutput);

//            return decksOutput;
            output.add(decksOutput);
        } catch (Exception e) {
        }
    }

//    static void printCards(ArrayList<Card> row) {
////        for (Card card : row) {
////
////        }
////    }

    public static void getPlayerMana(ActionsInput action, int command, ArrayNode output) {
        // create json objects
        try {
            ObjectNode manaOutput = mapper.createObjectNode();

            manaOutput.put("command", action.getCommand());

            manaOutput.put("playerIdx", action.getPlayerIdx());

            manaOutput.put("output", command);

//            String json = mapper.writeWithDefaultPrettyPrinter().writeValueAsString(turnOutput)
            output.add(manaOutput);
        } catch (Exception e) {
        }
    }

    public static void getThePositionOfTheCard(Player player1, Player player2, ActionsInput action, ArrayNode output) {
            ObjectNode positionOfCard = mapper.createObjectNode();

            positionOfCard.put("command", action.getCommand());

            if (action.getX() == 0) {
                if (player2.getBackRow().size() > 0 && action.getY() < player2.getBackRow().size()) {
                    positionOfCard.set("output", writeCard(player2.getBackRow().get(action.getY())));
                } else {
                    positionOfCard.put("output","No card available at that position." );
                }
            } else if (action.getX() == 1) {
                if (player2.getFrontRow().size() > 0 && action.getY() < player2.getFrontRow().size()) {
                    positionOfCard.set("output", writeCard(player2.getFrontRow().get(action.getY())));
                } else {
                    positionOfCard.put("output","No card available at that position." );
                }
            } else if (action.getX() == 2) {
                if (player1.getFrontRow().size() > 0 && action.getY() < player1.getFrontRow().size()) {
                    positionOfCard.set("output", writeCard(player1.getFrontRow().get(action.getY())));
                } else {
                    positionOfCard.put("output","No card available at that position." );
                }
            } else {
                if (player1.getBackRow().size() > 0 && action.getY() < player1.getBackRow().size()) {
                    positionOfCard.set("output", writeCard(player1.getBackRow().get(action.getY())));
                } else {
                    positionOfCard.put("output","No card available at that position." );
                }
            }

//            String json = mapper.writeWithDefaultPrettyPrinter().writeValueAsString(turnOutput)
            output.add(positionOfCard);
    }

    public static void getEnvironmentCardsInHand(Player player, ActionsInput action, ArrayNode output) {
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

//            manaOutput.put("output", command);

//            String json = mapper.writeWithDefaultPrettyPrinter().writeValueAsString(turnOutput)
            output.add(environmentCardInHand);
        } catch (Exception e) {
        }
    }

    public static void environmentCardError(ActionsInput action, ArrayNode output, String error) {
        ObjectNode placeCardOutput = mapper.createObjectNode();
        placeCardOutput.put("command", action.getCommand());
        placeCardOutput.put("handIdx", action.getHandIdx());
        placeCardOutput.put("affectedRow", action.getAffectedRow());
        placeCardOutput.put("error", error);
        output.add(placeCardOutput);
    }

    public static ArrayList<Card> getRow(Player player1, Player player2, Integer affectedRow) {
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

    public static Player getTurn(Player player1, Player player2, Game game) {
        if (game.turn == 1) {
            return player1;
        } else {
            return player2;
        }
    }
    public static void useEnvironmentCard(ArrayList<Card> hand, Player player1, Player player2, ActionsInput action, ArrayNode output, Game game) {
        if (!(hand.get(action.getHandIdx()) instanceof Environment)) {
            environmentCardError(action, output, "Chosen card is not of type environment.");
            return;
        }

        if (game.turn == 1 && player1.getMana() < hand.get(action.getHandIdx()).getMana()) {
            environmentCardError(action, output, "Not enough mana to use environment card.");
            return;
        } else if (game.turn == 2 && player2.getMana() < hand.get(action.getHandIdx()).getMana()) {
            environmentCardError(action, output, "Not enough mana to use environment card.");
            return;
        }

        if ((action.getAffectedRow() == 0 || action.getAffectedRow() == 1) && game.turn != 1) {
            environmentCardError(action, output, "Chosen row does not belong to the enemy.");
            return;
        } else if ((action.getAffectedRow() == 2 || action.getAffectedRow() == 3) && game.turn != 2) {
            environmentCardError(action, output, "Chosen row does not belong to the enemy.");
            return;
        }

        if (hand.get(action.getHandIdx()).getName().equals("Heart Hound") && action.getAffectedRow() == 0 && player1.getBackRow().size() == 5) {
            environmentCardError(action, output, "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound") && action.getAffectedRow() == 1 && player1.getFrontRow().size() == 5) {
            environmentCardError(action, output, "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound") && action.getAffectedRow() == 2 && player2.getFrontRow().size() == 5) {
            environmentCardError(action, output, "Cannot steal enemy card since the player's row is full.");
            return;
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound") && action.getAffectedRow() == 3 && player2.getBackRow().size() == 5) {
            environmentCardError(action, output, "Cannot steal enemy card since the player's row is full.");
            return;
        }

        Environment environmentCard = new Environment();

        if (hand.get(action.getHandIdx()).getName().equals("Firestorm")) {
            environmentCard.fireStorm(getRow(player1, player2, action.getAffectedRow()));
            Player player = getTurn(player1, player2, game);
            player.mana -= hand.get(action.getHandIdx()).mana;
            hand.remove(action.getHandIdx());
        } else if (hand.get(action.getHandIdx()).getName().equals("Winterfell")) {
            environmentCard.winterFell(getRow(player1, player2, action.getAffectedRow()));
            Player player = getTurn(player1, player2, game);
            player.mana -= hand.get(action.getHandIdx()).mana;
            hand.remove(action.getHandIdx());
        } else if (hand.get(action.getHandIdx()).getName().equals("Heart Hound")) {
            environmentCard.heartHound(getRow(player1, player2, action.getAffectedRow()), getRow(player1, player2, (3 - action.getAffectedRow())));
            Player player = getTurn(player1, player2, game);
            player.mana -= hand.get(action.getHandIdx()).mana;
            hand.remove(action.getHandIdx());
        }
    }

    public static void getFrozenCardsOnTheTable(Player player1, Player player2, ActionsInput action, ArrayNode output) {
        ObjectNode frozenCard = mapper.createObjectNode();

        frozenCard.put("command", action.getCommand());

        ArrayNode frozenCards = mapper.createArrayNode();

        for (Card card : player2.backRow) {
            if (card.frozen == true) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player2.frontRow) {
            if (card.frozen == true) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player1.frontRow) {
            if (card.frozen == true) {
                frozenCards.add(writeCard(card));
            }
        }

        for (Card card : player1.backRow) {
            if (card.frozen == true) {
                frozenCards.add(writeCard(card));
            }
        }

        frozenCard.set("output", frozenCards);

        output.add(frozenCard);
    }
}
