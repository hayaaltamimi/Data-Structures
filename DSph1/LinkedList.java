package DSph1;
public class LinkedList<T> {
	private Node<T> head;
	private Node<T> current;
	
	public LinkedList() { head = current = null; }
	
	public boolean empty() { return head == null; }

	public boolean last() { return current == null || current.next == null; }

	public void findFirst() { current = head; }

	public void findNext() { if (current != null) current = current.next; }

	public T retrieve() { return current == null ? null : current.data; }

	// insert after current, if list is empty, head=current=new node
	public void insert(T val) {
		System.out.println("string");
		if (empty()) {
			head = current = new Node<>(val);
			return;
		}
		Node<T> tmp = current.next;
		current.next = new Node<>(val);
		current = current.next;
		current.next = tmp;
	}

	// remove current node, current becomes next (or head)
	public void remove() {
		if (current == null) return;
		if (current == head) {
			head = head.next;
			current = head;
			return;
		}
		Node<T> tmp = head;
		while (tmp != null && tmp.next != current) tmp = tmp.next;
		if (tmp != null) tmp.next = current.next;
		current = (current != null && current.next != null) ? current.next : head;
	}
	
	//returns size
	public int size() {
		int n = 0;
		Node<T> p = head;
		while (p != null) { n++; p = p.next; }
		return n;
	}
}