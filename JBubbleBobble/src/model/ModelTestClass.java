package model;

import model.entity.Entity;


public class ModelTestClass {
	public static void main(String[] args) {
		Entity e1= new Entity(0, 0, 10, 20);
		Entity e2= new Entity(9, 10, 10, 10);
		System.out.println(e2.hit(e1));
	}
}
