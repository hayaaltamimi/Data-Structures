package DSph1;
import java.io.File;
import java.util.Scanner;

public class ReviewsManager {

	private LinkedList<Review> reviews;
	private ProductsManager pm;
	private Customers cm;

	public ReviewsManager() {
    	 this.pm = null;
         this.cm = null;
         this.reviews = null;
	}

	public ReviewsManager(ProductsManager pm,Customers cm) {
		this.pm = pm;
		this.cm = cm;
		this.reviews = new LinkedList<>();
	}

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

	public void updateReview(int id, Review newR) {
		Review old = searchById(id);
		if (old == null) {
			System.out.println("Review not found.");
			return;}
		old.updateFrom(newR);
		System.out.println("Review updated successfully");
	}

	public void reviewsByCustomer(int customerId) {
		System.out.println("----------------------");
		if (reviews.empty()) {
			System.out.println("No reviews.");
			return;}//list is empty

		boolean found = false;
		reviews.findFirst();
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

	public void displayAllReviews() {
		if (reviews.empty()) {
			System.out.println("No reviews.");
			return; }//list is empty

		reviews.findFirst();
		while (true) {
			reviews.retrieve().display();

			if (reviews.last())
				break;

			reviews.findNext();
		}//end of while
	}

	public void commonHighlyRatedProducts(int customerId1, int customerId2) {
		LinkedList<Product> allProducts = pm.getProductsList();
		if(allProducts.empty()) {
			System.out.println("No products found.");
			return; }

		System.out.printf("Common highly-rated products (>4) for customers %d & %d:%n", customerId1, customerId2);
		System.out.println("---------------------------------------------------");
		boolean anyFound = false;
		allProducts.findFirst();
		while(true) {
			Product p = allProducts.retrieve();
			int productId = p.getProductId();

			if(p.getAverageRating() > 4) {
				boolean found1 = false;
				boolean found2 = false;

				if(!reviews.empty()) {
					reviews.findFirst();
					while(true) {
						Review r = reviews.retrieve();

						if(r.getProductId() == productId) {
							if(r.getCustomerId() == customerId1) found1 = true;
							if(r.getCustomerId() == customerId2) found2 = true;
						}
						if(reviews.last()) break;
						reviews.findNext();
					}//end of while(inner)
				}
				if(found1 && found2) {
					System.out.println("- " + p.getName() + " | ID: " + productId +  " | Averageg Rating: " + p.getAverageRating());
					anyFound = true;}

			}
			if(allProducts.last()) break;
			allProducts.findNext();
		}//end of while(outer)
		if(!anyFound)
			System.out.println("No common highly rated products found.");
	}

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
			System.out.println( "Reviews loaded successfully."); 
		}
		catch (Exception e) {
			System.out.println("Error loading reviews from file," + e.getMessage());
		}
	}

	//getters
	public LinkedList<Review> getReviews() { return reviews; }

}
