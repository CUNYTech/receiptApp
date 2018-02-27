package com.groupmeal.farsh;

public class Item {
    private float price;
	private String itemName;
	private int quantity;
	private boolean valid;
	
	Item(float inPrice, String inName, int inAmount ){
		price = inPrice;
		itemName = inName;
		quantity = inAmount;
	}
	public float getPrice() {
		return price;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public boolean isValid() {
		return valid;
	}
}
