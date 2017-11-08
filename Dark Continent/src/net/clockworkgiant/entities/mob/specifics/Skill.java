package net.clockworkgiant.entities.mob.specifics;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.movement.DijkstraAOE;
import net.clockworkgiant.gamebase.Handler;
import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.tiles.Tile;
import net.clockworkgiant.ui.ClickListener;
import net.clockworkgiant.ui.UIImageButton;
import net.clockworkgiant.worlds.Node;

public class Skill {

	private int range = 1, radiusAOE = 0, damage, duration;
	private float gridX, gridY;
	private boolean dot, aoe, active, effect, ranged;
	private String skill_name, effect_name;
	private UIImageButton button;
	private SkillEffect skillEffect;
	private List<Node> aoeZone, shotRange;
	private Handler handler;
	private Mob mob;
	private BufferedImage[] buttonImage;

	public Skill(Handler handler, Mob mob, String skill_name) {
		this.skill_name = skill_name;
		this.mob = mob;
		this.handler = handler;
		this.buttonImage = Assets.ok;
	}
	
	public Skill(Skill skill, SkillEffect skillEffect) {
		this.handler = skill.handler;
		this.mob = skill.mob;
		this.range = skill.range;
		this.radiusAOE = skill.radiusAOE;
		this.damage = skill.damage;
		this.duration = skill.duration;
		this.dot = skill.dot;
		this.aoe = skill.aoe;
		this.active = skill.active;
		this.effect = skill.effect;
		this.skill_name = skill.skill_name;
		this.effect_name = skill.effect_name;
		this.button = skill.button;
		this.ranged = skill.ranged;
		this.skillEffect = skillEffect;
		this.shotRange = new ArrayList<Node>();
		this.aoeZone = new ArrayList<Node>();
		this.buttonImage = skill.getButtonImage();
		if(buttonImage != null) {
			button = new UIImageButton(0, 0, 32, 32, this.buttonImage, new ClickListener() {

			@Override
			public void onClick() {
				active = true;
				mob.setSkillActive(true);
				shotRangeUpdate();
				aoeUpdate();
			}});
		}
	}
	
	public void update() {
		this.aoeUpdate();
		
		if(handler.getMouseManager().keyJustReleased(MouseEvent.BUTTON1)) {
			for(Node node: shotRange) {
				if(node.getTile().getX() == handler.getMouseTX() 
						&& node.getTile().getY() == handler.getMouseTY()
						&& !handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid()) {
						//search target zone for "skill victim" and throw effects at it
					for(Mob mob: handler.getWorld().getControl().getActiveList()) {
						if(mob.checkTArea(aoeZone)) {
							mob.catchEffect(skillEffect.skillEfect());
							this.mob.setSkillActive(false);
							this.mob.getBattleUI().setEndTurn(true);
							this.active = false;
						}
					}
				}
			}
		}

		if(handler.getMouseManager().isRightPressed()) {
			this.mob.setSkillActive(false);
			this.active = false;
		}
	}
	
	public void render(Graphics g) {
		for(Node n: shotRange) {
			gridX = (n.getTile().getX() * Tile.T_WIDTH - n.getTile().getY() * Tile.T_HEIGHT) / 2.0f;
			gridY = (n.getTile().getY() * Tile.T_HEIGHT + n.getTile().getX() * Tile.T_WIDTH) / 4.0f;
			
			g.drawImage(Assets.move, (int) (gridX - handler.getGameCamera().getxOffset()), 
					(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
		}
		
		if(!handler.getWorld().getTile(handler.getMouseTX(), handler.getMouseTY()).isSolid())
		for(Node n: aoeZone) {
			gridX = (n.getTile().getX() * Tile.T_WIDTH - n.getTile().getY() * Tile.T_HEIGHT) / 2.0f;
			gridY = (n.getTile().getY() * Tile.T_HEIGHT + n.getTile().getX() * Tile.T_WIDTH) / 4.0f;
			
			g.drawImage(Assets.AOE, (int) (gridX - handler.getGameCamera().getxOffset()), 
					(int) (gridY - handler.getGameCamera().getyOffset()), Tile.T_WIDTH, Tile.T_HEIGHT, null);
		}
	}
	
	public void shotRangeUpdate() {
		this.shotRange = calculateAOEZone((int) mob.getX()/Tile.T_WIDTH, (int) mob.getY()/Tile.T_HEIGHT, range);
	}
	
	public void aoeUpdate() {
		if(aoe){
			this.aoeZone = calculateAOEZone(handler.getMouseTX(), handler.getMouseTY(), radiusAOE);
		}else if(!aoe) {
			this.aoeZone = calculateAOEZone(handler.getMouseTX(), handler.getMouseTY(), 0);
		}
	}
	
	public List<Node> calculateAOEZone(int xc, int yc, int range){
		return new DijkstraAOE(handler).calculateAOE(xc, yc, range);
	}
	
	public List<Node> getAOEZone(){
		return aoeZone;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getRadiusAOE() {
		return radiusAOE;
	}

	public void setRadiusAOE(int radiusAOE) {
		this.radiusAOE = radiusAOE;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public boolean isDot() {
		return dot;
	}

	public void setDot(boolean dot) {
		this.dot = dot;
	}

	public boolean isAoe() {
		return aoe;
	}

	public void setAoe(boolean aoe) {
		this.aoe = aoe;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public String getSkill_name() {
		return skill_name;
	}

	public void setSkill_name(String skill_name) {
		this.skill_name = skill_name;
	}

	public String getEffect_name() {
		return effect_name;
	}

	public void setEffect_name(String effect_name) {
		this.effect_name = effect_name;
	}

	public UIImageButton getButton() {
		return button;
	}

	public void setButton(UIImageButton button) {
		this.button = button;
	}

	public BufferedImage[] getButtonImage() {
		return buttonImage;
	}

	public void setButtonImage(BufferedImage[] buttonImage) {
		this.buttonImage = buttonImage;
	}
}
