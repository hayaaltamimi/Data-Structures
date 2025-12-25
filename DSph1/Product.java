package DSph1;
import java.io.File;
import java.util.Scanner;

public class Product {

	private int productId;
	private String name;
	private double price;
	private int stock;
	private LinkedList<Review> reviews;

	public Product(int productId, String name, double price, int stock) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.reviews = new LinkedList<>();
	}

	public void addReview(Review r) { reviews.insert(r); }

	public double getAverageRating() {
		if (reviews.empty()) 
			return 0;//list is empty

		reviews.findFirst();
		double sum = 0;
		int count = 0;

		while (true) {
			sum += reviews.retrieve().getRating();
			count++;

			if (reviews.last())
				break;//when end of list is reached
			reviews.findNext();
		}//end of while
		return sum / count;
	}

	public void displayReviews() {

		System.out.println("   -----------------------------------------------------------------------------------------");
		System.out.printf ("   | Reviews for product: %-35s \n", name);
		System.out.println("   -----------------------------------------------------------------------------------------");

		if (reviews.empty()) {
			System.out.printf("   | %-57s |\n", "No reviews.");
			System.out.println("   -----------------------------------------------------------------------------------------");
			return;
		}

		System.out.printf("   | %-8s | %-10s | %-8s | %-25s \n",
				"ReviewID", "Customer", "Rating", "Comment");
		System.out.println("   -----------------------------------------------------------------------------------------");

		reviews.findFirst();
		while (true) {
			Review r = reviews.retrieve();

			System.out.printf("   | %-8d | %-10d | %-8d | %-25s \n",
					r.getReviewId(),
					r.getCustomerId(),
					r.getRating(),
					r.getComment()
					);

			if (reviews.last())
				break;//when end of file is reached
			reviews.findNext();
		}//end of while

		System.out.println("   -----------------------------------------------------------------------------------------");
	}

	public void display() { 
		System.out.printf("%-12d %-25s %-10.2f %-8d %-8.1f%n", 
				productId, name, price, stock, getAverageRating()); 
	}

	//getters:
	public int getProductId() { return productId; }
	public String getName() { return name; }
	public double getPrice() { return price; }
	public int getStock() { return stock; }

	//setters:
	public void setName(String n) { name = n; }
	public void setPrice(double p) { price = p; }
	public void setStock(int s) { stock = s; }

}
