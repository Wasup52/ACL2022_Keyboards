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

public class Chest extends Object{
	Sound openChestSound;
	Sound closeChestSound;

    private Inventory inventory = new Inventory(this, 5, 5);
    
    private int spritesSize;
    private final int scale_factor = 2;

    private Animation openingAnim;
    private Animation closingAnim;
    private BufferedImage openedImage;
    private BufferedImage closedImage;

    private boolean opening = false;
    private boolean closing = false;

    public Chest(int col, int row, Tile[][] mapTiles) {
        super(col, row, mapTiles);
        hasInventory = true;
        System.out.println("solidBox: " + solidBox + " hitbox: " + hitbox);
    }

    public Chest(Tile[][] mapTiles, float distance_min, Player p) {
        super(mapTiles, distance_min, p);
        hasInventory = true;
        System.out.println("solidBox: " + solidBox + " hitbox: " + hitbox);
    }

    public void initHitBox() {
        hitBoxCornersOffset = new Point(1,1 );
        hitbox = new Rectangle(worldPosition.x-(spritesSize/2)*scale_factor, worldPosition.y-2*(spritesSize/2),spritesSize*scale_factor - hitBoxCornersOffset.x*scale_factor,spritesSize*scale_factor- hitBoxCornersOffset.y*scale_factor);
    }

    public void initSolidBox() {
        solidBoxCornersOffset = new Point(1,1);
        solidBox = new Rectangle(worldPosition.x-(spritesSize/2)*scale_factor, worldPosition.y-(spritesSize/2)*scale_factor,spritesSize*scale_factor - hitBoxCornersOffset.x*scale_factor,spritesSize*scale_factor- hitBoxCornersOffset.y*scale_factor);
    }

    protected void initSprites() {
        spritesSize = 16;
        SpriteSheet spriteSheet = new SpriteSheet("res/Objects/Chest.png", spritesSize, spritesSize);
        Sprite[] spriteArray = spriteSheet.getSpriteArray();

        openedImage = spriteArray[spriteArray.length-1].image;
        closedImage = spriteArray[0].image;


        openingAnim = new Animation(spriteArray, 5, false);
        closingAnim = new Animation(spriteArray, 5, true);
        
 	 openChestSound = new Sound("res/sound/openingChest.wav");
 	 closeChestSound = new Sound("res/sound/closingChest.wav");
    }

    public boolean isOpen() { return inventory.isOpen(); }
    public void open() { opening = true; /* spriteNum = 0 */; inventory.open(); }
    public void close() { closing = true; /* spriteNum = NUMBER_OF_FRAME_IN_OPEN_ANIM - 1 */; inventory.close(); }
    public void put(Item item) { inventory.addItem(item); }

    public void transfertClickedItem(int mouseX, int mouseY, Player player) {
        inventory.transfertClickedItem(mouseX, mouseY, player.getInventory());
    }

    public void drawInventory(Graphics2D g) { inventory.draw(g); }
    
	public void playOpenChestSound() {
	openChestSound.play();
	}
	
	public void playCloseChestSound() {
		closeChestSound.play();
	}

    public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
        BufferedImage image = null;

        screenPosition.x = worldPosition.x-spritesSize/2*scale_factor - playerWorldPos.x + playerScreenPos.x;
        screenPosition.y = worldPosition.y-spritesSize/2*scale_factor - playerWorldPos.y + playerScreenPos.y;

        if (opening || closing) {
            if (opening) {
                openingAnim.update();
                if (openingAnim.reachedEndFrame()) {
                    opening = false;
                    image = openedImage;
                } else {
                    image = openingAnim.getSprite().image;
                }
            } else {
                closingAnim.update();
                if (closingAnim.reachedEndFrame()) {
                    closing = false;
                    image = closedImage;
                } else {
                    image = closingAnim.getSprite().image;
                }
            }

            g.drawImage(image,
                screenPosition.x,
                screenPosition.y,
                spritesSize*scale_factor,
                spritesSize*scale_factor,
                null
            );
        } else {
            if (inventory.isOpen()) {
                g.drawImage(openedImage,
                    screenPosition.x,
                    screenPosition.y,
                    spritesSize*scale_factor,
                    spritesSize*scale_factor,
                    null
                );
            } else {
                g.drawImage(closedImage,
                    screenPosition.x,
                    screenPosition.y,
                    spritesSize*scale_factor,
                    spritesSize*scale_factor,
                    null
                );
            }
        }
    }
    
}
