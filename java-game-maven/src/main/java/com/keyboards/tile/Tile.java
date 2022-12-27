package com.keyboards.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.global.Global;

public class Tile {
	
	public BufferedImage image;
	public Rectangle solidBox;
	public int row;
	public int x;
	public int col;
	public int y;

	
	public Tile(int row, int col, BufferedImage image) {
		this.row = row;
		this.x = row * Global.TILE_SIZE;
		this.col = col;
		this.y = col * Global.TILE_SIZE;
		this.image = image;
		this.solidBox = null;
	}
	
	public Tile(int row, int col, BufferedImage image, Rectangle solidBox) {
		this.row = row;
		this.col = col;
		this.image = image;
		this.solidBox = solidBox;
	}
	
	public boolean isSolid() {
		return this.solidBox != null;
	}

	public boolean collidsWith(Rectangle solidBox) {
		return this.solidBox.intersects(solidBox);
	}
	
	public boolean needToBeDrawn(int col, int row, Point playerWorldPos, Point playerScreenPos) {
		int worldX = col * Global.TILE_SIZE;
		int worldY = row * Global.TILE_SIZE;

		return (worldX + Global.TILE_SIZE*2 > playerWorldPos.x - playerScreenPos.x
				&& worldX - Global.TILE_SIZE*2 < playerWorldPos.x + playerScreenPos.x
				&& worldY + Global.TILE_SIZE*2 > playerWorldPos.y - playerScreenPos.y
				&& worldY - Global.TILE_SIZE*2 < playerWorldPos.y + playerScreenPos.y);
	}

	public static void drawBlankTile(Graphics2D g, int col, int row, Point playerWorldPos, Point playerScreenPos) {
		int screenX = (col * Global.TILE_SIZE) - playerWorldPos.x + playerScreenPos.x;
		int screenY = (row * Global.TILE_SIZE) - playerWorldPos.y + playerScreenPos.y;
		
		g.setColor(Color.BLACK);
		g.fillRect(screenX, screenY, Global.TILE_SIZE, Global.TILE_SIZE);
	}

	public void draw(Graphics2D g, int col, int row, Point playerWorldPos, Point playerScreenPos) {
		int screenX = (col * Global.TILE_SIZE) - playerWorldPos.x + playerScreenPos.x;
		int screenY = (row * Global.TILE_SIZE) - playerWorldPos.y + playerScreenPos.y;
		
		g.drawImage(this.image, screenX, screenY, Global.TILE_SIZE, Global.TILE_SIZE, null);
		
		if (Global.DEBUG) {
			// draw the solid box
			g.setColor(Color.MAGENTA);
			if (this.solidBox != null) {
				int screenSolidBoxX = this.solidBox.x + screenX;
				int screenSolidBoxY = this.solidBox.y + screenY;

				g.drawRect(screenX, screenY, this.solidBox.width, this.solidBox.height);
			}
		}
	}
	
}