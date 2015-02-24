package Entities;

// Also heals when the player does
public class Blob extends Enemy {

	public Blob() {
		super("Blob");
	}
	
	public void regenerationAbility(int amountHealed) {
		setHealth(getHealth() + amountHealed);
		System.out.println("\t> The " + getName() + " mimicked your Healing Potion and also healed " + amountHealed + " damage!");
	}
	
}
