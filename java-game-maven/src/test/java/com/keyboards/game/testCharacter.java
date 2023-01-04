package com.keyboards.game;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

import com.keyboards.global.Global;
import com.keyboards.tile.Tile;
import com.keyboards.tile.TileManager;



public class testCharacter {

	@Test
	public void testMove() {
		BufferedImage image= null;
		TileManager tm= new TileManager("res/mapsFile/maps.json", 0);
		//Tile t = new Tile(5,10,image);
		Global.PLAYER_HAS_COLLISION = false; // on enleve les collisions car on ne teste pas canMove()
		Player p = new Player(5,10,tm.mapTiles);
		
		//move the character 
		p.move(1,2);
		
	
		assertEquals(p.worldPosition.x, 1); 
		assertEquals(p.worldPosition.y, 2); 
		
		assertEquals(p.hitbox.x,1 + p.hitBoxCornersOffset.x);
		assertEquals(p.hitbox.y,2 + p.hitBoxCornersOffset.y);
		
		assertEquals(p.solidBox.x, 1 + p.solidBoxCornersOffset.x);
		assertEquals(p.solidBox.y, 2 + p.solidBoxCornersOffset.y);
		
		assertEquals(p.attackLeftHitbox.x, 1 + p.attackLeftHitBoxCornersOffset.x);
		assertEquals(p.attackLeftHitbox.y, 2 + p.attackLeftHitBoxCornersOffset.y);
		
		assertEquals(p.attackRightHitbox.x, 1 + p.attackRightHitBoxCornersOffset.x);
		assertEquals(p.attackRightHitbox.y, 2 + p.attackRightHitBoxCornersOffset.y);
		
		
		
	}
	
	@Test
	public void testcanMove() {
		BufferedImage image= null;
		TileManager tm= new TileManager("res/mapsFile/maps.json", 0);
		
		Global.PLAYER_HAS_COLLISION = true;
		Player p = new Player(5,10,tm.mapTiles);
		
		
		//test des bords de lecran
		
		assertFalse(p.canMove(-1, 10)); // gauche
		assertFalse(p.canMove(10, -1)); // haut 
		assertFalse(p.canMove(Global.SCREEN_WIDTH+1, 10)); // droite
		assertFalse(p.canMove(10, Global.SCREEN_HEIGHT+1)); // bas
		
	
	}

	
	
}
