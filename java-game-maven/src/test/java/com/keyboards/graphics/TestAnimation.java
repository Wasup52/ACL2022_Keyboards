package com.keyboards.graphics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAnimation {

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
	public void testConstructeur() {
		SpriteSheet idleLeftSheet = new SpriteSheet("res/ghost/idle-left.png", 32, 32);
		
		
		Animation a = new Animation(idleLeftSheet.getSpriteArray(), 5);
		assertNotNull(a.getSprites());  
	    System.out.println("type object.sprites : " + a.getSprites().getClass().getSimpleName());  
	    assertNotNull(a.getSpriteCounter());  
	    System.out.println("type object.SpritesCounter : " + a.getSpriteCounter());
	    assertNotNull(a.isReversed());  
	    System.out.println("type object.isReversed() : " + a.isReversed()); 
		
	}

}
