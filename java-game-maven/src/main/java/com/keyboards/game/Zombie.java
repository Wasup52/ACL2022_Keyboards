package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.tile.Tile;

public class Zombie extends Mob {

    public final int NUMBER_OF_FRAME_IN_WALK_ANIM = 6;
    public final int NUMBER_OF_FRAME_IN_ATTACK_ANIM = 3;

    public boolean isAttacking = false;

    private Sprite[] idleLeft;
    private Sprite[] idleRight;
    private Sprite[] walkLeft;
    private Sprite[] walkRight;
    private Sprite[] attackLeft;
    private Sprite[] attackRight;

    public int spriteNum = 0;
    public int spriteAttack=0;
    public int spriteCounter = 0;
    
    public Zombie(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
    }

    public Zombie(Tile[][] mapTiles) {
        super(mapTiles);
    }

    protected void initStats() {
        health = 10;
        attackDamage = 3;
        speed = 1;
        attackCooldownMax = 60;
    }

    protected void initHitBox() {
        hitBoxCornersOffset = new Point(0, 0);
        hitbox = new Rectangle(position.x + hitBoxCornersOffset.x, position.y + hitBoxCornersOffset.y, 32, 32);

        attackLeftHitBoxCornersOffset = new Point(0, 0);
        attackLeftHitbox = new Rectangle(position.x + attackLeftHitBoxCornersOffset.x, position.y + attackLeftHitBoxCornersOffset.y, 32, 32);
        
        attackRightHitBoxCornersOffset = new Point(0, 0);
        attackRightHitbox = new Rectangle(position.x + attackRightHitBoxCornersOffset.x, position.y + attackRightHitBoxCornersOffset.y, 32, 32);
    }

    protected void initSolidBox() {
        solidBoxCornersOffset = new Point(0, 0);
        solidBox = new Rectangle(position.x + solidBoxCornersOffset.x, position.y + solidBoxCornersOffset.y, 32, 32);
    }

    protected void initSprites() {
        SpriteSheet idleLeftSheet = new SpriteSheet("res/slime/slime-idle-left.png", 32, 32);
        SpriteSheet idleRightSheet = new SpriteSheet("res/slime/slime-idle-right.png", 32, 32);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/slime/slime-walk-left.png", 32, 32);
        SpriteSheet walkRightSheet = new SpriteSheet("res/slime/slime-walk-right.png", 32, 32);
        SpriteSheet attackRightSheet = new SpriteSheet("res/slime/slime-attack-right.png", 32, 32);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/slime/slime-attack-left.png", 32, 32);
        
        idleLeft = idleLeftSheet.getSpriteArray();
        idleRight = idleRightSheet.getSpriteArray();
        walkLeft = walkLeftSheet.getSpriteArray();
        walkRight = walkRightSheet.getSpriteArray();
        attackRight = attackRightSheet.getSpriteArray();
        attackLeft = attackLeftSheet.getSpriteArray();
    }

    protected void die() {
        System.out.println("zombie died");
    }

    @Override
    public void draw(Graphics2D g) {

        BufferedImage image = null;
        
        if (direction == IDLE + RIGHT) {
            image = idleRight[spriteNum].image;
        } else if (direction == IDLE + LEFT) {
            image = idleLeft[spriteNum].image;
        } else if (direction == RIGHT) {
            image = walkRight[spriteNum].image;
        } else if (direction == LEFT) {
            image = walkLeft[spriteNum].image;
        }

        if ((direction == LEFT || lastDirection == LEFT) && isAttacking) {
        	image = attackLeft[spriteAttack].image;
        } else if ((direction == RIGHT || lastDirection == RIGHT) && isAttacking) {
        	image = attackRight[spriteAttack].image;
        }

        spriteCounter++;
        if (isAttacking) {
        	
    		if (spriteCounter > 5) {
    			if (spriteAttack < NUMBER_OF_FRAME_IN_ATTACK_ANIM - 1) {
    				spriteAttack++;
    			} else {
    				isAttacking = false;
    				spriteAttack = 0;
    			}
    			spriteCounter = 0;
    		}
    		
        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        	
        } else {
        	
    		if (spriteCounter > 5) {
    			if (spriteNum < NUMBER_OF_FRAME_IN_WALK_ANIM - 1) {
    				spriteNum++;
    			} else {
    				spriteNum = 0;
    			}
    			spriteCounter = 0;
    		}

        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        }

        // g.setColor(Color.GREEN);
        // g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        super.draw(g);
    }
}
