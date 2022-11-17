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
    Integer mana;
    boolean endTurn;
    ArrayList<Card> frontRow;
    ArrayList<Card> backRow;

    ArrayList<Card> playerHand;

    public Player(Decks playerDecks, int playerDeckIdx,
                  Hero playerHero) {
        this.playerDecks = playerDecks;
        this.playerDeckIdx = playerDeckIdx;
        this.playerHero = playerHero;
        this.mana = 0;
        this.endTurn = false;
        this.frontRow = new ArrayList<>();
        this.backRow = new ArrayList<>();
        this.playerHand = new ArrayList<>();
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

    public static ArrayList<Card> drawCard(ArrayList<Card> deck, Player player, Game game){
        if (deck.size() > 0) {
            player.playerHand.add(deck.get(0));
            deck.remove(0);
        }
        return player.playerHand;
    }

    public static void nextRound(Player player1, Player player2, Game game) {
        if (player1.mana <= 10) {
            player1.mana += game.round;
        }
        if(player2.mana <= 10) {
            player2.mana += game.round;
        }
        game.round++;
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

    public ArrayList<Card> getFrontRow() {
        return frontRow;
    }

    public void setFrontRow(ArrayList<Card> frontRow) {
        this.frontRow = frontRow;
    }

    public ArrayList<Card> getBackRow() {
        return backRow;
    }

    public void setBackRow(ArrayList<Card> backRow) {
        this.backRow = backRow;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

}
