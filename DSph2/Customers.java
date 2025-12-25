package DSph2;
import java.io.File;
import java.util.Scanner;

public class Customers {
	private AVLTree<Integer, Customer> customers;

	public Customers() {
		customers = new AVLTree<>();       
	}

	public Customers(AVLTree<Integer, Customer> customers) {
		this.customers=customers;
	}

	//Method that returns the customer with given id 
	public Customer searchById(int id) {
		if (customers.find(id)) {
			return customers.retrieve();
		}
		return null; 
	}

	//Method that adds customer to the tree (if the customer doesn't already exist)
	public void addCustomer(Customer c) {
		if (searchById(c.getCustomerId()) == null) {
			customers.insert(c.getCustomerId(), c);
			System.out.println("Customer "+ c.getName() + " added successfully");
		} else {
			System.out.println("Customer with ID:" + c.getCustomerId() + " already exists");
		}
	}

	public void displayAllCustomers() {
		if (customers.empty()) {
			System.out.println("No customers found!");
			return;
		}

		System.out.printf("%-15s %-20s %-25s%n",
				"CustomerID", "Name", "Email");
		System.out.println("------------------------------------------------------------------------------------------------");
		LinkedList<Customer> list = customers.inOrdertraverseData();

		if (!list.empty()) {
			list.findFirst();
			while (true) {
				Customer c = list.retrieve();
				if (c != null)
					c.display();

				if (list.last()) break;
				list.findNext();
			}
		}

		System.out.println("------------------------------------------------------------------------------------------------");
	}

	//Method that prints the customers in alphabetical order
	public void displayCustomersSorted() {
		if (customers.empty()) {
			System.out.println("No customers found!");
			return;
		}

		System.out.println("All customers sorted alphabetically:");
		System.out.println("------------------------------------------------------------------------------------------------");

		AVLTree<String, Customer> customersByName = new AVLTree<>();
		LinkedList<Customer> allCustomers = customers.inOrdertraverseData();

		allCustomers.findFirst();
		while (true) {
			Customer c = allCustomers.retrieve();
			if (c != null) {
				String key = c.getName().toLowerCase() + "_" + c.getCustomerId();
				customersByName.insert(key, c);
			}
			if (allCustomers.last()) break;
			allCustomers.findNext();
		}

		LinkedList<Customer> sorted = customersByName.inOrdertraverseData();

		System.out.printf("%-15s %-20s %-25s%n",
				"CustomerID", "Name", "Email");
		System.out.println("------------------------------------------------------------------------------------------------");
		if (!sorted.empty()) {
			sorted.findFirst();
			while (true) {
				Customer c = sorted.retrieve();
				if (c != null)
					c.display();  
				if (sorted.last()) break;
				sorted.findNext();
			}
		}
	}


	//Method that converts each line in the file to a customer
	public static Customer convert_String_to_Customer(String Line) {
		String a[] = Line.split(",");
		Customer customer = new Customer(Integer.parseInt(a[0].trim()), a[1].trim(), a[2].trim());
		return customer;
	}

	//Method that loads data from file
	public void loadCustomers(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);
			if (read.hasNextLine()) read.nextLine(); // Skip header

			int count = 0;
			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.isEmpty()) continue;  
				Customer customer = convert_String_to_Customer(line);
				customers.insert(customer.getCustomerId(), customer);
				count++;
			}

			read.close();
			System.out.println(count + " customers loaded successfully!");
		} catch (Exception e) {
			System.out.println("Error loading customers from file: " + e.getMessage());
		}
	}
}