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

    public Ghost(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles, false);
    }

    public Ghost(Tile[][] mapTiles) {
        super(mapTiles, false);
    }

    protected void initStats() {
        health = 1;
        attackDamage = 2;
        speed = 1;
        attackCooldownMax = 20;
    }

    protected void initHitBox() {
        hitBoxCornersOffset = new Point(8*SCALE_FACTOR,3*SCALE_FACTOR);
        hitbox = new Rectangle(worldPosition.x + hitBoxCornersOffset.x, worldPosition.y + hitBoxCornersOffset.y, 22*SCALE_FACTOR, 26*SCALE_FACTOR);

        attackLeftHitBoxCornersOffset = new Point(1*SCALE_FACTOR, 6*SCALE_FACTOR);
        attackLeftHitbox = new Rectangle(worldPosition.x + attackLeftHitBoxCornersOffset.x, worldPosition.y + attackLeftHitBoxCornersOffset.y, 12*SCALE_FACTOR, 15*SCALE_FACTOR);
        
        attackRightHitBoxCornersOffset = new Point(20*SCALE_FACTOR, 8*SCALE_FACTOR);
        attackRightHitbox = new Rectangle(worldPosition.x + attackRightHitBoxCornersOffset.x, worldPosition.y + attackRightHitBoxCornersOffset.y, 12*SCALE_FACTOR, 15*SCALE_FACTOR);
    }

    protected void initSolidBox() {
        solidBoxCornersOffset = new Point(8*SCALE_FACTOR,3*SCALE_FACTOR);
        solidBox = new Rectangle(worldPosition.x + solidBoxCornersOffset.x, worldPosition.y + solidBoxCornersOffset.y, 22*SCALE_FACTOR, 26*SCALE_FACTOR);
    }

    protected void initSprites() {
        SpriteSheet idleLeftSheet = new SpriteSheet("res/ghost/idle-left.png", 32, 32);
        SpriteSheet idleRightSheet = new SpriteSheet("res/ghost/idle-right.png", 32, 32);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/ghost/walk-left.png", 32, 32);
        SpriteSheet walkRightSheet = new SpriteSheet("res/ghost/walk-right.png", 32, 32);
        SpriteSheet attackRightSheet = new SpriteSheet("res/ghost/attack-right.png", 32, 32);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/ghost/attack-left.png", 32, 32);
        SpriteSheet deathSheet = new SpriteSheet("res/ghost/dead.png", 32, 32);

        idleLeft = new Animation(idleLeftSheet.getSpriteArray(), 5);
        idleRight = new Animation(idleRightSheet.getSpriteArray(), 5, true);
        walkLeft = new Animation(walkLeftSheet.getSpriteArray(), 5);
        walkRight = new Animation(walkRightSheet.getSpriteArray(), 5, true);
        attackLeft = new Animation(attackLeftSheet.getSpriteArray(), 5);
        attackRight = new Animation(attackRightSheet.getSpriteArray(), 5, true);
        deathLeft = new Animation(deathSheet.getSpriteArray(), 5);
        deathRight = new Animation(deathSheet.getSpriteArray(), 5);

    }

    protected void initSounds() {
        attackSound = new Sound("res/sound/Attack-ghost.wav");
        deathSound = new Sound("res/sound/ghostdead.wav");
    }
}
