package Game;

import fileio.ActionsInput;
import fileio.DecksInput;
import fileio.StartGameInput;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player {
    Decks playerDecks;
    int playerDeckIdx;
    Hero playerHero;
    int mana;
    boolean endTurn;
    ArrayList<Card> frontRow;
    ArrayList<Card> backRow;

    public Player(Decks playerDecks, int playerDeckIdx,
                  Hero playerHero) {
        this.playerDecks = playerDecks;
        this.playerDeckIdx = playerDeckIdx;
        this.playerHero = playerHero;
        this.mana = 0;
        this.endTurn = false;
        this.frontRow = new ArrayList<>();
        this.backRow = new ArrayList<>();
    }

    public static ArrayList<Card> getPlayerDeck(Player player, int index, Game game) {
        ArrayList<Card> deck = null;
        for (int i = 0; i < player.getPlayerDecks().getNrDecks(); i++) {
            if(i == index) {
                deck = player.getPlayerDecks().getDecks().get(i);
            }
        }
        Collections.shuffle(deck, new Random(game.getShuffleSeed()));
        return deck;
    }

    public static ArrayList<Card> Round(ArrayList<Card> deck, Player player, Game game){
        ArrayList<Card> hand = new ArrayList<>();
        if (deck != null) {
            hand.add(deck.get(0));
            deck.remove(0);
        }
        game.round++;
        if (player.mana + game.round <= 10) {
            player.mana += game.round;
        }
        return hand;
    }
    public Decks getPlayerDecks() {
        return playerDecks;
    }

    public void setPlayerDecks(Decks playerDecks) {
        this.playerDecks = playerDecks;
    }

    public int getPlayerDeckIdx() {
        return playerDeckIdx;
    }

    public void setPlayerDeckIdx(int playerDeckIdx) {
        this.playerDeckIdx = playerDeckIdx;
    }


    public Hero getPlayerHero() {
        return playerHero;
    }

    public void setPlayerHero(Hero playerHero) {
        this.playerHero = playerHero;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }


}
