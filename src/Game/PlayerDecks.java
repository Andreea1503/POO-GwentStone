package Game;

import java.util.ArrayList;

public class PlayerDecks {
    Decks playerOneDecks;
    Decks playerTwoDecks;
    ArrayList<Game> games;

    public PlayerDecks(Decks playerOneDecks, Decks playerTwoDecks,
                       ArrayList<Game> games) {
        this.playerOneDecks = playerOneDecks;
        this.playerTwoDecks = playerTwoDecks;
        this.games = new ArrayList<>();
        this.games = games;
    }
}
