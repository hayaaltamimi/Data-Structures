package DSph2;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;


public class Orders {
	private AVLTree<Integer,Order> ordersTree;

	public Orders() {
		ordersTree = new AVLTree<>();      
	}

	public Orders(AVLTree<Integer, Order> orders) {
		this.ordersTree=orders;
	}

	//Method that searches for an order by ID (null if not found)
	public Order searchOrder(int id) { 
		boolean found = ordersTree.find(id);
		if (!found)
			return null;
		return ordersTree.retrieve();
	}

	//Method that adds an order 
	public void addOrder(Order o) {
		int id = o.getOrderId();
		boolean inserted = ordersTree.insert(id, o);
		if (!inserted)
			System.out.println("Order with ID " + id + " already exists!");

	}

	//Method that removes an order with a given ID
	public void removeOrder(int id) {
		boolean found = ordersTree.find(id);
		if (!found) {
			System.out.println("Order is not found!");
			return ;}

		boolean removed = ordersTree.removeKey(id);
		if (!removed)
			System.out.println("Failed to remove order " + id);
	}

	//Method that updates the state of a specific order
	public void UpdateOrderState(int id, Order.Status newStatus) {
		Order ord=searchOrder(id);
		if(ord==null) {
			System.out.println("Order id is not found!");
			return;	}
		ord.setStatus(newStatus);
	}

	//Method that displays all orders 
	public void displayAllOrders() {
		if (ordersTree.empty()) {
			System.out.println("No orders found!");
			return;
		}
		System.out.printf("%-10s %-12s %-20s %-12s %-15s %-10s%n",
				"OrderID", "CustomerID", "ProductIDs", "TotalPrice", "Date", "Status");
		System.out.println("------------------------------------------------------------------------------------------------");
		LinkedList<Order> list = ordersTree.inOrdertraverseData();

		if (!list.empty()) {
			list.findFirst();
			while (true) {
				Order o = list.retrieve();
				if (o != null)
					o.display();

				if (list.last()) break;
				list.findNext();
			}
		}
		System.out.println("------------------------------------------------------------------------------------------------");
	}

	//Method that return a list of orders, between two given dates, sorted by date
	public LinkedList<Order> BetweenTwoDates(String date1, String date2) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate Ldate1 = LocalDate.parse(date1, formatter);
		LocalDate Ldate2 = LocalDate.parse(date2, formatter);

		LinkedList<Order> ordersBetweenDates = new LinkedList<>();
		AVLTree<Date, Order> treeByDate = ordersTree.intervalSearchDate(Ldate1, Ldate2);
		LinkedList<Order> tmp = treeByDate.inOrdertraverseData();

		if (!tmp.empty()) {
			tmp.findFirst();
			for (int i = 0; i < tmp.size(); i++) {
				ordersBetweenDates.insert(tmp.retrieve());
				if (tmp.last()) break;
				tmp.findNext();
			}
		}
		return ordersBetweenDates;
	}

	//Method that converts each line in the file to an order
	public static Order convert_String_to_product(String Line) {
		try {
			String[] a = Line.split(",");
			int orderId = Integer.parseInt(a[0].trim().replace("\"", ""));
			int customerId = Integer.parseInt(a[1].trim().replace("\"", ""));
			String productIds = a[2].trim().replace("\"", "");
			double totalPrice = Double.parseDouble(a[3].trim());

			LocalDate date;
			String dateStr = a[4].trim();

			if (dateStr.contains("-")) {
				date = LocalDate.parse(dateStr);
			} else {
				String[] parts = dateStr.split("/");
				int month = Integer.parseInt(parts[0]);
				int day = Integer.parseInt(parts[1]); 
				int year = Integer.parseInt(parts[2]);
				date = LocalDate.of(year, month, day);
			}

			Order.Status status;
			try {
				String statusStr = a[5].trim().toLowerCase();
				status = Order.Status.valueOf(statusStr);
			}
			catch (IllegalArgumentException e) {
				System.out.println("Unknown status: " + a[5] + " set to pending by default.");
				status = Order.Status.pending;
			}

			return new Order(orderId, customerId, productIds, totalPrice, date, status);
		} 
		catch (Exception e) {
			System.out.println("Error converting line to Order: " + Line);
			System.out.println("Error details: " + e.getMessage());
			return null;
		}
	}

	//Method that loads data from file
	public void loadOrders(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);        
			read.nextLine();

			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.isEmpty()) continue;
				Order ord = convert_String_to_product(line);
				if (ord != null) {
					addOrder(ord);
				}
			}//end of while
			read.close();
			System.out.println(ordersTree.size() + " orders loaded successfully!");
		} catch (Exception e) {
			System.out.println("Error loading orders from file,  " + e.getMessage());
		}
	}

	//Method the returns the orders, in order
	public LinkedList<Order> getOrdersList() { return ordersTree.inOrdertraverseData(); }
}

