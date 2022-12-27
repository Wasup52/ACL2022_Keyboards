package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.keyboards.global.Global;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.sound.Sound;
import com.keyboards.tile.Tile;

public class Treasure extends Object {
	
	Sound treasureSound;
	int coef_agrandissement_image=2;
    public Treasure(int col, int row, Tile[][] mapTiles) {
    	super(col, row, mapTiles);
    }

    public Treasure(Tile[][] mapTiles,float distance_min,Player p) {
       	super(mapTiles,distance_min,p);
    }

    public void initHitBox() {
        hitBoxCornersOffset = new Point(0,0 );
        hitbox = new Rectangle(worldPosition.x-(this.image.getHeight()/2)*2, worldPosition.y-2*(this.image.getWidth()/2),this.image.getHeight()*2 - hitBoxCornersOffset.x*2,this.image.getWidth()*2- hitBoxCornersOffset.y*2);
    }

    public void initSolidBox() {
        solidBoxCornersOffset = new Point(0,0);
        solidBox = new Rectangle(worldPosition.x-(this.image.getHeight()/2)*2, worldPosition.y-(this.image.getWidth()/2)*2,this.image.getHeight()*2 - hitBoxCornersOffset.x*2,this.image.getWidth()*2- hitBoxCornersOffset.y*2);
    }

    
    protected void initSprites() {
        SpriteSheet Sprite = new SpriteSheet("res/Objects/Treasure.png",16,16);
       
        super.sprite = Sprite.getSpriteArray();
        this.image = sprite[0].image;
        
        treasureSound = new Sound("res/sound/treasureSound.wav");
    }
	public void playTreasureSound() {
		treasureSound.play();
	}
    
    public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
        int screenX = worldPosition.x - playerWorldPos.x + playerScreenPos.x;
        int screenY = worldPosition.y - playerWorldPos.y + playerScreenPos.y;

        g.drawImage(this.image, screenX-(this.image.getHeight()/2)*2, screenY-(this.image.getWidth()/2)*2,this.image.getHeight()*2,image.getWidth()*2, null);
        
        if (Global.DEBUG) {
			g.setColor(Color.BLUE);
			g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
			g.setColor(Color.RED);
			g.drawRect(solidBox.x, solidBox.y, solidBox.width, solidBox.height);
		}
    }
}






