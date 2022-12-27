package com.keyboards.game;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.keyboards.global.Global;
import com.keyboards.graphics.Sprite;
import com.keyboards.tile.Tile;

public abstract class Object extends Entity {
	public Sprite[] sprite;
	public BufferedImage image;

	public Object(int col, int row, Tile[][] mapTiles) {

		// place the character in the middle of the tile
		worldPosition.x = col * Global.TILE_SIZE + Global.TILE_SIZE / 2;// - hitBoxCornersOffset.x;
		worldPosition.y = row * Global.TILE_SIZE + Global.TILE_SIZE / 2;// - hitBoxCornersOffset.y;

		// update the hitbox position
		// hitbox.x = position.x + hitBoxCornersOffset.x-nombre_pixel/4;
		// hitbox.y = position.y + hitBoxCornersOffset.y-nombre_pixel/4;

		// update the solidbox position
		// solidBox.x = position.x + solidBoxCornersOffset.x-nombre_pixel/4;
		// solidBox.y = position.y + solidBoxCornersOffset.y-nombre_pixel/4;

		System.out.println(this + " created at " + row + " " + col); 

		init();

	}

	public Object(Tile[][] mapTiles, float distance_min, Player p) {

		placeRandomly(mapTiles, distance_min, p);

		init();
	}

	private void placeRandomly(Tile[][] mapTiles, float distance_min, Player p) {
		// TODO place the object randomly in the map
		// TODO place the object randomly in the map

		int col = (int) (Math.random() * Global.WORLD_COL_NUM);
		int row = (int) (Math.random() * Global.WORLD_ROW_NUM);

		// try while the randomly chosen tile is solid
		while (mapTiles[row][col].isSolid()) {
			col = (int) (Math.random() * Global.WORLD_COL_NUM);
			row = (int) (Math.random() * Global.WORLD_ROW_NUM);
		}

		// try while the randomly chosen tile is too close to the player
		while (Math.sqrt(((p.worldPosition.x - this.worldPosition.x) * (p.worldPosition.x - this.worldPosition.x)
				+ (p.worldPosition.y - this.worldPosition.y)
						* (p.worldPosition.y - this.worldPosition.y))) < distance_min) {
			col = (int) (Math.random() * Global.WORLD_COL_NUM);
			row = (int) (Math.random() * Global.WORLD_ROW_NUM);
		}

		// place the postions in the middle of the tile
		worldPosition.x = col * Global.TILE_SIZE + Global.TILE_SIZE / 2;// - hitBoxCornersOffset.x;
		worldPosition.y = row * Global.TILE_SIZE + Global.TILE_SIZE / 2;// - hitBoxCornersOffset.y;

		// update the hitbox position
		// hitbox.x = position.x ;//+ hitBoxCornersOffset.x- nombre_pixel/4;
		// hitbox.y = position.y;// + hitBoxCornersOffset.y- nombre_pixel/4;

		// update the solidbox position
		// solidBox.x = position.x + solidBoxCornersOffset.x;
		// solidBox.y = position.y + solidBoxCornersOffset.y;

		System.out.println(this + " created randomly at " + row + " " + col);

	}

	protected abstract void initHitBox();

	protected abstract void initSolidBox();

	protected abstract void initSprites();

	public void init() {
		initSprites();
		initHitBox();
		initSolidBox();

	}

	public int[][] getSurroundingColsRows() {
		int leftX = solidBox.x;
		int rightX = solidBox.x + solidBox.width;
		int topY = solidBox.y;
		int bottomY = solidBox.y + solidBox.height;

		int leftTileCol = leftX / Global.TILE_SIZE;
		int rightTileCol = rightX / Global.TILE_SIZE;
		int topTileRow = topY / Global.TILE_SIZE;
		int bottomTileRow = bottomY / Global.TILE_SIZE;

		int[][] surroundingColsRows = new int[4][2];
		surroundingColsRows[0][0] = leftTileCol;
		surroundingColsRows[0][1] = topTileRow;
		surroundingColsRows[1][0] = rightTileCol;
		surroundingColsRows[1][1] = topTileRow;
		surroundingColsRows[2][0] = leftTileCol;
		surroundingColsRows[2][1] = bottomTileRow;
		surroundingColsRows[3][0] = rightTileCol;
		surroundingColsRows[3][1] = bottomTileRow;

		return surroundingColsRows;
	}
}
