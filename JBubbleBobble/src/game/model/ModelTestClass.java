package game.model;

import game.model.bubbles.PlayerBubble;

public class ModelTestClass {
	
	public static void main(String[] args) {
		PlayerBubble b=new PlayerBubble(0, 0, 0, 0);
		System.out.println(b.getLifeSpan());
		b.updateEntity();
		System.out.println(b.getLifeSpan());
	}
}
