package com.keyboards.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
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
	Font font = new Font("Arial", Font.BOLD, 20);
	Rectangle screen = new Rectangle(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);

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

	public void drawCenteredString(Graphics2D g, String text, Rectangle rect, Font font) {
		// Get the FontMetrics
		FontMetrics metrics = g.getFontMetrics(font);
		// Determine the X coordinate for the text
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		// Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		// Set the font
		g.setFont(font);
		// Draw the String
		g.drawString(text, x, y);
	}

	public void drawStartScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		// draw "Press Enter to start" in the middle of the screen
		drawCenteredString(g, "Press Enter to start", screen, font);
	}

	public void drawWiningScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		// draw "You win!" in the middle of the screen
		drawCenteredString(g, "You win!", screen, font);
	}

	public void drawGameOverScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		// draw "Game Over !" in the middle of the screen
		drawCenteredString(g, "Game Over !", screen, font);
	}

	public void drawPauseScreen(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Global.SCREEN_WIDTH, Global.SCREEN_HEIGHT);
		g.setColor(Color.WHITE);
		// draw "Pause (press ESC to resume)" in the middle of the screen
		drawCenteredString(g, "Pause (press ESC to resume)", screen, font);
	}

	/**
	 * methode  redefinie de Afficheur retourne une image du jeu
	 */
	@Override
	public void draw(BufferedImage im) {
		Graphics2D g = (Graphics2D) im.getGraphics();

		// draw the tiles
		game.tileManager.draw(g, game.player.worldPosition, game.player.screenPosition);
		
		// draw grid for tests
        // drawGrid(g, game.player.worldPosition, game.player.screenPosition);
		
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

		if (!game.isStarted) {
			drawStartScreen(g);
			// System.out.println("Drawn start screen");
		}

		if (game.isPaused) {
			drawPauseScreen(g);
			// System.out.println("Drawn pause screen");
		}

		if (game.isGameOver) {
			drawGameOverScreen(g);
			// System.out.println("Drawn game over screen");
			this.game.isDone = true;
		}

		if (game.isFinished) {
			drawWiningScreen(g);
			// System.out.println("Drawn wining screen");
			this.game.isDone = true;
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
