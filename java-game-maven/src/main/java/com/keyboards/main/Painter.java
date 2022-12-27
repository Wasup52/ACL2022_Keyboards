package com.keyboards.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Comparator;

import com.keyboards.engine.GamePainter;
import com.keyboards.game.Chest;
import com.keyboards.game.Entity;
import com.keyboards.game.Item;
import com.keyboards.game.Player;
import com.keyboards.global.Global;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class Painter implements GamePainter {
	
	protected RunGame game;

	// strok with a width of 2, normal cap and join
    private final Stroke tileStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
	
	/**
	 * la taille des cases
	 */

	/**
	 * appelle constructeur parent
	 * 
	 * @param game
	 *            le jeutest a afficher
	 */
	public Painter(RunGame game) {
		this.game = game;
	}
	
	
	private void drawGrid(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
        Stroke oldStroke = g.getStroke();

        g.setStroke(tileStroke);
        g.setColor(new Color(78, 108, 80));
        for (int y = 0; y < Global.WORLD_ROW_NUM; y++) {
            for (int x = 0; x < Global.WORLD_COL_NUM; x++) {
                int screenX = x * Global.TILE_SIZE - playerWorldPos.x + playerScreenPos.x;
                int screenY = y * Global.TILE_SIZE - playerWorldPos.y + playerScreenPos.y;
                g.drawRect(screenX, screenY, Global.TILE_SIZE, Global.TILE_SIZE);
            }
        }

        g.setStroke(oldStroke);
    }

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D g = (Graphics2D) im.getGraphics();

		// draw grid for tests
        drawGrid(g, game.player.worldPosition, game.player.screenPosition);

		// draw the tiles
		game.tileManager.draw(g, game.player.worldPosition, game.player.screenPosition);
		
		// sort the entities by y position to draw them in the right order
		Collections.sort(game.entities, new Comparator<Entity>() {
			@Override
			public int compare(Entity e1, Entity e2) {
				return e1.getY() - e2.getY();
			}
		});
		
		// draw the entities
		for (int i = 0; i < game.entities.size(); i++) {
			Entity e = game.entities.get(i);
			if (e instanceof Item) {
				// don't draw the item on the board if it's in the inventory
				if (!((Item) e).isInInventory) {
					((Item) e).draw(g, game.player.worldPosition, game.player.screenPosition);
				} else {
					// remove the item from the entities array if it's in the inventory
					game.entities.remove(i);
					i--;
				}
			} else {
				e.draw(g, game.player.worldPosition, game.player.screenPosition);
			}
		}

		//draw UI
		game.ui.drawPlayerLife(g, game.player);
		
		// draw the opened inventory at the end for it to be on top of everything
		if (game.inventoryOpen) {
			for (Entity e : game.entities) {
				if (e.hasInventory) {
					if (e instanceof Player && ((Player) e).isInventoryOpen()) { ((Player) e).drawInventory(g); }
					if (e instanceof Chest && ((Chest) e).isOpen()) { ((Chest) e).drawInventory(g); }
				}
			}
		}

		// if the game is paused, draw a black rectangle over the screen
		if (game.isPaused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		}
	}

	@Override
	public int getWidth() {
		return Global.SCREEN_WIDTH;
	}

	@Override
	public int getHeight() {
		return Global.SCREEN_HEIGHT;
	}
}
