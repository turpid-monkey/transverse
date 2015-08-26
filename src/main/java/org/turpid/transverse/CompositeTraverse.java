package org.turpid.transverse;

import java.util.Stack;

public class CompositeTraverse {
	Traverselet[] traverselets;
	TraverseHook hook;
	final Stack<Object> stack = new Stack<Object>();
	final static Object ROOT = new Traverselet.Root();

	CompositeTraverse(Traverselet... traverselets) {
		this.traverselets = traverselets;
		stack.push(ROOT);
	}

	public CompositeTraverse(TraverseHook hook, Traverselet... traverselets) {
		this(traverselets);
		this.hook = hook;
	}

	public void traverse(Object in) {
		Object parent = stack.peek();
		stack.push(in);
		if (hook != null)
			hook.handle(in, parent, stack, in.getClass(), stack.peek()
					.getClass());
		for (Traverselet t : traverselets) {
			try {
				t.traverse(in, this);
			} catch (ClassCastException e) {
				// nope
			}
		}
		stack.pop();
	}
}