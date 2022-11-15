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

    public Player(Decks playerDecks, int playerDeckIdx,
                  Hero playerHero) {
        this.playerDecks = playerDecks;
        this.playerDeckIdx = playerDeckIdx;
        this.playerHero = playerHero;
        this.mana = 0;
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
        hand.add(deck.get(0));
        deck.remove(0);
        player.mana += game.round;
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



}
