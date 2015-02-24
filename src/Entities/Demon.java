package Entities;

public class Demon extends Enemy {
	private final int EVADE_CHANCE = 40;
	
	public Demon() {
		super("Demon");
	}
	
	public boolean nimbleAbility() {
		// Return true if the nimble ability worked, otherwise false
		if(getRand().nextInt(100) < EVADE_CHANCE) {
			System.out.println("\t> The " + getName() + " deceived you, causing you to miss!");
			return true;
		} else {
			return false;
		}
	}

}
