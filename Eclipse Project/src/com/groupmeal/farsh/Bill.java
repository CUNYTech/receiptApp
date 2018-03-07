package com.groupmeal.farsh;

import java.util.*;

/**
 * This class will allow us to calculate a bill for any
 * specific user. It will keep track of what items the user
 * bought, the total price for all the items. The tax rate,
 * and possibly many more.
 */
public class Bill {
	/**
	 * This will keep track of what items are
	 * being bought currently for this bill.
	 */
	private Vector<Item> items;
	/**
	 * This is the sum of the cost of all the
	 * items that are being bought.
	 */
	private float subTotal;
	/**
	 * This is the total after calculating the
	 * tax, the tip, and the sub total.
	 */
	private float total;
	/**
	 * This is the cost of the tip which depends
	 * on the tip rate that is passed into the
	 * constructor.
	 */
	private float tip;
	/**
	 * This is the cost of the tax. Similar to
	 * the tip, this will also depend on the
	 * value passed into the constructor.
	 */
	private float tax;

	/**
	 * Create a new Bill with the given items,
	 * the tip, and the tax as well.
	 * 
	 * @param items
	 * A list of all the items that are being
	 * bought.
	 * 
	 * @param tipRate
	 * This is a percent of the tip that should
	 * be given.
	 * 
	 * @param taxRate
	 * Just like the tip rate, this is a percent
	 * of how much the tip rate should be.
	 */
	public Bill(Vector<Item> items, float tipRate, float taxRate) {
		this.items = items;
		this.subTotal = 0;

		for (int i = 0; i < items.size(); i++) {
			this.subTotal = items.elementAt(i).getPrice() + this.subTotal;
		}

		this.tip = tipRate * this.subTotal;
		this.tax = taxRate * this.subTotal;
		this.total = this.subTotal + this.tax + this.tip;
	}

	/**
	 * @see #items
	 */
	public Vector<Item> getItems() {
		return items;
	}

	/**
	 * @see #subTotal
	 */
	public float getSubTotal() {
		return subTotal;
	}

	/**
	 * @see #total
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * @see #tip
	 */
	public float getTip() {
		return tip;
	}

	/**
	 * @see #tax
	 */
	public float getTax() {
		return tax;
	}

	/**
	 * This will return a string version of this
	 * bill including the total costs.
	 */
	public String getReceipt() {
		return "-----------------\nSub-Total:$" + subTotal + "\ntax:$" + tax + "\ntip:$" + tip + "\ntotal:$" + total
				+ "\n+++++++++++++++++++++\n";
	}

}
