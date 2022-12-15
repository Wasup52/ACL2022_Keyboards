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

	TileManager tileManager = new TileManager("res/mapsFile/test1.txt");
	UI ui = new UI();
	
	int keyCooldown = Global.KEY_COOLDOWN;

	boolean isPaused = false;
	boolean isFinished = false;
	boolean inventoryOpen = false;
	
	Player player = new Player(tileManager.mapTiles);
	Ghost ghost = new Ghost(tileManager.mapTiles);
	Zombie zombie = new Zombie(tileManager.mapTiles);
	Treasure treasure = new Treasure(tileManager.mapTiles, 15, player);
	Chest chest = new Chest(tileManager.mapTiles, 15, player);
	Life_potion life_potion = new Life_potion(tileManager.mapTiles, 15, player, false);
	Attack_potion attack_potion = new Attack_potion(tileManager.mapTiles, 15, player, false);
	Shield_potion shield_potion = new Shield_potion(tileManager.mapTiles, 15, player, false);
	Speed_potion speed_potion = new Speed_potion(tileManager.mapTiles, 15, player, false);

	Sound ambientSound = new Sound("res/sound/AMBForest.wav");
	
	/**
	 * constructor with source file for help
	 */
	public RunGame(String source) {
		ambientSound.loop();
		
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

		mobs.add(zombie);
		mobs.add(ghost);
		
		for (Entity mob : mobs) {
			entities.add(mob);
		}
		
		chest.put(life_potion);

		entities.add(player);
		entities.add(treasure);
		entities.add(chest);
		entities.add(attack_potion);
		entities.add(speed_potion);
		entities.add(shield_potion);
	}
	
	/**
	 * constructor with source file for help
	 */
	@Override
	public void evolve(HashMap<String, Boolean> commands, GameMouseHandler mouse) {
		
		keyCooldown--;

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
				player.playFootStepGrassSound();
			}
			if (commands.get("DOWN")) { 
				player.moveDown(); 
				player.playFootStepGrassSound();
			}
			if (commands.get("LEFT")) { 
				player.moveLeft();
				player.playFootStepGrassSound();
			}
			if (commands.get("RIGHT")) { 
				player.moveRight();
				player.playFootStepGrassSound();
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
					for (Entity entity : entities) {
						if (player.collidesWith(entity)) {
							if (entity instanceof Item) {
								player.pickUp((Item) entity);
							}
							if (entity instanceof Chest) {
								((Chest) entity).open();
								chest.playOpenChestSound();
								inventoryOpen = true;
							}
							if (entity instanceof Treasure) {
								treasure.playTreasureSound();
								isFinished = true;
							}
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
					if (chest.isOpen()) {
						chest.playCloseChestSound();
						chest.close();
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
				if (chest.isOpen()) {
					chest.transfertClickedItem(mouse.getX(), mouse.getY(), player);
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
	}

	/**
	 * check if the game is over
	 */
	@Override
	public boolean isFinished() {
		if (isFinished) {
			System.out.println("Game finished");
		}
		return isFinished;
	}
}