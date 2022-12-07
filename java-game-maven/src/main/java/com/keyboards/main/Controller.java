package com.keyboards.main;

import java.awt.event.KeyEvent;
import java.util.HashMap;


import com.keyboards.engine.GameController;

public class Controller implements GameController{

	/**
	 * HasMap of pressed keys
	 */
	private HashMap<String, Boolean> commands = new HashMap<String, Boolean>();

	/**
	 * Constructor for the controller
	 */
	public Controller() {
		commands.put("UP", false);
		commands.put("DOWN", false);
		commands.put("LEFT", false);
		commands.put("RIGHT", false);
		commands.put("SHIFT", false);
		commands.put("ATTACK", false);
		commands.put("ESCAPE", false);
		commands.put("TAB", false);
		commands.put("INTERACT", false);
	}

	/**
	 * Returns true only if no commands are pressed
	 * 
	 * @return bool
	 */
	public static boolean isIdle(HashMap<String, Boolean> commands) {
		for (String key : commands.keySet()) {
			if (key != "SHIFT" && commands.get(key)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the commands HashMap
	 * 
	 * @return Commands pressed by the user
	 */
	public HashMap<String, Boolean> getCommand() {
		return this.commands;
	}

	@Override
	/**
	 * Update the commands HashMap when a key is pressed by the user
	 */
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_Z: commands.put("UP", true); break;
			case KeyEvent.VK_S: commands.put("DOWN", true); break;
			case KeyEvent.VK_Q: commands.put("LEFT", true); break;
			case KeyEvent.VK_D: commands.put("RIGHT", true); break;
			case KeyEvent.VK_SHIFT: commands.put("SHIFT", true); break;
			case KeyEvent.VK_SPACE: commands.put("ATTACK", true); break;
			case KeyEvent.VK_ESCAPE: commands.put("ESCAPE", true); break;
			case KeyEvent.VK_TAB: commands.put("TAB", true); break;
			case KeyEvent.VK_E: commands.put("INTERACT", true); break;
		}

	}

	@Override
	/**
	 * Update the commands HashMap when a key is released by the user
	 */
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {
			case KeyEvent.VK_Z: commands.put("UP", false); break;
			case KeyEvent.VK_S: commands.put("DOWN", false); break;
			case KeyEvent.VK_Q: commands.put("LEFT", false); break;
			case KeyEvent.VK_D: commands.put("RIGHT", false); break;
			case KeyEvent.VK_SHIFT: commands.put("SHIFT", false); break;
			case KeyEvent.VK_SPACE: commands.put("ATTACK", false); break;
			case KeyEvent.VK_ESCAPE: commands.put("ESCAPE", false); break;
			case KeyEvent.VK_TAB: commands.put("TAB", false); break;
			case KeyEvent.VK_E: commands.put("INTERACT", false); break;
		}

	}

	@Override
	/**
	 * Do nothing
	 */
	public void keyTyped(KeyEvent e) {

	}
}