package com.keyboards.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Sprite {

    public BufferedImage image;

	private int w;
	private int h;

    public Sprite(BufferedImage image) {
		this.image = image;
		this.w = image.getWidth();
		this.h = image.getHeight();
	}
	
	public int getWidth() { return w; }
	public int getHeight() { return h; }

    public Sprite getSubSprite(int x, int y, int w, int h) {
        return new Sprite(image.getSubimage(x, y, w, h));
	}

	static public Color HexToColor(String Hex) {
		// System.out.println(Hex);
		// System.out.println("R: " + Integer.valueOf( Hex.substring( 1, 3 ), 16 ) + " G: " + Integer.valueOf( Hex.substring( 3, 5 ), 16 ) + " B: " + Integer.valueOf( Hex.substring( 5, 7 ), 16 ));
		return new Color(
			Integer.valueOf( Hex.substring( 1, 3 ), 16 ),
			Integer.valueOf( Hex.substring( 3, 5 ), 16 ),
			Integer.valueOf( Hex.substring( 5, 7 ), 16 )
		);
	}
}