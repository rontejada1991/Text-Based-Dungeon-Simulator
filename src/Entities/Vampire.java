package Entities;

public class Vampire extends Enemy {
	public final double SYPHON_MOD = 1; 
	
	public Vampire() {
		super("Vampire");
	}
	
	public void syphonAbility() {
		setHealth(getHealth() + (int)(getDamageDealt() * SYPHON_MOD));
		System.out.println("\t> The " + getName() + " siphoned " + (int)(getDamageDealt() * SYPHON_MOD) + " health from you!");
	}

}
