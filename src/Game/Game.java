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
    int startingPlayer;
    int shuffleSeed;
    int round;

    public Game(int startingPlayer, int shuffleSeed) {
        this.startingPlayer = startingPlayer;
        this.shuffleSeed = shuffleSeed;
        this.round = 1;
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

    public static void endTurn(int turn) {
        if (turn == 1) {
            turn = 2;
        } else {
            turn = 1;
        }
    }
    // mai bine inca o clasa currentPlayer
//    static ObjectMapper mapper = new ObjectMapper();
    public static void parseActions(ArrayList<ActionsInput> actionsInputs, Game game, Player player1, Player player2, ArrayNode output) {
        ArrayList<Card> playerOneDeck = Player.getPlayerDeck(player1, player1.getPlayerDeckIdx(), game);
        ArrayList<Card> playerTwoDeck = Player.getPlayerDeck(player2, player2.getPlayerDeckIdx(), game);
        int turn = game.getStartingPlayer();

        ArrayList<Card> playerOneHand = Player.Round(playerOneDeck, player1, game);
        ArrayList<Card> playerTwoHand = Player.Round(playerTwoDeck, player2, game);

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
                int currentPlayer = turn;
                Write.writeTurn(action, turn, output);
            } else if (action.getCommand().equals("endPlayerTurn")) {
                endTurn(turn);
                game.round++;
            }
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
