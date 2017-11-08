package net.clockworkgiant.entities.mob;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.clockworkgiant.entities.mob.specifics.race.Race;
import net.clockworkgiant.entities.mob.specifics.BasicClass;
import net.clockworkgiant.entities.mob.specifics.Classes;
import net.clockworkgiant.entities.mob.specifics.Skill;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Animation;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.ui.BattleUI;
import net.clockworkgiant.utils.Vector2I;
import net.clockworkgiant.worlds.Node;

public class Player extends Mob {
	
	private boolean isMain = false, center = false, lockCheck = false, endCheck = false, attackActive = false;
	private float gridX, gridY;
	private BattleUI battleUI;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Mob.DEFAULT_MOB_WIDTH, Mob.DEFAULT_MOB_HEIGHT);
		
		this.group = 'p';
		this.groupID = 0;
		this.speed = Mob.DEFAULT_SPEED * 2;
		this.health = Mob.DEFAULT_HEALTH + this.hpMod;
		
		//Idle animations
		this.anIN = new Animation(75, Assets.riku, 8 * 0);
		this.anINE = new Animation(75, Assets.riku, 8 * 1);
		this.anIE = new Animation(75, Assets.riku, 8 * 2);
		this.anISE = new Animation(75, Assets.riku, 8 * 3);
		this.anIS = new Animation(75, Assets.riku, 8 * 4);
		this.anISW = new Animation(75, Assets.riku, 8 * 5);
		this.anIW = new Animation(75, Assets.riku, 8 * 6);
		this.anINW = new Animation(75, Assets.riku, 8 * 7);

		//Running/Movement animation
		this.anRN = new Animation(75, Assets.riku, 8 * 8);
		this.anRNE = new Animation(75, Assets.riku, 8 * 9);
		this.anRE = new Animation(75, Assets.riku, 8 * 10);
		this.anRSE = new Animation(75, Assets.riku, 8 * 11);
		this.anRS = new Animation(75, Assets.riku, 8 * 12);
		this.anRSW = new Animation(75, Assets.riku, 8 * 13);
		this.anRW = new Animation(75, Assets.riku, 8 * 14);
		this.anRNW = new Animation(75, Assets.riku, 8 * 15);
		
//don't forget - move this to mob
		this.race = "Dummy";
		new Race().setRaceStats(this);
		this.generateStats();
		
		this.classes = new Classes(handler, this, "pyromancer");
		
		this.battleUI = new BattleUI(handler, this);
		
	}

	@Override
	public void update() {
		
		//Lock camera on character
		if(!handler.getKeyManager().c_LOCK && lockCheck) {
			lockCheck = false;
		}else if(handler.getKeyManager().c_LOCK && active && !center && !lockCheck) {
			center = true;
			handler.getGameCamera().setLock(true);
			lockCheck = true;
		}else if(handler.getKeyManager().c_LOCK && active && center && !lockCheck) {
			center = false;
			handler.getGameCamera().setLock(false);
			lockCheck = true;
		}else if(!active && center) {
			center = false;
			lockCheck = false;
		}
		
		//Center camera on character
		if(handler.getKeyManager().c_SNAP || (center && active)) {
			handler.getGameCamera().centerOnEntity(this);
		}
		
		
		//Force end turn
		if(handler.getKeyManager().j_BUTTON && !endCheck) {
			this.endTurn = true;
			this.endCheck = true;
		}else if(!handler.getKeyManager().j_BUTTON && endCheck){
			this.endCheck = false;
		}

		Rescale();
		//Move only if "active character" or if ability circle is closed
		if(active || !skillActive) {
			getInput();
			move();
		}

		/*
		System.out.println("Update gridX = " + (x / width) + " || gridY = " + (y / height));
		System.out.println("Update x = " + x + " || y = " + y);
		System.out.println("Update a = " + a + " || b = " + b);
		System.out.println("/|\\");
		*/
	}
	
	private void getInput() {
		//Move to movement
		//Leave only one movement control method (A*)
		
		xMove = 0;
		yMove = 0;

		if(handler.getWorld().isBattle() && vGrid && movement) {
			if(path != null && handler.getKeyManager().p_BUTTON) {
				path = null;
				nodeIndex = -1;
			}else if(handler.getMouseManager().isLeftPressed() || path != null) {
				if(movementGrid == null) {
					movementLimit();
				}
				if(path == null && checkGrid(handler.getMouseTX(), handler.getMouseTY()) && !handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid()) {
					x = ((int)((x + Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH)) * Tile.T_WIDTH;
					y = ((int)((y + 3 * Tile.T_HEIGHT / 4.0f) / Tile.T_HEIGHT)) * Tile.T_HEIGHT;
					path = aStar.findPath((new Vector2I((int)(x / Tile.T_WIDTH),(int)(y / Tile.T_HEIGHT))), (new Vector2I(handler.getMouseTX(), handler.getMouseTY())), this);
					if(path != null) {
						nodeIndex = path.size() - 1;
					}
				}
				if(path != null && nodeIndex >= 0 && 
						(!path.get(0).getTile().equal(new Vector2I((int)(x / Tile.T_WIDTH),(int)(y / Tile.T_HEIGHT))) ||
						!path.get(0).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH - scale) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT - scale) / Tile.T_HEIGHT))))) {
					
					if(path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.05) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT))) &&
							path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT)))) {
						
						x = ((int)((x + Tile.T_WIDTH / 2.0f) / Tile.T_WIDTH)) * Tile.T_WIDTH;
						y = ((int)((y + 3 * Tile.T_HEIGHT / 4.0f) / Tile.T_HEIGHT)) * Tile.T_HEIGHT;
						nodeIndex--;
					}
					if((nodeIndex >= 0 &&
							(!path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.05) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT))) ||
							!path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT)))))) {
						if(path.get(nodeIndex).getTile().getX() > ((int)((x + Tile.T_WIDTH * 0.05)  / Tile.T_WIDTH)) ||
								path.get(nodeIndex).getTile().getX() > ((int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH))) {
							xMove = speed;
						}else if(path.get(nodeIndex).getTile().getX() < ((int)((x + Tile.T_WIDTH * 0.05)  / Tile.T_WIDTH)) ||
								path.get(nodeIndex).getTile().getX() < ((int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH))) {
							xMove = -speed;
						}
						if(path.get(nodeIndex).getTile().getY() > ((int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT)) ||
								path.get(nodeIndex).getTile().getY() > ((int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT))) {
							yMove = speed;
						}else if(path.get(nodeIndex).getTile().getY() < ((int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT)) ||
								path.get(nodeIndex).getTile().getY() < ((int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT))) {
							yMove = -speed;
						}
						
						/*
						System.out.println("Node index " + nodeIndex + " | node X " + path.get(nodeIndex).getTile().getX() + " | node Y" + path.get(nodeIndex).getTile().getY());
						System.out.println("Current upper x " + (int)((x + Tile.T_WIDTH * 0.05) / Tile.T_WIDTH) + " | y " + (int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT) + " | check result " + path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.05) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.05) / Tile.T_HEIGHT))));
						System.out.println("Current lower x " + (int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH) + " | y " + (int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT) + " | check result " + path.get(nodeIndex).getTile().equal(new Vector2I((int)((x + Tile.T_WIDTH * 0.95) / Tile.T_WIDTH),(int)((y + Tile.T_HEIGHT * 0.95) / Tile.T_HEIGHT))));
						*/
						
						if(xMove > 0 && yMove == 0) {
							facing = "E";
						}else if(xMove < 0 && yMove == 0) {
							facing = "W";
						}else if(yMove > 0 && xMove == 0) {
							facing = "S";
						}else if(yMove < 0 && xMove == 0) {
							facing = "N";
						}else if(xMove > 0 && yMove > 0) {
							facing = "SE";
						}else if(xMove > 0 && yMove < 0) {
							facing = "NE";
						}else if(xMove < 0 && yMove > 0) {
							facing = "SW";
						}else if(xMove < 0 && yMove < 0) {
							facing = "NW";
						}
					}
				}else {
					if(path != null) {
						if(path.size() > 0)
						movement = false;
					}
					path = null;
					nodeIndex = -1;
				}
			}
		}else if(!handler.getWorld().isBattle()) {
			if(handler.getKeyManager().left && !handler.getKeyManager().up && !handler.getKeyManager().down) {
				xMove = -speed;
				facing = "W";
			}else if(handler.getKeyManager().right && !handler.getKeyManager().down && !handler.getKeyManager().up) {
				xMove = speed;
				facing = "E";
			}else if(handler.getKeyManager().up && !handler.getKeyManager().left && !handler.getKeyManager().right) {
				yMove = -speed;
				facing = "N";
			}else if(handler.getKeyManager().down  && !handler.getKeyManager().left && !handler.getKeyManager().right) {
				yMove = speed;
				facing = "S";
			}else if(handler.getKeyManager().left && handler.getKeyManager().up) {
				xMove = (float) (-speed / Math.sqrt(2.0));
				yMove = (float) (-speed / Math.sqrt(2.0));
				facing = "NW";
			}else if(handler.getKeyManager().right && handler.getKeyManager().up) {
				xMove = (float) (speed / Math.sqrt(2.0));
				yMove = (float) (-speed / Math.sqrt(2.0));
				facing = "NE";
			}else if(handler.getKeyManager().down && handler.getKeyManager().right) {
				xMove = (float) (speed / Math.sqrt(2.0));
				yMove = (float) (speed / Math.sqrt(2.0));
				facing = "SE";
			}else if(handler.getKeyManager().down && handler.getKeyManager().left) {
				xMove = (float) (-speed / Math.sqrt(2.0));
				yMove = (float) (speed / Math.sqrt(2.0));
				facing = "SW";
			}
		}
	}

	@Override
	public void render(Graphics g) {
		Rescale();
		animationUpdate();
		g.drawImage(returnCAnimFrame(), (int) (a - handler.getGameCamera().getxOffset()), 
					(int) (b - handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void renderGrid(Graphics g) {
		if(handler.getWorld().isBattle() && active && movementGrid != null && vGrid && movement) {
			for(Node n: movementGrid) {
				gridX = (n.getTile().getX() * Tile.T_WIDTH - n.getTile().getY() * Tile.T_HEIGHT) / 2.0f;
				gridY = (n.getTile().getY() * Tile.T_HEIGHT + n.getTile().getX() * Tile.T_WIDTH) / 4.0f;
				
				//System.out.println("Grid x " + n.getTile().getX() + " | y " + n.getTile().getY());
				
					g.drawImage(Assets.move, (int) (gridX - handler.getGameCamera().getxOffset()), 
							(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
			
				
			}
			if(!handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid()) {
				gridX = (handler.getMouseTX() * Tile.T_WIDTH - handler.getMouseTY() * Tile.T_HEIGHT) / 2.0f;
				gridY = (handler.getMouseTY() * Tile.T_HEIGHT + handler.getMouseTX() * Tile.T_WIDTH) / 4.0f;
				g.drawImage(Assets.AOE, (int) (gridX - handler.getGameCamera().getxOffset()), 
						(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
			}
		}
		if(skillActive) {
			if(attackActive) {
				attackRender(g);
				return;
			}
			for(BasicClass basic: classes.getClassList()) {
				for(Skill skill: basic.getSkillList()) {
					if(skill.isActive()) {
						skill.render(g);
						return;
					}
				}
			}
		}
	}
	
	public void attackUpdate() {
		//System.out.println("Attack update");
		if(handler.getMouseManager().keyJustReleased(MouseEvent.BUTTON1)) {
			for(Node node: attackGrid) {
				if(node.getTile().getX() == handler.getMouseTX() 
						&& node.getTile().getY() == handler.getMouseTY()
						&& !handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid()) {
						//search target zone for "skill victim" and throw effects at it
					for(Mob mob: handler.getWorld().getControl().getActiveList()) {
						if(mob.checkPosition(handler.getMouseTX(), handler.getMouseTY())) {
							mob.catchEffect(this.mobInventory.getAttackEffects());
							this.skillActive = false;
							this.battleUI.setEndTurn(true);
							this.attackActive = false;
						}
					}
				}
			}
		}

		if(handler.getMouseManager().isRightPressed()) {
			this.skillActive = false;
			this.attackActive = false;
		}
	}
	
	public void attackRender(Graphics g) {
		//System.out.println("Attack render");
		for(Node n: attackGrid) {
			gridX = (n.getTile().getX() * Tile.T_WIDTH - n.getTile().getY() * Tile.T_HEIGHT) / 2.0f;
			gridY = (n.getTile().getY() * Tile.T_HEIGHT + n.getTile().getX() * Tile.T_WIDTH) / 4.0f;
			
			g.drawImage(Assets.move, (int) (gridX - handler.getGameCamera().getxOffset()), 
					(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
		}
		
		if(!handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid()) {
			gridX = (handler.getMouseTX() * Tile.T_WIDTH - handler.getMouseTY() * Tile.T_HEIGHT) / 2.0f;
			gridY = (handler.getMouseTY() * Tile.T_HEIGHT + handler.getMouseTX() * Tile.T_WIDTH) / 4.0f;
			g.drawImage(Assets.AOE, (int) (gridX - handler.getGameCamera().getxOffset()), 
					(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
		}
	}
	
	public void attackAction() {
		this.attackActive = true;
		this.skillActive = true;
		attackLimit();
	}
	
	public void defendAction() {
		
	}
	
	public boolean isAttackActive() {
		return attackActive;
	}
	@Override
	public BattleUI getBattleUI() {
		return this.battleUI;
	}

	@Override
	public void battleUIupdate() {
		this.battleUI.update(handler.getWorld().isBattle());
	}

	@Override
	public void battleUIrender(Graphics g) {
		this.battleUI.render(g, handler.getWorld().isBattle());
	}

	public boolean getMain() {
		return isMain;
	}

	public void setMain(boolean isMain) {
		this.isMain = isMain;
	}
	
	
}
