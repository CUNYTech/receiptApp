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
		int numPpl, numItems, quantity;
		float price;
		User users;
		Item item;
		
		//enter person information
		System.out.print("Enter number of people: ");
		Scanner scan = new Scanner(System.in);
		numPpl = scan.nextInt();
//		for(int i = 0; i<numPpl; i++){
//			
//			System.out.print("\nUser"+ (i+1)+"\n");
//			System.out.print("Enter your firstname\n");
//		    first = scan.next();
//			System.out.print("Enter your lastname\n");
//		    last = scan.next();
//			System.out.print("Enter your id\n");
//		    id = scan.nextLong();
//			System.out.print("Enter your phone\n");
//		    phone = scan.next();
//			System.out.print("Enter your email\n");
//		    email = scan.next();
//		    users = new User(id, email, "PassHash", "Salt", first, last, phone, 11111, (byte) 0);
//		    customer.add(users);
		    
//		}
		//enter item information
		System.out.print("Enter the number of items: ");
		numItems = scan.nextInt();
		
		for(int i = 0; i < numItems; i++){
			System.out.print("Enter the name of Item " + (i+1) + ": ");
			nameItem = scan.next();
			
			System.out.print("Enter the price: ");
			price = scan.nextFloat();
			
			System.out.print("Enter the quantity: ");
			quantity = scan.nextInt();
			item = new Item(price, nameItem, quantity);
			items.add(item);		
		}
		
		//select item
		String selectItem;
		
		for(int i = 0; i<numPpl; i++){
			Vector <Item>vec = new Vector<Item>();
			
			System.out.print("\nUser"+ (i+1)+" enter your order\n");
			selectItem = scan.next();			
			String[] temp = selectItem.split(",");
			
			for(int j=0;j<temp.length;j++){
				vec.add(items.elementAt(Integer.parseInt(temp[j])-1));				
			}
			
			//print receipt
			System.out.print("\nUser"+ (i+1)+" receipt:\n");
			Bill bill = new Bill(vec,0.15f,0.08775f);
			System.out.print(bill.getReceipt());
		}
		scan.close();
	}
}