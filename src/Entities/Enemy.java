package Entities;

import java.util.Random;

public class Enemy {
	public static final int ENEMY_TYPES = 8;
	public static int ENEMIES_DEFEATED;
	private Random rand;
	private String name;
	
	private int health;
	private int minHealth;
	private int healthRange;
	
	private int damageDealt;
	private int damageRange;
	
	private int potionDropChance;
	
	private int catchChance; 
	
	public Enemy(String name) {
		rand = new Random();
		this.name = name;
		minHealth = 25;
		healthRange = 45;
		damageDealt = 0;
		damageRange = 30;
		health = rand.nextInt(healthRange) + minHealth;
		potionDropChance = 36;
		catchChance = 50;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealthRange() {
		return healthRange;
	}
	
	public void setHealthRange(int healthRange) {
		this.healthRange = healthRange;
	}
	
	public int getDamageRange() {
		return damageRange;
	}

	public void setDamageRange(int damageRange) {
		this.damageRange = damageRange;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getPotionDropChance() {
		return potionDropChance;
	}

	public void setPotionDropChance(int potionDropChance) {
		this.potionDropChance = potionDropChance;
	}

	public boolean isAlive() {
		return health > 0;
	}
	
	public int dealDamage() {
		// Store the last damage dealt then return it
		damageDealt = rand.nextInt(damageRange);
		
		return damageDealt;
	}
	
	public int getMinHealth() {
		return minHealth;
	}

	public void setMinHealth(int minHealth) {
		this.minHealth = minHealth;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public void setDamageDealt(int damageDealt) {
		this.damageDealt = damageDealt;
	}

	public void takeDamage(int damageTaken) {
		health -= damageTaken;
	}
	
	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public int getCatchChance() {
		return catchChance;
	}

	public void setCatchChance(int catchChance) {
		this.catchChance = catchChance;
	}

	public boolean dropsPotion(int numPotionsLeft) {
		// The higher the number of potions left, the greater the chances of getting one. There is a cap though.	
		int extraChance;
		
		if (numPotionsLeft > 3) extraChance = 30;
		else extraChance = numPotionsLeft * 10;
		
		if (rand.nextInt(99) < potionDropChance + (extraChance)) 
			return true;
		else 
			return false;
	}
	
	// Chances of escaping this particular enemy
	public boolean catchesPlayer() {
		return rand.nextInt(100) < catchChance; 
	}
	
}
