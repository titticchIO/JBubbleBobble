package game.model;

import game.model.entities.MovingEntity;
import game.model.level.LevelLoader;

import static game.model.Settings.GAME_HEIGHT;
import static game.model.Settings.GAME_WIDTH;
import static game.model.Settings.TILE_SIZE;
import static game.model.Settings.SCALE;

public class HelpMethods {

	public static boolean canMoveHere(float x, float y, int width, int height) {
		if (!isSolid(x, y) && !isSolid(x + width, y) && !isSolid(x, y + height) && !isSolid(x + width, y + height))
			return true;
		return false;
	}

	public static boolean isSolid(float x, float y) {
		String[][] lvlData = LevelLoader.getLevelData();
		if (x < 0 || x >= GAME_WIDTH) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		if (y < 0 || y >= GAME_HEIGHT) {
			System.out.println("OUT OF BOUNDS");
			return true;
		}
		float xIndex = x / TILE_SIZE;
		float yIndex = y / TILE_SIZE;

		String value = lvlData[(int) yIndex][(int) xIndex];

		return "#".equals(value.substring(0, 1)); // casi in cui c'è un blocco
	}

	public static boolean isEntityInsideWall(float x, float y, float width, float height) {
		return isSolid(x, y) || isSolid(x + width, y) || isSolid(x, y + height) || isSolid(x + width, y + height);
	}

	public static float getEntityXPosNextToWall(MovingEntity movingEntity, float xSpeed) {
		int currentTile = (int) (movingEntity.getX() / TILE_SIZE);
		if (xSpeed > 0) {
			// right
			int tileXPos = (currentTile + 1) * TILE_SIZE;
//			int xOffset = (int) (GameFrame.TILE_SIZE - movingEntity.getWidth());
//			int xOffset = 0;
			return tileXPos - movingEntity.getWidth() - 1;
		} else {
			// left
			return currentTile * TILE_SIZE;
		}
	}

	public static float getEntityPosUnderRoofOrAboveFloor(MovingEntity movingEntity, float airSpeed) {
		int currentTile = (int) (movingEntity.getY() / TILE_SIZE);
		if (airSpeed > 0) {
			// falling or touching floor
			int tileYPos = currentTile * TILE_SIZE;
			int yOffset = (int) (TILE_SIZE - movingEntity.getHeight());
			return tileYPos + yOffset - 1;
		} else {
			// jumping
			return (currentTile * TILE_SIZE);
		}
	}

//	checks if any of the pixels under the entity are solid
	public static boolean isEntityGrounded(MovingEntity movingEntity) {
		return isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
				movingEntity.getY() + movingEntity.getHeight() + 1)
				&& !isSolidHorizontalLine(movingEntity.getX(), movingEntity.getX() + movingEntity.getWidth(),
						movingEntity.getY() + movingEntity.getHeight() - 1);
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidHorizontalLine(float x1, float x2, float y) {
		for (float x = x1; x <= x2; x += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

//	checks if any pixel in the line is solid
	public static boolean isSolidVerticalLine(float x, float y1, float y2) {
		for (float y = y1; y <= y2; y += 0.1) {
			if (isSolid(x, y))
				return true;
		}
		return false;
	}

}
