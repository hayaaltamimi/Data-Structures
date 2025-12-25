package DSph1;
import java.io.File;
import java.util.Scanner;

public class Review {

	private int reviewId;
	private int productId;
	private int customerId;
	private int rating;
	private String comment;

	public Review(int reviewId, int productId, int customerId, int rating, String comment) {
		this.reviewId = reviewId;
		this.productId = productId;
		this.customerId = customerId;
		this.rating = rating;
		this.comment = comment;
	}

	public void updateFrom(Review newR) {
		this.rating = newR.rating;
		this.comment = newR.comment;
	}

	public void display() {
		System.out.printf("   Review%-4d | Product:%-4d | Customer:%-4d | %d stars | %s%n",
				reviewId, productId, customerId, rating, comment);
	}

	//getters:
	public int getReviewId() { return reviewId; }
	public int getProductId() { return productId; }
	public int getCustomerId() { return customerId; }
	public int getRating() { return rating; }
	public String getComment() { return comment; }

}