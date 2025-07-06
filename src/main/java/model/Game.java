package model;

import java.util.Date;
import java.util.Objects;

public class Game {

    private final String homeTeam;
    private final String awayTeam;
    private final Date createTime;
    private Score score;


    public Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.createTime = new Date();
        this.score = new Score(0, 0);
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score newScore) {
        score = newScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return homeTeam.equals(game.homeTeam) && awayTeam.equals(game.awayTeam) && createTime.equals(game.createTime) && score.equals(game.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, createTime, score);
    }
}
