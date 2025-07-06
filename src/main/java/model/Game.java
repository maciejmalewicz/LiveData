package model;

import java.util.Date;

public class Game {

    private final String homeName;
    private final String awayTeam;
    private final Date createTime;
    private Score score;


    public Game(String homeName, String awayTeam) {
        this.homeName = homeName;
        this.awayTeam = awayTeam;
        this.createTime = new Date();
        this.score = new Score(0, 0);
    }

    public String getHomeName() {
        return homeName;
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
}
