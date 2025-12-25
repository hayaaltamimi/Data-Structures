package DSph1;

import java.io.File;
import java.util.Scanner;

public class ProductsManager {
	private LinkedList<Product> products;

	public ProductsManager() {
        products = new LinkedList<>();
    }

	public void addProduct(Product p) {
		if (searchById(p.getProductId()) != null) {
			System.out.println("Product already exists!");
			return;}//check if the product already exists

		products.insert(p);
		System.out.println("Product added successfully.");
	}

	public Product searchById(int id) {
		if (products.empty())
			return null;//list is empty

		products.findFirst();
		while (true) {
			if (products.retrieve().getProductId() == id)
				return products.retrieve();//when id is found

			if (products.last()) break;
			products.findNext();
		}//end of while
		return null;
	}

	public Product searchByName(String name) {
		if (products.empty())
			return null;//list is empty

		products.findFirst();
		while (true) {
			if (products.retrieve().getName().equalsIgnoreCase(name))
				return products.retrieve();//when name is found

			if (products.last())
				break;

			products.findNext();
		}//end of while
		return null;//if name is not found
	}

	public void removeProduct(int id) {
		Product target = searchById(id);
		if (target == null) {
			System.out.println("Product not found!");
			return;}//if id is not found

		products.remove();
		System.out.println("Product removed.");
	}

	public void updateProduct(int id, Product newP) {
		Product old = searchById(id);
		if (old == null) {
			System.out.println("Product not found!");
			return;
		}
		old.setName(newP.getName());
		old.setPrice(newP.getPrice());
		old.setStock(newP.getStock());
		System.out.println("Product updated.");
	}

	public void trackOutOfStock() {

		if (products.empty()) {
			System.out.println("No products.");
			return;}//list is empty

		boolean found = false;
		products.findFirst();
		while (true) {
			if (products.retrieve().getStock() == 0) {
				products.retrieve().display();
				found = true; }

			if (products.last())
				break;// when the end of list is reached
			products.findNext();
		}//end of while

		if (!found)
			System.out.println("All products in stock.");//no products are out of stock
	}

	public void displayAllProducts() {
		System.out.printf("%-12s %-25s %-10s %-8s %-8s%n",
				"ProductID", "Name", "Price", "Stock", "AvgRating");
		System.out.println("------------------------------------------------------------------------------------------------");

		if (products.empty()) {
			System.out.println("No products.");
			return;}//list is empty

		products.findFirst();
		while (true) {
			products.retrieve().display();

			products.retrieve().displayReviews();
			System.out.println("------------------------------------------------------------------------------------------------");

			if (products.last()) 
				break;
			products.findNext();
		}//end of while
	}

	public void top3ByRating() {
		if (products.empty()) {
			System.out.println("No products available.");
			return;}//list is empty

		Product first = null;
		Product second = null;
		Product third = null;
		products.findFirst();
		while (true) {
			Product p = products.retrieve();
			double avg = p.getAverageRating();
			if (first == null || avg > first.getAverageRating()) {
				third = second;
				second = first;
				first = p; }

			else if (second == null || avg > second.getAverageRating()) {
				third = second;
				second = p; }

			else if (third == null || avg > third.getAverageRating()) {
				third = p; }

			if (products.last()) 
				break;

			products.findNext();
		}//end of while

		System.out.println("\n=== Top 3 Products by Rating ===");
		if (first != null) {
			System.out.printf("   #1: %-25s | Rating: %.1f%n", first.getName(), first.getAverageRating());
		}
		if (second != null) {
			System.out.printf("   #2: %-25s | Rating: %.1f%n", second.getName(), second.getAverageRating());
		}
		if (third != null) {
			System.out.printf("   #3: %-25s | Rating: %.1f%n", third.getName(), third.getAverageRating());
		}
	}


	public Product convertLine(String line) { 
		String[] a = line.split(",");
		Product p = new Product(
				Integer.parseInt(a[0]),
				a[1],
				Double.parseDouble(a[2]),
				Integer.parseInt(a[3]));
		return p;
	}

	public void loadProducts(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);
			read.nextLine(); // skip header

			while (read.hasNextLine()) {
				String line = read.nextLine().trim();//to skip spaces
				if (line.isEmpty()) continue;

				Product p = convertLine(line);

				products.insert(p);   
			}//end of while

			read.close();
			System.out.println("Products loaded successfully.");
		}
		catch (Exception e) {
			System.out.println("Error loading products from file, " + e.getMessage());
		}
	}

	//getter:
	public LinkedList<Product> getProductsList() {
		return products;
	}
}
