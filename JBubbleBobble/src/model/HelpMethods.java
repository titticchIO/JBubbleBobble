package model;

import model.entities.MovingEntity;
import model.level.LevelLoader;
import view.GameFrame;
import static view.GameFrame.SCALE;

public class HelpMethods {

	public static boolean canMoveHere(float x, float y, int width, int height, String[][] lvlData) {
		if (!isSolid(x, y, lvlData) && !isSolid(x + width, y, lvlData) && !isSolid(x, y + height, lvlData)
				&& !isSolid(x + width, y + height, lvlData))
			return true;
		return false;
	}

	public static boolean isSolid(float x, float y, String[][] lvlData) {
		if (x < 0 || x >= GameFrame.FRAME_WIDTH) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= GameFrame.FRAME_HEIGHT) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		float xIndex = x / GameFrame.TILE_SIZE;
		float yIndex = y / GameFrame.TILE_SIZE;

		String value = lvlData[(int) yIndex][(int) xIndex];

		return "#".equals(value.substring(0, 1)); // casi in cui c'è un blocco
	}

	public static float getEntityXPosNextToWall(MovingEntity movingEntity, float xSpeed) {
		int currentTile = (int) (movingEntity.getX() / GameFrame.TILE_SIZE);
		if (xSpeed > 0) {
			// right
			int tileXPos = (currentTile+1 ) * GameFrame.TILE_SIZE;
			int xOffset = (int) (GameFrame.TILE_SIZE - movingEntity.getWidth());
//			int xOffset = 0;
			return tileXPos+xOffset - movingEntity.getWidth()-SCALE;
		} else {
			// left
			return currentTile * GameFrame.TILE_SIZE;
		}
	}

	public static float getEntityPosUnderRoofOrAboveFloor(MovingEntity movingEntity, float airSpeed) {
		int currentTile = (int) (movingEntity.getY() / GameFrame.TILE_SIZE);
		if (airSpeed > 0) {
			// falling or touching floor
			int tileYPos = currentTile * GameFrame.TILE_SIZE;
			int yOffset = (int) (GameFrame.TILE_SIZE - movingEntity.getHeight());
			return tileYPos + yOffset - 1 + movingEntity.getHeight();
		} else {
			// jumping
			return (currentTile * GameFrame.TILE_SIZE);
		}
	}

//	checks if any of the pixels under the entity are solid
	public static boolean isEntityGrounded(MovingEntity movingEntity, String[][] lvlData) {
		return isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
				movingEntity.getY() + movingEntity.getHeight() + 1);
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidHorizontalLine(float x1, float x2, float y) {
		for (float x = x1; x <= x2; x += 0.1) {
			if (isSolid(x, y, LevelLoader.getLevelData()))
				return true;
		}
		return false;
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidVerticalLine(float x, float y1, float y2) {
		for (float y = y1; y <= y2; y += 0.1) {
			if (isSolid(x, y, LevelLoader.getLevelData()))
				return true;
		}
		return false;
	}

}
