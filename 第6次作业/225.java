package implementStackUsingQueues;

import java.util.LinkedList;
import java.util.Queue;

class MyStack {
	private Queue<Integer> queue = new LinkedList<Integer>();
    // Push element x onto stack.
    public void push(int x) {
    	queue.offer(x);  
        int i=queue.size()-1;  
        while(i>0)  
        {  
            queue.offer(queue.poll());  
            i--;  
        }  
    }

    // Removes the element on top of the stack.
    public void pop() {
    	queue.poll(); 
    }

    // Get the top element.
    public int top() {
        return queue.peek();
    }

    // Return whether the stack is empty.
    public boolean empty() {
        return queue.isEmpty();
    }

}
