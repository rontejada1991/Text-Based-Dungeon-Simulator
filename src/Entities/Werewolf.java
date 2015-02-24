package Entities;

// Can damage the player when they heal
public class Werewolf extends Enemy {

	public Werewolf() {
		super("Werewolf");
	}

	public void swipeAbility() {
		System.out.println("\t> The " + getName() + " attacked you while you were trying to heal!");
	}
	
}
