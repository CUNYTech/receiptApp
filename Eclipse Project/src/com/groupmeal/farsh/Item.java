package com.groupmeal.farsh;

/**
 * This class will keep track of a receipt item.
 * Specifically, it'll keep track of the item's
 * price, name, and how many of there are of the
 * item as well.
 */
public class Item {
	/**
	 * This is how much one of this item costs.
	 */
	private float price;
	/**
	 * This is the name of the item, as seen on
	 * the receipt.
	 */
	private String itemName;
	/**
	 * This is how many of this item there are in
	 * this current stack.
	 */
	private int quantity;
	/**
	 * Since this isn't pulled from a database,
	 * it might always be valid, so this might
	 * not be needed.
	 */
	private boolean valid;

	/**
	 * Creates a new item with the given parameters.
	 * 
	 * @param inPrice
	 * @param inName
	 * @param inAmount
	 */
	Item(float inPrice, String inName, int inAmount) {
		price = inPrice;
		itemName = inName;
		quantity = inAmount;
	}

	/**
	 * @see #price
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @see #itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @see #quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @see #valid
	 */
	public boolean isValid() {
		return valid;
	}
}
