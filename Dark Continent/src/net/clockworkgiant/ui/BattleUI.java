package net.clockworkgiant.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.Player;
import net.clockworkgiant.entities.mob.specifics.BasicClass;
import net.clockworkgiant.entities.mob.specifics.Skill;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.tiles.Tile;

public class BattleUI {

	private Handler handler;
	private UIManager buiManager;
	private float x, y;
	private int width = 32, height = 32;
	private boolean endTurn = false, actionCheck = false, circleLock = false, attackCircle = false;
	private Mob mob;
	private ArrayList<UIObject> innerCircleFighter, innerCircleMage, innerCirclePrayer, outerCircle, innerCircleAttack;
	
	public BattleUI(Handler handler, Player player) {
		this.handler = handler;
		buiManager = new UIManager(handler);
		this.mob = player;
		//this.handler.getMouseManager().setBUIManager(buiManager);
		innerCircleFighter = new ArrayList<UIObject>();
		innerCircleMage = new ArrayList<UIObject>();
		innerCirclePrayer = new ArrayList<UIObject>();
		innerCircleAttack = new ArrayList<UIObject>();
		outerCircle = new ArrayList<UIObject>();
		
		outerCircle.add(new UIImageButton(0, 0, width, height, Assets.attack, new ClickListener() {

			@Override
			public void onClick() {
				attackCircle = true;
			}}));
		
		for(BasicClass basic: player.getClasses().getClassList()) {
			if(!outerCircle.contains(basic.battleUIicon())) outerCircle.add(basic.battleUIicon());
		}
		
		outerCircle.add(new UIImageButton(0, 0, width, height, Assets.defend, new ClickListener() {

			@Override
			public void onClick() {
				;
			}}));
		
		outerCircle.add(new UIImageButton(0, 0, width, height, Assets.skip, new ClickListener() {

			@Override
			public void onClick() {
				endTurn = true;
			}}));
		
		outerCircle.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			// Check for redundancy
			@Override
			public void onClick() {
				actionCheck = false;
				circleLock = false;
				buiManager.unHowerObjects();
				if(mob.getActive()) mob.setVGrid(true);
			}}));
		
		buiManager.setObjects(outerCircle);
		
		for(BasicClass basic: mob.getClasses().getClassList()) {
			if(basic.getType().equals("fighter")) {
				for(Skill skill: basic.getSkillList()) {
					innerCircleFighter.add(skill.getButton());
				}
			}
			if(basic.getType().equals("mage")) {
				for(Skill skill: basic.getSkillList()) {
					innerCircleMage.add(skill.getButton());
				}
			}
			if(basic.getType().equals("prayer")) {
				for(Skill skill: basic.getSkillList()) {
					innerCirclePrayer.add(skill.getButton());
				}
			}
		}
		
		
		if(innerCircleFighter.size() > 0)
		innerCircleFighter.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			@Override
			public void onClick() {
				BasicClass.innerCircleFighter = false;
				circleLock = false;
				buiManager.unHowerObjects();
				buiManager.setObjects(outerCircle);
			}}));
		
		if(innerCircleMage.size() > 0)
		innerCircleMage.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			@Override
			public void onClick() {
				BasicClass.innerCircleMage = false;
				circleLock = false;
				buiManager.unHowerObjects();
				buiManager.setObjects(outerCircle);
			}}));

		if(innerCirclePrayer.size() > 0)
		innerCirclePrayer.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			@Override
			public void onClick() {
				BasicClass.innerCirclePrayer = false;
				circleLock = false;
				buiManager.unHowerObjects();
				buiManager.setObjects(outerCircle);
			}}));
		
		if(innerCircleAttack.size() > 0)
		innerCirclePrayer.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			@Override
			public void onClick() {
				attackCircle = false;
				circleLock = false;
				buiManager.unHowerObjects();
				buiManager.setObjects(outerCircle);
			}}));
		
		if(buiManager.getActive()) {
			buiManager.setObjectsActive(false);
		}
		
		generateAttackCircle();
	}
	
	public void update(boolean battle) {
		if(battle) {
			
			//Need to remove "buiManager.unHowerObjects();" bunch of places...
			
			if(BasicClass.innerCirclePrayer && !circleLock) {
				//buiManager.unHowerObjects();
				buiManager.setObjects(innerCirclePrayer);
				circleLock = true;
				buiManager.setObjectsActive(actionCheck);
				//buiManager.unHowerObjects();
			}else if(BasicClass.innerCircleMage && !circleLock) {
				//buiManager.unHowerObjects();
				buiManager.setObjects(innerCircleMage);
				circleLock = true;
				buiManager.setObjectsActive(actionCheck);
				//buiManager.unHowerObjects();
			}else if(BasicClass.innerCircleFighter && !circleLock) {
				//buiManager.unHowerObjects();
				buiManager.setObjects(innerCircleFighter);
				circleLock = true;
				buiManager.setObjectsActive(actionCheck);
				//buiManager.unHowerObjects();
			}else if(attackCircle && !circleLock) {
				//buiManager.unHowerObjects();
				buiManager.setObjects(innerCircleAttack);
				circleLock = true;
				buiManager.setObjectsActive(actionCheck);
				//buiManager.unHowerObjects();
			}
			if(mob.getActive() && (!mob.getMovement() || !mob.getVGrid())) {
				if(mob.isSkillActive() || mob.isAttackActive()) {
					actionCheck = false;
				}else actionCheck = true;
				this.x = mob.getISOX() + 16; //p_width/2 - width/2
				this.y = mob.getISOY() + 16;
				for(int i = 0; i < buiManager.getObjects().size() - 1; i++) {
					buiManager.getObjects().get(i).setX((float) Math.sin(Math.PI * 2 * i / (buiManager.getObjects().size() - 1)) * 48 + x - handler.getGameCamera().getxOffset());
					buiManager.getObjects().get(i).setY((float) Math.cos(Math.PI * 2 * i / (buiManager.getObjects().size() - 1)) * -48 + y - handler.getGameCamera().getyOffset());
				}
				buiManager.getObjects().get(buiManager.getObjects().size() - 1).setX(x - handler.getGameCamera().getxOffset());
				buiManager.getObjects().get(buiManager.getObjects().size() - 1).setY(y - handler.getGameCamera().getyOffset());
				
				if(endTurn) {
					this.initEndTurn();
				}
			}
			if(handler.getMouseManager().keyJustReleased(MouseEvent.BUTTON3) && !actionCheck) {
				if(mob.getActive() && (int) (mob.getX() / Tile.T_WIDTH) == handler.getMouseTX() && (int) (mob.getY() / Tile.T_HEIGHT) == handler.getMouseTY()) {
					this.actionCheck = true;
					mob.setVGrid(false);
				}
			}
		}
		if(actionCheck) {
			if(!buiManager.getActive()) {
				buiManager.setObjectsActive(actionCheck);
			}
			//System.out.println("Action check in BUI: " + actionCheck);
			buiManager.update();
		}else if(!actionCheck){
			if(buiManager.getActive()) {
				buiManager.setObjectsActive(actionCheck);
				buiManager.unHowerObjects();
			}
		}
	}
	
	public void render(Graphics g, boolean battle) {
		if(((x - handler.getGameCamera().getxOffset()) > 0) 
				&& ((x - handler.getGameCamera().getxOffset()) < handler.getWidth()) 
				&& ((y - handler.getGameCamera().getyOffset()) > 0) 
				&& ((y - handler.getGameCamera().getyOffset()) < handler.getHeight())) {

				if(battle && actionCheck) {
					buiManager.render(g);
				}
			}
	}
	
	public void generateAttackCircle() {
		boolean defendCapability = false, simpleAttack = true, chargeAttack = false;
		innerCircleAttack = new ArrayList<UIObject>();
		
		if(mob.getRHand() != null) {
			if(mob.getRHand().getSubGroup().equals("Shield")) defendCapability = true;
			if(mob.getRHand().getValue("charges") > 0) chargeAttack = true;
		}
		
		if(mob.getLHand() != null) {
			if(!mob.getLHand().getType().equals("Two-Handed")) {
				if(mob.getRHand().getSubGroup().equals("Shield")) defendCapability = true;
				if(mob.getRHand().getValue("charges") > 0) chargeAttack = true;
			}
		}
		
		if(simpleAttack) {
			innerCircleAttack.add(new UIImageButton(0, 0, width, height, Assets.attack, new ClickListener() {

				@Override
				public void onClick() {
					mob.attackAction();
					actionCheck = false;
				}}));
		}
		
		if(chargeAttack) {
			innerCircleAttack.add(new UIImageButton(0, 0, width, height, Assets.magic, new ClickListener() {

				@Override
				public void onClick() {
					mob.attackAction();
					actionCheck = false;
				}}));
		}
		
		if(defendCapability) {
			innerCircleAttack.add(new UIImageButton(0, 0, width, height, Assets.defend, new ClickListener() {

				@Override
				public void onClick() {
					mob.defendAction();
				}}));
		}
		
		innerCircleAttack.add(new UIImageButton(0, 0, width, height, Assets.back, new ClickListener() {

			@Override
			public void onClick() {
				attackCircle = false;
				circleLock = false;
				buiManager.unHowerObjects();
				buiManager.setObjects(outerCircle);
			}}));
	}
	
	public void initEndTurn() {
		endTurn = false;
		actionCheck = false;
		mob.setVGrid(true);
		mob.setEndTurn(true);
		BasicClass.innerCircleFighter = false;
		BasicClass.innerCircleMage = false;
		BasicClass.innerCirclePrayer = false;
		attackCircle = false;
		circleLock = false;
		buiManager.unHowerObjects();
		buiManager.setObjects(outerCircle);
		buiManager.unHowerObjects();
	}

	public boolean isEndTurn() {
		return endTurn;
	}

	public void setEndTurn(boolean endTurn) {
		this.endTurn = endTurn;
	}
	
	public UIManager getManager() {
		return this.buiManager;
	}
}
