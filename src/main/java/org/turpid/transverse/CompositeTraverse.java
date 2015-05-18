package org.turpid.transverse;

import java.util.Stack;

public class CompositeTraverse {
	Traverselet[] traverselets;
	Buildlet builder = (in, p, stack, cli, clo, clp) -> {
		return null;
	};
	Stack<Object> stack = new Stack<Object>();
	Object result;

	CompositeTraverse(Traverselet... traverselets) {
		this.traverselets = traverselets;
		stack.push(new Buildlet.Root());
	}

	public CompositeTraverse(Buildlet builder, Traverselet... traverselets) {
		this(traverselets);
		this.builder = builder;
	}

	void traverse(Object in) {
		Object parent = stack.peek();
		stack.push(in);
		Object result = builder.build(in, parent, stack, in.getClass(), null,
				stack.peek().getClass());
		for (Traverselet t : traverselets) {
			try {
				t.traverse(in, this);
			} catch (ClassCastException e) {
				// nope
			}
		}
		if (result != null)
			this.result = result;
		stack.pop();
	}

	public <T> T getResult(Class<T> clo) {
		return clo.cast(result);
	}

}