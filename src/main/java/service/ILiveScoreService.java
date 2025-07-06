package service;

import model.Game;
import model.Score;

import java.util.List;

public interface ILiveScoreService {

    Game startGame(String homeTeamName, String awayTeamName);
    boolean finishGame(Game game);
    boolean updateScore(Game game, Score score);
    List<Game> getSummaryByTotalScore(Score totalScore);
}
