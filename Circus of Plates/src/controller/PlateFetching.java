package controller;

import model.players.PlayerIF;

public class PlateFetching {

    private PlayerIF player;

    public PlateFetching(PlayerIF player) {
        this.player = player;
    }

    public CheckResult CheckMe(int x, int y) {
        int[][] playerPosition = player.getPlayerPosition();
        int centerX = playerPosition[0][0];
        int centerY = playerPosition[0][1];
        // Checking Right Hand...
        if (x - centerX > 110 - 15 && x - centerX < 110 + 15) {
            if (y - centerY > 0 - 5 && y - centerY < 0 + 5) {
                return new CheckResult(0, false);
            }
        }
        // Checking Left Hand.....:*
        if (centerX - x > 15 - 15 && centerX - x < 15 + 15) {
            if (y - centerY > 35 - 5 && y - centerY < 35 + 5) {
                return new CheckResult(1, false);
            }
        }

        return new CheckResult(-1, true);
    }

    public class CheckResult {
        private int indexOfStack;
        private boolean intersect;

        public CheckResult(int index, boolean result) {
            indexOfStack = index;
            intersect = result;
        }

        public int getIndex() {
            return indexOfStack;
        }

        public boolean getResult() {
            return intersect;
        }
    }

}
