package DSph1;

import java.io.File;
import java.util.Scanner;

public class Customer {
	private int customerId;
	private String name;
	private String email;
	private LinkedList<Order> orders;
	private LinkedList<Review> reviews;
	
	public Customer(int cid, String name, String email) {
		customerId=cid;
		this.name=name;
		this.email=email;
		orders=new LinkedList<>();
		reviews=new LinkedList<>();
	}

	public void addOrder(Order o) { orders.insert(o); }

	public void addReview(Review r) { reviews.insert(r); }

	public void display() {
	    System.out.printf("%-15d %-20s %-25s%n", customerId, name, email);
	    System.out.println("----------------------------------");
	}

	public void displayOrders() {
		if(orders.empty()) {
			System.out.println("You have no orders");
			return;}//list is empty
		
		orders.findFirst();
		System.out.println(name+"'s orders:");
		while (true) {
			orders.retrieve().display();
			if(orders.last()) break;
			orders.findNext();
		}//end of while
	}
	
	public void displayReviews() {
		if(reviews.empty()) {
			System.out.println("You have no reviews");
			return;}//list is empty
		
		reviews.findFirst();
		System.out.println(name+"'s reviews:");
		while (true) {
			reviews.retrieve().display();
			if(reviews.last()) break;
			reviews.findNext();
		}//end of while
	}
	
	//getters
	public LinkedList<Order> getOrders() { return orders; }
	public int getCustomerId() { return customerId; }
	public String getName() { return name; }
} 

