package com.cannon.basegame;


public class Inventory {
	private Item[] items;
	private Equipment[] equippedEquipment;
	
	public Inventory() {
		items = new Item[16];
		equippedEquipment = new Equipment[4];
	}
	
	public Inventory(int[] idArray) {
		items = new Item[16];
		
		for(int i = 0; i < 16; i++) {
			if(idArray[i] != -1) {
				items[i] = new Item(idArray[i]);
			}
		}
	}
	
	public boolean add(Item item) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				items[i] = item;
				return true;
			}
		}
		return false;
	}
	
	public Item get(int index) {
		return items[index];
	}
	
	@Override
	public String toString() {
		String returnString = "Inventory: ";
		for(Item item : items) {
			if(item != null) {
				returnString += item.toString() + ", ";
			}
		}
		return returnString;
	}

	public void set(int i, Item item) {
		items[i] = item;
	}
	
	public void remove(Item i){
		if(indexOf(i)==-1)
			return;
		remove(indexOf(i));
	}
	
	public void remove(int index){
		set(index, null);
	}
	
	public int indexOf(Item i){
		int num=0;
		for(;num<items.length; num++){
			if(i==items[num])
				return num;
		}
		return -1;
		
	}

	public int[] getIdArray() {
		int[] idArray = new int[items.length];
		for(int i = 0; i < items.length; i++) {
			idArray[i] = -1;
			if(items[i] != null) {
				idArray[i] = items[i].getId();
			}
		}
		return idArray;
	}
	
	public Equipment[] getEquipment(){
		return equippedEquipment;
	}
	
	public void setEquipment(int index, Equipment equipment){
		if(equipment == null){
			return;
		}
		equippedEquipment[index] = equipment;
	}
}
