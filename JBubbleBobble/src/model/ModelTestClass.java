package model;

import model.entity.Hitbox;

public class ModelTestClass {
	public static void main(String[] args) {
		Hitbox h1=new Hitbox(0, 0, 10, 20);
		Hitbox h2=new Hitbox(9, 10, 10, 10);
		System.out.println(h1.hit(h2));
	}
}
