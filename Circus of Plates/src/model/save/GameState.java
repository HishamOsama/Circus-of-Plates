package model.save;

public class GameState {

    private int elapsedTime;
    private int[] scores;
    private int diff;

    private double[][] pS;

    public GameState(int[] scores, int elapsedTime, int diff, double[] p1, double[] p2) {
        this.elapsedTime = elapsedTime;
        this.scores = scores;
        this.diff = diff;
        pS = new double[2][];
        pS[0] = p1;
        pS[1] = p2;
    }

    public int[] getScores() {
        return scores;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public int getDiff() {
        return diff;
    }

    public double[] getP(int index) {
        return pS[index];
    }
    public int getPsLength() {
        return pS.length;
    }

}
