package DSph1;

import java.io.File;
import java.util.Scanner;

public class Customers {
	private LinkedList<Customer> customer_list;

  public Customers() {
		customer_list = new LinkedList<>();       
	}

	public Customers(LinkedList<Customer> customers) {
		customer_list=customers;
	}
	
	public Customer searchById(int id) {
		if (customer_list.empty()) 
			return null;//list is empty
		customer_list.findFirst();
		while (true) {
			if (customer_list.retrieve().getCustomerId() == id)
				return customer_list.retrieve();
			
			if (customer_list.last()) break;
			
			customer_list.findNext();
		}//end of while
		return null;
	}

	public void addCustomer(Customer c) {
		if (searchById(c.getCustomerId()) == null) {
			customer_list.insert(c);
			System.out.println("Customer "+ c.getName()+" added successfully");}
		else 
			System.out.println("Customer with ID:" + c.getCustomerId() + " already exists");
	}

	public void displayAll() {
		if (customer_list.empty()) {
			System.out.println("No customers found");
			return;}//list is empty
		
		System.out.println("****All Customers****");
		customer_list.findFirst();
		while (true) {
			customer_list.retrieve().display();
			
			if (customer_list.last()) 
				break;
			
			customer_list.findNext();
			}//end of while
	}
	
	public static Customer convert_String_to_Customer(String Line) {
		String a[]=Line.split(",");
		Customer p=new Customer(Integer.parseInt(a[0].trim()),a[1].trim(),a[2].trim());
		return p;
	}

	public void loadCustomers(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);
			if (read.hasNextLine()) read.nextLine(); 

			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.isEmpty()) continue;  
				Customer c = convert_String_to_Customer(line);
				customer_list.insert(c);
			}

			read.close();
			System.out.println("Customers loaded successfully!\n");
		} catch (Exception e) {
			System.out.println("Error loading customers from file, " + e.getMessage());
		}
	}

	//getter:
	public LinkedList<Customer> get_customers() { return customer_list; }
}

