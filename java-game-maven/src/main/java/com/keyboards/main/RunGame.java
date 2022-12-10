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
		
		// if an inventory is open the player can't do anything else
		if (!inventoryOpen) {
			if (commands.get("UP")) { player.moveUp(); }
			if (commands.get("DOWN")) { player.moveDown(); }
			if (commands.get("LEFT")) { player.moveLeft(); }
			if (commands.get("RIGHT")) { player.moveRight(); }
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
								inventoryOpen = true;
							}
							if (entity instanceof Treasure) {
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
		
		if (!zombie.isDead()) {
			zombie.moveTowards(player);
		}
		if (zombie.canAttack(player)) {
			zombie.attack(player);
			zombie.isAttacking=true;
		}
		if (!ghost.isDead()) {
			ghost.moveTowards(player);
		}
		if (ghost.canAttack(player)) {
			ghost.attack(player);
			ghost.isAttacking=true;
		}
		// ghost.moveTowards(player);
		// if (ghost.collidesWith(player)) {
		// 	ghost.attack(player);
		// }
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
