package DSph2;
public class Customer {

	private int customerId;
	private String name;
	private String email;
	private LinkedList<Order> orders;
	private LinkedList<Review> reviews;

	public Customer(int id, String name, String email) {
		this.customerId = id;
		this.name = name;
		this.email = email;
		orders = new LinkedList<>();
		reviews=new LinkedList<>();
	}

	//Method that adds an order to the orders list
	public void addOrder(Order o) 
	{orders.insert(o);}

	//Method that adds a review to the reviews list
	public void addReview(Review r) 
	{reviews.insert(r);}

	//Method to display the customer's info
	public void display() {
		System.out.printf("%-15d %-20s %-25s%n", customerId, name, email);
	}

	//Method that displays a customer's orders:
	public void displayOrders() {
		if (orders.empty()) {System.out.println(name+" doesn't have any orders");}//If the customer hasn't made any orders
		else {//A loop to go through the orders list, and print each order
			System.out.println(name+"'s Orders:");
			orders.findFirst();
			System.out.printf("%-10s %-12s %-20s %-12s %-15s %-10s%n",
					"OrderID", "CustomerID", "ProductIDs", "TotalPrice", "Date", "Status");
			System.out.println("------------------------------------------------------------------------------------------------");
			while (true) {
				orders.retrieve().display();
				if (orders.last()) break;//when reaching end of list
				orders.findNext();
			}//end of while
		}//end of else
	}

	//getters:
	public int getCustomerId() { return customerId; }
	public String getName() { return name; }
	public LinkedList<Order> getOrders() { return orders; }
}


