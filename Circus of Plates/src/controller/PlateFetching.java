package controller;

import model.players.PlayerIF;

public class PlateFetching {

    private PlayerIF player;

    public PlateFetching(PlayerIF player) {
        this.player = player;
    }

    public boolean CheckMe(int x, int y) {
        int[][] playerPosition = player.getPlayerPosition();
            int centerX = playerPosition[0][0];
            int centerY = playerPosition[0][1];
            // Checking Right Hand...
            if (x-centerX > 110 - 15 && x-centerX < 110 + 15 ) {
                if (y - centerY > 0 - 5 && y-centerY < 0 + 5 ) {
                    System.out.println("Yes1");
                    System.out.println("CenterX :"+ centerX);
                    System.out.println("CenterY :"+ centerY);
                    System.out.println("Given x :"+ x);
                    System.out.println("Given y :"+ y);
                    return false;
                }
            }
            // Checking Left Hand...
            if (centerX - x > 15 - 15 && centerX -x < 15 + 15 ) {
                if (y - centerY > 35 - 5 && y - centerY < 35 + 5 ) {
                    System.out.println("Yes2");
                    System.out.println("CenterX :"+ centerX);
                    System.out.println("CenterY :"+ centerY);
                    System.out.println("Given x :"+ x);
                    System.out.println("Given y :"+ y);
                    return false;
                }
            }
            

        
        return true;
    }

}
