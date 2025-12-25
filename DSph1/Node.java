package DSph1;
public class Node <T>{
	T data;
	Node <T> next;

	public Node() {
		data=null;
		next=null;
	}

	public Node(T val) {
		data=val;
		next=null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}
}