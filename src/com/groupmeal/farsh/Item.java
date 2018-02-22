package com.groupmeal.farsh;

public class Item {
    private float price;
	private String itemName;
	private int amount;
	private boolean valid;
	
	Item(float inPrice, String inName, int inAmount ){
		price = inPrice;
		itemName = inName;
		amount = inAmount;
	}
	public float getPrice() {
		return price;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean isValid() {
		return valid;
	}
}
