package DSph1;

import java.util.InputMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class Main {
	public static Scanner input = new Scanner (System.in);

	//Products:
	static ProductsManager pm = new ProductsManager();
	public static LinkedList<Product>  products;

	//Customer:
	public  static LinkedList<Customer> customers=new LinkedList<Customer>();
	public static Customers cdata = new Customers(customers);

	//Order:
	public static Orders order = new Orders();
	public static LinkedList<Order>  orders;

	// Reviews
	public static ReviewsManager rm=new ReviewsManager(pm, cdata);;
	public static LinkedList<Review> reviews;


	//=====================================================================================================================
	public static boolean loadData() {
		try {
			System.out.println("---LOADING SYSTEM DATA---");

			System.out.println("Loading products...");
			pm.loadProducts("products.csv");
			products = pm.getProductsList();
			if (products.size() == 0) return false;
			System.out.println("Products loaded: " + products.size());
			System.out.println("-----------------------------------");

			System.out.println("Loading customers...");
			cdata.loadCustomers("customers.csv");
			customers = cdata.get_customers();
			if (customers.size() == 0) return false;
			System.out.println("Customers loaded: " + customers.size());

			System.out.println("-----------------------------------");

			System.out.println("Loading orders...");
			order.loadOrders("orders.csv");
			orders = order.getOrdersList();
			System.out.println("Orders loaded: " + orders.size());
			if (orders.size() == 0) return false;
			System.out.println("-----------------------------------");

			System.out.println("Loading reviews...");
			rm.loadReviews("reviews.csv");
			reviews=rm.getReviews();			
			if (reviews.size() == 0) return false;
			System.out.println("Reviews loaded: " + reviews.size());
			System.out.println("-----------------------------------");

			System.out.println("All data loaded successfully!");
			return true;

		} catch (Exception e) {
			System.out.println("Error loading data: " + e.getMessage());
			return false;
		}
	}

	//=====================================================================================================================
	public static int mainMenu() {
		System.out.println("\n===== MAIN MENU =====");
		System.out.println("--------------------------");
		System.out.println("1. Products");
		System.out.println("2. Customers");
		System.out.println("3. Orders");
		System.out.println("4. Reviews");
		System.out.println("5. Exist");
		System.out.println("Enter your choice");
		return input.nextInt();
	}
	//=====================================================================================================================
	public static void productsMenu() {
		int choice=0;
		do {
			System.out.println("\n===== PRODUCTS MENU =====");
			System.out.println("1. Display All Products");
			System.out.println("2. Search Product by ID");
			System.out.println("3. Search Product by Name");
			System.out.println("4. Track Out of Stock Products");
			System.out.println("5. Show Top 3 Rated Products");
			System.out.println("6. Add New Product");
			System.out.println("7. Remove Product");
			System.out.println("8. Update Product");
			System.out.println("9. Return to Main Menu");
			System.out.print("Enter your choice: ");

			try {
				choice = input.nextInt();

				switch (choice) {
				case 1:
					System.out.println("\n-----------------------------------------ALL PRODUCTS-----------------------------------------");
					pm.displayAllProducts();
					break;

				case 2:
					System.out.print("Enter Product ID to search: ");
					int productId = input.nextInt();
					Product foundById = pm.searchById(productId);
					if (foundById != null) {
						System.out.println("\nProduct Found:");
						foundById.display();
						foundById.displayReviews();
					} else {
						System.out.println("Product with ID " + productId + " not found.");
					}
					break;

				case 3:
					System.out.print("Enter Product Name to search: ");
					input.nextLine(); 
					String productName = input.nextLine().trim(); 
					Product foundByName = pm.searchByName(productName);
					if (foundByName != null) {
						System.out.println("\nProduct Found:");
						foundByName.display();
						foundByName.displayReviews();
					} else {
						System.out.println("Product with name '" + productName + "' not found.");
					}
					break;

				case 4:
					System.out.println("\n--- OUT OF STOCK PRODUCTS ---");
					pm.trackOutOfStock();
					break;

				case 5:
					pm.top3ByRating();
					break;

				case 6: 
					System.out.println("\n--- ADD NEW PRODUCT ---"); 
					System.out.print("Enter Product ID: "); 
					int newId = input.nextInt(); 
					input.nextLine();  
					System.out.print("Enter Product Name: "); 
					String newName = input.nextLine(); 
					System.out.print("Enter Price: "); 
					double newPrice = input.nextDouble(); 
					System.out.print("Enter Stock Quantity: "); 
					int newStock = input.nextInt(); 

					Product newProduct = new Product(newId, newName, newPrice, newStock); 
					pm.addProduct(newProduct); 
					break;

				case 7:
					System.out.print("Enter Product ID to remove: ");
					int removeId = input.nextInt();
					pm.removeProduct(removeId);
					break;

				case 8:
					System.out.print("Enter Product ID to update: ");
					int updateId = input.nextInt();
					Product existing = pm.searchById(updateId);
					if (existing != null) {
						input.nextLine(); 
						System.out.print("Enter New Name: ");
						String updatedName = input.nextLine();
						System.out.print("Enter New Price: ");
						double updatedPrice = input.nextDouble();
						System.out.print("Enter New Stock: ");
						int updatedStock = input.nextInt();

						Product updatedProduct = new Product(updateId, updatedName, updatedPrice, updatedStock);
						pm.updateProduct(updateId, updatedProduct);
					} else {
						System.out.println("Product not found.");
					}
					break;

				case 9:
					System.out.println("Returning to Main Menu...");
					break;

				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please try again");
				input.nextLine(); // Clear invalid input
			}
		} while (choice != 9);
	}
	
	//=====================================================================================================================
	public static void CustomersMenu() {
		int choice;
		do {
			System.out.println("\n===== CUSTOMER MENU =====");
			System.out.println("1. Register new customer");
			System.out.println("2. Place New Order for specific customer");
			System.out.println("3. View Order history for specific customer");
			System.out.println("4. Return Main menu");
			System.out.print("Enter your choice: ");

			try {
				choice = input.nextInt();

				switch (choice) {
				case 1:
					System.out.print("Enter ID: ");
					int custId = input.nextInt();
					input.nextLine(); // Clear buffer
					System.out.print("Enter name: ");
					String name = input.nextLine();
					
					System.out.print("Enter email: ");
					String email = input.nextLine();
					
                    while( !email.contains("@")||email.indexOf("@")==0||
	             	email.indexOf("@")==email.length() - 1||
	            	email.lastIndexOf(".")<=email.indexOf("@") + 1||
	            	email.lastIndexOf(".") == email.length() - 1||email.contains(" ")  ) {
                    	System.out.print("Enter a valid email: ");
    				    email = input.nextLine();
                    }
					cdata.addCustomer(new Customer(custId, name, email));
					break;

				case 2: 
					placeOrder(); 
					break;

				case 3:
					System.out.print("Enter ID: ");
					int cId = input.nextInt();
					Customer customer = cdata.searchById(cId);
					if (customer == null) {
						System.out.println("Customer with ID " + cId + " not found");
					} else {
						customer.displayOrders();
					}
					break;

				case 4:
					System.out.println("Returning to Main Menu...");
					break;

				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please try again");
				input.nextLine(); // Clear invalid input
				choice = 0; // Reset choice to stay in menu
			}
		} while (choice != 4);
	}
	
	//=====================================================================================================================
	public static void OrdersMenu() {
		int choice = 0;
		do {
			System.out.println("1. Place New Order");
			System.out.println("2. Cancel Order");
			System.out.println("3. Update Order (Status)");
			System.out.println("4. Search By ID(liner)");
			System.out.println("5. All orders between two dates");
			System.out.println("6. Return Main menu");
			System.out.print("Enter your choice: ");

			try {
				choice = input.nextInt();

				switch (choice) {
				case 1:
					placeOrder();
					break;

				case 2:
					cancelOrder();
					break;

				case 3:
					System.out.println("update to new status...");
					if (orders.empty())
						System.out.println("empty Orders data");
					else {
						System.out.print("Enter order ID: ");
						int orderID = input.nextInt();
						input.nextLine(); // Clear buffer
						System.out.print("enter new status: ");
						String st = input.next();
						Order.Status s = Order.Status.valueOf(st.toUpperCase());
						order.UpdateOrderState(orderID, s);
					}
					break; 

				case 4:
					if (orders.empty())
						System.out.println("empty Orders data");
					else {
						System.out.print("Enter order ID: ");
						int orderID = input.nextInt();
						if(order.searchOrder(orderID)==null)
						System.out.println("order not found");
						else
						order.searchOrder(orderID).display();
					}
					break;

				case 5:
					
					System.out.print("Enter first date (M/d/yyyy): ");
				    String date1 = input.next();
				    System.out.print("Enter second date (M/d/yyyy): ");
				    String date2 = input.next();
				    
				    try {
				       LinkedList<Order> dateOrders = order.BetweenTwoDates(date1, date2);
				        
				       if (dateOrders.empty()) 
				            System.out.println("No orders found between " + date1 + " and " + date2);
				        else {
				            System.out.println("\n--- ORDERS BETWEEN " + date1 + " AND " + date2 + "---");
				            System.out.printf("%-10s %-12s %-20s %-12s %-15s %-10s%n","OrderID", "CustomerID", "ProductIDs", "TotalPrice", "Date", "Status");
				            System.out.println("----------------------------------------------------------------------------");
				            
				            dateOrders.findFirst();
				            while (true) {
				                dateOrders.retrieve().display();
				                if (dateOrders.last()) break;
				                dateOrders.findNext();
				            }//end while
				            System.out.println("----------------------------------------------------------------------------");
				            System.out.println("Total orders found: " + dateOrders.size());
				        }//end else
				    } catch (DateTimeParseException e) {
				        System.out.println("Invalid date format! Please use M/d/yyyy (e.g., 11/14/2025)");
				    } catch (Exception e) {
				        System.out.println("Error: " + e.getMessage());
				    }
				    break;
				case 6:
					System.out.println("Returning to Main Menu...");
					break;

				default:
					System.out.println("Invalid choice! Try again");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please try again");
				input.nextLine(); // Clear invalid input
			}
			catch (IllegalArgumentException e) {
				System.out.println("Invalid input! Please try again");
				input.nextLine(); // Clear invalid input
			}
			
		} while (choice != 6);
	}
	
	//=====================================================================================================================
	public static void ReviewsMenu() {
		int choice = 0;
		do {
			System.out.println("\n=== REVIEWS MENU ===");
			System.out.println("1. Add Review");
			System.out.println("2. Edit Review (rating, comment)");
			System.out.println("3. Get Average Rating for Product");
			System.out.println("4. Top 3 Products by Rating");
			System.out.println("5. Common Highly Rated Products Between 2 Customers");
			System.out.println("6. Display All Reviews");
			System.out.println("7. Display Reviews by Customer");
			System.out.println("8. Return to Main Menu");
			System.out.print("Enter your choice: ");

			try {
				choice = input.nextInt();

				switch(choice) {
				case 1: 
					System.out.print("Enter Review ID: "); 
					int reviewId = input.nextInt(); 
					System.out.print("Enter Product ID: "); 
					int productId = input.nextInt(); 
					System.out.print("Enter Customer ID: "); 
					int custId = input.nextInt(); 
					System.out.print("Enter Rating (1-5): "); 
					int rating = input.nextInt(); 
					input.nextLine(); // Clear buffer 
					System.out.print("Enter Comment: "); 
					String comment = input.nextLine(); 

					Review newReview = new Review(reviewId, productId, custId, rating, comment); 
					rm.addReview(newReview); 
					break;

				case 2:
					System.out.print("Enter Review ID to edit: ");
					int editReviewId = input.nextInt();
					Review existingReview = rm.searchById(editReviewId);
					if (existingReview != null) {
						System.out.print("Enter new Rating (1-5): ");
						int newRating = input.nextInt();
						input.nextLine(); // Clear buffer
						System.out.print("Enter new Comment: ");
						String newComment = input.nextLine();

						Review updatedReview = new Review(editReviewId, existingReview.getProductId(), 
								existingReview.getCustomerId(), newRating, newComment);
						rm.updateReview(editReviewId, updatedReview);
					} else {
						System.out.println("Review not found!");
					}
					break;

				case 3:
					System.out.print("Enter Product ID: ");
					int avgProductId = input.nextInt();
					Product product = pm.searchById(avgProductId);
					if (product != null) {
						double avgRating = product.getAverageRating();
						System.out.printf("Average rating for product '%s': %.1f%n", 
								product.getName(), avgRating);
					} else {
						System.out.println("Product not found!");
					}
					break;

				case 4:
					System.out.println("\n--- TOP 3 RATED PRODUCTS ---");
					pm.top3ByRating();
					break;

				case 5:
					System.out.print("Enter First Customer ID: ");
					int custId1 = input.nextInt();
					System.out.print("Enter Second Customer ID: ");
					int custId2 = input.nextInt();
					rm.commonHighlyRatedProducts(custId1, custId2);
					break;

				case 6:
					System.out.println("\n--- ALL REVIEWS ---");
					rm.displayAllReviews();
					break;

				case 7:
					System.out.print("Enter Customer ID: ");
					int customerId = input.nextInt();
					rm.reviewsByCustomer(customerId);
					break;

				case 8:
					System.out.println("Returning to Main Menu...");
					break;

				default:
					System.out.println("Invalid choice! Please try again.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input! Please try again");
				input.nextLine(); // Clear invalid input
			}
		} while (choice != 8);
	}


	//=====================================================================================================================
	public static void placeOrder() {


		Order new_order = new Order ();
		double total_price = 0;
		//read&validate Order id
		System.out.println("Enter order ID: ");
		int oid = input.nextInt();
		while (order.searchOrder(oid)!=null)
		{
			System.out.println(" order id is available , try again");
			oid = input.nextInt();
		}
		//read&validate customer id
		System.out.println("Enter customer ID:");
		int cid = input.nextInt();
		while(cdata.searchById(cid)==null)
		{
			System.out.println("Re-enter customer ID, is not available , try again");
			cid = input.nextInt();
		}
		//reading product
		boolean flag=false ;
		char answer = 'y';
		while (answer == 'y' || answer == 'Y')
		{
			System.out.println("Enter product ID:");
			int pid = input.nextInt();


			products.findFirst();
			for ( int i = 0 ;  i < products.size() ;i++)
			{
				if (products.retrieve().getProductId() == pid)
				{
					if (products.retrieve().getStock() == 0)
						System.out.println("product out of stock , try another time");
					else //in stock
					{
						Product pp = products.retrieve();
						products.remove();
						pp.setStock(pp.getStock()-1);
						products.insert(pp);
						System.out.println("product added to order");
						flag = true;

						new_order.addId(pp.getProductId());
						total_price += pp.getPrice();
					}
					break;
				}
			products.findNext();
			}

			if (!flag)
				System.out.println("No such product id");


			System.out.println("Do you want to continue adding product? (Y/N)");
			answer = input.next().charAt(0);
		}
		if (total_price == 0) {
		    System.out.println("Cannot complete order - no products added!");
		    System.out.println("Order cancelled.");
		    return;
		}		
		
		//reading &validate status*/
		LocalDate Ldate = null;
		boolean validDate = false;
		while (!validDate) {
		    try {
		        System.out.println("Enter order date (dd/mm/yyyy):");
		        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		        Ldate = LocalDate.parse(input.next(), dateformat);
		        
		        // Check if date is in the past
		        LocalDate today = LocalDate.now();
		        if (Ldate.isBefore(today)) {
		            System.out.println("Error: Date cannot be in past!");
		            System.out.println("Please enter today or future date");
		        } else {
		            validDate = true;
		        }
		        
		    } catch (DateTimeParseException e) {
		        System.out.println("Invalid date format!");
		        System.out.println("Please enter date in dd/mm/yyyy");
		        input.nextLine(); // Clear invalid input
		    }
		}
		
		Order.Status s = null;
		flag = false;

		while (!flag) {
			System.out.println("Enter new status (pending, shipped, delivered, cancelled):");
			String status = input.next().toLowerCase();
			try {
				s = Order.Status.valueOf(status);
				flag = true;   
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid status! Try again.");
			}
		}
		new_order.setOrderId(oid);
		new_order.setCustomerId(cid);
		new_order.setTotalPrice(total_price);
		new_order.setOrderDate(Ldate);
		new_order.setStatus(s);

		orders.insert(new_order);
		// add order to customer list
		customers.findFirst();
		for(int i = 0 ; i < customers.size(); i++)
		{
			if (customers.retrieve().getCustomerId() == new_order.getCustomerId())
			{
				Customer cust = customers.retrieve();
				customers.remove();
				cust.addOrder(new_order);
				customers.insert(cust);
				break;
			}
			customers.findNext();
		}   

		System.out.println("Order had been added ");
		new_order.display();   	}
	//=====================================================================================================================
	public static void cancelOrder() {
		System.out.println("Enter order ID to cancel: ");
		int oid = input.nextInt();

		// البحث عن الطلب
		Order cancel_order = order.searchOrder(oid);
		while (cancel_order == null) {
			System.out.println("Re-enter order id, is not available , try again");
			oid = input.nextInt();
			cancel_order = order.searchOrder(oid);
		}

		order.removeOrder(oid);

		customers.findFirst();
		for (int i = 0; i < customers.size(); i++) {
			if (customers.retrieve().getCustomerId() == cancel_order.getCustomerId()) {
				Customer cust = customers.retrieve();
				customers.remove();

				LinkedList<Order> custOrders = cust.getOrders();
				if (!custOrders.empty()) {
					custOrders.findFirst();
					while (true) {
						if (custOrders.retrieve().getOrderId() == oid) {
							custOrders.remove();
							break;
						}
						if (custOrders.last()) break;
						custOrders.findNext();
					}
				}

				customers.insert(cust);
				break;
			}
			customers.findNext();
		}
		System.out.println("Order " + oid + " has been cancelled successfully");
	}

	//=====================================================================================================================


	//Main:
	public static void main(String[] args) {
		boolean dataloaded=loadData();
		int choice;
		if(dataloaded) {
			do {
				choice = mainMenu();
				switch (choice)
				{
				case 1:
					productsMenu();
					break;
				case 2:
					CustomersMenu();
					break;
				case 3:
					OrdersMenu();
					break;
				case 4:
					ReviewsMenu();
					break;
				case 5:
					System.out.println("Exiting...");
					break;
				default:
					System.out.println("invalid choice! try again");
				}//end of switch
			}while (choice != 5);//end of do while
		}//end of if 
		else
			System.out.println("Cannot proceed unless all files are loaded successfully");
	}//end of main


}
