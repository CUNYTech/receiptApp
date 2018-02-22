package com.groupmeal.farsh;
import java.util.*;
import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Vector<User> customer = new Vector<User>();
		String first, last, phone, email;
		long id;
		int numPpl;
		User users;
		
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
		}
}