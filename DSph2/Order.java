package DSph2;
import java.time.LocalDate;

public class Order {

	public enum Status { pending, shipped, delivered, cancelled };
	private int orderId; 
	private int customerId;//as reference 
	private String prod_Ids;//to add it to the product list
	private LinkedList<Integer> productsId;
	private double totalPrice;
	private LocalDate orderDate;    
	private Status status;

	public Order() {
		this.orderId = 0;
		this.customerId = 0;
		this.prod_Ids = "";
		this.totalPrice = 0;
		this.orderDate = LocalDate.now();
		this.status = Status.pending;
		this.productsId = new LinkedList<>();;
	}

	public Order(int orderId, int customerId, String prod_Ids, double totalPrice,  LocalDate orderDate, Status status) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.prod_Ids=prod_Ids;       
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.status = status;
		this.productsId = new LinkedList<>();
		addIds(prod_Ids);
	}

	public void addIds(String Ids){//add to the product list
		String a[]=Ids.split(";");
		for(int i=0;i<a.length;i++)
			productsId.insert(Integer.parseInt(a[i].trim()));
	}
	
	public void addId(int id) {
	    productsId.insert(id);
	    if (prod_Ids == null || prod_Ids.isEmpty()) {
	        prod_Ids = String.valueOf(id);
	    } else {
	        prod_Ids += ";" + id;
	    }
	}

	public void UpdateOrder (Order o) {
		this.orderId = o.orderId;
		this.customerId =o.customerId;
		this.prod_Ids =o.prod_Ids;
		this.totalPrice =o.totalPrice;
		this.orderDate =o.orderDate;
		this.status =o.status;
		this.productsId =o.productsId;
	}


	public void display() {
		System.out.printf("%-10d %-12d %-20s %-12.2f %-15s %-10s%n",
				orderId, customerId, prod_Ids, totalPrice, orderDate, status);
	}

	//getters:
	public int getOrderId() { return orderId; }
	public int getCustomerId() { return customerId; }
	public  LocalDate getOrderDate() { return orderDate; }

	//setters:
	public void setOrderId(int orderId) { this.orderId = orderId; }
	public void setCustomerId(int customerId) { this.customerId = customerId; }
	public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
	public void setOrderDate(LocalDate orderDate) {	this.orderDate = orderDate;	}
	public void setStatus(Status status) { this.status = status; }
}