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
        return getAllGames().stream().sorted((g1, g2) -> {
            if (g1.getTotalScore() != g2.getTotalScore()) {
                return g2.getTotalScore() - g1.getTotalScore();
            }
            return g1.getId() < g2.getId() ? -1 : 1;
        }).toList();
    }

    List<Game> getAllGames() {
        return games;
    }
}
