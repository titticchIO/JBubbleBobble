package model;

import view.GameFrame;

public class HelpMethods {
    
    public static boolean canMoveHere(float x, float y, int width, int height, String[][] lvlData) {
        if (!isSolid(x, y, lvlData))
            if (!isSolid(x + width, y, lvlData))
                if (!isSolid(x, y + height, lvlData))
                    if (!isSolid(x + width, y + height, lvlData))
                        return true;
        return false;
    }

    private static boolean isSolid(float x, float y, String[][] lvlData) {
        if (x < 0 || x >= GameFrame.FRAME_WIDTH)
            return true;
        if (y < 0 || y >= GameFrame.FRAME_HEIGHT)
            return true;

        int xIndex = (int) (x / GameFrame.TILE_SIZE);
        int yIndex = (int) (y / GameFrame.TILE_SIZE);

        String value = lvlData[xIndex][yIndex];

        return "#".equals(value.substring(0,1));  //casi in cui c'è un blocco
    }
}
