package DSph2;
import java.time.LocalDate;
import java.util.Date;

public class AVLTree<K extends Comparable<K>, T>{

	//AVLNode class:
	class AVLNode<K extends Comparable<K>, T> {
		public K key;
		public T data;
		AVLNode<K,T> parent; //parent pointer 
		AVLNode<K,T> left; //left child pointer
		AVLNode<K,T> right; //right child pointer
		int bf; //balance 

		public AVLNode() {
			this.key = null;  
			this.data = null;
			this.parent = this.left = this.right = null;
			this.bf = 0;
		}

		public AVLNode(K key, T data) {
			this.key = key  ;  
			this.data = data;
			this.parent = this.left = this.right = null;
			this.bf = 0;
		}

		public AVLNode(K key, T data, AVLNode<K,T> p, AVLNode<K,T> l, AVLNode<K,T> r){
			this.key = key  ;  
			this.data = data;
			left = l;
			right = r;
			parent = p;
			bf =0;
		}

		public String toString() {
			return "AVL Node{" + "key=" + key + ", data =" + data + '}';
		}


	}//end AVLNode

	//------------------------------------------------------------------------------------
	private AVLNode<K,T> root;
	private AVLNode<K,T>  curr;
	private int count;

	public AVLTree() {
		root = curr = null;
		count = 0;
	}

	public boolean empty() {
		return root == null;
	}

	public int size() {
		return count;
	}

	//Returns the key and data of the current element
	public T retrieve()
	{
		T data =null;
		if (curr != null)
			data = curr.data;
		return data;
	}

	//Updates the data of the current element
	public void update(T e)
	{
		if (curr != null)
			curr.data = e;
	}

	//Searches for the key in the tree, returns the data or null (if not found)
	private T searchTreeHelper(AVLNode<K,T> node, K key) {
		// Place your code here\\
		if (node == null)
			return null;
		else if (node.key.compareTo(key) ==0) 
		{
			curr = node;
			return node.data;
		} 
		else if (node.key.compareTo(key) >0)
			return searchTreeHelper(node.left, key);
		else
			return searchTreeHelper(node.right, key);
	}
	//Updates the balance factor of the node
	private void updateBalance(AVLNode<K,T> node) {
		if (node.bf < -1 || node.bf > 1) {
			rebalance(node);
			return;
		}

		if (node.parent != null) {
			if (node == node.parent.left) {
				node.parent.bf -= 1;
			} 

			if (node == node.parent.right) {
				node.parent.bf += 1;
			}

			if (node.parent.bf != 0) { 
				updateBalance(node.parent);
			}
		}
	}

	//Balances the tree
	void rebalance(AVLNode<K,T> node) {
		if (node.bf > 0) { 
			if (node.right.bf < 0) {
				rightRotate(node.right);
				leftRotate(node);
			} else {
				leftRotate(node);
			}
		} else if (node.bf < 0) {
			if (node.left.bf > 0) {
				leftRotate(node.left);
				rightRotate(node);
			} else {
				rightRotate(node);
			}
		}
	}

	public boolean find(K key) {
		T data = searchTreeHelper(this.root, key);
		if ( data != null)
			return true;
		return false;
	}


	void leftRotate(AVLNode<K,T> x) {
		AVLNode<K,T> y = x.right;
		x.right = y.left;
		if (y.left != null) {
			y.left.parent = x;
		}

		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
		x.bf = x.bf - 1 - Math.max(0, y.bf);
		y.bf = y.bf - 1 + Math.min(0, x.bf);
	}

	void rightRotate(AVLNode<K,T> x) {
		AVLNode<K,T> y = x.left;
		x.left = y.right;
		if (y.right != null) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;

		// update the balance factor
		x.bf = x.bf + 1 - Math.min(0, y.bf);
		y.bf = y.bf + 1 + Math.max(0, x.bf);
	}



	public boolean insert(K key, T data) {
	
		AVLNode<K,T> node = new AVLNode<K,T>(key, data);

		AVLNode<K,T> p = null;
		AVLNode<K,T> current = this.root;

		while (current != null) {
			p = current;
			if (node.key.compareTo(current.key) ==0) {
				return false;
			}else if (node.key.compareTo(current.key) <0 ) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		node.parent = p;
		if (p == null) {
			root = node;
			curr = node;
		} else if (node.key.compareTo(p.key) < 0 ) {
			p.left = node;
		} else {
			p.right = node;
		}
		count ++;
		updateBalance(node);
		System.out.println("added");
		return true;        
	}

	public boolean removeKey(K key) {
		K k1 = key;
		AVLNode<K,T>  p = root;
		AVLNode<K,T>  q = null; 

		while (p != null) 
		{
			if (k1.compareTo(p.key) <0)
			{
				q =p;
				p = p.left;
			} 
			else if (k1.compareTo(p.key) >0)
			{    
				q = p;
				p = p.right;
			}
			else 
			{ 
				if ((p.left != null) && (p.right != null)) { 
					AVLNode<K,T> min = p.right;
					q = p;
					while (min.left != null){
						q = min;
						min = min.left;}

					p.key = min.key;
					p.data = min.data;
					k1 = min.key;
					p = min;}
				if (p.left != null) 
				{ 

					p = p.left;
				} 
				else 
				{ 
					p = p.right;
				}
				if (q == null)
				{ 
					root = p;
					this.updateBalance(p);
				} 
				else 
				{
					if (k1.compareTo(q.key) <0)
						q.left = p;
					else 
						q.right = p;
					this.updateBalance(q);
				}
				count--;
				curr = root;
				return true;    
			} 
		} 
		return false;
	}


	public LinkedList<T>  inOrdertraverseData() {
		LinkedList<T> data = new LinkedList<T>();
		private_inOrdertraverseData( root , data);
		return data;
	}

	private void  private_inOrdertraverseData(AVLNode<K, T>  node, LinkedList<T> data)
	{
		if (node == null)
			return ;
		private_inOrdertraverseData(node.left, data );
		data.insert(node.data);
		private_inOrdertraverseData(node.right, data);
	}

	public AVLTree<Date, T> intervalSearchDate(LocalDate k1, LocalDate k2) {
		AVLTree<Date, T> q = new AVLTree<Date, T>();

		if (k1.isAfter(k2)) {
			LocalDate temp = k1;
			k1 = k2;
			k2 = temp;
		}

		if (root != null && root.data instanceof Order) {
			rec_intervalSearchDate(k1, k2, root, q);
		}

		return q;
	}
	private void rec_intervalSearchDate(LocalDate Ldate1, LocalDate Ldate2,
			AVLNode<K, T> p, AVLTree<Date, T> q) {
		if (p == null)
			return;

		rec_intervalSearchDate(Ldate1, Ldate2, p.left, q);

		if (p.data instanceof Order) {
			LocalDate od = ((Order) p.data).getOrderDate();

			if (!od.isBefore(Ldate1) && !od.isAfter(Ldate2)) {
				Date d = java.sql.Date.valueOf(od); 
				boolean inserted = q.insert(d, p.data);

				//if not inserted(duplicate date), change the time so that we can insert it
				if (!inserted) {
					long base = d.getTime();
					int offset = 1;
					while (!q.insert(new Date(base + offset), p.data)) {
						offset++;
					}
				}
			}
		}

		rec_intervalSearchDate(Ldate1, Ldate2, p.right, q);
	}

	public void inOrdertraverseOutStock() {
		System.out.println("Products out of stock:");
		System.out.printf("%-12s %-25s %-10s %-8s %-8s%n",
				"ProductID", "Name", "Price", "Stock", "AvgRating");
		System.out.println("------------------------------------------------------------------------------------------------");
		private_inOrdertraverseOutStock( root);
	}

	private void  private_inOrdertraverseOutStock(AVLNode<K, T>  node)
	{
		if (node == null)
			return ;
		private_inOrdertraverseOutStock(node.left);
		if (node.data instanceof Product) {
			Product p = (Product) node.data;
			if (p.getStock() == 0) {
				p.display(); // هنا أكيد ينادي toString()
			}}
		private_inOrdertraverseOutStock(node.right);
	}

	public LinkedList <T> intervalSearchprice(double k1, double k2)
	{
		LinkedList <T> q = new LinkedList <T>();
		if (root != null)
			rec_intervalSearchprice (k1 , k2, root , q);
		return q;
	}

	private void rec_intervalSearchprice (double k1, double k2, AVLNode <K, T> p , LinkedList <T> q)
	{
		if (p == null)
			return;
		else
		{
			rec_intervalSearchprice (k1, k2 , p.left , q);
			if (p.data instanceof Product) 
				if ((( (Product) p.data).getPrice() >= k1 ) && ((( Product) p.data).getPrice() <=k2))
					q.insert(p.data);
			rec_intervalSearchprice (k1, k2 , p.right , q);

		}
	}   
}