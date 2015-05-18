package org.turpid.transverse;

import java.util.Stack;

@FunctionalInterface
public interface Buildlet<In, Out, Parent> {
	Out build(In in, Parent p, Stack<?> stackIn, Class<In> cli, Class<Out> clo,
			Class<Parent> clp);

	static class Root {
	}
}