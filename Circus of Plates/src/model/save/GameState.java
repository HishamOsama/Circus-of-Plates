package model.save;

public class GameState {

    private int elapsedTime;
    private int[] scores;

    public GameState(int[] scores, int elapsedTime) {
        this.elapsedTime = elapsedTime;
        this.scores = scores;
    }

    public int[] getScores() {
        return scores;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

}
