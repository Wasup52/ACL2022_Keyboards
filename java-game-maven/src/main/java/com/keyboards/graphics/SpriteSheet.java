package com.keyboards.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import com.keyboards.global.Global;
 
public class SpriteSheet {

    private Sprite SPRITESHEET = null;
    private Sprite[] spriteArray;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;
    private String file;


    /**
     * Loads the sprite sheet into the spriteArray where the sprites are TILE_SIZE pixels high and TILE_SIZE pixels wide
     * 
     * @param file
     */
    public SpriteSheet(String file) {
        this.file = file;
        this.w = Global.TILE_SIZE;
        this.h = Global.TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    /**
     * Loads the sprite sheet into the spriteArray where the sprites are h pixels high and w pixels wide
     * 
     * @param file
     * @param w
     * @param h
     */
    public SpriteSheet(String file, int w, int h) {
        this.file = file;
        this.w = w;
        this.h = h;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w; // the 
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    public int getWidth() { return w; } // returns width of sprite
    public int getHeight() { return h; } // returns the height of the sprite
    public int getRows() { return hSprite; } // number of rows in the spritesheet
    public int getCols() { return wSprite; } // the number of columns in the sprite sheet
    public int getTotalTiles() { return wSprite * hSprite; } // total tiles in spritesheet
    public Sprite getSpriteSheet() { return SPRITESHEET; } // returns the spritesheet
    public Sprite[] getSpriteArray() { return spriteArray; } // returns the sprite array

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(new File(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }
        return sprite;
    }

    private void loadSpriteArray() {
        spriteArray = new Sprite[wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[x] = getSprite(x, y);
            }
        }
    }

    /**
     * Returns the sprite at the x and y position where x and y are the coordinates of the sprite in the spritesheet
     * 
     * @param x
     * @param y
     * @return sprite
     */
    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubSprite(x * w, y * h, w, h);
    }
}