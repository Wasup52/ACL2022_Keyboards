package com.keyboards.game;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;

import com.keyboards.global.Global;

public class Inventory {
    
    private Item[][] inventory;
    private boolean isOpen = false;
    private boolean isFull = false;

    private Entity owner;

    private final int BETWEEN_ITEM_SPACING = 4;
    private final int BORDER_SPACING = 8;
    private final int TRANSPARENCY = 200;

    private int total_width;
    private int total_height;

    private Point position;

    public Inventory(Entity owner, int capacity_row, int capacity_col) {
        this.owner = owner;
        this.inventory = new Item[capacity_row][capacity_col];

        this.total_width = Global.TILE_SIZE * inventory[0].length + BORDER_SPACING*2 + BETWEEN_ITEM_SPACING*(inventory[0].length-1);
        this.total_height = Global.TILE_SIZE * inventory.length + BORDER_SPACING*2 + BETWEEN_ITEM_SPACING*(inventory.length-1);

        this.position = new Point(Global.SCREEN_WIDTH/2 - total_width/2, Global.SCREEN_HEIGHT/2 - total_height/2);
    }

    public void addItem(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                // put the item in the first empty slot
                if (inventory[i][j] == null) {
                    item.setIsInInventory(true);
                    inventory[i][j] = item;

                    if (i == inventory.length - 1 && j == inventory[i].length - 1) {
                        this.isFull = true;
                    }

                    return;
                }
            }
        }
    }

    private void removeItem(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {
                if (inventory[i][j] == item) {
                    inventory[i][j] = null;
                    this.isFull = false;
                    return;
                }
            }
        }
    }

    public boolean isOpen() { return isOpen; }
    public boolean isFull() { return isFull; }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }

    private Item getClickedItem(int mouseX, int mouseY) {
        if (isOpen) {
            int itemWidth = Global.TILE_SIZE;
            int itemHeight = Global.TILE_SIZE;

            for (int i = 0; i < inventory.length; i++) {
                for (int j = 0; j < inventory[i].length; j++) {
                    if (inventory[i][j] != null) {
                        int x = position.x + BORDER_SPACING + (itemWidth + BETWEEN_ITEM_SPACING) * j;
                        int y = position.y + BORDER_SPACING + (itemHeight + BETWEEN_ITEM_SPACING) * i;
                        
                        if (mouseX >= x && mouseX <= x + itemWidth && mouseY >= y && mouseY <= y + itemHeight) {
                            return inventory[i][j];
                        }
                    }
                }
            }
            return null; // no item was clicked
        } else {
            return null; // inventory is closed
        }
    }

    public void useClickedItem(int mouseX, int mouseY) {
        Item item = getClickedItem(mouseX, mouseY);
        if (item != null) {
            item.use((Character) owner);
            removeItem(item);
        }
    }

    public void transfertClickedItem(int mouseX, int mouseY, Inventory otherInventory) {
        Item item = getClickedItem(mouseX, mouseY);
        if (item != null) {
            otherInventory.addItem(item);
            removeItem(item);
        }
    }

    public void draw(Graphics2D g) {
        // draw a centered grid of capacity_row x capacity_col squares of size Global.TILE_SIZE

        int x = position.x;
        int y = position.y;

        // color #c6c6c6
        g.setColor(new Color(198, 198, 198, TRANSPARENCY));
        g.fillRoundRect(x, y, total_width, total_height, 15, 15);
        g.setColor(Color.BLACK);
        Stroke oldStroke = g.getStroke();
        g.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g.drawRoundRect(x, y, total_width, total_height, 15, 15);
        g.setStroke(oldStroke);

        x += BORDER_SPACING;
        y += BORDER_SPACING;

        for (int i = 0; i < inventory.length; i++) {
            for (int j = 0; j < inventory[i].length; j++) {

                // color #8b8b8b
                g.setColor(new Color(139, 139, 139, TRANSPARENCY));
                g.fillRoundRect(x + j * Global.TILE_SIZE, y + i * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE, 15, 15);
                g.setColor(Color.BLACK);
                oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
                g.drawRoundRect(x + j * Global.TILE_SIZE, y + i * Global.TILE_SIZE, Global.TILE_SIZE, Global.TILE_SIZE, 15, 15);
                g.setStroke(oldStroke);

                // if inventory[i][j] is not null, draw the object at the center of the square
                if (inventory[i][j] != null) {
                    inventory[i][j].draw(
                        g, x + j * Global.TILE_SIZE + (Global.TILE_SIZE - inventory[i][j].hitbox.width)/2,
                        y + i * Global.TILE_SIZE + (Global.TILE_SIZE - inventory[i][j].hitbox.height)/2
                    );
                }

                x += BETWEEN_ITEM_SPACING;
            }
            x = (Global.SCREEN_WIDTH - total_width) / 2 + BORDER_SPACING;
            y += BETWEEN_ITEM_SPACING;
        }

    }
}
