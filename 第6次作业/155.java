package minStack;

import java.util.Stack;

class MinStack {
    private Stack<Integer> stack = new Stack<Integer>();
    private Stack<Integer> minstack = new Stack<Integer>();
    public void push(int x) {
        if(minstack.isEmpty() || minstack.peek()>=x)
            minstack.push(x);
        stack.push(x);
    }

    public void pop() {
        if(minstack.peek().equals(stack.peek()))
            minstack.pop();
        stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minstack.peek();
    }
}
