package net.clockworkgiant.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.clockworkgiant.entities.mob.Mob;
import net.clockworkgiant.entities.mob.specifics.Effect;
import net.clockworkgiant.items.Item;

public class MobInventory extends Inventory {
	
	private Mob mob;
	private Map<String, Item> equiped;
	
	/*Look through the code and find all instances of [String] == "text"
	 * replace those instances with .equals("text")
	 */

	public MobInventory(Mob mob) {
		super(mob.getHandler());
		this.mob = mob;
		this.equiped = new HashMap<String, Item>();
		this.equiped.put("Left Hand", null);
		this.equiped.put("Right Hand", null);
		this.equiped.put("Gauntlets", null);
		this.equiped.put("Head", null);
		this.equiped.put("Torso", null);
		this.equiped.put("Waist", null);
		this.equiped.put("Legs", null);
		this.equiped.put("Feet", null);
		this.equiped.put("Neck", null);
		this.equiped.put("Left Wrist", null);
		this.equiped.put("Right Wrist", null);
		this.equiped.put("Left Ring", null);
		this.equiped.put("Right Ring", null);
	}
	
	public void getEquipmentEffects() {
		this.mob.catchEffect(null);
	}
	
	public void getStats() {
		
	}
	
	public void equipLHand(Item item) {
		if(item == null) {
			if(this.equiped.get("Left Hand") != null) {
				if(this.equiped.get("Left Hand").getType().equals("One-Handed")) {
					this.equiped.get("Left Hand").equipTo(null);
					this.list.add(this.equiped.get("Left Hand"));
					this.equiped.put("Left Hand", null);
				}else if(this.equiped.get("Left Hand").getType().equals("Two-Handed") 
						&& this.equiped.get("Right Hand").getType().equals("Two-Handed")) {
					this.equiped.get("Right Hand").equipTo(null);
					this.list.add(equiped.get("Right Hand"));
					this.equiped.put("Left Hand", null);
					this.equiped.put("Right Hand", null);
				}
			}
		}else if(item.getSubGroup().equals("Weapon") || item.getSubGroup().equals("Shield")) {
			
			item.equipTo(this.mob);
			
			if(item.getType().equals("One-Handed")) {
				if(this.equiped.get("Left Hand") == null) {
					this.equiped.put("Left Hand", item);
				}else if(this.equiped.get("Left Hand").getType().equals("One-Handed")) {
					this.equiped.get("Left Hand").equipTo(null);
					this.list.add(this.equiped.get("Left Hand"));
					this.equiped.put("Left Hand", item);
				}else if(this.equiped.get("Left Hand").getType().equals("Two-Handed") 
						&& this.equiped.get("Right Hand").getType().equals("Two-Handed")) {
					this.equiped.get("Right Hand").equipTo(null);
					this.list.add(equiped.get("Right Hand"));
					this.equiped.put("Right Hand", null);
					this.equiped.put("Left Hand", item);
				}
				
			}else if(item.getType().equals("Two-Handed")) {
				if(this.equiped.get("Left Hand") == null
						&& this.equiped.get("Right Hand") == null) {
					this.equiped.put("Left Hand", item);
					this.equiped.put("Right Hand", item);
				}else if(this.equiped.get("Left Hand") != null 
						|| this.equiped.get("Right Hand") != null) {
					
					if(this.equiped.get("Left Hand") != null) {
						if(this.equiped.get("Left Hand").getType().equals("One-Handed")) {
							this.equiped.get("Left Hand").equipTo(null);
							this.list.add(this.equiped.get("Left Hand"));
						}
						this.equiped.put("Left Hand", null);
					}
					if(this.equiped.get("Right Hand") != null) {
						this.equiped.get("Right Hand").equipTo(null);
						this.list.add(this.equiped.get("Right Hand"));
						this.equiped.put("Right Hand", null);
					}
					this.equiped.put("Left Hand", item);
					this.equiped.put("Right Hand", item);
				}
			}
		}
		
	}
	
	public void equipRHand(Item item) {
		if(item == null) {
			if(this.equiped.get("Right Hand") != null) {
				if(this.equiped.get("Right Hand").getType().equals("One-Handed")) {
					this.equiped.get("Right Hand").equipTo(null);
					this.list.add(this.equiped.get("Right Hand"));
					this.equiped.put("Right Hand", null);
				}else if(this.equiped.get("Left Hand").getType().equals("Two-Handed") 
						&& this.equiped.get("Right Hand").getType().equals("Two-Handed")) {
					this.equiped.get("Right Hand").equipTo(null);
					this.list.add(equiped.get("Right Hand"));
					this.equiped.put("Left Hand", null);
					this.equiped.put("Right Hand", null);
				}
			}
		}else if(item.getSubGroup().equals("Weapon") || item.getSubGroup().equals("Shield")) {
			
			item.equipTo(this.mob);
			
			if(item.getType().equals("One-Handed")) {
				if(this.equiped.get("Right Hand") == null) {
					this.equiped.put("Right Hand", item);
					System.out.println("Equipped to empty right Hand - " + item.toString());
				}else if(this.equiped.get("Right Hand").getType().equals("One-Handed")) {
					this.list.add(this.equiped.get("Right Hand"));
					this.equiped.get("Right Hand").equipTo(null);
					this.equiped.put("Right Hand", item);
				}else if(this.equiped.get("Left Hand").getType().equals("Two-Handed") 
						&& this.equiped.get("Right Hand").getType().equals("Two-Handed")) {
					this.list.add(equiped.get("Right Hand"));
					this.equiped.get("Right Hand").equipTo(null);
					this.equiped.put("Left Hand", null);
					this.equiped.put("Right Hand", item);
				}
				
			}else if(item.getType().equals("Two-Handed")) {
				if(this.equiped.get("Left Hand") == null
						&& this.equiped.get("Right Hand") == null) {
					this.equiped.put("Left Hand", item);
					this.equiped.put("Right Hand", item);
				}else if(this.equiped.get("Left Hand") != null 
						|| this.equiped.get("Right Hand") != null) {
					
					if(this.equiped.get("Left Hand") != null) {
						if(this.equiped.get("Left Hand").getType().equals("One-Handed")) {
							this.equiped.get("Left Hand").equipTo(null);
							this.list.add(this.equiped.get("Left Hand"));
						}
						this.equiped.put("Left Hand", null);
					}
					if(this.equiped.get("Right Hand") != null) {
						this.equiped.get("Right Hand").equipTo(null);
						this.list.add(this.equiped.get("Right Hand"));
						this.equiped.put("Right Hand", null);
					}
					this.equiped.put("Left Hand", item);
					this.equiped.put("Right Hand", item);
				}
			}
		}
		
	}

	public void equipHead(Item item) {
		if(item == null) {
			if(this.equiped.get("Head") != null) {
				this.equiped.get("Head").equipTo(null);
				this.list.add(this.equiped.get("Head"));
			}
			this.equiped.put("Head", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Head")) {
			
			item.equipTo(this.mob);
			
			if(this.equiped.get("Head") != null) {
				this.equiped.get("Head").equipTo(null);
				this.list.add(this.equiped.get("Head"));
			}
			this.equiped.put("Head", item);
		}
	}

	public void equipGauntlets(Item item) {
		if(item == null) {
			if(this.equiped.get("Gauntlets") != null) {
				this.equiped.get("Gauntlets").equipTo(null);
				this.list.add(this.equiped.get("Gauntlets"));
			}
			this.equiped.put("Gauntlets", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Gauntlets")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Gauntlets") != null) {
				this.equiped.get("Gauntlets").equipTo(null);
				this.list.add(this.equiped.get("Gauntlets"));
			}
			this.equiped.put("Gauntlets", item);
		}
	}
	
	public void equipTorso(Item item) {
		if(item == null) {
			if(this.equiped.get("Torso") != null) {
				this.equiped.get("Torso").equipTo(null);
				this.list.add(this.equiped.get("Torso"));
			}
			this.equiped.put("Torso", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Torso")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Torso") != null) {
				this.equiped.get("Torso").equipTo(null);
				this.list.add(this.equiped.get("Torso"));
			}
			this.equiped.put("Torso", item);
		}
	}

	public void equipWaist(Item item) {
		if(item == null) {
			if(this.equiped.get("Waist") != null) {
				this.equiped.get("Waist").equipTo(null);
				this.list.add(this.equiped.get("Waist"));
			}
			this.equiped.put("Waist", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Waist")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Waist") != null) {
				this.equiped.get("Waist").equipTo(null);
				this.list.add(this.equiped.get("Waist"));
			}
			this.equiped.put("Waist", item);
		}
	}
	
	public void equipLegs(Item item) {
		if(item == null) {
			if(this.equiped.get("Legs") != null) {
				this.equiped.get("Legs").equipTo(null);
				this.list.add(this.equiped.get("Legs"));
			}
			this.equiped.put("Legs", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Legs")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Legs") != null) {
				this.equiped.get("Legs").equipTo(null);
				this.list.add(this.equiped.get("Legs"));
			}
			this.equiped.put("Legs", item);
		}
	}

	public void equipFeet(Item item) {
		if(item == null) {
			if(this.equiped.get("Feet") != null) {
				this.equiped.get("Feet").equipTo(null);
				this.list.add(this.equiped.get("Feet"));
			}
			this.equiped.put("Feet", null);
		}else if(item.getSubGroup().equals("Armor") && item.getType().equals("Feet")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Feet") != null) {
				this.equiped.get("Feet").equipTo(null);
				this.list.add(this.equiped.get("Feet"));
			}
			this.equiped.put("Feet", item);
		}
	}
	
	public void equipNeck(Item item) {
		if(item == null) {
			if(this.equiped.get("Neck") != null) {
				this.equiped.get("Neck").equipTo(null);
				this.list.add(this.equiped.get("Neck"));
			}
			this.equiped.put("Neck", null);
		}else if(item.getSubGroup().equals("Accessories") && item.getType().equals("Neck")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Neck") != null) {
				this.equiped.get("Neck").equipTo(null);
				this.list.add(this.equiped.get("Neck"));
			}
			this.equiped.put("Neck", item);
		}
	}

	public void equipLWrist(Item item) {
		if(item == null) {
			if(this.equiped.get("Left Wrist") != null) {
				this.equiped.get("Left Wrist").equipTo(null);
				this.list.add(this.equiped.get("Left Wrist"));
			}
			this.equiped.put("Left Wrist", null);
		}else if(item.getSubGroup().equals("Accessories") && item.getType().equals("Wrist")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Left Wrist") != null) {
				this.equiped.get("Left Wrist").equipTo(null);
				this.list.add(this.equiped.get("Left Wrist"));
			}
			this.equiped.put("Left Wrist", item);
		}
	}
	
	public void equipRWrist(Item item) {
		if(item == null) {
			if(this.equiped.get("Right Wrist") != null) {
				this.equiped.get("Right Wrist").equipTo(null);
				this.list.add(this.equiped.get("Right Wrist"));
			}
			this.equiped.put("Right Wrist", null);
		}else if(item.getSubGroup().equals("Accessories") && item.getType().equals("Wrist")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Right Wrist") != null) {
				this.equiped.get("Right Wrist").equipTo(null);
				this.list.add(this.equiped.get("Right Wrist"));
			}
			this.equiped.put("Right Wrist", item);
		}
	}

	public void equipLRing(Item item) {
		if(item == null) {
			if(this.equiped.get("Left Ring") != null) {
				this.equiped.get("Left Ring").equipTo(null);
				this.list.add(this.equiped.get("Left Ring"));
			}
			this.equiped.put("Left Ring", null);
		}else if(item.getSubGroup().equals("Accessories") && item.getType().equals("Ring")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Left Ring") != null) {
				this.equiped.get("Left Ring").equipTo(null);
				this.list.add(this.equiped.get("Left Ring"));
			}
			this.equiped.put("Left Ring", item);
		}
	}
	
	public void equipRRing(Item item) {
		if(item == null) {
			if(this.equiped.get("Right Ring") != null) {
				this.equiped.get("Right Ring").equipTo(null);
				this.list.add(this.equiped.get("Right Ring"));
			}
			this.equiped.put("Right Ring", null);
		}else if(item.getSubGroup().equals("Accessories") && item.getType().equals("Ring")) {
			
			item.equipTo(this.mob);
			
			
			if(this.equiped.get("Right Ring") != null) {
				this.equiped.get("Right Ring").equipTo(null);
				this.list.add(this.equiped.get("Right Ring"));
			}
			this.equiped.put("Right Ring", item);
		}
	}
	
	public void unequipAccessories() {
		this.equipNeck(null);
		this.equipLWrist(null);
		this.equipRWrist(null);
		this.equipLRing(null);
		this.equipRRing(null);
	}
	
	public void unequipArmor() {
		this.equipGauntlets(null);
		this.equipHead(null);
		this.equipTorso(null);
		this.equipWaist(null);
		this.equipLegs(null);
		this.equipFeet(null);
	}
	
	public void unequipWeapons() {
		this.equipLHand(null);
		this.equipRHand(null);
	}
	
	public void unequipAll() {
		this.unequipAccessories();
		this.unequipArmor();
		this.unequipWeapons();
	}
	
	public int getAttackRange() {
		int r1 = 0, r2 = 0;
		
		if(this.equiped.get("Right Hand") != null) {
			if(this.equiped.get("Right Hand").getSubGroup().equals("Weapon")) {
				r1 = this.equiped.get("Right Hand").getValue("range");
			}
		}
		if(this.equiped.get("Left Hand") != null) {
			if(this.equiped.get("Left Hand").getSubGroup().equals("Weapon")) {
				r1 = this.equiped.get("Left Hand").getValue("range");
			}
		}
		
		System.out.println("Weapon 1 range: " + r1 + " | Weapon 2 range: " + r2);
		
		if((r1 < 1) && (r2 < 1))
			return 1;
		else if((r1 < r2) && (r1 > 0)) 
			return r1;
		else if(r2 < 1)
			return 1;
		else 
			return r2;
	}
	
	public ArrayList<Effect> getAttackEffects() {
		ArrayList<Effect> weaponEffects = new ArrayList<Effect>();
		
		if(this.equiped.get("Right Hand") != null) {
			if(this.equiped.get("Right Hand").getSubGroup().equals("Weapon")) {
				weaponEffects.addAll(this.equiped.get("Right Hand").getEffects());
			}
		}
		if(this.equiped.get("Left Hand") != null) {
			if(this.equiped.get("Left Hand").getSubGroup().equals("Weapon") 
					&& !this.equiped.get("Left Hand").getType().equals("Two-Handed")) {
				weaponEffects.addAll(this.equiped.get("Left Hand").getEffects());
			}
		}
		
		return weaponEffects;
	}
	
	/*
		"Left Hand"
		"Right Hand"
		"Gauntlets"
		"Head"
		"Torso"
		"Waist"
		"Legs"
		"Feet"
		"Neck"
		"Left Wrist"
		"Right Wrist"
		"Left Ring"
		"Right Ring"
	 */

	public Item getHead() {
		return this.equiped.get("Head");
	}

	public Item getTorso() {
		return this.equiped.get("Torso");
	}

	public Item getWaist() {
		return this.equiped.get("Waist");
	}

	public Item getLegs() {
		return this.equiped.get("Legs");
	}

	public Item getFeet() {
		return this.equiped.get("Feet");
	}

	public Item getGauntlets() {
		return this.equiped.get("Gauntlets");
	}

	public Item getRHand() {
		return this.equiped.get("Right Hand");
	}

	public Item getLHand() {
		return this.equiped.get("Left Hand");
	}

	public Item getNeck() {
		return this.equiped.get("Neck");
	}

	public Item getLWrist() {
		return this.equiped.get("Left Wrist");
	}

	public Item getRWrist() {
		return this.equiped.get("Right Wrist");
	}

	public Item getLRing() {
		return this.equiped.get("Left Ring");
	}

	public Item getRRing() {
		return this.equiped.get("Right Ring");
	}
}
