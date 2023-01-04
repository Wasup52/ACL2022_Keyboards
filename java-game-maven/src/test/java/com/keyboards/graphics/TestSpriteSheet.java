package com.keyboards.graphics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSpriteSheet {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testloadSprite() {
		SpriteSheet s = new SpriteSheet("res/ghost/idle-left.png", 32, 32);
		assertNotNull(s.loadSprite("res/ghost/idle-left.png"));
		System.out.println("type s.loadSprite : " + s.loadSprite("res/ghost/idle-left.png").getClass().getSimpleName());
	}
	@Test
	public void testloadSpriteArray() {
		SpriteSheet s = new SpriteSheet("res/ghost/idle-left.png", 32, 32);
		s.loadSpriteArray();
		assertNotNull(s.getSpriteArray());
		System.out.println("type s.spriteArray : " + s.getSpriteArray().getClass().getSimpleName());
		
	}

}
