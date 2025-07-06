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
    public void finishGame(Game game) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Game updateScore(Game game, Score score) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<Game> getSummaryByTotalScore(Score totalScore) {
        throw new RuntimeException("Not implemented");
    }

    List<Game> getAllGames() {
        return games;
    }
}
