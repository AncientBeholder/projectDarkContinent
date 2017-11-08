package net.clockworkgiant.entities.mob.movement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.specifics.BasicClass;
import net.clockworkgiant.entities.mob.specifics.Skill;
import net.clockworkgiant.gamebase.Handler;

public class Control {
	private Handler handler;
	private ArrayList<Mob> activeList;
	private boolean nextTurn = true;
	private int iterator = -1;
	
	private Comparator<Mob> prioritySorter = new Comparator<Mob>() {

		@Override
		public int compare(Mob m0, Mob m1) {
			if(m0.getPriority() > m1.getPriority()) return 1;
			else if(m0.getPriority() < m1.getPriority()) return -1;
			return 0;
		}
		
	};
	
	public Control(Handler handler) {
		this.handler = handler;
		activeList = new ArrayList<Mob>();
	}
	
	public void update() {
		if(handler.getWorld().isBattle() && activeList.size() > 0) {
			if(nextTurn || iterator < 0) {
				for(Mob mob: activeList) {
					mob.priorityCheck();
					mob.setEndTurn(false);
					mob.setActive(false);
				}
				Collections.sort(activeList, prioritySorter);
				nextTurn = false;
				iterator = activeList.size() - 1;
				System.out.println("Iterator: " + iterator);
				if(iterator >= 0) {
					activeList.get(iterator).setActive(true);
					activeList.get(iterator).updateEffect();
					activeList.get(iterator).movementLimit();
					activeList.get(iterator).setMovement(true);
					handler.getMouseManager().setBUIManager(activeList.get(iterator).getBattleUI().getManager());
				}
			}else if(!nextTurn) {
				if(activeList.get(iterator).isSkillActive()) {
					if(activeList.get(iterator).isAttackActive()) {
						activeList.get(iterator).attackUpdate();
					}else {
						for(BasicClass basic: activeList.get(iterator).getClasses().getClassList()) {
							for(Skill skill: basic.getSkillList()) {
								if(skill.isActive()) {
									skill.update();
								}
							}
						}
					}
				}
				//WTF?
				activeList.get(iterator).update();
				activeList.get(iterator).battleUIupdate();
				
				if(activeList.get(iterator).getEndTurn()) {
					activeList.get(iterator).setActive(false);
					activeList.get(iterator).setEndTurn(false);
					activeList.get(iterator).getBattleUI().initEndTurn();
					handler.getMouseManager().setBUIManager(null);
					
					System.out.println("Iterator: " + iterator + " | End turn: " + activeList.get(iterator).getEndTurn());
					iterator--;
					if(iterator < 0) {
						nextTurn = true;
						System.out.println("Next Turn: " + nextTurn);
					}else {
						activeList.get(iterator).setActive(true);
						activeList.get(iterator).updateEffect();
						activeList.get(iterator).movementLimit();
						activeList.get(iterator).setMovement(true);
						handler.getMouseManager().setBUIManager(activeList.get(iterator).getBattleUI().getManager());
					}
				}
			}
		}
	}

	public void setActiveList(ArrayList<Mob> activeList) {
		this.activeList.addAll(activeList);
	}
	
	public ArrayList<Mob> getActiveList(){
		return activeList;
	}

}
