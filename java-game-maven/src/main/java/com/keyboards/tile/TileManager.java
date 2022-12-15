package com.keyboards.tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.keyboards.global.Global;
import com.keyboards.graphics.Sprite;
import com.keyboards.graphics.SpriteSheet;

public class TileManager {
	
	public ArrayList<BufferedImage> tilesImages;
	public Tile[][] mapTiles;
	SpriteSheet tileSpriteSheet;

	public TileManager(String mapFilePath) {
		tilesImages = new ArrayList<BufferedImage>();
		mapTiles = new Tile[Global.WORLD_ROW_NUM][Global.WORLD_COL_NUM];
		
		loadTileArrayFromDir("res/tiles-grass");
		System.out.println("TileManager: " + tilesImages.size() + " tiles loaded");

		initMapFromFile(mapFilePath);

		System.out.println("Nb of rows: " + mapTiles.length);
		System.out.println("Nb of cols: " + mapTiles[0].length);

		// print map tiles
//		for (int i = 0; i < mapTiles.length; i++) {
//			for (int j = 0; j < mapTiles[0].length; j++) {
//				System.out.print(mapTiles[i][j] + " "); 
//			}
//			System.out.println();
//		}
	}
	
	public TileManager(String mapFilePath, String sprintSheetPath) {
		tileSpriteSheet = new SpriteSheet(sprintSheetPath, 16, 16);
		tilesImages = new ArrayList<BufferedImage>();
		for(Sprite sprite : tileSpriteSheet.getSpriteArray()) {
			tilesImages.add(sprite.image);
		}
		mapTiles = new Tile[Global.WORLD_ROW_NUM][Global.WORLD_COL_NUM];
		
		
		System.out.println("TileManager: " + tilesImages.size() + " tiles loaded");

		initMapFromFile(mapFilePath);

		System.out.println("Nb of rows: " + mapTiles.length);
		System.out.println("Nb of cols: " + mapTiles[0].length);

		// print map tiles
//		for (int i = 0; i < mapTiles.length; i++) {
//			for (int j = 0; j < mapTiles[0].length; j++) {
//				System.out.print(mapTiles[i][j] + " "); 
//			}
//			System.out.println();
//		}
	}
	
	private BufferedImage loadTileImage(File file) {
		BufferedImage tileImage = null;
        try {
			System.out.println("Loading: " + file + "...");
            tileImage = ImageIO.read(file);
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
            System.err.println(e);
        }
        return tileImage;
	}


	private void loadTileArrayFromDir(String dirPath) {
		File dir = new File(dirPath);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			// sort the files alphabetically to ensure that the tiles are loaded in a predictable order
			for (int i = 0; i < files.length; i++) {
				for (int j = i + 1; j < files.length; j++) {
					if (files[i].getName().compareTo(files[j].getName()) > 0) {
						File temp = files[i];
						files[i] = files[j];
						files[j] = temp;
					}
				}
			}
			for (int i = 0; i < files.length; i++) {
				tilesImages.add(loadTileImage(files[i]));
			}
			System.out.println(tilesImages.size() + " tiles loaded");
		} else {
			System.out.println("ERROR: not a directory: " + dirPath);
		}
	}
	
	// needs to be in map class
	public Tile getTile(int row, int col) {
		return mapTiles[row][col];
	}

	private void initMap() {
	    System.out.println("Initializing map...");
		for (int row = 0; row < Global.WORLD_ROW_NUM; row++) {
			for (int col = 0; col < Global.WORLD_COL_NUM; col++) {
				mapTiles[row][col] = new Tile(row, col, null);
			}
		}

		for (int i = 0; i < tilesImages.size(); i++) {
			BufferedImage tileImage = tilesImages.get(i);
			if (tileImage != null) {
				if (i == 3) {
					mapTiles[5][i+1] = new Tile(5, i + 1, tileImage, new Rectangle((i+1) * Global.TILE_SIZE, 5 * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE));
				} else {
					mapTiles[5][i] = new Tile(5, i, tileImage, new Rectangle(i * Global.TILE_SIZE, 5 * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE));
				}
			}
		}
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

	private void initMapFromFile(String filePath) {
	    System.out.println("Initializing map...");

		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));

			if (getNbOfLine(filePath) != Global.WORLD_ROW_NUM) {
				br.close();
				throw new Exception("ERROR: invalid map file (wrong number of rows)");
			}

			String line;
			int row = 0;
			while ((line = br.readLine()) != null) {
				System.out.println(line);

				// split line on space
				String[] lineSplit = line.split(" ");

				if (lineSplit.length != Global.WORLD_COL_NUM) {
					br.close();
					throw new Exception("ERROR: invalid map file (wrong number of columns on line " + row + ")");
				}
	
				for (int col = 0; col < lineSplit.length; col++) {
					int tileIndex = Integer.parseInt(lineSplit[col])-1;

					if (tileIndex >= tilesImages.size()) {
						br.close();
						System.out.println("Index : " + tileIndex + ", size : " + tilesImages.size());
						throw new Exception("ERROR: invalid map file (invalid tile index on line " + row + ")");
					}

					if (tileIndex == -1) {
						mapTiles[row][col] = new Tile(row, col, null);
					} else {
						BufferedImage tileImage;
						tileImage = tilesImages.get(tileIndex);
						if (tileIndex != 5) {
							mapTiles[row][col] = new Tile(row, col, tileImage, new Rectangle(col * Global.TILE_SIZE, row * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE));
						} else {
							mapTiles[row][col] = new Tile(row, col, tileImage);
						}
					}
				}

				row++;
			}
			br.close();

			System.out.println("Map initialized");
		} catch (Exception e) {
			System.out.println("ERROR: could not load file: " + filePath);
			System.err.println(e);
		}
	}

	public void draw(Graphics2D g) {

		for (int row = 0; row < Global.WORLD_ROW_NUM; row++) {
			for (int col = 0; col < Global.WORLD_COL_NUM; col++) {
				if (mapTiles[row][col] != null) {
					mapTiles[row][col].draw(g, col, row);
				}
			}
		}
	}

}