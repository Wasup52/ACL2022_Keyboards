package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.graphics.Animation;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.tile.Tile;

public class Zombie extends Mob {

    public boolean isAttacking = false;

    private Animation idleLeft;
    private Animation idleRight;
    private Animation walkLeft;
    private Animation walkRight;
    private Animation attackLeft;
    private Animation attackRight;
    
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
        
        idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 5);
        idleRight = new Animation(idleRightSheet.getSpriteArray(), 5);
        walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 5);
        walkRight = new Animation(walkRightSheet.getSpriteArray(), 5);
        attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 5);
        attackRight = new Animation(attackRightSheet.getSpriteArray(), 5);

    }

    protected void die() {
        System.out.println("zombie died");
    }

    @Override
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
        	
        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        	
        } else {

        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        }

        // g.setColor(Color.GREEN);
        // g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);

        super.draw(g);
    }
}
