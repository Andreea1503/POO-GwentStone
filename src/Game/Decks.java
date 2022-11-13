package Game;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Decks {
    int nrCardsInDeck;
    int nrDecks;
    ArrayList<ArrayList<Card>> decks;
    public Decks(int nrCardsInDeck, int nrDecks, ArrayList<ArrayList<Card>> decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = decks;
    }
    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                +  ", nr_decks="
                + nrDecks
                + ", decks="
                + decks
                + '}';
    }

}
