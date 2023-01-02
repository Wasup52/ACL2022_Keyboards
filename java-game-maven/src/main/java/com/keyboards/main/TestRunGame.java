package com.keyboards.main;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.keyboards.game.Zombie;

public class TestRunGame {

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
		RunGame game = new RunGame("helpFile.txt");
		assertNotNull(game.mobs);  
	    System.out.println("type object.mobs : " + game.mobs.getClass().getSimpleName());  
	    assertNotNull(game.entities);  
	    System.out.println("type object.entities : " + game.entities.getClass().getSimpleName());  
		
		
	}
	
	@Test
	public void testEvolve() {
		
	}

}
