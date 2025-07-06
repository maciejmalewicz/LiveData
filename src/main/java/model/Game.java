package model;

import java.util.Objects;

public class Game {

    private static long CURRENT_ID;

    private final long id;
    private final String homeTeam;
    private final String awayTeam;
    private Score score;


    public Game(String homeTeam, String awayTeam) {
        this.id = ++CURRENT_ID;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.score = new Score(0, 0);
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public long getId() {
        return id;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score newScore) {
        score = newScore;
    }

    public int getTotalScore() {
        return score.homeTeamScore() + score.awayTeamScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id && homeTeam.equals(game.homeTeam) && awayTeam.equals(game.awayTeam) && score.equals(game.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, homeTeam, awayTeam, score);
    }
}
