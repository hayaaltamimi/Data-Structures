## E-commerce Inventory & Order Management System

### Course
CSC212 – Data Structures  
College of Computer and Information Sciences  
King Saud University

---

## Project Overview
This project implements an E-commerce Inventory & Order Management System that manages products, customers, orders, and reviews.

Phase I provides a full working system using linear data structures (Singly Linked Lists).
Phase II upgrades the system by replacing key linear structures with AVL Trees to improve performance and enable more efficient queries.

---

## Phase I (Linked Lists)
Phase I is implemented using custom generic Singly Linked List structures to manage the core entities in the system. Core operations such as searching, updating, and removing are generally linear-time due to list traversal.

Core data structures:
- Node (generic)
- LinkedList (generic singly linked list)

Main modules:
- Products management (add/search/update/remove, out-of-stock tracking, top-rated products)
- Customers management (add/search, view activity)
- Orders management (add/search/update/remove, between two dates)
- Reviews management (add/update/search, reviews by customer, common highly-rated products)

---

## Phase II (AVL Trees Upgrade)
Phase II refactors Phase I by replacing Phase I’s linear structures with AVL Trees for Products, Customers, and Orders to achieve logarithmic-time performance for core operations.

Key improvements:
- Products, Customers, and Orders stored in AVL Trees (balanced BST)
- Improved efficiency for insert/search/remove operations to O(log n)
- Supports advanced queries such as sorted traversals and range-based operations

Core data structures:
- AVLTree (generic self-balancing tree)
- AVLNode (inner class)

---

## Key Features
- Product inventory management (add, update, remove, search)
- Customer registration and customer activity tracking
- Order placement, cancellation, status updates
- Order history retrieval and date-range queries
- Review management (add/update, show by customer)
- Product rating analytics (top-rated, most reviewed, etc.)

---

## Algorithms and Concepts
- Singly Linked Lists (Phase I)
- AVL Trees and rotations (Phase II)
- In-order traversals for sorted output
- Range / interval queries (e.g., price range, date range)
