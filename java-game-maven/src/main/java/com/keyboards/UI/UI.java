package com.keyboards.UI;

import com.keyboards.global.Global;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.game.Object;
import com.keyboards.game.Player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.keyboards.game.Entity;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import com.keyboards.tile.Tile;

public class UI {

	public BufferedImage getHeartFull() {
		return heartFull;
	}

	public BufferedImage getHeartHalf() {
		return heartHalf;
	}

	public BufferedImage getHeartEmpty() {
		return heartEmpty;
	}

	public int getWIDTH_OFFSET() {
		return WIDTH_OFFSET;
	}

	public int getHEIGHT_OFFSET() {
		return HEIGHT_OFFSET;
	}

	public int getWIDTH_SPACING() {
		return WIDTH_SPACING;
	}

	public int getHEIGHT_SPACING() {
		return HEIGHT_SPACING;
	}

	public int getSCALE_FACTOR() {
		return SCALE_FACTOR;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	private BufferedImage heartFull;
	private BufferedImage heartHalf;
	private BufferedImage heartEmpty;
	
	private final int WIDTH_OFFSET = 10;
	private final int HEIGHT_OFFSET = 10;
	private final int WIDTH_SPACING = -20;
	private final int HEIGHT_SPACING = -20;
	
	private final int SCALE_FACTOR = 2;
	private int spriteHeight;
	private int spriteWidth;

	public UI() {
		SpriteSheet heartSpriteSheet = new SpriteSheet("res/player_life/life_heart.png", 32, 32);

		Sprite[] heartSprites = heartSpriteSheet.getSpriteArray();

		heartFull = heartSprites[0].image;
		heartHalf = heartSprites[1].image;
		heartEmpty = heartSprites[2].image;
		
		spriteHeight = heartFull.getHeight()*SCALE_FACTOR;
		spriteWidth = heartFull.getWidth()*SCALE_FACTOR;
	}

	private void drawFullHeart(Graphics2D g, int x, int y) {
		g.drawImage(heartFull, x, y, spriteHeight, spriteWidth, null);
	}

	private void drawHalfHeart(Graphics2D g, int x, int y) {
		g.drawImage(heartHalf, x, y, spriteHeight, spriteWidth, null);
	}

	private void drawEmptyHeart(Graphics2D g, int x, int y) {
		g.drawImage(heartEmpty, x, y, spriteHeight, spriteWidth, null);
	}

	public void drawPlayerLife(Graphics2D g, Player player) {
		int x = WIDTH_OFFSET;
		int y = HEIGHT_OFFSET;
				
		for (int i = 1; i <= (player.maxHealth + player.bouclier)/2 ; i++) {
			if (player.health >= 2*i) {
				drawFullHeart(g, x, y);
			} else if (player.health >= 2*i-1) {
				drawHalfHeart(g, x, y);
			} else {
				drawEmptyHeart(g, x, y);
			}
			
			if (x + spriteWidth*2 + WIDTH_SPACING < Global.SCREEN_WIDTH) {
				x += spriteWidth + WIDTH_SPACING;
			} else {
				x = WIDTH_OFFSET;
				y += spriteHeight + HEIGHT_SPACING;
			}
		}

		int maxHealth = player.maxHealth + player.bouclier;
		System.out.println("Player life: " + player.health + "/" + maxHealth);
	}
}
