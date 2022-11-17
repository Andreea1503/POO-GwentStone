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

    public static void placeCard(Card card, ArrayNode output, Player player,
                                boolean errorOccurred, ActionsInput action) {
        try {

            if (card instanceof Environment) {
                ObjectNode placeCardOutput = mapper.createObjectNode();
                placeCardOutput.put("command", action.getCommand());
                placeCardOutput.put("handIdx", action.getHandIdx());
                placeCardOutput.put("error", "Cannot place environment card on table.");
                errorOccurred = true;
                output.add(placeCardOutput);
            } else if (player.getMana() < card.getMana()) {
                ObjectNode placeCardOutput = mapper.createObjectNode();
                placeCardOutput.put("command", action.getCommand());
                placeCardOutput.put("handIdx", action.getHandIdx());
                placeCardOutput.put("error", "Not enough mana to place card on table.");
                errorOccurred = true;
                output.add(placeCardOutput);
            } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj") ||
                        card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                if (player.frontRow.size() >= 5) {
                    ObjectNode placeCardOutput = mapper.createObjectNode();
                    placeCardOutput.put("command", action.getCommand());
                    placeCardOutput.put("handIdx", action.getHandIdx());
                    placeCardOutput.put("error", "Cannot place card on table since row is full.");
                    errorOccurred = true;
                    output.add(placeCardOutput);
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
                }
            }

        } catch (Exception e) {
        }
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
}
