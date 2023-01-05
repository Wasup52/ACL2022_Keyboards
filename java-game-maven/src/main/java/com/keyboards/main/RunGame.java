package com.keyboards.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.keyboards.UI.UI;
import com.keyboards.engine.Game;
import com.keyboards.engine.GameMouseHandler;
import com.keyboards.game.Entity;
import com.keyboards.game.Ghost;
import com.keyboards.game.Item;
import com.keyboards.game.Player;
import com.keyboards.game.Attack_potion;
import com.keyboards.game.Chest;
import com.keyboards.game.Shield_potion;
import com.keyboards.game.Life_potion;
import com.keyboards.game.Mob;
import com.keyboards.game.Speed_potion;
import com.keyboards.game.Treasure;
import com.keyboards.game.Zombie;
import com.keyboards.global.Global;
import com.keyboards.sound.Sound;
import com.keyboards.tile.TileManager;

public class RunGame implements Game {
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<Mob> mobs = new ArrayList<Mob>();
	ArrayList<Chest> chests = new ArrayList<Chest>();
	ArrayList<Item> items = new ArrayList<Item>();

	TileManager tileManager;
	UI ui = new UI();
	
	int keyCooldown = Global.KEY_COOLDOWN;

	boolean isStarted = false;
	boolean isPaused = false;
	boolean isWon = false;
	boolean isFinished = false;
	boolean isDone = false;
	boolean isGameOver = false;
	boolean inventoryOpen = false;

	int nbOfMaps = 2;
	int currentMap = 0;

	Player player;
	Treasure treasure;
	
	/**
	 * constructor with source file for help
	 */
	public RunGame(String source) {
		// ambientSound.loop();
		
		BufferedReader helpReader;
		try {
			helpReader = new BufferedReader(new FileReader(source));
			String ligne;
			while ((ligne = helpReader.readLine()) != null) {
				System.out.println(ligne);
			}
			helpReader.close();
		} catch (IOException e) {
			System.out.println("Help not available");
		}

		init(0, 10, 10, 10, 50, 50, 50, 50, 10, 10, 10, 10);
	}
	
	public void init(int mapIndex, int nbOfZombies, int nbOfGhost, int nbOfChest, int chanceOfHavingLifePotionInChest, int chanceOfHavingAttackPotionInChest, int chanceOfHavingShieldPotionInChest, int chanceOfHavingSpeedPotionInChest, int nbOfLifePotion, int nbOfAttackPotion, int nbOfShieldPotion, int nbOfSpeedPotion) {
		tileManager = new TileManager("res/mapsFile/maps.json", mapIndex);

		player = new Player(tileManager.mapTiles);
		entities.add(player);

		treasure = new Treasure(tileManager.mapTiles, 15, player);
		entities.add(treasure);

		for (int i = 0; i < nbOfZombies; i++) {
			Zombie zombie = new Zombie(tileManager.mapTiles);
			mobs.add(zombie);
			entities.add(zombie);
		}
		for (int i = 0; i < nbOfGhost; i++) {
			Ghost ghost = new Ghost(tileManager.mapTiles);
			mobs.add(ghost);
			entities.add(ghost);
		}
		for (int i = 0; i < nbOfChest; i++) {
			Chest chest = new Chest(tileManager.mapTiles, 15, player);
			if (Math.random() * 100 < chanceOfHavingLifePotionInChest) {
				Life_potion life_potion = new Life_potion(tileManager.mapTiles, 15, player, false);
				chest.put(life_potion);
			}
			if (Math.random() * 100 < chanceOfHavingAttackPotionInChest) {
				Attack_potion attack_potion = new Attack_potion(tileManager.mapTiles, 15, player, false);
				chest.put(attack_potion);
			}
			if (Math.random() * 100 < chanceOfHavingShieldPotionInChest) {
				Shield_potion shield_potion = new Shield_potion(tileManager.mapTiles, 15, player, false);
				chest.put(shield_potion);
			}
			if (Math.random() * 100 < chanceOfHavingSpeedPotionInChest) {
				Speed_potion speed_potion = new Speed_potion(tileManager.mapTiles, 15, player, false);
				chest.put(speed_potion);
			}
			chests.add(chest);
			entities.add(chest);
		}
		for (int i = 0; i < nbOfLifePotion; i++) {
			Life_potion life_potion = new Life_potion(tileManager.mapTiles, 15, player, false);
			items.add(life_potion);
			entities.add(life_potion);
		}
		for (int i = 0; i < nbOfAttackPotion; i++) {
			Attack_potion attack_potion = new Attack_potion(tileManager.mapTiles, 15, player, false);
			items.add(attack_potion);
			entities.add(attack_potion);
		}
		for (int i = 0; i < nbOfShieldPotion; i++) {
			Shield_potion shield_potion = new Shield_potion(tileManager.mapTiles, 15, player, false);
			items.add(shield_potion);
			entities.add(shield_potion);
		}
		for (int i = 0; i < nbOfSpeedPotion; i++) {
			Speed_potion speed_potion = new Speed_potion(tileManager.mapTiles, 15, player, false);
			items.add(speed_potion);
			entities.add(speed_potion);
		}

		tileManager.loopAmbientSound();
	}

	public void clear() {
		entities.clear();
		mobs.clear();
		chests.clear();
		items.clear();

		tileManager.stopAmbientSound();
	}

	/**
	 * constructor with source file for help
	 */
	@Override
	public void evolve(HashMap<String, Boolean> commands, GameMouseHandler mouse) {
		
		keyCooldown--;

		if (isStarted) {
			if (!inventoryOpen) {
				if (!isPaused) {
					if (commands.get("ESCAPE")) {
						if (keyCooldown <= 0) {
							isPaused = true;
							keyCooldown = Global.KEY_COOLDOWN;
						}
					}
				} else {
					if (commands.get("ESCAPE")) {
						if (keyCooldown <= 0) {
							isPaused = false;
							keyCooldown = Global.KEY_COOLDOWN;
						}
					}
				}
			}
			
			// if an inventory is open the player can't do anything else
			if (!inventoryOpen && !player.isDead() && !isPaused) {
				if (commands.get("UP")) {
					player.moveUp();
					tileManager.playStepSound();
				}
				if (commands.get("DOWN")) { 
					player.moveDown(); 
					tileManager.playStepSound();
				}
				if (commands.get("LEFT")) { 
					player.moveLeft();
					tileManager.playStepSound();
				}
				if (commands.get("RIGHT")) { 
					player.moveRight();
					tileManager.playStepSound();
				}
				if (Controller.isIdle(commands)) { player.idle(); }
	
				if (commands.get("SHIFT")) {
					player.isSprinting = true;
				} else {
					player.isSprinting = false;
				}
	
				if (commands.get("ATTACK")) {
					player.playAttackSound();
					
					player.isAttacking = true;
					// System.out.println("Player is attacking");
				}
	
				if (player.isAttacking) {
					// check if a mob is in the attack range
					for (Mob mob : mobs) {
						if (player.canAttack(mob)) {
							System.out.println(mob + " attacked");
							player.attack(mob);
						}
					}
				}
	
				if (commands.get("INTERACT")) {
					if (keyCooldown <= 0) {
						for (Item item : items) {
							if (player.collidesWith(item)) {
								player.pickUp(item);
							}
						}
						for (Chest chest : chests) {
							if (player.collidesWith(chest)) {
								chest.open();
								chest.playOpenChestSound();
								inventoryOpen = true;
							}
						}
						if (treasure != null) {
							if (player.collidesWith(treasure)) {
								treasure.playTreasureSound();
								isWon = true;
							}
						}
						keyCooldown = Global.KEY_COOLDOWN;
					}
				}
			} else {
				player.idle(); // idle if the inventory is open
			}
	
			if (commands.get("TAB") || (inventoryOpen && commands.get("ESCAPE"))) {
				if (keyCooldown <= 0) {
					if (inventoryOpen) {
						if (player.isInventoryOpen()) {
							player.closeInventory();
						}
						// if one of the chest in the chests array is open, close it
						for (Chest chest : chests) {
							if (chest.isOpen()) {
								chest.playCloseChestSound();
								chest.close();
							}
						}
					} else {
						player.openInventory();
					}
					inventoryOpen = !inventoryOpen;
					keyCooldown = Global.KEY_COOLDOWN;
				}
			}
	
			if (mouse.getButton() != -1) {
				// System.out.println("Mouse button " + mouse.getButton() + " pressed at " + mouse.getX() + ", " + mouse.getY());
				if (inventoryOpen) {
					if (player.isInventoryOpen()) {
						player.getInventory().useClickedItem(mouse.getX(), mouse.getY());
					}
					for (Chest chest : chests) {
						if (chest.isOpen()) {
							chest.transfertClickedItem(mouse.getX(), mouse.getY(), player);
						}
					}
				}
			}
	
			for (int i=0; i < mobs.size(); i++) {
				Mob mob = mobs.get(i);
				if (!isPaused) {
					mob.update(player);
					if (mob.isFaded()) {
						entities.remove(mob);
						mobs.remove(mob);
						i--;
					}
				} else {
					mob.idle();
				}
			}
		} else {
			if (commands.get("ENTER")) {
				if (keyCooldown <= 0) {
					isStarted = true;
					keyCooldown = Global.KEY_COOLDOWN;
				}
			}
		}
	}

	public boolean isGameOver() { return isGameOver; }
	public boolean isGameFinished() { return isFinished; }

	/**
	 * check if the game is over
	 */
	@Override
	public boolean isFinished() {
		if (isWon && !isDone) {
			System.out.println("Level " + currentMap + " won");
			
			currentMap++;
			System.out.println("Loading level " + currentMap);
			if (currentMap < 2) {
				clear();
				init(currentMap, 10, 10, 10, 50, 50, 50, 50, 10, 10, 10, 10);
				isWon = false;
			} else {
				// System.out.println("You won the game");
				isFinished = true;
			}
		}

		if (player.isDead()) {
			// System.out.println("Game over");
			isGameOver = true;
		}

		if (isDone) {
			tileManager.stopSounds();
		}

		return isDone;
	}
}