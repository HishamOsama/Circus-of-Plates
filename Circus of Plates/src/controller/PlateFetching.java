package controller;

import model.players.PlayerIF;

public class PlateFetching {

    private PlayerIF player;

    public PlateFetching(PlayerIF player) {
        this.player = player;
    }

    public boolean CheckMe(double x, double y) {
        float[][] stackCenters = player.getStacksCenters();
        for (int i = 0; i < stackCenters.length; i++) {
            float centerX = stackCenters[i][0];
            float centerY = stackCenters[i][1];
            if (Math.abs(centerX - x) <= 30) {
                if (Math.abs(centerY - y) <= 5) {
                    System.out.println("CenterX :"+ centerX);
                    System.out.println("CenterY :"+ centerY);
                    System.out.println("Given x :"+ x);
                    System.out.println("Given y :"+ y);
                    //player.updateHight(50, i);
                    return false;
                }
            }

        }
        return true;
    }

}
