package com.groupmeal.farsh;
import java.util.*;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Vector<User> customer = new Vector<User>();
		Vector<Item> items = new Vector<Item>();
		String first, last, phone, email, nameItem;
		long id;
		int numPpl, numItems, price, quantity;
		User users;
		Item item;
		
		System.out.print("Enter number of people: ");
		Scanner scan = new Scanner(System.in);
		numPpl = scan.nextInt();
		System.out.print(numPpl);
		for(int i = 0; i<numPpl; i++){
			
			System.out.print("Enter your firstname\n");
		    first = scan.next();
			System.out.print("Enter your lastname\n");
		    last = scan.next();
			System.out.print("Enter your id\n");
		    id = scan.nextLong();
			System.out.print("Enter your phone\n");
		    phone = scan.next();
			System.out.print("Enter your email\n");
		    email = scan.next();
		    users = new User(id, email, "PassHash", "Salt", first, last, phone, 11111, (byte) 0);
		    customer.add(users);
		    
		}
		
		System.out.print("Enter the number of items: ");
		numItems = scan.nextInt();
		
		for(int i = 1; i <= numItems; i++){
			System.out.print("Enter the name of Item " + i + ": ");
			nameItem = scan.next();
			
			System.out.print("Enter the price: ");
			price = scan.nextInt();
			
			System.out.print("Enter the quantity: ");
			quantity = scan.nextInt();
			item = new Item(price, nameItem, quantity);
			items.add(item);
		
		}
	}
}