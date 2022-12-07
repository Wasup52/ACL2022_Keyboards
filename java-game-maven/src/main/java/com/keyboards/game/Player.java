package com.keyboards.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.graphics.Animation;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.sound.Sound;
import com.keyboards.tile.Tile;


public class Player extends Character {
    private Inventory inventory = new Inventory(this, 2, 6);

    public final int NUMBER_OF_FRAME_IN_WALK_ANIM = 6;
    public final int NUMBER_OF_FRAME_IN_ATTACK_ANIM = 4;

    public boolean isAttacking = false;

    private Animation idleLeft;
    private Animation idleRight;
    private Animation walkLeft;
    private Animation walkRight;
    private Animation attackLeft;
    private Animation attackRight;
    
    private final int SCALE_FACTOR = 2;

    public final int maxHealth = 12;

    public Player(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
    }

    public Player(Tile[][] mapTiles) {
        super(mapTiles);
    }

    protected void initStats() {
        hasInventory = true;
        health = maxHealth;
        attackDamage = 3;
        speed = 1;
        sprintSpeed = 2;
        attackCooldownMax = 25;
    }

    public void initHitBox() {
        hitBoxCornersOffset = new Point(16*SCALE_FACTOR, 13*SCALE_FACTOR);
        hitbox = new Rectangle(position.x + hitBoxCornersOffset.x, position.y + hitBoxCornersOffset.y, 15*SCALE_FACTOR, 20*SCALE_FACTOR);

        attackLeftHitBoxCornersOffset = new Point(4*SCALE_FACTOR,15*SCALE_FACTOR);
        attackLeftHitbox = new Rectangle(position.x + attackLeftHitBoxCornersOffset.x, position.y + attackLeftHitBoxCornersOffset.y, 17*SCALE_FACTOR, 21*SCALE_FACTOR);
        
        attackRightHitBoxCornersOffset = new Point(27*SCALE_FACTOR,15*SCALE_FACTOR);
        attackRightHitbox = new Rectangle(position.x + attackRightHitBoxCornersOffset.x, position.y + attackRightHitBoxCornersOffset.y, 17*SCALE_FACTOR, 21*SCALE_FACTOR);
    }

    public void initSolidBox() {
        solidBoxCornersOffset = new Point(16*SCALE_FACTOR, 25*SCALE_FACTOR);
        solidBox = new Rectangle(position.x + solidBoxCornersOffset.x, position.y + solidBoxCornersOffset.y, 15*SCALE_FACTOR, 6*SCALE_FACTOR);
    }

    protected void initSprites() {
        SpriteSheet idleLeftSheet = new SpriteSheet("res/player/player-idle-left-strip.png", 48, 48);
        SpriteSheet idleRightSheet = new SpriteSheet("res/player/player-idle-right-strip.png", 48, 48);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/player/player-walk-left-strip.png", 48, 48);
        SpriteSheet walkRightSheet = new SpriteSheet("res/player/player-walk-right-strip.png", 48, 48);
        SpriteSheet attackRightSheet = new SpriteSheet("res/player/player-attack-right-strip.png", 48, 48);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/player/player-attack-left-strip.png", 48, 48);
        
        idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 5);
        idleRight = new Animation(idleRightSheet.getSpriteArray(), 5);
        walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 5);
        walkRight = new Animation(walkRightSheet.getSpriteArray(), 5);
        attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 5);
        attackRight = new Animation(attackRightSheet.getSpriteArray(), 5);
        
        attackSound = new Sound("res/sound/Attackwoosh.wav");
    }

    public void pickUp(Item item) {
        if (!item.isInInventory) {
            inventory.addItem(item);
        } else {
            System.out.println("inventory is full, cannot pick up item " + item.getClass().getName());
        }
    }

    protected void die() {
        // System.out.println("player died");
    }

    public Inventory getInventory() { return inventory; }
    public boolean isInventoryOpen() { return inventory.isOpen(); }
    public void openInventory() { inventory.open(); }
    public void closeInventory() { inventory.close(); }

    public void drawInventory(Graphics2D g) { inventory.draw(g); }

    public void draw(Graphics2D g) {

        idleLeft.update();
        idleRight.update();
        walkLeft.update();
        walkRight.update();
        
        BufferedImage image = null;
        
        if (direction == IDLE + RIGHT) {
            image = idleRight.getSprite().image;
        } else if (direction == IDLE + LEFT) {
            image = idleLeft.getSprite().image;
        } else if (direction == RIGHT) {
            image = walkRight.getSprite().image;
        } else if (direction == LEFT) {
            image = walkLeft.getSprite().image;
        }

        if ((direction == LEFT || lastDirection == LEFT) && isAttacking) {
        	image = attackLeft.getSprite().image;
        } else if ((direction == RIGHT || lastDirection == RIGHT) && isAttacking) {
        	image = attackRight.getSprite().image;
        }

        if (isAttacking) {
    		if (attackLeft.reachedEndFrame() || attackRight.reachedEndFrame()) {
    			isAttacking = false;
    		}

            attackLeft.update();
            attackRight.update();
    		
        	g.drawImage(image, position.x, position.y, image.getHeight()*SCALE_FACTOR, image.getWidth()*SCALE_FACTOR, null);        	
        	
        } else {

        	g.drawImage(image, position.x, position.y, image.getHeight()*SCALE_FACTOR, image.getWidth()*SCALE_FACTOR, null);        	
        }

        super.draw(g);
    }
}