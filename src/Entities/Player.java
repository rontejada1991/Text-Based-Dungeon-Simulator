package Entities;

import java.util.Random;

public class Player {
	private Random rand;
	
	private int health;
	private int maxHealth;
	
	private int damageDealt;
	private int damageRange;
	
	private int numPotions;
	private int minPotionHealAmount;
	private int maxPotions;
	private int amountHealed;
	private int potionHealAmountRange;
	

	public Player() {
		rand = new Random();
		health = 100;
		maxHealth = 100;
		damageDealt = 0;
		damageRange = 50;
		maxPotions = 5;
		numPotions = 3;
		minPotionHealAmount = 20;
		potionHealAmountRange = 20;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getDamageDealt() {
		return damageDealt;
	}

	public void setDamageDealt(int damageDealt) {
		this.damageDealt = damageDealt;
	}

	public int getDamageRange() {
		return damageRange;
	}

	public void setDamageRange(int damageRange) {
		this.damageRange = damageRange;
	}

	public int getNumPotions() {
		return numPotions;
	}

	public void setNumPotions(int numPotions) {
		this.numPotions = numPotions;
	}
	
	public int getMinPotionHealAmount() {
		return minPotionHealAmount;
	}

	public void setMinPotionHealAmount(int minPotionHealAmount) {
		this.minPotionHealAmount = minPotionHealAmount;
	}

	public int getPotionHealAmountRange() {
		return potionHealAmountRange;
	}

	public void setPotionHealAmountRange(int potionHealAmountRange) {
		this.potionHealAmountRange = potionHealAmountRange;
	}

	public boolean isDefeated() {
		return health < 1;
	}
	
	public boolean hasPotions() {
		return numPotions > 0;
	}
	
	public int usePotion() {
		numPotions--;

		amountHealed = rand.nextInt(potionHealAmountRange) + minPotionHealAmount;
		
		// Player's health should not go above the maximum
		if (health + amountHealed > maxHealth) {
			int amountHealed = maxHealth - health;
			health = maxHealth;
			// Return the amount healed
			return amountHealed;
		}
		else {
			health += amountHealed;
			return amountHealed;
		}
	}
	
	public void increasePotionCount() {
		numPotions++;
	}
	
	public int dealDamage() {
		// Store the last damage dealt and return it
		damageDealt = rand.nextInt(damageRange);
		
		return damageDealt;
	}
	
	public int getAmountHealed() {
		return amountHealed;
	}

	public void setAmountHealed(int amountHealed) {
		this.amountHealed = amountHealed;
	}

	public Random getRand() {
		return rand;
	}

	public void setRand(Random rand) {
		this.rand = rand;
	}

	public void takeDamage(int damageTaken) {
		health -= damageTaken;
	}
	
	public int getMaxPotions() {
		return maxPotions;
	}

	public void setMaxPotions(int maxPotions) {
		this.maxPotions = maxPotions;
	}

	public boolean isFull() {
		return health == maxHealth;
	}
	
	public boolean isMaxedOnPotions() {
		return numPotions == maxPotions;
	}
	
}