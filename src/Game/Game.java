package Game;

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
}
