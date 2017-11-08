package net.clockworkgiant.entities.mob.specifics;

import java.util.ArrayList;
import java.util.List;

import net.clockworkgiant.gfx.Assets;
import net.clockworkgiant.ui.ClickListener;
import net.clockworkgiant.ui.UIImageButton;

public class BasicClass {

	private List<Skill> listSkills;
	private String class_name, class_type;
	public static boolean innerCircleFighter = false, innerCircleMage = false, innerCirclePrayer = false;
	
	public BasicClass(String class_name, String class_type) {
		this.class_name = class_name;
		this.class_type = class_type;
		this.listSkills = new ArrayList<Skill>();
	}
	
	public UIImageButton battleUIicon() {
		if(class_type == "fighter") {
			return new UIImageButton(0, 0, 32, 32, Assets.shoot, new ClickListener() {

				@Override
				public void onClick() {
					innerCircleFighter = true;
				}});
		}else if(class_type == "mage") {
			return new UIImageButton(0, 0, 32, 32, Assets.magic, new ClickListener() {

				@Override
				public void onClick() {
					innerCircleMage = true;
				}});
		}else if(class_type == "prayer") {
			return new UIImageButton(0, 0, 32, 32, Assets.miracle, new ClickListener() {

				@Override
				public void onClick() {
					innerCirclePrayer = true;
				}});
		}else {
			return null;
		}
	}
	
	public void addSkill(Skill skill) {
		this.listSkills.add(skill);
	}
	
	public List<Skill> getSkillList(){
		return this.listSkills;
	}

	public String getName() {
		return this.class_name;
	}
	
	public String getType() {
		return this.class_type;
	}
}
