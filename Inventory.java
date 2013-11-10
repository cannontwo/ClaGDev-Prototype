package com.cannon.basegame;


public class Inventory {
	private Item[] items;
	
	public Inventory() {
		items = new Item[16];
	}
	
	public void add(Item item) {
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				items[i] = item;
				break;
			}
		}
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
}
