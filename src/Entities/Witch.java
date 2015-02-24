package Entities;

// Can do extra damage
public class Witch extends Enemy {
	private final int EXTRA_DAMAGE = 15;
	
	public Witch() {
		super("Witch");
	}
	
	public int getEXTRA_DAMAGE() {
		return EXTRA_DAMAGE;
	}
	
	@Override
	public int dealDamage() {
		// Set the damage dealt to be at a larger range of damage
		setDamageDealt(getRand().nextInt(getDamageRange() + EXTRA_DAMAGE));
		
		return getDamageDealt();
	}
	
}
