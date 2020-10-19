# Implement EventStore

This is my implementation of Intelie Event Store challenge.

### Design choices
 
To implement this challenge I’ve chosen to use a ConcurrentLinkedQueue because I found that it was the data structure that would bring the application the best relation between costs and benefits, as explained below. 

ConcurrentLinkedQueue provides an unbounded, thread-safe, type safe and non-blocking queue, based on linked nodes that employs an efficient "wait-free" algorithm.

Although ConcurrentLinkedQueue is an unbounded queue and there is no provision to specify the queue size during creation, witch could bring memory cost to the application, it offers a non-lock concurrency control that brings the application great performance.

Another benefit from this data structure is that it implements a linked list. This would make a lot easier to recreate a state of the application than other data structures with random access, even though its a little bit slower.

ConcurrentLinkedQueue and its iterator implement all of the optional methods of the Queue and Iterator interfaces witch brings memory consistency effects. Insert actions in a thread always occur before access and remove actions in other threads.

In order to improve maximize memory-efficiency in the application the ConcurrentLinkedQueue is only created when of the first event insertion. 

The event iterator class implementation also takes care of memory efficiency implementing autoclosable interface witch guarantees that the iterator will be closed. The close method makes the iterator point to null so java's garbage collector can take care of the unused objects remaining in memory.

Thread-safe is also guaranteed in the Event class due to it’s immutable state. This class also implements the methods equals and hashcode that makes the comparison	of Events much more efficient and faster.

