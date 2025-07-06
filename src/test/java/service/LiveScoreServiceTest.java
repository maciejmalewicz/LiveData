package service;

import model.Game;
import model.Score;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    void assertGameSaved(Game game) {
        var numOfGames = service.getAllGames().stream()
                .filter(game::equals)
                .count();
        assertEquals(1, numOfGames);
    }

    void assertGame(Game game, String homeTeamName, String awayTeamName, Score score) {
        assertEquals(homeTeamName, game.getHomeTeam());
        assertEquals(awayTeamName, game.getAwayTeam());
        assertEquals(score, game.getScore());
    }

    List<Game> generateSampleGames() {
        return Arrays.asList(
                createGameWithScore("Brazil", "Argentina", new Score(2, 1)), // repeated score A
                createGameWithScore("France", "Germany", new Score(1, 2)),   // repeated score B
                createGameWithScore("Spain", "Portugal", new Score(2, 1)),   // repeated score A
                createGameWithScore("Italy", "England", new Score(0, 0)),    // repeated score C
                createGameWithScore("Netherlands", "Belgium", new Score(3, 2)), // repeated score D
                createGameWithScore("Sweden", "Norway", new Score(1, 1)),    // repeated score E
                createGameWithScore("Denmark", "Finland", new Score(2, 1)),  // repeated score A
                createGameWithScore("Poland", "Czech Republic", new Score(1, 2)), // repeated score B
                createGameWithScore("Austria", "Switzerland", new Score(2, 1)), // repeated score A
                createGameWithScore("Croatia", "Serbia", new Score(0, 0)),   // repeated score C
                createGameWithScore("USA", "Mexico", new Score(1, 0)),
                createGameWithScore("Canada", "Costa Rica", new Score(2, 2)),
                createGameWithScore("Japan", "South Korea", new Score(3, 2)), // repeated score D
                createGameWithScore("Australia", "New Zealand", new Score(1, 1)), // repeated score E
                createGameWithScore("Turkey", "Greece", new Score(2, 1)),     // repeated score A
                createGameWithScore("Russia", "Ukraine", new Score(1, 2)),    // repeated score B
                createGameWithScore("China", "Iran", new Score(1, 0)),
                createGameWithScore("Egypt", "Morocco", new Score(2, 1)),     // repeated score A
                createGameWithScore("Nigeria", "Ghana", new Score(0, 0)),     // repeated score C
                createGameWithScore("Colombia", "Chile", new Score(2, 1))     // repeated score A
        );
    }

    Game createGameWithScore(String homeTeamName, String awayTeamName, Score score) {
        var game = new Game(homeTeamName, awayTeamName);
        game.setScore(score);
        return game;
    }
}