package net.clockworkgiant.worlds;

import java.awt.Graphics;
//import java.util.Random;

import net.clockworkgiant.entities.EntityManager;
import net.clockworkgiant.entities.mob.Player;
import net.clockworkgiant.entities.mob.movement.Control;
import net.clockworkgiant.entities.statics.Statue;
import net.clockworkgiant.entities.statics.Wall;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.items.itemLists.WeaponList;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.utils.Utils;

public class World {
	protected Handler handler;
	private int width, height, spawnX, spawnY;
	private int[][] tiles;
	private boolean battle;
	private Control control;
	
	private EntityManager eManager;
	
	public World(Handler handler, String path) {
		battle = false;
		this.handler = handler;
		control = new Control(handler);
		eManager = new EntityManager(handler);
		loadWorld(path);
		
		eManager.setPlayer(new Player(handler, 400, 400));
		eManager.setPlayer(new Player(handler, 400, 500));
		
		if(eManager.getPlayer() != null) {
			for(int i = 0; i < eManager.getPlayer().size(); i++) {
				eManager.getPlayer().get(i).setX(getSpawnX() * (i + 1));
				eManager.getPlayer().get(i).setY(getSpawnY() * (20 * i + 1));
				eManager.getPlayer().get(i).setActive(false);
			}
		}
		
		eManager.getPlayer().get(1).equipRHand(new WeaponList().getWeapon("dummy_stick"));
		
		eManager.addEntity(new Statue(handler, 18, 30));
		

		for(int i = 0; i < eManager.getPlayer().size(); i++) {
			if(eManager.getPlayer().get(i).getRHand() != null) {
				System.out.println("#" + i + " equipped with...");
				System.out.println(eManager.getPlayer().get(i).getRHand().toString());
			}
		}
		
		if(eManager.getPlayer() != null) {
			for(int i = 0; i < eManager.getPlayer().size(); i++) {
				eManager.getPlayer().get(i).getBattleUI().generateAttackCircle();
			}
		}
	}
	
	public void update() {
/*		if(handler.getKeyManager().k_BUTTON) {
			battle = true;
		}else if(handler.getKeyManager().l_BUTTON) {
			battle = false;
		}
*/		
		
		// In need of reworking - problem with static entities
		// Proposition: separate list for static entities and each of mob groups
		// Possible "room by room" static entity list
		if(battle) {
			if(control.getActiveList().size() > 0) {
				control.update();
			}else if(control.getActiveList() == null || control.getActiveList().size() <= 0) {
				control.setActiveList(eManager.getPlayer());
			}
		}else {
			eManager.update();
		}
		
		if(!battle) battle = true;
	}
	
	public void render(Graphics g) {
		
		float a, b;
		
		// Must be redone - find another way to limit render
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				a = (x * Tile.T_WIDTH - y * Tile.T_HEIGHT) / 2;
				b = (y * Tile.T_HEIGHT + x * Tile.T_WIDTH) / 4;
				if(((a - handler.getGameCamera().getxOffset() + Tile.T_WIDTH) > 0) 
						&& ((a - handler.getGameCamera().getxOffset()) < handler.getWidth()) 
						&& ((b - handler.getGameCamera().getyOffset() + Tile.T_HEIGHT) > 0) 
						&& ((b - handler.getGameCamera().getyOffset()) < handler.getHeight())) {
						getTile(x, y).render(g, (int) (a - handler.getGameCamera().getxOffset()), 
								(int) (b - handler.getGameCamera().getyOffset()));
					
				}
			}
		}
		
		// What do I do with this shit?...
		
		/*int isoTX, isoTY, carTX, carTY, ax = 0, ay = 0;
		float camISOX = handler.getGameCamera().getxOffset();
		float camISOY = handler.getGameCamera().getyOffset();
		int camTX = (int) ((2 * camISOY + camISOX) / 2.0f / Tile.T_WIDTH);
		int camTY = (int) ((2 * camISOY - camISOX) / 2.0f / Tile.T_HEIGHT);
		int width_tiles = handler.getWidth() / Tile.T_WIDTH;
		int hieght_tiles = handler.getHeight() / Tile.T_HEIGHT * 2;
		
		for(int y = 0; y < hieght_tiles; y++) {
			for(int x = 0; x < width_tiles; x++) {
				carTX = (x + y + ax) - camTX;
				carTY = (y - x + ay) - camTY;

				isoTX = (carTX * Tile.T_WIDTH - carTY * Tile.T_HEIGHT) / 2;
				isoTY = (carTY * Tile.T_HEIGHT + carTX * Tile.T_WIDTH) / 4;
				
				if((carTY >= 0) && (carTX >= 0)) {
					getTile(carTX, carTY).render(g, isoTX, isoTY);
				}
			}
			if(y % 2 == 0) {
				ay++;
			}
			else {
				ax++;
			}
		}*/
		
		
		eManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= width)
			return Tile.blank;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.blank;
		return t;
	}
	
	public void loadWorld(String path) {
		int token;
		
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				token = Utils.parseInt(tokens[(x + y * width) + 4]);
				
				//Old tile randomizer - do we need this?
/*				if(token == 0) {
					token = (int)(new Random().nextInt(2) * (new Random().nextInt(6) + 10));
				}
*/				
				tiles[x][y] = token;
				
				//Adding wall, door and other scenery in static entity list
				//Used to change render order
				if(getTile(x, y).getID() == 1) {
					eManager.addEntity(new Wall(handler, x, y));
				}
			}
		}
	}

	public float getSpawnX() {
		return (spawnX) * Tile.T_WIDTH * 1.0f;
	}

	public float getSpawnY() {
		return (spawnY) * Tile.T_HEIGHT * 1.0f;
	}

	public int getISOWidth() {
		return (width * Tile.T_WIDTH) / 2;
	}

	public int getISOHeight() {
		return (height * Tile.T_HEIGHT + width * Tile.T_WIDTH) / 4;
	}

	public EntityManager geteManager() {
		return eManager;
	}

	public boolean isBattle() {
		return battle;
	}

	public void setBattle(boolean battle) {
		this.battle = battle;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}
	
	
}
