package DSph1;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Orders {
	private LinkedList<Order> ordersList;

	public Orders(LinkedList<Order> orders) {
		this.ordersList=orders;
	}

	public Orders() {
		ordersList = new LinkedList<>();      
	}

	public Order searchOrder(int id) { 
		if(ordersList.empty()) 
			return null;	

		ordersList.findFirst();
		while(true) {
			if(ordersList.retrieve().getOrderId() == id) 
				return ordersList.retrieve();  //return when found

			if(ordersList.last()) 
				break;  //break if reached the last element in the list

			ordersList.findNext();  
		}//end of while
		return null;  //if id not found
	}

	public void UpdateOrderState(int id, Order.Status newStatus) {
		if(ordersList.empty()) { 
			System.out.println("The list is empty!");
			return;	}

		ordersList.findFirst();
		while(true) {
			if(ordersList.retrieve().getOrderId() == id) {
				ordersList.retrieve().setStatus(newStatus);
				System.out.println("Order " + id + " new status is: " + ordersList.retrieve().getStatus());
				return;
			}//when id is found
			if(ordersList.last()) break;//when the end of list is reached
			ordersList.findNext();
		}//end of while
		System.out.println("Order id not found");
	}

	public void removeOrder(int id) {
		if (ordersList.empty()) {
			System.out.println("The list is empty!");
			return; }

		ordersList.findFirst();
		while (true) {
			if (ordersList.retrieve().getOrderId() == id) {
				ordersList.remove();  //delete order
				System.out.println("Order " + id + " is removed");
				return;
			}//when id is found
			if (ordersList.last()) break;//when the end of list is reached

			ordersList.findNext();
		}//end of while
		System.out.println("Order ID not found!");
	}

	public void addOrder(Order o) {
		if (searchOrder(o.getOrderId()) == null)  
			ordersList.insert(o);
		else 
			System.out.println("Order with ID " + o.getOrderId() + " already exists!");
	}  

	
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
	    } catch (Exception e) {
	        System.out.println("Error converting line to Order: " + Line);
	        System.out.println("Error details: " + e.getMessage());
	        return null;
	    }
	}
   
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
			System.out.println("Orders loaded successfully!");
		} catch (Exception e) {
			System.out.println("Error loading orders from file,  " + e.getMessage());
		}
	}

	public LinkedList<Order> BetweenTwoDates(String date1, String date2) {
		LinkedList<Order> ordersbetweenDates = new LinkedList<Order>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		LocalDate Ldate1 = LocalDate.parse(date1, formatter);
		LocalDate Ldate2 = LocalDate.parse(date2, formatter);

		if (!ordersList.empty()) {
			ordersList.findFirst();

			while (true) {
				LocalDate orderDate = ordersList.retrieve().getOrderDate();
				
				if (!orderDate.isBefore(Ldate1) && !orderDate.isAfter(Ldate2)) {
					ordersbetweenDates.insert(ordersList.retrieve());
				}

				if (ordersList.last()) break;
				ordersList.findNext();
			}
		}
		return ordersbetweenDates;
	}

	public void displayAllOrders() {
		if (ordersList.empty()) {
			System.out.println("No orders found!");
			return;
		}
		System.out.printf("%-10s %-12s %-20s %-12s %-15s %-10s%n",
				"OrderID", "CustomerID", "ProductIDs", "TotalPrice", "Date", "Status");
		System.out.println("----------------------------------------------------------------------------");
		ordersList.findFirst();
		while (true) {
			if (ordersList.retrieve() != null) 
				ordersList.retrieve().display();

			if (ordersList.last()) break;
			ordersList.findNext();
		}//end of while
		System.out.println("----------------------------------------------------------------------------");
	}

	public LinkedList<Order> getOrdersList() { return ordersList; }
}
