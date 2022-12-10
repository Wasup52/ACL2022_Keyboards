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

public class Zombie extends Mob {

    public boolean isAttacking = false;

    private Animation idleLeft;
    private Animation idleRight;
    private Animation walkLeft;
    private Animation walkRight;
    private Animation attackLeft;
    private Animation attackRight;
    private Animation deadLeft;
    private Animation deadRight;
    private Sprite[] cadavreLeft;
    private Sprite[] cadavreRight;
    public int temps_cadrave_disparaisse=500;
    public int compteur=0;
    public Sound sound_dead = new Sound("res/sound/zombiedead.wav");
    public Sound sound_attack = new Sound("res/sound/Attack-zombie.wav");
    public Zombie(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
    }

    public Zombie(Tile[][] mapTiles) {
        super(mapTiles);
    }

    protected void initStats() {
        health = 1;
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
        SpriteSheet idleLeftSheet = new SpriteSheet("res/zombie/idle-left.png", 32, 32);
        SpriteSheet idleRightSheet = new SpriteSheet("res/zombie/idle-right.png", 32, 32);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/zombie/walk-left.png", 32, 32);
        SpriteSheet walkRightSheet = new SpriteSheet("res/zombie/walk-right.png", 32, 32);
        SpriteSheet attackRightSheet = new SpriteSheet("res/zombie/attack-right.png", 32, 32);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/zombie/attack-left.png", 32, 32);
        SpriteSheet deadLeftSheet = new SpriteSheet("res/zombie/dead-left.png", 32, 32);
        SpriteSheet cadavreLeftSheet = new SpriteSheet("res/zombie/dead-left.png", 32, 32);
        SpriteSheet deadRightSheet = new SpriteSheet("res/zombie/dead-right.png", 32, 32);
        SpriteSheet cadavreRightSheet = new SpriteSheet("res/zombie/dead-right.png", 32, 32);
        
        idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 6,true);
        idleRight = new Animation(idleRightSheet.getSpriteArray(), 6);
        walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 11,true);
        walkRight = new Animation(walkRightSheet.getSpriteArray(), 11);
        attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 7,true);
        attackRight = new Animation(attackRightSheet.getSpriteArray(), 7);
        deadLeft = new Animation (deadLeftSheet.getSpriteArray(),5);
        deadRight = new Animation (deadLeftSheet.getSpriteArray(),5);
        cadavreLeft=cadavreLeftSheet.getSpriteArray();
        cadavreRight=cadavreRightSheet.getSpriteArray();
    }

    protected void die() {
        System.out.println("zombie died");
       sound_dead.play();
   	this.hitbox=new Rectangle(0,0,0,0);
   	this.solidBox=new Rectangle(0,0,0,0);
    }

    @Override
    public void draw(Graphics2D g) {


        
        BufferedImage image = null;
        if(compteur==temps_cadrave_disparaisse-1) {
        	
        }
        else {
        
        if (this.health==0 || this.health<0  ) {

        	if (lastDirection == RIGHT) {
        	if ( !deadRight.reachedEndFrame()) { 
        	
        		
        		System.out.println("zombie died test");
        		
        		deadRight.update();
        		compteur =compteur +1;
        		image=deadRight.getSprite().image;
        		g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);
        		this.speed=0;
        		this.attackDamage=0;
        		
        	}
        	else  {
        		
        		System.out.println(compteur);
        		image=cadavreRight[3].image;
        		
        		compteur =compteur +1;
        		g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);
        	
            		}
        	
        		
        }
        	else {
        		if ( !deadLeft.reachedEndFrame()) { 
                	
            		
            		System.out.println("zombie died test");
            		
            		deadLeft.update();
            		compteur =compteur +1;
            		image=deadLeft.getSprite().image;
            		g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);
            		this.speed=0;
            		this.attackDamage=0;
            		
            	}
            	else  {
            		
            		System.out.println(compteur);
            		image=cadavreLeft[0].image;
            		
            		compteur =compteur +1;
            		g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);
            	
                		}
            	
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
        	
        	g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);        	
        	
        } else {

        	g.drawImage(image, position.x-16, position.y-16, image.getHeight()*2, image.getWidth()*2, null);        	
        }

        

        super.draw(g);
    }
}
    }
}
