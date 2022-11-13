package Game;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Game {
    int playerOneDeckIdx;
    int playerTwoDeckIdx;
    int shuffleSeed;
    Hero playerOneHero;
    Hero playerTwoHero;
    int startingPlayer;

    public Game(int playerOneDeckIdx, int playerTwoDeckIdx, int shuffleSeed,
                Hero playerOneHero, Hero playerTwoHero, int startingPlayer) {
        this.playerOneDeckIdx = playerOneDeckIdx;
        this.playerTwoDeckIdx = playerTwoDeckIdx;
        this.shuffleSeed = shuffleSeed;
        this.playerOneHero = playerOneHero;
        this.playerTwoHero = playerTwoHero;
        this.startingPlayer = startingPlayer;
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
                } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                        || card.getName().equals("The Cursed One") || card.getName().equals("Disciple")) {
                    Spells spell = new Spells(card);
                    deck.add(spell);
                } else if (card.getName().equals("Firestorm") || card.getName().equals("Winterfell")
                        || card.getName().equals("Heart Hound")) {
                    Environment environmentCard = new Environment(card);
                    deck.add(environmentCard);
                }
            }
            listOfDecks.add(deck);
            listofDeck = new Decks(decksInput.getNrCardsInDeck(), decksInput.getNrDecks(), listOfDecks);
        }
        System.out.println(listofDeck);
        return listofDeck;
    }

    @Override
    public String toString() {
        return "StartGameInput{"
                + "playerOneDeckIdx="
                + playerOneDeckIdx
                + ", playerTwoDeckIdx="
                + playerTwoDeckIdx
                + ", shuffleSeed="
                + shuffleSeed
                +  ", playerOneHero="
                + playerOneHero
                + ", playerTwoHero="
                + playerTwoHero
                + ", startingPlayer="
                + startingPlayer
                + '}';
    }

}
