package org.turpid.transverse;

import java.util.Stack;

public interface TraverseHook<In, Out, Parent> {
	void handle(In in, Parent p, Stack<?> stackIn, Class<In> cli, Class<Parent> clp);
}
