package game;
import write.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Information about the players
 */
public class Player {
    public static final int PLAYER_MANA = 10;
    private Decks playerDecks;
    private int playerDeckIdx;
    private Hero playerHero;
    private Integer mana;
    private boolean endTurn;
    private ArrayList<Card> frontRow;
    private ArrayList<Card> backRow;
    private ArrayList<Card> playerHand;

    public Player() {

    }

    public Player(final Decks playerDecks, final int playerDeckIdx,
                  final Hero playerHero) {
        this.playerDecks = playerDecks;
        this.playerDeckIdx = playerDeckIdx;
        this.playerHero = playerHero;
        this.mana = 0;
        this.endTurn = false;
        this.frontRow = new ArrayList<>();
        this.backRow = new ArrayList<>();
        this.playerHand = new ArrayList<>();
    }

    /**
     * Getting the chosen deck from the player decks and shuffling it
     * @param player
     * @param index
     * @param game
     * @return
     */
    public static ArrayList<Card> getPlayerDeck(final Player player, final int index,
                                                final Game game) {
        ArrayList<Card> deck = null;
        for (int i = 0; i < player.getPlayerDecks().getNrDecks(); i++) {
            if (i == index) {
                deck = player.getPlayerDecks().getDecks().get(i);
            }
        }
        Collections.shuffle(deck, new Random(game.getShuffleSeed()));
        return deck;
    }

    /**
     * Getting the card from a given position
     * @param player1
     * @param player2
     * @param x
     * @param y
     * @return playerCard
     */
    public Card getPlayerCard(final Player player1, final Player player2, final Integer x,
                              final Integer y) {
        Card playerCard = null;
        if (x == 0) {
            if (player2.getBackRow().size() > y) {
                playerCard = player2.getBackRow().get(y);
            }
        } else if (x == 1) {
            if (player2.getFrontRow().size() > y) {
                playerCard = player2.getFrontRow().get(y);
            }
        } else if (x == 2) {
            if (player1.getFrontRow().size() > y) {
                playerCard = player1.getFrontRow().get(y);
            }
        } else {
            if (player1.getBackRow().size() > y) {
                playerCard = player1.getBackRow().get(y);
            }
        }
        return playerCard;
    }

    /**
     * Getting a card from the player deck, and putting it in the player hand,
     * then removing it from the deck if there is any card available in the
     * deck
     * @param deck
     * @param player
     * @param game
     * @return playerHand
     */
    public static ArrayList<Card> drawCard(final ArrayList<Card> deck, final Player player,
                                           final Game game) {
        if (deck.size() > 0) {
            player.getPlayerHand().add(deck.get(0));
            deck.remove(0);
        }
        return player.getPlayerHand();
    }

    /**
     * Increasing the player mana based on the round
     * @param player1
     * @param player2
     * @param game
     */
    public static void nextRound(final Player player1, final Player player2, final Game game) {
        // increasing the player mana with a maximum of 10
        if (game.getRound() >= PLAYER_MANA) {
            player1.setMana(player1.getMana() + PLAYER_MANA);
        } else {
            player1.setMana(player1.getMana() + game.getRound());
        }

        // increasing the player mana with a maximum of 10
        if (game.getRound() >= PLAYER_MANA) {
            player2.setMana(player2.getMana() + PLAYER_MANA);
        } else {
            player2.setMana(player2.getMana() + game.getRound());
        }

        game.setRound(game.getRound() + 1);
    }

    /**
     * Getter the player decks
     * @return playerDecks
     */
    public Decks getPlayerDecks() {
        return playerDecks;
    }

    /**
     * Setter for the player decks
     * @param playerDecks
     */
    public void setPlayerDecks(final Decks playerDecks) {
        this.playerDecks = playerDecks;
    }

    /**
     * Getter for the number of the chosen deck
     * @return playerDeckIdx
     */
    public int getPlayerDeckIdx() {
        return playerDeckIdx;
    }

    /**
     * Setter for the number of the chosen deck
     * @param playerDeckIdx
     */
    public void setPlayerDeckIdx(final int playerDeckIdx) {
        this.playerDeckIdx = playerDeckIdx;
    }

    /**
     * Getter for the player hero
     * @return playerHero
     */
    public Hero getPlayerHero() {
        return playerHero;
    }

    /**
     * Setter for the player hero
     * @param playerHero
     */
    public void setPlayerHero(final Hero playerHero) {
        this.playerHero = playerHero;
    }

    /**
     * Getter for the player mana
     * @return mana
     */
    public int getMana() {
        return mana;
    }

    /**
     * Setter for the player mana
     * @param mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * Getter for the player turn
     * @return turn
     */
    public boolean isEndTurn() {
        return endTurn;
    }

    /**
     * Setter for the player turn
     * @param endTurn
     */
    public void setEndTurn(final boolean endTurn) {
        this.endTurn = endTurn;
    }

    /**
     * Getter for the player front row
     * @return frontRow
     */
    public ArrayList<Card> getFrontRow() {
        return frontRow;
    }

    /**
     * Setter for the player front row
     * @param frontRow
     */
    public void setFrontRow(final ArrayList<Card> frontRow) {
        this.frontRow = frontRow;
    }

    /**
     * Getter for the player back row
     * @return backRow
     */
    public ArrayList<Card> getBackRow() {
        return backRow;
    }

    /**
     * Setter for the back row
     * @param backRow
     */
    public void setBackRow(final ArrayList<Card> backRow) {
        this.backRow = backRow;
    }

    /**
     * Getter for the player hand
     * @return playerHand
     */
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    /**
     * Setter for the player hand
     * @param playerHand
     */
    public void setPlayerHand(final ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }
}
