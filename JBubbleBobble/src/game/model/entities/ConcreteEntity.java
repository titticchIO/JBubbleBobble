package game.model.entities;

public class ConcreteEntity extends Entity {
	
	public ConcreteEntity(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public static void main(String[] args) {
		ConcreteEntity e1 = new ConcreteEntity(22, 11, 1, 2);
		ConcreteEntity e2 = new ConcreteEntity(22, 11, 2, 1);
		
		System.out.println(e1.hashCode());
		System.out.println(e2.hashCode());
		System.out.println(e1.equals(e2));
	}

}
