package DSph2;
import java.io.File;
import java.util.Scanner;

public class ReviewsManager {

	private LinkedList<Review> reviews;
	private Products pm;
	private Customers cm;

	public ReviewsManager() {
		this.pm = null;
		this.cm = null;
		this.reviews = null;
	}

	public ReviewsManager(Products pm,Customers cm) {
		this.pm = pm;
		this.cm = cm;
		this.reviews = new LinkedList<>();
	}

	//Method that returns the customer with given id 
	public Review searchById(int id) {
		if (reviews.empty())
			return null;//list is empty

		reviews.findFirst();
		while (true) {
			if (reviews.retrieve().getReviewId() == id)
				return reviews.retrieve();

			if (reviews.last()) 
				break;//when end of list is reached

			reviews.findNext();
		}//end of while
		return null;//if id is not found
	}

	//Method that adds a review
	public void addReview(Review r) {
		if (searchById(r.getReviewId()) != null) {
			System.out.println("Review with ID " + r.getReviewId() + " already exists.");
			return;
		}
		Product p = pm.searchById(r.getProductId());
		Customer c = cm.searchById(r.getCustomerId());
		if (p == null) {
			System.out.println("Product does not exist for this review.");
			return;
		}//if product id is not found

		if (c == null) {
			System.out.println("Customer does not exist for this review.");
			return;
		}//customer id not found

		reviews.insert(r);

		p.addReview(r);
		c.addReview(r);
		System.out.println("Review added successfully");
	}

	//Method that updates a certain review
	public void updateReview(int id, Review newR) {
		Review old = searchById(id);
		if (old == null) {
			System.out.println("Review not found.");
			return;}
		old.updateFrom(newR);
		System.out.println("Review updated successfully");
	}

	//Method that displays all reviews
	public void displayAllReviews() {
		if (reviews.empty()) {
			System.out.println("No reviews.");
			return; }//list is empty

		reviews.findFirst();
		System.out.printf("%-12s %-12s %-12s %-10s %-50s%n",
		        "ReviewID", "ProductID", "CustomerID", "Rating", "Comment");
		System.out.println("------------------------------------------------------------------------------------------------");
		while (true) {
			reviews.retrieve().display();

			if (reviews.last())
				break;

			reviews.findNext();
		}//end of while
		System.out.println("------------------------------------------------------------------------------------------------");
	}

	//Method that prints reviews of a certain customer
	public void reviewsByCustomer(int customerId) {
		System.out.println("------------------------------------------------------------------------------------------------");
		if (reviews.empty()) {
			System.out.println("No reviews.");
			return;}//list is empty

		boolean found = false;
		reviews.findFirst();
		System.out.printf("%-12s %-12s %-12s %-10s %-50s%n",
		        "ReviewID", "ProductID", "CustomerID", "Rating", "Comment");
		System.out.println("------------------------------------------------------------------------------------------------");
		while (true) {
			if (reviews.retrieve().getCustomerId() == customerId) {
				reviews.retrieve().display();
				found = true;}

			if (reviews.last()) 
				break;//when end of list is reached
			reviews.findNext();
		}//end of while
		if (!found) System.out.println("This customer has no reviews");
	}

	//Method that converts each line in the file to a review
	private Review convertLine(String line) {
		String[] a = line.split(",", 5);

		return new Review(
				Integer.parseInt(a[0].trim()),
				Integer.parseInt(a[1].trim()),
				Integer.parseInt(a[2].trim()),
				Integer.parseInt(a[3].trim()),
				a[4]  
				);
	}

	//Method that loads data from file
	public void loadReviews(String fileName) {
		try {
			File f = new File(fileName);
			Scanner read = new Scanner(f);
			read.nextLine();
			int count=0;
			while (read.hasNextLine()) {
				String line = read.nextLine().trim();
				if (line.isEmpty()) continue;

				Review r = convertLine(line);
				reviews.insert(r);

				// Link to product and customer
				Product p = pm.searchById(r.getProductId());
				Customer c = cm.searchById(r.getCustomerId());
				if (p != null) p.addReview(r);
				if (c != null) c.addReview(r);

				count++;
			}
			read.close();
			System.out.println(count + " reviews loaded successfully!"); 
		}
		catch (Exception e) {
			System.out.println("Error loading reviews from file," + e.getMessage());
		}
	}

	//getter:
	public LinkedList<Review> getReviews() { return reviews; }
}
