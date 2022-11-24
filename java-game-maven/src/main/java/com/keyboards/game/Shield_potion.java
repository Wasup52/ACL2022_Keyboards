package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.global.Global;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.tile.Tile;

public class Shield_potion extends Item {

	public Shield_potion(int col, int row, Tile[][] mapTiles) {
		super(col,row,mapTiles);
	}

	public Shield_potion (Tile[][] mapTiles,float distance_min,Player p) {
		super(mapTiles,distance_min,p);

	}


	public void use(Player player) {
		player.health=this.augmentation_competence;	
	}

	public void initHitBox() {
		hitBoxCornersOffset = new Point(3,3);
		hitbox = new Rectangle(position.x-this.image.getHeight()/2, position.y-this.image.getWidth()/2,this.image.getHeight() - hitBoxCornersOffset.x,this.image.getWidth()- hitBoxCornersOffset.y);
	}
	public void initSolidBox() {
		solidBoxCornersOffset = new Point(3,3);
		solidBox = new Rectangle(position.x-this.image.getHeight()/2, position.y-this.image.getWidth()/2,this.image.getHeight() - hitBoxCornersOffset.x,this.image.getWidth()- hitBoxCornersOffset.y);
	}

	protected void initSprites() {
		SpriteSheet Sprite = new SpriteSheet("res/Objects/Shield-potion.png",32,32);

		sprite = Sprite.getSpriteArray();
		this.image = sprite[0].image;
	}


	public void draw(Graphics2D g) {
		// TODO

		g.drawImage(this.image, position.x-this.image.getHeight()/2, position.y-this.image.getWidth()/2,this.image.getHeight(),image.getWidth(), null);
		g.setColor(Color.BLUE);
		g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
	}



}




