package DSph2;
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

	//Method that updates a review
	public void updateFrom(Review newR) {
		this.rating = newR.rating;
		this.comment = newR.comment;
	}

	//Method that displays a review
	public void display() {
		System.out.printf("%-12d %-12d %-12d %-10d %-50s%n",reviewId, productId, customerId, rating, comment);	
	}

	//getters:
	public int getReviewId() { return reviewId; }
	public int getProductId() { return productId; }
	public int getCustomerId() { return customerId; }
	public int getRating() { return rating; }
	public String getComment() { return comment; }

}