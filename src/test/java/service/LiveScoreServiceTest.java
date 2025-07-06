package service;

import model.Game;
import model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LiveScoreServiceTest {

    private LiveScoreService service;

    @BeforeEach
    void setupTests() {
        service = new LiveScoreService();
    }

    @Test
    void testStartGame_FirstGame() {
        var game = service.startGame("Poland", "Germany");
        assertGameSaved(game);
        assertGame(game, "Poland", "Germany", new Score(0, 0));
    }

    @Test
    void testStartGame_NotFirstGame() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);
        var game = service.startGame("Poland", "Germany");
        assertGameSaved(game);
        assertGame(game, "Poland", "Germany", new Score(0, 0));

        assertEquals(sampleGames.size() + 1, service.getAllGames().size());
        sampleGames.forEach(this::assertGameSaved);
    }

    @Test
    void testStartGame_DoubleTeamNames() {
        var game1 = service.startGame("Poland", "Germany");
        var game2 = service.startGame("Poland", "Germany");
        assertGameSaved(game1);
        assertGame(game1, "Poland", "Germany", new Score(0, 0));
        assertGameSaved(game2);
        assertGame(game2, "Poland", "Germany", new Score(0, 0));
    }

    @Test
    void testFinishGame_NormalScenario() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);
        var brazilArgentinaGame = sampleGames.get(0);
        var result = service.finishGame(brazilArgentinaGame);
        assertEquals(sampleGames.size() - 1, service.getAllGames().size());
        assertGameNotSaved(brazilArgentinaGame);
        assertTrue(result);
    }

    @Test
    void testFinishGame_GameNotExisting() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);

        var gameToRemove = new Game("USA", "China");
        var result = service.finishGame(gameToRemove);
        assertEquals(sampleGames.size(), service.getAllGames().size());
        assertFalse(result);
    }

    @Test
    void testUpdateGame_NormalScenario() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);
        var brazilArgentinaGame = sampleGames.get(0);
        var allOtherGames = sampleGames.stream().filter(g -> !g.equals(brazilArgentinaGame)).toList();
        var newScore = new Score(3, 1);
        var result = service.updateScore(brazilArgentinaGame, newScore);
        assertEquals(sampleGames.size(), service.getAllGames().size());
        assertHavingGameWithScore(brazilArgentinaGame.getHomeTeam(), brazilArgentinaGame.getAwayTeam(), newScore);
        allOtherGames.forEach(game -> assertHavingGameWithScore(game.getHomeTeam(), game.getAwayTeam(), game.getScore()));
        assertTrue(result);
    }

    @Test
    void testUpdateGame_NonExistingGame() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);
        var toBeUpdated = new Game("USA", "Portugal");
        var newScore = new Score(3, 1);
        var result = service.updateScore(toBeUpdated, newScore);
        assertEquals(sampleGames.size(), service.getAllGames().size());
        sampleGames.forEach(game -> assertHavingGameWithScore(game.getHomeTeam(), game.getAwayTeam(), game.getScore()));
        assertFalse(result);
    }

    @Test
    void testGetSummaryByTotalScore() {
        var sampleGames = generateSampleGames();
        service = new LiveScoreService(sampleGames);
        var gamesOrdered = service.getSummaryByTotalScore();
        var expectedResult = generateOrderedSampleGames();
        assertEquals(expectedResult.size(), gamesOrdered.size());
        IntStream.range(0, expectedResult.size()).forEach(i -> {
            var expectedGame = expectedResult.get(i);
            var actualGame = expectedResult.get(i);
            assertEquals(expectedGame.getHomeTeam(), actualGame.getHomeTeam());
            assertEquals(expectedGame.getAwayTeam(), actualGame.getAwayTeam());
            assertEquals(expectedGame.getScore(), actualGame.getScore());
        });
    }


    void assertHavingGameWithScore(String homeTeam, String awayTeam, Score score) {
        assertEquals(1, service.getAllGames().stream()
                .filter(g -> g.getHomeTeam().equals(homeTeam) && g.getAwayTeam().equals(awayTeam) && g.getScore().equals(score))
                .count());
    }

    void assertGameSaved(Game game) {
        var numOfGames = service.getAllGames().stream()
                .filter(game::equals)
                .count();
        assertEquals(1, numOfGames);
    }

    void assertGameNotSaved(Game game) {
        var numOfGames = service.getAllGames().stream()
                .filter(game::equals)
                .count();
        assertEquals(0, numOfGames);
    }

    void assertGame(Game game, String homeTeamName, String awayTeamName, Score score) {
        assertEquals(homeTeamName, game.getHomeTeam());
        assertEquals(awayTeamName, game.getAwayTeam());
        assertEquals(score, game.getScore());
    }

    List<Game> generateSampleGames() {
        return Arrays.asList(
                createGameWithScore("Brazil", "Argentina", new Score(2, 1)),
                createGameWithScore("France", "Germany", new Score(1, 2)),
                createGameWithScore("Spain", "Portugal", new Score(2, 1)),
                createGameWithScore("Italy", "England", new Score(0, 0)),
                createGameWithScore("Netherlands", "Belgium", new Score(3, 2)),
                createGameWithScore("Sweden", "Norway", new Score(1, 1)),
                createGameWithScore("Denmark", "Finland", new Score(2, 1)),
                createGameWithScore("Poland", "Czech Republic", new Score(1, 2)),
                createGameWithScore("Austria", "Switzerland", new Score(2, 1)),
                createGameWithScore("Croatia", "Serbia", new Score(0, 0)),
                createGameWithScore("USA", "Mexico", new Score(1, 0)),
                createGameWithScore("Canada", "Costa Rica", new Score(2, 2)),
                createGameWithScore("Japan", "South Korea", new Score(3, 2)),
                createGameWithScore("Australia", "New Zealand", new Score(1, 1)),
                createGameWithScore("Turkey", "Greece", new Score(2, 1)),
                createGameWithScore("Russia", "Ukraine", new Score(1, 2)),
                createGameWithScore("China", "Iran", new Score(1, 0)),
                createGameWithScore("Egypt", "Morocco", new Score(2, 1)),
                createGameWithScore("Nigeria", "Ghana", new Score(0, 0)),
                createGameWithScore("Colombia", "Chile", new Score(2, 1))
        );
    }

    List<Game> generateOrderedSampleGames() {
        return Arrays.asList(
                // total score 5
                createGameWithScore("Netherlands", "Belgium", new Score(3, 2)),
                createGameWithScore("Japan", "South Korea", new Score(3, 2)),
                // total score 4
                createGameWithScore("Canada", "Costa Rica", new Score(2, 2)),
                // total score 3
                createGameWithScore("Brazil", "Argentina", new Score(2, 1)),
                createGameWithScore("France", "Germany", new Score(1, 2)),
                createGameWithScore("Spain", "Portugal", new Score(2, 1)),
                createGameWithScore("Denmark", "Finland", new Score(2, 1)),
                createGameWithScore("Poland", "Czech Republic", new Score(1, 2)),
                createGameWithScore("Austria", "Switzerland", new Score(2, 1)),
                createGameWithScore("Turkey", "Greece", new Score(2, 1)),
                createGameWithScore("Russia", "Ukraine", new Score(1, 2)),
                createGameWithScore("Egypt", "Morocco", new Score(2, 1)),
                createGameWithScore("Colombia", "Chile", new Score(2, 1)),
                // total score 2
                createGameWithScore("Sweden", "Norway", new Score(1, 1)),
                createGameWithScore("Australia", "New Zealand", new Score(1, 1)),
                // total sore 1
                createGameWithScore("USA", "Mexico", new Score(1, 0)),
                createGameWithScore("China", "Iran", new Score(1, 0)),
                // total score 0
                createGameWithScore("Italy", "England", new Score(0, 0)),
                createGameWithScore("Croatia", "Serbia", new Score(0, 0)),
                createGameWithScore("Nigeria", "Ghana", new Score(0, 0))
        );
    }

    Game createGameWithScore(String homeTeamName, String awayTeamName, Score score) {
        var game = new Game(homeTeamName, awayTeamName);
        game.setScore(score);
        return game;
    }
}