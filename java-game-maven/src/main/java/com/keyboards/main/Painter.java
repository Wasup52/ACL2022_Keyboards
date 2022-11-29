package com.keyboards.main;

import java.awt.Color;
import java.awt.Graphics2D;
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
	
	
	private void drawGrid(Graphics2D g) {
        Stroke oldStroke = g.getStroke();

        g.setStroke(tileStroke);
        g.setColor(new Color(78, 108, 80));
        for (int y = 0; y < Global.ROW_NUM; y++) {
            for (int x = 0; x < Global.COL_NUM; x++) {
                int nx = x * Global.TILE_SIZE;
                int ny = y * Global.TILE_SIZE;
                g.drawRect(nx, ny, Global.TILE_SIZE, Global.TILE_SIZE);
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
        drawGrid(g);

		// draw the tiles
		game.tileManager.draw(g);
		
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
					((Item) e).draw(g);
				} else {
					// remove the item from the entities array if it's in the inventory
					game.entities.remove(i);
					i--;
				}
			} else {
				e.draw(g);
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
	}

	@Override
	public int getWidth() {
		return Global.WIDTH;
	}

	@Override
	public int getHeight() {
		return Global.HEIGHT;
	}
}
