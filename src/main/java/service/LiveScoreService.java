package service;

import model.Game;
import model.Score;

import java.util.List;

public class LiveScoreService implements ILiveScoreService {

    public LiveScoreService() {
    }

    public LiveScoreService(List<Game> existingGames) {
    }

    @Override
    public Game startGame(String homeTeamName, String awayTeamName) {
        throw new RuntimeException("Not implemented");
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
        throw new RuntimeException("Not implemented");
    }
}
