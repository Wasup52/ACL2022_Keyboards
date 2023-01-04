package com.keyboards.game;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;

import com.keyboards.global.Global;
import com.keyboards.graphics.Animation;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.sound.Sound;
import com.keyboards.tile.Tile;


public class Player extends Character {
    private Inventory inventory = new Inventory(this, 2, 6);

    public final int maxHealth = 12;
    public int bouclier=0;
    public Player(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles, Global.PLAYER_HAS_COLLISION);

        screenPosition.x = Global.SCREEN_WIDTH / 2 - Global.TILE_SIZE*SCALE_FACTOR / 2;
        screenPosition.y = Global.SCREEN_HEIGHT / 2 - Global.TILE_SIZE*SCALE_FACTOR / 2;

        screenHitbox.x = screenPosition.x + hitBoxCornersOffset.x;
		screenHitbox.y = screenPosition.y + hitBoxCornersOffset.y;

		screenSolidBox.x = screenPosition.x + solidBoxCornersOffset.x;
		screenSolidBox.y = screenPosition.y + solidBoxCornersOffset.y;

		screenAttackLeftHitbox.x = screenPosition.x + attackLeftHitBoxCornersOffset.x;
		screenAttackLeftHitbox.y = screenPosition.y + attackLeftHitBoxCornersOffset.y;

		screenAttackRightHitbox.x = screenPosition.x + attackRightHitBoxCornersOffset.x;
		screenAttackRightHitbox.y = screenPosition.y + attackRightHitBoxCornersOffset.y;
    }

    public Player(Tile[][] mapTiles) {
        super(mapTiles, Global.PLAYER_HAS_COLLISION);

        screenPosition.x = Global.SCREEN_WIDTH / 2 - Global.TILE_SIZE*SCALE_FACTOR / 2;
        screenPosition.y = Global.SCREEN_HEIGHT / 2 - Global.TILE_SIZE*SCALE_FACTOR / 2;

        screenHitbox.x = screenPosition.x + hitBoxCornersOffset.x;
		screenHitbox.y = screenPosition.y + hitBoxCornersOffset.y;

		screenSolidBox.x = screenPosition.x + solidBoxCornersOffset.x;
		screenSolidBox.y = screenPosition.y + solidBoxCornersOffset.y;

		screenAttackLeftHitbox.x = screenPosition.x + attackLeftHitBoxCornersOffset.x;
		screenAttackLeftHitbox.y = screenPosition.y + attackLeftHitBoxCornersOffset.y;

		screenAttackRightHitbox.x = screenPosition.x + attackRightHitBoxCornersOffset.x;
		screenAttackRightHitbox.y = screenPosition.y + attackRightHitBoxCornersOffset.y;
    }

    protected void initStats() {
        hasInventory = true;
        health = maxHealth;
        attackDamage = 3;
        if (Global.BOOST_PLAYER_SPEED) {
            speed = 10;
        } else {
            speed = 1;
        }
        sprintSpeed = 2;
        attackCooldownMax = 25;

        SCALE_FACTOR = 2;
    }

    public void initHitBox() {
        hitBoxCornersOffset = new Point(16*SCALE_FACTOR, 13*SCALE_FACTOR);
        hitbox = new Rectangle(worldPosition.x + hitBoxCornersOffset.x, worldPosition.y + hitBoxCornersOffset.y, 15*SCALE_FACTOR, 20*SCALE_FACTOR);

        attackLeftHitBoxCornersOffset = new Point(4*SCALE_FACTOR,15*SCALE_FACTOR);
        attackLeftHitbox = new Rectangle(worldPosition.x + attackLeftHitBoxCornersOffset.x, worldPosition.y + attackLeftHitBoxCornersOffset.y, 17*SCALE_FACTOR, 21*SCALE_FACTOR);
        
        attackRightHitBoxCornersOffset = new Point(27*SCALE_FACTOR,15*SCALE_FACTOR);
        attackRightHitbox = new Rectangle(worldPosition.x + attackRightHitBoxCornersOffset.x, worldPosition.y + attackRightHitBoxCornersOffset.y, 17*SCALE_FACTOR, 21*SCALE_FACTOR);
    }

    public void initSolidBox() {
        solidBoxCornersOffset = new Point(16*SCALE_FACTOR, 25*SCALE_FACTOR);
        solidBox = new Rectangle(worldPosition.x + solidBoxCornersOffset.x, worldPosition.y + solidBoxCornersOffset.y, 15*SCALE_FACTOR, 6*SCALE_FACTOR);
    }

    protected void initSprites() {
        SpriteSheet idleLeftSheet = new SpriteSheet("res/player/player-idle-left-strip.png", 48, 48);
        SpriteSheet idleRightSheet = new SpriteSheet("res/player/player-idle-right-strip.png", 48, 48);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/player/player-walk-left-strip.png", 48, 48);
        SpriteSheet walkRightSheet = new SpriteSheet("res/player/player-walk-right-strip.png", 48, 48);
        SpriteSheet attackRightSheet = new SpriteSheet("res/player/player-attack-right-strip.png", 48, 48);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/player/player-attack-left-strip.png", 48, 48);
        SpriteSheet deathRightSheet = new SpriteSheet("res/player/player-death-right-strip.png", 48, 48);
        SpriteSheet deathLeftSheet = new SpriteSheet("res/player/player-death-left-strip.png", 48, 48);
        
        idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 5);
        idleRight = new Animation(idleRightSheet.getSpriteArray(), 5);
        walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 5);
        walkRight = new Animation(walkRightSheet.getSpriteArray(), 5);
        attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 5);
        attackRight = new Animation(attackRightSheet.getSpriteArray(), 5);
        deathLeft = new Animation(deathLeftSheet.getSpriteArray(), 5);
        deathRight = new Animation(deathRightSheet.getSpriteArray(), 5);
    }

    protected void initSounds() {
        attackSound = new Sound("res/sound/Attackwoosh.wav");
        deathSound = new Sound("res/sound/ghostdead.wav");
        footStepGrassSound = new Sound("res/sound/grassFootStepSound.wav");

    }

    public void pickUp(Item item) {
        if (!inventory.isFull()) {
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

    // @Override
	public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
		attackCooldown--;

		BufferedImage image = null;

		if (isDead()) {
			if (!deathLeft.reachedEndFrame() && !deathRight.reachedEndFrame()) {
				if (direction == LEFT || lastDirection == LEFT) {
					image = deathLeft.getSprite().image;
					deathLeft.update();
				} else {
					image = deathRight.getSprite().image;
					deathRight.update();
				}
			} else {
				if (!isFaded()) { fade(); }

				if (direction == LEFT || lastDirection == LEFT) {
					image = deathLeft.getLastFrameSprite().image;
				} else {
					image = deathRight.getLastFrameSprite().image;
				}
			}
		} else {
			if (direction == IDLE + RIGHT) {
				image = idleRight.getSprite().image;
				idleRight.update();
			} else if (direction == IDLE + LEFT) {
				image = idleLeft.getSprite().image;
				idleLeft.update();
			} else if (direction == RIGHT) {
				image = walkRight.getSprite().image;
				walkRight.update();
			} else if (direction == LEFT) {
				image = walkLeft.getSprite().image;
				walkLeft.update();
			}
	
			if ((direction == LEFT || lastDirection == LEFT) && isAttacking) {
				image = attackLeft.getSprite().image;
				attackLeft.update();
			} else if ((direction == RIGHT || lastDirection == RIGHT) && isAttacking) {
				image = attackRight.getSprite().image;
				attackRight.update();
			}
	
			if (isAttacking && (attackLeft.reachedEndFrame() || attackRight.reachedEndFrame())) {
				isAttacking = false;
			}
		}

		if (alphaValue != 1) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
		}

		g.drawImage(image, screenPosition.x, screenPosition.y, image.getHeight()*SCALE_FACTOR, image.getWidth()*SCALE_FACTOR, null);
		
		if (alphaValue != 1) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}

        if (Global.DEBUG) {
            // fill suronding squares with semi transparent yellow
            /*
            g.setColor(new Color(255, 255, 0, 100));
            int[][] surroundingColsRows = getSurroundingColsRows();
            for (int i = 0; i < surroundingColsRows.length; i++) {
                int col = surroundingColsRows[i][0];
                int row = surroundingColsRows[i][1];
                g.fillRect(col * Global.TILE_SIZE, row * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE);
            }
            */
			// draw a filled circle centered at x, y
        	g.setColor(Color.RED);
			int r = 5;
			g.fillOval(screenPosition.x - r, screenPosition.y - r, r * 2, r * 2);
            
        	// draw hitbox
        	g.setColor(Color.BLUE);
        	g.drawRect(screenHitbox.x, screenHitbox.y, hitbox.width, hitbox.height);

        	// draw solidbox
        	g.setColor(Color.GREEN);
        	g.drawRect(screenSolidBox.x, screenSolidBox.y, solidBox.width, solidBox.height);
        	
        	// draw attackHitbox
        	if (true) {
        		g.setColor(Color.RED);
        		if (direction == LEFT || lastDirection == LEFT) {
        			g.drawRect(screenAttackLeftHitbox.x, screenAttackLeftHitbox.y, attackLeftHitbox.width, attackLeftHitbox.height);
        		}
        		if (direction == RIGHT || lastDirection == RIGHT) {
        			g.drawRect(screenAttackRightHitbox.x, screenAttackRightHitbox.y, attackRightHitbox.width, attackRightHitbox.height);
        		}
        	}
        }
    }
}