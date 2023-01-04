package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.keyboards.global.Global;
import com.keyboards.tile.Tile;

public abstract class Item extends Object{
	Player pl;
	int utilise=0;
	int temps=100;
	int skillIncrease = 0;
	public boolean isInInventory;

	public Item (int col, int row, Tile[][] mapTiles, boolean isInInventory) {
		super(col, row, mapTiles);
		setIsInInventory(isInInventory);
	}

	public Item(Tile[][] mapTiles, float minDistance, Player player, boolean isInInventory) {
		super(mapTiles, minDistance, player);
		setIsInInventory(isInInventory);
	}

	public void setIsInInventory(boolean isInInventory) {
        this.isInInventory = isInInventory;

        if (isInInventory) {
            this.worldPosition.x = -1;
            this.worldPosition.y = -1;
        }
    }
	
	public abstract void use(Player p);

    public void draw(Graphics2D g, int x, int y) {
		if (image != null) {
			g.drawImage(this.image, x, y,this.image.getHeight(),image.getWidth(), null);
		}
    }
	
    public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
		screenPosition.x = worldPosition.x - playerWorldPos.x + playerScreenPos.x;
		screenPosition.y = worldPosition.y - playerWorldPos.y + playerScreenPos.y;

		if (image != null) {
			g.drawImage(this.image, screenPosition.x, screenPosition.y, this.image.getHeight(), image.getWidth(), null);
		}

		if (Global.DEBUG) {
			g.setColor(Color.BLUE);
			g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
			g.setColor(Color.RED);
			g.drawRect(solidBox.x, solidBox.y, solidBox.width, solidBox.height);
		}
    }
}
