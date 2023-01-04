package com.keyboards.graphics;

public class Animation {

    public Sprite[] getSprites() {
		return sprites;
	}

	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}

	public int getSpriteCounter() {
		return spriteCounter;
	}

	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}

	public int getSpriteCounterMax() {
		return spriteCounterMax;
	}

	public void setSpriteCounterMax(int spriteCounterMax) {
		this.spriteCounterMax = spriteCounterMax;
	}

	public boolean isReversed() {
		return isReversed;
	}

	public void setReversed(boolean isReversed) {
		this.isReversed = isReversed;
	}

	public boolean isHasReachedEndFrame() {
		return hasReachedEndFrame;
	}

	public void setHasReachedEndFrame(boolean hasReachedEndFrame) {
		this.hasReachedEndFrame = hasReachedEndFrame;
	}

	public void setSpriteNum(int spriteNum) {
		this.spriteNum = spriteNum;
	}

	private Sprite[] sprites;

    private int spriteNum = 0;
    private int spriteCounter = 0;
    private int spriteCounterMax = 0;

    private boolean isReversed;
    private boolean hasReachedEndFrame = false;

    public Animation(Sprite[] spriteArray, int frameRate) {
        this.sprites = spriteArray;
        this.spriteCounterMax = frameRate;
        this.isReversed = false;
    }

    public Animation(Sprite[] spriteArray, int frameRate, boolean isReversed) {
        this.sprites = spriteArray;
        this.spriteCounterMax = frameRate;
        this.isReversed = isReversed;
        if (isReversed) {
            this.spriteNum = sprites.length-1;
        }
    }

    public Animation(SpriteSheet spriteSheet, int frameRate) {
        this.sprites = spriteSheet.getSpriteArray();
        this.spriteCounterMax = frameRate;
        this.isReversed = false;
    }

    public Animation(SpriteSheet spriteSheet, int frameRate, boolean isReversed) {
        this.sprites = spriteSheet.getSpriteArray();
        this.spriteCounterMax = frameRate;
        this.isReversed = isReversed;
        if (isReversed) {
            this.spriteNum = sprites.length-1;
        }
    }

    public void update() {
        spriteCounter++;
        hasReachedEndFrame = false;
        if (spriteCounter > spriteCounterMax) {
            spriteCounter = 0;
            if (isReversed) {
                if (spriteNum == 0) {
                    hasReachedEndFrame = true;
                }
                spriteNum--;
                if (spriteNum < 0) {
                    spriteNum = sprites.length - 1;
                }
            } else {
                if (spriteNum == sprites.length - 1) {
                    hasReachedEndFrame = true;
                }
                spriteNum++;
                if (spriteNum >= sprites.length) {
                    spriteNum = 0;
                }
            }
        }
    }

    public int getSpriteNum() { return spriteNum; }
    public boolean reachedEndFrame() { return hasReachedEndFrame; }
    public Sprite getSprite() { return sprites[spriteNum]; }

    public Sprite getLastFrameSprite() {
        if (isReversed) {
            return sprites[0];
        } else {
            return sprites[sprites.length-1];
        }
    }
    
}
