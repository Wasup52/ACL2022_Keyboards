package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.keyboards.graphics.Animation;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.sound.Sound;
import com.keyboards.tile.Tile;

public class Ghost extends Mob {
	
	    public boolean isAttacking = false;

	    private Animation idleLeft;
	    private Animation idleRight;
	    private Animation walkLeft;
	    private Animation walkRight;
	    private Animation attackLeft;
	    private Animation attackRight;
	    private Animation dead;
	    private Sprite[] cadavre;
	    public int temps_cadrave_disparaisse=500;
	    public int compteur=0;
	    public Sound sound_dead = new Sound("res/sound/ghostdead.wav");
	    public Sound sound_attack = new Sound("res/sound/Attack-ghost.wav");
    public Ghost(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
    }

    public Ghost(Tile[][] mapTiles) {
        super(mapTiles);
    }

    protected void initStats() {
        health = 1;
        attackDamage = 2;
        speed = 1;
        attackCooldownMax = 20;
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
    	 SpriteSheet idleLeftSheet = new SpriteSheet("res/ghost/idle-left.png", 32, 32);
         SpriteSheet idleRightSheet = new SpriteSheet("res/ghost/idle-right.png", 32, 32);
         SpriteSheet walkLeftSheet = new SpriteSheet("res/ghost/walk-left.png", 32, 32);
         SpriteSheet walkRightSheet = new SpriteSheet("res/ghost/walk-right.png", 32, 32);
         SpriteSheet attackRightSheet = new SpriteSheet("res/ghost/attack-right.png", 32, 32);
         SpriteSheet attackLeftSheet = new SpriteSheet("res/ghost/attack-left.png", 32, 32);
         SpriteSheet deadSheet = new SpriteSheet("res/ghost/dead.png", 32, 32);
         
         
         idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 5);
         idleRight = new Animation(idleRightSheet.getSpriteArray(), 5,true);
         walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 4);
         walkRight = new Animation(walkRightSheet.getSpriteArray(), 4,true);
         attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 5);
         attackRight = new Animation(attackRightSheet.getSpriteArray(), 5,true);
         dead = new Animation (deadSheet.getSpriteArray(),5);

    }

    protected void die() {
    	
    	System.out.println("ghost died");
    	sound_dead.play();
    	this.hitbox=new Rectangle(0,0,0,0);
    	this.solidBox=new Rectangle(0,0,0,0);
    }
 public void draw(Graphics2D g) {

        BufferedImage image = null;
        
        if(compteur==temps_cadrave_disparaisse-1) {
        	
        }
        else {
        
        if (this.health==0 || this.health<0  ) {
        	
        	
        	if ( !dead.reachedEndFrame()) { 
        	
        		
        		System.out.println("zombie died test");
        		
        		dead.update();
        		compteur =compteur +1;
        		image=dead.getSprite().image;
        		g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);
        		this.speed=0;
        		this.attackDamage=0;
        		
        	}
        	else  {
        		
        		System.out.println(compteur);
        		
        		compteur =compteur +1;
        		
        	
            		}

        		
        }
        
        	
        else {	
            
            
        	idleLeft.update();
            idleRight.update();
            walkLeft.update();
            walkRight.update();
        	
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
    		
        	sound_attack.play();
        	if (attackLeft.reachedEndFrame() || attackRight.reachedEndFrame()) {
    			isAttacking = false;
    		}

            attackLeft.update();
            attackRight.update();
        	
        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        	
        } else {

        	g.drawImage(image, position.x, position.y, image.getHeight(), image.getWidth(), null);        	
        }

        

        super.draw(g);
    }
    }
    }
    
    
   
}
