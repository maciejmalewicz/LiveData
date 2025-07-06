package service;

import model.Game;
import model.Score;

import java.util.ArrayList;
import java.util.List;

public class LiveScoreService implements ILiveScoreService {

    private final List<Game> games;

    public LiveScoreService() {
        games = new ArrayList<>();
    }

    public LiveScoreService(List<Game> existingGames) {
        games = new ArrayList<>(existingGames);
    }

    @Override
    public Game startGame(String homeTeamName, String awayTeamName) {
        var game = new Game(homeTeamName, awayTeamName);
        games.add(game);
        return game;
    }

    @Override
    public boolean finishGame(Game game) {
        var toBeRemoved = games.stream()
                .filter(g -> g == game)
                .findFirst();
        toBeRemoved.ifPresent(games::remove);
        return toBeRemoved.isPresent();
    }

    @Override
    public boolean updateScore(Game game, Score score) {
        var toBeUpdated = games.stream()
                .filter(g -> g == game)
                .findFirst();
        toBeUpdated.ifPresent(g -> g.setScore(score));
        return toBeUpdated.isPresent();
    }

    @Override
    public List<Game> getSummaryByTotalScore() {
        throw new RuntimeException("Not implemented");
    }

    List<Game> getAllGames() {
        return games;
    }
}
