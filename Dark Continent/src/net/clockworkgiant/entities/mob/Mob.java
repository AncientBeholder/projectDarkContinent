package net.clockworkgiant.entities.mob;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.clockworkgiant.entities.Entity;
import net.clockworkgiant.entities.mob.movement.AStarMovement;
import net.clockworkgiant.entities.mob.movement.DijkstraAOE;
import net.clockworkgiant.entities.mob.specifics.Classes;
import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Animation;
import net.clockworkgiant.inventory.MobInventory;
import net.clockworkgiant.items.Item;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.ui.BattleUI;
import net.clockworkgiant.worlds.Node;

public abstract class Mob extends Entity {
	
	public static final int DEFAULT_HEALTH = 10;
	public static final float DEFAULT_SPEED = 4.0f;
	public static final int DEFAULT_MOB_WIDTH = 64,  DEFAULT_MOB_HEIGHT = 64;
	
	protected Animation anRN, anRNE, anRE, anRSE, anRS, anRSW, anRW, anRNW, anIN, anINE, anIE, anISE, anIS,
	anISW, anIW, anINW;
	
	protected boolean endTurn = false;
	protected boolean active = false, movement = true, vGrid = true, skillActive = false;
	
	protected Effect effect = new Effect();
	
	protected char group; // p - player party, e - enemy, n - neutral, a - allied
	protected short groupID;
	
	protected String race, gender;
	protected int health, soul, constitution, strength, dexterity, intellect, wisdom, charisma,
		luck, madness, priority, movementRange, mRange, attackRange, aRange, level, hit_evade_base;
	protected int hpMod = 0, spMod = 0, rangeMod = 0, attackRangeMod = 0;
	protected float speed, xMove, yMove, a, b;
	protected double scale;
	protected String facing;
	
	protected AStarMovement aStar = null;
	protected DijkstraAOE dAOE = null;
	protected List<Node> path, movementGrid, attackGrid;
	protected int nodeIndex;
	protected Classes classes;
	protected ArrayList<Effect> underEffect;
	
	protected MobInventory mobInventory;

	public String getFacing() {
		return facing;
	}

	public Mob(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		mobInventory = new MobInventory(this);
		aStar = new AStarMovement(handler);
		dAOE = new DijkstraAOE(handler);
		underEffect = new ArrayList<Effect>();
		movementRange = 10;
		mRange = movementRange;
		scale = Handler.getSCALE();
	}
	
	public void move() {
		//Calculation of collision corners
		int tx1 = (int) ((x + width * 0.2 + xMove) / Tile.T_WIDTH);
		int ty1 = (int) ((y + height * 0.2 + yMove) / Tile.T_HEIGHT);
		int tx2 = (int) ((x + width * 0.8 + xMove) / Tile.T_WIDTH);
		int ty2 = (int) ((y + height * 0.8 + yMove) / Tile.T_HEIGHT);
		
		//Movement
		moveX(tx1, tx2, ty1, ty2);
		moveY(tx1, tx2, ty1, ty2);
		moveXY(tx1, tx2, ty1, ty2);
		
		/*
		System.out.println(facing);
		System.out.println("TX1: " + tx1 + " || TY1:  " + ty1 + " || TX2:  " + tx2 + " || TY2: " + ty2);
		System.out.println("Movement xMove " + xMove + " | yMove " + yMove);
		*/
	}
	
	public void moveX(int tx1, int tx2, int ty1, int ty2) {
		if(this.facing == "E") {
			if(!(collisionWTile(tx2, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx2 ,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}else if(this.facing == "W") {
			if(!(collisionWTile(tx1, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx1,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}
	}
	
	public void moveY(int tx1, int tx2, int ty1, int ty2) {
		if(this.facing == "S") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty2) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty2))) {
				y += yMove;
			}
		}else if(this.facing == "N") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty1) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty1))) {
				y += yMove;
			}
		}
	}
	
	public void moveXY(int tx1, int tx2, int ty1, int ty2) {
		if(this.facing == "SW") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty2) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty2))) {
				y += yMove;
			}
			if(!(collisionWTile(tx1, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx1,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}else if(this.facing == "SE") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty2) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty2))) {
				y += yMove;
			}
			if(!(collisionWTile(tx2, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx2 ,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}else if(this.facing == "NW") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty1) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty1))) {
				y += yMove;
			}
			if(!(collisionWTile(tx1, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx1,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}else if(this.facing == "NE") {
			if(!(collisionWTile((int) ((x + width * 0.2) / Tile.T_WIDTH), ty1) 
					 || collisionWTile((int) ((x + width * 0.8) / Tile.T_WIDTH), ty1))) {
				y += yMove;
			}
			if(!(collisionWTile(tx2, (int) ((y + 0.2 * height) / Tile.T_HEIGHT)) 
					|| collisionWTile(tx2 ,  (int) ((y + 0.8 * height) / Tile.T_HEIGHT)))) {
				x += xMove;
			}
		}
	}
	
	public void updateEffect() {
		this.rangeMod = 0;
		this.attackRangeMod = 0;
		if(underEffect.size() > 0) {
			Iterator<Effect> effect = underEffect.iterator();
			while(effect.hasNext()) {
				this.effect = effect.next();
				this.health -= this.effect.damage;
				this.rangeMod -= this.effect.rangeMod;
				this.attackRangeMod -= this.effect.attackRangeMod;
				if(this.effect.duration < 101) {
					this.effect.duration--;
					System.out.println("Under effect: " + this.effect.effectName);
					if(this.effect.duration <= 0) {
				        effect.remove();
					}
				}
			}
		}
		this.mRange = this.movementRange - this.rangeMod;
		this.aRange = this.attackRange - this.attackRangeMod;
		System.out.println("Health: " + this.health);
	}
	
	public void catchEffect(ArrayList<Effect> effects) {
		for(Effect effect: effects) {
			if(effect.effectName == "Damage" && effect.duration == 0) {
				this.health -= effect.damage;
			}else{
				underEffect.add(effect);
			}
		}
	}
	
	public ArrayList<Effect> getEffects(){
		return underEffect;
	}
	
	public abstract void attackAction();
	
	public abstract void defendAction();
	
	public boolean collisionWTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}
	
	public void priorityCheck() {
		this.priority = new Random().nextInt(50);
	}
	
	public void equipRHand(Item item) {
		System.out.println("Equpping right hand with...");
		System.out.println(item.toString());
		this.mobInventory.equipRHand(item);
	}
	
	public Item getRHand() {
		return this.mobInventory.getRHand();
	}
	
	public void equipLHand(Item item) {
		this.mobInventory.equipLHand(item);
	}
	
	public Item getLHand() {
		return this.mobInventory.getLHand();
	}
	
	public BattleUI getBattleUI() {
		return null;
	}

	public void renderGrid(Graphics g) {
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public int getPriority() {
		return this.priority;
	}

	public boolean getEndTurn() {
		return endTurn;
	}

	public void setEndTurn(boolean endTurn) {
		this.endTurn = endTurn;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public char getGroup() {
		return group;
	}

	public short getGroupID() {
		return groupID;
	}
	
	public void movementLimit() {
		System.out.println("Generating movement grid");
		Rescale();
		this.movementGrid = dAOE.calculateAOE((int)(x / Tile.T_WIDTH), (int)(y / Tile.T_HEIGHT), mRange);
	}
	
	public void attackLimit() {
		System.out.println("Generating attack range");
		Rescale();
		this.attackGrid = dAOE.calculateAOE((int)(x / Tile.T_WIDTH), (int)(y / Tile.T_HEIGHT), this.mobInventory.getAttackRange());
	}
	
	public boolean checkGrid(int x, int y) {
		for(Node n: movementGrid) {
			if(n.getTile().getX() == x && n.getTile().getY() == y) return true;
		}
		return false;
	}
	
	public boolean getMovement() {
		return movement;
	}
	
	public void setMovement(boolean movement) {
		this.movement = movement;
	}


	public void setVGrid(boolean vGrid) {
		this.vGrid = vGrid;
	}
	
	public boolean getVGrid() {
		return vGrid;
	}

	public abstract void battleUIrender(Graphics g);
	public abstract void battleUIupdate();
	
	public abstract void attackUpdate();
	public abstract void attackRender(Graphics g);
	
	public abstract boolean isAttackActive();
	
	public Classes getClasses() {
		return this.classes;
	}

	public int getMovementRange() {
		return movementRange;
	}

	public void setMovementRange(int movementRange) {
		this.movementRange = movementRange;
	}

	public int getmRange() {
		return mRange;
	}

	public void setmRange(int mRange) {
		this.mRange = mRange;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public int getaRange() {
		return aRange;
	}

	public void setaRange(int aRange) {
		this.aRange = aRange;
	}

	public int getRangeMod() {
		return rangeMod;
	}

	public void setRangeMod(int rangeMod) {
		this.rangeMod = rangeMod;
	}

	public int getAttackRangeMod() {
		return attackRangeMod;
	}

	public void setAttackRangeMod(int attackRangeMod) {
		this.attackRangeMod = attackRangeMod;
	}
	
	public boolean checkTArea(List<Node> tiles) {
		Rescale();
		int tx = (int) (this.x / Tile.T_WIDTH);
		int ty = (int) (this.y / Tile.T_HEIGHT);
		for(int i = 0; i < tiles.size(); i++) {
			if(tiles.get(i).getTile().getX() == tx 
					&& tiles.get(i).getTile().getY() == ty) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPosition(int x, int y) {
		Rescale();
		int tx = (int) (this.x / Tile.T_WIDTH);
		int ty = (int) (this.y / Tile.T_HEIGHT);
		if((tx == x) && (ty == y)) 
			return true;
		else 
			return false;
	}
	
	protected void generateStats() {
		this.health = this.constitution * 15 + this.level * 10;
		this.hit_evade_base = 4 * this.dexterity;
	}

	public boolean isSkillActive() {
		return skillActive;
	}

	public int getConstitution() {
		return constitution;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getWisdom() {
		return wisdom;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public void setSkillActive(boolean skillActive) {
		this.skillActive = skillActive;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public int getIntellect() {
		return intellect;
	}

	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	public int getCharisma() {
		return charisma;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	
	@Override
	public float getX() {
		Rescale();
		return x;
	}
	
	@Override
	public float getY() {
		Rescale();
		return y;
	}
	
	@Override
	public void Rescale() {
		float pre_width = width;
		float pre_height = height;
		scale = Handler.getSCALE();
		//System.out.println("Scale " + scale);
		speed = (float) (2.0f * DEFAULT_SPEED * scale);
		//System.out.println("Before rescale X " + x + " | Y " + y);
		//System.out.println("Rescale tileX " + tileX + " | tileY " + tileY);
		width = (int) (STARTING_WIDTH * scale);
		height = (int) (STARTING_HEIGHT * scale);
		x = x * (1.0f * width / pre_width);
		y = y * (1.0f * height / pre_height);
		//System.out.println("After rescale width " + width + " | height " + height);
		//System.out.println("After rescale tile width " + Tile.T_WIDTH + " | height " + Tile.T_HEIGHT);
		isoPosition(this.x, this.y);
		//System.out.println("Iso a " + a + " | b " + b);
	}
	
	@Override
	protected void isoPosition(float x, float y) {
		a = (x - y) / 2.0f;
		b = (y + x) / 4.0f;
	}
	
	protected void animationUpdate() {
		if((xMove < 0) && (yMove < 0)) {
			anRNW.update();
		}else if((xMove > 0) && (yMove < 0)) {
			anRNE.update();
		}else if((xMove < 0) && (yMove > 0)) {
			anRSW.update();
		}else if((xMove > 0) && (yMove > 0)) {
			anRSE.update();
		}else if(xMove > 0) {
			anRE.update();
		}else if(xMove < 0) {
			anRW.update();
		}else if(yMove < 0) {
			anRN.update();
		}else if(yMove > 0) {
			anRS.update();
		}else if(facing == "E") {
			anIE.update();
		}else if(facing == "W") {
			anIW.update();
		}else if(facing == "N") {
			anIN.update();
		}else if(facing == "S") {
			anIS.update();
		}else if(facing == "NE") {
			anINE.update();
		}else if(facing == "NW") {
			anINW.update();
		}else if(facing == "SE") {
			anISE.update();
		}else if(facing == "SW") {
			anISW.update();
		}else {
			anISW.update();
		}
	}
	
	protected BufferedImage returnCAnimFrame() {
		if((xMove < 0) && (yMove < 0)) {
			return anRNW.getCFrame();
		}else if((xMove > 0) && (yMove < 0)) {
			return anRNE.getCFrame();
		}else if((xMove < 0) && (yMove > 0)) {
			return anRSW.getCFrame();
		}else if((xMove > 0) && (yMove > 0)) {
			return anRSE.getCFrame();
		}else if(xMove > 0) {
			return anRE.getCFrame();
		}else if(xMove < 0) {
			return anRW.getCFrame();
		}else if(yMove < 0) {
			return anRN.getCFrame();
		}else if(yMove > 0) {
			return anRS.getCFrame();
		}else if(facing == "E") {
			return anIE.getCFrame();
		}else if(facing == "W") {
			return anIW.getCFrame();
		}else if(facing == "N") {
			return anIN.getCFrame();
		}else if(facing == "S") {
			return anIS.getCFrame();
		}else if(facing == "NE") {
			return anINE.getCFrame();
		}else if(facing == "NW") {
			return anINW.getCFrame();
		}else if(facing == "SE") {
			return anISE.getCFrame();
		}else if(facing == "SW") {
			return anISW.getCFrame();
		}else {
			return anISW.getCFrame();
		}
	}
}
