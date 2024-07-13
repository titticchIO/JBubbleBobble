package model;

import view.GameFrame;

public class HelpMethods {

	public static boolean canMoveHere(float x, float y, int width, int height, String[][] lvlData) {
		if (!isSolid(x, y, lvlData) && !isSolid(x + width, y, lvlData) && !isSolid(x, y + height, lvlData)
				&& !isSolid(x + width, y + height, lvlData))
			return true;
		return false;
	}

	private static boolean isSolid(float x, float y, String[][] lvlData) {
		if (x < 0 || x >= GameFrame.FRAME_WIDTH) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= GameFrame.FRAME_HEIGHT) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		int xIndex = (int) (x / GameFrame.TILE_SIZE);
		int yIndex = (int) (y / GameFrame.TILE_SIZE);

		String value = lvlData[yIndex][xIndex];

		return "#".equals(value.substring(0, 1)); // casi in cui c'è un blocco
	}
}
