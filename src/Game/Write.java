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

    public static void writeTurn(ActionsInput action, int turn, ArrayNode output) {
        // create json objects
        try {
            ObjectNode turnOutput = mapper.createObjectNode();

            turnOutput.put("command", action.getCommand());

            turnOutput.put("output", turn);

//            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(turnOutput)
            output.add(turnOutput);
        } catch (Exception e) {
        }
    }
}
