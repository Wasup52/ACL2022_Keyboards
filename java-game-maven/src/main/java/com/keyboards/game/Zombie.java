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

    public Zombie(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles, true);
    }

    public Zombie(Tile[][] mapTiles) {
        super(mapTiles, true);
    }

    protected void initStats() {
        health = 1;
        attackDamage = 3;
        speed = 1;
        attackCooldownMax = 60;

        SCALE_FACTOR = 2;
    }

    protected void initHitBox() {
        hitBoxCornersOffset = new Point(9*SCALE_FACTOR, 7*SCALE_FACTOR);
        hitbox = new Rectangle(worldPosition.x + hitBoxCornersOffset.x, worldPosition.y + hitBoxCornersOffset.y, 12*SCALE_FACTOR, 17*SCALE_FACTOR);

        attackLeftHitBoxCornersOffset = new Point(2*SCALE_FACTOR, 8*SCALE_FACTOR);
        attackLeftHitbox = new Rectangle(worldPosition.x + attackLeftHitBoxCornersOffset.x, worldPosition.y + attackLeftHitBoxCornersOffset.y, 12*SCALE_FACTOR, 15*SCALE_FACTOR);

        attackRightHitBoxCornersOffset = new Point(9*SCALE_FACTOR, 7*SCALE_FACTOR);
        attackRightHitbox = new Rectangle(worldPosition.x + attackRightHitBoxCornersOffset.x, worldPosition.y + attackRightHitBoxCornersOffset.y, 12*SCALE_FACTOR, 17*SCALE_FACTOR);
    }

    protected void initSolidBox() {
        solidBoxCornersOffset = new Point(9*SCALE_FACTOR, 7*SCALE_FACTOR);
        solidBox = new Rectangle(worldPosition.x + solidBoxCornersOffset.x, worldPosition.y + solidBoxCornersOffset.y, 12*SCALE_FACTOR, 17*SCALE_FACTOR);
    }

    protected void initSprites() {
        SpriteSheet idleLeftSheet = new SpriteSheet("res/zombie/idle-left.png", 32, 32);
        SpriteSheet idleRightSheet = new SpriteSheet("res/zombie/idle-right.png", 32, 32);
        SpriteSheet walkLeftSheet = new SpriteSheet("res/zombie/walk-left.png", 32, 32);
        SpriteSheet walkRightSheet = new SpriteSheet("res/zombie/walk-right.png", 32, 32);
        SpriteSheet attackRightSheet = new SpriteSheet("res/zombie/attack-right.png", 32, 32);
        SpriteSheet attackLeftSheet = new SpriteSheet("res/zombie/attack-left.png", 32, 32);
        SpriteSheet deathLeftSheet = new SpriteSheet("res/zombie/dead-left.png", 32, 32);
        SpriteSheet deathRightSheet = new SpriteSheet("res/zombie/dead-right.png", 32, 32);

        idleLeft = new Animation(idleLeftSheet, 5, true);
        idleRight = new Animation(idleRightSheet, 5);
        walkLeft = new Animation(walkLeftSheet, 5, true);
        walkRight = new Animation(walkRightSheet, 5);
        attackLeft = new Animation(attackLeftSheet, 5, true);
        attackRight = new Animation(attackRightSheet, 5);
        deathLeft = new Animation(deathLeftSheet, 5, true);
        deathRight = new Animation(deathRightSheet, 5);
    }

    protected void initSounds() {
        attackSound = new Sound("res/sound/Attack-zombie.wav");
        deathSound = new Sound("res/sound/zombiedead.wav");
    }
}
