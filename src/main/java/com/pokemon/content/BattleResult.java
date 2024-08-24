package com.pokemon.content;

public class BattleResult {

    private String winnerName;
    private int remainingHitPoints;

    public BattleResult(String winnerName, int remainingHitPoints) {
        this.winnerName = winnerName;
        this.remainingHitPoints = remainingHitPoints;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public int getRemainingHitPoints() {
        return remainingHitPoints;
    }

    public void setRemainingHitPoints(int remainingHitPoints) {
        this.remainingHitPoints = remainingHitPoints;
    }
}
