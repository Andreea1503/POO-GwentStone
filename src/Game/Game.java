package Game;
import java.util.Random;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.DecksInput;
import java.util.*;

import java.util.ArrayList;

public class Game {
//    public static int firstPlayer = 1;
//    public static int secondPlayer = 2;
    Integer startingPlayer;
    Integer shuffleSeed;
    Integer round;
    Integer turn;

    public Game(int startingPlayer, int shuffleSeed) {
        this.startingPlayer = startingPlayer;
        this.shuffleSeed = shuffleSeed;
        this.round = 1;
        this.turn = 0;
    }

    public static Decks createDecks(DecksInput decksInput) {
        ArrayList<ArrayList<CardInput>> decks = decksInput.getDecks();
        ArrayList<CardInput> deckInput;
        ArrayList<Card> deck;
        ArrayList<ArrayList<Card>> listOfDecks = new ArrayList<>();
        Decks listofDeck = null;

        for (int i = 0; i < decksInput.getNrDecks(); i++) {
            deckInput = decks.get(i);
            deck = new ArrayList<>();
            for (int j = 0; j < decksInput.getNrCardsInDeck(); j++) {
                CardInput card = deckInput.get(j);
                if (card.getName().equals("Sentinel") || card.getName().equals("Berserker") ||
                        card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                    Minion minion = new Minion(card);
                    deck.add(minion);
//                    Write.writeCard(minion);
                } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                        || card.getName().equals("The Cursed One") || card.getName().equals("Disciple")) {
                    Spells spell = new Spells(card);
                    deck.add(spell);
//                    Write.writeCard(spell);
                } else if (card.getName().equals("Firestorm") || card.getName().equals("Winterfell")
                        || card.getName().equals("Heart Hound")) {
                    Environment environmentCard = new Environment(card);
                    deck.add(environmentCard);
//                    Write.writeCard(environmentCard);
                }
            }
            listOfDecks.add(deck);
            listofDeck = new Decks(decksInput.getNrCardsInDeck(), decksInput.getNrDecks(), listOfDecks);
        }
//        System.out.println(listofDeck);
        return listofDeck;
    }

    public static void endTurn(ArrayList<Card> playerOneDeck, ArrayList<Card> playerTwoDeck, Player player1, Player player2, Game game) {
        if (game.turn == 1) {
            game.turn = 2;
            player1.endTurn = true;
            unfreezeCards(playerOneDeck);
        } else {
            game.turn = 1;
            player2.endTurn = true;
            unfreezeCards(playerTwoDeck);
        }

        if (player1.endTurn && player2.endTurn) {
            Player.drawCard(playerOneDeck, player1, game);
            Player.drawCard(playerTwoDeck, player2, game);
            Player.nextRound(player1, player2, game);
            player1.endTurn = false;
            player2.endTurn = false;
        }
    }

    public static void unfreezeCards (ArrayList<Card> deck) {
        for (Card card : deck) {
            if (card.frozen == true) {
                card.frozen = false;
            }
        }
    }

    // mai bine inca o clasa currentPlayer
//    static ObjectMapper mapper = new ObjectMapper();
    public static void parseActions(ArrayList<ActionsInput> actionsInputs, Game game, Player player1, Player player2, ArrayNode output) {
        ArrayList<Card> playerOneDeck = Player.getPlayerDeck(player1, player1.getPlayerDeckIdx(), game);
        ArrayList<Card> playerTwoDeck = Player.getPlayerDeck(player2, player2.getPlayerDeckIdx(), game);
        game.turn = game.getStartingPlayer();

        player1.playerHand = Player.drawCard(playerOneDeck, player1, game);
        player2.playerHand = Player.drawCard(playerTwoDeck, player2, game);
        Player.nextRound(player1, player2, game);
//        System.out.println(playerOneDeck);
//        System.out.println(playerTwoDeck);

        for (ActionsInput action : actionsInputs) {
            if (action.getCommand().equals("getPlayerDeck")) {
                if (action.getPlayerIdx() == 1) {
                    Write.writeDeck(playerOneDeck, action, output);
                } else {
                    Write.writeDeck(playerTwoDeck, action, output);
                }
            } else if (action.getCommand().equals("getPlayerHero")) {
                if (action.getPlayerIdx() == 1) {
                    Hero hero = player1.getPlayerHero();
                    Write.outputCard(hero, action, output);
                } else {
                    Hero hero = player2.getPlayerHero();
                    Write.outputCard(hero, action, output);
                }
            } else if (action.getCommand().equals("getPlayerTurn")) {
                Write.getPlayerTurn(action, game.turn, output);
            } else if (action.getCommand().equals("endPlayerTurn")) {
                endTurn(playerOneDeck, playerTwoDeck, player1, player2, game);
            } else if (action.getCommand().equals("placeCard")) {
                if (game.turn == 1 && action.getHandIdx() < player1.playerHand.size() && player1.playerHand.size() > 0) {
                    placeCardOnTheTable(action, output, player1);
//                    System.out.println(player1.getPlayerHand());
                } else if (game.turn == 2 && action.getHandIdx() < player2.playerHand.size() && player2.playerHand.size() > 0){
                    placeCardOnTheTable(action, output, player2);
//                    System.out.println(player2.getPlayerHand());
                }
            } else if (action.getCommand().equals("getCardsInHand")) {
                if (action.getPlayerIdx() == 1) {
                    Write.getCardsFromHand(player1.playerHand, action, output);
                } else {
                    Write.getCardsFromHand(player2.playerHand, action, output);
                }
            } else if (action.getCommand().equals("getPlayerMana")) {
                if (action.getPlayerIdx() == 1) {
                    Write.getPlayerMana(action, player1.getMana(), output);
                } else {
                    Write.getPlayerMana(action, player2.getMana(), output);
                }
            } else if (action.getCommand().equals("getCardsOnTable")) {
                Write.getCardsOnTheTable(player1, player2, action, output);
            }
        }
    }


    public static void placeCardOnTheTable(ActionsInput action, ArrayNode output, Player player) {
        boolean errorOccurred = false;

        for (int i = 0; i < player.playerHand.size(); i++) {
            if (action.getHandIdx() == i) {
                Write.placeCard(player.playerHand.get(i), output, player, errorOccurred, action);
                if (!errorOccurred) {
                    placeCardOnTable(player, player.playerHand.get(i));
                }
            }
        }
     }

     public static void placeCardOnTable(Player player, Card card) {
         if (card.getName().equals("The Ripper") || card.getName().equals("Miraj") ||
                 card.getName().equals("Goliath") || card.getName().equals("Warden")) {
             player.mana -= card.mana;
             player.frontRow.add(card);
             player.playerHand.remove(card);
         } else if (card.getName().equals("Sentinel") || card.getName().equals("Berserker") ||
                 card.getName().equals("The Cursed One") || card.getName().equals("Disciple")) {
             player.mana -= card.mana;
             player.backRow.add(card);
             player.playerHand.remove(card);
         }
     }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    @Override
    public String toString() {
        return "StartGameInput{"
                + ", startingPlayer="
                + startingPlayer
                + '}';
    }

}
