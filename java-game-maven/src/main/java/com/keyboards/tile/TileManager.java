package com.keyboards.tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.keyboards.global.Global;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;
import com.keyboards.sound.Sound;

public class TileManager {
	
	public ArrayList<BufferedImage> tilesImages;
	public Tile[][] mapTiles = new Tile[Global.WORLD_ROW_NUM][Global.WORLD_COL_NUM];

	Color backgroundColor;

	Sound ambientSound;
	Sound stepSound;

	public TileManager(String mapsJsonFilePAth, int mapIndex) {
		try {
			initMapFromJsonFile(mapsJsonFilePAth, mapIndex);
		} catch (Exception e) {
			System.out.println("ERROR: could not load map from json file");
			e.printStackTrace();
		}
	}
	
	public Tile getTile(int row, int col) {
		return mapTiles[row][col];
	}
	
	void loadTilesImages(SpriteSheet spriteSheet) {
		tilesImages = new ArrayList<BufferedImage>();
		for(Sprite sprite : spriteSheet.getSpriteArray()) {
			tilesImages.add(sprite.image);
		}
		
		System.out.println("Loaded " + tilesImages.size() + " images from spriteSheet");
	}

	private int getNbOfLine(String filePath) throws IOException {
		int nbLine = 0;
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		while (br.readLine() != null) {
			nbLine++;
		}
		br.close();

		return nbLine;
	}

	private void initMapFromJsonFile(String filePath, int mapIndex) throws Exception {
	    System.out.println("Initializing map...");

		File file = new File(filePath);
		FileReader fr = new FileReader(file);

		JSONParser parser = new JSONParser();
		Object mapsJSON = parser.parse(fr);
		JSONArray maps = (JSONArray) mapsJSON;

		if (mapIndex >= maps.size() || mapIndex < 0) {
			throw new Exception("ERROR: invalid map index");
		}

		JSONObject map = (JSONObject) maps.get(mapIndex);

		ambientSound = new Sound((String) map.get("ambientSound"));
		stepSound = new Sound((String) map.get("stepSound"));
		
		loadTilesImages(new SpriteSheet((String) map.get("spriteSheet"), 16, 16));
		backgroundColor = Sprite.HexToColor((String) map.get("backgroundColorHex"));
		
		String mapFile = (String) map.get("mapFile");
		JSONArray tilesJSON = (JSONArray) map.get("tiles");

		BufferedReader br = new BufferedReader(new FileReader(new File(mapFile)));

		if (getNbOfLine(mapFile) != Global.WORLD_ROW_NUM) {
			br.close();
			throw new Exception("ERROR: invalid map file (wrong number of rows)");
		}

		String line;
		int row = 0;
		while ((line = br.readLine()) != null) {
			String[] tilesIndex = line.split(" ");

			if (tilesIndex.length != Global.WORLD_COL_NUM) {
				br.close();
				throw new Exception("ERROR: invalid map file (wrong number of columns) on line " + row);
			}

			for (int col = 0; col < tilesIndex.length; col++) {
				int tileIndex = Integer.parseInt(tilesIndex[col]);

				if (tileIndex >= 0 && tileIndex < tilesImages.size() && tileIndex < tilesJSON.size()) {
					// get the solidBox from the tilesJSON
					int xOffset = tilesJSON.get(tileIndex) != null ? ((Long) ((JSONObject) ((JSONObject) tilesJSON.get(tileIndex)).get("solidBox")).get("xOffset")).intValue()  * Global.SCALE : 0; // if the tile is not in the tilesJSON, the xOffset is 0
					int yOffset = tilesJSON.get(tileIndex) != null ? ((Long) ((JSONObject) ((JSONObject) tilesJSON.get(tileIndex)).get("solidBox")).get("yOffset")).intValue()  * Global.SCALE : 0; // if the tile is not in the tilesJSON, the yOffset is 0
					int width = tilesJSON.get(tileIndex) != null ? ((Long) ((JSONObject) ((JSONObject) tilesJSON.get(tileIndex)).get("solidBox")).get("width")).intValue() * Global.SCALE : Global.TILE_SIZE; // if the tile is not in the tilesJSON, the width is the default tile size
					int height = tilesJSON.get(tileIndex) != null ? ((Long) ((JSONObject) ((JSONObject) tilesJSON.get(tileIndex)).get("solidBox")).get("height")).intValue() * Global.SCALE : Global.TILE_SIZE; // if the tile is not in the tilesJSON, the height is the default tile size
					Boolean isSolid = tilesJSON.get(tileIndex) != null ? (Boolean) ((JSONObject) tilesJSON.get(tileIndex)).get("isSolid") : false; // if the tile is not in the tilesJSON, it is not solid

					Rectangle tileSolidBox = new Rectangle(col * Global.TILE_SIZE + xOffset, row * Global.TILE_SIZE + yOffset, width, height);

					Tile tile;
					if (isSolid) {
						tile = new Tile(row, col, tilesImages.get(tileIndex), tileSolidBox);
					} else {
						tile = new Tile(row, col, tilesImages.get(tileIndex));
					}

					mapTiles[row][col] = tile;
				} else {
					mapTiles[row][col] = new Tile(row, col, null);
				}
			}

			row++;
		}

		System.out.println("Map initialized");
		fr.close();
		br.close();

	}

	public void playStepSound() {
		if (stepSound != null) {
			stepSound.play();
		}
	}

	public void loopAmbientSound() {
		if (ambientSound != null) {
			ambientSound.loop();
		}
	}

	public void stopAmbientSound() {
		if (ambientSound != null) {
			ambientSound.stop();
		}
	}

	public void stopSounds() {
		ambientSound.stop();
		stepSound.stop();
	}

	public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
		// draw a sqare the size of the screen whith the background color to prevent seeing white when on the edge of the map
		g.setColor(backgroundColor);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

		for (int worldRow = 0; worldRow < Global.WORLD_ROW_NUM; worldRow++) {
			for (int worldCol = 0; worldCol < Global.WORLD_COL_NUM; worldCol++) {
				if (mapTiles[worldRow][worldCol].needToBeDrawn(worldCol, worldRow, playerWorldPos, playerScreenPos)) {
					if (mapTiles[worldRow][worldCol] != null) {
						mapTiles[worldRow][worldCol].draw(g, worldCol, worldRow, playerWorldPos, playerScreenPos);
					} else {
						Tile.drawBlankTile(g, worldCol, worldRow, playerWorldPos, playerScreenPos);
					}
				}
			}
		}
	}

}