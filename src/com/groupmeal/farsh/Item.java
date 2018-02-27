package com.groupmeal.farsh;

public class Item {
	/**
	 * This contains the price of one of this item.
	 */
	public final float price;
	/**
	 * This is the name of the item.
	 */
	public final String itemName;
	/**
	 * This is how many of this item there are.
	 */
	public final int quantity;
	
	public Item (float price, String itemName, int quantity ) {
		this.price = price;
		this.itemName = itemName;
		this.quantity = quantity;
	}
}
