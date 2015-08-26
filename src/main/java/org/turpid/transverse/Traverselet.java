package org.turpid.transverse;

@FunctionalInterface
public interface Traverselet<In> {
	void traverse(In o, CompositeTraverse ctx);
	

	static class Root {
	}
}