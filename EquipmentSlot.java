package com.cannon.basegame;

import java.util.Map;

public class EquipmentSlot extends ItemSlot {

	private int equipmentType;
	private Equipment lastEquipment = null;
	
	public EquipmentSlot(int type) {
		equipmentType = type;
	}
	
	@Override
	public boolean canDrop(Item item){
		if(!(item instanceof Equipment) || ((Equipment)item).getEquipmentType() != this.equipmentType){
			return false;
		}
		return super.canDrop(item);
	}
	
	@Override
	public void setItem(Item item) {
		if(!(item instanceof Equipment)){
			System.out.println("WARNING: NOT EQUIPMENT");
			return;
		}
		super.setItem(item);
		Equipment equipment = (Equipment) item;
		revertStatChanges(lastEquipment);
		applyStatChanges(equipment);
		Entity.getPlayer().initActions();
	}
	
	private void revertStatChanges(Equipment equipment){
		if(equipment == null){
			return;
		}
		Player player = Entity.getPlayer();
		for(Map.Entry<String, Integer> equipmentStat : equipment.getStats().entrySet()){
			for(Map.Entry<String, Integer> playerStat : player.getStats().entrySet()){
				if(equipmentStat.getKey().equals(playerStat.getKey())){
					player.getStats().put(equipmentStat.getKey(), playerStat.getValue() - equipmentStat.getValue());
					break;
				}
			}
		}
		lastEquipment = null;
	}
	private void applyStatChanges(Equipment equipment){
		if(equipment == null){
			return;
		}
		Player player = Entity.getPlayer();
		for(Map.Entry<String, Integer> equipmentStat : equipment.getStats().entrySet()){
			for(Map.Entry<String, Integer> playerStat : player.getStats().entrySet()){
				if(equipmentStat.getKey().equals(playerStat.getKey())){
					player.getStats().put(equipmentStat.getKey(), playerStat.getValue() + equipmentStat.getValue());
					break;
				}
			}
		}
		lastEquipment = equipment;
		
	}

}
