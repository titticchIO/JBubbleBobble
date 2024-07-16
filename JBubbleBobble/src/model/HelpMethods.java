package model;

import model.entities.MovingEntity;
import view.GameFrame;

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
			int tileXPos = (currentTile + 1) * GameFrame.TILE_SIZE;
			return tileXPos - movingEntity.getWidth();
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

	public static boolean isEntityGrounded(MovingEntity movingEntity, String[][] lvlData) {
		for (float x=movingEntity.getX();x<movingEntity.getX()+movingEntity.getWidth();x++) {
			if (isSolid(x, movingEntity.getY()+movingEntity.getHeight()+1, lvlData))
				return true;
		}
		return false;
		
//		return isSolid(movingEntity.getX()+movingEntity.getWidth()+1, movingEntity.getY()+movingEntity.getHeight()+1, lvlData);
	}

}
