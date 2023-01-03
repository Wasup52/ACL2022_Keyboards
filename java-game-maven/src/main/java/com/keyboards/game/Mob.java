package com.keyboards.game;

import java.util.ArrayList;
import java.util.List;

import com.keyboards.global.Global;
import com.keyboards.pathfinding.Graph;
import com.keyboards.pathfinding.Node;
import com.keyboards.tile.Tile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

public abstract class Mob extends Character{
    private Node[][] grid;
    
    private Graph<Point> graph;
    
    private Node<Point> startNode;
    private Node<Point> targetNode;
    
    private List<Node<Point>> path;
    private Point nextPoint;
    
    private final Stroke pathStroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    int agroRange;

    public Mob(int col, int row, Tile[][] mapTiles) {
		super(col, row, mapTiles);

        initPathFinding();
	}

    public Mob(int col, int row, Tile[][] mapTiles, boolean hasCollision) {
        super(col, row, mapTiles, hasCollision);

        initPathFinding();
    }

    public Mob(Tile[][] mapTiles) {
		super(mapTiles);

        initPathFinding();
	}

    public Mob(Tile[][] mapTiles, boolean hasCollision) {
		super(mapTiles, hasCollision);

        initPathFinding();
	}

    private void initPathFinding() {
        path = new ArrayList<>();
        
        grid = new Node[Global.WORLD_ROW_NUM][Global.WORLD_COL_NUM];
        
        graph = new Graph<>((start, target, current) -> {
            // --- implement heuristic here ---
            
            // heuristic = manhattan distance
            // int dx = Math.abs(target.getObj().x - current.getObj().x);
            // int dy = Math.abs(target.getObj().y - current.getObj().y);
            // return dx + dy;

            // heuristic = linear distance
            int dx = target.getObj().x - current.getObj().x;
            int dy = target.getObj().y - current.getObj().y;
            return Math.sqrt(dx * dx + dy * dy);
            
            // heuristic = 0 -> equivalent to Dijkstra
            // return 0;
        });
        
        createGrid(mapTiles);
    }

    private void createGrid(Tile[][] mapTiles) {
        // String output = "";
        
        // create a grid of nodes, where each node represents a tile in the map
        for (int y = 0; y < Global.WORLD_ROW_NUM; y++) {
            for (int x = 0; x < Global.WORLD_COL_NUM; x++) {
                int nx = x * Global.TILE_SIZE;
                int ny = y * Global.TILE_SIZE;
                if (mapTiles[y][x].isSolid() && hasCollision) {
                    // output += "solid ";
                    Node node = new Node(new Point(nx, ny));
                    node.setBlocked(true);
                    graph.addNode(node);
                    grid[y][x] = node;
                } else {
                    // output += "empty ";
                    Node node = new Node(new Point(nx, ny));
                    graph.addNode(node);
                    grid[y][x] = node;
                }
            }
            // output += "\n";
        }

        // link all nodes
        
        double diagonalG = Math.sqrt(Global.TILE_SIZE * Global.TILE_SIZE + Global.TILE_SIZE * Global.TILE_SIZE); // diagonal distance between two tiles
        
        for (int y = 0; y < Global.WORLD_ROW_NUM - 1; y++) {
            for (int x = 0; x < Global.WORLD_COL_NUM; x++) {
                // vertical '|'
                Node top = grid[y][x];
                Node bottom = grid[y + 1][x];
                graph.link(top, bottom, Global.TILE_SIZE);
                
                // diagonals 'X'
                if (x < Global.WORLD_COL_NUM - 1) {
                    // diagonal '\'
                    top = grid[y][x];
                    bottom = grid[y + 1][x + 1];
                    graph.link(top, bottom, diagonalG);
                    
                    // diagonal '/'
                    top = grid[y][x + 1];
                    bottom = grid[y + 1][x];
                    graph.link(top, bottom, diagonalG);
                }
            }
        }

        for (int x = 0; x < Global.WORLD_COL_NUM - 1; x++) {
            for (int y = 0; y < Global.WORLD_ROW_NUM; y++) {
                // horizontal '-'
                Node left = grid[y][x];
                Node right = grid[y][x + 1];
                graph.link(left, right, Global.TILE_SIZE);
            }
        }
    }

    private List<Node<Point>> getPath(Entity target) {
        
        if (getRow() >= 0 && getRow() < Global.WORLD_ROW_NUM && getCol() >= 0 && getCol() < Global.WORLD_COL_NUM) {
            startNode = grid[getRow()][getCol()];
            targetNode = grid[target.getRow()][target.getCol()];

            path.clear();
            graph.findPath(startNode, targetNode, path);
            
            return path;
        } else {
            // if the mob is outside the map, return an empty path
            return new ArrayList<>();
        }

    }

    public int getDistance(Entity target) {
        return (int) Math.sqrt(Math.pow(getX() - target.getX(), 2) + Math.pow(getY() - target.getY(), 2));
    }

    public boolean isInAgroRange(Entity target) {
        if (agroRange == 0) { 
            return true;
        } else {
            return getDistance(target) <= agroRange; 
        }
    }

    public void moveTowards(Entity target) {
        // TODO move towards the target
        path = getPath(target);
        // System.out.println("moving towards " + target);

        if (path.size() > 1) {
            Node<Point> nextNode = path.get(1);
            this.nextPoint = nextNode.getObj();
            int nextX = nextPoint.x;
            int nextY = nextPoint.y;
            int dx = nextX - worldPosition.x;
            int dy = nextY - worldPosition.y;
            if (dx > 0) {
                moveRight();
            }
            if (dx < 0) {
                moveLeft();
            }
            if (dy > 0) {
                moveDown();
            } 
            if (dy < 0) {
                moveUp();
            }
        } else {
            idle();
        }
    }

    private void drawNode(Graphics2D g, Node<Point> node, Point playerWorldPos, Point playerScreenPos) {
        Color color = Color.BLACK;
        
        switch (node.getState()) {
            case OPEN:
                // translucent cyan
                color = new Color(0, 255, 255, 100);
                break;
            case CLOSED:
                // translucent orange
                color = new Color(255, 165, 0, 100);
                break;
            case UNVISITED:
                // transparent color
                color = new Color(0, 0, 0, 0);
                break;
        }

        if (node == startNode) {
            // translucent red
            color = new Color(0, 0, 255, 100);
        }
        else if (node == targetNode) {
            // translucent red
            color = new Color(255, 0, 0, 100);
        }
        else if (node.isBlocked()) {
            // translucent black
            color = new Color(0, 0, 0, 100);
        }

        g.setColor(color);
        int sceenX = node.getObj().x - playerWorldPos.x + playerScreenPos.x;
        int sceenY = node.getObj().y - playerWorldPos.y + playerScreenPos.y;
        g.fillRect(sceenX, sceenY, Global.TILE_SIZE, Global.TILE_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(sceenX, sceenY, Global.TILE_SIZE, Global.TILE_SIZE);
    }
    
    private void drawPath(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
        Stroke originalStroke = g.getStroke();
        g.setColor(Color.BLUE);
        g.setStroke(pathStroke);
        for (int i = 0; i < path.size() - 1; i++) {
            Node<Point> a = path.get(i);
            Node<Point> b = path.get(i + 1);
            int x1 = a.getObj().x + Global.TILE_SIZE / 2;
            int y1 = a.getObj().y + Global.TILE_SIZE / 2;
            int screenX1 = x1 - playerWorldPos.x + playerScreenPos.x;
            int screenY1 = y1 - playerWorldPos.y + playerScreenPos.y;
            int x2 = b.getObj().x + Global.TILE_SIZE / 2;
            int y2 = b.getObj().y + Global.TILE_SIZE / 2;
            int screenX2 = x2 - playerWorldPos.x + playerScreenPos.x;
            int screenY2 = y2 - playerWorldPos.y + playerScreenPos.y;
            g.drawLine(screenX1, screenY1, screenX2, screenY2);
        }
        g.setStroke(originalStroke);
    }

    public void update(Player player) {
        if (!isDead()) {
            if (isInAgroRange(player)) {
                moveTowards(player);
            } else {
                idle();
            }

            if (canAttack(player)) {
                if (!Global.PLAYER_INVINCIBLE) {
                    System.out.println("attacking player");
                    attack(player);
                } else {
                    System.out.println("player is invincible");
                }
            }
        }
    }

    public void draw(Graphics2D g, Point playerWorldPos, Point playerScreenPos) {
        if (Global.DEBUG && path != null) {
            // draw the nodes (diffrent colors for different states (open, closed, unvisited, start, target, blocked))
            for (Node<Point> node : graph.getNodes()) {
                drawNode(g, node, playerWorldPos, playerScreenPos);
            }

            drawPath(g, playerWorldPos, playerScreenPos);
    
            // draw the next point
            if (nextPoint != null) {
                int screenX = nextPoint.x - playerWorldPos.x + playerScreenPos.x;
                int screenY = nextPoint.y - playerWorldPos.y + playerScreenPos.y;
    
                g.setColor(Color.RED);
                int r = 5;
                g.fillOval(screenX + Global.TILE_SIZE / 2 - r / 2, screenY + Global.TILE_SIZE / 2 - r / 2, r, r);
            }
        }


        super.draw(g, playerWorldPos, playerScreenPos);
    }
    
}
