import java.util.Random;
import java.util.Scanner;

import Entities.Blob;
import Entities.Crawler;
import Entities.Demon;
import Entities.Enemy;
import Entities.Player;
import Entities.Skeleton;
import Entities.Vampire;
import Entities.Werewolf;

public class Main {
	private static Scanner scanIn;
	private static Random rand;
	private static boolean running;
	private static Player player;
	private static Enemy currentEnemy;
	
	public static void main(String[] args) {
		setup(); // Initialize variables
		start(); // Main game loop
	}
	
	public static void setup() {
		// System variables
		scanIn = new Scanner(System.in);
		rand = new Random();
		running = true;

		// Player variables
		player = new Player();
		
		// Enemy variables 
		currentEnemy = null;
		
		System.out.println("--------------------------------------------------");
		System.out.println("Dungeon Simulator by Ronald Tejada");
		System.out.println("Objective: Defeat As Many Monsters As You Can!");
	}
	
	public static void start() {
		GAME:
		while (running) {
			// Selects a random enemy our player will fight against
			enemySelection();
			
			// Breaks only if the enemy is no longer alive,
			// The player successfully ran away, or
			// The player was defeated
			while (currentEnemy.isAlive()) {
				
				// Displays branching options the user may select
				String mainInput = displayMainOptions();
				
				if (mainInput.equals("1")) combatOption();
				else if (mainInput.equals("2")) potionOption();
				else if (mainInput.equals("3"))
					// If the player ran away, select a new enemy
					if (runOption())
						continue GAME;
				else System.out.println("\tInvalid command!\n");
				
				// The last item to check is if the player is defeated, to exit the combat loop
				if (player.isDefeated()) {
					break;
				}
			}
			
			// Player is defeated
			if (combatAftermath()) {
				System.out.println("You limp out of the dungeon, barely escaping.");
				gameOver();
			}
			
			// Player is not defeated, can choose to continue or exit the dungeon
			if (displayAftermathOptions())  {
				continue GAME; // Will skip gameOver method and keep playing with the current stats
			} else gameOver();
		}
	
	}
	
	public static void enemySelection() {
		System.out.println("--------------------------------------------------");
		
		// Choosing which type of enemy to initialize
		switch (rand.nextInt(Enemy.ENEMY_TYPES)) {
			case 0: currentEnemy = new Crawler(); // Zombie
					break;
			case 1: currentEnemy = new Crawler(); // Witch
					break;
			case 2: currentEnemy = new Crawler();
					break;
			case 3: currentEnemy = new Vampire();
					break;
			case 4: currentEnemy = new Werewolf();
					break;
			case 5: currentEnemy = new Blob();
					break;
			case 6: currentEnemy = new Skeleton();
					break;
			case 7: currentEnemy = new Demon();
					break;
		}
		
		System.out.println("\t# A " + currentEnemy.getName() + " Has Appeared! #\n");
	}
	
	public static String displayMainOptions() {
		System.out.println("\tYour Stamina: " + player.getHealth());
		System.out.println("\t" + currentEnemy.getName() + "'s Stamina: " + currentEnemy.getHealth());
		System.out.println("\n\tWhat would you like to do?");
		System.out.println("\t1. Attack");
		System.out.println("\t2. Drink A Health Potion");
		System.out.println("\t3. Run Away");
		
		return scanIn.nextLine();
	}
	
	public static void combatOption() {
		
		// Demons ability
		if (currentEnemy instanceof Demon) {
			if (((Demon) currentEnemy).nimbleAbility()) {
				enemyDamage();
			} else {
				playerDamage();
				enemyDamage();
			}
		} else {
			playerDamage();
			enemyDamage();			
		}		
	}	
	
	public static void potionOption() {
		if (player.isFull()) {
			System.out.println("\t> You are already at max Stamina, save your Health Potions!");
		} else if (player.hasPotions()) {
			
			System.out.println("\t> You drank a Health Potion and healed " + player.usePotion() + " damage." + 
					"\n\t> New Stamina: " + player.getHealth() +
					"\n\t> Health Potions left: " + player.getNumPotions());
			
			// Blob ability
			if (currentEnemy instanceof Blob && player.getAmountHealed() > 0 && currentEnemy.isAlive()) {
				((Blob) currentEnemy).regenerationAbility(player.getAmountHealed());
			}
			
		} else {
			System.out.println("\t> No Health Potions left! Defeat enemies for a chance to get one!");
		}
		
		// Werewolf ability
		if (currentEnemy instanceof Werewolf) {
			((Werewolf) currentEnemy).swipeAbility();
			enemyDamage();
		} else {
			System.out.println();
		}
		
	}

	public static boolean runOption() {
		if (currentEnemy.catchesPlayer()) {
			System.out.println("\t> The " + currentEnemy.getName() + " caught up to you!");
			
			// Crawlers ability
			if (currentEnemy instanceof Crawler) {
				((Crawler) currentEnemy).catchUp();
			}
			enemyDamage();
			
			return false; // did not run away
		} else {
			System.out.println("\t> You run away from the " + currentEnemy.getName() + "!");
			return true; // ran away
		}
		
	}

	public static void playerDamage() {
		currentEnemy.takeDamage(player.dealDamage());
		
		if (player.getDamageDealt() > 0) {
			System.out.println("\t> You hit the " + currentEnemy.getName() + " for " + player.getDamageDealt() + " damage.");
		} else {
			System.out.println("\t> The " + currentEnemy.getName() + " evaded your attack!");
		}
	}
	
	public static void enemyDamage() {
		player.takeDamage(currentEnemy.dealDamage());
		
		if (currentEnemy.getDamageDealt() > 0)
			System.out.println("\t> You took " + currentEnemy.getDamageDealt() + " damage.");
		else
			System.out.println("\t> You evaded the " + currentEnemy.getName() + "'s attack!");
		
		// Vampire ability
		if (currentEnemy instanceof Vampire) {
			((Vampire) currentEnemy).syphonAbility();
		}
		
		System.out.println();
		
	}	
	
	public static boolean combatAftermath() {

		System.out.println("--------------------------------------------------");

		// Check is the current enemy is defeated before incrementing the score
		if (!currentEnemy.isAlive()) {
			Enemy.ENEMIES_DEFEATED++;
			System.out.println("\t The " + currentEnemy.getName() + " was defeated!");
		}		
		
		// Check if the player is defeated, if so, simply return as there is no need
		// to check for potions or display stats
		if (player.isDefeated()) return true;
		
		// Check if the enemy dropped a potion
		// Skeletons ability
		if (currentEnemy instanceof Skeleton) {
			((Skeleton) currentEnemy).emptyPockets();
		} else if (currentEnemy.dropsPotion(player.getNumPotions())) {
			System.out.println("The " + currentEnemy.getName() + " dropped a Health Potion!");
			
			// Check if we are allowed more potions
			if (player.isMaxedOnPotions()) {
				System.out.println("You cannot carry anymore potions.");
				usePotionAndObtainOne();
				
			} else {
				player.increasePotionCount();
				
			}
		} else { 
			System.out.println("Conserve your potions for a higher chance in finding another one.");
		}
		
		System.out.println("Stamina: " + player.getHealth());
		System.out.println("Health Potions: " + player.getNumPotions());		
		System.out.println("# Current Enemies Defeated: " + Enemy.ENEMIES_DEFEATED + " #");
		
		// If we get to here the player is alive so we return false
		return false;
	}
	
	public static boolean displayAftermathOptions() {
		System.out.println("--------------------------------------------------");
		System.out.println("What would you like to do now?");
		System.out.println("1. Continue fighting");
		System.out.println("2. Use a Health Potion");
		System.out.println("3. Exit the dungeon");
		String aftermathInput = scanIn.nextLine();
		
		// Make sure it is a correct option
		while (!aftermathInput.equals("1") && !aftermathInput.equals("2") && !aftermathInput.equals("3")) {
			System.out.println("Invalid command!");
			System.out.println("--------------------------------------------------");
			System.out.println("What would you like to do now?");
			System.out.println("1. Continue fighting");
			System.out.println("2. Use a Health Potion");
			System.out.println("3. Exit the dungeon");
			aftermathInput = scanIn.nextLine();
		}
		
		while(aftermathInput.equals("2")) {
			potionOption();
			System.out.println("What would you like to do now?");
			System.out.println("1. Continue fighting");
			System.out.println("2. Use a Health Potion");
			System.out.println("3. Exit the dungeon");
			aftermathInput = scanIn.nextLine();
		}
		
		if (aftermathInput.equals("1")) {
			System.out.println("--------------------------------------------------");
			System.out.println("You choose to continue!");
			return true; // exit
		} else if (aftermathInput.equals("3")) {
			System.out.println("--------------------------------------------------");
			System.out.println("You exit the dungeon.");
			return false; // dont exit
		}
		
		// Will never get to this point, the while loop will not move unless a correct command is given
		return true;
	}
	
	public static void gameOver() {
		System.out.println("# Total Monsters Defeated: " + Enemy.ENEMIES_DEFEATED + " #");
		System.out.println("--------------------------------------------------");
		System.out.println("What would you like to do now?");
		System.out.println("1. Rest, then go back into the dungeon");
		System.out.println("2. Go Home");
		String restartInput = scanIn.nextLine();
		
		// Make sure it is a correct option
		while (!restartInput.equals("1") && !restartInput.equals("2")) {
			System.out.println("Invalid command!");
			System.out.println("--------------------------------------------------");
			System.out.println("What would you like to do now?");
			System.out.println("1. Rest, then go back into the dungeon");
			System.out.println("2. Go home");
			restartInput = scanIn.nextLine();
		}
		
		if (restartInput.equals("1")) {
			System.out.println("--------------------------------------------------");
			System.out.println("You chose to rest, then go back into the dungeon!");
			// Reset the player object since this is a renewed character
			// Resets the enemies defeated as well
			player = new Player();
			Enemy.ENEMIES_DEFEATED = 0;
			
		} else if (restartInput.equals("2")) {
			System.out.println("--------------------------------------------------");
			System.out.println("You chose to go home");
			System.out.println("--------------------------------------------------");
			System.out.println("# THANKS FOR PLAYING! #");
			// Game loop finishes here
			running = false;
		}
		
	}

	// If you are at max potions, and came across another one, your character will automatically
	// drink it and keep your potions at max
	public static void usePotionAndObtainOne() {
		if (!player.isFull() && player.hasPotions()) {
			System.out.println("\t> You drank the dropped Health Potion and healed " + player.usePotion() + " damage.");
			player.increasePotionCount();	
		} else {
			System.out.println("You are at maximum Stamina.");
		}
	}
	
}