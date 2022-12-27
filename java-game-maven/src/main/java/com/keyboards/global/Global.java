package com.keyboards.global;

public class Global {
	public static final int ORIGINAL_TILE_SIZE = 16;
	public static final int SCALE = 3;
	public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

	public static final int SCREEN_COL_NUM = 16;
	public static final int SCREEN_ROW_NUM = 12;
	public static final int SCREEN_WIDTH = SCREEN_COL_NUM * TILE_SIZE;
	public static final int SCREEN_HEIGHT = SCREEN_ROW_NUM * TILE_SIZE;
	
	public static final int WORLD_COL_NUM = 50;
	public static final int WORLD_ROW_NUM = 50;
	public static final int WORLD_WIDTH = WORLD_COL_NUM * TILE_SIZE;
	public static final int WORLD_HEIGHT = WORLD_ROW_NUM * TILE_SIZE;
	
	public static final int FPS = 60;
	public static final int KEY_COOLDOWN = 15; // in frames

	public static boolean DEBUG = false;
	public static boolean PLAYER_INVINCIBLE = false;
}