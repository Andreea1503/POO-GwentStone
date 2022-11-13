package main;

import Game.*;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation

//        DecksInput decksInput = inputData.getPlayerOneDecks();
//        ArrayList<ArrayList<CardInput>> decks = decksInput.getDecks();
//        ArrayList<CardInput> deck = decks.get(0);
//        CardInput card = deck.get(0);
//
//        Minion minion = new Minion(card);
//
//        System.out.println(minion);

        DecksInput decksInputPlayer1 = inputData.getPlayerOneDecks();
        DecksInput decksInputPlayer2 = inputData.getPlayerTwoDecks();
//        Game.createDecks(decksInput);
        Player player = new Player(Game.createDecks(decksInputPlayer1), Game.createDecks(decksInputPlayer2));

        ArrayList<GameInput> input = inputData.getGames();
        Game game = null;
        for (GameInput gameInput : input) {
            StartGameInput stGameInput = gameInput.getStartGame();
            Hero playerOneHero = new Hero(stGameInput.getPlayerOneHero());
            Hero playerTwoHero = new Hero(stGameInput.getPlayerTwoHero());
            game = new Game(stGameInput.getPlayerOneDeckIdx(), stGameInput.getPlayerTwoDeckIdx(), stGameInput.getShuffleSeed(),
                            playerOneHero, playerTwoHero, stGameInput.getStartingPlayer());
//            System.out.println(game);
        }



        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
