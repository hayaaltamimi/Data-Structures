package DSph2;
import java.io.File;
import java.util.Scanner;

public class Products {
	private AVLTree<Integer, Product> productsAVL;

	public Products() {
		productsAVL = new AVLTree<>();
	}

	//Method that returns the product with given id (null if not found)
	public Product searchById(int id) {
		if (productsAVL.find(id)) {
			return productsAVL.retrieve();
		}
		return null; 
	}

	//Method that adds a product
	public boolean addProduct(Product p) {
		if (productsAVL.find(p.getProductId())) {
			System.out.println("Product with ID " + p.getProductId() + " already exists!");
						return false;
		}

		boolean added = productsAVL.insert(p.getProductId(), p);
		if (added) {
			System.out.println("Product '" + p.getName() + "' (ID: " + p.getProductId() + ") added successfully!");
		}
		return added;
	}

	//Method that removes a product with a given ID
	public boolean removeProduct(int id) {
		boolean removed = productsAVL.removeKey(id);
		if (removed) {
			System.out.println("Product with ID " + id + " removed successfully!");
		} else {
			System.out.println("Product with ID " + id + " not found - cannot remove!");
		}
		return removed;
	}

	//Method that updates specific product
	public boolean updateProduct(int id, Product newProduct) {
		if (productsAVL.find(id)) {
			productsAVL.update(newProduct);
			System.out.println("Product with ID " + id + " updated successfully!");
			return true;
		} else {
			System.out.println("Product with ID " + id + " not found - cannot update!");
			return false;
		}
	}

	//Method that displays all products
	public void displayAllProducts() {
		System.out.printf("%-12s %-25s %-10s %-8s %-8s%n",
				"ProductID", "Name", "Price", "Stock", "AvgRating");
		System.out.println("------------------------------------------------------------------------------------------------");

		LinkedList<Product> allProducts = productsAVL.inOrdertraverseData();
		if (allProducts.empty()) {
			System.out.println("No products.");
			return;
		}

		allProducts.findFirst();
		while (true) {
			Product p = allProducts.retrieve();
			p.display();
			if (allProducts.last()) break;
			allProducts.findNext();
		}
		System.out.println("------------------------------------------------------------------------------------------------");
	}

	//Method that finds all products within a price range
	public void displayProductsByPriceRange(double minPrice, double maxPrice) {
		LinkedList<Product> productsInRange = productsAVL.intervalSearchprice(minPrice, maxPrice);

		if (productsInRange.empty()) {
			System.out.println("No products found in price range $" + minPrice + " - $" + maxPrice);
			return;
		}

		System.out.println("\n=== Products in Price Range: $" + minPrice + " - $" + maxPrice + " ===");
		System.out.println("Found " + productsInRange.size() + " products");
		System.out.printf("%-12s %-25s %-10s %-8s %-8s%n",
				"ProductID", "Name", "Price", "Stock", "AvgRating");
		System.out.println("------------------------------------------------------------------------------------------------");

		productsInRange.findFirst();
		while (true) {
			Product p = productsInRange.retrieve();
			p.display();

			if (productsInRange.last()) break;
			productsInRange.findNext();
		}
		System.out.println("------------------------------------------------------------------------------------------------");
	}

	//Method that tracks products that are out of stock
	public void trackOutOfStock() {
		if (productsAVL.empty())
			System.out.println("empty Products data");
		else 
		productsAVL.inOrdertraverseOutStock();
	}

	//Method that prints the 3 highest rated products
	public void top3ByRating() {
		LinkedList<Product> allProducts = productsAVL.inOrdertraverseData();
		if (allProducts.empty()) {
			System.out.println("No products available.");
			return;
		}

		AVLTree<Integer, Product> ratingTree = new AVLTree<>();

		allProducts.findFirst();
		int sequence = 0; // To handle duplicate ratings
		while (true) {
			Product p = allProducts.retrieve();
			double rating = p.getAverageRating();

			// negative for descending
			// Add sequence number to handle duplicates
			int key = -(int)(rating * 10000) + sequence;
			sequence++;

			ratingTree.insert(key, p);

			if (allProducts.last()) break;
			allProducts.findNext();
		}

		System.out.println("\n*** Top 3 Products by Rating ***"); //highest first
		LinkedList<Product> sorted = ratingTree.inOrdertraverseData();
		sorted.findFirst();

		for (int i = 0; i < 3 && !sorted.empty(); i++) {
			Product p = sorted.retrieve();
			System.out.printf("   #%d: %-25s | Rating: %.1f%n", 
					i + 1, p.getName(), p.getAverageRating());

			if (sorted.last()) break;
			sorted.findNext();
		}
	}    

	//Prints the customers who reviewed a certain product, and their reviews
	public void displayCustomersWhoReviewedProduct(int productId, Customers customers) {

		Product product = searchById(productId);
		if (product == null) {
			System.out.println("Product with ID " + productId + " not found.");
			return;
		}


		LinkedList<Review> reviews = product.getReviews();
		if (reviews.empty()) {
			System.out.println("No customers reviewed product: " + product.getName());
			return;
		}


		AVLTree<Integer, Review> sortedReviews = new AVLTree<>();

		reviews.findFirst();
		while (true) {
			Review r = reviews.retrieve();
			sortedReviews.insert(r.getCustomerId(), r);  
			if (reviews.last()) break;
			reviews.findNext();
		}


		System.out.println("\nCustomers who reviewed: " + product.getName() + " (Sorted by Customer ID) ==="); //ascending (lowest customer id first)
		System.out.println("------------------------------------------------------------------------------------------------");
		LinkedList<Review> sortedList = sortedReviews.inOrdertraverseData();
		sortedList.findFirst();
		while (true) {
			Review r = sortedList.retrieve();
			Customer customer = customers.searchById(r.getCustomerId());
			if (customer != null) {
				System.out.printf("Customer ID: %-5d | Name: %-20s | Rating: %d stars | Comment: %s%n",
						customer.getCustomerId(), customer.getName(), r.getRating(), r.getComment());
			}
			if (sortedList.last()) break;
			sortedList.findNext();
		}
	}

	//Method that prints the 3 most reviewed products
	public void top3ByMostReviewed() {
		LinkedList<Product> allProducts = productsAVL.inOrdertraverseData();
		if (allProducts.empty()) {
			System.out.println("No products available.");
			return;
		}

		AVLTree<Integer, Product> reviewTree = new AVLTree<>();

		allProducts.findFirst();
		int sequence = 0;
		int maxReviews = -1;
		boolean allEqual = true;

		while (true) {
			Product p = allProducts.retrieve();
			int reviewCount = getReviewCount(p);

			// Check if all products have same review count
			if (maxReviews == -1) {
				maxReviews = reviewCount;
			} else if (reviewCount != maxReviews) {
				allEqual = false;
			}

			int key = -reviewCount * 1000 + sequence;
			sequence++;
			reviewTree.insert(key, p);

			if (allProducts.last()) break;
			allProducts.findNext();
		}

		System.out.println("\n Top 3 Most Reviewed Products ===");
		//highest first

		if (allEqual) {
			System.out.println("   Note: All products have " + maxReviews + " reviews each");
		}

		LinkedList<Product> sorted = reviewTree.inOrdertraverseData();
		sorted.findFirst();

		int count = 0;
		while (count < 3 && !sorted.empty()) {
			Product p = sorted.retrieve();
			count++;
			System.out.printf("   #%d: %-25s | Reviews: %d%n", 
					count, p.getName(), getReviewCount(p));

			if (sorted.last()) break;
			sorted.findNext();
		}
	}

	// Helper method to count reviews
	private int getReviewCount(Product p) {
		if (p == null || p.getReviews().empty()) return 0;

		int count = 0;
		LinkedList<Review> reviews = p.getReviews();
		reviews.findFirst();
		while (true) {
			count++;
			if (reviews.last()) break;
			reviews.findNext();
		}
		return count;
	}

	//Method that converts each line in the file to a product
	public static Product convertLine(String Line)
	{
		String a[]=Line.split(",");
		Product p=new Product(Integer.parseInt(a[0]), a[1],Double.parseDouble(a[2]),Integer.parseInt(a[3]));
		return p;
	}

	//Method that loads data from file
	public void loadProducts(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);



			read.nextLine().trim(); // Skip header
			int count = 0;

			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (!line.isEmpty()) {                  
					Product p = convertLine(line);

					if (!productsAVL.find(p.getProductId())) {
						productsAVL.insert(p.getProductId(), p);
						count++;
					}             
				}
			}

			read.close();
			System.out.println( count + " products loaded successfully!");
		} catch (Exception e) {
			System.out.println("Error reading file: " + e.getMessage());
		}
	}

	//getter:
	public LinkedList<Product> getAllProducts() { return productsAVL.inOrdertraverseData();}
}