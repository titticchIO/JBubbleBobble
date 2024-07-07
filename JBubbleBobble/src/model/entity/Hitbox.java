package model.entity;

public class Hitbox {
	/**
	 * x and y coordinates of the rectangle representing the hitbox
	 */
	private float x;
	private float y;

	private float width;
	private float height;

	public Hitbox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Getters and Setters
	 */
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	private float[][] getPoints() {
		return new float[][] { { x, y }, { x + width, y }, { x, y + height }, { x + width, y + height } };
	}
	
	
	/**
	 * method to check thw collision of two hitboxes
	 * @param hitbox
	 * @return			boolean
	 */
	public boolean hit(Hitbox hitbox) {
		float[][] p1 = getPoints();
		float[][] p2 = hitbox.getPoints();
		if (p2[3][0] > p1[0][0] && p2[3][0] < p1[1][0] && p2[3][1] > p1[0][1] && p2[3][1] < p1[3][1])	//check p1 top left
			return true;
		if (p2[2][0] > p1[0][0] && p2[2][0] < p1[1][0] && p2[2][1] > p1[0][1] && p2[2][1] < p1[3][1])	//check p1 top right
			return true;
		if (p2[1][0] > p1[0][0] && p2[1][0] < p1[1][0] && p2[1][1] > p1[0][1] && p2[1][1] < p1[3][1])	//check p1 bottom left
			return true;
		if (p2[0][0] > p1[0][0] && p2[0][0] < p1[1][0] && p2[0][1] > p1[0][1] && p2[0][1] < p1[3][1])	//check p1 bottom right
			return true;
		return false;
	}

}
