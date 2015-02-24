package Entities;

// Do not drop any potions
public class Skeleton extends Enemy {
	
	public Skeleton() {
		super("Skeleton");
		setPotionDropChance(0);
	}
	
	public void emptyPockets() {
		System.out.println("The " + getName() + " never drops Health Potions.");
	}
	
}
