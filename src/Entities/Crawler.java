package Entities;

// Can be harder to run away from
public class Crawler extends Enemy {
	private final int EXTRA_CATCH_CHANCE = 25;

	public Crawler() {
		super("Crawler");
		// Add the extra catch chance to the one already set
		setCatchChance(getCatchChance() + EXTRA_CATCH_CHANCE);
	}

	public int getEXTRA_CATCH_CHANCE() {
		return EXTRA_CATCH_CHANCE;
	}
	
	public void catchUp() {
		System.out.println("\t> It is difficult to run away from the " + getName() + " ... ");
	}

}
