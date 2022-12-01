package com.keyboards.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import com.keyboards.tile.Tile;

public class Ghost extends Mob {
    
    public Ghost(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
    }

    public Ghost(Tile[][] mapTiles) {
        super(mapTiles);
    }

    protected void initStats() {
        health = 10;
        attackDamage = 2;
        speed = 6;
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
        // TODO gives sprites to the ghost
    }

    protected void die() {
        System.out.println("ghost died");
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GRAY);
        g.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        
        super.draw(g);
    }

}
