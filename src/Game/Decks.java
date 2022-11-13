package Game;

import java.util.ArrayList;

public class Decks {
    int nrCardsInDeck;
    int nrDecks;
    ArrayList<ArrayList<Card>> decks;

    public Decks(int nrCardsInDeck, int nrDecks, ArrayList<ArrayList<Card>>
                 decks) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
        this.decks = new ArrayList<>(nrDecks);
    }

}
