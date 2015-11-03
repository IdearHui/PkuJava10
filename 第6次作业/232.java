package implementQueueUsingStack;

import java.util.Stack;

class MyQueue {
    // Push element x to the back of queue.
	Stack<Integer> stack=new Stack<>();
	Stack<Integer> stack2=new Stack<>();
	
    public void push(int x) {
    	while(!stack2.isEmpty())
    		stack.push(stack2.pop());
    	stack.push(x);
        
    }

    // Removes the element from in front of queue.
    public void pop() {
    	
    	while(!stack.isEmpty())
    		stack2.push(stack.pop());
        stack2.pop();
      
    }

    // Get the front element.
    public int peek() {
    	while(!stack.isEmpty())
    		stack2.push(stack.pop());
        return stack2.peek();
      
    }

    // Return whether the queue is empty.
    public boolean empty() {
    	while(!stack2.isEmpty())
    		stack.push(stack2.pop());
        return stack.isEmpty();
    }
}