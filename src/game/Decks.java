package game;
import write.*;
import java.util.ArrayList;

/**
 * Information about the number of decks and the number of cards per deck
 */
public class Decks {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;
    public Decks(final int nrCardsInDeck, final int nrDecks,
                 final ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
    }

    /**
     * Getter for the number of cards in a deck
     * @return number of cards in a deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     * Setter for the number od cards in a deck
     * @param nrCardsInDeck
     */
    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    /**
     * Getter for the number of decks
     * @return number of decks
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     * Setter for the number of decks
     * @param nrDecks
     */
    public void setNrDecks(final int nrDecks) {
        this.nrDecks = nrDecks;
    }

    /**
     * Getter for the player decks
     * @return player decks
     */
    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    /**
     * Setter for the player decks
     * @param decks
     */
    public void setDecks(final ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }


}
