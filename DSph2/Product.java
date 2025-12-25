package DSph2;
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

	//Method that adds review
	public void addReview(Review r) { reviews.insert(r); }

	//Method that displays product info
	public void display() { 
		System.out.printf("%-12d %-25s %-10.2f %-8d %-8.1f%n", productId, name, price, stock, getAverageRating()); 
	}

	//Method that calculates average rating of a product
	public double getAverageRating() {
		if (reviews.empty()) 
			return 0;

		reviews.findFirst();
		double sum = 0;
		int count = 0;

		while (true) {
			Review r = reviews.retrieve();
			if (r != null) {
				sum += r.getRating();
				count++;
			}
			if (reviews.last())
				break;
			reviews.findNext();
		}
		return sum / count;
	}

	//getters:
	public int getProductId() { return productId; }
	public String getName() { return name; }
	public double getPrice() { return price; }
	public int getStock() { return stock; }
	public LinkedList<Review> getReviews() { return reviews; }

	//setters
	public void setStock(int s) { stock = s; }
}
