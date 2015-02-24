package Entities;

// Has extra health
public class Zombie extends Enemy {
	private final int EXTRA_HEALTH = 15;
	
	public Zombie() {
		super("Zombie");
		// Add the extra health to the health already set
		setHealth(getHealth() + EXTRA_HEALTH);
	}
	
	public int getEXTRA_HEALTH() {
		return EXTRA_HEALTH;
	}

}
