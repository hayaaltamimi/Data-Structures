package DSph2;
public class LinkedList<T> {
	private Node<T> head;
	private Node<T> current;

	public LinkedList() { head = current = null; }

	//checks if the list is empty (if head is null)
	public boolean empty() { return head == null; }

	//checks if the current is the last node (if next is null)
	public boolean last() { return current == null || current.next == null; }

	//Head node becomes current
	public void findFirst() { current = head; }

	//Current moves to next node
	public void findNext() { if (current != null) current = current.next; }

	//Retrieves data at current
	public T retrieve() { return current == null ? null : current.data; }

	//Inserts after current (if list is empty head=current=new node)
	public void insert(T val) {
		if (empty()) {
			head = current = new Node<>(val);
			return;
		}
		Node<T> tmp = current.next;
		current.next = new Node<>(val);
		current = current.next;
		current.next = tmp;
	}

	//Removes current node, current becomes next (or head if it the last node left)
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

	//Returns size of list
	public int size() {
		int n = 0;
		Node<T> p = head;
		while (p != null) { n++; p = p.next; }
		return n;
	}
}