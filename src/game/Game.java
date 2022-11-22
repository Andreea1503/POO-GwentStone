package game;
import write.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

/**
 * Information about the game including starting player,
 * the seed after the decks are shuffled, round and turn
 */
public class Game {
    private Integer startingPlayer;
    private Integer shuffleSeed;
    private Integer round;
    private Integer turn;

    public Game(final int startingPlayer, final int shuffleSeed) {
        this.startingPlayer = startingPlayer;
        this.shuffleSeed = shuffleSeed;
        this.round = 1;
        this.turn = 0;
    }

    /**
     * Add the cards in a deck for each player depending on the type of the card
     * @param decksInput
     * @return deckList (a list of decks for each player)
     */
    public static Decks createDecks(final DecksInput decksInput) {
        // getting the cards out from the input
        ArrayList<ArrayList<CardInput>> decks = decksInput.getDecks();
        ArrayList<CardInput> deckInput;
        ArrayList<Card> deck;
        // creating a new list to hold the decks
        ArrayList<ArrayList<Card>> decksList = new ArrayList<>();
        Decks deckList = null;

        for (int i = 0; i < decksInput.getNrDecks(); i++) {
            deckInput = decks.get(i);
            // creating a new deck for each player
            deck = new ArrayList<>();
            for (int j = 0; j < decksInput.getNrCardsInDeck(); j++) {
                CardInput card = deckInput.get(j);
                if (card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                        || card.getName().equals("Goliath") || card.getName().equals("Warden")) {
                    Minion minion = new Minion(card);
                    deck.add(minion);
                } else if (card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                           || card.getName().equals("The Cursed One")
                            || card.getName().equals("Disciple")) {
                    Spells spell = new Spells(card);
                    deck.add(spell);
                } else if (card.getName().equals("Firestorm")
                            || card.getName().equals("Winterfell")
                            || card.getName().equals("Heart Hound")) {
                    Environment environmentCard = new Environment(card);
                    deck.add(environmentCard);
                }
            }
            decksList.add(deck);
            deckList = new Decks(decksInput.getNrCardsInDeck(), decksInput.getNrDecks(),
                                 decksList);
        }
        return deckList;
    }

    /**
     * Unfreezing and unblocking the cards and changing the round of the game
     * based on when the players finish their turn
     * @param playerOneDeck
     * @param playerTwoDeck
     * @param player1
     * @param player2
     * @param game
     */
    public static void endTurn(final ArrayList<Card> playerOneDeck,
                               final ArrayList<Card> playerTwoDeck,
                               final Player player1, final Player player2, final Game game) {
        if (game.getTurn() == 1) {
            game.setTurn(2);
            player1.setEndTurn(true);
            game.unfreezeAndUnblockCards(player1.getFrontRow(), player1);
            game.unfreezeAndUnblockCards(player1.getBackRow(), player1);
        } else {
            game.setTurn(1);
            player2.setEndTurn(true);
            game.unfreezeAndUnblockCards(player2.getFrontRow(), player2);
            game.unfreezeAndUnblockCards(player2.getBackRow(), player2);
        }

        if (player1.isEndTurn() && player2.isEndTurn()) {
            Player.drawCard(playerOneDeck, player1, game);
            Player.drawCard(playerTwoDeck, player2, game);
            Player.nextRound(player1, player2, game);
            player1.setEndTurn(false);
            player2.setEndTurn(false);
        }
    }

    /**
     * Setting the fields false for the frozen, attack, used ability fields
     * @param deck
     * @param player
     */
    public void unfreezeAndUnblockCards(final ArrayList<Card> deck, final Player player) {
        for (Card card : deck) {
            if (card.isFrozen()) {
                card.setFrozen(false);
            }
            if (card.isAttack()) {
                card.setAttack(false);
            }
            if (card.isUsedAbility()) {
                card.setUsedAbility(false);
            }
        }
        player.getPlayerHero().setAttack(false);
    }

    /**
     * Parsing the commands from the input performing the actions
     * while using the Write class to construct the output
     * @param actionsInputs
     * @param game
     * @param player1
     * @param player2
     * @param output
     * @param wins
     */
    public void parseActions(final ArrayList<ActionsInput> actionsInputs, final Game game,
                             final Player player1, final Player player2, final ArrayNode output,
                             final ArrayList<Integer> wins) {
        ArrayList<Card> playerOneDeck = Player.getPlayerDeck(player1, player1.getPlayerDeckIdx(),
                                                             game);
        ArrayList<Card> playerTwoDeck = Player.getPlayerDeck(player2, player2.getPlayerDeckIdx(),
                                                             game);
        game.setTurn(game.getStartingPlayer());

        player1.setPlayerHand(Player.drawCard(playerOneDeck, player1, game));
        player2.setPlayerHand(Player.drawCard(playerTwoDeck, player2, game));

        Player.nextRound(player1, player2, game);
        Write write = new Write();

        for (ActionsInput action : actionsInputs) {
            if (action.getCommand().equals("getPlayerDeck")) {
                if (action.getPlayerIdx() == 1) {
                    write.writeDeck(playerOneDeck, action, output);
                } else {
                    write.writeDeck(playerTwoDeck, action, output);
                }
            } else if (action.getCommand().equals("getPlayerHero")) {
                if (action.getPlayerIdx() == 1) {
                    Hero hero = player1.getPlayerHero();
                    write.outputCard(hero, action, output);
                } else {
                    Hero hero = player2.getPlayerHero();
                    write.outputCard(hero, action, output);
                }
            } else if (action.getCommand().equals("getPlayerTurn")) {
                write.getPlayerTurn(action, game.getTurn(), output);
            } else if (action.getCommand().equals("endPlayerTurn")) {
                endTurn(playerOneDeck, playerTwoDeck, player1, player2, game);
            } else if (action.getCommand().equals("placeCard")) {
                if (game.getTurn() == 1 && action.getHandIdx() < player1.getPlayerHand().size()
                        && player1.getPlayerHand().size() > 0) {
                    placeCardOnTheTable(action, output, player1);
                } else if (game.getTurn() == 2 && action.getHandIdx()
                        < player2.getPlayerHand().size() && player2.getPlayerHand().size() > 0) {
                    placeCardOnTheTable(action, output, player2);
                }
            } else if (action.getCommand().equals("getCardsInHand")) {
                if (action.getPlayerIdx() == 1) {
                    write.getCardsFromHand(player1.getPlayerHand(), action, output);
                } else {
                    write.getCardsFromHand(player2.getPlayerHand(), action, output);
                }
            } else if (action.getCommand().equals("getPlayerMana")) {
                if (action.getPlayerIdx() == 1) {
                    write.getPlayerMana(action, player1.getMana(), output);
                } else {
                    write.getPlayerMana(action, player2.getMana(), output);
                }
            } else if (action.getCommand().equals("getCardsOnTable")) {
                write.getCardsOnTheTable(player1, player2, action, output);
            } else if (action.getCommand().equals("useEnvironmentCard")) {
                if (game.getTurn() == 1) {
                    write.useEnvironmentCard(player1.getPlayerHand(), player1, player2, action,
                                             output, game);
                } else {
                    write.useEnvironmentCard(player2.getPlayerHand(), player1, player2, action,
                                             output, game);
                }
            } else if (action.getCommand().equals("getCardAtPosition")) {
                write.getThePositionOfTheCard(player1, player2, action, output);
            } else if (action.getCommand().equals("getEnvironmentCardsInHand")) {
                if (action.getPlayerIdx() == 1) {
                    write.getEnvironmentCardsInHand(player1, action, output);
                } else {
                    write.getEnvironmentCardsInHand(player2, action, output);
                }
            } else if (action.getCommand().equals("getFrozenCardsOnTable")) {
                write.getFrozenCardsOnTheTable(player1, player2, action, output);
            } else if (action.getCommand().equals("cardUsesAttack")) {
                write.attackCard(player1, player2, action, output, game);
            } else if (action.getCommand().equals("cardUsesAbility")) {
                write.useCardAbility(player1, player2, game, action, output);
            } else if (action.getCommand().equals("useAttackHero")) {
                Player playerWins = write.useAttackHero(player1, player2, game, action, output);
                if (playerWins != null) {
                    if (playerWins.equals(player1)) {
                        wins.set(0, wins.get(0) + 1);
                    } else if (playerWins.equals(player2)) {
                        wins.set(1, wins.get(1) + 1);
                    }
                }
            } else if (action.getCommand().equals("useHeroAbility")) {
                write.useHeroAbility(player1, player2, game, action, output);
            } else if (action.getCommand().equals("getPlayerOneWins")) {
                write.playerWins(wins.get(0), action, output);
            } else if (action.getCommand().equals("getPlayerTwoWins")) {
                write.playerWins(wins.get(1), action, output);
            } else if (action.getCommand().equals("getTotalGamesPlayed")) {
                int totalOfGames = wins.get(0) + wins.get(1);
                write.playerWins(totalOfGames, action, output);
            }
        }
    }

    /**
     * Placing card on the table if it matches the criteria,
     * otherwise writing an output error
     * @param action
     * @param output
     * @param player
     */
    public static void placeCardOnTheTable(final ActionsInput action, final ArrayNode output,
                                           final Player player) {
        Write write = new Write();

        for (int i = 0; i < player.getPlayerHand().size(); i++) {
            if (action.getHandIdx() == i && action.getHandIdx() < player.getPlayerHand().size()) {
                 boolean errorOccurred = write.placeCard(player.getPlayerHand().get(i), output,
                                                        player, action);
                if (!errorOccurred) {
                    placeCardOnTable(player, player.getPlayerHand().get(i));
                }
            }
        }
     }

    /**
     * Placing cards on the front row or back row of the table, depending on the
     * type of the card
     * @param player
     * @param card
     */
     public static void placeCardOnTable(final Player player, final Card card) {
         if (card.getName().equals("The Ripper") || card.getName().equals("Miraj")
                 || card.getName().equals("Goliath") || card.getName().equals("Warden")) {
             player.setMana(player.getMana() - card.getMana());
             player.getFrontRow().add(card);
             player.getPlayerHand().remove(card);
         } else if (card.getName().equals("Sentinel") || card.getName().equals("Berserker")
                 || card.getName().equals("The Cursed One") || card.getName().equals("Disciple")) {
             player.setMana(player.getMana() - card.getMana());
             player.getBackRow().add(card);
             player.getPlayerHand().remove(card);
         }
     }

    /**
     * Getter for the starting player
     * @return startingPlayer
     */
    public int getStartingPlayer() {
        return startingPlayer;
    }

    /**
     * Setter for the starting player
     * @param startingPlayer
     */
    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    /**
     * Getter for the shuffle seed
     * @return shuffleSeed
     */
    public int getShuffleSeed() {
        return shuffleSeed;
    }

    /**
     * Setter for the shuffle seed
     * @param shuffleSeed
     */
    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    /**
     * Getter for the player turn
     * @return turn
     */
    public Integer getTurn() {
        return turn;
    }

    /**
     * Setter for the player turn
     * @param turn
     */
    public void setTurn(final Integer turn) {
        this.turn = turn;
    }

    /**
     * Getter for the game round
     * @return round
     */
    public Integer getRound() {
        return round;
    }

    /**
     * Setter for the game round
     * @param round
     */
    public void setRound(final Integer round) {
        this.round = round;
    }
}
