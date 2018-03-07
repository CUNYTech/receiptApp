package com.groupmeal.farsh;

import java.util.*;

public class Bill {
	private Vector<Item> items;
	private float subTotal;
	private float total;
	private float tip;
	private float tax;

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

	public Vector<Item> getItems() {
		return items;
	}

	public float getSubTotal() {
		return subTotal;
	}

	public float getTotal() {
		return total;
	}

	public float getTip() {
		return tip;
	}

	public float getTax() {
		return tax;
	}

	public String getReceipt() {
		return "-----------------\nSub-Total:$" + subTotal + "\ntax:$" + tax + "\ntip:$" + tip + "\ntotal:$" + total
				+ "\n+++++++++++++++++++++\n";
	}

}
